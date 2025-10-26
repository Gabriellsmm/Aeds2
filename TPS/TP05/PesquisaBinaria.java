import java.io.*;
import java.util.*;

class PesquisaBinaria {

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

    // construtor inicializa todos os atributos
    public PesquisaBinaria() {
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

    // lê uma linha do CSV e converte para objeto
    public void readLine(String line) {
        String[] fields = splitCsvFields(line); // separa os campos respeitando vírgulas, aspas e colchetes
        assignValues(fields); // converte os campos e preenche os atributos
    }

    // separa os campos do CSV sem quebrar listas (que usam vírgulas internas)
    private String[] splitCsvFields(String line) {
        String[] fields = new String[14];
        int fieldIndex = 0;
        String current = "";
        boolean insideQuotes = false, insideBrackets = false;

        // percorre cada caractere da linha
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') insideQuotes = !insideQuotes;          // alterna estado se encontrar aspas
            else if (c == '[') insideBrackets = true;            // dentro de colchetes
            else if (c == ']') insideBrackets = false;           // fim de colchete
            else if (c == ',' && !insideQuotes && !insideBrackets) {
                // vírgula válida como separador → salva campo atual
                fields[fieldIndex++] = current;
                current = "";
            } else current += c; // adiciona caractere ao campo atual
        }

        fields[fieldIndex] = current; // adiciona último campo
        return fields;
    }

    // converte cada campo do vetor em atributo do objeto
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

    // tenta converter string em número inteiro
    private int parseInt(String s) {
        if (isEmpty(s)) return 0;
        try {
            return Integer.parseInt(s.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    // igual ao parseInt, mas com valor padrão em caso de erro
    private int parseIntOrDefault(String s, int def) {
        if (isEmpty(s)) return def;
        try {
            return Integer.parseInt(s.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return def;
        }
    }

    // converte preço em número decimal
    private float parsePrice(String s) {
        if (isEmpty(s) || s.equalsIgnoreCase("Free to Play")) return 0;
        try {
            return Float.parseFloat(s.replaceAll("[^0-9.]", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    // converte nota do usuário (float)
    private float parseUserScore(String s) {
        if (isEmpty(s) || s.equalsIgnoreCase("tbd")) return -1;
        try {
            return Float.parseFloat(s.replace(",", "."));
        } catch (Exception e) {
            return -1;
        }
    }

    // converte listas CSV em vetores de strings
    private String[] parseList(String s) {
        if (isEmpty(s)) return new String[0];
        s = s.replaceAll("[\\[\\]'\"]", ""); // remove colchetes e aspas
        if (s.equals("")) return new String[0];
        String[] parts = s.split(",");
        for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();
        return parts;
    }

    // converte datas do CSV para formato padronizado DD/MM/AAAA
    private String normalizeDate(String s) {
        if (isEmpty(s)) return "01/01/2000";
        s = s.replace("\"", "");
        String[] parts = s.split(" ");
        try {
            String day = parts[1].replace(",", "");
            String month = getMonth(parts[0]);
            String year = parts[2];
            return day + "/" + month + "/" + year;
        } catch (Exception e) {
            return "01/01/2000";
        }
    }

    // retorna número correspondente ao mês abreviado
    private String getMonth(String month) {
        month = month.toLowerCase();
        String prefix = month.substring(0, 3);
        if (prefix.equals("jan")) return "01";
        if (prefix.equals("feb")) return "02";
        if (prefix.equals("mar")) return "03";
        if (prefix.equals("apr")) return "04";
        if (prefix.equals("may")) return "05";
        if (prefix.equals("jun")) return "06";
        if (prefix.equals("jul")) return "07";
        if (prefix.equals("aug")) return "08";
        if (prefix.equals("sep")) return "09";
        if (prefix.equals("oct")) return "10";
        if (prefix.equals("nov")) return "11";
        if (prefix.equals("dec")) return "12";
        return "01";
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().equals("");
    }

    // formata saída padrão de um objeto jogo
    public String toString() {
        return "=> " + id + " ## " + name + " ## " + releaseDate + " ## " +
                estimatedOwners + " ## " + price + " ## " + Arrays.toString(supportedLanguages) + " ## " +
                metacriticScore + " ## " + userScore + " ## " + achievements + " ## " +
                Arrays.toString(publishers) + " ## " + Arrays.toString(developers) + " ## " +
                Arrays.toString(categories) + " ## " + Arrays.toString(genres) + " ## " +
                Arrays.toString(tags) + " ##";
    }

    // carrega o arquivo CSV e retorna todos os jogos lidos
    private static PesquisaBinaria[] loadGames(String path) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(path));
        br.readLine(); // ignora o cabeçalho
        ArrayList<PesquisaBinaria> list = new ArrayList<>();
        String line;

        // lê linha por linha e converte cada uma em objeto
        while ((line = br.readLine()) != null) {
            PesquisaBinaria g = new PesquisaBinaria();
            g.readLine(line);
            list.add(g);
        }

        br.close();
        return list.toArray(new PesquisaBinaria[0]);
    }

    // lê IDs informados e seleciona os jogos correspondentes
    private static PesquisaBinaria[] insertGames(Scanner in, PesquisaBinaria[] allGames) {
        PesquisaBinaria[] selected = new PesquisaBinaria[5000];
        int count = 0;
        String line = in.nextLine();

        // lê até que o usuário digite "FIM"
        while (!line.equals("FIM")) {
            int id = Integer.parseInt(line.trim());

            // busca o jogo com o ID informado
            for (int i = 0; i < allGames.length; i++) {
                if (allGames[i].id == id) {
                    selected[count++] = allGames[i];
                    break; // para após encontrar o jogo
                }
            }
            line = in.nextLine();
        }

        return Arrays.copyOf(selected, count);
    }

    // realiza a busca binária pelo nome do jogo
    private static boolean binarySearch(String key, PesquisaBinaria[] games, int n, int[] comparisons) {
        int left = 0, right = n - 1;

        // divide o intervalo até achar ou acabar
        while (left <= right) {
            int mid = (left + right) / 2; // calcula índice do meio
            comparisons[0]++; // conta comparação feita

            int cmp = key.compareToIgnoreCase(games[mid].name); // compara nome buscado com o nome do meio

            if (cmp == 0) return true;          // nome encontrado
            else if (cmp > 0) left = mid + 1;   // busca na metade direita
            else right = mid - 1;               // busca na metade esquerda
        }

        return false; // não encontrado
    }

    // ordena o vetor de jogos alfabeticamente pelo nome
    private static void sort(PesquisaBinaria[] games, int n, int[] comparisons) {
        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                comparisons[0]++; // conta cada comparação feita
                // se o nome for menor, atualiza o índice do menor
                if (games[j].name.compareToIgnoreCase(games[min].name) < 0) min = j;
            }
            // troca as posições caso tenha encontrado um menor
            PesquisaBinaria tmp = games[i];
            games[i] = games[min];
            games[min] = tmp;
        }
    }

    // cria o arquivo de log com matrícula, tempo e número de comparações
    private static void createLog(String matricula, int[] comparisons, int time) {
        try (FileWriter fw = new FileWriter(matricula + "_binaria.txt")) {
            fw.write(matricula + "\t" + time + "\t" + comparisons[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // método principal que controla a leitura e execução da busca
    public static void read() throws Exception {
        Scanner in = new Scanner(System.in);
        PesquisaBinaria[] allGames = loadGames("/tmp/games.csv"); // carrega CSV completo

        PesquisaBinaria[] selected = insertGames(in, allGames); // insere jogos informados
        int[] comparisons = {0}; // contador de comparações
        sort(selected, selected.length, comparisons); // ordena os jogos pelo nome

        int start = (int) System.currentTimeMillis(); // marca o tempo inicial

        String search = in.nextLine().trim(); // lê o primeiro nome para busca
        while (!search.equals("FIM")) {
            // realiza busca binária e exibe resultado
            boolean found = binarySearch(search, selected, selected.length, comparisons);
            System.out.println(found ? " SIM" : " NAO");
            search = in.nextLine().trim();
        }

        int time = (int) (System.currentTimeMillis() - start); // calcula tempo total
        createLog("869624", comparisons, time); // cria arquivo de log
        in.close();
    }

    // executa o programa
    public static void main(String[] args) throws Exception {
        read();
    }
}
