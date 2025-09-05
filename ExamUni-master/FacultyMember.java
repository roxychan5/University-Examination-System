public class FacultyMember extends Person {
    public FacultyMember() {
        super();
    }

    public FacultyMember(String ID, String ic, String password, String email, String name, String contactNumber) {
        super(ID, ic, password, email, name, contactNumber);
    }

    public String toString() {
        return super.toString().replace("Course ID:","Course Handle:");
    }


}
