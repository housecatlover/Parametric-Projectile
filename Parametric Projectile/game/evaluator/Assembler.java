package game.evaluator;

import java.util.function.BiFunction;
import java.util.function.Function;

import game.Collider;
import game.EquationEditor;

public class Assembler {
  private enum EquationConstructors {
    ADDITION('+', Addition::new),
    SUBTRACTION('-', Subtraction::new),
    MULTIPLICATION('*', Multiplication::new),
    DIVISION('/', Division::new),
    POW('^', Pow::new);  //TODO power goes after implied multiplication
    final char symbol;
    final BiFunction<Term, Term, Term> constructor;

    EquationConstructors(char symbol, BiFunction<Term, Term, Term> constructor) {
      this.symbol = symbol;
      this.constructor = constructor;
    }
  }

  private enum VariableConstructors {
    X('x', xCoord::new),
    Y('y', yCoord::new),
    T('t', Tick::new);
    final char symbol;
    final Function<Collider, Term> constructor;

    VariableConstructors(char symbol, Function<Collider, Term> constructor) {
      this.symbol = symbol;
      this.constructor = constructor;
    }
  }

  private enum MathConstructors {
    SQRT("sqrt", Sqrt::new),
    SIN("sin", Sin::new),
    COS("cos", Cos::new),
    TAN("tan", Tan::new);
    final String symbol;
    final Function<Term, Term> constructor;

    MathConstructors(String symbol, Function<Term, Term> constructor) {
      this.symbol = symbol;
      this.constructor = constructor;
    }
  }

  /**
   * Recursively assembles an equation based on the source input and the arguments pulled from the
   * EquationEditor.
   *
   * @param input  the String being translated to an equation
   * @param editor the source of the variables used in the equation
   * @return the assembled Equation
   */
  //Since it's recursive invert the order of operations, ()^*/+- -> +-/*^()
  public static Term assemble(String input, EquationEditor editor) {
    input = input.strip();
    //shed outer parenthesis or use the Math function
    if (input.charAt(0) == '(' && getClose(input, 0) == input.charAt(input.length() - 1)) {
      return assemble(input.substring(1, input.length() - 1), editor);
    }
    for (MathConstructors equation : MathConstructors.values()) {
      int length = equation.symbol.length();
      if (input.startsWith(equation.symbol + "(") &&
              getClose(input, length) == input.length() - 1) {
        return equation.constructor.apply(assemble(
                input.substring(length + 1, input.length() - 1), editor));
      }
    }

    //split by exterior equations
    for (EquationConstructors equation : EquationConstructors.values()) {
      int index = indexOfOut(input, equation.symbol);
      if (index != -1) {
        return equation.constructor.apply(assemble(input.substring(0, index), editor),
                assemble(input.substring(index + 1), editor));
      }
    }

    //Define individual terms (constants, variables, and multiplied parenthesis)
    return parseTerms(input, editor);
  }

  /**
   * Walk character by character through the input to parse the constant or separate the terms
   * with implied multiplication.
   *
   * @param input  the input String
   * @param editor the EquationEditor to pass back to assemble
   * @return the Term equivilant of the input String
   */
  private static Term parseTerms(String input, EquationEditor editor) {
    StringBuilder mem = new StringBuilder();
    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      //constant
      if ("1234567890.".indexOf(c) != -1) {
        mem.append(c);
        continue;
      }
      if (!mem.isEmpty()) { //spaces also work as multiplication
        return new Multiplication(new Constant(Double.parseDouble(mem.toString())),
                assemble(input.substring(i), editor));
      }

      //variable
      for (VariableConstructors equation : VariableConstructors.values()) {
        if (c == equation.symbol) {
          Term first = equation.constructor.apply(editor.getCollider());
          if (i == input.length() - 1) {
            return first;
          }
          return new Multiplication(first, assemble(input.substring(i + 1), editor));
        }
      }
      //parenthesis around the first term
      if (c == '(') {
        int bound = getClose(input, i);
        return new Multiplication(assemble(input.substring(1, bound), editor),
                assemble(input.substring(bound), editor));
      }
    }
    if (!mem.isEmpty()) {
      return new Constant(mem.toString());
    }

    return new Constant(1); // a space is getting multiplied by some other term
  }

  /**
   * Finds the index of the first instance of a character in the given source string that
   * is outside parenthesis.
   *
   * @param source the String being searched
   * @param ch     the character being searched for
   * @return the index of the character if found, or -1 if not
   */
  private static int indexOfOut(String source, char ch) {
    for (int i = 0; i < source.length(); i++) {
      char c = source.charAt(i);
      if (c == '(') {
        i = getClose(source, i); //jump to the close parenthesis
      } else if (c == ch) {
        return i;
      }
    }
    return -1;
  }

  private static int getClose(String source, int begin) {
    int layers = 0;
    for (int j = begin; j < source.length(); j++) {
      switch (source.charAt(j)) {
        case '(':
          layers++;
          break;
        case ')':
          if (--layers == 0) {
            return j;
          }
      }
    }
    return -1;
  }
}