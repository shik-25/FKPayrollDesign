import java.util.*; 
import java.sql.*;
class Employee {
    String name;
    int mobile_number;
    String ID;
    String password;
    int method_of_payment;
    boolean union_member;
    int Deductions;
    public Employee(String name,int mobile,String ID,String password,Boolean isUnion,int PaymentType){
        this.name = name;
        this.mobile_number = mobile;
        this.ID = ID;
        this.password = password;
        this.union_member = isUnion;
        this.method_of_payment = PaymentType;
    }
}
class HourlyWorkingEmployee extends Employee {
    int hourly_rate;
    public HourlyWorkingEmployee(String name,int mobile,String ID,String password,Boolean isUnion,int salary,int PaymentType){
        super(name, mobile, ID, password, isUnion, PaymentType);
        this.hourly_rate = salary;
        System.out.println("Hourly");
    }
    public static void postTimecard(String user_name){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter todays working details");
        System.out.println("Enter number of hours worked");
        int hours = sc.nextInt();
        try{
            Connection connect = database_manager.dbconnect();
            String query = "select salary, amount_pending, balance from employee where ID=?";
            PreparedStatement ps =connect.prepareStatement(query);
            ps.setString(1,user_name);
            ResultSet rs=ps.executeQuery();
            int hour_rate = rs.getInt("salary");
            int amt= rs.getInt("amount_pending");
            int bal = rs.getInt("balance");
            connect.close();
            if(hours > 8){
                amt += hours * hour_rate + 0.5 * hour_rate * (hours - 8);
            }else amt += hours * hour_rate;
            
            Connection connect1 = database_manager.dbconnect();
            String query1 = "UPDATE employee SET amount_pending = ? WHERE ID = ?";
            PreparedStatement ps1 = null;
            ps1 =connect1.prepareStatement(query1);
            ps1.setInt(1,amt);
            ps1.setString(2,user_name);
            ps1.execute();
        }catch(java.sql.SQLException e){
            System.out.println(e);
        }catch(Exception e){
            System.out.println(e);
        }
    }

}

class SalariedEmployee extends Employee {
    int monthly_salary;
    int commission_rate;
    public SalariedEmployee(String name, int mobile, String ID, String password, boolean isUnion, int salary, int comission_rate,int PaymentType){
        super(name, mobile, ID, password, isUnion, PaymentType);
        this.monthly_salary = salary;
        this.commission_rate = comission_rate;
        System.out.println("Salaried");
    }
    public static void postSalesReciept(String user_name){
        System.out.println("Enter Sales Receipt");
    }
}