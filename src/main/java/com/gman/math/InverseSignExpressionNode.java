package com.gman.math;

/**
 * @author gman
 * @since 06.10.13 11:40
 */
public class InverseSignExpressionNode implements Node {

    private final Node node;

    public InverseSignExpressionNode(Node node) {
        this.node = node;
    }

    @Override
    public double calculate() {
        return -node.calculate();
    }
}
