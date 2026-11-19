package lpoo.sym;
/*
 * Autores: 
Bianca Andrade Tofanelli, 
Luis Henrique Cardoso, 
Paola Vendruscolo
 */
public final class X
  extends Expression
{
  public static final X x = new X("x", 0);
  public static final X y = new X("y", 1);
  public static final X z = new X("z", 2);

  /**
   * Retorna o singleton da variável de nome name (x, y ou z), ou null.
   */

  public static X byName(String name) //metodo utilizado para receber um termo unico de um polinomio , X.x retorna os termos de x
  {
    switch (name)  //substitui o modelo if,else , retornando uma resposta com base no que recebe
    {
      case "x": //funciona como se fosse um if (name == x) ; retorna o caso "x"
        return x;
      case "y":
        return y;
      case "z":
        return z;
      default: //representa um caso que foge do escopo trabalhado , um caso name == k por exemplo
        return null;
    }
  }

  @Override
  public float eval(float x, float y, float z)
  {
    switch (index)
    {
      case 0:
        return x;
      case 1:
        return y;
      default:
        return z;
    }
  }

  @Override
  //se o termo a ser derivado pertence ao termo sendo derivado (EX : (y)`dx , y == x?)
  public Expression diff(X v)
  {
    return this == v ? Constant.ONE : Constant.ZERO;
  }

  @Override
  public String toString()
  {
    return name;
  }

  private final String name;
  private final int index;

  private X(String name, int index)
  {
    this.name = name;
    this.index = index;
  }

} // X
