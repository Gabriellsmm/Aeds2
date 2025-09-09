import java.util.*;

class somaDigitosRecursivo {

  public static int sumOfDigits (int numbersToSum) { // metodo que faz a soma 
    if (numbersToSum == 0) // condição de parada do metodo recursivo
      return 0;
    return (numbersToSum % 10) + sumOfDigits (numbersToSum / 10); // chamada recursiva que faz a soma dos numeros
  }

  public static void printResult (int numbersToSum) { // metodo que printa o resultado da soma dos numeros
    System.out.println("" + sumOfDigits(numbersToSum)); // printa o resultado e chama o metodo que faz a soma
  }

  public static void readNumbers () { // metodo que lê o inteiro
    Scanner in = new Scanner(System.in);
    String word = in.nextLine();
    if (word.equals("FIM")) // se o conteudo digitado for FIM, para o codigo
      return;
    int numbersToSum = Integer.parseInt(word); // transforma o conteudo da string para inteiro
    while (true) { 
      printResult(numbersToSum); // chama o metodo para printar o resultado da soma
      word = in.nextLine();
      if (word.equals("FIM")) // se o conteudo digitado for FIM, para o loop
        break;
      numbersToSum = Integer.parseInt(word); // transforma a string em inteiro
    }
    in.close();
  }

  public static void main (String[] args) {
    readNumbers(); // chama o metodo que lê o valor a ser somado
  }

}
