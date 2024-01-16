package game.evaluator;

import org.junit.Test;

import static game.evaluator.Assembler.assemble;
import static org.junit.Assert.*;

public class AssemblerTest {

  @Test
  public void testBasicArithmetic() {
    Term equation = assemble("3 + 5 * 2", new MockEditor(2, 1));
    assertEquals(3 + 5 * 2, equation.evaluate(), 0.01);
  }

  @Test
  public void testParenthesesAndExponents() {
    Term equation = assemble("2^3 + (4 * 5)", new MockEditor(2, 1));
    assertEquals(Math.pow(2, 3) + (4 * 5), equation.evaluate(), 0.01);
  }

  @Test
  public void testTrigonometricFunctions() {
    Term equation = assemble("sin(0.5) + cos(0.5)", new MockEditor(2, 1));
    assertEquals(Math.sin(0.5) + Math.cos(0.5), equation.evaluate(), 0.01);
  }

  @Test
  public void testVariables() {
    Term equation = assemble("x + y", new MockEditor(3, 7));
    assertEquals(3 + 7, equation.evaluate(), 0.01);
  }

  @Test
  public void testComplexExpression() {
    Term equation = assemble("(2 + 3) * (4 - 1) / 2", new MockEditor(2, 1));
    assertEquals((2.0 + 3) * (4 - 1) / 2, equation.evaluate(), 0.01);
  }

  @Test
  public void testSquareRootAndExponent() {
    Term equation = assemble("sqrt(16) + 2^3", new MockEditor(2, 1));
    assertEquals(Math.sqrt(16) + Math.pow(2, 3), equation.evaluate(), 0.01);
  }

  @Test
  public void testMultipleVariables() {
    Term equation = assemble("x * 7y / 2", new MockEditor(6, 3));
    assertEquals(6.0 * 7 * 3 / 2, equation.evaluate(), 0.01);
  }

  @Test
  public void testNestedFunctions() {
    Term equation = assemble("sqrt(sin(0.4)^2 + cos(0.4)^2)", new MockEditor(2, 1));
    assertEquals(Math.sqrt(Math.pow(Math.sin(0.4), 2) + Math.pow(Math.cos(0.4), 2)), equation.evaluate(), 0.01);
  }

  @Test
  public void testMixedFunctionsAndVariables() {
    Term equation = assemble("sqrt(x^2 + y^2)", new MockEditor(3, 4));
    assertEquals(Math.sqrt(Math.pow(3, 2) + Math.pow(4, 2)), equation.evaluate(), 0.01);
  }

  @Test
  public void testZeroDivisionCheck() {
    Term equation = assemble("5 / 0", new MockEditor(2, 1));
    assertTrue(Double.isInfinite(equation.evaluate()));
  }

  @Test
  public void testVariableIncrement() {
    Term equation = assemble("t + t", new MockEditor(0, 0));

    // First evaluation
    assertEquals(0 + 0, equation.evaluate(), 0.01);

    // Second evaluation
    assertEquals(1 + 1, equation.evaluate(), 0.01);

    // Third evaluation
    assertEquals(2 + 2, equation.evaluate(), 0.01);
  }
}