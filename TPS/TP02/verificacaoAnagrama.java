import java.util.Scanner;

class verificacaoAnagrama {
  
  public static boolean checker (String firstWord, String secondWord) { // metodo para verificar se as strings sao um anagrama
    int letterCounter = 0;
    boolean wordChecker = true;
    String newFirstWord = firstWord.toLowerCase(); // transforma a string toda em letras minusculas
    String secondWordLower = secondWord.toLowerCase(); //transforma a string toda em letras minusculas 
    char[] newSecondWord = secondWordLower.toCharArray(); // cria um vetor de caracteres com as letras minusculas da segunda string
    if (firstWord.length() != secondWord.length()) // compara o tamanho das strings, se forem diferentes, ja retorna falso
      return wordChecker = false;
    for (int i = 0; i < firstWord.length(); i++) {
      for (int j = 0; j < secondWord.length(); j++) {
        if (newFirstWord.charAt(i) == newSecondWord[j]) { // faz a comparacao letra por letra, de uma letra da primeira string, com todas da segunda string
          letterCounter++; // se encontrar uma letra igual, aumenta no contador
          newSecondWord[j] = 0; // a letra que foi encontrada, é substituada por 0, para nao entrar em mais comparacoes
          j = secondWord.length(); // condicao de saida do for, quadno encontrar uma letra igual 
        }
      }
      
    }
    if (letterCounter != firstWord.length()) // se contador de letras for diferente do tamanho da string, retorna falso
      return wordChecker = false;
    return wordChecker;
  }

  public static void printResult (String firstWord, String secondWord) { // metodo para printar o resultado do anagrama
    if (checker(firstWord, secondWord)) { // chama o metodo para testar se é ou nao, e em seguida printa
      System.out.println("SIM");
    } else {
      MyIO.println("NÃO");
    }
  }

  public static void read () { // metodo para ler as strings
    Scanner in = new Scanner(System.in);
    while (in.hasNext()) { // continua o loop até encontrar o break dentro do codigo
      String firstWord = in.next();
      if (firstWord.contains("FIM")) break; // continua o loop ate encontrar a palavra "FIM"
      if (!in.hasNext()) break;
      String middleWord = in.next();
      String secondWord;
      if (middleWord.equals("-")) {
        if (!in.hasNext()) break;
        secondWord = in.next(); // precisar usar o .next ao inves do .nextLine, por motivos de erro de compilacao no verde
      } else {
        secondWord = middleWord;
      }
      printResult (firstWord, secondWord); // chama a funcao para printar o resultado
    }
    in.close();
  }

  public static void main (String[] args) {
    read(); // chama o metodo para ler as strings
  }

}
