package org.dragon;

import org.dragon.controller.Bank;

import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        Scanner ins = new Scanner(System.in);
        // Press Shift+F10 or click the green arrow button in the gutter to run the code.
        boolean isAlive = true;

        System.out.println("Welcome to My bank");
        Bank bank = new Bank();

        while (isAlive) {
            String command="";
            command+=ins.nextLine();
            if (command.equals("exit")) {
                isAlive = false;
                break;
            }
            bank.processCommand(command);
        }
    }
}