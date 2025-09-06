import java.util.Scanner;

class Palindromo {
  public static boolean Verificar (String word) { //metodo para testar se é um palindromo
    int i = 0;
    boolean checker = true;
    for (int tam = word.length() - 1; tam >= 0; tam--) { // compara a string em suas posições inversas
      if (word.charAt(i) != word.charAt(tam)) {
        return checker = false; // se em algum momento da string, as letras ou simbolos não coincidirem, já retorna falso
      } 
        i++;
    }
    return checker;
  }

  public static void main (String[] args) {
    Scanner in = new Scanner (System.in);
    while (true) {
      String word = in.nextLine(); 
      if (word.length() == 3 && word.charAt(0) == 'F' && word.charAt(1) == 'I' && word.charAt(2) == 'M') { // o loop se encerra quando encontra a palavra FIM
        break;
      }
      boolean checker = Verificar(word); // chama o metodo para imprimir a resposta 
      if (checker) {
        System.out.println("SIM");
      } else {
        System.out.println("NAO");
      }
    }
  }
}
