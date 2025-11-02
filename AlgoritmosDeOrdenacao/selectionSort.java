import java.util.*;

class selectionSort {

  public class selectionSort (int[] array) { // metodo insertionSort

    for (int i = 0; i < array.length; i++) {

      int smallest = smallestNumber (array, i); // recebe o menor valor do array, a partir da posição do indice
      int current = array[i]; // recebe o valor da atual posição do indice no array

      array[i] = smallest; // a atual posição do indice, recebe o menor valor encontrado
      array[smallest] = current; // a posição do então menor valor encontrado, recebe o valor da posição de i

    }
      
  }

  public static int smallestNumber (array, i) { // metodo que encontra o menor numero do array a partir do indice

    int smallest = i; // a variavel contem a posição do indice

    for (int j = i; i < array.length; i++) {
      if (array[smallest] > array[j]) { // se a posição do indice for maior que a alguma posição de "J", a variavel smallest recebe o indice da posição com o menor valor
        smallest = j; 
      }
    }

    return smallest;

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
    selectionSort (array); // chama o metodo que faz o heapsort
    printArray (array); // chama o metodo que printa o array

    in.close();

  }

  public static void main (String[] args) {

    read (); // chama o metodo que lê

  }

}
