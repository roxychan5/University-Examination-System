import java.sql.*;

public class Exam {
    private String examID;
    private String subjectID;
    private String examDate;
    private String examDay;
    private int time;
    private String duration;
    private String venue;

    // Default Constructor
    public Exam() {
        this.examID = "";
        this.subjectID = "";
        this.examDate = "";
        this.examDay = "";
        this.time = 0;
        this.duration = "";
        this.venue = "";
    }

    // Parameterized Constructor
    public Exam(String examID, String subjectID, String examDate, String examDay, int time, String duration,
            String venue) {
        this.examID = examID;
        this.subjectID = subjectID;
        this.examDate = examDate;
        this.examDay = examDay;
        this.time = time;
        this.duration = duration;
        this.venue = venue;
    }

    // Getters
    public String getExamID() {
        return examID;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public String getExamDate() {
        return examDate;
    }

    public String getExamDay() {
        return examDay;
    }

    public int getTime() {
        return time;
    }

    public String getDuration() {
        return duration;
    }

    public String getVenue() {
        return venue;
    }

    // Setters
    public void setExamID(String examID) {
        this.examID = examID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public void setExamDay(String examDay) {
        this.examDay = examDay;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    Exam exam;

    // Display Exam Details(for Student)
    public void displayExamDetails(Exam exam, String StudentID) {
        try {
            Connection connection = connectdatabase.getConnection();
            String sql = "SELECT exams.*, subject.SubjectName " +
                    "FROM exams " +
                    "JOIN students_exams ON exams.ExamID = students_exams.ExamID " +
                    "JOIN subject ON exams.SubjectID = subject.SubjectID " +
                    "WHERE students_exams.StudentID = ? ";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, StudentID);
            ResultSet resultSet = statement.executeQuery();

            boolean hasExams = false;
            PersonController.clearScreen();
            System.out.println("----------- Exam Schedule -----------");

            // Display Exam Details (for Student)
            while (resultSet.next()) {
                hasExams = true;
                exam.setExamID(resultSet.getString("ExamID")); // Get Exam ID
                exam.setSubjectID(resultSet.getString("SubjectID")); // Get Subject ID
                Subject.setSubjectName(resultSet.getString("SubjectName"));
                exam.setExamDate(resultSet.getString("ExamDate"));
                exam.setExamDay(resultSet.getString("ExamDay"));
                exam.setTime(resultSet.getInt("Time"));
                exam.setDuration(resultSet.getString("Duration"));
                exam.setVenue(resultSet.getString("Venue"));

                // Validate the exam time (should be between 8 AM and 8 PM)
                if (exam.getTime() < 8 || exam.getTime() > 20) {
                    System.out.println(PersonController.RED + "Error: Exam time must be between 8 AM and 8 PM." + PersonController.RESET);
                    continue; // Skip displaying this exam
                }

                System.out.println("\nExam ID\t\t: " + examID);
                System.out.println("Subject ID\t: " + subjectID);
                System.out.println("Subject Name\t: " + Subject.getSubjectName());
                System.out.println("Date\t\t: " + examDate + " (" + examDay + ")");

                int displayTime = (exam.getTime() == 0) ? 12 : (exam.getTime() > 12) ? (exam.getTime() - 12) : exam.getTime(); // Convert to 12-hour format
                String amPm = (exam.getTime() < 12) ? "AM" : "PM"; // Determine AM/PM

                System.out.println("Time\t\t: " + displayTime + " " + amPm);
                System.out.println("Duration\t: " + duration);
                System.out.println("Venue\t\t: " + venue);
                System.out.println("\n------------------------------------");

            }
            System.out.print("Press Enter to Continue");

            if (!hasExams) {
                System.out.println("\nNo exams found for Subject ID" + subjectID);
            }

        } catch (Exception e) {
            System.out.print(PersonController.RED + "Error: System busy. Please Try Again" + PersonController.RESET);
            PersonController.delay();

        }
    }
    // Display Exam Details (for Academic Staff)
public void displayExamDetailsForAcademicStaff(String acaStaffID) {
    try {
        Connection connection = connectdatabase.getConnection();
        String sql = "SELECT exams.*, subject.SubjectName " +
             "FROM exams " +  // <- added space
             "JOIN subject ON exams.SubjectID = subject.SubjectID";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        boolean hasExams = false;
        PersonController.clearScreen();
        System.out.println("----------- Exam Schedule (Academic Staff View) -----------");

        while (resultSet.next()) {
            hasExams = true;
            // Directly print from ResultSet without needing Exam object
            String examID = resultSet.getString("ExamID");
            String subjectID = resultSet.getString("SubjectID");
            String subjectName = resultSet.getString("SubjectName");
            String examDate = resultSet.getString("ExamDate");
            String examDay = resultSet.getString("ExamDay");
            int time = resultSet.getInt("Time");
            String duration = resultSet.getString("Duration");
            String venue = resultSet.getString("Venue");

            // Validate the exam time (should be between 8 AM and 8 PM)
            if (time < 8 || time > 20) {
                System.out.println(PersonController.RED + "Error: Exam time must be between 8 AM and 8 PM." + PersonController.RESET);
                continue;
            }
            // Convert to 12-hour format
            int displayTime = (time == 0) ? 12 : (time > 12) ? (time - 12) : time;
            String amPm = (time < 12) ? "AM" : "PM";

            System.out.println("\nExam ID\t\t: " + examID);
            System.out.println("Subject ID\t: " + subjectID);
            System.out.println("Subject Name\t: " + subjectName);
            System.out.println("Date\t\t: " + examDate + " (" + examDay + ")");


            System.out.println("Time\t\t: " + displayTime + " " + amPm);
            System.out.println("Duration\t: " + duration);
            System.out.println("Venue\t\t: " + venue);
            System.out.println("\n------------------------------------");
        }

        if (!hasExams) {
            System.out.println("\nNo exams found for Academic Staff ID: " + acaStaffID);
        }


        System.out.print("Press Enter to continue");
        
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
        
        // Close resources
        resultSet.close();
        statement.close();
        connection.close();

    } catch (SQLException e) {
        System.out.print(PersonController.RED + "Database Error: " + e.getMessage() + PersonController.RESET);
        PersonController.delay();
    } catch (Exception e) {
        System.out.print(PersonController.RED + "Error: " + e.getMessage() + PersonController.RESET);
        PersonController.delay();
    }
}

}