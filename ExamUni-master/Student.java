public class Student extends Person {
   private int yearOfStudy;
   private int age;
   private String gender;
   private String courseName;

   public Student() {
      super();
   }

   public Student(String id, String name, String ic, String password, String email, String contactNumber,
         int yearOfStudy, int age, String gender, String courseName) {
      super(id, name, ic, password, email, contactNumber);
      this.yearOfStudy = yearOfStudy;
      this.age = age;
      this.gender = gender;
      this.courseName = courseName;
   }

   public String toString() {
      return super.toString() + "\nCourse: " + courseName + "\nYear of Study: " + yearOfStudy ;
   }

   public int getYearOfStudy() {
      return yearOfStudy;
   }


   public void setAge(int age) {
      this.age = age;
   }

   public int getAge() {
      return age;
   }

   public void setGender(String gender) {
      this.gender = gender;
   }

   public String getGender() {
      return gender;
   }

   public void setYearOfStudy(int yearOfStudy) {
      this.yearOfStudy = yearOfStudy;
   }


   public void setCourseName(String courseName) {
      this.courseName = courseName;
   }

   public String getCourseName() {
      return courseName;
   }
}
