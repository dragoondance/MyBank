package org.dragon.constant;

public class Resources {
    public static String INVALID_NUMBER = "Invalid number format. example: 123.12";
    public static String INVALID_COMMAND = "Invalid command, please use existing commands.";
    public static String INVALID_SESSION = "Invalid session. please login to your account.";
    public static String INVALID_AMOUNT = "Invalid amount supplied. please input amount correctly.";
    public static String HELLO = "Hello, %s!";
    public static String GOODBYE = "Goodbye, %s!";
    public static String OWEDFROM = "Owed $%s from %s";
    public static String OWEDTO = "Owed $%s to %s";
    public static String TRANSFERREDTO = "Transferred $%s to %s";
    public static String YOUR_BALANCE = "Your balance is: $%s";
    public static String LIST_OF_COMMAND = "----List of command:----";
    public static String LOGIN_COMMAND = "login [username] -> for login to account and access other menu";
    public static String EXIT_COMMAND = "exit -> close MyBank apps";

    public static String[] POST_LOGIN_COMMAND = new String[]{
        "deposit [amount] -> for deposit amount to active account's balance",
                "withdraw [amount] -> for withdraw amount from active account's balance",
                "transfer [target_username] [amount] -> for transfer amount to another user",
                "logout -> logout user"};


}
