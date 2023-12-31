package ru.spbu.apcyb.svp.tasks;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class Task5Test {

    @Test
    void invalidInputExceptionTest() {
        String[] args = {"..//"};
        assertThrows(IllegalArgumentException.class, () -> Task5.main(args));
    }

    @Test
    void FileNotFoundExceptionTest() {
        String[] args = {"non-existent file.txt", "recordingFile.txt"};
        assertThrows(FileNotFoundException.class, () -> Task5.main(args));
    }

    @Test
    void FileSystemExceptionTest() {
        String[] args = {"..//", "recordingFile.txt"};
        FileSystemException ex = assertThrows(FileSystemException.class, () -> Task5.main(args));
        assertEquals("Указанный объект не является файлом", ex.getMessage());
    }

    @Test
    void testOnSmallInputFile() throws IOException {
        String inputFile = "src/test/Task5FilesForTest/SmallInputFile.txt";
        String outputFile = "src/test/Task5FilesForTest/DirectoryForSmallInputFile";
        String[] args = {inputFile, outputFile};

        Task5.main(args);

        assertEquals(-1, Files.mismatch(
                Path.of("src/test/Task5FilesForTest/ExpectedDirectoryForSmallInputFile/count.txt"),
                Path.of("src/test/Task5FilesForTest/DirectoryForSmallInputFile/count.txt")
        ));

        assertEquals(-1, Files.mismatch(
                Path.of("src/test/Task5FilesForTest/ExpectedDirectoryForSmallInputFile/Words/bar.txt"),
                Path.of("src/test/Task5FilesForTest/DirectoryForSmallInputFile/Words/bar.txt")
        ));

        assertEquals(-1, Files.mismatch(
                Path.of("src/test/Task5FilesForTest/ExpectedDirectoryForSmallInputFile/Words/foo.txt"),
                Path.of("src/test/Task5FilesForTest/DirectoryForSmallInputFile/Words/foo.txt")
        ));

        assertEquals(-1, Files.mismatch(
                Path.of("src/test/Task5FilesForTest/ExpectedDirectoryForSmallInputFile/Words/baz.txt"),
                Path.of("src/test/Task5FilesForTest/DirectoryForSmallInputFile/Words/baz.txt")
        ));
    }

    @Test
    void testOnLargeInputFile() throws IOException {
        String inputFile = "src/test/Task5FilesForTest/LargeInputFile.txt";
        String outputFile = "src/test/Task5FilesForTest/DirectoryForLargeInputFile";
        String[] args = {inputFile, outputFile};

        assertDoesNotThrow(() -> Task5.main(args));
    }
}
