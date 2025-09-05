import java.util.*;
import java.util.regex.*;
import java.io.Console;
import java.sql.*;

public class PersonController {
    public static String RED = "\u001B[31m";// PersonController.RED -->(Warning/error)
    public static String GREEN = "\u001B[32m";// PersonController.GREEN -->(Success)
    public static String BLUE = "\u001B[34m";// Info // PersonController.BLUE -->(Info)
    public static String RESET = "\u001B[0m";// Close color // PersonController.RESET -->(Close color)
    public static Scanner scanner = new Scanner(System.in); // PersonController.scanner.nextLine()
    // JDBC connection details
    public static String dbURL = "jdbc:mysql://localhost:3306/unisystem";
    public static String dbName = "root";
    public static String dbpass = "";
    Console console = System.console();

    public static void displayMainMenu() {
        while (true) {
            try {
                clearScreen();
                System.out.println(PersonController.BLUE + "Type exit to exit" + PersonController.RESET);
                System.out.println("----------Welcome to TARUMT-----------");
                System.out.println("1. Student");
                System.out.println("2. Faculty Member");
                System.out.println("3. Academic Staff");
                System.out.println("4. Exit");
                System.out.print("Your choice(1-4): ");

                String input = scanner.nextLine().trim();

                // Handle 'exit' command
                if (input.equalsIgnoreCase("exit")) {
                    System.out.print("Exiting");
                    PersonController.delay();
                    System.exit(0);
                }

                // Validate numeric input
                try {
                    int choice = Integer.parseInt(input);

                    if (choice >= 1 && choice <= 4) {
                        if (choice == 4) {
                            System.out.print("Exiting");
                            PersonController.delay();
                            System.exit(0);
                        } else {
                            User.login(choice);
                        }
                    } else {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    System.out.println(
                            PersonController.RED + "Error: Please enter a number 1-4"
                                    + PersonController.RESET);
                    System.out.print("Returning to Main Menu");
                    PersonController.delay();
                }

            } catch (Exception e) {
                System.out.println(
                        PersonController.RED + "An error occurred: " + e.getMessage() + PersonController.RESET);
                System.out.print("Returning to Main Menu");
                PersonController.delay();
                scanner.nextLine();
            }
        }

    }

    public String modifyPersonName(String tableName, String identifierField) {// sql table Column(Name)
        // get sql to call:
        // PersonController modify = new PersonController(); --> create a new object
        // sql = modify.modifyPersonName("","");
        String sql = "";
        String modifyName = "";
        while (true) {
            System.out.print("Enter your name: ");
            modifyName = PersonController.scanner.nextLine();
            if (modifyName.length() >= 3) {
                sql = "UPDATE " + tableName + " SET Name = \"" + modifyName + "\" WHERE " + identifierField + " = ?";
                System.out.println();
                break;
            } else {
                System.out.println(
                        PersonController.RED + "Error:Provide name more than 3 character" + PersonController.RESET);
            }
        }
        return sql;
    }

    public String modifyPersonEmail(String tableName, String identifierField) {// sql table Column(Email)
        String sql = "";
        String modifyEmail = "";
        while (true) {
            System.out.print("Enter a new email: ");
            modifyEmail = PersonController.scanner.nextLine();
            String EmailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            Pattern EmailPattern = Pattern.compile(EmailRegex);
            Matcher EmailMatcher = EmailPattern.matcher(modifyEmail);
            if (EmailMatcher.matches()) {
                sql = "UPDATE " + tableName + " SET Email = \"" + modifyEmail + "\" WHERE " + identifierField + " = ?";
                break;
            } else {
                System.out.println(PersonController.RED + "Error:Invalid email format." + PersonController.RESET);
            }
        }
        return sql;
    }

    public String modifyContactNumber(String tableName, String identifierField) {// sql table Column(Contact)
        String sql = "";
        String modifyContactNumber = "";
        while (true) {
            System.out.print("Enter a new Contact: ");
            modifyContactNumber = PersonController.scanner.nextLine();
            String ContactRegex = "^\\d{10,11}$";
            Pattern ContactNumberPattern = Pattern.compile(ContactRegex);
            Matcher ContactNumberMatcher = ContactNumberPattern.matcher(modifyContactNumber);
            if (ContactNumberMatcher.matches()) {
                sql = "UPDATE " + tableName + " SET Contact = " + modifyContactNumber + " WHERE " + identifierField
                        + " = ?";
                break;
            } else {
                System.out.println(
                        PersonController.RED + "Error:Invalid Format Contact Number!" + PersonController.RESET);
                System.out.println("Reminder: No dash(-)");

            }
        }
        return sql;

    }

    public String modifyPersonPassword(String tableName, String identifierField) {// sql table Column(Password)
        String sql = "";
        String modifyPassword = "";

        while (true) {
            modifyPassword = new String(console.readPassword("Enter a new Password: "));
            System.out.print("Enter a new Password: ");

            System.out.print(PersonController.BLUE + "Show Password?(Y) " + PersonController.RESET);
            String confirm = PersonController.scanner.nextLine();
            if (confirm.equalsIgnoreCase("Y")) {
                System.out.println("Password: " + modifyPassword);
                
            } else if (confirm.equalsIgnoreCase("N")) {
            }
            String PasswordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.])(?=\\S+$).{8,}$";
            Pattern PasswordPattern = Pattern.compile(PasswordRegex);
            Matcher PasswordMatcher = PasswordPattern.matcher(modifyPassword);
            if (PasswordMatcher.matches()) {
                sql = "UPDATE " + tableName + " SET Password = \"" + modifyPassword + "\" WHERE " + identifierField
                        + " = ?";
                break;
            } else {
                System.out.println(PersonController.RED + "Error:Password are too weak!!!");
                System.out.println(
                        "Password must contain a digit, a lower case letter, a upper case letter, a special character, no whitespace, and at least 8 characters"
                                + PersonController.RESET);
            }
        }
        return sql;
    }

    public boolean isPersonIDExist(String id) {
        return true;
    }

    public static void main(String[] args) {
        PersonController.clearScreen();
        System.out.print("Connecting to database");// Connect to database first or else unable proceed
        delay();
        while (true) {
            try {
                DriverManager.getConnection(dbURL, dbName, dbpass);
                System.out.print(PersonController.GREEN + "Database connected successfully" + PersonController.RESET);
                delay();
                break;
            } catch (SQLException e) {
                System.out.print(PersonController.RED + "Error: Failed to connect to database");
                delay();
                System.out.print(PersonController.RESET);

            }
        }
        PersonController.displayMainMenu();
    }

    // -----------------------Function------------------------------
    // clear screen
    public static void clearScreen() { // PersonController.clearScreen()
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // delay
    public static void delay() { // PersonController.delay()
        try {
            int interval = 500; // Display a dot every 500 milliseconds
            int dotCount = 2500 / interval;// 5 dots

            for (int i = 0; i < dotCount; i++) {
                System.out.print(".");
                System.out.flush();
                Thread.sleep(interval);
            }
            System.out.println(); // Move to the next line after printing dots
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Error: Delay interrupted.");
        }
    }

    // load
    public static void load() { // PersonController.load()

        String[] spinner = new String[] { "|", "/", "-", "\\" };
        int spinnerIndex = 0;
        long startTime = System.currentTimeMillis();
        long duration = 2000;
        System.out.print("Accessing ");

        while (System.currentTimeMillis() - startTime < duration) {

            System.out.print("\b" + spinner[spinnerIndex]);
            spinnerIndex = (spinnerIndex + 1) % spinner.length;

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void timer() { // PersonController.timer()
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void loading() {

        try {
            long startTime = System.currentTimeMillis();
            long duration = 1000;

            while (System.currentTimeMillis() - startTime < duration) {
                System.out.print("Loading ");

                System.out.print("x");
                System.out.flush();
                Thread.sleep(500);

                System.out.print("_");
                System.out.flush();
                Thread.sleep(500);

                System.out.print("x");
                System.out.flush();
                Thread.sleep(500);

                System.out.print("\r");
                System.out.flush();
            }
            clearScreen();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}