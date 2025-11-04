#include <stdio.h>
#include <stdlib.h>

void mostarMatriz (int **matriz, int linhas, int colunas) { // metodo que mostra a matriz preenchida

  for (int i = 0; i < linhas; i++) {
    for (int j = 0; j < colunas; j++) {
      printf("%d ", matriz[i][j]);
    }
    printf("\n");
  }

  return;
}

void preencherMatriz (int **matriz, int linhas, int colunas) { // metodo que preenche a matriz

  printf("Preencha a matriz: ");

  for (int i = 0; i < linhas; i++) {
    for (int j = 0; j < colunas; j++) {
      scanf("%d", &matriz[i][j]);
    }
  }

  return;
}

int** criarMatriz (int linhas, int colunas) { // metodo que cria a matriz dinamica, utilizando malloc

  int** matriz = (int**) malloc (linhas * sizeof (int*)); // cria as linhas

  for (int i = 0; i < linhas; i++) {
    matriz[i] = (int*) malloc (colunas * sizeof(int)); // crias as colunas de acordo com a quantidade de linhas
  }

  return matriz;
}

void read () { // metodo que faz a leitura 
  
  int linhas, colunas;

  printf("Quantas linhas você deseja? ");
  scanf("%d", &linhas);
  printf("Quantas Colunas você deseja? ");
  scanf("%d", &colunas);

  int **matriz = criarMatriz(linhas, colunas); // chama o metodo para criar a matriz
  preencherMatriz (matriz, linhas, colunas); // chama o metodo para preencher a matriz
  mostarMatriz (matriz, linhas, colunas); // chama o metodo para printar a matriz

}

int main () {

  read (); // chama o metodo que faz a leitura

  return 0;
}
