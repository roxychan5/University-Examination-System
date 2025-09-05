import java.sql.*;
import java.util.InputMismatchException;

public class FacMemberController extends PersonController implements PersonAction {

    private String facMemberID;
    FacultyMember facMember;
    Result result;
    Exam exam;
    Course course;
    AcademicStaff academicStaff;
    Student student;

  public void FacMemberMenu(String facMemberID) {
    this.facMemberID = facMemberID;
    this.facMember = new FacultyMember();
    
    while (true) {
        PersonController.clearScreen();
        System.out.println("----------Faculty Member Menu----------");
        System.out.println("1. View Profile");
        System.out.println("2. Update Profile");
        System.out.println("3. Delete Academic Staff Profile");
        System.out.println("4. Student Menu");
        System.out.println("5. Result Menu");
        System.out.println("6. Exam Menu");
        System.out.println("7. Logout");
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
                displayProfile(facMemberID);
                break;
            case 2:
                updateProfile(facMemberID);
                break;
            case 3:
                AcademicStaffController staff = new AcademicStaffController();
                  // Handle 'exit' command 
                  
                  staff.deleteProfile(facMemberID);
                        
                break;
            case 4:
                StudentController student = new StudentController();
                int stuOption;
                while (true) {
                    PersonController.clearScreen();
                    System.out.println("----------Student Menu----------");
                    System.out.println("1. Delete Student Profile");
                    System.out.println("2. View All Students");
                    System.out.print("Select option: ");
                    try {
                        stuOption = PersonController.scanner.nextInt();
                
                        if (stuOption == 1) {
                            student.deleteProfile(facMemberID);
                            break; // exit student menu
                        } else if (stuOption == 2) {
                            student.showAllStudent(facMemberID);
                            break; // exit student menu
                        } else {
                            System.out.println(PersonController.RED + "Invalid Student Menu Option! Please choose 1 or 2." + PersonController.RESET);
                        } PersonController.scanner.nextLine(); // consume newline
                         // Handle 'exit' command
                        System.out.println("Type 'exit' to quit or choose an option.");
                        String enput;
                        enput = PersonController.scanner.nextLine();
                        if (enput.equalsIgnoreCase("exit")) {
                            System.out.print("Exiting");
                            PersonController.delay();
                            System.exit(0);
                        }
                    } catch (InputMismatchException e) {
                        System.out.println(PersonController.RED + "Error: Please enter a valid number (1 or 2)!" + PersonController.RESET);
                        PersonController.scanner.nextLine(); // clear invalid input
                    }
                }
                break;
            case 5:
                ResultController result = new ResultController();
                result.ResultMenu(facMemberID, "Faculty");
                break;
            case 6:
                ExamController exam = new ExamController();
                exam.ExamMenu(facMemberID, "Faculty");  
                break;
            case 7:
                System.out.print("Logging out...");
                PersonController.delay();
                facMember = null;
                displayMainMenu();
                return; // Exit the menu loop
            default:
                System.out.println(PersonController.RED + "Error: Invalid choice! Please choose 1-8." + PersonController.RESET);
        }

        // Prompt user to press Enter before showing menu again
        System.out.print("Press Enter to return to Faculty Member Menu...");
        PersonController.scanner.nextLine();
    }
}


    public boolean showMenu(String facMemberID, String Password) {
        try {
            Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass);
            String sql = "SELECT * FROM facultyinfo WHERE FacultyID = ? AND Password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, facMemberID);
            statement.setString(2, Password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println(PersonController.GREEN + "Login Successfully!!!");
                System.out.println("Welcome " + resultSet.getString("Name") + "!" + PersonController.RESET);
                System.out.print("Redirecting to Faculy Member Menu");
                PersonController.delay();
                return true;
            } else {
                System.out.println(
                        PersonController.RED + "Error:Invalid Faculty ID or Password" + PersonController.RESET);
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

    public void displayProfile(String facMemberID) {
        readFacMember(facMemberID);
        System.out.println("----------Faculty Member Information----------");
        System.out.println(facMember.toString());
        System.out.println("----------------------------------------------");
        System.out.print("Press enter to continue");

        while (true) {
            String input = PersonController.scanner.nextLine();
            if (input.isEmpty()) { // input empty
                System.out.print("Redirecting to Faculty Member Menu");
                PersonController.delay();
                break;
            } else {
                System.out.println("Please press Enter only to continue...");
            }
        }
    }

    public void readFacMember(String facMemberID) {
        try {
            // Establish a connection
            Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass);

            // SQL query
            String sql = "SELECT * FROM facultyinfo WHERE FacultyID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, facMemberID);

            // Execute query and check results
            ResultSet resultSet = statement.executeQuery();
            PersonController.clearScreen();
            if (resultSet.next()) {
                facMember.setID(resultSet.getString("FacultyID"));
                facMember.setName(resultSet.getString("Name"));
                facMember.setIc(resultSet.getString("IC"));
                facMember.setCourseID(resultSet.getString("CourseID"));
                facMember.setEmail(resultSet.getString("Email"));
                facMember.setContactNumber(resultSet.getString("Contact"));

            } else {
                System.out.println("Error:Invalid ID or password. Please Try Again!!!");
                System.out.println();
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.print(PersonController.RED + "Error: System busy. Please Try Again"
                    + PersonController.RESET);
            PersonController.delay();
        }
    }

    public void updateProfile(String facMemeberID) {
        String sql = displayOptionModify();
        if (sql == null) {
            return;
        }
        try {
            Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, facMemeberID);

            if (statement.executeUpdate() > 0) {
                System.out.println(PersonController.GREEN + "Updated Successfully" + PersonController.RESET);
                System.out.print("Redirecting to Faculty Member Menu");
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
            System.out.println("5. Return to Faculty Member Menu");
            System.out.print("Your Option: ");
            int option = PersonController.scanner.nextInt();
            PersonController.scanner.nextLine();
            if (option == 1) {
                sql = modifyPersonEmail("facultyinfo", "FacultyID");
            } else if (option == 2) {
                sql = modifyPersonPassword("facultyinfo", "FacultyID");
            } else if (option == 3) {
                sql = modifyContactNumber("facultyinfo", "FacultyID");
            } else if (option == 4) {
                sql = modifyPersonName("facultyinfo", "FacultyID");
            } else if (option == 5) {
                System.out.print("Returning to Faculty Member Menu");
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

    public void addFacMember() {

    }

}