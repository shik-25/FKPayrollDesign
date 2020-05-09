 import java.util.*;
 import java.sql.*;
class database_manager {
    Connection conn=null;
    public static Connection dbconnect(){   
        System.out.println("Connection");
    try
    {
    Class.forName("org.sqlite.JDBC");
    Connection conn=DriverManager.getConnection("jdbc:sqlite:employee.db");
    //JOptionPane.showMessageDialog(null ,"connected");
    System.out.println("Connected");
    return conn;
}catch(Exception e){
    System.out.println("error" + e);
    return null;
}
}
}