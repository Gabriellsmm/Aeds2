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

  // Lê uma linha do CSV e separa os campos, preenchendo os atributos do jogo
  public void readLine(String line) {
    String[] fields = splitCsvFields(line);
    assignValues(fields);
  }

  // Separa manualmente os 14 campos do CSV considerando aspas e colchetes
  private String[] splitCsvFields(String line) {
    String[] fields = new String[14];
    int fieldIndex = 0;
    String current = "";
    boolean insideQuotes = false;
    boolean insideBrackets = false;

    for (int i = 0; i < line.length(); i++) {
      char c = line.charAt(i);

      if (c == '"') insideQuotes = !insideQuotes;
      else if (c == '[') { insideBrackets = true; current += c; }
      else if (c == ']') { insideBrackets = false; current += c; }
      else if (c == ',' && !insideQuotes && !insideBrackets) {
        fields[fieldIndex++] = current;
        current = "";
      } else current += c;
    }

    fields[fieldIndex] = current;
    return fields;
  }

  // Atribui os valores convertidos a cada atributo do objeto
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

  // Converte uma string para inteiro, ignorando caracteres não numéricos
  private int parseInt(String s) {
    if (isEmpty(s)) return 0;
    try { return Integer.parseInt(s.replaceAll("[^0-9]", "")); }
    catch (Exception e) { return 0; }
  }

  // Converte string para inteiro, retornando um valor padrão em caso de erro
  private int parseIntOrDefault(String s, int def) {
    if (isEmpty(s)) return def;
    try { return Integer.parseInt(s.replaceAll("[^0-9]", "")); }
    catch (Exception e) { return def; }
  }

  // Converte o campo de preço; se for “Free to Play” ou vazio, retorna 0.0
  private float parsePrice(String s) {
    if (isEmpty(s) || s.equalsIgnoreCase("Free to Play")) return 0.0f;
    try { return Float.parseFloat(s.replace(",", ".")); }
    catch (Exception e) { return 0.0f; }
  }

  // Converte o campo de nota dos usuários; se for vazio ou “tbd”, retorna -1.0
  private float parseUserScore(String s) {
    if (isEmpty(s) || s.equalsIgnoreCase("tbd")) return -1.0f;
    try { return Float.parseFloat(s.replace(",", ".")); }
    catch (Exception e) { return -1.0f; }
  }

  // Transforma uma string com colchetes em um array de strings
  private String[] parseList(String s) {
    if (isEmpty(s)) return new String[0];
    s = s.replaceAll("[\\[\\]'\"]", "");
    if (s.equals("")) return new String[0];
    String[] parts = s.split(",");
    trimArray(parts);
    return parts;
  }

  // Normaliza o formato da data para dd/mm/aaaa
  private String normalizeDate(String s) {
    if (isEmpty(s)) return "01/01/2000";
    s = s.replace("\"", "");
    String[] parts = s.split(" ");
    return formatDate(parts);
  }

  // Monta a data com base no número do mês e nas partes da string
  private String formatDate(String[] parts) {
    try {
      if (parts.length == 3) {
        String day = parts[1].replace(",", "");
        String month = getMonth(parts[0]);
        String year = parts[2];
        return String.format("%02d/%02d/%s", Integer.parseInt(day), Integer.parseInt(month), year);
      } else if (parts.length == 2) {
        String month = getMonth(parts[0]);
        String year = parts[1];
        return String.format("01/%02d/%s", Integer.parseInt(month), year);
      } else return "01/01/2000";
    } catch (Exception e) {
      return "01/01/2000";
    }
  }

  // Converte o nome do mês para o número correspondente
  private String getMonth(String month) {
    month = month.toLowerCase();
    switch (month.substring(0, 3)) {
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

  // Verifica se a string é nula ou vazia
  private boolean isEmpty(String s) {
    return s == null || s.trim().equals("");
  }

  // Remove espaços extras de cada elemento do array
  private void trimArray(String[] arr) {
    for (int i = 0; i < arr.length; i++) arr[i] = arr[i].trim();
  }

  // Monta a saída formatada no padrão solicitado no enunciado
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
    return output;
  }

  // Formata arrays de strings no estilo “[A, B, C]”
  private String formatList(String[] arr) {
    String result = "[";
    for (int i = 0; i < arr.length; i++) {
      result += arr[i].trim();
      if (i < arr.length - 1) result += ", ";
    }
    result += "]";
    return result;
  }

  // Controla todo o processo de leitura, carga e impressão dos jogos
  public static void read() throws Exception {
    int[] ids = readInputIds();
    Game[] games = loadGames("/tmp/games.csv");
    printSelectedGames(ids, games);
  }

  // Lê os IDs fornecidos na entrada até encontrar “FIM”
  private static int[] readInputIds() {
    Scanner in = new Scanner(System.in);
    int[] ids = new int[500];
    int count = 0;
    String line = in.nextLine();
    while (!line.startsWith("FIM")) {
      ids[count++] = Integer.parseInt(line);
      line = in.nextLine();
    }
    in.close();
    int[] result = new int[count];
    System.arraycopy(ids, 0, result, 0, count);
    return result;
  }

  // Lê o arquivo CSV e cria um array de objetos Game
  private static Game[] loadGames(String path) throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(path));
    String line = br.readLine(); // ignora cabeçalho
    Game[] games = new Game[20000];
    int total = 0;

    while ((line = br.readLine()) != null) {
      Game g = new Game();
      g.readLine(line);
      games[total++] = g;
    }
    br.close();

    Game[] result = new Game[total];
    System.arraycopy(games, 0, result, 0, total);
    return result;
  }

  // Imprime apenas os jogos cujos IDs foram informados na entrada
  private static void printSelectedGames(int[] ids, Game[] games) {
    for (int id : ids) {
      Game found = findGameById(id, games);
      if (found != null) System.out.println(found);
    }
  }

  // Busca um jogo pelo ID dentro do array de jogos
  private static Game findGameById(int id, Game[] games) {
    for (Game g : games) {
      if (g.id == id) return g;
    }
    return null;
  }

  public static void main(String[] args) throws Exception {
    read(); // apenas inicia o processo de leitura
  }
}
