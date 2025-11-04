// implementação da lista utilizando as cedulas

class Lista {



  private Celula primeiro; // declaracao das variaveis
  private Celula ultimo;



  public Lista () { // construtor

    primeiro = new Celula();
    ultimo = primeiro;

  }



  public void inserirInicio (int x) { // metodo para inserir no inicio da lista

    Celula tmp = new Celula (x);
    tmp.prox = primeiro.prox;
    primeiro.prox = tmp;

    if (primeiro == ultimo) {
      ultimo = tmp;
    }

    tmp = null;

  }



  public void removerInicio () throws Exception { // metodo de remover no inicio da lista

    if (primeiro == ultimo) {

      throw new Exception ("A fila está vazia!");
    
    }

    Celula tmp = primeiro.prox;
    primeiro.prox = tmp.prox;
    tmp.prox = null;

  }



  public void inserir (int x, int posicao) throws Exception { // metodo para inserir em qualquer posicao da lista

    int tamanho = tamanho ();

    if (posicao < 0 || posicao > tamanho) {
      
      throw new Exception ("Erro ao inserir posição!");

    } else if  (posicao == 0) { // se estiver na primeira posição, chama o metodo inserir inicio

      inserirInicio (x);

    } else if (posicao == tamanho) { // se estiver na ultima posição, chama o metodo inserir fim

      inserirFim (x);

    } else { // se estiver entre o inicio e fim, insere na posição desejada

      Celula i = primeiro;
      for (int j = 0; j < posicao; j++, i = i.prox); // caminha com i.prox ate a posição desejada

      // implementação
      Celula tmp = new Celula (x); // construtor
      tmp.prox = i.prox;
      i.prox = tmp;
      i = null;
      tmp = null;

    }

  }



  public void remover (int posicao) throws Exception {

    int tamanho = tamanho ();

    if (posicao < 0 || posicao > tamanho) {

      throw new Exception ("Essa posição não está na lista!");

    } else if (posicao == 0) {

      removerInicio ();

    } else if (posicao == tamanho) {

      removerFim ();

    } else {

      Celula i = primeiro;
      for (int j = 0; j < posicao; j++, i = i.prox);

      Celula tmp = i.prox;
      i.prox = tmp.prox;
      tmp.prox = null;
      i = null;
      tmp = null;

    }

  }



  public void removerFim () throws Exception { // metodo de remover o ultimo elemento da lista

    if (primeiro == ultimo) {

      throw new Exception ("A fila esta vazia!");

    } 
      
    Celula i;
    for (i = primeiro; i != ultimo; i = i.prox);
    ultimo = i;
    ultimo.prox = null;

  }



  public void inserirFim (int x) { // metodo para inserir no fim da lista

    ultimo.prox = new Celula (x);
    ultimo = ultimo.prox; 

  }



  public void mostrarLista () { // metodo para mostar a lista

    for (Celula i = primeiro.prox; i != null; i = i.prox) {
      System.out.print(i.prox + " ");
    }

  }

  
  
  public int tamanho () { // metodo para descobrir o tamanho da lista

    int tamanho = 0;
    for (Celula i = primeiro.prox; i != null; i = i.prox, tamanho++);

    return tamanho;
    
  }

}

