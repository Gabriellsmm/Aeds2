import java.util.Scanner;

class inversaoString {

  public static String inversion (String word) { // metodo que faz a inversao da string
    String newWord = "";
    for (int i = word.length() - 1; i >= 0; i--) { // i tem o valor da ultima posicao da string, fazendo assim a leitura ao contrario
      newWord += word.charAt(i); // uma nova string recebe a string original 
    }
    return newWord;
  }

  public static void read () { // metodo para leitura da palavra
    Scanner in = new Scanner(System.in);
    String word = in.nextLine();
    while (!word.equals("FIM")) { // o loop se encerra quando encontrar a palavra "FIM"
      System.out.println("" + inversion(word));
      word = in.nextLine();
    }
    in.close();
  }

  public static void main (String[] args) {
    read(); // chama o metodo para ler as palavras
  }

}
