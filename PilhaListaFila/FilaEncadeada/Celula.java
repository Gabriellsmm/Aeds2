// criação de uma celula simples encadeada

public class Celula {
  
  // define os elementos presentes na celula
  public int elemento;
  public Celula proxima;

  // chama o outro construtor
  public Celula () {
    
    this(0);

  }

  // construtor completo
  public Celula (int elemento) {

    this.elemento = elemento;
    this.proxima = null;

  }

}
