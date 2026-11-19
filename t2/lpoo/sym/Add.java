package lpoo.sym;

/*
 * Autores: 
Bianca Andrade Tofanelli, 
Luis Henrique Cardoso, 
Paola Vendruscolo
 */
public final class Add
  extends BinaryExpression
{
  public Add(Expression e1, Expression e2)
  {
    super(e1, e2, '+');
  }

  @Override
  public float eval(float x, float y, float z)
  {
    return e1.eval(x, y, z) + e2.eval(x, y, z);
  }

  @Override
  public Expression diff(X v) //v representa o termo a ser trabalhado , demais termos constantes
  {
    return (new Add(e1.diff(v), e2.diff(v))).simplify();
  }

  //metodo que cria um atalho em alguns casos de soma , retornando o valor s2 caso s1 seja nulo e vice versa
  @Override
  public Expression simplify()
  {
    //simplifica os dois termos da soma
    Expression s1 = e1.simplify();
    Expression s2 = e2.simplify();

    //verifica se cada um dos termos e nulo , retornando o outro em caso positivo
    if (s1.isConstant())
    {
      float v1 = ((Constant)s1).value;

      if (0 == v1)
        return s2;
      if (s2.isConstant())
        return new Constant(v1 + ((Constant)s2).value);
    }
    else if (s2.isConstant() && 0 == ((Constant)s2).value)
      return s1;
      //caso nenhum dos termos seja nulo retorna a soma normal
    return new Add(s1, s2);
  }

} // Add
