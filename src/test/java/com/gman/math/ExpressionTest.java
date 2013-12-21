package com.gman.math;

import com.gman.math.parser.Expression;
import com.gman.math.parser.ExpressionParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * @author gman
 * @since 06.10.13 12:50
 */
@RunWith(Parameterized.class)
public class ExpressionTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.<Object[]>asList(
                new Object[]{"2", 2.0},
                new Object[]{"1e6", 1000000.0},
                new Object[]{"(2)", 2.0},
                new Object[]{"((((2))))", 2.0},
                new Object[]{"+2", 2.0},
                new Object[]{"+(2)", 2.0},
                new Object[]{"-2", -2.0},
                new Object[]{"-(2)", -2.0},
                new Object[]{"-(-2)", 2.0},
                new Object[]{"2+2", 4.0},
                new Object[]{"+2+2", 4.0},
                new Object[]{"-2-2", -4.0},
                new Object[]{"0-2", -2.0},
                new Object[]{"0-2-2", -4.0},
                new Object[]{"-(2+2)", -4.0},
                new Object[]{"-(2+2)+2", -2.0},
                new Object[]{"+(2+2)", 4.0},
                new Object[]{"2+2+2", 6.0},
                new Object[]{"sum(2;2)", 4.0},
                new Object[]{"-sum(2;2)", -4.0},
                new Object[]{"(2+2)", 4.0},
                new Object[]{"(2+2)+2", 6.0},
                new Object[]{"2+(2+2)", 6.0},
                new Object[]{"sum(2;sum(2;2))", 6.0},
                new Object[]{"2+sum(2;2)", 6.0},
                new Object[]{"sum(2;2)+2", 6.0},
                new Object[]{"sum(2;2+2)", 6.0},
                new Object[]{"sum(2+2;2)", 6.0},
                new Object[]{"2*2+2", 6.0},
                new Object[]{"2+2*2", 6.0},
                new Object[]{"2+2*2+2", 8.0},
                new Object[]{"2*2+2*2", 8.0},
                new Object[]{"2*3*4", 24.0},
                new Object[]{"24/3/4", 2.0},
                new Object[]{"2+3*4^5", 3074.0},
                new Object[]{"(2+3*4)^5", 537824.0},
                new Object[]{"(sum(1;1))^5", 32.0},
                new Object[]{"(sum(1;3))!", 24.0},
                new Object[]{"(5)!", 120.0},
                new Object[]{"((3)!)!", 720.0}
        );
    }

    private final String expression;
    private final double calculated;

    public ExpressionTest(String expression, double calculated) {
        this.expression = expression;
        this.calculated = calculated;
    }

    @Test
    public void testCalculationWithExpression() {

        assertEquals(expression, calculated, ExpressionParser.parse(new Expression(expression)).calculate(), 1e-6);
    }
}
