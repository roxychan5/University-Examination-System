import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Subject extends Course{

    private String subjectID;
    private static String subjectName;
    Course course;

    public Subject () {
    }

    public Subject(String subjectID, String subjectName,Course course){
        this.subjectID = subjectID;
        Subject.subjectName = subjectName;
        this.course = course;
    }

    public String getSubjectID(){
        return subjectID;
    }

    public void getSubjectID(String subjectID){
        this.subjectID = subjectID;
    }

    public static String getSubjectName(){
        return subjectName;
    }

    public static void setSubjectName(String subjectName){
        Subject.subjectName = subjectName;
    }

    public String toString() {
        return "Course name: " + course + "\nSubject ID: " + subjectID + "\nSubject name: " + subjectName;
    }

    public void display() {
        System.out.println(toString());
    }
    public static boolean IsExistSubject(String subjectID){
                String sql = "SELECT SubjectID FROM subject WHERE SubjectID = ?";
        try {
            Connection connection = DriverManager.getConnection(PersonController.dbURL, PersonController.dbName,
                    PersonController.dbpass);
            PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, subjectID);
            ResultSet resultSet = statement.executeQuery();

            boolean exists = resultSet.next(); 

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
}
