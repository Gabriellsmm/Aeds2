import java.io.*;

class FilaRegistroGame {

    // Atributos que representam os dados de um jogo
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

    // Construtor padrão que inicializa os atributos com valores neutros
    public FilaRegistroGame() {
        id = 0;
        name = "";
        releaseDate = "";
        estimatedOwners = 0;
        price = 0;
        supportedLanguages = new String[0];
        metacriticScore = -1;
        userScore = -1;
        achievements = 0;
        publishers = new String[0];
        developers = new String[0];
        categories = new String[0];
        genres = new String[0];
        tags = new String[0];
    }

    // Lê uma linha do CSV e atribui seus valores ao objeto atual
    public void readLine(String line) {
        String[] fields = splitCsvFields(line); // divide a linha em colunas
        assignValues(fields); // converte e atribui valores aos atributos
    }

    // Separa os campos de uma linha CSV levando em conta aspas e colchetes
    private String[] splitCsvFields(String line) {
        String[] fields = new String[14];
        int fieldIndex = 0;
        String current = "";
        boolean insideQuotes = false;
        boolean insideBrackets = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i); // percorre cada caractere

            if (c == '"') {
                insideQuotes = !insideQuotes; // alterna o estado de aspas
            } else if (c == '[') {
                insideBrackets = true; // entra em colchetes
                current += c;
            } else if (c == ']') {
                insideBrackets = false; // sai dos colchetes
                current += c;
            } else if (c == ',' && !insideQuotes && !insideBrackets) {
                fields[fieldIndex++] = current; // salva o campo atual
                current = ""; // reinicia para o próximo campo
            } else {
                current += c; // adiciona o caractere atual
            }
        }

        fields[fieldIndex] = current; // adiciona o último campo
        return fields;
    }

    // Converte e atribui os valores do vetor de strings aos atributos do jogo
    private void assignValues(String[] f) {
        id = parseInt(f[0]);
        name = f[1];
        releaseDate = normalizeDate(f[2]);
        estimatedOwners = parseInt(f[3]);
        price = parsePrice(f[4]);
        supportedLanguages = parseList(f[5]);
        metacriticScore = parseIntOrDefault(f[6], -1);
        userScore = parseUserScore(f[7]);
        achievements = parseInt(f[8]);
        publishers = parseList(f[9]);
        developers = parseList(f[10]);
        categories = parseList(f[11]);
        genres = parseList(f[12]);
        tags = parseList(f[13]);
    }

    // Converte string para número inteiro, removendo caracteres não numéricos
    private int parseInt(String s) {
        if (isEmpty(s)) return 0; // se vazio, retorna 0
        try {
            return Integer.parseInt(s.replaceAll("[^0-9]", "")); // remove tudo que não for número
        } catch (Exception e) {
            return 0;
        }
    }

    // Converte string em inteiro, retornando valor padrão em caso de erro
    private int parseIntOrDefault(String s, int def) {
        if (isEmpty(s)) return def;
        try {
            return Integer.parseInt(s.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return def;
        }
    }

    // Converte preço textual em float; "Free to Play" é tratado como 0
    private float parsePrice(String s) {
        if (isEmpty(s) || s.equalsIgnoreCase("Free to Play")) return 0.0f;
        try {
            return Float.parseFloat(s.replace(",", ".")); // troca vírgula por ponto
        } catch (Exception e) {
            return 0.0f;
        }
    }

    // Converte nota do usuário em float; "tbd" ou vazio é -1
    private float parseUserScore(String s) {
        if (isEmpty(s) || s.equalsIgnoreCase("tbd")) return -1.0f;
        try {
            return Float.parseFloat(s.replace(",", "."));
        } catch (Exception e) {
            return -1.0f;
        }
    }

    // Converte campos em formato de lista para vetor de strings
    private String[] parseList(String s) {
        if (isEmpty(s)) return new String[0];
        s = s.replaceAll("[\\[\\]'\"]", ""); // remove colchetes e aspas
        if (s.equals("")) return new String[0];
        String[] parts = s.split(","); // divide por vírgulas
        trimArray(parts); // remove espaços extras
        return parts;
    }

    // Normaliza datas no formato textual ("Oct 18, 2018") para "18/10/2018"
    private String normalizeDate(String s) {
        if (isEmpty(s)) return "01/01/2000";
        s = s.replace("\"", "");
        String[] parts = s.split(" ");
        return formatDate(parts);
    }

    // Reorganiza as partes da data (mês, dia, ano) no formato correto
    private String formatDate(String[] parts) {
        try {
            if (parts.length == 3) { // dia e mês informados
                String day = parts[1].replace(",", "");
                String month = getMonth(parts[0]); // converte nome do mês
                String year = parts[2];
                return String.format("%02d/%02d/%s",
                        Integer.parseInt(day), Integer.parseInt(month), year);
            } else if (parts.length == 2) { // só mês e ano informados
                String month = getMonth(parts[0]);
                String year = parts[1];
                return String.format("01/%02d/%s", Integer.parseInt(month), year);
            } else {
                return "01/01/2000";
            }
        } catch (Exception e) {
            return "01/01/2000";
        }
    }

    // Converte nome abreviado do mês em número
    private String getMonth(String month) {
        month = month.toLowerCase();
        String prefix = month.substring(0, 3);
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
            default: return "1";
        }
    }

    // Retorna verdadeiro se a string for vazia ou nula
    private boolean isEmpty(String s) {
        return s == null || s.trim().equals("");
    }

    // Remove espaços extras em todos os elementos de um vetor
    private void trimArray(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].trim();
        }
    }

    // Retorna uma string formatada com todos os dados do jogo
    public String toString() {
        String output = id + " ## " + name + " ## " + releaseDate + " ## " +
                estimatedOwners + " ## " + price + " ## ";
        output += formatList(supportedLanguages);
        output += " ## " + metacriticScore + " ## " + userScore + " ## " + achievements + " ## ";
        output += formatList(publishers);
        output += " ## " + formatList(developers);
        output += " ## " + formatList(categories);
        output += " ## " + formatList(genres);
        output += " ## " + formatList(tags);
        return output;
    }

    // Formata listas como "[A, B, C]"
    private String formatList(String[] arr) {
        String result = "[";
        for (int i = 0; i < arr.length; i++) {
            result += arr[i].trim();
            if (i < arr.length - 1) result += ", ";
        }
        result += "]";
        return result;
    }

    // Classe interna que implementa uma fila simples de jogos
    static class GameQueue {
        private FilaRegistroGame[] queue;
        private int size;

        // Inicializa a fila com capacidade de 1000 elementos
        public GameQueue() {
            queue = new FilaRegistroGame[1000];
            size = 0;
        }

        // Adiciona um jogo ao final da fila
        public void enqueue(FilaRegistroGame g) {
            queue[size++] = g;
        }

        // Remove o primeiro jogo da fila e desloca os demais
        public FilaRegistroGame dequeue() {
            if (size == 0) return null;
            FilaRegistroGame removed = queue[0];
            for (int i = 1; i < size; i++) {
                queue[i - 1] = queue[i];
            }
            size--;
            return removed;
        }

        // Imprime os jogos da fila com índice
        public void display() {
            for (int i = 0; i < size; i++) {
                System.out.println("[" + i + "] => " + queue[i]);
            }
        }
    }

    // Controla a leitura de IDs, comandos e execução da fila
    public static void read() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // leitura padrão
        FilaRegistroGame[] games = loadGames("/tmp/games.csv"); // carrega o CSV em memória
        GameQueue queue = new GameQueue(); // inicializa a fila

        // Lê IDs iniciais até "FIM"
        String line = br.readLine();
        while (line != null && !line.equals("FIM")) {
            int id = Integer.parseInt(line.trim()); // converte o ID
            FilaRegistroGame g = findGameById(id, games); // busca o jogo no vetor
            if (g != null) queue.enqueue(g); // adiciona à fila
            line = br.readLine(); // próxima linha
        }

        // Lê número de comandos
        int n = Integer.parseInt(br.readLine().trim());

        // Executa cada comando
        for (int i = 0; i < n; i++) {
            line = br.readLine();
            if (line == null) break;
            line = line.trim();

            if (line.startsWith("I ")) { // comando de inserção
                int id = Integer.parseInt(line.substring(2)); // pega o ID após o "I "
                FilaRegistroGame g = findGameById(id, games);
                if (g != null) queue.enqueue(g);
            } else if (line.equals("R")) { // comando de remoção
                FilaRegistroGame removed = queue.dequeue();
                if (removed != null)
                    System.out.println("(R) " + removed.name);
            }
        }

        // Exibe a fila final
        queue.display();
        br.close();
    }

    // Lê o arquivo CSV e carrega todos os jogos em um vetor
    private static FilaRegistroGame[] loadGames(String path) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = br.readLine(); // ignora o cabeçalho
        FilaRegistroGame[] games = new FilaRegistroGame[20000];
        int count = 0;

        while ((line = br.readLine()) != null) {
            FilaRegistroGame g = new FilaRegistroGame();
            g.readLine(line); // lê e preenche os atributos
            games[count++] = g;
        }

        br.close();

        // Cria um vetor com tamanho exato
        FilaRegistroGame[] result = new FilaRegistroGame[count];
        System.arraycopy(games, 0, result, 0, count);
        return result;
    }

    // Busca um jogo pelo ID no vetor de jogos
    private static FilaRegistroGame findGameById(int id, FilaRegistroGame[] games) {
        for (FilaRegistroGame g : games) {
            if (g != null && g.id == id)
                return g;
        }
        return null;
    }

    // Método principal — inicia a execução
    public static void main(String[] args) throws Exception {
        read();
    }
}
