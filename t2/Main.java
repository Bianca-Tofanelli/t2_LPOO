import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import lpoo.sym.*;
import lpoo.util.*;

public class Main
{
  public static void main(String[] args)
  {
    if (args.length == 0)
    {
      for (;;)
      {
        String line = Console.readString("> ");
        if (line == null)
          return;
        if (!line.equals("") && !Calc.execute(line))
          return;
      }
    }
    else
    {
      for (int i = 0; i < args.length; i++)
      {
        try
        {
          File arquivo = new File(args[i]);
          Scanner leitor = new Scanner(arquivo);

          while (leitor.hasNextLine())
          {
            String line = leitor.nextLine();

            if (!line.equals(""))
            {
              Console.println("> " + line);

              if (!Calc.execute(line))
                break;
            }
          }

          leitor.close();
        }
        catch (FileNotFoundException e)
        {
          Console.println("Arquivo nao encontrado: " + args[i]);
        }
      }
    }
  }

} // Main