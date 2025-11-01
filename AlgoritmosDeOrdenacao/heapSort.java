import java.util.*;

class heapSort {

  public static void heapSort (int[] array) { // metodo heapsort

    int arrayLength = array.length; 

    for (int i = arrayLength / 2 - 1; i >= 0; i--) { // primeiro for que transforma o vetor em arvore
      applyHeap (array, arrayLength, i); // aplica a "arvore" no vetor
    }

    for (int i = arrayLength - 1; i > 0; i--) { // segundo for que ordena a arvore
      swap (array, i, 0); // metodo que faz a troca
      applyHeap (array, i, 0); // chama o metodo que aplica o heapsort para ordenalo de vez
    }

  }

  public static void applyHeap (int[] array, int arrayLength, int i) { // metodo que aplica o heapsort
  
    int root = i; // define a raiz
    int left = 2 * i + 1; // define a folha a esquerda da raiz
    int rigth = 2 * i + 2; // define a folha a direita da raiz

    if (left < arrayLength && array[left] > array[root]) { // verifica se a folha da esquerda é maior que a raiz
      root = left;
    }

    if (rigth < arrayLength && array[rigth] > array[root]) { // verifica se a folha da direita é maior que a raiz
      root = rigth;
    }

    if (root != i) { // se a raiz for diferente de "i", seu valor original, chama recursivamente o metodo que aplica o heapsort
      swap (array, root, i); // faz a troca das folhas e raizes
      applyHeap (array, arrayLength, root); // chama recursivamente o metodo
    }

  }

  public static void swap (int[] array, int root, int i) { // metodo que faz a troca
    
    int tmp = array[i];
    array[i] = array[root];
    array[root] = tmp;

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
    
    for (int i = 0; i < array.length; i++) {
      System.out.print(array[i] + " ");
    }

  }

  public static void read () { // metodo que faz a leitura do tamanho do array

    Scanner in = new Scanner (System.in);
    int length;

    System.out.print("Digite o tamanho do seu array: ");
    length = in.nextInt();
    int[] array = new int[length];

    fillArray (array); // chama o metodo que preenche o array
    heapSort (array); // chama o metodo que faz o heapsort
    printArray (array); // chama o metodo que printa o array

    in.close();

  }

  public static void main (String[] args) {

    read (); // chama o metodo que lê

  }

}
