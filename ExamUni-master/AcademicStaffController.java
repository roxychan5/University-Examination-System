import java.sql.*;
import javax.security.auth.Subject;
import java.util.regex.Pattern;
import java.util.InputMismatchException;
import java.util.regex.Matcher;

public class AcademicStaffController extends PersonController implements PersonAction {
    private String acaStaffID;
    private Subject subject;
    private Result result;
    private ResultController resultcontroller = new ResultController();
    private AcademicStaff academicStaff;
    private ExamController examController;

    public void AcademicStaffMenu(String acaStaffID) {
        this.acaStaffID = acaStaffID;
        while (true) {
            PersonController.clearScreen();
            System.out.println("----------Academic Staff Menu----------");// after login
            System.out.println("1. Academic Staff Information");
            System.out.println("2. Edit Information");
            System.out.println("3. View Timetable");
            System.out.println("4. Manage Grading");
            System.out.println("5. Show All Students");
            System.out.println("6. Log Out");
            System.out.print("Your choice: ");
            int option;  
            try {
            option = PersonController.scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(PersonController.RED + "Error: Please enter a valid number!" + PersonController.RESET);
            PersonController.scanner.nextLine(); // Clear invalid input
            continue; // Go back to menu
        }
            switch (option) {
                case 1:
                    displayProfile(acaStaffID);
                    break;
                case 2:
                    updateProfile(acaStaffID);
                    break;
                case 3:
                    String subjectID = PersonController.scanner.nextLine(); // Prompt user to input Subject ID
                    Exam exam = new Exam();
                    exam.displayExamDetailsForAcademicStaff(acaStaffID); // Call method with valid Subject ID
                    break;
                case 4:
                    // addResult(acaStaffID);
                    resultcontroller.ResultMenu(acaStaffID, "Staff");
                    break;
                case 5:
                    readStudent(acaStaffID);
                    break;
                case 6:
                    System.out.print("Logging out");
                    PersonController.delay();
                    academicStaff = null;
                    displayMainMenu();
                default:
                    System.out.println(PersonController.RED + "Error:Invalid choice" + PersonController.RESET);
                    System.out.print("Redirecting to Academic Staff Menu");
                    PersonController.delay();
            }// Prompt user to press Enter before showing menu again
        System.out.print("Press Enter to return to Menu...");
        PersonController.scanner.nextLine();
        }

    }

    public boolean showMenu(String acaStaffID, String password) {
        // login
        this.academicStaff = new AcademicStaff();

        try {
            Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass);
            String sql = "SELECT * FROM staff WHERE StaffID = ? AND Password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, acaStaffID);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                academicStaff.setSubjectID(resultSet.getString("SubjectID"));
                System.out.println(PersonController.GREEN + "Login Successfully!!!");
                System.out.println("Welcome " + resultSet.getString("Name") + "!" + PersonController.RESET);
                System.out.print("Redirecting to Academic Staff Menu");
                PersonController.delay();
                return true;
            } else {
                System.out.println(
                        PersonController.RED + "Error: Invalid Staff ID or Password" + PersonController.RESET);
                System.out.print("Redirecting to Main Menu");
                PersonController.delay();
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.print(PersonController.RED + "Error: System busy. Please Try Again"
                    + PersonController.RESET);
            delay();

        }

        return false;
    }

    public void displayProfile(String loggedInAcaStaffID) {
        getAcaStaffInfo(loggedInAcaStaffID);
        System.out.println("----------Academic Staff Information----------");
        System.out.println(academicStaff.toString());
        System.out.println("----------------------------------------------");
        System.out.print("Press enter to continue");
        while (true) {
            String input = PersonController.scanner.nextLine();
            if (input.isEmpty()) { // input empty
                System.out.print("Redirecting to Academic Staff Menu");
                PersonController.delay();
                break;
            } else {
                System.out.print("Please press Enter only to continue");

            }
        }
    }

    public void getAcaStaffInfo(String loggedInAcaStaffID) {
        try {
            Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass);
            String sql = "SELECT s.*, subj.subjectName " +
                    "FROM staff s " +
                    "LEFT JOIN subject subj ON s.SubjectID = subj.SubjectID " +
                    "WHERE s.StaffID = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, loggedInAcaStaffID);
            ResultSet resultSet = statement.executeQuery();
            PersonController.clearScreen();
            if (resultSet.next()) {
                academicStaff.setID(loggedInAcaStaffID);
                academicStaff.setName(resultSet.getString("Name"));
                academicStaff.setIc(resultSet.getString("IC"));
                academicStaff.setEmail(resultSet.getString("Email"));
                academicStaff.setContactNumber(resultSet.getString("Contact"));
                academicStaff.setSubjectID(resultSet.getString("SubjectID"));
                academicStaff.setSubjectName(resultSet.getString("SubjectName"));
            } else {
                System.out.println("No Staff found with ID: " + loggedInAcaStaffID);
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.print(PersonController.RED + "Error: System busy. Please Try Again"
                    + PersonController.RESET);
            delay();
        }
    }

    public void addAcademicStaff(String facID) {
        // Check if facID is authorized (dummy check, you can add real DB/logic)
        if (facID == null || facID.isEmpty()) {
            System.out.println(
                    PersonController.RED + "Error: Unauthorized access. Faculty ID required." + PersonController.RESET);
            return;
        }

        String staffID = "S" + (10000 + (int) (Math.random() * 99999));
        String name;
        String ic;
        String password;
        String email;
        String contactNumber;
        String subjectID;

        PersonController.clearScreen();
        System.out.println(PersonController.BLUE + "Type exit to exit" + PersonController.RESET);
        System.out.println("----------Academic Staff Registration----------");
        System.out.println("Please fill in the form:");

        // Name
        while (true) {
            System.out.print("Name\t\t\t: ");
            name = PersonController.scanner.nextLine();
            if (name.equalsIgnoreCase("exit")) {
                System.out.print("Exiting");
                PersonController.delay();
                return;
            }
            if (name.length() >= 3) {
                break;
            } else {
                System.out.println(
                        PersonController.RED + "Error: Provide name more than 3 characters" + PersonController.RESET);
            }
        }

        // IC
        while (true) {
            System.out.print("IC (no - )\t\t: ");
            ic = PersonController.scanner.nextLine();
            if (ic.length() == 12) {
                break;
            } else {
                System.out.println(PersonController.RED + "Error: Invalid Format IC" + PersonController.RESET);
            }
        }

        // Email
        while (true) {
            System.out.print("Email\t\t\t: ");
            email = PersonController.scanner.nextLine();
            String EmailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            Pattern EmailPattern = Pattern.compile(EmailRegex);
            Matcher EmailMatcher = EmailPattern.matcher(email);
            if (EmailMatcher.matches()) {
                break;
            } else {
                System.out.println(PersonController.RED + "Error: Invalid email format." + PersonController.RESET);
            }
        }

        // Password
        while (true) {
            password = new String(console.readPassword("Create a New Password: "));
            System.out.print(PersonController.BLUE + "Show Password? (Y) " + PersonController.RESET);
            String confirm = PersonController.scanner.nextLine();
            if (confirm.equalsIgnoreCase("Y")) {
                System.out.println("Password: " + password);
            }

            String PasswordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.])(?=\\S+$).{8,}$";
            Pattern PasswordPattern = Pattern.compile(PasswordRegex);
            Matcher PasswordMatcher = PasswordPattern.matcher(password);

            if (PasswordMatcher.matches()) {
                break;
            } else {
                System.out.println(PersonController.RED + "Error: Password too weak.");
                System.out.println(
                        "Password must contain digit, lowercase, uppercase, special character, no whitespace, min 8 characters."
                                + PersonController.RESET);
            }
        }


        // Contact
        while (true) {
            System.out.print("Contact Number(no - ) : ");
            contactNumber = PersonController.scanner.nextLine();
            String ContactRegex = "^\\d{10,11}$";
            Pattern ContactPattern = Pattern.compile(ContactRegex);
            Matcher ContactMatcher = ContactPattern.matcher(contactNumber);
            if (ContactMatcher.matches()) {
                break;
            } else {
                System.out.println(PersonController.RED + "Error: Invalid contact number format. No dash (-)."
                        + PersonController.RESET);
            }
        }

        // Subject ID
        System.out.print("Subject ID\t\t: ");
        subjectID = PersonController.scanner.nextLine();

        // Database insertion
        try {
            Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass);
            String sql = "INSERT INTO staff (StaffID, Name, IC, Email, Password, Contact, SubjectID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, staffID);
            statement.setString(2, name);
            statement.setString(3, ic);
            statement.setString(4, email);
            statement.setString(5, password);
            statement.setString(6, contactNumber);
            statement.setString(7, subjectID);

            statement.executeUpdate();
            System.out.println(
                    PersonController.GREEN + "Academic Staff Registered Successfully!" + PersonController.RESET);
            System.out.println("Staff ID: " + PersonController.GREEN + staffID + PersonController.RESET);
            System.out.print("Press Enter to continue...");
            while (true) {
                String input = PersonController.scanner.nextLine();
                if (input.isEmpty()) {
                    System.out.print("Redirecting...");
                    PersonController.delay();
                    break;
                } else {
                    System.out.print("Please press Enter only to continue...");
                }
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(
                    PersonController.RED + "Error: System busy. Please try again later." + PersonController.RESET);
            PersonController.delay();
        }
    }

    public void updateProfile(String loggedInAcaStaffID) {
        String sql = displayOptionModify();
        // sql connection + run query
        if (sql == null) {
            return;
        }
        try {
            Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, loggedInAcaStaffID);

            if (statement.executeUpdate() > 0) {
                System.out.println(PersonController.GREEN + "Updated Successfully" + PersonController.RESET);
                System.out.print("Redirecting to Academic Staff Menu");
                PersonController.delay();
            } else {
                System.out.println(PersonController.RED + "Unable to Update your information. Please Try Again"
                        + PersonController.RESET);
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.print(PersonController.RED + "Error: System busy. Please Try Again"
                    + PersonController.RESET);
            delay();
        }

    }

    public String displayOptionModify() {
        String sql = "";
        while (true) {
            PersonController.clearScreen();
            System.out.println("----------Edit your information----------");
            System.out.println("1. Change email");
            System.out.println("2. Change password");
            System.out.println("3. Change Contact");
            System.out.println("4. Change Username");
            System.out.println("5. Return to Academic Staff Menu");
            System.out.print("Your Option: ");
            int option = PersonController.scanner.nextInt();
            PersonController.scanner.nextLine();
            if (option == 1) {
                sql = modifyPersonEmail("staff", "StaffID");
            } else if (option == 2) {
                sql = modifyPersonPassword("staff", "StaffID");
            } else if (option == 3) {
                sql = modifyContactNumber("staff", "StaffID");
            } else if (option == 4) {
                sql = modifyPersonName("staff", "StaffID");
            } else if (option == 5) {
                System.out.print("Returning to Academic Staff Menu");
                PersonController.delay();
                return null;
            } else {
                System.out.println(
                        PersonController.RED + "Error:Invalid Options. Please Try Again" + PersonController.RESET);
                System.out.print("Redirecting to Edit Menu");
                PersonController.delay();
                continue;
            }
            return sql;

        }
    }

    public void deleteProfile(String facID) {// delete by faculty member

          System.out.println("Type 'exit' to quit or choose an option.");
        String acaStaffID = displayDeleteProfile();
        if (acaStaffID == null) {// make sure it does not continue if acaStaffID is not found
            return;
        }
        if (!isAcaStaffInFacultyCourse(acaStaffID, facID)) {
            System.out.println(PersonController.RED + "Error: You are not authorized to delete this student!"
                    + PersonController.RESET);
            System.out.print("Redirecting to Faculty Member Menu");
            delay();
            return;
        }

        String input = PersonController.scanner.nextLine();
                        if (input.equalsIgnoreCase("exit")) {
                            System.out.print("Exiting");
                            PersonController.delay();
                            System.exit(0);
                        }

        String sql = "DELETE FROM staff WHERE StaffID = ?";
        try {
            Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, acaStaffID);

            if (statement.executeUpdate() > 0) {
                System.out.println(PersonController.GREEN + "Profile Deleted Successfully" + PersonController.RESET);
                System.out.print("Redirecting to Faculty Member Menu");
                PersonController.delay();
            } else {
                System.out.println(PersonController.RED + "Unable to Delete this Academic Staff. Please Try Again"
                        + PersonController.RESET);
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.print(PersonController.RED + "Error: System busy. Please Try Again"
                    + PersonController.RESET);
            delay();
        }
    }

    public boolean isAcaStaffInFacultyCourse(String acaStaffID, String facultyID) {
        String sql = "SELECT COUNT(*) AS match_count " +
                "FROM staff " +
                "INNER JOIN subject ON staff.SubjectID = subject.SubjectID " +
                "INNER JOIN facultyinfo ON subject.CourseID = facultyinfo.CourseID " +
                "WHERE staff.StaffID = ? AND facultyinfo.FacultyID = ?;";

        try {
            Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, acaStaffID);
            statement.setString(2, facultyID);

            ResultSet resultSet = statement.executeQuery();
            boolean exists = false;

            if (resultSet.next()) {
                exists = resultSet.getInt(1) > 0;
            }

            resultSet.close();
            statement.close();
            connection.close();

            return exists; // Return true, faculty can delete the student
        } catch (SQLException e) {
            System.out.print(PersonController.RED + "Error: System busy. Please Try Again"
                    + PersonController.RESET);
            PersonController.delay();
            return false;
        }
    }

    public String displayDeleteProfile() {
        PersonController.clearScreen();
        System.out.println("----------Delete Academic Staff Profile----------");
        System.out.print("Enter StaffID to delete: ");
        String acaStaffID = PersonController.scanner.nextLine();
        String option = "";
        if (IsExistAcaStaff(acaStaffID) == true) {
            while (!option.equalsIgnoreCase("Y") && !option.equalsIgnoreCase("N")) {
                System.out.print(PersonController.BLUE + "Are you sure you want to delete Academic Staff " + acaStaffID
                        + "? Yes(Y) || No(N): " + PersonController.RESET);
                option = PersonController.scanner.nextLine();
            }
            if (option.equalsIgnoreCase("Y")) {
                return acaStaffID;
            } else {
                System.out.print("Redirecting to Faculty Member Menu");
                PersonController.delay();
                return null;
            }

        } else {
            System.out.println(
                    PersonController.RED + "Error: Unable to find Academic Staff " + acaStaffID
                            + PersonController.RESET);
            System.out.print("Redirecting to Faculty Member Menu");
            PersonController.delay();
            return null;
        }

    }

    public boolean IsExistAcaStaff(String acaStaffID) {
        String sql = "SELECT StaffID FROM staff WHERE StaffID = ?";
        try {
            Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, acaStaffID);
            ResultSet resultSet = statement.executeQuery();

            boolean exists = resultSet.next(); // if academic staff found, return true

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

    public void readStudent(String loggedInAcaStaffID) {
        try {
            Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass);
            String sql = "SELECT s.* " +
                    "FROM studentinfo s " +
                    "JOIN subject subj ON s.CourseID = subj.CourseID " + // Link CourseID to Subject table
                    "JOIN staff stf ON subj.SubjectID = stf.SubjectID " + // Link SubjectID to Staff
                    "WHERE stf.StaffID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, loggedInAcaStaffID);
            ResultSet resultSet = statement.executeQuery();
            PersonController.clearScreen();
            int numOfStu = 0;
            System.out.println("----------All Student Information----------");
            while (resultSet.next()) {
                numOfStu++;
                System.out.println("Student " + numOfStu + ": ");
                System.out.println("Student ID: " + resultSet.getString("StudentID"));
                System.out.println("Student Name: " + resultSet.getString("Name"));
                System.out.println("Student Contact: " + resultSet.getString("Contact"));
                System.out.println("CourseID: " + resultSet.getString("CourseID"));
                System.out.println("Year Of Study: " + resultSet.getString("YearOfStudy"));
                System.out.println("---------------------------------------");

            }
            System.out.print("Press enter to continue");

            while (true) {
                String input = PersonController.scanner.nextLine();
                if (input.isEmpty()) { // input empty
                    System.out.print("Redirecting to Academic Staff Menu");
                    PersonController.delay();
                    break;
                } else {
                    System.out.print("Please press Enter only to continue");

                }
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.print(PersonController.RED + "Error: System busy. Please Try Again" + PersonController.RESET);
            PersonController.delay();
        }

    }

    // Staff key in all student which need to check the staffID subjectID same with
    // the student CourseID and SubjectID using while loop() key in the result one
    // by one of the student while key in the result need to show StudentID and the
    // SubjectName

    /*
     * public void addResult(String staffID) {
     * PersonController.clearScreen();
     * System.out.println("----------Grade Input Form----------");
     * try {
     * Connection connection = DriverManager.getConnection(PersonController.dbURL,
     * PersonController.dbName,
     * PersonController.dbpass);
     * // First, fetch staff SubjectID from the database using staffID
     * String staffSubjectID = academicStaff.getSubjectID();
     * String staffQuery = "SELECT SubjectID FROM staff WHERE StaffID = ?";
     * try (PreparedStatement staffStmt = connection.prepareStatement(staffQuery)) {
     * staffStmt.setString(1, staffID);
     * ResultSet staffResult = staffStmt.executeQuery();
     * if (staffResult.next()) {
     * staffSubjectID = staffResult.getString("SubjectID");
     * // return;
     * }
     * staffResult.close();
     * }
     * 
     * if (staffSubjectID.isEmpty()) {
     * System.out.println("Error: Staff has no assigned SubjectID.");
     * return;
     * }
     * 
     * // Fetch student data with their SubjectID from the Course table
     * String studentQuery =
     * "SELECT s.StudentID, s.CourseID, subj.SubjectID, subj.SubjectName " +
     * "FROM studentinfo s " +
     * "JOIN Course c ON s.CourseID = c.CourseID " +
     * "JOIN Subject subj ON subj.CourseID = c.CourseID";
     * try (PreparedStatement studentStmt =
     * connection.prepareStatement(studentQuery);
     * ResultSet resultSet = studentStmt.executeQuery()) {
     * String checkQuery =
     * "SELECT marksObtained FROM result WHERE StudentID = ? AND SubjectID = ?";
     * String insertQuery =
     * "INSERT INTO result (StudentID, SubjectID, marksObtained) VALUES (?, ?, ?)";
     * String updateQuery =
     * "UPDATE result SET marksObtained = ? WHERE StudentID = ? AND SubjectID = ?";
     * 
     * while (resultSet.next()) {
     * String studentID = resultSet.getString("StudentID");
     * String subjectID = resultSet.getString("SubjectID");
     * String subjectName = resultSet.getString("SubjectName");
     * 
     * // Match Staff SubjectID with Student SubjectID
     * if (staffSubjectID.equals(subjectID)) {
     * System.out.println("StudentID: " + studentID);
     * System.out.println("Subject: " + subjectID + " " + subjectName);
     * 
     * // Get result input
     * System.out.print("Enter result for " + studentID + ": ");
     * int result = PersonController.scanner.nextInt();
     * if (result < 0 || result > 100) {
     * System.out.println("Error: Marks must be between 0 - 100");
     * continue; // Skip to next student
     * }
     * 
     * 
     * // Check if result already exists
     * try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
     * checkStmt.setString(1, studentID);
     * checkStmt.setString(2, subjectID);
     * ResultSet rs = checkStmt.executeQuery();
     * 
     * if (rs.next()) {
     * // Perform UPDATE
     * try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery))
     * {
     * updateStmt.setInt(1, result);
     * updateStmt.setString(2, studentID);
     * updateStmt.setString(3, subjectID);
     * updateStmt.executeUpdate();
     * System.out.println(
     * PersonController.GREEN + "Result Updated!" + PersonController.RESET);
     * }
     * } else {
     * // Perform INSERT
     * try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery))
     * {
     * insertStmt.setString(1, studentID);
     * insertStmt.setString(2, subjectID);
     * insertStmt.setInt(3, result);
     * insertStmt.executeUpdate();
     * System.out
     * .println(PersonController.GREEN + "Result Added!" + PersonController.RESET);
     * }
     * }
     * 
     * } catch (SQLException e) {
     * System.out.println(PersonController.RED +
     * "Error: System busy. Please Try Again"
     * + PersonController.RESET);
     * PersonController.delay();
     * }
     * }
     * 
     * }
     * 
     * } catch (SQLException e) {
     * System.out.println(
     * PersonController.RED + "Error: System busy. Please Try Again" +
     * PersonController.RESET);
     * e.printStackTrace();
     * PersonController.delay();
     * }
     * } catch (SQLException e) {
     * System.out.println(PersonController.RED +
     * "Error: System busy. Please Try Again" + PersonController.RESET);
     * e.printStackTrace();
     * }
     * while (true) {
     * String input = PersonController.scanner.nextLine();
     * if (input.isEmpty()) { // input empty
     * System.out.print("Returning to Academic Staff Menu");
     * PersonController.delay();
     * break;
     * } else {
     * System.out.print("Please press Enter only to continue");
     * 
     * }
     * }
     * 
     * }
     */

}
