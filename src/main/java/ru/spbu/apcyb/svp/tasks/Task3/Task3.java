package ru.spbu.apcyb.svp.tasks.Task3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Задание 3.
 */
public class Task3{

    private static BufferedWriter buffWriter;


    /**
     * Стартовая функция.
     * Создает файл и записывает в него имена всех папок и файлов
     * директории с помощью recordListOfFilesAndFolders
     *
     * @param str содержит 2 строки: 1) Путь до доректории 2) Путь до файла
     */
    public static void main(String[] str) throws IOException {
        checkArgs(str);

        /* файл, куда будет производиться запись*/
        Path recordFile = Files.createFile(Path.of(str[1]).toAbsolutePath());
        buffWriter = Files.newBufferedWriter(recordFile);
        recordListOfFilesAndFolders(str[0]);
        buffWriter.close();
    }

    /**
     * Проверяет, что введены правильные аргументы, указанная директория существует, 2ым аргументом
     * указан путь ФАЙЛА и файла с указанным путем еще не существует.
     */
    public static void checkArgs(String[] str) throws IOException {
        if (str.length != 2) {
            throw new IllegalArgumentException("Вы ввели неверное количество аргументов");
        }

        if (!Files.exists(Path.of(str[0]))) {
            throw new FileNotFoundException("Директория не найдена");
        }

        if (new File(str[1]).isDirectory()) {
            throw new FileSystemException("Вы указали не файл");
        }

        if (Files.exists(Path.of(str[1]))) {
            throw new FileAlreadyExistsException("Такой файл уже существует");
        }
    }

    /**
     * Оберточная функция для функуии recordListOfFilesAndFolders.
     *
     * @param putt строка, в которой содержится путь к директории
     */
    public static void recordListOfFilesAndFolders(String putt)
            throws IOException {
        recordListOfFilesAndFolders(putt, 0);
    }


    /**
     * Основная исполнительная функция.
     * Получает все папки и файлы из директории и записывает  их имена в целевой файл
     *
     * @param putt  строка, содержащая путь к директории
     * @param index индекс глубины рекурсии (нужен для отслеживания глубины дериктории)
     */
    public static void recordListOfFilesAndFolders(String putt, int index)
            throws IOException {
        File[] files = getFilesOfFolder(putt);
        for (File f : files) {
            indent(index);
            if (f.isDirectory()) {
                buffWriter.write(f.getName() + "\n");
                recordListOfFilesAndFolders(f.getAbsolutePath(), index + 1);
            } else {
                buffWriter.write(f.getName() + "\n");
            }
        }
    }

    /**
     * Получение всех файлов/папок папки на том же уровне глубины.
     */
    private static File[] getFilesOfFolder(String putt) {
        File root = new File(putt);
        return root.listFiles();
    }


    /**
     * Совершение отступа для структурирования папок и файлов на разных уровнях глубины.
     */
    public static void indent(int val) throws IOException {
        for (int i = 0; i < val; ++i) {
            buffWriter.write("  ");
        }
    }
}