import java.sql.*;

public class Result {
    private String resultID;
    private String studentID;
    private String subjectID;
    private int marksObtained;
    private String finalGrade;
    private double cgpa;

    // Default constructor
    public Result() {
        this.resultID = "";
        this.studentID = "";
        this.subjectID = "";
        this.marksObtained = 0;
        this.finalGrade = "";
        this.cgpa = 0.0;
    }

    // Constructor with parameters
    public Result(String resultID, String studentID, String subjectID, int marksObtained, String finalGrade) {
        this.resultID = resultID;
        this.studentID = studentID;
        this.subjectID = subjectID;
        this.marksObtained = marksObtained;
        this.finalGrade = finalGrade;
    }

    // Getters
    public String getResultID() {
        return resultID;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public int getMarksObtained() {
        return marksObtained;
    }

    public String getFinalGrade() {
        return finalGrade;
    }

    public double getCgpa() {
        return cgpa;
    }

    // Setters
    public void setResultID(String resultID) {
        this.resultID = resultID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public void setMarksObtained(int marksObtained) {
        this.marksObtained = marksObtained;
    }

    public void setFinalGrade(String finalGrade) {
        this.finalGrade = finalGrade;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    // display results details then -:
    // calculates the CGPA of a student marks from database
    // show the CGPA of the student after all results are displayed
    // (BUT ResultController alr have addResults) if facultyMember add results, then
    // only enter marks, auto generate CGPA
    // and store in the database

    Result result;

    // Display results and calculate CGPA
    public void displayResults(Result result, String studentID) {
    try {
        Connection connection = connectdatabase.getConnection();
        String sql = "SELECT r.*, subj.SubjectName " +
                "FROM result r " +
                "JOIN subject subj ON r.SubjectID = subj.SubjectID " +
                "WHERE r.StudentID = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, studentID);
        ResultSet resultSet = statement.executeQuery();

        double totalGradePoints = 0.0;
        int subjectCount = 0;
        boolean hasResults = false;

        PersonController.clearScreen();
        System.out.println("\n----------- Exam Results -----------");

        while (resultSet.next()) {
            hasResults = true;

            result.setResultID(resultSet.getString("ResultID"));
            result.setStudentID(resultSet.getString("StudentID"));
            result.setSubjectID(resultSet.getString("SubjectID"));
            Subject.setSubjectName(resultSet.getString("SubjectName"));
            result.setMarksObtained(resultSet.getInt("MarksObtained"));
            result.setFinalGrade(resultSet.getString("FinalGrade"));

            double marks = result.getMarksObtained();
            double gradePoint = convertMarksToCgpa(marks);

            totalGradePoints += gradePoint;
            subjectCount++;

            System.out.println("Result ID\t: " + result.getResultID());
            System.out.println("Student ID\t: " + result.getStudentID());
            System.out.println("Subject ID\t: " + result.getSubjectID());
            System.out.println("Subject Name\t: " + Subject.getSubjectName());
            System.out.println("Marks\t\t: " + result.getMarksObtained());
            System.out.println("Grade\t\t: " + result.getFinalGrade());
            System.out.println("------------------------------------");
        }

        if (hasResults) {
            // Calculate CGPA
            double calculatedCGPA = subjectCount > 0 ? totalGradePoints / subjectCount : 0.0;
            calculatedCGPA = Math.round(calculatedCGPA * 100.0) / 100.0;
            result.setCgpa(calculatedCGPA);
            System.out.printf("Auto-Generated CGPA for Student %s: %.2f\n", studentID, calculatedCGPA);

            // Check if CGPA exists for student
            boolean cgpaExists = false;
            try {
                String checkSql = "SELECT COUNT(*) FROM cgpa WHERE StudentID = ?";
                PreparedStatement checkStmt = connection.prepareStatement(checkSql);
                checkStmt.setString(1, studentID);
                ResultSet countResult = checkStmt.executeQuery();
                if (countResult.next()) {
                    cgpaExists = countResult.getInt(1) > 0;
                }
            } catch (SQLException e) {
                System.out.println(PersonController.RED + "Error checking CGPA: " + e.getMessage() + PersonController.RESET);
            }

            // Prepare appropriate SQL
            String updateSql;
            if (!cgpaExists) {
                updateSql = "INSERT INTO cgpa (StudentID, CGPA) VALUES (?, ?)";
            } else {
                updateSql = "UPDATE cgpa SET CGPA = ? WHERE StudentID = ?";
            }

            // Execute insert/update
            try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                if (!cgpaExists) {
                    updateStmt.setString(1, studentID);
                    updateStmt.setDouble(2, calculatedCGPA);
                } else {
                    updateStmt.setDouble(1, calculatedCGPA);
                    updateStmt.setString(2, studentID);
                }
                
                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println(PersonController.GREEN + "CGPA " + 
                                     (cgpaExists ? "updated" : "added") + 
                                     " successfully." + PersonController.RESET);
                }
            }
        } else {
            System.out.println("No results found for Student ID: " + studentID);
        }

        System.out.println("Press Enter to continue...");
        PersonController.scanner.nextLine();

    } catch (Exception e) {
        System.out.print(PersonController.RED + "Error: System busy. Please Try Again" + PersonController.RESET);
        PersonController.delay();
    }
}

    // Convert marks to CGPA
    public static double convertMarksToCgpa(double marks) {
        if (marks >= 85.0)
            return 4.0;
        else if (marks >= 75.0)
            return 3.7;
        else if (marks >= 65.0)
            return 3.3;
        else if (marks >= 55.0)
            return 3.0;
        else if (marks >= 45.0)
            return 2.0;
        else
            return 0.0;
    }
}