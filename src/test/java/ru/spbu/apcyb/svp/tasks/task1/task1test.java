package ru.spbu.apcyb.svp.tasks.task1;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Тесты для 1 таска.
 */
class task1test{

    @Test
    void inputSumTest_normalNumber() {
        System.setIn(new ByteArrayInputStream("5\n".getBytes()));
        assertEquals(5L, task1.getSum());
    }

    @Test
    void inputSumTest_zeroNumber() {
        System.setIn(new ByteArrayInputStream("0\n".getBytes()));
        ArithmeticException ex = assertThrows(ArithmeticException.class, task1::getSum);
        assertEquals("Вы ввели нулевую или отрицательную сумму", ex.getMessage());
    }

    @Test
    void inputSumTest_negativeNumber() {
        System.setIn(new ByteArrayInputStream("-105\n".getBytes()));
        ArithmeticException ex = assertThrows(ArithmeticException.class, task1::getSum);
        assertEquals("Вы ввели нулевую или отрицательную сумму", ex.getMessage());
    }

    @Test
    void inputSumTest_empty() {
        System.setIn(new ByteArrayInputStream("".getBytes()));
        NoSuchElementException ex = assertThrows(NoSuchElementException.class, task1::getSum);
        assertEquals("Вы не ввели сумму", ex.getMessage());
    }

    @Test
    void inputSumTest_notNumber() {
        System.setIn(new ByteArrayInputStream("asd".getBytes()));
        NoSuchElementException ex = assertThrows(NoSuchElementException.class, task1::getSum);
        assertEquals("Сумма введена неправильно!", ex.getMessage());
    }

    @Test
    void inputDenominationsTest_normalNumbers() {
        System.setIn(new ByteArrayInputStream("3 2\n".getBytes()));
        assertEquals(Arrays.asList(3L, 2L), task1.inputDenominations());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0, Среди введенных номиналов есть нулевые или отрицательные",
            "-1 1, Среди введенных номиналов есть нулевые или отрицательные",
            "a b, Вы неправильно ввели номиналы"

    })
    void inputDenominationsTest_NumberFormatExceptionThrow(String denomination, String expected) {
        System.setIn(new ByteArrayInputStream(denomination.getBytes()));
        NumberFormatException ex = assertThrows(NumberFormatException.class, task1::inputDenominations);
        assertEquals(expected, ex.getMessage());
    }

    @Test
    void inputDenominationsTest_noNumbers() {
        System.setIn(new ByteArrayInputStream("\n".getBytes()));
        NullPointerException ex = assertThrows(NullPointerException.class, task1::inputDenominations);
        assertEquals("Ничего не было введено", ex.getMessage());
    }

    @Test
    void inputDenominationsTest_empty() {
        System.setIn(new ByteArrayInputStream("".getBytes()));
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                task1::inputDenominations);
        assertEquals("Ошибка ввода", ex.getMessage());
    }

    @Test
    void constructionOfCombinationTest_empty() {
        assertEquals(new ArrayList<>(), task1.constructionOfCombination(new ArrayList<>(), null));
    }

    @Test
    void constructionOfCombinationTest_2_2_1_1_1() {
        assertEquals(Arrays.asList(2L, 2L, 1L, 1L, 1L),
                task1.constructionOfCombination(Arrays.asList(2L, 1L), new long[]{2L, 3L}));
    }

    @Test
    void listOfAllWaysTest_5__3_2() {
        List<List<Long>> expected = new ArrayList<>();
        expected.add(Arrays.asList(3L, 2L));
        assertEquals(expected, task1.listCombinations(5L, Arrays.asList(3L, 2L)));
    }

    @Test
    void listOfAllWaysTest_4__2_1() {
        List<List<Long>> expected = new ArrayList<>();
        expected.add(Arrays.asList(2L, 2L));
        expected.add(Arrays.asList(2L, 1L, 1L));
        expected.add(Arrays.asList(1L, 1L, 1L, 1L));
        assertEquals(expected, task1.listCombinations(4L, Arrays.asList(2L, 1L)));
    }

    @Test
    void listOfAllWaysTest_1000__1() {
        List<List<Long>> expected = new ArrayList<>();
        expected.add(task1.constructionOfCombination(List.of(1L), new long[]{1000L}));
        assertEquals(expected, task1.listCombinations(1000L, List.of(1L)));
    }

    @Test
    void listOfAllWaysTest_1000__500_1() {
        List<List<Long>> expected = new ArrayList<>();
        expected.add(task1.constructionOfCombination(Arrays.asList(500L, 1L), new long[]{2L, 0L}));
        expected.add(task1.constructionOfCombination(Arrays.asList(500L, 1L), new long[]{1L, 500L}));
        expected.add(task1.constructionOfCombination(Arrays.asList(500L, 1L), new long[]{0L, 1000L}));
        assertEquals(expected, task1.listCombinations(1000L, Arrays.asList(500L, 1L)));
    }

    @Test
    void allWaysTest_5__3_2() {
        System.setIn(new ByteArrayInputStream("5\n3 2\n".getBytes()));
        assertEquals(1, task1.Combinations(5, Arrays.asList(3L, 2L)));
    }

    @Test
    void allWaysTest_4__2_1() {
        System.setIn(new ByteArrayInputStream("5\n3 2\n".getBytes()));
        assertEquals(3, task1.Combinations(4, Arrays.asList(2L, 1L)));
    }

    @Test
    void allWaysTest_1000__1() {
        System.setIn(new ByteArrayInputStream("5\n3 2\n".getBytes()));
        assertEquals(1, task1.Combinations(1000, List.of(1L)));
    }

    @Test
    void allWaysTest_1000__500_1() {
        System.setIn(new ByteArrayInputStream("5\n3 2\n".getBytes()));
        assertEquals(3, task1.Combinations(1000, Arrays.asList(500L, 1L)));
    }



}