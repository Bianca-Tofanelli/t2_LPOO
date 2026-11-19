package lpoo.sym;

/*
 * Autores: 
Bianca Andrade Tofanelli, 
Luis Henrique Cardoso, 
Paola Vendruscolo
 */


public final class Constant
  extends Expression
{
  //declara as constantes zero , um , pi e euler (e)
  public final static Constant ZERO = new Constant(0);
  public final static Constant ONE = new Constant(1);
  public final static Constant PI = new Constant((float)Math.PI);
  public final static Constant E = new Constant((float)Math.E);
 
  public final float value;

  public Constant(float value)
  {
    this.value = value;
  }

  @Override
  //retorna o valor da constante contida no eval
  public float eval(float x, float y, float z)
  {
    return value;
  }

  @Override
  //caso seja feito a derivada parcial de uma constante , sempre nulo
  public Expression diff(X v) //v representa o termo a ser trabalhado
  {
    return ZERO;
  }

  @Override
  //retorna o valor das constantes quando encontradas no texto digitado
  public String toString()
  {
    if (value == PI.value)
      return "pi";
    if (value == E.value)
      return "e";
    return Float.toString(value);
  }

} // Constant
