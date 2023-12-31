package ru.spbu.apcyb.svp.tasks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Задание 5. */
public class Task5{

    static Logger logger = Logger.getLogger(Task5.class.getName());

    /**
     * @param arg содержит 2 строки: 1) Путь до input файла
     *                                2) Путь до директории, куда будет проводиться запись
     */
    public static void main(String[] arg) throws IOException {
        Argchecking(arg);
        Path inputFile = Path.of(arg[0]);
        Map<String, Long> statistics = Wordscount(inputFile);
        Path directory = Path.of(arg[1]);
        if (!Files.exists(directory)) {
            Files.createDirectory(directory);
        }
        StatisticsOfWords(statistics, Path.of(arg[1] + "/count.txt"));
        WordsToManyFiles(statistics, arg[1] + "/Words/");
    }

    /** Считает сколько раз встречается каждое слово в выбранном файле */
    public static Map<String, Long> Wordscount(Path inPath) throws IOException {
        try (Stream<String> stream = Files.lines(inPath)) {
            return stream.flatMap(line -> Arrays.stream(line.split(" ")))
                    .map(word -> word.replaceAll("[\\p{Punct}\\d–]", ""))
                    .filter(word -> !word.isEmpty())
                    .map(String::toLowerCase)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        } catch (IOException e) {
            throw new IOException("Ошибка при чтении файла ввода\n" + e.getMessage());
        }
    }

    /** Записывает слово и количесво его вхождений в указанный файл. */
    public static void StatisticsOfWords(Map<String, Long> stat, Path outPath)
            throws IOException {
        try (BufferedWriter put = Files.newBufferedWriter(outPath)) {
            for (var entry : stat.entrySet()) {
                put.write(entry.getKey() + " " + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            throw new IOException("Ошибка записи в файл");
        }
    }

    /** Проверяет, что введены правильные аргументы, указанный файл ввода существует и является именно файлом.*/
    public static void Argchecking(String[] str) throws IOException {
        if (str.length != 2) {
            throw new IllegalArgumentException("Неверное количество аргументов");
        }

        if (!Files.exists(Path.of(str[0]))) {
            throw new FileNotFoundException("Указанный файл ввода не найден");
        }

        if (!new File(str[0]).isFile()) {
            throw new FileSystemException("Указанный объект не является файлом");
        }
    }

    /** Записывает все слова в указанном количестве в одноименные файлы. */
    public static void WordsToManyFiles(Map<String, Long> stat, String outPath)
            throws IOException {
        ExecutorService executorS = Executors.newFixedThreadPool(8);
        try {
            Path dir = Path.of(outPath);
            if (!Files.exists(dir)) {
                Files.createDirectory(dir);
            }

            stat.forEach((word, count) -> CompletableFuture.runAsync(
                    () -> WordToOneFile(word, count, outPath), executorS).join());
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            executorS.shutdownNow();
        }
    }

    /** Записывает указанное слово в указанном количестве в указанный файл. */
    public static void WordToOneFile(String word, Long count, String outPath) {
        Path outFile = Path.of(outPath + word + ".txt");
        try (BufferedWriter put = Files.newBufferedWriter(outFile)) {
            for (long i = 0; i < count; ++i) {
                put.write(word + " ");
            }
        } catch (IOException e) {
            logger.info("Ошибка записи слова \"" + word + "\" в файл");
        }
    }

}
