package ru.spbu.apcyb.svp.tasks.Task4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static ru.spbu.apcyb.svp.tasks.Task4.Task4.*;

/**
 * Тесты для задания 4.
 */
public class Task4Test {

    int size;

    @Test
    void isOneNumber() throws IOException, ExecutionException, InterruptedException {
        size = 1;
        genNumbersFile("num.txt", size);
        boolean result1 = multithreadedTan("num.txt", "num_tan.txt", size, 10);
        boolean result2 = oneThreadTan("num.txt", "num_tan2.txt", size);
        Assertions.assertTrue(result1);
        Assertions.assertTrue(result2);
    }

    @Test
    void isOneHundredNumbers() throws IOException, ExecutionException, InterruptedException {
        size = 100;
        genNumbersFile("num.txt", size);
        boolean result1 = multithreadedTan("num.txt", "num_tan.txt", size, 10);
        boolean result2 = oneThreadTan("num.txt", "num_tan2.txt", size);
        Assertions.assertTrue(result1);
        Assertions.assertTrue(result2);
    }

    @Test
    void isOneMillionNumbers() throws IOException, ExecutionException, InterruptedException {
        size = 1000000;
        genNumbersFile("num.txt", size);
        boolean result1 = multithreadedTan("num.txt", "num_tan.txt", size, 10);
        boolean result2 = oneThreadTan("num.txt", "num_tan2.txt", size);
        Assertions.assertTrue(result1);
        Assertions.assertTrue(result2);
    }

}
