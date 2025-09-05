import java.io.Console;

public class User {
    public static void login(int choice) {
        Console console = System.console();
        Person staff = new AcademicStaff();
        Person faculty = new FacultyMember();
        Person student = new Student();
        PersonController.clearScreen();
        if (choice == 1) {///////
            StudentController studentController = new StudentController();
            studentController.StuAccess();
        } else if (choice == 2) {
            PersonController.clearScreen();
            System.out.println(PersonController.BLUE + "Type exit to exit" + PersonController.RESET);
            System.out.println("----------Faculty Member Login----------");
            System.out.print("Faculty Member ID: ");
            faculty.setID(PersonController.scanner.nextLine()); //// to get ID
            if (faculty.getID().equalsIgnoreCase("exit")) {
                System.out.print("Exiting");
                PersonController.delay();
                return;
            }

            String facPassword = new String(console.readPassword("Password: "));
            System.out.print(PersonController.BLUE + "Show Password?(Y) " + PersonController.RESET);
            String confirm = PersonController.scanner.nextLine();
            if (confirm.equalsIgnoreCase("Y")) {
                System.out.println("Password: " + facPassword);
                System.out.print("Press Enter to continue");
                while (true) {
                    String input = PersonController.scanner.nextLine();
                    if (input.isEmpty()) { // input empty
                        break;
                    } else {
                        System.out.print("Please press Enter only to continue");

                    }
                }
            }
            faculty.setPassword(facPassword);
            // menu
            FacMemberController facMemberController = new FacMemberController();
            if (facMemberController.showMenu(faculty.getID(), faculty.getPassword()) == true) {
                facMemberController.FacMemberMenu(faculty.getID());
            }
        } else if (choice == 3) {
            PersonController.clearScreen();
            System.out.println(PersonController.BLUE + "Type exit to exit" + PersonController.RESET);
            System.out.println("----------Academic Staff Login----------");
            System.out.print("Academic Staff ID: ");
            staff.setID(PersonController.scanner.nextLine());
            if (staff.getID().equalsIgnoreCase("exit")) {
                System.out.print("Exiting");
                PersonController.delay();
                return;
            }

            String staffPassword = new String(console.readPassword("Password: "));
            System.out.print(PersonController.BLUE + "Show Password?(Y) " + PersonController.RESET);
            String confirm = PersonController.scanner.nextLine();
            if (confirm.equalsIgnoreCase("Y")) {
                System.out.println("Password: " + staffPassword);
                System.out.print("Press Enter to continue");
                while (true) {
                    String input = PersonController.scanner.nextLine();
                    if (input.isEmpty()) { // input empty
                        break;
                    } else {
                        System.out.print("Please press Enter only to continue");

                    }
                }
            }
            staff.setPassword(staffPassword);
            // to menu
            AcademicStaffController academicStaffController = new AcademicStaffController();
            if(academicStaffController.showMenu(staff.getID(), staff.getPassword()) == true){
                academicStaffController.AcademicStaffMenu(staff.getID());
            }

        }
    }
}