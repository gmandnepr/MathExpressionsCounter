package com.gman.math;

/**
 * @author gman
 * @since 06.10.13 11:18
 */
public enum Operation {

    ADDITION("+", 5, 2, OperationMode.INFIX) {
        @Override
        protected double calc(double[] args) {
            return args[0] + args[1];
        }
    },
    ADDITION_("sum", 6, 2, OperationMode.PREFIX) {
        @Override
        protected double calc(double[] args) {
            return args[0] + args[1];
        }
    },
    SUBTRACTION("-", 5, 2, OperationMode.INFIX) {
        @Override
        protected double calc(double[] args) {
            return args[0] - args[1];
        }
    },
    SUBTRACTION_("sub", 6, 2, OperationMode.PREFIX) {
        @Override
        protected double calc(double[] args) {
            return args[0] - args[1];
        }
    },
    MULTIPLICATION("*", 10, 2, OperationMode.INFIX) {
        @Override
        protected double calc(double[] args) {
            return args[0] * args[1];
        }
    },
    MULTIPLICATION_("mult", 11, 2, OperationMode.PREFIX) {
        @Override
        protected double calc(double[] args) {
            return args[0] * args[1];
        }
    },
    DIVISION("/", 10, 2, OperationMode.INFIX) {
        @Override
        protected double calc(double[] args) {
            return args[0] / args[1];
        }
    },
    DIVISION_("div", 11, 2, OperationMode.PREFIX) {
        @Override
        protected double calc(double[] args) {
            return args[0] / args[1];
        }
    },
    INVOLUTION("^", 15, 2, OperationMode.INFIX) {
        @Override
        protected double calc(double[] args) {
            return Math.pow(args[0], args[1]);
        }
    },
    INVOLUTION_("pow", 15, 2, OperationMode.PREFIX) {
        @Override
        protected double calc(double[] args) {
            return Math.pow(args[0], args[1]);
        }
    },
    FACTORIAL("!", 20, 1, OperationMode.POSTFIX) {
        @Override
        protected double calc(double[] args) {
            final long arg = Math.round(args[0]);
            long result = 1;
            for (int i = 2; i <= arg; i++) {
                result *= i;
            }
            return result;
        }
    };

    private final String name;
    private final int priority;
    private final int argumentsNumber;
    private final OperationMode operationMode;

    private Operation(String name, int priority, int argumentsNumber, OperationMode operationMode) {
        this.name = name;
        this.priority = priority;
        this.argumentsNumber = argumentsNumber;
        this.operationMode = operationMode;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public int getArgumentsNumber() {
        return argumentsNumber;
    }

    public OperationMode getOperationMode() {
        return operationMode;
    }

    public double calculate(double[] args) {
        if (args.length != argumentsNumber) {
            throw new IllegalArgumentException("Wrong parameters number");
        }
        return calc(args);
    }

    protected abstract double calc(double[] args);
}
