package lpoo.sym;
/*
 * Autores: 
Bianca Andrade Tofanelli, 
Luis Henrique Cardoso, 
Paola Vendruscolo
 */
public final class Sub
  extends BinaryExpression
{
  public Sub(Expression e1, Expression e2)
  {
    super(e1, e2, '-');
  }

  @Override
  public float eval(float x, float y, float z)
  {
    return e1.eval(x, y, z) - e2.eval(x, y, z);
  }

  @Override
  public Expression diff(X v)
  {
    return (new Sub(e1.diff(v), e2.diff(v))).simplify();
  }

  @Override
  public Expression simplify()
  {
    Expression s1 = e1.simplify();
    Expression s2 = e2.simplify();

    //casos notaveis de constantes
    if (s1.isConstant())
    
    {
      float v1 = ((Constant)s1).value;

      //se o subtraendo e constante retorna a subtracao regular
      if (s2.isConstant())
        return new Constant(v1 - ((Constant)s2).value);
      //se o minuendo e nulo retorna o negativo do subtraendo
      if (0 == v1)
        return (new Minus(s2)).simplify();
    }
    //se o subtraendo e nulo, retona o minuendo 
    else if (s2.isConstant() && 0 == ((Constant)s2).value)
      return s1;
    //caso nenhum caso seja satisfeito retorna o tratamento regular sem atalhos
    return new Sub(s1, s2);
  }

} // Sub
