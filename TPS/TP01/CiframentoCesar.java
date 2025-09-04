import java.util.Scanner;

class CiframentoCesar {
  public static String Cifra (String word) { // metodo que faz o ciframento 
    String newWord = "";
    for (int i = 0; i < word.length(); i++) { // varre a string fazendo o ciframento
      if (word.charAt(i) > 122) { // compara até a posição 122 da tabela ASCII, se for maior que 122, mantem o caracter original
        newWord += (char)(word.charAt(i));
        continue;
      }
      newWord += (char)(word.charAt(i) + 3); // adiciona a nova string a palavra ja cifrada
    }
    return newWord;
  }

  public static void Read () { // metodo para ler a palavra cifrada
    Scanner in = new Scanner (System.in);
    String word = in.nextLine();
    while (!(word.length() >= 3 && word.charAt(0) == 'F' && word.charAt(1) == 'I' && word.charAt(2) == 'M')) { // para quando encontrar a palavra FIM
      System.out.println("" + Cifra(word));
      word = in.nextLine();
    }
    in.close();
  }

  public static void main (String[] args) {
    Read (); // chama o metodo para ler a palavra cifrada
  }
}

