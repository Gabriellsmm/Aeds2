import java.util.*;

class quickSort {

  static Random randomGenerator = new Random(); // define o gerador aleatório

  public static void swap(int[] array, int i, int j) { // metodo de troca
    int temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }

  public static int[] generateRandomArray(int size) { // metodo para preencher o array
    int[] array = new int[size];
    for (int i = 0; i < size; i++) {
      array[i] = randomGenerator.nextInt(size * 10); // preenche o array com valores baseados no tamanho do array
    }
    return array;
  }

  public static long measureExecution(Runnable sortFunction) { // metodo para calcular o tempo de execução
    long start = System.nanoTime();
    sortFunction.run();
    return System.nanoTime() - start;
  }

  public static void quickSortFirstPivot(int[] array, int start, int end) { // metodo com o pivo na primeira posição
    if (start < end) {
      int pivotIndex = partitionFirstPivot(array, start, end);
      quickSortFirstPivot(array, start, pivotIndex - 1); // recursão para o lado esquerdo
      quickSortFirstPivot(array, pivotIndex + 1, end); // recursão para o lado direito
    }
  }

  public static int partitionFirstPivot(int[] array, int start, int end) { // método que organiza usando o primeiro pivô
    int pivot = array[start];
    int left = start + 1;
    int right = end;

    while (left <= right) {
      while (left <= right && array[left] <= pivot) left++;
      while (left <= right && array[right] > pivot) right--;
      if (left < right) swap(array, left, right);
    }

    swap(array, start, right);
    return right;
  }

  public static void quickSortLastPivot(int[] array, int start, int end) { // metodo com o pivo na ultima posição
    if (start < end) {
      int pivotIndex = partitionLastPivot(array, start, end);
      quickSortLastPivot(array, start, pivotIndex - 1);
      quickSortLastPivot(array, pivotIndex + 1, end);
    }
  }

  public static int partitionLastPivot(int[] array, int start, int end) { // organiza utilizando o pivo na ultima posição
    int pivot = array[end];
    int i = start - 1;

    for (int j = start; j < end; j++) {
      if (array[j] <= pivot) {
        i++;
        swap(array, i, j);
      }
    }

    swap(array, i + 1, end);
    return i + 1;
  }

  public static void quickSortRandomPivot(int[] array, int start, int end) { // quicksort com pivo aleatorio
    if (start < end) {
      int pivotIndex = partitionRandomPivot(array, start, end);
      quickSortRandomPivot(array, start, pivotIndex - 1);
      quickSortRandomPivot(array, pivotIndex + 1, end);
    }
  }

  public static int partitionRandomPivot(int[] array, int start, int end) { // escolhe o pivo aleatorio e organiza
    int randomIndex = start + randomGenerator.nextInt(end - start + 1);
    swap(array, randomIndex, end);
    return partitionLastPivot(array, start, end);
  }

  public static void quickSortMedianOfThree(int[] array, int start, int end) { // quicksort com a mediana de tres
    if (start < end) {
      int pivotIndex = partitionMedianOfThree(array, start, end);
      quickSortMedianOfThree(array, start, pivotIndex - 1);
      quickSortMedianOfThree(array, pivotIndex + 1, end);
    }
  }

  public static int partitionMedianOfThree(int[] array, int start, int end) { // organiza o array escolhendo a mediana de tres
    int mid = (start + end) / 2;
    int medianIndex = medianOfThree(array, start, mid, end);
    swap(array, medianIndex, end);
    return partitionLastPivot(array, start, end);
  }

  public static int medianOfThree(int[] array, int start, int b, int c) { // método que organiza usando o primeiro pivô
    int pivot = array[start];
    if ((array[start] > array[b]) != (array[start] > array[c])) 
      return start;
    else if ((array[b] > array[start]) != (array[b] > array[c])) 
      return b;
    else 
      return c;
  }

  public static void printFirstPivot(int[] baseArray) { // metodo que printa o resultado do tempo de execução com o pivo na primeira posição
    int[] arrayCopy = Arrays.copyOf(baseArray, baseArray.length);
    long time = measureExecution(() -> quickSortFirstPivot(arrayCopy, 0, arrayCopy.length - 1));
    System.out.println("First pivot: " + time / 1_000_000_000.0 + " seconds");
  }

  public static void printLastPivot(int[] baseArray) { // metodo que printa o resultado do tempo de execução com o pivo na ultima posição
    int[] arrayCopy = Arrays.copyOf(baseArray, baseArray.length);
    long time = measureExecution(() -> quickSortLastPivot(arrayCopy, 0, arrayCopy.length - 1));
    System.out.println("Last pivot: " + time / 1_000_000_000.0 + " seconds");
  }

  public static void printRandomPivot(int[] baseArray) { // metodo que printa o resultado do tempo de execução com o pivo escolhido aleatoriamente
    int[] arrayCopy = Arrays.copyOf(baseArray, baseArray.length);
    long time = measureExecution(() -> quickSortRandomPivot(arrayCopy, 0, arrayCopy.length - 1));
    System.out.println("Random pivot: " + time / 1_000_000_000.0 + " seconds");
  }

  public static void printMedianOfThreePivot(int[] baseArray) { // metodo que printa o resultado do tempo de execução com o pivo escolhido a partir da mediana de tres
    int[] arrayCopy = Arrays.copyOf(baseArray, baseArray.length);
    long time = measureExecution(() -> quickSortMedianOfThree(arrayCopy, 0, arrayCopy.length - 1));
    System.out.println("Median of three: " + time / 1_000_000_000.0 + " seconds");
  }

  public static void printAll (int size) { // metodo que chama todos os metodos que printam
    int[] baseArray = generateRandomArray(size);
    System.out.println("Array size: " + size);
    printFirstPivot(baseArray);
    printLastPivot(baseArray);
    printRandomPivot(baseArray);
    printMedianOfThreePivot(baseArray);
  }

  public static void read() { // metodo que define o tamanho do array
    int size = 10000000; // mudar o valor manualmente
    printAll(size);
  }

  public static void main(String[] args) {
    read(); // chama o metodo que define o tamanho do array
  }

}

