import java.sql.*;
public class App {
    public static void main(String[] args) {
try{
    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:8000/mydb","root","root");
    Statement stmt=con.createStatement();
    ResultSet rs=stmt.executeQuery("select * from studentinfo");
    while(rs.next())
    System.out.println();
    con.close();
   }catch(Exception e){ System.out.println(e);
} 
   }
}