import java.io.*;
import java.util.*;

class HeapSortJava {

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

    // construtor básico inicializa tudo vazio ou zero
    public HeapSortJava() {
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

    // lê uma linha do csv e converte para objeto
    public void readLine(String line) {
        String[] fields = splitCsvFields(line); // separa os campos da linha
        assignValues(fields); // atribui os valores nos atributos
    }

    // separa os campos respeitando aspas e colchetes
    private String[] splitCsvFields(String line) {
        String[] fields = new String[14]; // total de colunas no csv
        int fieldIndex = 0;
        String current = "";
        boolean insideQuotes = false;
        boolean insideBrackets = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i); // caractere atual
            if (c == '"') insideQuotes = !insideQuotes; // alterna se encontrar aspas
            else if (c == '[') {
                insideBrackets = true; // detecta colchete de início
                current += c;
            } else if (c == ']') {
                insideBrackets = false; // detecta fim de colchete
                current += c;
            } else if (c == ',' && !insideQuotes && !insideBrackets) {
                fields[fieldIndex++] = current; // salva campo
                current = ""; // limpa variável temporária
            } else current += c; // adiciona caractere normalmente
        }

        fields[fieldIndex] = current; // adiciona último campo
        return fields; // retorna vetor com todos os campos separados
    }

    // converte cada campo e preenche o objeto
    private void assignValues(String[] f) {
        id = parseInt(f[0]); // converte ID
        name = f[1]; // nome do jogo
        releaseDate = normalizeDate(f[2]); // normaliza data
        estimatedOwners = parseInt(f[3]); // converte número de donos
        price = parsePrice(f[4]); // trata preço
        supportedLanguages = parseList(f[5]); // converte lista de idiomas
        metacriticScore = parseIntOrDefault(f[6], -1); // nota metacritic
        userScore = parseUserScore(f[7]); // nota dos usuários
        achievements = parseInt(f[8]); // conquistas
        publishers = parseList(f[9]); // publicadoras
        developers = parseList(f[10]); // desenvolvedoras
        categories = parseList(f[11]); // categorias
        genres = parseList(f[12]); // gêneros
        tags = parseList(f[13]); // tags
    }

    private int parseInt(String s) {
        if (isEmpty(s)) return 0; // se vazio retorna 0
        try {
            return Integer.parseInt(s.replaceAll("[^0-9]", "")); // remove tudo que não for número
        } catch (Exception e) {
            return 0; // se der erro retorna 0
        }
    }

    private int parseIntOrDefault(String s, int def) {
        if (isEmpty(s)) return def;
        try {
            return Integer.parseInt(s.replaceAll("[^0-9]", "")); // converte se possível
        } catch (Exception e) {
            return def; // se não der, retorna valor padrão
        }
    }

    private float parsePrice(String s) {
        if (isEmpty(s) || s.equalsIgnoreCase("Free to Play")) return 0.0f; // trata jogo gratuito
        try {
            String cleaned = s.replaceAll("[^0-9,\\.]", "").replace(",", "."); // tira símbolos
            if (cleaned.equals("")) return 0.0f;
            return Float.parseFloat(cleaned); // converte pra float
        } catch (Exception e) {
            return 0.0f; // fallback
        }
    }

    private float parseUserScore(String s) {
        if (isEmpty(s) || s.equalsIgnoreCase("tbd")) return -1.0f;
        try {
            return Float.parseFloat(s.replace(",", ".")); // converte nota de usuário
        } catch (Exception e) {
            return -1.0f;
        }
    }

    private String[] parseList(String s) {
        if (isEmpty(s)) return new String[0];
        s = s.replaceAll("[\\[\\]'\"]", ""); // remove colchetes e aspas
        if (s.equals("")) return new String[0];
        String[] parts = s.split(","); // divide pelos itens
        for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim(); // tira espaços
        return parts; // retorna lista
    }

    private String normalizeDate(String s) {
        if (isEmpty(s)) return "01/01/2000"; // padrão se vazio
        s = s.replace("\"", "");
        String[] parts = s.split(" ");
        return formatDate(parts); // formata a data
    }

    private String formatDate(String[] parts) {
        try {
            if (parts.length == 3) {
                String day = parts[1].replace(",", ""); // remove vírgula
                String month = getMonth(parts[0]); // converte mês pra número
                String year = parts[2]; // pega ano
                return String.format("%02d/%02d/%s", Integer.parseInt(day), Integer.parseInt(month), year); // monta data completa
            } else if (parts.length == 2) {
                String month = getMonth(parts[0]);
                String year = parts[1];
                return String.format("01/%02d/%s", Integer.parseInt(month), year); // data padrão com dia 01
            } else return "01/01/2000";
        } catch (Exception e) {
            return "01/01/2000"; // fallback se erro
        }
    }

    private String getMonth(String month) {
        month = month.toLowerCase();
        String prefix = month.substring(0, 3); // pega primeiros 3 caracteres
        if (prefix.equals("jan")) return "1";
        if (prefix.equals("feb")) return "2";
        if (prefix.equals("mar")) return "3";
        if (prefix.equals("apr")) return "4";
        if (prefix.equals("may")) return "5";
        if (prefix.equals("jun")) return "6";
        if (prefix.equals("jul")) return "7";
        if (prefix.equals("aug")) return "8";
        if (prefix.equals("sep")) return "9";
        if (prefix.equals("oct")) return "10";
        if (prefix.equals("nov")) return "11";
        if (prefix.equals("dec")) return "12";
        return "1"; // se der erro retorna janeiro
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().equals(""); // verifica se é vazio
    }

    private String formatList(String[] arr) {
        String result = "[";
        for (int i = 0; i < arr.length; i++) {
            result += arr[i];
            if (i < arr.length - 1) result += ", ";
        }
        result += "]";
        return result; // retorna lista formatada
    }

    public String toString() {
        return "=> " + id + " ## " + name + " ## " + releaseDate + " ## " +
                estimatedOwners + " ## " + price + " ## " + formatList(supportedLanguages) +
                " ## " + metacriticScore + " ## " + userScore + " ## " + achievements + " ## " +
                formatList(publishers) + " ## " + formatList(developers) + " ## " +
                formatList(categories) + " ## " + formatList(genres) + " ## " +
                formatList(tags) + " ##";
    }

    private static int comparisons = 0;
    private static int movements = 0;

    // compara dois objetos de jogo com base em estimatedOwners e id (critério secundário)
    private static boolean greater(HeapSortJava a, HeapSortJava b) {
        comparisons++;
        if (a.estimatedOwners > b.estimatedOwners) return true;
        if (a.estimatedOwners == b.estimatedOwners && a.id > b.id) return true;
        return false;
    }

    // troca dois elementos de posição no vetor
    private static void swap(HeapSortJava[] arr, int i, int j) {
        HeapSortJava temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        movements += 3; // cada troca equivale a 3 movimentações
    }

    // reorganiza o heap mantendo a propriedade de heap máximo
    private static void heapify(HeapSortJava[] arr, int n, int i) {
        int largest = i;           // índice do maior elemento (começa como o pai)
        int left = 2 * i + 1;      // filho à esquerda
        int right = 2 * i + 2;     // filho à direita

        // verifica se o filho esquerdo é maior que o pai
        if (left < n && greater(arr[left], arr[largest])) largest = left;

        // verifica se o filho direito é maior que o maior atual
        if (right < n && greater(arr[right], arr[largest])) largest = right;

        // se o maior não for o pai, faz a troca e aplica heapify recursivamente
        if (largest != i) {
            swap(arr, i, largest); // move o maior elemento para o topo
            heapify(arr, n, largest); // garante que o sub-heap também esteja correto
        }
    }

    // método principal de ordenação heap sort
    public static void heapSort(HeapSortJava[] arr) {
        int n = arr.length;

        // etapa 1: construção do heap máximo
        // começa do último nó não-folha até a raiz
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // etapa 2: remoção sucessiva do maior elemento
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);      // coloca o maior elemento (raiz) no final
            heapify(arr, i, 0);   // reajusta o heap com o restante do vetor
        }
    }

    public static void createLog(String matricula, int comparisons, int movements, int time) throws Exception {
        try (PrintWriter log = new PrintWriter(matricula + "_heapsort.txt", "UTF-8")) {
            log.printf("%s\t%d\t%d\t%d", matricula, comparisons, movements, time); // grava tudo no arquivo
        }
    }

    private static int[] readInputIds() {
        Scanner in = new Scanner(System.in);
        int[] ids = new int[500];
        int count = 0;
        String line = in.nextLine();

        while (!line.equals("FIM")) {
            ids[count++] = Integer.parseInt(line.trim()); // converte e guarda id
            line = in.nextLine(); // lê próxima linha
        }

        in.close();
        return Arrays.copyOf(ids, count); // retorna vetor ajustado
    }

    private static HeapSortJava[] loadGames(String path) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(path));
        br.readLine(); // ignora cabeçalho
        ArrayList<HeapSortJava> list = new ArrayList<>();

        String currentLine;
        while ((currentLine = br.readLine()) != null) {
            HeapSortJava g = new HeapSortJava();
            g.readLine(currentLine); // lê e converte linha
            list.add(g); // adiciona à lista
        }

        br.close();
        HeapSortJava[] games = new HeapSortJava[list.size()];
        for (int i = 0; i < list.size(); i++) games[i] = list.get(i); // copia lista pra vetor
        return games; // retorna todos os jogos
    }

    private static HeapSortJava[] selectGamesByIds(int[] ids, HeapSortJava[] games) {
        ArrayList<HeapSortJava> sel = new ArrayList<>();
        HashSet<Integer> unique = new HashSet<>();

        for (int i = 0; i < ids.length; i++) {
            int id = ids[i];
            if (unique.add(id)) { // evita repetição
                for (int j = 0; j < games.length; j++) {
                    if (games[j].id == id) sel.add(games[j]); // adiciona se encontrar id
                }
            }
        }

        HeapSortJava[] selected = new HeapSortJava[sel.size()];
        for (int i = 0; i < sel.size(); i++) selected[i] = sel.get(i);
        return selected; // retorna jogos selecionados
    }

    public static void read() throws Exception {
        int[] ids = readInputIds(); // lê ids
        HeapSortJava[] allGames = loadGames("/tmp/games.csv"); // carrega todos os jogos
        HeapSortJava[] selected = selectGamesByIds(ids, allGames); // filtra pelos ids

        int start = (int) System.currentTimeMillis(); // marca início

        if (selected.length > 0)
            heapSort(selected); // ordena vetor usando heapsort

        int time = (int) (System.currentTimeMillis() - start); // calcula tempo total

        for (int i = 0; i < selected.length; i++)
            System.out.println(selected[i]); // imprime resultados na tela

        createLog("869624", comparisons, movements, time); // gera arquivo de log
    }

    public static void main(String[] args) throws Exception {
        read(); // executa programa
    }
}
