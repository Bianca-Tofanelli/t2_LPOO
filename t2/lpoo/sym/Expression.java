package lpoo.sym;

public abstract class Expression
{
  /**
   * Avalia a expressão no ponto (x, y, z).
   */
  public abstract float eval(float x, float y, float z);

  /**
   * Retorna a derivada parcial da expressão em relação à variável v.
   */
  public abstract Expression diff(X v);

  public Expression simplify()
  {
    return this;
  }

  public final boolean isConstant()
  {
    return this instanceof Constant;
  }

  public final boolean isBinary()
  {
    return this instanceof BinaryExpression;
  }

} // Expression
