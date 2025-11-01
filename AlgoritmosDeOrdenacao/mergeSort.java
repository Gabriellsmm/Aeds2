import java.util.*;

class mergeSort {

  public static void mergeSort (int[] array, int left, int right) { // metodo mergesort que faz a partição até que tenha 1 numero por "quadrado"

    if (left < right) {

      int middle = (left + right) / 2; // define o meio da entao sublista

      mergeSort (array, left, middle); // divide a metade da esqueda
      mergeSort (array, middle + 1, right); // divide a metade da direita

      merge (array, left, middle, right); // chama o metodo que faz a junção e ordenação

    }

  }

  public static void merge (int[] array, int left, int middle, int right) { // metodo que faz a junção e ordenação

    int[] tmpLeft = new int [middle - left + 1]; // vetor temporario para guardar a metade da esquerda
    int[] tmpRight = new int [right - middle]; // vetor temporario para guardar a metade da direita

    for (int i = 0; i < tmpLeft.length; i++) {
      tmpLeft[i] = array[left + i]; // é preenchido com os numeros da esquerda ate o meio
    }

    for (int i = 0; i < tmpRight.length; i++) {
      tmpRight[i] = array[middle + 1 + i]; // é preenchido com os valores do meio mais um ate o final do array
    }

    // variaveis de controle
    int i = 0; 
    int j = 0;
    int k = left;

    while (i < tmpLeft.length && j < tmpRight.length) { // roda enquanto as variaveis de controle forem menores que o tamanho do array
      if (tmpLeft[i] <= tmpRight[j]) { // compara se a entao posição da esquerda é menor que a então posição da direita
        array[k] = tmpLeft[i]; // se for, coloca no array original a entao posição da esquerda
        i++;
      } else {
        array[k] = tmpRight[j]; // se não for, coloca a então posição da direita 
        j++;
      }
      k++;
    }
    

    // defesa utilizada em caso de um dos arrays chegar ao fim primeiro que o outro
    while (i < tmpLeft.length) {
      array[k] = tmpLeft[i];
      i++;
      k++;
    }

    while (j < tmpRight.length) {
      array[k] = tmpRight[j];
      j++;
      k++;
    }

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
    mergeSort (array, 0, array.length - 1); // chama o metodo que faz o heapsort
    printArray (array); // chama o metodo que printa o array

    in.close();

  }

  public static void main (String[] args) {

    read (); // chama o metodo que lê

  }

}
