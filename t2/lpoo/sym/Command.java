package lpoo.sym;

import java.util.LinkedHashMap;
import java.util.Map;
import lpoo.util.*;

/*
 * Autores: 
Bianca Andrade Tofanelli, 
Luis Henrique Cardoso, 
Paola Vendruscolo
 */
public abstract class Command
{
  public void setArguments(Object... args)
  {
    arguments = args;
  }
  
  public final boolean execute()
  {
    boolean returnValue = run();
    
    arguments = null;
    return returnValue;
  }
  
  protected abstract boolean run();
  
  protected Object[] arguments;

  public static final Command eval = new Command()
  {
    @Override
    protected boolean run()
    {
      /*  Extrai a Expression e as coordenadas x, y e z do vetor
      arguments e imprime o resultado no console */
      Expression expr = (Expression)arguments[0];
      float x = (Float)arguments[1];
      float y = (Float)arguments[2];
      float z = (Float)arguments[3];

      Console.println(expr.eval(x,y,z));
      //
      return true;
    }
  };

  public static final Command diff = new Command()
  {
    @Override
    protected boolean run()
    {
       /* Extrai a Expression e a variável de derivação
      e calcula a derivada simbólica simplificada, imprime no console*/
      Expression expr = (Expression)arguments[0];
      X v = (X)arguments[1];

      Console.println(expr.diff(v).simplify());
      //
      return true;
    }
  };

  public static final Command exit = new Command()
  {
    @Override
    protected boolean run()
    {
      return false;
    }
  };

  // Novos comandos: define, clear e list

  // Recebe um nome e uma Expression, grava no symbols e imprime que definiu no console
  public static final Command define = new Command() 
  {
    protected boolean run()
    {
      String name = (String)arguments[0];
      Expression expr = (Expression)arguments[1];

      symbols.put(name, expr);
      Console.println(name + " = " + expr);

      return true;
    }
  };
  // Limpa as definições criadas e imprime avisando
  public static final Command clear = new Command()
  {
   @Override
  protected boolean run()
  {
    if (arguments != null && arguments.length > 0 && arguments[0] != null)
    {
      String name = (String)arguments[0];
      if (symbols.remove(name) != null)
        Console.println("Removed '" + name + "'");
      else
        Console.println("Undefined identifier '" + name + "'");
    }
    else
    {
      symbols.clear();
      Console.println("Environment cleared");
    }
    return true;
  }
};
// Imprime todas as variáveis definidas, se não tiver nenhuma, imprime "No definitions"
  public static final Command list = new Command()
  {
    protected boolean run()
    {
      if (symbols.isEmpty())
        Console.println("No definitions");
      else
        for (Map.Entry<String, Expression> entry : symbols.entrySet())
          Console.println(entry.getKey() + " = " + entry.getValue());

      return true;
    }
  };
    /* Tabela de símbolos, armazena as expressões na ordem em que foram criadas 
  para que o parser possa consultar essas variáveis ao ler novas fórmulas*/ 
  public static boolean hasSymbol(String name)
  {
    return symbols.containsKey(name);
  }

  public static Expression getSymbol(String name)
  {
    return symbols.get(name);
  }

  private static final Map<String, Expression> symbols = new LinkedHashMap<>();
  //
  
} // Command
