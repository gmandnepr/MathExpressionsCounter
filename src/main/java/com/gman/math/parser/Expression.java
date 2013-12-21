package com.gman.math.parser;

import com.gman.math.Operation;
import com.gman.math.OperationMetadata;
import com.gman.math.OperationMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author gman
 * @since 06.10.13 12:38
 */
public class Expression {

    public static final char OPEN = '(';
    public static final char CLOSE = ')';
    public static final char ARG_SEPARATOR = ';';

    private static final List<Operation> PRIORITY_ORDERED_OPERATIONS;

    static {
        PRIORITY_ORDERED_OPERATIONS = new ArrayList<>(Operation.values().length);
        PRIORITY_ORDERED_OPERATIONS.addAll(Arrays.asList(Operation.values()));
        Collections.sort(PRIORITY_ORDERED_OPERATIONS, new Comparator<Operation>() {
            @Override
            public int compare(Operation o1, Operation o2) {
                return Integer.compare(o1.getPriority(), o2.getPriority());
            }
        });
    }

    protected final String expression;
    protected final int from;
    protected final int to;

    public Expression(String expression) {
        this(expression, 0, expression.length());
    }

    public Expression(String expression, int from, int to) {
        this.expression = expression;
        this.from = from;
        this.to = to;
    }

    public char s(int pos) {
        return expression.charAt(pos);
    }

    public OperationMetadata getLastLowestPriorityOperation() {
        for (Operation operation : PRIORITY_ORDERED_OPERATIONS) {
            int position = to-1;
            while (position >= from) {
                if (s(position) == CLOSE) {
                    position = getLeadingParenthesesPosition(position) - 1;
                } else if (isOperation(operation, position)) {
                    return new OperationMetadata(operation, position);
                } else {
                    position--;
                }
            }
        }
        return null;
    }

    public int getFollowingArgumentEnd(int argumentStart) {
        assert argumentStart >= from;
        assert argumentStart <= to;
        assert s(argumentStart) == OPEN || s(argumentStart) == ARG_SEPARATOR;

        int position = argumentStart + 1;
        while (position < to) {
            if (s(position) == OPEN) {
                position = getFollowingParenthesesPosition(position) + 1;
            } else if (s(position) == ARG_SEPARATOR || s(position) == CLOSE) {
                return position;
            } else {
                position++;
            }
        }

        throw syntaxException("unfinished argument", argumentStart);
    }

    public int getFollowingParenthesesPosition(int parenthesesPosition) {
        assert parenthesesPosition >= from;
        assert parenthesesPosition < to;
        assert s(parenthesesPosition) == OPEN;

        int nesting = 1;
        for (int position = parenthesesPosition + 1; position < to; position++) {
            switch (s(position)) {
                case OPEN:
                    nesting++;
                    break;
                case CLOSE:
                    nesting--;
                    break;
            }
            if (nesting == 0) {
                return position;
            }
        }

        throw syntaxException("unclosed parentheses", parenthesesPosition);
    }

    public int getLeadingParenthesesPosition(int parenthesesPosition) {
        assert parenthesesPosition > from;
        assert parenthesesPosition <= to;
        assert s(parenthesesPosition) == CLOSE;

        int nesting = 1;
        for (int position = parenthesesPosition - 1; position >= from; position--) {
            switch (s(position)) {
                case OPEN:
                    nesting--;
                    break;
                case CLOSE:
                    nesting++;
                    break;
            }
            if (nesting == 0) {
                return position;
            }
        }

        throw syntaxException("unopened parentheses", parenthesesPosition);
    }

    public boolean isOperation(Operation operation, int pos) {
        final String name = operation.getName();
        for (int i = 0; i < name.length(); i++) {
            if (s(pos + i) != name.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean isInParentheses() {
        return to - from > 2 &&
                s(from) == OPEN &&
                s(to - 1) == CLOSE &&
                getFollowingParenthesesPosition(from) == to - 1;
    }

    public Expression subExpression(int from, int to) {
        assert from >= this.from;
        assert to <= this.to;
        return new Expression(expression, from, to);
    }

    public Expression removeOuterParentheses() {
        assert isInParentheses();
        return subExpression(from + 1, to - 1);
    }

    public Expression removeSign() {
        assert s(from) == '+' || s(from) == '-';
        return subExpression(from + 1, to);
    }

    public double parseNumber() {
        final String toParse = expression.substring(from, to);
        if (!toParse.isEmpty()) {
            return Double.parseDouble(toParse);
        } else {
            return 0.0;
        }
    }

    public Expression[] paramsFor(OperationMetadata operationMetadata) {
        switch (operationMetadata.getOperation().getOperationMode()) {
            case PREFIX:
                return paramsForPrefixOperation(operationMetadata);
            case INFIX:
                return paramsForInfixOperation(operationMetadata);
            case POSTFIX:
                return paramsForPostfixOperation(operationMetadata);
            default:
                throw new IllegalStateException("Unreachable");
        }
    }

    private Expression[] paramsForPrefixOperation(OperationMetadata operationMetadata) {
        assert operationMetadata.getOperation().getOperationMode() == OperationMode.PREFIX;

        return params(operationMetadata.getOperation().getArgumentsNumber(), operationMetadata.getOperationEndPosition());
    }

    private Expression[] paramsForInfixOperation(OperationMetadata operationMetadata) {
        assert operationMetadata.getOperation().getOperationMode() == OperationMode.INFIX;

        return new Expression[]{
                subExpression(from, operationMetadata.getOperationStartPosition()),
                subExpression(operationMetadata.getOperationEndPosition(), to)
        };
    }

    private Expression[] paramsForPostfixOperation(OperationMetadata operationMetadata) {
        assert operationMetadata.getOperation().getOperationMode() == OperationMode.POSTFIX;

        final int argumentsStartPosition = getLeadingParenthesesPosition(operationMetadata.getOperationStartPosition() - 1);
        return params(operationMetadata.getOperation().getArgumentsNumber(), argumentsStartPosition);
    }

    private Expression[] params(int parametersNumber, int startPosition) {
        assert s(startPosition) == OPEN;

        int argumentStart = startPosition;
        final Expression[] expressions = new Expression[parametersNumber];
        for (int i = 0; i < parametersNumber; i++) {
            int argumentEnd = getFollowingArgumentEnd(argumentStart);
            expressions[i] = subExpression(argumentStart + 1, argumentEnd);
            argumentStart = argumentEnd;
        }
        return expressions;
    }

    private RuntimeException syntaxException(String message, int pos) {
        return new IllegalArgumentException("Failed to parse exception: " + message + " near " + pos + " symbol");
    }

    @Override
    public String toString() {
        return expression;
    }
}