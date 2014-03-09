package com.gman.math.parser;

import com.gman.math.*;

/**
 * @author gman
 * @since 06.10.13 11:28
 */
public final class ExpressionParser {

    private static final OperationMetadata PLUS_SIGN = new OperationMetadata(Operation.ADDITION, 0);
    private static final OperationMetadata MINUS_SIGN = new OperationMetadata(Operation.SUBTRACTION, 0);

    private ExpressionParser() {
    }

    public static double calculate(String expression) {
        return parse(new Expression(expression)).calculate();
    }

    public static Node parse(Expression expression) {

        if (expression.isInParentheses()) {
            return parse(expression.removeOuterParentheses());
        }

        OperationMetadata operationMetadata = expression.getLastLowestPriorityOperation();
        if (PLUS_SIGN.equals(operationMetadata)) {
            return parse(expression.removeSign());
        } else if (MINUS_SIGN.equals(operationMetadata)) {
            return new InverseSignExpressionNode(parse(expression.removeSign()));
        } else if (operationMetadata != null) {
            final Expression[] expressions = expression.paramsFor(operationMetadata);
            final Node[] nodes = new Node[expressions.length];
            for (int i = 0; i < expressions.length; i++) {
                nodes[i] = parse(expressions[i]);
            }
            return new ExpressionNode(operationMetadata.getOperation(), nodes);
        } else {
            return new NumberNode(expression.parseNumber());
        }
    }
}
