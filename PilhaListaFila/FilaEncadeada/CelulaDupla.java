// construção de uma celula duplamente encadeada

public class CelulaDupla {

  // declaração dos elementos
  public int elemento;
  public CelulaDupla proxima;
  public CelulaDupla anterior;

  // chama o outro construtor
  public CelulaDupla () {

    this(0, null, null);

  }

  // construtor completo
  public CelulaDupla (int elemento, CelulaDupla proxima, CelulaDupla anterior) {

    this.elemento = elemento;
    this.proxima = proxima;
    this.anterior = anterior;

  }

}
