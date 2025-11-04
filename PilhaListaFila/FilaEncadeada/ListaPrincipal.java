class ListaPrincipal {

  public static void read () throws Exception {

    try {

      System.out.println("===Lista simples encadeada===");

      Lista lista = new Lista ();

      lista.inserirInicio (4);
      lista.inserirInicio (5);
      lista.inserirFim (8);
      lista.inserirFim (2);
      lista.inserir (6, 1);
      lista.inserir (7, 0);
      lista.inserir (1, 8);
      lista.inserir (3, 5);
      lista.inserirFim (9);

      System.out.println("Após Inserções: ");
      lista.mostrarLista ();

    } catch (Exception erro) {

      System.out.println(erro.getMessage());

    }

  }

  public static void main(String[] args) {

    try {

        read();  // o método pode lançar uma Exception
                 
    } catch (Exception e) {

        System.out.println("Erro ao executar o método read: " + e.getMessage());

    }
}


}
