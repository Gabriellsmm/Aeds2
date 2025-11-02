import java.util.*;

class insertionSort {

  public static void insertionSort (int[] array) { // metodo de ordenação do insertionSort

    for (int i = 1; i < array.length; i++) { // i começa em 1 porque em tese, a posição 0 já está ordenada

      int tmp = array[i]; // armazena o valor que vai ser comparado
      int j = i - 1; // j é o valor anterior

      while (j >= 0 && array[j] > tmp) { // enquanto ouverem posições a frente de j e a anterior for maior que a atual
        array[j + 1] = array[j]; // faz a troca
        j--;
      }

      array[j + 1] = tmp; // quando j vale -1, ou o numero anterior não é maior que o proximo, finaliza a troca colocando o valor de tmp em seu lugar

    }

  }

  public static void fillArray (int[] array) { // metodo que preenche o array

    Scanner in = new Scanner (System.in);

    System.out.println("Prenncha o array: ");
    for (int i = 0; i < array.length; i++) {
      System.out.print("Preencha a posição " + (i + 1) + " do seu array: ");
      array[i] = in.nextInt();
    }

    in.close();

  }

  public static void printArray (int[] array) { // metodo que printa o array
    
    System.out.print("Array Ordenado: ");

    for (int i = 0; i < array.length; i++) {
      System.out.print(array[i] + " ");
    }
    
    System.out.println("");

  }

  public static void read () { // metodo que faz a leitura do tamanho do array

    Scanner in = new Scanner (System.in);
    int length;

    System.out.print("Digite o tamanho do seu array: ");
    length = in.nextInt();
    int[] array = new int[length];

    fillArray (array); // chama o metodo que preenche o array
    insertionSort (array); // chama o metodo que faz o heapsort
    printArray (array); // chama o metodo que printa o array

    in.close();

  }

  public static void main (String[] args) {

    read (); // chama o metodo que lê

  }

}
