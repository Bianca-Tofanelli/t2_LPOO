Trabalho 2 
Bianca Andrade Tofanelli, 202519050206
Luis Henrique Cardoso, 202519050150
Paola Vendruscolo, 202519050133

Link do vídeo: https://youtu.be/3DF6ArVAnw8

Atividades concluídas: Todos os pedidos foram completamente concluídos (A1, A2, A3 e A4).

Mudanças em relação à gramática: inicialização obrigatória no define para evitar símbolos nulos (define <nome> = <expressao>).

Testes feitos:
teste_operacoes.txt: avaliação numérica, derivadas parciais em x, y e z, funções e potências.
teste_comandos.txt: define, atribuição "=", eval, list, clear e exit.
teste_erros.txt: tratamento de erros sintáticos, léxicos e de símbolos reservados.

Como compilar e executar:
Compilar:
  javac Main.java lpoo\sym\*.java lpoo\sym\parser\*.java lpoo\util\*.java
Executar em modo arquivo:
  java Main teste_operacoes.txt teste_comandos.txt teste_erros.txt
Executar em modo interativo:
  java Main