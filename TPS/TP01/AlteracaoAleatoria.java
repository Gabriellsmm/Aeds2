import java.util.Random;
import java.util.Scanner;

class AlteracaoAleatoria {

  public static String Swap (String word, char of, char to) { // metodo para fazer a troca das letras
    String newWord = "";
    for (int i = 0; i < word.length(); i++) { // usado para ler toda a string
      if (word.charAt(i) == of) { // faz a comparação com a primeira letra sorteada
        newWord += to; // se for verdadeiro, faz a troca das letras
        continue;
      } 
        newWord += word.charAt(i); // caso for falso, implementa a letra normalmente
    }
    return newWord;
  }

  public static void Read () { // metodo usado para fazer a leitura da palavra
    Scanner in = new Scanner(System.in);
    Random generator = new Random ();
    generator.setSeed(4); 
    String word = in.nextLine();
    while (!(word.length() >= 3 && word.charAt(0) == 'F' && word.charAt(1) == 'I' && word.charAt(2) == 'M')) { // continua no loop ate encontrar a palavra FIM
      char of = (char)('a' + (Math.abs(generator.nextInt()) % 26)); // define a primeira letra aleatoria, que sera trocada
      char to = (char)('a' + (Math.abs(generator.nextInt()) % 26)); // define a segunda letra aleatoria, para trocar com a primeira letra 
      System.out.println("" + Swap(word, of, to));
      word = in.nextLine();
    }
  }

  public static void main (String[] args) {
    Read (); // chama o metodo para fazer a leitura da palavra
  }
}
