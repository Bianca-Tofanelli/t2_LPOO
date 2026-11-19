package lpoo.sym;
/*
 * Autores: 
Bianca Andrade Tofanelli, 
Luis Henrique Cardoso, 
Paola Vendruscolo
 */
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

  /**
   * Declara o metodo simplify , responsavel por remover termos despreziveis ao termo (EX : 1*X = X)
   */
  public Expression simplify()
  {
    return this;
  }

  /**
  * Retorna se o termo e constante
  */
  public final boolean isConstant()
  {
    return this instanceof Constant;
  }

  /**
   * Retorna se o termo e binario
   */
  public final boolean isBinary()
  {
    return this instanceof BinaryExpression;
  }

} // Expression
