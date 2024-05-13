package com.zsgs.chandru.librarymanagement.exceptionmesage;

import com.zsgs.chandru.librarymanagement.colortext.Color;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ExceptionHandling {
    private static Scanner scan = new Scanner(System.in);

    public static int getIntInput() {
        int input = 0;
        while (true) {
            try {
                input = scan.nextInt();
                break;
            } catch (InputMismatchException e) {
               printErrorMessage("Input Must Be Integer");
               scan.next(); // Clear input buffer
               return 0;
            }
        }
        return input;
    }
    public static void printErrorMessage(String message) {
        System.out.println(Color.ANSI_RED +
                "\n=================================" +
                "\n=             ERROR             =" +
                "\n\t" + message +
                "\n=        Please Try Again       =" +
                "\n=================================" +
                Color.ANSI_RESET);
    }
}
