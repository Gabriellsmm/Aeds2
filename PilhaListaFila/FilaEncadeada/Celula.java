// criação de uma celula simples encadeada

class Celula {
  
  // define os elementos presentes na celula
  public int elemento;
  public Celula prox;

  // chama o outro construtor
  public Celula () {
    
    this(0);

  }

  // construtor completo
  public Celula (int elemento) {

    this.elemento = elemento;
    this.prox = null;

  }

}
