import java.sql.*;
import java.util.InputMismatchException;
import java.util.regex.*;
import java.io.Console;

//create read update delete(CRUD)
public class StudentController extends PersonController implements PersonAction {
    Console console = System.console();
    private String studentID;
    Student student;

    public void StudentMenu(String studentID) {
        this.studentID = studentID;
        this.student = new Student();
        while (true) {
            PersonController.clearScreen();
            System.out.println("----------Student Menu----------");// after login
            System.out.println("1. Student Information");
            System.out.println("2. Edit Information");
            System.out.println("3. Examination Info");
            System.out.println("4. Examination Result");
            System.out.println("5. Register Course");
            System.out.println("6. Log Out");
            System.out.print("Your choice: ");
             int choice;
        try {
            choice = PersonController.scanner.nextInt();
            PersonController.scanner.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            System.out.println(PersonController.RED + "Error: Please enter a valid number!" + PersonController.RESET);
            PersonController.scanner.nextLine(); // Clear invalid input
            continue; // Go back to menu
        }
            switch (choice) {
                case 1:
                    displayProfile(studentID);
                    break;
                case 2:
                    updateProfile(studentID);
                    break;
                case 3:
                    readExam(studentID);
                    break;
                case 4:
                    readResult(studentID);
                    break;
                case 5:
                    registerCourse(studentID);
                    break;
                case 6:
                    System.out.print("Logging out");
                    PersonController.delay();
                    student = null;
                    displayMainMenu();
                default:
                    System.out.println(PersonController.RED + "Error:Invalid choice" + PersonController.RESET);
                    System.out.print("Redirecting to Student Menu");
                    PersonController.delay();
            }// Prompt user to press Enter before showing menu again
        System.out.print("Press Enter to return to Menu...");
        PersonController.scanner.nextLine();
    }
        }
    

    public void StuAccess() {// Login or Register
        Person student = new Student();

        while (true) {
            PersonController.clearScreen();
            System.out.println("----------Student Access Menu----------");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Back to Main Menu");
            System.out.print("Your choice: ");

            int choice;  
            try {
            choice = PersonController.scanner.nextInt();
            PersonController.scanner.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            System.out.println(PersonController.RED + "Error: Please enter a valid number!" + PersonController.RESET);
            PersonController.scanner.nextLine(); // Clear invalid input
            continue; // Go back to menu
        }
            switch (choice) {
                case 1:// login
                    PersonController.clearScreen();
                    System.out.println(PersonController.BLUE + "Type exit to exit" + PersonController.RESET);
                    System.out.println("----------Student Login----------");
                    System.out.print("Student ID: ");
                    student.setID(PersonController.scanner.nextLine());
                    if (student.getID().equalsIgnoreCase("exit")) {
                        System.out.print("Exiting");
                        PersonController.delay();
                        return;
                    }
                    // hide input --> console.readPassword(a method that built-in in java)
                    // it store in array char[] for the input
                    // new String() --> convert array char[] to String

                    String stuPassword = new String(console.readPassword("Password: "));
                    System.out.print(PersonController.BLUE + "Show Password?(Y) " + PersonController.RESET);
                    String confirm = PersonController.scanner.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        System.out.println("Password: " + stuPassword);
                        System.out.print("Press Enter to continue");
                        while (true) {
                            String input = PersonController.scanner.nextLine();
                            if (input.isEmpty()) { // input empty
                                break;
                            } else {
                                System.out.print("Please press Enter only to continue");

                            }
                        }

                    }
                    student.setPassword(stuPassword);
                    StudentController studentController = new StudentController();
                    if (studentController.showMenu(student.getID(), student.getPassword()) == true) {
                        studentController.StudentMenu(student.getID());
                    }
                    break;
                case 2:// register
                    addStudent();
                    break;
                case 3:// back
                    System.out.print("Redirecting to Student Menu");
                    PersonController.delay();
                    return;
                default:
                    System.out.println(PersonController.RED + "Error:Invalid choice" + PersonController.RESET);
                    System.out.print("Redirecting to Student Access Menu");
                    PersonController.delay();
            }
            // Prompt user to press Enter before showing menu again
        System.out.print("Press Enter to return to Menu...");
        PersonController.scanner.nextLine();
        }
    }

    public boolean showMenu(String studentID, String Password) {// login
        try {
            Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass);
            String sql = "SELECT * FROM studentinfo WHERE studentID = ? AND Password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, studentID);
            statement.setString(2, Password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {// used to return true or false
                System.out.println(PersonController.GREEN + "Login Successfully!!!");
                System.out.println("Welcome " + resultSet.getString("Name") + "!" + PersonController.RESET);
                System.out.print("Redirecting to Student Menu");
                PersonController.delay();
                return true;
            } else {
                System.out.println(
                        PersonController.RED + "Error:Invalid Student ID or Password" + PersonController.RESET);
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

    public void registerCourse(String loggedInStudentID) {
        CourseController CourseSelection = new CourseController();
        CourseSelection.CourseMenu(loggedInStudentID);// CourseMenu
    }

    public void addStudent() {
        // Local variable cannot declare modifier
        studentID = "24WMD" + (10000 + (int) (Math.random() * 99999));
        String name;
        String ic;
        int age;
        String gender;
        String password;
        String email;
        String contactNumber;
        int yearOfStudy = 1;
        PersonController.clearScreen();
        System.out.println(PersonController.BLUE + "Type exit to exit" + PersonController.RESET);
        System.out.println("----------Registration form----------");
        System.out.println("Please fill in the form:");
        while (true) {
            System.out.print("Name\t\t\t: ");
            name = PersonController.scanner.nextLine();
            if (name.equalsIgnoreCase("exit")) {
                System.out.print("Exiting");
                PersonController.delay();
                return; // Return "EXIT" if user chooses to exit
            }
            if (name.length() >= 3) {
                break;
            } else {
                System.out.println(
                        PersonController.RED + "Error:Provide name more than 3 character" + PersonController.RESET);
            }
        }

        // IC validation
        while (true) {
            System.out.print("IC (no - )\t\t: ");
            ic = PersonController.scanner.nextLine();
            if (ic.length() == 12) {
                break;
            } else {
                System.out.println(PersonController.RED + "Error:Invalid Format IC" + PersonController.RESET);
            }
        }

        // Age validation
        while (true) {
            System.out.print("Age\t\t\t: ");
            age = PersonController.scanner.nextInt();
            PersonController.scanner.nextLine();
            if (age < 18) {
                System.out.println(PersonController.RED + "Unable to register at this age." + PersonController.RESET);
                continue;
            } else if (age > 100) {
                System.out.println(PersonController.RED + "Are you sure that you " + age + " years old?");
                System.out.println("Please Give Your Actual Age" + PersonController.RESET);
                continue;
            } else {
                break;
            }

        }

        // Gender validation gay
        while (true) {
            System.out.print("Gender(M/F)\t\t: ");
            gender = PersonController.scanner.nextLine();
            if (gender.equalsIgnoreCase("M")) {
                gender = "Male";
                break;
            } else if (gender.equalsIgnoreCase("F")) {
                gender = "Female";
                break;
            } else if (gender.length() > 1) {
                System.out.println(PersonController.RED + "Enter M or F only" + PersonController.RESET);
                continue;
            } else {
                System.out.println(PersonController.RED + "Enter M or F only" + PersonController.RESET);
                continue;
            }
        }

        // Email validation
        while (true) {
            System.out.print("Email\t\t\t: ");
            email = PersonController.scanner.nextLine();
            String EmailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            Pattern EmailPattern = Pattern.compile(EmailRegex);
            Matcher EmailMatcher = EmailPattern.matcher(email);
            if (EmailMatcher.matches()) {
                break;
            } else {
                System.out.println(PersonController.RED + "Error:Invalid email format." + PersonController.RESET);
            }
        }

        // Password validation
        while (true) {
            password = new String(console.readPassword("Create a New Password\t\t: "));
            System.out.print(PersonController.BLUE + "Show Password?(Y) " + PersonController.RESET);
            String confirm = PersonController.scanner.nextLine();
            if (confirm.equalsIgnoreCase("Y")) {
                System.out.print("Password: " + password);
                PersonController.scanner.nextLine();

            }

            String PasswordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.])(?=\\S+$).{8,}$";
            Pattern PasswordPattern = Pattern.compile(PasswordRegex);
            Matcher PasswordMatcher = PasswordPattern.matcher(password);

            if (PasswordMatcher.matches()) {
                break;
            } else {
                System.out.println(PersonController.RED + "Error:Password are too weak!!!");
                System.out.println(
                        "Password must contain a digit, a lower case letter, a upper case letter, a special character, no whitespace, and at least 8 characters"
                                + PersonController.RESET);
            }

        }

        // Contact validation
        while (true) {
            System.out.print("Contact Number(no - ) : ");// Validation Contact
            contactNumber = PersonController.scanner.nextLine();
            String ContactRegex = "^\\d{10,11}$";
            Pattern ContactNumberPattern = Pattern.compile(ContactRegex);
            Matcher ContactNumberMatcher = ContactNumberPattern.matcher(contactNumber);
            if (ContactNumberMatcher.matches()) {
                break;
            } else {
                System.out.println(PersonController.RED + "Error:Invalid Format Contact Number!");
                System.out.println("Reminder: No dash(-)" + PersonController.RESET);
            }

        }
        // db
        try {

            Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass);
            // SQL query
            String sql = "INSERT INTO studentinfo (StudentID, Name, IC, Age ,Gender, Email, Password, Contact, YearOfStudy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // insert into database
            statement.setString(1, studentID);
            statement.setString(2, name);
            statement.setString(3, ic);
            statement.setInt(4, age);
            statement.setString(5, gender);
            statement.setString(6, email);
            statement.setString(7, password);
            statement.setString(8, contactNumber);
            statement.setInt(9, yearOfStudy);
            statement.executeUpdate();
            System.out.println(PersonController.GREEN + "Register Successfully!!!" + PersonController.RESET);
            System.out.println("Your Student ID is: " + PersonController.GREEN + studentID + PersonController.RESET);
            System.out.println("Please remember your Student ID");
            System.out.print("Press Enter to continue");
            while (true) {// input with enter only
                String input = PersonController.scanner.nextLine();
                if (input.isEmpty()) {
                    System.out.print("Redirecting to Student Menu");
                    PersonController.delay();
                    break;
                } else {
                    System.out.print("Please press Enter only to continue");
                }
            }
            /////

            // close resource
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.print(PersonController.RED + "Error: System busy. Please Try Again"
                    + PersonController.RESET);
            delay();
        }

    }

    public void displayProfile(String loggedInStudentID) {// from interface class
        getStuInfo(loggedInStudentID);
        System.out.println("----------Student Information----------");
        System.out.println(student.toString());
        System.out.println("---------------------------------------");
        System.out.print("Press enter to continue");
        while (true) {
            String input = PersonController.scanner.nextLine();
            if (input.isEmpty()) { // input empty
                System.out.print("Redirecting to Student Menu");
                PersonController.delay();
                break;
            } else {
                System.out.println("Please press Enter only to continue...");
            }
        }
    }

    public void getStuInfo(String loggedInStudentID) {
        try {
            // Establish a connection
            Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass);
            // sql
            String sql = "SELECT s.*, c.CourseName " +
                    "FROM studentinfo s " +
                    "LEFT JOIN Course c ON s.CourseID = c.CourseID " +
                    "WHERE s.StudentID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);// prepare query
            statement.setString(1, loggedInStudentID);// based on the ?(arrangement is important)
            ResultSet resultSet = statement.executeQuery();// run query

            PersonController.clearScreen();
            if (resultSet.next()) {// get data from database
                student.setID(loggedInStudentID);
                student.setName(resultSet.getString("Name"));
                student.setIc(resultSet.getString("IC"));
                student.setAge(resultSet.getInt("Age"));
                student.setGender(resultSet.getString("Gender"));
                student.setEmail(resultSet.getString("Email"));
                student.setContactNumber(resultSet.getString("Contact"));
                if (resultSet.getString("CourseName") == null || resultSet.getString("CourseName").isEmpty()
                        || resultSet.getString("CourseID") == null | resultSet.getString("CourseID").isEmpty()) {
                    student.setCourseName(PersonController.BLUE + "Please Register Your Course at Student Menu"
                            + PersonController.RESET);
                    student.setCourseID("-");
                } else {
                    student.setCourseName(resultSet.getString("CourseName"));
                    student.setCourseID(resultSet.getString("CourseID"));
                }
                student.setYearOfStudy(resultSet.getInt("YearOfStudy"));
            } else {
                System.out.println("No student found with ID: " + loggedInStudentID);
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

    public void updateProfile(String loggedInStudentID) {
        String sql = displayOptionModify();
        // sql connection + run query
        if (sql == null) {
            return;
        }
        try {
            Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, loggedInStudentID);

            if (statement.executeUpdate() > 0) {
                System.out.println(PersonController.GREEN + "Updated Successfully" + PersonController.RESET);
                System.out.print("Redirecting to Student Menu");
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
                    System.out.println(e);
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
            System.out.println("5. Return to Student Menu");
            System.out.print("Your Option: ");
            int option = PersonController.scanner.nextInt();
            PersonController.scanner.nextLine();
            if (option == 1) {
                sql = modifyPersonEmail("studentinfo", "StudentID");
            } else if (option == 2) {
                sql = modifyPersonPassword("studentinfo", "StudentID");
            } else if (option == 3) {
                sql = modifyContactNumber("studentinfo", "StudentID");
            } else if (option == 4) {
                sql = modifyPersonName("studentinfo", "StudentID");
            } else if (option == 5) {
                System.out.print("Returning to Student Menu");
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

    public void deleteProfile(String facID) {// for faculty Member delete user profile
        String studentID = displayDeleteProfile();
        if (studentID == null) {// make sure it does not continue if studentID not found
            return;
        }
        if (!isStudentInFacultyCourse(studentID, facID)) {
            System.out.println(PersonController.RED + "Error: You are not authorized to delete this student!"
                    + PersonController.RESET);
            System.out.print("Redirecting to Faculty Member Menu");
            delay();
            return;
        }

        String sql = "DELETE FROM studentinfo WHERE StudentID = ?";
        try {
            Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, studentID);

            if (statement.executeUpdate() > 0) {
                System.out.println(PersonController.GREEN + "Profile Deleted Successfully" + PersonController.RESET);
                System.out.print("Redirecting to Faculty Member Menu");
                PersonController.delay();
            } else {
                System.out.println(PersonController.RED + "Unable to Delete this Student. Please Try Again"
                        + PersonController.RESET);
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.print(PersonController.RED + "Error: System busy. Please Try Again"
                    + PersonController.RESET);
                    System.out.println(e);
            delay();
        }
    }

    public boolean isStudentInFacultyCourse(String studentId, String facultyID) {
        String sql = "SELECT COUNT(*) AS match_count FROM studentinfo " +
                "INNER JOIN facultyinfo ON studentinfo.CourseID = facultyinfo.CourseID " +
                "WHERE studentinfo.StudentID = ? AND facultyinfo.FacultyID = ?";

        try {
            Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, studentId);
            statement.setString(2, facultyID);

            ResultSet resultSet = statement.executeQuery();
            boolean exists = false;

            if (resultSet.next()) {
                exists = resultSet.getInt(1) > 0; // If count > 0, faculty manages this student's course
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
        System.out.println("----------Delete Student Profile----------");
        System.out.print("Enter StudentID to delete: ");
        PersonController.scanner.nextLine();
        String studentID = PersonController.scanner.nextLine();
        String option = "";
        if (IsExistStudent(studentID) == true) {
            while (!option.equalsIgnoreCase("Y") && !option.equalsIgnoreCase("N")) {
                System.out.print(PersonController.BLUE + "Are you sure you want to delete Student " + studentID
                        + "? Yes(Y) || No(N): " + PersonController.RESET);
                option = PersonController.scanner.nextLine();
            }
            if (option.equalsIgnoreCase("Y")) {
                return studentID;
            } else {
                System.out.print("Redirecting to Faculty Member Menu");
                PersonController.delay();
                return null;
            }

        } else {
            System.out.println(
                    PersonController.RED + "Error: Unable to find Student " + studentID + PersonController.RESET);
            System.out.print("Redirecting to Faculty Member Menu");
            PersonController.delay();
            return null;
        }

    }

    public static boolean IsExistStudent(String studentID) {
        String sql = "SELECT StudentID FROM studentinfo WHERE StudentID = ?";
        try {
            Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, studentID);
            ResultSet resultSet = statement.executeQuery();

            boolean exists = resultSet.next(); // if student found, return true

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

    public void readResult(String loggedInStudentID) {
        // PersonController.clearScreen();
        Result result = new Result();
        // ResultController resultController = new ResultController(loggedInStudentID);
        result.displayResults(result, studentID);
       
    }

    public void readExam(String loggedInStudentID) {
        PersonController.clearScreen();
        Exam exam = new Exam();
        exam.displayExamDetails(exam, loggedInStudentID);
        // some code should be continued here or deleted
        while (true) {
            String input = PersonController.scanner.nextLine();
            if (input.isEmpty()) { // input empty
                System.out.print("Redirecting to Student Menu");
                PersonController.delay();
                break;
            } else {
                System.out.print("Please press Enter only to continue");
            }
        }
    }

    public void showAllStudent(String facultyID) {// for faculty Member read all student at the course
        try {
            Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass);
            String sql = "SELECT s.* " +
                    "FROM studentinfo s " +
                    "JOIN facultyinfo f ON s.CourseID = f.CourseID " +
                    "WHERE f.FacultyID = ?";
            // retrive all student with same CourseID
            // Check facultyID's same courseID with student CourseID
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, facultyID);
            ResultSet resultSet = statement.executeQuery();
            PersonController.clearScreen();
            int numOfStu = 0;
            System.out.println("----------All Student Information----------");
            while (resultSet.next()) {
                numOfStu++;
                System.out.println("Student " + numOfStu + ": ");
                System.out.println("Student ID: " + resultSet.getString("StudentID"));
                System.out.println("Student Name: " + resultSet.getString("Name")); // Debugging
                if (resultSet.getString("Name") == null || resultSet.getString("Name").isEmpty()) {
                    System.out.println(PersonController.RED + "Error: Student Name is missing in the database!" + PersonController.RESET);
                }
                System.out.println("Student Contact: " + resultSet.getString("Contact"));
                System.out.println("CourseID: " + resultSet.getString("CourseID"));
                System.out.println("Year Of Study: " + resultSet.getString("YearOfStudy"));
                System.out.println("---------------------------------------");

            }
            System.out.print("Press enter to continue");

            while (true) {
                String input = PersonController.scanner.nextLine();
                if (input.isEmpty()) { // input empty
                    System.out.print("Redirecting to Faculty Member Menu");
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
}
