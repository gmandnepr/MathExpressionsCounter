package com.gman.math;

/**
 * @author gman
 * @since 06.10.13 11:40
 */
public class ExpressionNode implements Node {

    private final Operation operation;
    private final Node[] arguments;

    public ExpressionNode(Operation operation, Node[] arguments) {
        this.operation = operation;
        this.arguments = arguments;
    }

    @Override
    public double calculate() {
        final double[] args = new double[arguments.length];
        for (int i = 0; i < args.length; i++) {
            args[i] = arguments[i].calculate();
        }
        return operation.calculate(args);
    }

    @Override
    public String toString() {
        return operation.name();
    }
}
