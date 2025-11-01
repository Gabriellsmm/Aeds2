import java.io.*;

class ListaRegistroGame {

    // Atributos principais do jogo
    int id;
    String name;
    String releaseDate;
    int estimatedOwners;
    float price;
    String[] supportedLanguages;
    int metacriticScore;
    float userScore;
    int achievements;
    String[] publishers;
    String[] developers;
    String[] categories;
    String[] genres;
    String[] tags;

    // Construtor padrão inicializa os valores
    public ListaRegistroGame() {
        id = 0; // ID padrão
        name = ""; // nome vazio
        releaseDate = ""; // data vazia
        estimatedOwners = 0; // donos = 0
        price = 0; // preço = 0
        supportedLanguages = new String[0]; // sem idiomas
        metacriticScore = -1; // sem nota
        userScore = -1; // sem nota de usuário
        achievements = 0; // sem conquistas
        publishers = new String[0]; // sem publisher
        developers = new String[0]; // sem dev
        categories = new String[0]; // sem categoria
        genres = new String[0]; // sem gênero
        tags = new String[0]; // sem tags
    }

    public void readLine(String line) {
        String[] fields = splitCsvFields(line); // separa os campos do CSV
        assignValues(fields); // atribui cada valor ao atributo certo
    }

    private String[] splitCsvFields(String line) {
        String[] fields = new String[14]; // número total de colunas
        int fieldIndex = 0; // índice de campo
        String current = ""; // acumulador de texto
        boolean insideQuotes = false; // dentro de aspas
        boolean insideBrackets = false; // dentro de colchetes

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i); // caractere atual

            if (c == '"') {
                insideQuotes = !insideQuotes; // alterna estado de aspas
            } else if (c == '[') {
                insideBrackets = true; current += c; // entra em colchetes
            } else if (c == ']') {
                insideBrackets = false; current += c; // sai de colchetes
            } else if (c == ',' && !insideQuotes && !insideBrackets) {
                fields[fieldIndex++] = current; // armazena campo completo
                current = ""; // reinicia acumulador
            } else {
                current += c; // concatena caractere
            }
        }

        fields[fieldIndex] = current; // último campo
        return fields; // retorna vetor final
    }

    private void assignValues(String[] f) {
        id = parseInt(f[0]); // converte ID
        name = f[1]; // nome do jogo
        releaseDate = normalizeDate(f[2]); // formata data
        estimatedOwners = parseInt(f[3]); // número de donos
        price = parsePrice(f[4]); // preço
        supportedLanguages = parseList(f[5]); // idiomas
        metacriticScore = parseIntOrDefault(f[6], -1); // nota crítica
        userScore = parseUserScore(f[7]); // nota do usuário
        achievements = parseInt(f[8]); // conquistas
        publishers = parseList(f[9]); // publicadoras
        developers = parseList(f[10]); // desenvolvedoras
        categories = parseList(f[11]); // categorias
        genres = parseList(f[12]); // gêneros
        tags = parseList(f[13]); // tags
    }

    private int parseInt(String s) {
        if (isEmpty(s)) return 0; // evita erro com vazio
        try {
            return Integer.parseInt(s.replaceAll("[^0-9]", "")); // remove letras
        } catch (Exception e) {
            return 0; // falha retorna 0
        }
    }

    private int parseIntOrDefault(String s, int def) {
        if (isEmpty(s)) return def; // retorna padrão se vazio
        try {
            return Integer.parseInt(s.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return def; // erro → valor padrão
        }
    }

    private float parsePrice(String s) {
        if (isEmpty(s) || s.equalsIgnoreCase("Free to Play")) return 0.0f; // jogo grátis
        try {
            return Float.parseFloat(s.replace(",", ".")); // converte vírgula
        } catch (Exception e) {
            return 0.0f; // erro → 0
        }
    }

    private float parseUserScore(String s) {
        if (isEmpty(s) || s.equalsIgnoreCase("tbd")) return -1.0f; // sem nota
        try {
            return Float.parseFloat(s.replace(",", ".")); // converte nota
        } catch (Exception e) {
            return -1.0f;
        }
    }

    private String[] parseList(String s) {
        if (isEmpty(s)) return new String[0]; // lista vazia
        s = s.replaceAll("[\\[\\]'\"]", ""); // remove símbolos
        if (s.equals("")) return new String[0];
        String[] parts = s.split(","); // separa por vírgula
        trimArray(parts); // limpa espaços
        return parts;
    }

    private String normalizeDate(String s) {
        if (isEmpty(s)) return "01/01/2000"; // padrão
        s = s.replace("\"", ""); // tira aspas
        String[] parts = s.split(" "); // separa partes
        return formatDate(parts); // formata
    }

    private String formatDate(String[] parts) {
        try {
            if (parts.length == 3) {
                String day = parts[1].replace(",", ""); // dia
                String month = getMonth(parts[0]); // mês
                String year = parts[2]; // ano
                return String.format("%02d/%02d/%s", Integer.parseInt(day), Integer.parseInt(month), year); // formata DD/MM/YYYY
            } else if (parts.length == 2) {
                String month = getMonth(parts[0]); // mês
                String year = parts[1]; // ano
                return String.format("01/%02d/%s", Integer.parseInt(month), year); // data padrão
            } else {
                return "01/01/2000"; // fallback
            }
        } catch (Exception e) {
            return "01/01/2000";
        }
    }

    private String getMonth(String month) {
        month = month.toLowerCase(); // normaliza texto
        String prefix = month.substring(0, 3); // pega prefixo
        switch (prefix) {
            case "jan": return "1";
            case "feb": return "2";
            case "mar": return "3";
            case "apr": return "4";
            case "may": return "5";
            case "jun": return "6";
            case "jul": return "7";
            case "aug": return "8";
            case "sep": return "9";
            case "oct": return "10";
            case "nov": return "11";
            case "dec": return "12";
            default: return "1"; // padrão
        }
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().equals(""); // verifica vazio
    }

    private void trimArray(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].trim(); // remove espaços
        }
    }

    public String toString() {
        String output = id + " ## " + name + " ## " + releaseDate + " ## " +
                estimatedOwners + " ## " + price + " ## ";
        output += formatList(supportedLanguages); // idiomas
        output += " ## " + metacriticScore + " ## " + userScore + " ## " + achievements + " ## ";
        output += formatList(publishers); // publishers
        output += " ## " + formatList(developers); // devs
        output += " ## " + formatList(categories); // categorias
        output += " ## " + formatList(genres); // gêneros
        output += " ## " + formatList(tags); // tags
        return output;
    }

    private String formatList(String[] arr) {
        String result = "[";
        for (int i = 0; i < arr.length; i++) {
            result += arr[i].trim();
            if (i < arr.length - 1) {
                result += ", "; // separador entre itens
            }
        }
        result += "]";
        return result;
    }

    static class GameList {
        private ListaRegistroGame[] list;
        private int size;

        public GameList() {
            list = new ListaRegistroGame[1000]; // vetor com capacidade inicial
            size = 0; // começa vazia
        }

        public void inserirInicio(ListaRegistroGame g) {
            for (int i = size; i > 0; i--) {
                list[i] = list[i - 1]; // desloca elementos
            }
            list[0] = g; // adiciona no início
            size++; // atualiza tamanho
        }

        public void inserirFim(ListaRegistroGame g) {
            list[size++] = g; // insere no final
        }

        public void inserir(ListaRegistroGame g, int pos) {
            for (int i = size; i > pos; i--) {
                list[i] = list[i - 1]; // desloca
            }
            list[pos] = g; // insere posição específica
            size++;
        }

        public ListaRegistroGame removerInicio() {
            ListaRegistroGame tmp = list[0]; // guarda o removido
            for (int i = 0; i < size - 1; i++) {
                list[i] = list[i + 1]; // desloca
            }
            size--;
            return tmp; // retorna removido
        }

        public ListaRegistroGame removerFim() {
            ListaRegistroGame tmp = list[size - 1]; // último
            size--;
            return tmp;
        }

        public ListaRegistroGame remover(int pos) {
            ListaRegistroGame tmp = list[pos]; // guarda removido
            for (int i = pos; i < size - 1; i++) {
                list[i] = list[i + 1]; // desloca
            }
            size--;
            return tmp;
        }

        public void display() {
            for (int i = 0; i < size; i++) {
                System.out.println(list[i]); // imprime elementos
            }
        }
    }

    public static void read() throws Exception {
        ListaRegistroGame[] games = loadGames("/tmp/games.csv"); // carrega arquivo
        GameList list = new GameList(); // cria lista
        processInput(games, list); // processa comandos
        list.display(); // exibe final
    }

    private static void processInput(ListaRegistroGame[] games, GameList list) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // leitura
        String line = br.readLine();

        while (!line.equals("FIM")) {
            int id = Integer.parseInt(line.trim()); // lê ID
            ListaRegistroGame g = findGameById(id, games); // busca jogo
            if (g != null) {
                list.inserirFim(g); // adiciona no final
            }
            line = br.readLine(); // próxima linha
        }

        int n = Integer.parseInt(br.readLine().trim()); // número de comandos
        for (int i = 0; i < n; i++) {
            line = br.readLine().trim(); // comando atual
            executeCommand(line, list, games); // executa comando
        }
        br.close();
    }

    private static void executeCommand(String cmd, GameList list, ListaRegistroGame[] games) {
        try {
            if (cmd.startsWith("II")) {
                int id = Integer.parseInt(cmd.substring(3).trim());
                list.inserirInicio(findGameById(id, games)); // insere no início
            } else if (cmd.startsWith("IF")) {
                int id = Integer.parseInt(cmd.substring(3).trim());
                list.inserirFim(findGameById(id, games)); // insere no fim
            } else if (cmd.startsWith("I*")) {
                String[] parts = cmd.split(" ");
                int pos = Integer.parseInt(parts[1]);
                int id = Integer.parseInt(parts[2]);
                list.inserir(findGameById(id, games), pos); // insere posição
            } else if (cmd.equals("RI")) {
                System.out.println("(R) " + list.removerInicio().name); // remove início
            } else if (cmd.equals("RF")) {
                System.out.println("(R) " + list.removerFim().name); // remove fim
            } else if (cmd.startsWith("R*")) {
                int pos = Integer.parseInt(cmd.substring(3).trim());
                System.out.println("(R) " + list.remover(pos).name); // remove posição
            }
        } catch (Exception e) {
            // ignora erro de comando inválido
        }
    }

    private static ListaRegistroGame[] loadGames(String path) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(path)); // abre CSV
        String line = br.readLine(); // ignora cabeçalho
        ListaRegistroGame[] games = new ListaRegistroGame[20000]; // vetor temporário
        int count = 0;

        while ((line = br.readLine()) != null) {
            ListaRegistroGame g = new ListaRegistroGame();
            g.readLine(line); // lê e preenche dados
            games[count++] = g;
        }

        br.close();
        ListaRegistroGame[] result = new ListaRegistroGame[count]; // vetor exato
        System.arraycopy(games, 0, result, 0, count); // copia
        return result;
    }

    private static ListaRegistroGame findGameById(int id, ListaRegistroGame[] games) {
        for (ListaRegistroGame g : games) {
            if (g != null && g.id == id) {
                return g; // retorna jogo encontrado
            }
        }
        return null; // não encontrado
    }

    public static void main(String[] args) throws Exception {
        read(); // inicia execução
    }
}
