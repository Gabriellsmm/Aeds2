#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>

bool vowelChecker (char word[]) { // funcao para verificar se a string possui somente vogais
  bool vowel = true;
  for (int i = 0; i < strlen(word); i++) { 
    if (word[i] == 'a' || word[i] == 'A' || word[i] == 'e' || word[i] == 'E' || word[i] == 'i' || word[i] == 'I' || word[i] == 'o' || word[i] == 'O' || word[i] == 'u' || word[i] == 'U') { // compara a posicao da string com as vogais
      continue;
    } 
      return vowel = false; // se alguma letra nao for uma vogal, ja retorna falso 
  }
  return vowel;
}

bool consoantChecker (char word[]) { // funcao para verificar se a string possui somente consoantes
  bool consoant = true;
  for (int i = 0; i < strlen(word); i++) {
    if (word[i] != 'a' && word[i] != 'A' && word[i] != 'e' && word[i] != 'E' && word[i] != 'i' && word[i] != 'I' && word[i] != 'o' && word[i] != 'O' && word[i] != 'u' && word[i] != 'U' // compara se a posicao da string nao possui vogais, nem numeros, nem sinais, e pontos
    && word[i] != '1' && word[i] != '2' && word[i] != '3' && word[i] != '4' && word[i] != '5' && word[i] != '6' && word[i] != '7' && word[i] != '8' && word[i] != '9' && word[i] != '0' && word[i] != '.' && word[i] != '-' && word[i] != '+') { 
      continue;
    }
    return consoant = false; // se a string possuir alguma vogal, numero, sinais ou pontos, ja retorna falso
  }
  return consoant;
}

bool wholeChecker (char word[]) { // funcao para verificar se a string so possui somente numeros inteiros
  bool whole = true;
  for (int i = 0; i < strlen(word); i++) {
    if (word[i] == '1' || word[i] == '2' || word[i] == '3' || word[i] == '4' || word[i] == '5' || word[i] == '6' || word[i] == '7' || word[i] == '8' || word[i] == '9' || word[i] == '0') { // compara se a posicao da string possui apenas numeros
      continue;
    }
    return whole = false; // se a string possuir alguma letra, ou sinais, ja retorna falso
  }
  return whole;
}

bool realsChecker (char word[]) { // funcao para verificar se a string possui somente numeros reais
  bool reals = true;
  int pointCounter = 0;
  for (int i = 0; i < strlen(word); i++) {
    if (word[i] == '.' || word[i] == ',') { // compara as letras da string, tentando encontrar algum ponto, que indicaria um numero real
      pointCounter++;
      continue;
    }
    if (word[i] == '1' || word[i] == '2' || word[i] == '3' || word[i] == '4' || word[i] == '5' || word[i] == '6' || word[i] == '7' || word[i] == '8' || word[i] == '9' || word[i] == '0') { // compara se a posicao da string possui apenas numeros
      continue;
    }
    return reals = false; // se tiver algum outro caracter alem de numeros, retorna falso
    }
    if (pointCounter > 1) { // se o contador for maior que 1, retorna falso, pois indica que nao é um numero real
      return reals = false;
    }
    return reals;
}

void readAll (char word[]) { // funcao para printar se a string atende ou nao atende ao que foi pedido
  if (vowelChecker(word)) { // verifica se a string so possui vogais
    printf("SIM ");
  } else {
    printf("NAO ");
  }
  if (consoantChecker(word)) { // verifica se a string so possui consoantes
    printf("SIM ");
  } else {
    printf("NAO ");
  }
  if (wholeChecker(word)) { // verifica se a string so possui numeros inteiros
    printf("SIM ");
  } else {
    printf("NAO ");
  }
  if (realsChecker(word)) { // verifica se a string so possui numeros reais
    printf("SIM");
  } else {
    printf("NAO");
  }
}

void read () { // funcao para ler a string
  char word[100];
  fgets(word, 100, stdin);
  word[strcspn(word, "\r\n")] = '\0'; // troca o '\n' do enter por um '\0', sinalizando o fim da string
  while (strcmp(word, "FIM") != 0) { // entra num loop que so termina quando encontrar a palavra "FIM"
    readAll (word); // chama a funcao que printa todos os "SIM" e "NAO"
    printf("\n");
    fgets(word, 100, stdin);
    word[strcspn(word, "\r\n")] = '\0'; // troca o '\n' do enter por um '\0', indicando o fim da string
  }
}

int main () {

  read(); // chama a funcao que le a string

  return 0;
}
