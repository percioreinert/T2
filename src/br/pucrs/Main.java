package br.pucrs;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    private static String fileContent;
    private static String[][] matrix;
    private static int[][] numberOfMoves;
    private static Dot[][] from;
    private static int count = 0;
    private static int x = 0;
    private static int y = 0;
    private static int xS = 0;
    private static int yS = 0;

    public static void main(String[] args) throws IOException {
        fileContent = Files.readString(Paths.get("casoenunciado.txt"));
        readFile();
        findMinimumPath(null, new Dot(x, y), 0);
        printMinimumPath();
    }

    private static void findMinimumPath(Dot last, Dot current,  int moves) {
        if (matrix[current.x][current.y].equals("S")) {
            return;
        }
        if (matrix[current.x][current.y].equals("x")) {
            return;
        }
        moves++;

        // verifica se o número de movimentos necessários para se chegar até esse ponto é menor do que o menor até então
        if (last != null) {
            if (numberOfMoves[current.x][current.y] < moves) {
                numberOfMoves[current.x][current.y] = moves;
                from[current.x][current.y] = last;
            }
        }

        // observar as constraints de topo/baixo e lados
        if (current.y + 1 < matrix[0].length) {
            findMinimumPath(current, new Dot(current.x, current.y + 1), moves);
        }
        if (current.y >= 0) {
            findMinimumPath(current, new Dot(current.x, current.y - 1), moves);
        }
        if (current.x + 1 >= matrix.length) {
            findMinimumPath(current, new Dot(0, current.y), moves);
        } else {
            findMinimumPath(current, new Dot(current.x + 1, current.y), moves);
        }
        if (current.x - 1 < 0) {
            findMinimumPath(current, new Dot(matrix.length - 1, current.y), moves);
        } else {
            findMinimumPath(current, new Dot(current.x - 1, current.y), moves);
        }

        // continuar as constraints
        findMinimumPath(current, new Dot(current.x + 1, current.y + 2), moves);
        findMinimumPath(current, new Dot(current.x + 1, current.y - 2), moves);
        findMinimumPath(current, new Dot(current.x - 1, current.y + 2), moves);
        findMinimumPath(current, new Dot(current.x - 1, current.y - 2), moves);
    }

    private static void printMinimumPath() {
        System.out.print("S -> ");

        // xS e yS são os pontos do caracter S
        Dot previous = from[xS][yS];
        while (previous.x != x && previous.y != y) {
            System.out.print(previous.x + " " + previous.y);
            System.out.println(" ");
            previous = from[previous.x][previous.y];
        }
    }

    private static void readFile() {
        Scanner reader = new Scanner(new StringReader(fileContent));

        String line;
        String[] splittedLine;
        matrixStart(40, 20);
        while (reader.hasNextLine()) {
            line = reader.nextLine();

            splittedLine = line.split("");
            for (int i = 0; i < splittedLine.length; i++) {
                matrix[count][i] = splittedLine[i];
                if (matrix[count][i].equals("C")) {
                    // verificar se o x e o y estão recebendo o valor correto
                    x = i;
                    y = count;
                } else if (matrix[count][i].equals("S")) {
                    // verificar se o x e o y estão recebendo o valor correto
                    xS = i;
                    yS = count;
                }
            }
            count++;
        }
    }

    private static void matrixStart(int columns, int rows) {
        matrix = new String[rows][columns];
        numberOfMoves = new int[rows][columns];
        for (int i = 0; i < numberOfMoves.length; i++) {
            for (int j = 0; j < numberOfMoves[0].length; j++) {
                numberOfMoves[x][y] = Integer.MAX_VALUE;
            }
        }
        from = new Dot[rows][columns];
    }
}
