import java.io.*;
import java.util.Scanner;

class Game {

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

    public Game() {
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

    // Lê uma linha do CSV e preenche os campos do jogo
    public void readLine(String line) {
        String[] fields = splitCsvFields(line);  // separa os 14 campos manualmente
        assignValues(fields);                    // atribui valores convertidos
    }

    // Faz a separação manual dos campos do CSV
    private String[] splitCsvFields(String line) {
        String[] fields = new String[14];
        int fieldIndex = 0;
        String current = "";
        boolean insideQuotes = false;
        boolean insideBrackets = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                insideQuotes = !insideQuotes;
            } else if (c == '[') {
                insideBrackets = true;
                current += c;
            } else if (c == ']') {
                insideBrackets = false;
                current += c;
            } else if (c == ',' && !insideQuotes && !insideBrackets) {
                fields[fieldIndex] = current;
                fieldIndex++;
                current = "";
            } else {
                current += c;
            }
        }

        fields[fieldIndex] = current;
        return fields; // retorna todos os campos já separados
    }

    // Atribui valores convertidos a cada atributo
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

    private int parseInt(String s) {
        if (isEmpty(s)) {
            return 0; // retorna 0 se o campo estiver vazio
        }
        try {
            return Integer.parseInt(s.replaceAll("[^0-9]", "")); // retorna valor numérico limpo
        } catch (Exception e) {
            return 0; // retorna 0 se houver erro na conversão
        }
    }

    private int parseIntOrDefault(String s, int def) {
        if (isEmpty(s)) {
            return def; // retorna valor padrão se vazio
        }
        try {
            return Integer.parseInt(s.replaceAll("[^0-9]", "")); // retorna valor numérico limpo
        } catch (Exception e) {
            return def; // retorna valor padrão se falhar
        }
    }

    private float parsePrice(String s) {
        if (isEmpty(s) || s.equalsIgnoreCase("Free to Play")) {
            return 0.0f; // retorna 0 se for gratuito ou vazio
        }
        try {
            return Float.parseFloat(s.replace(",", ".")); // retorna valor float do preço
        } catch (Exception e) {
            return 0.0f; // retorna 0 se falhar
        }
    }

    private float parseUserScore(String s) {
        if (isEmpty(s) || s.equalsIgnoreCase("tbd")) {
            return -1.0f; // retorna -1 se vazio ou “tbd”
        }
        try {
            return Float.parseFloat(s.replace(",", ".")); // retorna nota do usuário
        } catch (Exception e) {
            return -1.0f; // retorna -1 se erro
        }
    }

    private String[] parseList(String s) {
        if (isEmpty(s)) {
            return new String[0]; // retorna lista vazia
        }

        s = s.replaceAll("[\\[\\]'\"]", "");

        if (s.equals("")) {
            return new String[0]; // retorna lista vazia se conteúdo for vazio
        }

        String[] parts = s.split(",");
        trimArray(parts);
        return parts; // retorna lista já formatada
    }

    private String normalizeDate(String s) {
        if (isEmpty(s)) {
            return "01/01/2000"; // retorna data padrão se vazia
        }

        s = s.replace("\"", "");
        String[] parts = s.split(" ");
        return formatDate(parts); // retorna data formatada corretamente
    }

    private String formatDate(String[] parts) {
        try {
            if (parts.length == 3) {
                String day = parts[1].replace(",", "");
                String month = getMonth(parts[0]);
                String year = parts[2];
                return String.format("%02d/%02d/%s",
                        Integer.parseInt(day), Integer.parseInt(month), year); // retorna data completa
            } else if (parts.length == 2) {
                String month = getMonth(parts[0]);
                String year = parts[1];
                return String.format("01/%02d/%s",
                        Integer.parseInt(month), year); // retorna data sem dia (assume 01)
            } else {
                return "01/01/2000"; // retorna padrão se formato inválido
            }
        } catch (Exception e) {
            return "01/01/2000"; // retorna padrão se erro
        }
    }

    private String getMonth(String month) {
        month = month.toLowerCase();
        String prefix = month.substring(0, 3);

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
        return "1"; // retorna janeiro por padrão
    }

    private boolean isEmpty(String s) {
        if (s == null) {
            return true; // retorna verdadeiro se nulo
        }
        if (s.trim().equals("")) {
            return true; // retorna verdadeiro se só tiver espaços
        }
        return false; // retorna falso caso contrário
    }

    private void trimArray(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].trim();
        }
    }

    public String toString() {
        String output = "=> " + id + " ## " + name + " ## " + releaseDate + " ## " +
                estimatedOwners + " ## " + price + " ## ";

        output += formatList(supportedLanguages);
        output += " ## " + metacriticScore + " ## " + userScore + " ## " + achievements + " ## ";
        output += formatList(publishers);
        output += " ## " + formatList(developers);
        output += " ## " + formatList(categories);
        output += " ## " + formatList(genres);
        output += " ## " + formatList(tags);
        output += " ##";

        return output; // retorna string formatada completa do jogo
    }

    private String formatList(String[] arr) {
        String result = "[";
        for (int i = 0; i < arr.length; i++) {
            result += arr[i].trim();
            if (i < arr.length - 1) {
                result += ", ";
            }
        }
        result += "]";
        return result; // retorna lista formatada com colchetes
    }

    public static void read() throws Exception {
        int[] ids = readInputIds();                 // lê IDs digitados
        Game[] games = loadGames("/tmp/games.csv"); // carrega dados do CSV
        printSelectedGames(ids, games);             // imprime os jogos encontrados
    }

    private static int[] readInputIds() {
        Scanner in = new Scanner(System.in);
        int[] ids = new int[500];
        int count = 0;

        String line = in.nextLine();
        while (!line.startsWith("FIM")) {
            ids[count] = Integer.parseInt(line);
            count++;
            line = in.nextLine();
        }
        in.close();

        int[] result = new int[count];
        System.arraycopy(ids, 0, result, 0, count);
        return result; // retorna apenas IDs válidos lidos
    }

    private static Game[] loadGames(String path) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = br.readLine(); // ignora cabeçalho
        Game[] games = new Game[20000];
        int total = 0;

        while ((line = br.readLine()) != null) {
            Game g = new Game();
            g.readLine(line);
            games[total] = g;
            total++;
        }

        br.close();

        Game[] result = new Game[total];
        System.arraycopy(games, 0, result, 0, total);
        return result; // retorna apenas os jogos carregados
    }
    
    // Percorre todos os IDs informados na entrada e imprime os jogos correspondentes
    private static void printSelectedGames(int[] ids, Game[] games) {
        for (int i = 0; i < ids.length; i++) {
            // Busca o jogo com o ID atual dentro do array de jogos
            Game found = findGameById(ids[i], games);

            // Se o jogo for encontrado, imprime na tela
            if (found != null) {
                System.out.println(found);
            }
        }
    }

    // Procura um jogo pelo ID dentro do array e retorna o objeto encontrado
    private static Game findGameById(int id, Game[] games) {
        for (int i = 0; i < games.length; i++) {
            if (games[i].id == id) {
                return games[i]; // retorna o jogo correspondente
            }
        }
        return null; // retorna null se não encontrar
    }

    public static void main(String[] args) throws Exception {
        read(); // inicia o processo de leitura
    }
}
