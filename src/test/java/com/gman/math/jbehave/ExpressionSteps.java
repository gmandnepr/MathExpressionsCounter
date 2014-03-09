package com.gman.math.jbehave;

import com.gman.math.parser.ExpressionParser;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ExpressionSteps {

    private final List<ExpressionWithCalculatedAndExpectedResult> expressions = new ArrayList<>();

    @Given("expressions $expressions")
    public void givenExpressions(ExamplesTable table) {
        for (Map<String, String> row : table.getRows()) {
            expressions.add(new ExpressionWithCalculatedAndExpectedResult(row.get("expression"), Double.parseDouble(row.get("result"))));
        }
    }

    @When("calculator evaluates and counts expressions")
    public void whenCalculatorEvaluatesAndCountsExpressions() {
        for (ExpressionWithCalculatedAndExpectedResult expression : expressions) {
            expression.actual = ExpressionParser.calculate(expression.expression);
        }
    }

    @Then("the results are correct")
    public void thenTheResultsAreCorrect() {
        for (ExpressionWithCalculatedAndExpectedResult expression : expressions) {
            assertEquals(expression.expected, expression.actual, 1e-6);
        }
    }

    private static final class ExpressionWithCalculatedAndExpectedResult {

        private final String expression;
        private final double expected;
        private double actual;

        private ExpressionWithCalculatedAndExpectedResult(String expression, double expected) {
            this.expression = expression;
            this.expected = expected;
        }
    }
}
