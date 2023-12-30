package ru.spbu.apcyb.svp.tasks.Task3;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;


class Task3Test {

    @Test
    void invalidInputExceptionTest() {
        String[] args = {"..//"};
        assertThrows(IllegalArgumentException.class, () -> Task3.main(args));
    }

    @Test
    void FileNotFoundExceptionTest() {
        String[] args = {"non-existent directory/", "recordingFile.txt"};
        assertThrows(FileNotFoundException.class, () -> Task3.main(args));
    }

    @Test
    void FileSystemExceptionTest() {
        String[] args = {"..//", "..//"};
        assertThrows(FileSystemException.class, () -> Task3.main(args));
    }

    @Test
    void FileAlreadyExistsExceptionTest() {
        String[] args = {"..//", "src/test/answerFileForTask3.txt"};
        assertThrows(FileAlreadyExistsException.class, () -> Task3.main(args));
    }

    @Test
    void normalTest() throws IOException {
        String pathToDirectory = "src/test/Task3FilesForTest/";
        Path recordingFile = Path.of("src/test/recordingFileForTask3.txt");
        String[] args = {pathToDirectory, recordingFile.toString()};
        Task3.main(args);
        boolean equals = true;
        try (BufferedReader recordingReader = new BufferedReader(new FileReader(
                recordingFile.toFile())); BufferedReader answerReader = new BufferedReader(
                new FileReader(Path.of("src/test/answerFileForTask3.txt").toFile()))) {

            String resLine = recordingReader.readLine();
            String ansLine = answerReader.readLine();
            while (resLine != null && ansLine != null) {
                if (!ansLine.equals(resLine)) {
                    equals = false;
                }
                resLine = recordingReader.readLine();
                ansLine = answerReader.readLine();
            }
            if (resLine != null || ansLine != null) {
                equals = false;
            }
        }
        Files.delete(recordingFile);
        assertTrue(equals);
    }
}
