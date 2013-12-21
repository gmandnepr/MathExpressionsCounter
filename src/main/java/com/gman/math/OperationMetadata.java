package com.gman.math;

/**
 * @author gman
 * @since 06.10.13 13:03
 */
public class OperationMetadata {

    private final Operation operation;
    private final int position;

    public OperationMetadata(Operation operation, int position) {
        this.operation = operation;
        this.position = position;
    }

    public Operation getOperation() {
        return operation;
    }

    public int getOperationStartPosition() {
        return position;
    }

    public int getOperationEndPosition() {
        return position + operation.getName().length();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OperationMetadata that = (OperationMetadata) o;

        return operation == that.operation &&
                position == that.position;
    }

    @Override
    public int hashCode() {
        int result = operation.hashCode();
        result = 31 * result + position;
        return result;
    }
}
