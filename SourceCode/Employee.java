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
}