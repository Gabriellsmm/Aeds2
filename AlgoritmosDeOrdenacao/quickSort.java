import java.util.*;

class quickSort {

  public static void quickSort (int[] array, int low, int high) { // metodo do quicksort recursivo

    if (low < high) { // condição de parada
 
      int pivo = partition (array, low, high); // definição do pivo

      // chamadas recursivas da direita e da esquerda
      quickSort (array, low, pivo - 1);
      quickSort (array, pivo + 1, high);

    }

  }

  public static int partition (int[] array, int low, int high) { // metodo de partição, que retorna o novo pivo e ordena

    int pivo = array[high];
    int i = low - 1;

    for (int j = low; j < high; j++) {
      if (array[j] <= pivo) { 
        i++;
        swap (array, i, j);
      } 
    }

    swap (array, i + 1, high);
    return i + 1; // retorna o indice a frente do pivo

  }

  public static void swap (int[] array, int i, int j) { // metodo que faz a troca
    
    int aux = array[i];
    array[i] = array[j];
    array[j] = aux;

  }

  public static void fillArray (int[] array) { // metodo para preencher o vetor
      
    Scanner in = new Scanner (System.in);
    for (int i = 0; i < array.length; i++) {
      System.out.print("Preencha a posição " + (i + 1) + " do array: ");
      array[i] = in.nextInt();
    }

    in.close();

  }

  public static void printArray (int[] array) { // metodo que printa o vetor ordenado

    for (int i = 0; i < array.length; i++) {
      System.out.print("" + array[i] + " ");
    }

    System.out.println("");

  }

  public static void callMetods (int[] array) { // chama os metodos 
    
    fillArray (array); // metodo que preenche o array
    quickSort (array, 0, array.length - 1); // metodo de ordenação
    printArray (array); // printa o array ordenado

  }

  public static void read () { // metodo que lê o tamanho do array

    int arrayLength;
    Scanner in = new Scanner (System.in);
    
    System.out.print("Digite o tamanho do array: ");
    arrayLength = in.nextInt();

    int[] array = new int[arrayLength];

    callMetods (array); // chama o  metodo que chama os outros metodos

    in.close();

  }

  public static void main (String[] args) {
    
    read (); // chama o metodo que lê o tamanho do array

  }

}
