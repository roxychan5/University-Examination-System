public class AcademicStaff extends Person {
    private String subjectName;
    private static String subjectID;

    public AcademicStaff() {
        super(); // https://www.w3schools.com/java/ref_keyword_super.asp
    }

    public AcademicStaff(String ID, String ic, String password, String email, String name, String contactNumber,String subjectID,
            String subjectName) {
        super(ID, ic, password, email, name, contactNumber);
        this.subjectName = subjectName;
        this.subjectID = subjectID;
    }

    public String toString() {
        return super.toString() + "\nSubject Handle: " + subjectID + " " + subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public static String getSubjectID() {
        return subjectID;
    }

}