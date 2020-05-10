import java.util.*; 
import java.sql.*;
class Employee {
    String name;
    int mobile_number;
    String ID;
    String password;
    int method_of_payment;
    int union_member;
    int Deductions;
    public Employee(String name,int mobile,String ID,String password,int isUnion,int PaymentType){
        this.name = name;
        this.mobile_number = mobile;
        this.ID = ID;
        this.password = password;
        this.union_member = isUnion;
        this.method_of_payment = PaymentType;
    }
        public static void DeductUnionCharges(int amount){
            try{
            Connection connect = database_manager.dbconnect();
            String query = "Select ID, Deductions from employee where union_member = ?";
            PreparedStatement ps = null;
            ps = connect.prepareStatement(query);
            ps.setInt(1, 1);
            ResultSet rs = ps.executeQuery();
            //connect.close();
            while(rs.next()){
                String id = rs.getString("ID");
                int deductions = rs.getInt("Deductions");
                //System.out.println(id + deductions);
                try{
                    //Connection conn = database_manager.dbconnect();
                    String query1 = "UPDATE employee SET deductions = ? WHERE ID = ?";
                    PreparedStatement ps1 = null;
                    ps1 = connect.prepareStatement(query1);
                    ps1.setInt(1, amount + deductions);
                    ps1.setString(2, id);
                    ps1.execute();
                    //conn.close();
                }catch(Exception e){
                    System.out.println(e);
                }
            }
            }catch(Exception e){
                System.out.println(e);
            }
        }
}
class HourlyWorkingEmployee extends Employee {
    int hourly_rate;
    public HourlyWorkingEmployee(String name,int mobile,String ID,String password,int isUnion,int salary,int PaymentType){
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
    public SalariedEmployee(String name, int mobile, String ID, String password, int isUnion, int salary, int comission_rate,int PaymentType){
        super(name, mobile, ID, password, isUnion, PaymentType);
        this.monthly_salary = salary;
        this.commission_rate = comission_rate;
        System.out.println("Salaried");
    }
    public static void postSalesReciept(String user_name){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your sales details");
        System.out.println("Enter the amount of sales you did today");
        int sales = sc.nextInt();
        try{
            Connection connect = database_manager.dbconnect();
            String query = "select comission_rate, amount_pending from employee where ID=?";
            PreparedStatement ps =connect.prepareStatement(query);
            ps.setString(1,user_name);
            ResultSet rs=ps.executeQuery();
            int rate = rs.getInt("comission_rate");
            int amt= rs.getInt("amount_pending");
            connect.close();
            amt += sales * rate/100;
            
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