package lpoo.sym;

public final class Pow
  extends BinaryExpression
{
  public Pow(Expression base, Expression exp)
  {
    super(base, exp, '^');
  }

  public Pow(Expression base, float exp)
  {
    this(base, new Constant(exp));
  }

  @Override
  public float eval(float x, float y, float z)
  {
    return (float)Math.pow(e1.eval(x, y, z), e2.eval(x, y, z));
  }

  @Override
  public Expression diff(X v)
  {
    if (e2.isConstant())
    {
      float exp = ((Constant)e2).value;

      if (0 == exp)
        return Constant.ZERO;

      Expression bd = e1.diff(v);

      if (1 == exp)
        return bd;
      return (new Mul(new Mul(e2, new Pow(e1, exp - 1)), bd)).simplify();
    }
    //trecho da derivada de potencias nao constantes
    Expression t1 = new Mul(e2.diff(v), Function.log(e1));
    Expression t2 = new Div(new Mul(e2, e1.diff(v)), e1);

    return (new Mul(this, new Add(t1, t2))).simplify();
  }

  @Override
  public Expression simplify()
  {
    //trecho que simplifica tanto o termo de cima quanto de baixo ( remove termos dispensaveis como 1 * X = X)
    Expression bs = e1.simplify();
    Expression es = e2.simplify();

    if (es.isConstant())
    {
      float exp = ((Constant)es).value;

      if (0 == exp)
        return Constant.ONE;
      if (1 == exp)
        return bs;
      if (bs.isConstant())
        return new Constant((float)Math.pow(((Constant)bs).value, exp));
    }
    return new Pow(bs, es);
  }

} // Pow
