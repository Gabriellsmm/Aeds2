import java.util.*;

class bubbleSort {
  
  public static void bubbleSort (int[] array) { // metodo bubblesort que faz a ordenação 
  
    for (int i = 0; i < array.length - 1; i++) { // garante que percorra todo o vetor
      for (int j = 0; j < array.length - i - 1; j++) { // faz as comparações e as trocas, se necessario
        if (array[j] > array[j + 1]) { // se a proxima posição for menor que a anterior, faz a troca
          swap (array, j, j + 1);
        }
      }
    }  

  }

  public static void swap (int[] array, int i, int j) { // metodo que faz as trocas

    int tmp = array[i];
    array[i] = array[j];
    array[j] = tmp;

  }

  public static void fillArray (int[] array) { // metodo que preenche o array

    Scanner in = new Scanner (System.in);

    System.out.println("Prenncha o array: ");
    for (int i = 0; i < array.length; i++) {
      System.out.print("Preencha a posição " + (i + 1) + " do seu array: ");
      array[i] = in.nextInt();
    }

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
    bubbleSort (array); // chama o metodo que faz o heapsort
    printArray (array); // chama o metodo que printa o array

    in.close();

  }

  public static void main (String[] args) {

    read (); // chama o metodo que lê

  }

}
