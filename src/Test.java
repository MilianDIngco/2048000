package src;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        GameLogic gl = new GameLogic();
        gl.generateRandom();
        Scanner scanner = new Scanner(System.in);
        String input = "";
        gl.printBoard();
        while (!input.equals("quit")) {
            gl.copyBoard();
            if (input.equals("r")) {
                gl.rightMove();
                gl.generateRandom();
                System.out.println("right");
            }
            if (input.equals("l")) {
                gl.leftMove();
                gl.generateRandom();
                System.out.println("left");
            }
            if (input.equals("u")) {
                gl.upMove();
                gl.generateRandom();
                System.out.println("up");
            }
            if (input.equals("d")) {
                gl.downMove();
                gl.generateRandom();
                System.out.println("down");
            }
            System.out.println("PREVIOUS");
            gl.printPrevBoard();
            System.out.println("NEW");
            gl.printBoard();
            input = scanner.next();
        }
    }
}