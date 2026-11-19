package lpoo.sym;
/*
 * Autores: 
Bianca Andrade Tofanelli, 
Luis Henrique Cardoso, 
Paola Vendruscolo
 */
public final class Minus
  extends UnaryExpression
{
  public Minus(Expression e)
  {
    super(e, '-');
  }

  @Override
  public float eval(float x, float y, float z)
  {
    return -e.eval(x, y, z);
  }

  @Override
  /**
   * Retorna a derivada da parcial da subtracao
   */
  public Expression diff(X v)
  {
    return (new Minus(e.diff(v))).simplify();
  }

  @Override
  /**
   * determina se o termo e constante
   */
  public Expression simplify()
  {
    Expression s = e.simplify();

    //caso seja igual a zero , retorna zero , caso contrario retorna o negativo da constante (evita a notacao "-0")
    if (s.isConstant())
    {
      float v = ((Constant)s).value;
      return 0 == v ? Constant.ZERO : new Constant(-v);
    }
    //caso de dupla negacao - (-a) = a
    return s instanceof Minus ? ((Minus)s).e : new Minus(s);
  }

} // Minus
