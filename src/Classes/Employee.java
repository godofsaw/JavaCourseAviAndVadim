package Classes;

public class Employee {
    private String empSn;
    private String empName;
    private String empId;
    private String empTel;
    private String empBank;
    private String empBranch;
    private String empType;
    private String empPassword;

    public Employee(String empSn, String empName, String empId, String empTel, String empBank, String empBranch, String empType,String password) {
        this.empSn = empSn;
        this.empName = empName;
        this.empId = empId;
        this.empTel = empTel;
        this.empBank = empBank;
        this.empBranch = empBranch;
        this.empType = empType;
        this.empPassword = password;
    }

    public Employee(){
        this.empSn = "";
        this.empName = "";
        this.empId = "";
        this.empTel = "";
        this.empBank = "";
        this.empBranch = "";
        this.empType = "";
        this.empPassword = "";
    }

    public String getEmpSn() {
        return empSn;
    }

    public void setEmpSn(String empSn) {
        this.empSn = empSn;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpTel() {
        return empTel;
    }

    public void setEmpTel(String empTel) {
        this.empTel = empTel;
    }

    public String getEmpBank() {
        return empBank;
    }

    public void setEmpBank(String empBank) {
        this.empBank = empBank;
    }

    public String getEmpBranch() {
        return empBranch;
    }

    public void setEmpBranch(String empBranch) {
        this.empBranch = empBranch;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public String getPassword() {
        return empPassword;
    }

    public void setPassword(String password) {
        this.empPassword = password;
    }
}

