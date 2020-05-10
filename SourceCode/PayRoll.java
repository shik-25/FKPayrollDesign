//java -classpath .:sqlite-jdbc-3.27.2.1.jar PayRoll


import java.util.*;
import java.sql.*;
public class PayRoll{
    static String user= "";
    public static boolean login(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username");
        String user_name = sc.nextLine();
        System.out.println("Enter password");
        String password = sc.nextLine();
         try
            {
                Connection connect = database_manager.dbconnect();
                String query ="select PASSWORD from employee where ID=? ";
                PreparedStatement ps =connect.prepareStatement(query);
                ps.setString(1,user_name);
                ResultSet rs=ps.executeQuery();
                int count=0;
                String pass=null;
                while(rs.next()){
                count++;
                pass=rs.getString("PASSWORD");
                }
                if(count==1){
                    if(pass.equals(password)){
                    System.out.println("Welcome "+ user_name);
                    user = user_name;
                    connect.close();
                    return true;
                    }
                    else
                    System.out.println("Incorrect password!");
                }
                else{
                    System.out.println("SORRY you need to register!");
                }
            }catch(Exception e){
                System.out.println(e);
            }
        return false;
    }
    public static void register(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name");
        String name = sc.nextLine();
        System.out.println("Enter your mobile number");
        int mobile = sc.nextInt();
        System.out.println("Enter your username");
        String ID = sc.nextLine();
        ID = sc.nextLine();
        System.out.println("Enter your password");
        String password = sc.nextLine();
        System.out.println("true if you are a union member else type false");
        boolean isUnion = sc.nextBoolean();
        System.out.println("1 for hourly paid employee, 2 for salaried");
        int employeeType = sc.nextInt();        
        System.out.println("Enter your hourly rate or salary");
        int salary = sc.nextInt();
        System.out.println("Enter your comissionrate");
        int comission_rate = sc.nextInt();
        System.out.println("1 for receiving a paycheck at home, 2 for collecting it yourself and 3 if you want it you want it to be deposited in a bank");
        String Payment_details="";String pay="collet";
        int PaymentType = sc.nextInt();
        if(PaymentType == 1){
        System.out.println("Enter your address");
        pay="Home";
        Payment_details = sc.nextLine();
        Payment_details = sc.nextLine();
        }else if(PaymentType == 3){
        System.out.println("Enter your bank account number");
        Payment_details = sc.nextLine();
        Payment_details = sc.nextLine();
        pay="bank";
        }

        if(employeeType == 1){
           HourlyWorkingEmployee emp = new HourlyWorkingEmployee(name, mobile, ID, password, isUnion, salary, PaymentType);
        }else{
           SalariedEmployee emp = new SalariedEmployee(name, mobile, ID, password, isUnion, salary, comission_rate, PaymentType);
        }
        try{
          Connection connect = database_manager.dbconnect();
          String query="insert into employee values(?,?,?,?,?,?,?,?,?,?)";
          PreparedStatement ps=null;
          ps=connect.prepareStatement(query);
          ps.setString(1, name);
          ps.setString(2, ID);
          ps.setString(3, password);
          if(employeeType == 1) ps.setString(4, "Hourly");
          else if(employeeType == 2) ps.setString(4, "Salaried");
          ps.setInt(5, salary);
          ps.setInt(6, comission_rate);
          ps.setBoolean(7, isUnion);
          ps.setInt(8, 0);
          ps.setString(9, pay);
          ps.setString(10, Payment_details);
          ps.execute();
          System.out.println("Employee successfully registered");
          connect.close();
      }catch (java.sql.SQLException e){
          System.out.println(e);
      }
      catch(Exception e){
           System.out.println(e);
      }
    }
    public static void delete(){
        try{
            Connection connect = database_manager.dbconnect();
            String query = "Delete from employee where ID = ?";
            PreparedStatement ps = null;
            ps = connect.prepareStatement(query);
            ps.setString(1, user);
            ps.execute();
            System.out.println("User successfully deleted");
            connect.close();
        }catch(java.sql.SQLException e){
            System.out.println(e);
            }
            catch(Exception e){
            System.out.println("User could not be deleted");
        }
    }
    public static void main(String[] args) {
        System.out.println("..............Welcome to PayRoll...................");
        while(true){
        System.out.println("Press 1 to log in , 2 to register, 3 to quit");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        if(n == 1){
            Boolean loggedIn = login();
            if(loggedIn){
                System.out.println("HI");
                while(true){
                System.out.println("Type a number corresponding to the action you want to perform");
                System.out.println("0. Log out");
                System.out.println("1. Delete Acount");
                int m = sc.nextInt();
                if(m == 0){
                    user = null;
                    break;
                }else if(m == 1){
                    delete();
                }
           }
            }
        }else if(n == 2){
           register();
        }else{
            System.out.println("Bye...!");
            break;
        }
    }
    }
}

