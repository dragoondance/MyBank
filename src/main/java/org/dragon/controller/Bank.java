package org.dragon.controller;

import org.dragon.constant.Command;
import org.dragon.constant.Resources;
import org.dragon.object.Account;
import org.dragon.object.Debt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Bank {
    private HashMap<String, Account> accounts;
    private List<Debt> debtList;

    private Account activeAccount;

    public Account getActiveAccount() {
        return activeAccount;
    }

    public List<Debt> getDebtList() {
        return debtList;
    }

    public void displayInstruction() {
        System.out.println(Resources.LIST_OF_COMMAND);
        if (activeAccount == null) {
            System.out.println(Resources.LOGIN_COMMAND);
        } else {
            for (String s : Resources.POST_LOGIN_COMMAND) {
                System.out.println(s);
            }
        }
        System.out.println(Resources.EXIT_COMMAND);
    }

    public Bank() {
        accounts = new HashMap<String, Account>();
        debtList = new ArrayList<Debt>();
        displayInstruction();
    }

    public void setActiveAccount(Account a) {
        this.activeAccount = a;
    }

    public boolean checkNonActiveAccount() {
        if (activeAccount == null) {
            System.out.println(Resources.INVALID_SESSION);
            displayInstruction();
            return true;
        } else {
            return false;
        }
    }

    public void login(String username) {
        System.out.println(Resources.HELLO.formatted(username));
        Account a = accounts.get(username);
        if (a != null) {
            setActiveAccount(a);
        } else {
            a = new Account(username);
            accounts.put(username, a);
        }
        setActiveAccount(a);
        displayOwed(username);
        displayInstruction();
    }

    public void logout() {
        if (checkNonActiveAccount()) return;
        this.accounts.put(this.activeAccount.getName(), this.activeAccount);
        System.out.println(Resources.GOODBYE.formatted(this.activeAccount.getName()));
        setActiveAccount(null);
        displayInstruction();
    }

    public void displayOwed(String username) {
        System.out.println(Resources.YOUR_BALANCE.formatted(activeAccount.getBalance()));
        List<Debt> owedFrom = debtList.stream().filter(debt -> debt.getTarget().equals(username)).collect(Collectors.toList());
        for (Debt d: owedFrom) {
            System.out.println(Resources.OWEDFROM.formatted(d.getAmount(), d.getFrom()));
        }
        List<Debt> owedTo = debtList.stream().filter(debt -> debt.getFrom().equals(username)).collect(Collectors.toList());
        for (Debt d: owedTo) {
            System.out.println(Resources.OWEDTO.formatted(d.getAmount(), d.getTarget()));
        }
    }

    private boolean checkZeroAmount(double amount) {
        if (amount < 0) {
            System.out.println(Resources.INVALID_AMOUNT);
            return true;
        } else {
            return false;
        }
    }

    public void deposit(double amount) {
        if (checkZeroAmount(amount)) return;
        if (checkNonActiveAccount()) return;
        double tempAmount = Double.parseDouble(String.format("%.2f", amount));
        //get all debts from the current users
        List<Debt> currentDebts = debtList.stream()
                                        .filter(debt -> debt.getFrom().equals(activeAccount.getName()))
                                                .collect(Collectors.toList());
        // loop for each debt to other users then pay all of the debts
        // until debt amount is 0 or amount is 0 whichever come first
        for (Debt d: currentDebts) {
            Account targetAccount = accounts.get(d.getTarget());
            if (d.getAmount() <= tempAmount) {
                tempAmount = Double.parseDouble(String.format("%.2f", tempAmount - d.getAmount()));
                System.out.println(Resources.TRANSFERREDTO.formatted(d.getAmount(), d.getTarget()));
                targetAccount.addBalance(d.getAmount());
                debtList.remove(d);
            } else {
                d.setAmount(d.getAmount() - tempAmount);
                System.out.println(Resources.TRANSFERREDTO.formatted(tempAmount, d.getTarget()));
                targetAccount.addBalance(tempAmount);
                tempAmount = 0;
                break;
            }
            accounts.put(d.getTarget(), targetAccount);
        }
        // if the amount deposited after subtracted with debts will surplus will add to current balance
        if (tempAmount > 0) {
            activeAccount.addBalance(tempAmount);
        }
        displayOwed(activeAccount.getName());
    }

    public void withdraw(double amount) {
        if (checkZeroAmount(amount)) return;
        if (checkNonActiveAccount()) return;
        // check if the current balance is less than amount then display warning, else subtract from active account
        if (this.activeAccount.getBalance() < amount) {
            System.out.println(Resources.INVALID_AMOUNT);
            return;
        } else {
            activeAccount.subtractBalance(amount);
        }
        displayOwed(activeAccount.getName());
    }

    public void transfer(String target, double amount) {
        if (checkZeroAmount(amount)) return;
        double tfAmount = Double.parseDouble(String.format("%.2f", amount));
        //get all of debt owed from user that is going to be transferred, if exist, will subtract the debt from the target user
        List<Debt> owedFromTarget = debtList.stream().filter(debt -> debt.getTarget().equals(activeAccount.getName()) && debt.getFrom().equals(target)).collect(Collectors.toList());
        if (owedFromTarget.size() > 0) {
            if (owedFromTarget.get(0).getAmount() > tfAmount) {
                for (Debt d : debtList) {
                    if (d.getFrom().equals(target) && d.getTarget().equals(activeAccount.getName())) {
                        d.subtractAmount(tfAmount);
                        tfAmount = 0;
                    }
                }
            } else {
                tfAmount = Double.parseDouble(String.format("%.2f", tfAmount - owedFromTarget.get(0).getAmount()));
                debtList.remove(owedFromTarget.get(0));
            }
        }
        // if amount after subtracted with the owed balance, still more, will execute transfer process
        if (tfAmount > 0) {
            Account targetAccount;
            // check if target account exist, if not then create new account
            if (accounts.containsKey(target)) {
                targetAccount = accounts.get(target);
            } else {
                targetAccount = new Account(target);
            }
            // if tfAmount is more than active balance, then insert or add debt balance to the target account where the value is tfAmount - active balance
            if (tfAmount > activeAccount.getBalance()) {
                tfAmount = Double.parseDouble(String.format("%.2f", tfAmount - activeAccount.getBalance()));
                List<Debt> owedTo = debtList.stream().filter(debt -> debt.getFrom().equals(activeAccount.getName()) && debt.getTarget().equals(target)).collect(Collectors.toList());
                if (owedTo.size() > 0) {
                    for (Debt d : debtList) {
                        if (d.getTarget().equals(target) && d.getFrom().equals(activeAccount.getName())) {
                            d.addAmount(tfAmount);
                        }
                    }
                } else {
                    Debt d = new Debt(activeAccount.getName(), target, tfAmount);
                    debtList.add(d);
                }
                targetAccount.addBalance(activeAccount.getBalance());
                accounts.put(target, targetAccount);
                activeAccount.setBalance(0);
            } else {
                targetAccount.addBalance(tfAmount);
                accounts.put(target, targetAccount);
                activeAccount.subtractBalance(tfAmount);
            }
        }
        System.out.println(Resources.TRANSFERREDTO.formatted(amount, target));
        displayOwed(activeAccount.getName());
    }

    public void processCommand(String command) {
        String[] parts = command.split(" ");
        // sanitize string command to be used in application
        if (parts[0].equals(Command.login.name()) && parts.length > 1) {
            login(parts[1]);
        } else if (parts[0].equals(Command.logout.name())) {
            logout();
        } else if (parts[0].equals(Command.deposit.name()) && parts.length > 1) {
            try {
                deposit(Double.parseDouble(parts[1]));
            } catch (Exception e) {
                System.out.println(Resources.INVALID_NUMBER);
                displayInstruction();
            }
        } else if (parts[0].equals(Command.withdraw.name()) && parts.length > 1) {
            try {
                withdraw(Double.parseDouble(parts[1]));
            } catch (Exception e) {
                System.out.println(Resources.INVALID_NUMBER);
                displayInstruction();
            }
        } else if (parts[0].equals(Command.transfer.name()) && parts.length > 2) {
            try {
                transfer(parts[1], Double.parseDouble(parts[2]));
            } catch (Exception e) {
                System.out.println(Resources.INVALID_NUMBER);
                displayInstruction();
            }
        } else {
            System.out.println(Resources.INVALID_COMMAND);
            displayInstruction();
        }
    }

}
