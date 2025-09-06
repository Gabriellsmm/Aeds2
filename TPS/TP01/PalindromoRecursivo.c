#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>

bool Palindromo (char s[], int i, int j) { // função para identificar se a palavra é um palindromo
  if (i >= j)
    return true;

  if (s[i] != s[j] && s[i] < 122 && s[j] < 122)
    return false; // assim que encontrar uma letra diferente, ja retorna falso, nao testa toda a string
  
  return Palindromo(s, i + 1, j - 1); // recursividade para o teste de palindromo
}

bool TestePalindromo (char s[]) { // função que passa os parametros para o teste de palindromo

  int i = 0;
  int j = strlen(s) - 1;

  bool resp = Palindromo(s, i, j);

  return resp;
}

int main () {

  char word[100];
  bool resposta;
  do {
    fgets(word, 100, stdin);
    word[strcspn(word, "\r\n")] = '\0'; // adiciona um \0 para finalizar a string, assim tirando o \n(ou o \r se a maquina for windows)
    if (strcmp(word, "FIM") == 0) { // metodo de parada para nao imprimir 'NAO' após encontrar o FIM
      break;
    }
    resposta = TestePalindromo(word);
    if (resposta) {
      printf("SIM\n");
    } else {
      printf("NAO\n");
    }
  } while (1); // condição que faz com que o loop se repita infinitamente, até parar no break 
  return 0;
}
