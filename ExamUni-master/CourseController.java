import java.sql.*;

public class CourseController {
    private String courseID;
    private Course course;
    private String ExamID1;
    private String ExamID2;
    private String ExamID3;

    public void CourseMenu(String loggedInStudentID) {
        // this.CourseID = CourseID;
        this.course = new Course();
        int choice = 0;
        String sql;
        String input;// Y or N

        while (true) {
            PersonController.clearScreen();
            System.out.println("------------Diploma Course Menu------------");
            System.out.println("1. Chemistry");
            System.out.println("2. Information Technology");
            System.out.println("3. Accounting");
            System.out.println("4. Electronics Engineering Technology");
            System.out.println("5. Architecture");
            System.out.println("6. Return Student Menu");
            System.out.print("Your choice: ");
            choice = PersonController.scanner.nextInt();
            PersonController.scanner.nextLine();
            input = PersonController.scanner.nextLine().trim();
            switch (choice) {
                case 1:
                    while (true) {
                        PersonController.clearScreen();
                        System.out.println("----------Subjects for Chemistry----------");// CHS
                        System.out.println("1. CATALYSIS");
                        System.out.println("2. ORGANIC CHEMISTRY");
                        System.out.println("3. POLYMER CHEMISTRY");
                        System.out.print("(Y)Register || (N)Return: ");

                        input = PersonController.scanner.nextLine();
                        if (input.equalsIgnoreCase("Y")) {
                            courseID = "CHS";
                            ExamID1 = "EX303";// CTCHS003
                            ExamID2 = "EX301";// OCCHS001
                            ExamID3 = "EX302";// PCCHS002
                            break;
                        } else if (input.equalsIgnoreCase("N")) {
                            System.out.print("Redirecting to Course Menu");
                            PersonController.delay();
                            break;
                        } else {
                            System.out.println(PersonController.RED + "Error:Invalid Choice" + PersonController.RESET);
                            System.out.print("Redirecting to Chemistry Course Menu");
                            PersonController.delay();

                        }
                    }
                    if (input.equalsIgnoreCase("N")) {
                        continue;
                    } else {
                        break;
                    }

                case 2:
                    while (true) {
                        PersonController.clearScreen();
                        System.out.println("----------Subjects for Information Technology----------");// DFT
                        System.out.println("1. OBJECT-ORIENTED PROGRAMMING TECHNIQUES");
                        System.out.println("2. INTRODUCTION TO INTERFACE DESIGN");
                        System.out.println("3. DISCRETE MATHEMATICS");
                        System.out.print("(Y)Register || (N)Return: ");

                        input = PersonController.scanner.nextLine();
                        if (input.equalsIgnoreCase("Y")) {
                            courseID = "DFT";
                            ExamID1 = "EX401";// AACS2204
                            ExamID2 = "EX402";// AACS2303
                            ExamID3 = "EX403";// AMMS2603
                            break;
                        } else if (input.equalsIgnoreCase("N")) {
                            System.out.print("Redirecting to Course Menu");
                            PersonController.delay();
                            break;
                        } else {
                            System.out.println(PersonController.RED + "Error:Invalid Choice" + PersonController.RESET);
                            System.out.print("Redirecting to Chemistry Course Menu");
                            PersonController.delay();
                        }
                    }
                    if (input.equalsIgnoreCase("N")) {
                        continue;
                    } else {
                        break;
                    }

                case 3:
                    while (true) {
                        PersonController.clearScreen();
                        System.out.println("----------Subjects for Accounting----------");// ACC
                        System.out.println("1. AUDITING");
                        System.out.println("2. MANAGEMENT ACCOUNTING");
                        System.out.println("3. TAXATION");
                        System.out.print("(Y)Register || (N)Return: ");

                        input = PersonController.scanner.nextLine();
                        if (input.equalsIgnoreCase("Y")) {
                            courseID = "ACC";
                            ExamID1 = "EX101";// ADACC003
                            ExamID2 = "EX102";// MAACC002
                            ExamID3 = "EX103";// TXACC001
                            break;
                        } else if (input.equalsIgnoreCase("N")) {
                            System.out.print("Redirecting to Course Menu");
                            PersonController.delay();
                            break;
                        } else {
                            System.out.println(PersonController.RED + "Error:Invalid Choice" + PersonController.RESET);
                            System.out.print("Redirecting to Chemistry Course Menu");
                            PersonController.delay();

                        }
                    }
                    if (input.equalsIgnoreCase("N")) {
                        continue;
                    } else {
                        break;
                    }

                case 4:
                    while (true) {
                        PersonController.clearScreen();
                        System.out.println("----------Subjects for Electronics Engineering Technology----------");// EET
                        System.out.println("1. ANALOGUE ELECTRONICS");
                        System.out.println("2. ENGINEERING MATHEMATICS");
                        System.out.println("3. ELECTROMAGNETISM");
                        System.out.print("(Y)Register || (N)Return: ");

                        input = PersonController.scanner.nextLine();
                        if (input.equalsIgnoreCase("Y")) {
                            courseID = "EET";
                            ExamID1 = "EX501";// AEEET001
                            ExamID2 = "EX502";// EMEET002
                            ExamID3 = "EX503";// EMEET003
                            break;
                        } else if (input.equalsIgnoreCase("N")) {
                            System.out.print("Redirecting to Course Menu");
                            PersonController.delay();
                            break;
                        } else {
                            System.out.println(PersonController.RED + "Error:Invalid Choice" + PersonController.RESET);
                            System.out.print("Redirecting to Chemistry Course Menu");
                            PersonController.delay();

                        }
                    }
                    if (input.equalsIgnoreCase("N")) {
                        continue;
                    } else {
                        break;
                    }

                case 5:
                    while (true) {
                        PersonController.clearScreen();
                        System.out.println("----------Subjects for Architecture----------");// ARC
                        System.out.println("1. BUILDING TECHNOLOGY");
                        System.out.println("2. STUDIO DESIGN");
                        System.out.println("3. STRUCTURAL STUDIES");
                        System.out.print("(Y)Register || (N)Return: ");

                        input = PersonController.scanner.nextLine();
                        if (input.equalsIgnoreCase("Y")) {
                            courseID = "ARC";
                            ExamID1 = "EX201";// BTARC003
                            ExamID2 = "EX202";// SDARC001
                            ExamID3 = "EX203";// SSARC002
                            break;
                        } else if (input.equalsIgnoreCase("N")) {
                            System.out.print("Redirecting to Course Menu");
                            PersonController.delay();
                            break;
                        } else {
                            System.out.println(PersonController.RED + "Error:Invalid Choice" + PersonController.RESET);
                            System.out.print("Redirecting to Chemistry Course Menu");
                            PersonController.delay();
                        }
                    }
                    if (input.equalsIgnoreCase("N")) {
                        continue;
                    } else {
                        break;
                    }

                case 6:
                    System.out.print("Redirecting to Student Menu");
                    PersonController.delay();
                    return;

                default:
                    System.out.println(PersonController.RED + "Error:Invalid Choice" + PersonController.RESET);
                    System.out.print("Redirecting to Course Menu");
                    PersonController.delay();

            }
            // db
            try {
                Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                        PersonController.dbpass);
                // Check CourseID is registered before or not
                sql = "SELECT CourseID from studentinfo WHERE StudentID = ?";
                PreparedStatement checkstatement = connection.prepareStatement(sql);
                checkstatement.setString(1, loggedInStudentID);
                ResultSet resultSet = checkstatement.executeQuery();
                if (resultSet.next()) {
                    String existCourseID = resultSet.getString("CourseID");
                    if (existCourseID == null || existCourseID.isEmpty()) {
                        try {
                            sql = "UPDATE studentinfo SET CourseID = ? WHERE StudentID= ?";
                            PreparedStatement updatestatement = connection.prepareStatement(sql);
                            updatestatement.setString(1, courseID);
                            updatestatement.setString(2, loggedInStudentID);

                            if (updatestatement.executeUpdate() > 0) {
                                System.out.println(
                                        PersonController.GREEN + "Register Successfully" + PersonController.RESET);
                                System.out.print("Redirecting to Student Menu");
                                PersonController.delay();
                                try {// link Student with exam when Student is Registered Course
                                    Connection connections = DriverManager.getConnection(PersonController.dbURL,
                                            PersonController.dbName, PersonController.dbpass);

                                    String sqls = "INSERT INTO students_exams (StudentID, ExamID) VALUES (?, ?)";
                                    PreparedStatement checkstatements = connections.prepareStatement(sqls);

                                    String[][] examID = {
                                            { loggedInStudentID, ExamID1 },
                                            { loggedInStudentID, ExamID2 },
                                            { loggedInStudentID, ExamID3 }
                                    };

                                    // Adding multiple entries to the batch
                                    for (String[] allID : examID) {
                                        checkstatements.setString(1, allID[0]); // Student ID
                                        checkstatements.setString(2, allID[1]); // Exam ID
                                        checkstatements.addBatch();

                                    }

                                    // Execute batch insert
                                    checkstatements.executeBatch();

                                    // Close resources
                                    checkstatements.close();
                                    connections.close();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    System.out.print(PersonController.RED + "Error: System busy. Please Try Again"
                                            + PersonController.RESET);
                                    PersonController.delay();
                                }
                                break;
                            } else {
                                System.out.println(PersonController.RED + "Unable to Register. Please Try Again"
                                        + PersonController.RESET);
                                PersonController.delay();
                            }
                            updatestatement.close();
                        } catch (Exception e) {
                            System.out.print(PersonController.RED + "Error: System busy. Please Try Again"
                                    + PersonController.RESET);
                            PersonController.delay();
                        }
                        // Handle 'exit' command
                        if (input.equalsIgnoreCase("exit")) {
                            System.out.print("Exiting");
                            PersonController.delay();
                            System.exit(0);
                        }

                        // Validate numeric input
                        try {
                            choice = Integer.parseInt(input);

                            if (choice >= 1 && choice <= 6) {
                                if (choice == 6) {
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
                    } else {
                        System.out
                                .println(PersonController.RED + "You had already register " + existCourseID + " course"
                                        + PersonController.RESET);
                        System.out.print("Redirecting to Student Menu");
                        PersonController.delay();
                        return;
                    }
                }

            } catch (Exception e) {
                System.out
                        .print(PersonController.RED + "Error: System busy. Please Try Again" + PersonController.RESET);
                PersonController.delay();
            }

        }

    }

}
