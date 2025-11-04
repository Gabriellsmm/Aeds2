// construção de uma celula duplamente encadeada

class CelulaDupla {

  // declaração dos elementos
  public int elemento;
  public CelulaDupla prox;
  public CelulaDupla anterior;

  // chama o outro construtor
  public CelulaDupla () {

    this(0, null, null);

  }

  // construtor completo
  public CelulaDupla (int elemento, CelulaDupla prox, CelulaDupla anterior) {

    this.elemento = elemento;
    this.prox = prox;
    this.anterior = anterior;

  }

}
