#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>

bool vowelChecker (char word[], int i) { // funcao para verificar se a string possui somente vogais
  if (i < 0) return true;
  if (word[i] == 'a' || word[i] == 'A' || word[i] == 'e' || word[i] == 'E' || word[i] == 'i' || word[i] == 'I' || word[i] == 'o' || word[i] == 'O' || word[i] == 'u' || word[i] == 'U') { // compara a posicao da string com as vogais
    return vowelChecker (word, i - 1);
  } else {
    return false; // se alguma letra nao for uma vogal, ja retorna falso 
  }
}

bool consoantChecker (char word[], int i) { // funcao para verificar se a string possui somente consoantes
  if (i < 0) return true;
  if (word[i] != 'a' && word[i] != 'A' && word[i] != 'e' && word[i] != 'E' && word[i] != 'i' && word[i] != 'I' && word[i] != 'o' && word[i] != 'O' && word[i] != 'u' && word[i] != 'U' // compara se a posicao da string nao possui vogais, nem numeros, nem sinais, e pontos
  && word[i] != '1' && word[i] != '2' && word[i] != '3' && word[i] != '4' && word[i] != '5' && word[i] != '6' && word[i] != '7' && word[i] != '8' && word[i] != '9' && word[i] != '0' && word[i] != '.' && word[i] != '-' && word[i] != '+') { 
    return consoantChecker (word, i - 1);  
  } else {
    return false; // se a string possuir alguma vogal, numero, sinais ou pontos, ja retorna falso
  }
}

bool wholeChecker (char word[], int i) { // funcao para verificar se a string so possui somente numeros inteiros
  if (i < 0) return true;
  if (word[i] == '1' || word[i] == '2' || word[i] == '3' || word[i] == '4' || word[i] == '5' || word[i] == '6' || word[i] == '7' || word[i] == '8' || word[i] == '9' || word[i] == '0') { // compara se a posicao da string possui apenas numeros
    return wholeChecker (word, i - 1);
  } else {
    return false; // se a string possuir alguma letra, ou sinais, ja retorna falso
  }
}

bool realsChecker (char word[], int i, int pointCounter) { // funcao para verificar se a string possui somente numeros reais
  if (i < 0 && pointCounter <= 1) return true;
  if ((word[i] >= '0' && word[i] <= '9') || (word[i] == '.' || word[i] == ',')) { //compara o caracter, se é um numero, um ponto ou uma virgula
    if (word[i] == '.' || word[i] == ',') { // se for um ponto ou uma virgula, adiciona um ao contador
      pointCounter++; 
    }
    return realsChecker (word, i - 1, pointCounter); // chamada da função de forma recursiva, percorrendo assim toda a string
  } else {
  return false; // se nao for um numero, um ponto ou uma virgula. já retorna falso
  }
}

void readVowelChecker (char word[], int lengthWord) { // função que printa se são ou não apenas vogais
  if (vowelChecker(word, lengthWord)) {
    printf("SIM ");
  } else {
    printf("NAO ");
  }
}

void readConsoantChecker (char word[], int lengthWord) { // função que printa se são ou não apenas consoantes 
  if (consoantChecker(word, lengthWord)) {
    printf("SIM ");
  } else {
    printf("NAO ");
  }
}

void readWholeChecker (char word[], int lengthWord) { // função que printa se são ou não apenas numeros
  if (wholeChecker(word, lengthWord)) {
    printf("SIM ");
  } else {
    printf("NAO ");
  }
}

void readRealsChecker (char word[], int lengthWord, int pointCounter) { // função que printa se são ou não números reais
  if (realsChecker(word, lengthWord, pointCounter)) {
    printf("SIM\n");
  } else {
    printf("NAO\n");
  }
}

void readAll (char word[], int lengthWord) { // função que chama todas as funcoes que printam
  readVowelChecker (word, lengthWord);
  readConsoantChecker (word, lengthWord);
  readWholeChecker (word, lengthWord);
  readRealsChecker (word, lengthWord, 0);
}

void read () { // funcao para ler a string
  char word[100];
  fgets(word, 100, stdin);
  word[strcspn(word, "\r\n")] = '\0'; // troca o '\n' do enter por um '\0', sinalizando o fim da string
  while (strcmp(word, "FIM") != 0) { // entra num loop que so termina quando encontrar a palavra "FIM"
    int lengthWord = strlen(word) - 1;
    readAll (word, lengthWord); // chama a funcao que printa todos os "SIM" e "NAO"
    fgets(word, 100, stdin);
    word[strcspn(word, "\r\n")] = '\0'; // troca o '\n' do enter por um '\0', indicando o fim da string
  }
}

int main () {

  read(); // chama a funcao que le a string

  return 0;
}
