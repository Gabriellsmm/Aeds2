#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>

#define MAX_LINE 4096
#define MAX_FIELD 512
#define MAX_LIST 1024

// Estrutura que representa um jogo com os principais dados do CSV
typedef struct {
    int id;
    char name[MAX_FIELD];
    char releaseDate[MAX_FIELD];
    int estimateOwners;
    float price;
    char supportedLanguages[MAX_LIST];
    int metacriticScore;
    float userScore;
    int achievements;
    char publishers[MAX_LIST];
    char developers[MAX_LIST];
    char categories[MAX_LIST];
    char genres[MAX_LIST];
    char tags[MAX_LIST];
} Game;

// Remove espaços em branco do início e do final da string
void trim(char *str) {
    int start = 0;
    int end = strlen(str) - 1;

    // Avança até o primeiro caractere não vazio
    while (isspace((unsigned char)str[start])) start++;

    // Regride até o último caractere não vazio
    while (end >= start && isspace((unsigned char)str[end])) end--;

    // Move a parte útil da string para o início
    memmove(str, str + start, end - start + 1);

    // Adiciona o terminador de string '\0'
    str[end - start + 1] = '\0';
}

// Remove aspas simples e duplas de uma string
void removeQuotes(char *str) {
    int j = 0;
    for (int i = 0; str[i] != '\0'; i++) {
        // Apenas copia caracteres que não são aspas
        if (str[i] != '"' && str[i] != '\'')
            str[j++] = str[i];
    }
    str[j] = '\0';
}

// Adiciona espaço após cada vírgula, para melhor leitura em listas
void normalizeCommas(char *str) {
    char temp[MAX_LIST];
    int j = 0;

    for (int i = 0; str[i] != '\0'; i++) {
        temp[j++] = str[i];
        if (str[i] == ',')
            temp[j++] = ' '; // adiciona espaço após a vírgula
    }

    temp[j] = '\0';
    strcpy(str, temp);
}

// Limpa campos de lista do CSV, removendo aspas e ajustando vírgulas
void cleanList(char *str) {
    removeQuotes(str);
    normalizeCommas(str);
    trim(str);
}

// Converte abreviações de mês (ex: "Aug") para número correspondente
int getMonth(const char *abbr) {
    const char *months[] = {
        "jan","feb","mar","apr","may","jun",
        "jul","aug","sep","oct","nov","dec"
    };

    for (int i = 0; i < 12; i++) {
        if (strncasecmp(abbr, months[i], 3) == 0)
            return i + 1;
    }

    // Retorna 1 (janeiro) se não encontrar correspondência
    return 1;
}

// Extrai apenas números de uma string e os converte para inteiro
int extractInt(const char *str) {
    char digits[64] = "";
    int j = 0;

    for (int i = 0; str[i] != '\0' && j < 63; i++) {
        // Copia apenas dígitos numéricos
        if (isdigit((unsigned char)str[i]))
            digits[j++] = str[i];
    }

    digits[j] = '\0';
    return (strlen(digits) > 0) ? atoi(digits) : 0;
}

// Converte texto de preço para float (ex: "19.99" -> 19.99)
// Retorna 0.0 se o texto for "Free" ou vazio
float parseFloat(const char *str) {
    if (!str || strlen(str) == 0) return 0.0f;
    if (strcasestr(str, "Free")) return 0.0f;
    return atof(str);
}

// Converte texto de nota de usuário para float
// Retorna -1.0 se o texto for "tbd" ou vazio (indicando ausência de nota)
float parseUserScore(const char *str) {
    if (!str || strlen(str) == 0) return -1.0f;
    if (strcasecmp(str, "tbd") == 0) return -1.0f;
    return atof(str);
}

// Converte datas do formato textual ("Aug 13, 2018") para "13/08/2018"
void normalizeDate(char *dest, const char *src) {
    if (!src || strlen(src) == 0) {
        // Se o campo estiver vazio, define uma data padrão
        strcpy(dest, "01/01/0001");
        return;
    }

    char month[16], day[16], year[16];
    char copy[MAX_FIELD];

    // Cria uma cópia da string para manipulação
    strncpy(copy, src, MAX_FIELD - 1);
    copy[MAX_FIELD - 1] = '\0';

    trim(copy);
    removeQuotes(copy);

    // Substitui vírgulas por espaços para facilitar a leitura com sscanf
    for (int i = 0; copy[i]; i++)
        if (copy[i] == ',') copy[i] = ' ';

    // Divide a data em partes: mês, dia e ano
    int n = sscanf(copy, "%s %s %s", month, day, year);

    if (n == 3)
        sprintf(dest, "%02d/%02d/%s", atoi(day), getMonth(month), year);
    else if (n == 2)
        sprintf(dest, "01/%02d/%s", getMonth(month), day);
    else
        strcpy(dest, "01/01/0001");
}

// Divide uma linha CSV em campos, respeitando aspas que podem conter vírgulas internas
int splitCSV(char *line, char fields[15][MAX_LIST]) {
    bool inQuotes = false;
    int fieldIndex = 0;
    int charIndex = 0;

    for (int i = 0; line[i] != '\0'; i++) {
        char c = line[i];

        if (c == '"') {
            inQuotes = !inQuotes; // alterna o estado entre dentro e fora das aspas
        } else if (c == ',' && !inQuotes) {
            // Quando encontra vírgula fora de aspas, fecha o campo atual
            fields[fieldIndex][charIndex] = '\0';
            trim(fields[fieldIndex]);
            fieldIndex++;
            charIndex = 0;
        } else if (charIndex < MAX_LIST - 1) {
            // Adiciona o caractere ao campo atual
            fields[fieldIndex][charIndex++] = c;
        }
    }

    // Finaliza o último campo
    fields[fieldIndex][charIndex] = '\0';
    trim(fields[fieldIndex]);

    // Retorna a quantidade total de campos lidos
    return fieldIndex + 1;
}

// Preenche a struct Game com os valores lidos do CSV
void fillGameData(Game *g, char fields[15][MAX_LIST]) {
    g->id = extractInt(fields[0]);
    strncpy(g->name, fields[1], MAX_FIELD - 1);
    normalizeDate(g->releaseDate, fields[2]);
    g->estimateOwners = extractInt(fields[3]);
    g->price = parseFloat(fields[4]);

    strncpy(g->supportedLanguages, fields[5], MAX_LIST - 1);
    cleanList(g->supportedLanguages);

    g->metacriticScore = (strlen(fields[6]) > 0) ? extractInt(fields[6]) : -1;
    g->userScore = parseUserScore(fields[7]);
    g->achievements = (strlen(fields[8]) > 0) ? extractInt(fields[8]) : 0;

    strncpy(g->publishers, fields[9], MAX_LIST - 1);
    strncpy(g->developers, fields[10], MAX_LIST - 1);
    strncpy(g->categories, fields[11], MAX_LIST - 1);
    strncpy(g->genres, fields[12], MAX_LIST - 1);
    strncpy(g->tags, fields[13], MAX_LIST - 1);

    cleanList(g->publishers);
    cleanList(g->developers);
    cleanList(g->categories);
    cleanList(g->genres);
    cleanList(g->tags);
}

// Lê o arquivo CSV e procura um jogo pelo ID
bool readGameById(int id, Game *g) {
    FILE *file = fopen("/tmp/games.csv", "r");
    if (!file) return false; // se não conseguir abrir o arquivo, retorna falso

    char line[MAX_LINE];
    fgets(line, sizeof(line), file); // ignora o cabeçalho do CSV
    bool found = false;

    // Percorre todas as linhas até encontrar o ID desejado
    while (fgets(line, sizeof(line), file)) {
        if (atoi(line) == id) {
            // Quando o ID é encontrado, divide os campos e preenche a struct
            char fields[15][MAX_LIST];
            splitCSV(line, fields);
            fillGameData(g, fields);
            found = true;
            break;
        }
    }

    fclose(file);
    return found;
}

// Exibe os dados de um jogo formatados na saída padrão
void printGame(const Game *g) {
    printf("=> %d ## %s ## %s ## %d ## %.2f ## [%s] ## %d ## %.1f ## %d ## [%s] ## [%s] ## [%s] ## [%s] ## [%s] ##\n",
           g->id, g->name, g->releaseDate, g->estimateOwners, g->price,
           g->supportedLanguages, g->metacriticScore, g->userScore, g->achievements,
           g->publishers, g->developers, g->categories, g->genres, g->tags);
}

// Lê IDs digitados pelo usuário, busca e imprime os jogos correspondentes
void processInput() {
    char input[MAX_FIELD];

    // Lê uma linha de entrada até EOF ou "FIM"
    while (fgets(input, sizeof(input), stdin)) {
        trim(input); // remove possíveis quebras de linha

        if (strcmp(input, "FIM") == 0) break;
        if (strlen(input) == 0) continue; // ignora entradas vazias

        int id = atoi(input);
        Game g;

        // Busca e imprime o jogo, se encontrado
        if (readGameById(id, &g))
            printGame(&g);
    }
}

// Função principal que inicia o programa
int main() {
    processInput();
    return 0;
}

