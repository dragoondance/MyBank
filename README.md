<!-- omit in toc -->

# My Bank ATM Application

- [My Bank ATM Application](#my-bank-atm-application)
  - [Setup](#setup)
  - [Run](#run)
  - [Commands](#commands)

## Setup
1. Download Java JDK [here](https://www.oracle.com/id/java/technologies/downloads/).
2. After download, install java on your machine.
3. Setting your JAVA_HOME and PATH environtment variables to java directory that have been installed.
4. Download Apache Maven, choose binary tar.gz or binary zip [here](https://maven.apache.org/download.cgi).
5. Extract the downloaded maven into your local directory, for example: C:/apache-maven.
6. Setting your MAVEN_HOME to apache-maven directory and PATH variable to apache-maven/bin directory.

## Run
1. After you clone or download this project, open using your bash terminal into the root of this project.
2. execute ./start.sh to start the application

## Commands
1. login {username} -> put your username to login into application.
2. deposit {amount} -> put balance to your active account.
3. withdraw {amount} -> substract balance from your active account.
4. transfer {target_username} {amount} -> transfer balance to the target username.
5. logout -> logout current user, and set active user to null.
6. exit -> quit the application.