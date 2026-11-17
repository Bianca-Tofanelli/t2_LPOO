package lpoo.sym.parser;

import lpoo.sym.*;

/**
 *
 * @author insert your name here
 */
public class Parser
{
  public Parser()
  {
    scanner = new Scanner();
  }

  public void setInput(Input input)
    throws ParseException
  {
    scanner.setInput(input);
    advance();
  }

  public Command parse()
    throws ParseException
  {
    Command c = null;

    if (token.type == TokenType.EVAL)
    {
      advance(); // EVAL
      match(TokenType.LPAREN);
      expression();
      match(TokenType.COMMA);
      args();
      match(TokenType.RPAREN);
      c = Command.eval;
      // insert your code here
      c.setArguments(expr, x, y, z);
      //
    }
    // insert your code here
    else if (token.type == TokenType.DIFF)
    {
      c = diff();
    }
    else if (token.type == TokenType.DEFINE)
    {
      c = define();
    }
    else if (token.type == TokenType.CLEAR)
    {
      advance(); 
      c = Command.clear;
    }
    else if (token.type == TokenType.LIST)
    {
      advance(); 
      c = Command.list;
    }
    else if (token.type == TokenType.NAME)
    {
      c = assignment();
    }
    else if (token.type == TokenType.EOI)
    {
      error(SYNTAX);
    }
    //
    else if (token.type == TokenType.EXIT)
    {
      advance(); // EXIT
      c = Command.exit;
    }
    if (token.type != TokenType.EOI)
      error(SYNTAX);
    return c;
  }

  private void args()
    throws ParseException
  {
    if (token.type != TokenType.FLOAT)
      error(NUMBER_EXPECTED);

    float x = ((Token.Float)token).value();

    advance();
    match(TokenType.COMMA);
    if (token.type != TokenType.FLOAT)
      error(NUMBER_EXPECTED);

    float y = ((Token.Float)token).value();

    advance();
    match(TokenType.COMMA);
    if (token.type != TokenType.FLOAT)
      error(NUMBER_EXPECTED);

    float z = ((Token.Float)token).value();

    advance();
    // insert your code here
    this.x = x;
    this.y = y;
    this.z = z;
    //
  }

  private void expression()
    throws ParseException
  {
    // insert your code here
    expr = addSub();
    //
  }

  private void error(int code, Object... args)
    throws ParseException
  {
    throw new ParseException(parseErrorMessages[code], args);
  }

  private void error(String s, Object... args)
    throws ParseException
  {
    throw new ParseException(s, args);
  }

  private Token advance()
    throws ParseException
  {
    try
    {
      token = scanner.nextToken();
    }
    catch (InvalidInputException e)
    {
      error(e.getMessage());
    }
    return token;
  }

  private void match(TokenType t)
    throws ParseException
  {
    if (token.type != t)
    {
      if (token.type == TokenType.EOI)
        error(EXPECTED, t.toString());
      error(UNEXPECTED, token.lexeme);
    }
    advance();
  }

  private Scanner scanner;
  private Token token;

  static final int SYNTAX = 0;
  static final int IDENTIFIER_EXPECTED = 1;
  static final int NUMBER_EXPECTED = 2;
  static final int EXPECTED = 3;
  static final int UNEXPECTED = 4;

  private static final String[] parseErrorMessages =
  {
    "Syntax error",
    "Identifier expected",
    "Number expected",
    "'%1$s' expected",
    "Unexpected '%1$s'"
  };

  // Atributos e métodos auxiliares privados

  private Expression expr;
  private float x;
  private float y;
  private float z;

  private boolean isReservedMathName(String name)
  {
    return name.equals("x") || name.equals("y") || name.equals("z") ||
           name.equals("e") || name.equals("sin") || name.equals("cos") ||
           name.equals("log") || name.equals("pi");
  }

  private Command diff()
    throws ParseException
  {
    advance(); // DIFF
    match(TokenType.LPAREN);
    expression();
    match(TokenType.COMMA);
    if (token.type != TokenType.NAME)
      error(IDENTIFIER_EXPECTED);

    String varName = ((Token.Name)token).value();
    X v = X.byName(varName);

    if (v == null)
      error("Variable 'x', 'y' or 'z' expected");
    advance();
    match(TokenType.RPAREN);

    Command c = Command.diff;
    c.setArguments(expr, v);
    return c;
  }

  private Command define()
    throws ParseException
  {
    advance(); // DEFINE
    if (token.type != TokenType.NAME)
      error(IDENTIFIER_EXPECTED);

    String name = ((Token.Name)token).value();

    if (isReservedMathName(name))
      error("Cannot redefine '%1$s'", name);
    if (Command.hasSymbol(name))
      error("'%1$s' is already defined", name);
    advance();
    match(TokenType.ASSIGN_OP);
    expression();

    Command c = Command.define;
    c.setArguments(name, expr);
    return c;
  }

  private Command assignment()
    throws ParseException
  {
    String name = ((Token.Name)token).value();

    advance();
    if (token.type != TokenType.ASSIGN_OP)
      error(SYNTAX);
    if (isReservedMathName(name))
      error("Cannot assign to '%1$s'", name);
    advance(); // '='
    expression();

    Command c = Command.define;
    c.setArguments(name, expr);
    return c;
  }

  private Expression addSub()
    throws ParseException
  {
    boolean negate = false;

    if (token.type == TokenType.ADD_OP)
      advance();
    else if (token.type == TokenType.SUB_OP)
    {
      negate = true;
      advance();
    }

    Expression e = mul();

    if (negate)
      e = new Minus(e);

    while (token.type == TokenType.ADD_OP || token.type == TokenType.SUB_OP)
    {
      TokenType op = token.type;
      advance();
      Expression right = mul();
      e = (op == TokenType.ADD_OP) ? new Add(e, right) : new Sub(e, right);
    }
    return e;
  }

  private Expression mul()
    throws ParseException
  {
    Expression e = pow();

    while (token.type == TokenType.MUL_OP || token.type == TokenType.DIV_OP)
    {
      TokenType op = token.type;
      advance();
      Expression right = pow();
      e = (op == TokenType.MUL_OP) ? new Mul(e, right) : new Div(e, right);
    }
    return e;
  }

  private Expression pow()
    throws ParseException
  {
    Expression base = primary();

    if (token.type == TokenType.POW_OP)
    {
      advance();
      Expression exp = pow();
      return new Pow(base, exp);
    }
    return base;
  }

  private Expression primary()
    throws ParseException
  {
    if (token.type == TokenType.FLOAT)
    {
      float v = ((Token.Float)token).value();
      advance();
      if (v == 0)
        return Constant.ZERO;
      if (v == 1)
        return Constant.ONE;
      return new Constant(v);
    }
    if (token.type == TokenType.LPAREN)
    {
      advance();
      Expression e = addSub();
      match(TokenType.RPAREN);
      return e;
    }
    if (token.type == TokenType.NAME)
    {
      String name = ((Token.Name)token).value();
      advance();

      if (name.equals("pi"))
        return Constant.PI;

      if (name.equals("sin") || name.equals("cos") || name.equals("log"))
      {
        match(TokenType.LPAREN);
        Expression arg = addSub();
        match(TokenType.RPAREN);
        if (name.equals("sin"))
          return Function.sin(arg);
        if (name.equals("cos"))
          return Function.cos(arg);
        return Function.log(arg);
      }
      X var = X.byName(name);
      if (var != null)
        return var;
      if (name.equals("e"))
        return Constant.E;

      Expression sym = Command.getSymbol(name);
      if (sym == null)
        error("Undefined identifier '%1$s'", name);
      return sym;
    }
    error(SYNTAX);
    return null;
  }

} // Parser