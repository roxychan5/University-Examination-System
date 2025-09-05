import java.sql.*;
import java.util.InputMismatchException;

public class ResultController {
    private String resultID;
    private Result result; // contains the result details

    public ResultController(String resultID) {
        this.resultID = resultID;
        this.result = new Result();
    }

    public ResultController() {
    }

    public String getResultID() {
        return resultID;
    }

    public void setResultID(String resultID) {
        this.resultID = resultID;
    }

    public void ResultMenu(String id, String role) {
        this.resultID = resultID;
        this.result = new Result();
        while (true) {
        
            PersonController.clearScreen();
            System.out.println("---------- Result Menu ----------");
            System.out.println("1. View Result");
            System.out.println("2. Modify Result");
            System.out.println("3. Delete Result");
            System.out.println("4. Add Result");
            System.out.println("5. Return to menu");
            System.out.print("Your choice: ");
          
                int option;
        try {
            option = PersonController.scanner.nextInt();
            PersonController.scanner.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            System.out.println(PersonController.RED + "Error: Please enter a valid number!" + PersonController.RESET);
            PersonController.scanner.nextLine(); // Clear invalid input
            continue; // Go back to menu
        }
                    switch (option) {
                case 1:
                    readResult(id, role);
                    break;
                case 2:
                    modifyResult();
                    break;
                case 3:
                    deleteResult();
                    break;
                case 4:
                    addResult();
                    break;
                case 5:
                    System.out.print("Exiting");
                    PersonController.delay();
                    return;
                default:
                    System.out.println(PersonController.RED + "Error:Invalid choice" + PersonController.RESET);
                    PersonController.delay();
            }
              // Prompt user to press Enter before showing menu again
        System.out.print("Press Enter to return to Menu...");
        PersonController.scanner.nextLine();
        }
    }

    public boolean showMenu() {
        return true; // return true if menu is displayed
    }

  public void readResult(String id, String role) {
    PersonController.clearScreen();
    System.out.println(PersonController.BLUE + "Type exit to exit" + PersonController.RESET);

    System.out.print("Enter SubjectID: ");
    String inputSubID = PersonController.scanner.nextLine(); // ✅ get user input

    if (inputSubID.equalsIgnoreCase("exit")) {
        System.out.print("Exiting");
        PersonController.delay();
        return;
    }

    String sql = "";
    if (role.equals("Faculty")) {
        sql = "SELECT s.SubjectID " +
              "FROM facultyinfo f " +
              "JOIN course c ON f.CourseID = c.CourseID " +
              "JOIN subject s ON c.CourseID = s.CourseID " +
              "WHERE f.FacultyID = ?";
    } else if (role.equals("Staff")) {
        sql = "SELECT SubjectID FROM staff WHERE StaffID = ?";
    }

    try (Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
            PersonController.dbpass)) {
        System.out.println("----------All Student Results----------");
        String resultQuery = "SELECT StudentID, MarksObtained, FinalGrade FROM result WHERE SubjectID = ?";
        try (PreparedStatement resultStmt = connection.prepareStatement(resultQuery)) {
            resultStmt.setString(1, inputSubID); // ✅ set the SubjectID parameter!

            ResultSet resultSet = resultStmt.executeQuery();

            boolean hasResults = false;
            while (resultSet.next()) {
                hasResults = true;
                System.out.println("StudentID: " + resultSet.getString("StudentID"));
                System.out.println("Marks: " + resultSet.getInt("MarksObtained"));
                System.out.println("Grade: " + resultSet.getString("FinalGrade"));
                System.out.println("---------------------------------");
            }

            if (!hasResults) {
                System.out.println(PersonController.RED + "No student results found for SubjectID: " + inputSubID + PersonController.RESET);
            }

            System.out.print("Press Enter to continue");
            while (true) {
                String input = PersonController.scanner.nextLine();
                if (input.isEmpty()) {
                    break;
                } else {
                    System.out.print("Please press Enter only to continue");
                }
            }
            System.out.print("Redirecting to Result Menu");
            PersonController.delay();

            resultSet.close();
        }
    } catch (SQLException e) {
        System.out.println(PersonController.RED + "Database error: " + e.getMessage() + PersonController.RESET);
        e.printStackTrace(); // Debugging Output
        PersonController.delay();
    }
}


    public void modifyResult() {
        while (true) {
            PersonController.clearScreen();
            System.out.println(PersonController.BLUE + "Type exit to exit" + PersonController.RESET);
            System.out.println("----------Modify Result Form----------");
            System.out.print("Enter current Result ID to modify: ");
            String currentResultID = PersonController.scanner.nextLine();

             if (currentResultID.equalsIgnoreCase("exit")) {
            System.out.print("Exiting");
            PersonController.delay();
            return;
        }

            if (!validateResultID(currentResultID)) {
                System.out.println(PersonController.RED + "Error: Invalid Exam ID format" + PersonController.RESET);
                PersonController.delay();
                continue;
            }

            if (!IsExistResult(currentResultID)) {
                System.out.println(PersonController.RED + "Error: ResultID does not exist" + PersonController.RESET);
                PersonController.delay();
                continue;
            }

            try (Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass)) {
                System.out.print("Enter new Marks Obtained: ");

                int marksObtained;
                try {
                    marksObtained = PersonController.scanner.nextInt();
                    PersonController.scanner.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println(PersonController.RED + "Error: Invalid input. Please enter a number"
                            + PersonController.RESET);
                    PersonController.scanner.nextLine();
                    continue;
                }

                if (marksObtained < 0 || marksObtained > 100) {
                    System.out.println(
                            PersonController.RED + "Error: Marks must be between 0 and 100" + PersonController.RESET);
                    System.out.println("Redirecting to Result Menu");
                    PersonController.delay();
                    return;
                }
                
                String grade = checkGrade(marksObtained);

                String sql = "UPDATE result SET MarksObtained = ?, FinalGrade = ? WHERE ResultID = ?";
                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, marksObtained);
                    stmt.setString(2, grade);
                    stmt.setString(3 , currentResultID);

                    if (stmt.executeUpdate() > 0) {
                        System.out.println(
                                PersonController.GREEN + "Result modified successfully!" + PersonController.RESET);
                        System.out.print("Redirecting to Result Menu");
                        PersonController.delay();
                        return;
                    } else {
                        System.out.print(
                                PersonController.RED + "Error: Unable to update result" + PersonController.RESET);
                        PersonController.delay();
                    }
                }
            } catch (SQLException e) {
                System.out.println(PersonController.RED + "Database error: " + e.getMessage() + PersonController.RESET);
                e.printStackTrace(); // Debugging output
            }
        }
    }

    public void deleteResult() {
        PersonController.clearScreen();
        System.out.println(PersonController.BLUE + "Type exit to exit" + PersonController.RESET);
        System.out.println("----------Delete Result----------");
        System.out.print("Enter Result ID to delete: ");
        String resultID = PersonController.scanner.nextLine();

        if (resultID.equalsIgnoreCase("exit")) {
            System.out.print("Exiting");
            PersonController.delay();
            return;
        }
        if (IsExistResult(resultID) == false) {
            System.out.println(PersonController.RED + "" + PersonController.RESET);
        }
        try {
            Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass);
            String sql = "DELETE FROM result WHERE ResultID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, resultID);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.print(PersonController.GREEN + "Result deleted successfully!" + PersonController.RESET);
                PersonController.delay();

            } else {
                System.out.println(PersonController.RED + "Error: No result found for ID" + PersonController.RESET);
                PersonController.delay();

            }

        } catch (SQLException e) {
            System.out.print(PersonController.RED + "Error: System busy. Please Try Again" + PersonController.RESET);
            PersonController.delay();
        }
    }

    public void addResult() {
        StudentController student = new StudentController();
        String resultID = "R" + (100 + (int) (Math.random() * 999));
        PersonController.clearScreen();
        System.out.println(PersonController.BLUE + "Type exit to exit" + PersonController.RESET);
        System.out.println("----------Add Result----------");
        System.out.println("Please fill in the result form:");

        String studentID;
        while (true) {
            System.out.print("Student ID\t: ");
            studentID = PersonController.scanner.nextLine();
            if (studentID.equalsIgnoreCase("exit")) {
                System.out.print("Exiting");
                PersonController.delay();
                return;
            }
            if (validateStudentID(studentID)) {
                if (StudentController.IsExistStudent(studentID) == true) {
                    break;
                } else {
                    System.out.print(PersonController.RED + "Invalid StudentID" + PersonController.RESET);
                    PersonController.delay();
                    return;
                }
            } else {
                System.out.println(PersonController.RED + "Error: Invalid StudentID format"
                        + PersonController.RESET);
            }
        }

        String subjectID;
        while (true) {
            System.out.print("Subject ID\t: ");
            subjectID = PersonController.scanner.nextLine();

            if (validateSubjectID(subjectID)) {
                if (Subject.IsExistSubject(subjectID) == true) {
                    break;
                } else {
                    System.out.println("HI");
                    System.out.print(PersonController.RED + "Invalid SubjectID" + PersonController.RESET);
                    PersonController.delay();
                    return;
                }
            } else {
                System.out.println(PersonController.RED + "Error: Invalid SubjectID"
                        + PersonController.RESET);
            }
        }

        int marksObtained;
        while (true) {
            System.out.print("Marks Obtained\t: ");
            String marksObtainedStr = PersonController.scanner.nextLine();

            try {
                marksObtained = Integer.parseInt(marksObtainedStr);
                if (marksObtained >= 0 && marksObtained <= 100) {
                    break;
                } else {
                    System.out.println(PersonController.RED + "Error: Marks must be between 0 and 100"
                            + PersonController.RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(PersonController.RED + "Error: Invalid input. Please enter a number"
                        + PersonController.RESET);
            }
        }
        String finalGrade = checkGrade(marksObtained);
        // Create and save the result object
        Result newResult = new Result(resultID, studentID, subjectID, (int) marksObtained, finalGrade);
        saveResultToDatabase(newResult);

    }

    private boolean validateResultID(String resultID) {
        if (resultID.length() != 4) {
            return false;
        }
        if (resultID.charAt(0) != 'R') {
            return false;
        }
        for (int i = 1; i < 4; i++) {
            if (!Character.isDigit(resultID.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean IsExistResult(String ResultID) {
        String sql = "SELECT ResultID FROM result WHERE ResultID = ?";
        try {
            Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ResultID);
            ResultSet resultSet = statement.executeQuery();

            boolean exists = resultSet.next(); // if result found, return true

            resultSet.close();
            statement.close();
            connection.close();

            return exists;
        } catch (SQLException e) {
            System.out.print(PersonController.RED + "Error: System busy. Please Try Again"
                    + PersonController.RESET);
            PersonController.delay();
            return false;
        }

    }

    private static boolean validateSubjectID(String subjectID) {
        if (subjectID.length() != 8) {
            return false;
        }
        return true;
    }

    private boolean validateStudentID(String studentID) {
        if (!studentID.startsWith("24WMD")) {
            return false;
        }

        for (int i = 5; i < studentID.length(); i++) {
            if (!Character.isDigit(studentID.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private String checkGrade(int marksObtained) {
        if (marksObtained >= 90) {
            return "A+";
        } else if (marksObtained >= 80) {
            return "A";
        } else if (marksObtained > 75) {
            return "A-";
        } else if (marksObtained > 70) {
            return "B+";
        } else if (marksObtained > 65) {
            return "B";
        } else if (marksObtained > 60) {
            return "C+";
        } else if (marksObtained >= 50) {
            return "C";
        } else if (marksObtained < 50) {
            return "F";
        } else {
            return "Invalid Grade";
        }
    }

    private void saveResultToDatabase(Result result) {
        try {
            Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass);
            String sql = "INSERT INTO result (ResultID, StudentID, SubjectID, MarksObtained, FinalGrade) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, result.getResultID());
            statement.setString(2, result.getStudentID());
            statement.setString(3, result.getSubjectID());
            statement.setInt(4, result.getMarksObtained());
            statement.setString(5, result.getFinalGrade());

            if (statement.executeUpdate() > 0) {
                System.out.print(PersonController.GREEN + "Result added successfully!" + PersonController.RESET);
                PersonController.delay();
            } else {
                System.out.println("Unable to add result for this student: " + result.getStudentID());
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.print(PersonController.RED + "Error: System busy. Please Try Again" + PersonController.RESET);
            e.printStackTrace();
            PersonController.delay();
            PersonController.delay();
            PersonController.delay();
            PersonController.delay();
            PersonController.delay();

        }
    }
}