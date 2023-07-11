import org.postgresql.util.PSQLException;

import java.util.*;
import java.sql.*;
import java.math.*;

public class App {
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static String url = "jdbc:postgresql://localhost:5432/StudentsD2T1", name = "postgres", password = "hello", sql;
    static Connection con;
    static PreparedStatement st;
    static Scanner sc;
    static ResultSet result;
    static Statement statement;
    static void update() throws SQLException {
        int id;
        String name;
        sql = "UPDATE StudentDetails SET name=? WHERE ID=?";
        System.out.println("ENTER THE ID WHERE YOU NEED THE NAME ");
        id=sc.nextInt();
        sc.nextLine();
        System.out.println("ENTER THE NAME TO BE REPLACED:");
        name=sc.nextLine() ;


        st = con.prepareStatement(sql);
        st.setString(1, name);
        st.setInt(2, id);
        int rowsUpdated = st.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("An existing user was updated successfully!");
        }
    }

    static void delete() throws Exception
    {
        String sql = "DELETE FROM studentdetails WHERE id=?";
        int id;
        System.out.println("ENTER THE ID WHERE YOU NEED TO DELETE:");
        id=sc.nextInt();
        sc.nextLine() ;
        st = con.prepareStatement(sql);
        st.setInt(1, id);

        int rowsDeleted = st.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("A user was deleted successfully!");
        }
    }
    static void select() throws SQLException
    {
        sql = "SELECT * FROM studentdetails";

        statement= con.createStatement();
        result = statement.executeQuery(sql);
        int count = 0;
        while (result.next()){
            int id = result.getInt(1);
            String name = result.getString(2);
            int age = result.getInt(3);
            BigDecimal phoneNo = result.getBigDecimal(4);
            String address = result.getString(5);
            System.out.println(id+" "+name+" "+age+" "+phoneNo+" "+address+"\n");
        }
    }
    static void insert() throws Exception
    {
        sql= "INSERT INTO studentdetails (ID,Name,Age,PhoneNumber,Address) VALUES (?,?, ?, ?, ?)";
        PreparedStatement st=con.prepareStatement(sql);
        int id,age,i;
        BigDecimal phoneNo;
        String name,address;
        while(true) {
            try{
                System.out.println("ENTER ID:");
                id = sc.nextInt();
                sc.nextLine();
                st.setInt(1, id);
                System.out.println("ENTER NAME");
                name=sc.nextLine();
                st.setString(2,name);
                System.out.println("ENTER AGE");
                age=sc.nextInt();
                sc.nextLine();
                st.setInt(3,22);
                System.out.println("ENTER PHONE NUMBER");
                phoneNo=sc.nextBigDecimal() ;
                sc.nextLine();
                st.setBigDecimal(4,new BigDecimal(String.valueOf(phoneNo)));
                System.out.println("ENTER ADDRESS");
                address=sc.nextLine();
                st.setString(5,"123,peelamedu");
                i=st.executeUpdate();
                break;
            }
            catch(PSQLException e)
            {
                System.out.println("id already exists...try another id");
            }

        }

        System.out.println(i+" records inserted");
    }
    public static void main(String args[])
    {
        int choice;
        sc=new Scanner(System.in);
        System.out.println("JDBC");
        try {

            con = DriverManager.getConnection(url, name, password);
            do {

                System.out.println("1.SELECT");
                System.out.println("2.INSERT");
                System.out.println("3.UPDATE");
                System.out.println("4.DELETE");
                System.out.println("5.EXIT");
                System.out.println("ENTER YOUR CHOICE:");
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        System.out.println("1.SELECT");
                        select();
                        break;
                    case 2:
                        System.out.println("2.INSERT");
                        insert();
                        break;
                    case 3:
                        System.out.println("3.UPDATE");
                        update();
                        break;
                    case 4:
                        System.out.println("4.DELETE");
                        delete();
                        break;
                    case 5:
                        System.out.println("5.EXIT");
                        break;
                    default:
                        System.out.println("PLEASE SELECT THE GIVEN OPTIONS");
                }

            } while (choice != 5);
            con.close();
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
