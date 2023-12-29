package ru.spbu.apcyb.svp.tasks.task1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class task1 {

    static Logger logger = Logger.getLogger(task1.class.getName());

    /**
     * Функция main.
     */
    public static void main(String[] args) {
        logger.info("Введите сумму, которую необходимо разменять:");
        final long sum = getSum();

        logger.info("Введите номиналы денежных единиц размена (в одну строку через пробел):");
        List<Long> denominations = inputDenominations();

        long count = Combinations(sum, denominations);

        logger.log(Level.INFO, "Всевозможных комбинаций размена - {0}", count);
    }

    /**
     * Получение суммы, которую надо разменять, от пользователя.
     */
    public static long getSum() {
        Scanner inp = new Scanner(System.in);
        long summa;
        try {
            summa = inp.nextLong();
            inp.nextLine();
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Сумма введена неправильно!");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Вы не ввели сумму");
        }
        if (summa <= 0) {
            throw new ArithmeticException("Вы ввели нулевую или отрицательную сумму");
        }
        return summa;
    }

    /**
     * Получение номиналов денег от пользователя. Возвращает отсортированный массив номиналов.
     */
    public static List<Long> inputDenominations() {
        Scanner inp = new Scanner(System.in);
        String stringOfDenominations;
        try {
            stringOfDenominations = inp.nextLine();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Ошибка ввода");
        }

        /*Проверка, что строка не пуста*/
        if (Objects.equals(stringOfDenominations, "")) {
            throw new NullPointerException("Ничего не было введено");
        }

        /*Получение отсортированного в обратном порядке списка номиналов*/
        List<Long> listOfDenominations;
        try {
            listOfDenominations = Arrays.stream(stringOfDenominations.trim().split(" "))
                    .map(Long::parseLong).sorted(Comparator.reverseOrder()).toList();
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Вы неправильно ввели номиналы");
        }

        /*Убираем задублированность номиналов*/
        listOfDenominations = listOfDenominations.stream().distinct().toList();

        if (listOfDenominations.get(listOfDenominations.size() - 1) <= 0) {
            throw new NumberFormatException("Среди введенных номиналов есть нулевые или отрицательные");
        }

        return listOfDenominations;
    }

    /**
     * Оберточная функция для listCombinations.
     */
    public static List<List<Long>> listCombinations(long sum, List<Long> denominations) {
        return listCombinations(sum, denominations, new long[denominations.size()], 0, new ArrayList<>());
    }

    /**
     * Вычисляет все комбинации размена суммы. Возвращает список всех комбинаций
     * (Используется в тестах для проверки правильности вычисления комбинаций)
     *
     * @param sum                         сумма, которую нужно разменять
     * @param denominations               массив номиналов денежных единиц
     * @param distributionOfDenominations массив, хранящий количество д.е. каждого номинала в
     *                                    комбинации (изначально - нулевой)
     * @param index                       индекс, который указывает, с каким номиналом нужно работать
     *                                    (используется в рекурсии)
     * @param combinations                список всех комбинаций (заполняется в рекурсии)
     */
    private static List<List<Long>> listCombinations(long sum, List<Long> denominations,
                                                  long[] distributionOfDenominations,
                                                  int index, List<List<Long>> combinations) {
        int n = denominations.size();
        long value; // для записи возможных промежуточных значений

        if (index == n - 1) {
            if (sum % denominations.get(index) == 0) {
                distributionOfDenominations[index] = sum / denominations.get(index);
                List<Long> combination = constructionOfCombination(denominations,
                        distributionOfDenominations);
                combinations.add(combination);
            }
        } else {
            long l = sum / denominations.get(index);
            for (long i = l; i >= 0; --i) {
                value = sum - i * denominations.get(index);
                distributionOfDenominations[index] = i;
                listCombinations(value, denominations, distributionOfDenominations, index + 1, combinations);
            }
        }
        return combinations;
    }

    /**
     * Оберточная функция для функции allWays.
     */
    public static long Combinations(long sum, List<Long> denominations) {
        return Combinations(sum, denominations, new long[denominations.size()], 0);
    }

    /**
     * Вычисляет все комбинации размена суммы и выводит их. Возвращает число всех комбинаций
     *
     * @param sum                         сумма, которую нужно разменять
     * @param denominations               массив номиналов
     * @param distributionOfDenominations массив, хранящий количество д.е. каждого номинала в
     *                                    комбинации (изначально - нулевой)
     * @param index                       индекс, указывающий, с каким номиналом нужно работать
     *                                    (используется в рекурсии)
     */
    private static long Combinations(long sum, List<Long> denominations,
                                long[] distributionOfDenominations,
                                int index) {
        int n = denominations.size();
        long value; // для записи всевозможных промежуточных значений
        long count = 0; // cчетчик комбинаций

        if (index == n - 1) {
            if (sum % denominations.get(index) == 0) {
                count = 1;
                distributionOfDenominations[index] = sum / denominations.get(index);
                printCombination(denominations, distributionOfDenominations);
            }
        } else {
            long l = sum / denominations.get(index);
            for (long i = l; i >= 0; --i) {
                value = sum - i * denominations.get(index);
                distributionOfDenominations[index] = i;
                count += Combinations(value, denominations, distributionOfDenominations, index + 1);
            }
        }
        return count;
    }

    /**
     * Вывод комбинации.
     */
    public static void printCombination(List<Long> denominations,
                                        long[] distributionOfDenominations) {
        int n = denominations.size();
        logger.info("Begin of combination");
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < distributionOfDenominations[i]; ++j) {
                logger.log(Level.INFO, "{0}", denominations.get(i));
            }
        }
        logger.info("End of combination");
    }

    /**
     * Создает кобинацию в виде списка на основе массива номиналов и массива, содержащим числа монет
     * каждого номинала.
     */
    public static List<Long> constructionOfCombination(List<Long> denominations,
                                                       long[] distributionOfDenominations) {
        int n = denominations.size();
        List<Long> combination = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < distributionOfDenominations[i]; ++j) {
                combination.add(denominations.get(i));
            }
        }
        return combination;
    }
}
