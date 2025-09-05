public class Person {
    private String ID;
    private String ic;
    private String password;
    private String email;
    private String name;
    private String contactNumber;
    private int role;
    private String courseId;

    public Person() {

    }

    public Person(String ID, String ic, String password, String email, String name, String contactNumber) {
        this.ID = ID;
        this.ic = ic;
        this.password = password;
        this.email = email;
        this.name = name;
        this.contactNumber = contactNumber;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String toString() {// To show information(parent class)
        String info = "ID: " + ID + "\nIC: " + ic + "\nEmail: " + email + "\nName: " + name
                + "\nContact Number: " + contactNumber;
        if(courseId != null){
            info += "\nCourse ID: " + courseId;
        }
        return info;

    }

    public void display() {
        System.out.println(toString());
    }

    public String getRole() {
        if (role == 1) {
            return "Student";
        } else if (role == 2) {
            return "Faculty Member";
        } else if (role == 3) {
            return "Staff";
        } else {
            return "Invalid";
        }

    }
    public void setCourseID(String courseId){
        this.courseId = courseId;
    }
    public String getCourseId(){
        return courseId;
    }
}
