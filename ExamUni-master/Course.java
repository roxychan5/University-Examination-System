public class Course {

    private String courseID;
    private String courseName;
    private int creditHours; 
    private String assignedinstructor;

    public Course() {
        this.courseID = "";
        this.courseName = "";
        this.creditHours = 0;
    }

    public Course(String courseID, String courseName, int creditHours, String assignedinstructor) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.creditHours = creditHours;
        this.assignedinstructor = assignedinstructor;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }


    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public String getAssignedinstructor() {
        return assignedinstructor;
    }

    public void setAssignedinstructor(String assignedinstructor) {
        this.assignedinstructor = assignedinstructor;
    }

    public String toString() {
        return "Course ID: " + courseID + "\nCourse Name: " + courseName + "\nCredit Hours: " + creditHours + "\nAssigned Instructor: " + assignedinstructor;
    }

    public void display() {
        System.out.println(toString());
    }
    
}
