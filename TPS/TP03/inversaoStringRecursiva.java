import java.util.*;

class inversaoStringRecursiva {
  
  public static String reverseString (String word, int lengthWord, String reverseWord) { // metodo que faz a inversao
    if (lengthWord < 0) // condição de parada, < 0 para implementar também a posição 0
      return reverseWord;
    reverseWord += word.charAt(lengthWord);
    return reverseString (word, lengthWord - 1, reverseWord); // chamada da função de forma recursiva
  }

  public static void read () { // metodo que le a string
    Scanner in = new Scanner(System.in);
    String word = in.nextLine();
    String reverseWord = "";
    while (!word.equals("FIM")) { // continua o loop ate encontrar a palavra FIM
      int lengthWord = word.length() - 1;
      System.out.println("" + reverseString (word, lengthWord, reverseWord)); // imprime a palavra invertida chamando o metodo que faz a inversao
      word = in.nextLine();
    }
    in.close();
  }

  public static void main (String[] args) {
    read (); // chama o metodo para ler a string
  }

}
