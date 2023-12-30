package ru.spbu.apcyb.svp.tasks.Task4;

import java.io.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.Double.parseDouble;
import static java.lang.String.format;

/**
 * Задание 4.
 */
class Task4{

    public static void main(String[] str) throws IOException, ExecutionException, InterruptedException {
        int size = 10000;
        genNumbersFile("num.txt", size);
        oneThreadTan("num.txt", "num_tan.txt", size);
        multithreadedTan("num.txt", "num_tan.txt", size, 8);
    }
    public static void genNumbersFile(String fWName, int size) throws IOException {
        try (FileWriter fW = new FileWriter(fWName, false)) {
            SecureRandom rand = new SecureRandom();
            for (int i = 0; i < size; i++) {
                fW.write(Float.toString(rand.nextFloat(-1000, 1000)) + " ");
            }
            fW.flush();
        }
    }

    public static boolean oneThreadTan(String fRName, String fWName, int size) throws IOException {
        long start;
        try (FileReader fR = new FileReader(fRName)) {
            try (FileWriter fW = new FileWriter(fWName, false)) {
                fW.write("Количество вычисленных значений: " + size + "\n");

                BufferedReader fBReader = new BufferedReader(fR);
                StringBuilder sb = new StringBuilder("").append(fBReader.readLine());
                String[] tmp = sb.toString().split(" ");
                start = System.nanoTime();
                for (String integer : tmp) {
                    final double i = parseDouble(integer);
                    fW.write(Math.tan(i) + " ");
                }
                fW.flush();
            }
        }
        System.out.println(format("One thread Executed by %d ns, size : %d",
                (System.nanoTime() - start), size));
        return true;
    }

    public static boolean multithreadedTan(String fRName, String fWName, int size, int nThreads) throws IOException, ExecutionException, InterruptedException {
        try (FileReader fR = new FileReader(fRName)) {
            try (FileWriter fW = new FileWriter(fWName, false)) {
                fW.write("Количество вычисленных значений: " + size + "\n");

                StringBuilder sb;
                try (BufferedReader fBReader = new BufferedReader(fR)) {
                    sb = new StringBuilder("").append(fBReader.readLine());
                }
                String[] tmp = sb.toString().split(" ");

                ExecutorService threadPool = null;
                try {
                    threadPool = Executors.newFixedThreadPool(nThreads);
                    long start = System.nanoTime();

                    List<CompletableFuture<Double>> futures = new ArrayList<>();
                    for (String integer : tmp) {
                        final double i = parseDouble(integer);
                        futures.add(CompletableFuture.supplyAsync(() -> Math.tan(i), threadPool));
                    }
                    CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
                    for (Future<Double> future : futures) {
                        fW.write(String.valueOf(future.get()) + " ");
                    }
                    fW.flush();
                    System.out.println(format("Multi thread Executed by %d ns, size : %d",
                            (System.nanoTime() - start), size));
                } finally {
                    if (threadPool != null){
                        threadPool.shutdown();
                    }
                }

            }
        }
        return true;
    }

}
