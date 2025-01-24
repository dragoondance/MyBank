package org.dragon.constant;

public enum Command {
    login("login"),
    logout("logout"),
    withdraw("withdraw"),
    deposit("deposit"),
    transfer("transfer");
    @SuppressWarnings("unused")
    private final String name;

    Command(String s) {
        name = s;
    }
}
