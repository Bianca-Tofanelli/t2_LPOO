package lpoo.sym;
/*
 * Autores: 
Bianca Andrade Tofanelli, 
Luis Henrique Cardoso, 
Paola Vendruscolo
 */
public class Function
  extends Expression
/**
 * Declara as funcoes de seno,cos e ln (log)
 */
{
  public static final Function sin(Expression arg)
  {
    return new Function(NativeFunction.SIN, arg);
  }

  public static final Function cos(Expression arg)
  {
    return new Function(NativeFunction.COS, arg);
  }

  public static final Function log(Expression arg)
  {
    return new Function(NativeFunction.LOG, arg);
  }

  @Override
  public float eval(float x, float y, float z)
  {
    return function.eval(arg.eval(x, y, z),0,0);
  }

  
  @Override
  public Expression diff(X v)
  {
    // regra da cadeia: f(g)' = f'(g) * g'
    return (new Mul(function.derivative(arg), arg.diff(v))).simplify();
  }

  @Override
  public String toString()
  {
    return String.format("%s(%s)", function, arg);
  }

  private final NativeFunction function;
  private final Expression arg;

  private Function(NativeFunction function, Expression arg)
  {
    this.function = function;
    this.arg = arg;
  }

} // Function

/**
 * Declara as classes os quais contemplam as funcoes sen , cos e ln
 */
abstract class NativeFunction
  extends Expression 
{
  static final NativeFunction SIN = new Sin();
  static final NativeFunction COS = new Cos();
  static final NativeFunction LOG = new Log();

  /**
   * Retorna f'(arg), isto é, a derivada da função nativa aplicada a arg.
   */
  abstract Expression derivative(Expression arg);

  /**
   * Derivada da função em relação ao seu único argumento (variável x).
   */
  @Override
  public Expression diff(X v) //ignora o v ,visto que o termo trabalhado sempre se encontra em x
  {
    return derivative(X.x);
  }

} // NativeFunction

final class Sin
  extends NativeFunction
{
  @Override
  public float eval(float x, float y, float z)
  {
    return (float)Math.sin(x);
  }

  @Override
  Expression derivative(Expression arg)
  {
    return Function.cos(arg); //retorna o cosseno do termo , o qual e a derivada do sin
  }

  @Override
  public String toString()
  {
    return "sin";
  }

} // Sin

final class Cos
  extends NativeFunction
{
  @Override
  public float eval(float x, float y, float z)
  {
    return (float)Math.cos(x);
  }

  @Override
  Expression derivative(Expression arg)
  {
    return new Minus(Function.sin(arg)); //Retorna o negativo de seno , o qual e a derivada do cos
  }

  @Override
  public String toString()
  {
    return "cos";
  }

} // Cos

final class Log
  extends NativeFunction
{
  @Override
  public float eval(float x, float y, float z)
  {
    return (float)Math.log(x); // log é tratato pelo java como ln , por isso esta como log apenas
  }

  @Override
  Expression derivative(Expression arg)
  {
    return new Div(Constant.ONE, arg);
  }

  @Override
  public String toString()
  {
    return "log";
  }

} // Log
