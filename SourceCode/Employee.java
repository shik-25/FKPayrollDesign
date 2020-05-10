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
    public static void updateDetails(String user_name){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of your choice :-");
        System.out.println("1. Update name");
        System.out.println("2. Update password");
        System.out.println("3. Update salary/hourly rate");
        System.out.println("4. Update Comission rate");
        System.out.println("5. change union member setting");
        int choice = sc.nextInt();
        if(choice == 1){
            System.out.println("enter correct name");
            String name = sc.nextLine();
            name = sc.nextLine();
            try{
                Connection connect = database_manager.dbconnect();
                String query = "update employee set name = ? where ID = ?";
                PreparedStatement ps = null;
                ps = connect.prepareStatement(query);
                ps.setString(1, name);
                ps.setString(2, user_name);
                ps.execute();
            }catch(Exception e){
                System.out.println(e);
            }
        }else if(choice == 2){
            System.out.println("enter new password");
            String pass = sc.nextLine();
            pass = sc.nextLine();
            try{
                Connection connect = database_manager.dbconnect();
                String query = "update employee set password = ? where ID = ?";
                PreparedStatement ps = null;
                ps = connect.prepareStatement(query);
                ps.setString(1, pass);
                ps.setString(2, user_name);
                ps.execute();
            }catch(Exception e){
                System.out.println(e);
            }
        }else if(choice == 3){
            System.out.println("enter new salary");
            int salary = sc.nextInt();
            try{
                Connection connect = database_manager.dbconnect();
                String query = "update employee set salary = ? where ID = ?";
                PreparedStatement ps = null;
                ps = connect.prepareStatement(query);
                ps.setInt(1, salary);
                ps.setString(2, user_name);
                ps.execute();
            }catch(Exception e){
                System.out.println(e);
            }
        }else if(choice == 4){
            System.out.println("enter new comission rate");
            int comm_rate = sc.nextInt();
            try{
                Connection connect = database_manager.dbconnect();
                String query = "update employee set comission_rate = ? where ID = ?";
                PreparedStatement ps = null;
                ps = connect.prepareStatement(query);
                ps.setInt(1, comm_rate);
                ps.setString(2, user_name);
                ps.execute();
            }catch(Exception e){
                System.out.println(e);
            }
        }else if(choice == 5){
            System.out.println("enter 1 if a union member or 0 if not");
            int union = sc.nextInt();
            try{
                Connection connect = database_manager.dbconnect();
                String query = "update employee set union_member = ? where ID = ?";
                PreparedStatement ps = null;
                ps = connect.prepareStatement(query);
                ps.setInt(1, union);
                ps.setString(2, user_name);
                ps.execute();
            }catch(Exception e){
                System.out.println(e);
            }
        }
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