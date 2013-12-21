package com.gman.math;

/**
 * @author gman
 * @since 06.10.13 11:40
 */
public class NumberNode implements Node {

    private final double value;

    public NumberNode(double value) {
        this.value = value;
    }

    @Override
    public double calculate() {
        return value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
