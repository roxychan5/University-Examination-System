import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.InputMismatchException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ExamController {
    private String examID;
    private Exam exam;
    private Date Datecreated;

    // Default constructor
    public ExamController() {
        this.examID = "";
    }

    // Parameterized Constructor
    public ExamController(String examID, Exam exam) {
        this.examID = examID;
        this.exam = exam;
    }

    // Getters
    public String getExamID() {
        return examID;
    }

    public Date getDateCreated() {
        return Datecreated;
    }

    // Setters
    public void setExamID(String examID) {
        this.examID = examID;
    }

    public void setDateCreated(Date datecreated) {
        this.Datecreated = datecreated;
    }

    public void ExamMenu(String id,String role) {// faculty
        this.examID = examID;
        this.exam = new Exam();
        String studentID;
        while (true) {
            PersonController.clearScreen();
            System.out.println("---------- Exam Menu ----------");
            System.out.println("1. Exam Information");
            System.out.println("2. Modify Exam");
            System.out.println("3. Delete Exam"); 
            System.out.println("4. Return to Main Menu"); 
            System.out.print("Your Option: ");

            int option;
            try {
                option = PersonController.scanner.nextInt();
                PersonController.scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(PersonController.RED + "Error: Please enter a valid number" + PersonController.RESET);
                PersonController.scanner.nextLine();
                continue;
            }

            switch (option) {
                case 1:                    
                    exam.displayExamDetailsForAcademicStaff(id); // Call method with valid Subject ID
                    break;
                case 2:
                    modifyExam();
                    break;
                case 3:
                    deleteExam();
                    break;
                case 4:
                    FacMemberController facMemberController = new FacMemberController();
                    facMemberController.FacMemberMenu(examID);
                    System.out.println("Redirecting");
                    PersonController.delay();
                default:
                    System.out.println(PersonController.RED + "Error: Invalid Option" + PersonController.RESET);
                    System.out.print("Press Enter to return to Menu...");
                    PersonController.delay();
            }
        }
    }

    public boolean showMenu() {
        return true;
    }

    public void modifyExam() {
        // Get current Exam ID at the start
        System.out.print("Enter current Exam ID to modify: ");
        String currentExamID = PersonController.scanner.nextLine();

        if (!validateExamID(currentExamID)) {
            System.out.println(PersonController.RED + "Error: Invalid Exam ID format" + PersonController.RESET);
            PersonController.delay();
            return;
        }
        
        while (true) {
            PersonController.clearScreen();
            System.out.println("----------Modify Exam Information----------");
            System.out.println("1. Change Exam Date");
            System.out.println("2. Change Exam Time");
            System.out.println("3. Change Exam Venue");
            System.out.println("4. Change Exam Duration");
            System.out.println("5. Return to Exam Menu");
            System.out.print("Your Option: ");

            int option;
            try {
                option = PersonController.scanner.nextInt(); // Get user input for option
                PersonController.scanner.nextLine(); // Consume newline character
            } catch (InputMismatchException e) {
                System.out.println(PersonController.RED + "Error: Please enter a valid number" + PersonController.RESET);
                PersonController.scanner.nextLine(); // Clear the invalid input
                PersonController.delay(); // Delay for user to read the error message
                continue;
            }

            if (option == 5) {
                System.out.print("Returning to Exam Menu");
                PersonController.delay(); // If user chooses to return to Exam Menu
                ExamMenu(examID,"Faculty"); // Return to Exam Menu
            }

            try {
                Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName, PersonController.dbpass);
                boolean success = false; // Flag to check if update was successful

                switch (option) {
                    case 1:
                        success = updateExamDate(connection, currentExamID); // Update Exam Date
                        break;
                    case 2:
                        success = updateExamTime(connection, currentExamID);
                        break;
                    case 3:
                        success = updateExamVenue(connection, currentExamID);
                        break;
                    case 4:
                        success = updateExamDuration(connection, currentExamID);
                        break;
                    default:
                        System.out.println(PersonController.RED + "Error: Invalid Option" + PersonController.RESET);
                        PersonController.delay(); // Invalid option, continue to next iteration
                        continue;
                }

                if (success) {
                    System.out.println(PersonController.GREEN + "Exam updated successfully!" + PersonController.RESET);
                } else {
                    System.out.println(PersonController.RED + "Failed to update exam" + PersonController.RESET);
                }
                PersonController.delay();

            } catch (SQLException e) {
                System.out.println(PersonController.RED + "Database error: " + e.getMessage() + PersonController.RESET);
                PersonController.delay();
            }
        }
    }

    /* updateExamID method to update the exam ID
    private boolean updateExamID(Connection connection, String currentExamID) throws SQLException {
        System.out.print("Enter new Exam ID (format EX000): ");
        String newExamID = PersonController.scanner.nextLine(); // Get new Exam ID

        // Validate the new Exam ID format
        if (!validateExamID(newExamID)) {
            System.out.println(PersonController.RED + "Error: Invalid Exam ID format" + PersonController.RESET); // Validate new Exam ID
            return false;
        }

        // Then update the exam record
        String sql = "UPDATE exams SET ExamID = ? WHERE ExamID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, newExamID); // Set new Exam ID
        stmt.setString(2, currentExamID); // Set parameters for the SQL statement

        return stmt.executeUpdate() > 0;
    }

    // Update foreign key references in other tables
    private void updateExamReferences(Connection connection, String oldExamID, String newExamID) throws SQLException {
        // Update students_exams table
        String sql = "UPDATE students_exams SET ExamID = ? WHERE ExamID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, newExamID); // set new Exam ID
        stmt.setString(2, oldExamID); // set old Exam ID
        stmt.executeUpdate(); // Execute the update statement
    } */

    // updateExamDate method to update the exam date
    private boolean updateExamDate(Connection connection, String currentExamID) throws SQLException {
        System.out.print("Enter new Exam Date (format YYYY-MM-DD): ");
        String newExamDate = PersonController.scanner.nextLine(); // Get new Exam Date

        // Validate format first
        if (!validateDate(newExamDate)) {
            System.out.println(PersonController.RED + "Error: Date must be in YYYY-MM-DD format (e.g., 2023-12-31)" + PersonController.RESET);
            return false;
        }

        LocalDate date;
        try {
            date = LocalDate.parse(newExamDate);
        } catch (DateTimeParseException e) {
            System.out.println(PersonController.RED + 
                "Error: Invalid date - " + e.getMessage() + 
                PersonController.RESET);
            return false;
        }

        // Check if date is in the future
        if (date.isBefore(LocalDate.now())) {
            System.out.println(PersonController.RED + 
                "Error: Exam date cannot be in the past" + 
                PersonController.RESET);
            return false;
        }

        // Check if date is unchanged
        String currentDate = getCurrentExamDate(connection, currentExamID);
        if (currentDate != null && currentDate.equals(newExamDate)) {
            System.out.println(PersonController.RED + "Notice: New date is same as current date" + PersonController.RESET);
            return false;
        }

        String sql = "UPDATE exams SET ExamDate = ? WHERE ExamID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newExamDate);
            stmt.setString(2, currentExamID);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println(PersonController.GREEN + 
                    "Exam date updated successfully!" + 
                    PersonController.RESET);
                return true;
            } else {
                System.out.println(PersonController.RED + 
                    "Error: Exam ID not found or date unchanged" + 
                    PersonController.RESET);
                return false;
            }
        }
    }

    // Helper method to get current exam date
    private String getCurrentExamDate(Connection connection, String examID) throws SQLException {
        String sql = "SELECT ExamDate FROM exams WHERE ExamID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, examID);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getString("ExamDate") : null;
        }
    }

    // updateExamTime method to update the exam time
    private boolean updateExamTime(Connection connection, String currentExamID) throws SQLException {
        System.out.print("Enter new Exam Time (8-20, in 24-hour format): ");
        String input = PersonController.scanner.nextLine();

        int newTime;
        try {
            newTime = Integer.parseInt(input);
            if (newTime < 8 || newTime > 20) {
                System.out.println(PersonController.RED + "Error: Time must be between 8 (8AM) and 20 (8PM)." + PersonController.RESET);
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println(PersonController.RED + "Error: Please enter a valid number." + PersonController.RESET);
            return false;
        }

        String sql = "UPDATE exams SET Time = ? WHERE ExamID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, newTime);
            stmt.setString(2, currentExamID);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                // Convert to AM/PM format
                String amPmTime = (newTime == 12) ? "12PM" :
                                (newTime == 0) ? "12AM" :
                                (newTime > 12) ? (newTime - 12) + "PM" :
                                newTime + "AM";
                System.out.println("Exam time updated successfully to " + amPmTime + ".");
                return true;
            } else {
                System.out.println(PersonController.RED + "Error: Exam ID not found." + PersonController.RESET);
                return false;
            }
        }
    }

    // updateExamVenue method to update the exam venue
    private boolean updateExamVenue(Connection connection, String currentExamID) throws SQLException {
        System.out.print("Enter new Exam Venue: "); // Get new Exam Venue
        String newVenue = PersonController.scanner.nextLine().trim(); // Trim to remove leading spaces

        if (newVenue.isEmpty()) {
            System.out.println(PersonController.RED + "Error: Venue cannot be empty" + PersonController.RESET);
            return false;
        }

        String sql = "UPDATE exams SET Venue = ? WHERE ExamID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newVenue); // Set new Exam Venue
            stmt.setString(2, currentExamID); // Set the current Exam ID as the condition for the update

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Exam venue updated successfully.");
                return true;
            } else {
                System.out.println(PersonController.RED + "Error: Exam ID not found." + PersonController.RESET);
                return false;
            }
        }
    }

    // updateExamDuration method to update the exam duration
    private boolean updateExamDuration(Connection connection, String currentExamID) throws SQLException {
        System.out.print("Enter new Exam Duration (must be 2 or 3 hours): ");
        String input = PersonController.scanner.nextLine().trim();

        int newDuration;
        try {
            newDuration = Integer.parseInt(input); // Try to convert input to integer
            if (newDuration != 2 && newDuration != 3) {
                System.out.println(PersonController.RED + "Error: Duration must be either 2 or 3 hours." + PersonController.RESET);
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println(PersonController.RED + "Error: Invalid input. Please enter a valid integer." + PersonController.RESET);
            return false;
        }

        String sql = "UPDATE exams SET Duration = ? WHERE ExamID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, newDuration); // Store as integer
            stmt.setString(2, currentExamID);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Exam duration updated successfully to " + newDuration + " hour(s).");
                return true;
            } else {
                System.out.println(PersonController.RED + "Error: Exam ID not found." + PersonController.RESET);
                return false;
            }
        }
    }

    // Validation methods
    private boolean validateExamID(String examID) {
        if (examID.length() != 5) // Check length if it is 5 characters
            return false;
        if (!examID.startsWith("EX")) // Check if it starts with "EX"
            return false;
        try {
            Integer.parseInt(examID.substring(2));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validateSubjectID(String subjectID) {
        if (subjectID.length() != 8) {
            return false;
        }
        return true;
    }

    private boolean validateDate(String date) {
        if (!date.matches("^\\d{4}-\\d{2}-\\d{2}$")) return false;
        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean validateDay(String day) {
        String[] validDays = { "MON", "TUE", "WED", "THU", "FRI" };
        for (String validDay : validDays) {
            if (validDay.equalsIgnoreCase(day))
                return true;
        }
        return false;
    }

    // Method to delete an exam
    public void deleteExam() {
        PersonController.clearScreen();
        System.out.println("----------Delete Exam----------");
        System.out.print("Enter Exam ID to delete: ");
        String examIDToDelete = PersonController.scanner.nextLine().trim();

        if (!validateExamID(examIDToDelete)) {
            System.out.println(PersonController.RED + "Error: Invalid Exam ID format (e.g., EX101)" + PersonController.RESET);
            PersonController.delay();
            return;
        }

        System.out.print("Are you sure you want to delete the exam (Y/N)? ");
        String confirmation = PersonController.scanner.nextLine().trim().toUpperCase();

        if (!confirmation.equals("Y")) {
            System.out.println("Deletion cancelled.");
            PersonController.delay();
            return;
        }

        try (Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName, PersonController.dbpass)) {
            // First, delete any dependent records (e.g., in students_exams)
            String deleteDependenciesSQL = "DELETE FROM students_exams WHERE ExamID = ?";
            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteDependenciesSQL)) {
                deleteStmt.setString(1, examIDToDelete);
                deleteStmt.executeUpdate();
            }

            // Then delete the exam record itself
            String deleteExamSQL = "DELETE FROM exams WHERE ExamID = ?";
                try (PreparedStatement stmt = connection.prepareStatement(deleteExamSQL)) {
                    stmt.setString(1, examIDToDelete);
                    int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println(PersonController.GREEN + "Exam deleted successfully!" + PersonController.RESET);
                    } else {
                        System.out.println(PersonController.RED + "Error: Exam ID not found." + PersonController.RESET);
                    }
                }
            } catch (SQLException e) {
                System.out.println(PersonController.RED + "Database error: " + e.getMessage() + PersonController.RESET);
            }
            PersonController.delay();
    }
}