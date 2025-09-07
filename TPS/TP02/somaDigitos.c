#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int sumOfDigits (int numbersToSum) { // funcao para somar os numeros
  if (numbersToSum == 0) // condicao de parada
    return 0;
  return (numbersToSum % 10) + sumOfDigits(numbersToSum / 10);
}

void printResult (int numbersToSum) { // funcao para imprimir o resultado
  printf("%d\n", sumOfDigits(numbersToSum)); 
}

void read () { // funcao para ler o numero
  int numbersToSum;
  while (scanf("%d", &numbersToSum) == 1 && numbersToSum != -1) { // continua em loop enquanto for um numero e for inteiro
    printResult(numbersToSum); // chama a funcao para printar o resultado
  }
}

int main () {

  read (); // chama a funcao para ler o numero

  return 0;
}
