package com.gman.math.jbehave;

import com.gman.math.parser.Expression;
import com.gman.math.parser.ExpressionParser;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.junit.Assert.assertEquals;

public class OperationSteps {

    private String expression;
    private double result;

    @Given("expression $expression")
    public void givenExpression(String expression) {
        this.expression = expression;
    }

    @When("calculator evaluates and counts expression")
    public void whenCalculatorEvaluatesAndCountsExpression() {
        result = ExpressionParser.parse(new Expression(expression)).calculate();
    }

    @Then("the result is $result")
    public void thenTheResultIs(double result) {
        assertEquals(result, this.result, 1e-6);
    }
}
