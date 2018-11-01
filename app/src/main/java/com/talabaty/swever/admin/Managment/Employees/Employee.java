package com.talabaty.swever.admin.Managment.Employees;

import java.io.Serializable;

public class Employee implements Serializable{
    public int Id;
    public String FullName;
    public String FirstName;
    public String LastName;
    public String Address;
    public String UserName;
    public String Password;
    public String ConfirmPassword;
    public String ImageCard;
    public String Photo;
    public String SalaryBonus;
    public String Mail;
    public int Salary;
    public int TransportationAllowance;
    public int HousingAllowance;
    public int InsuranceAllowance;
    public int InsuranceNum;
    public int EmploymentTypeId;
    public int InfectionAllowance;
    public int WorkingHours;
    public int WorkingNaturalId;
    public String BranchName;
    public int WorkingHoursBonus;
    public int RulesId;
    public int ManagmentId;
    public String Phone;
    public String WorkingStart;
    public String WorkingEnd;
    public int CreditLimit;
    public int Balance;
    public boolean Flag;
    public boolean Gender;
    public int UserId;
    public int ShopId;

    public Employee() {
    }

    public Employee(int id, String fullName, String userName, String password, String confirmPassword, String imageCard, String photo, String salaryBonus, int salary, int transportationAllowance, int housingAllowance, int insuranceAllowance, int insuranceNum, int employmentTypeId, int infectionAllowance, int workingHours, int workingNaturalId, String branchName, int workingHoursBonus, int rulesId, int managmentId, String phone, String workingStart, String workingEnd, int creditLimit, int balance, boolean flag, int userId) {
        Id = id;
        FullName = fullName;
        UserName = userName;
        Password = password;
        ConfirmPassword = confirmPassword;
        ImageCard = imageCard;
        Photo = photo;
        SalaryBonus = salaryBonus;
        Salary = salary;
        TransportationAllowance = transportationAllowance;
        HousingAllowance = housingAllowance;
        InsuranceAllowance = insuranceAllowance;
        InsuranceNum = insuranceNum;
        EmploymentTypeId = employmentTypeId;
        InfectionAllowance = infectionAllowance;
        WorkingHours = workingHours;
        WorkingNaturalId = workingNaturalId;
        BranchName = branchName;
        WorkingHoursBonus = workingHoursBonus;
        RulesId = rulesId;
        ManagmentId = managmentId;
        Phone = phone;
        WorkingStart = workingStart;
        WorkingEnd = workingEnd;
        CreditLimit = creditLimit;
        Balance = balance;
        Flag = flag;
        UserId = userId;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setGender(boolean gender) {
        Gender = gender;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public void setImageCard(String imageCard) {
        ImageCard = imageCard;
    }

    public void setHousingAllowance(int housingAllowance) {
        HousingAllowance = housingAllowance;
    }

    public void setInsuranceAllowance(int insuranceAllowance) {
        InsuranceAllowance = insuranceAllowance;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setInsuranceNum(int insuranceNum) {
        InsuranceNum = insuranceNum;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public void setEmploymentTypeId(int employmentTypeId) {
        EmploymentTypeId = employmentTypeId;
    }

    public void setBranchName(String branchName) {
        BranchName = branchName;
    }

    public void setInfectionAllowance(int infectionAllowance) {
        InfectionAllowance = infectionAllowance;
    }

    public void setSalary(int salary) {
        Salary = salary;
    }

    public void setSalaryBonus(String salaryBonus) {
        SalaryBonus = salaryBonus;
    }

    public void setTransportationAllowance(int transportationAllowance) {
        TransportationAllowance = transportationAllowance;
    }

    public void setManagmentId(int managmentId) {
        ManagmentId = managmentId;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setRulesId(int rulesId) {
        RulesId = rulesId;
    }

    public void setWorkingHours(int workingHours) {
        WorkingHours = workingHours;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setWorkingNaturalId(int workingNaturalId) {
        WorkingNaturalId = workingNaturalId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public void setWorkingHoursBonus(int workingHoursBonus) {
        WorkingHoursBonus = workingHoursBonus;
    }

    public void setBalance(int balance) {
        Balance = balance;
    }

    public void setWorkingStart(String workingStart) {
        WorkingStart = workingStart;
    }

    public void setCreditLimit(int creditLimit) {
        CreditLimit = creditLimit;
    }

    public void setFlag(boolean flag) {
        Flag = flag;
    }

    public void setWorkingEnd(String workingEnd) {
        WorkingEnd = workingEnd;
    }

    public String getImageCard() {
        return ImageCard;
    }

    public String getPhoto() {
        return Photo;
    }

    public int getId() {
        return Id;
    }

    public int getUserId() {
        return UserId;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public int getSalary() {
        return Salary;
    }

    public String getFullName() {
        return FullName;
    }

    public String getAddress() {
        return Address;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public boolean getGender(){
        return Gender;
    }

    public String getPassword() {
        return Password;
    }

    public int getHousingAllowance() {
        return HousingAllowance;
    }

    public int getInsuranceAllowance() {
        return InsuranceAllowance;
    }

    public int getEmploymentTypeId() {
        return EmploymentTypeId;
    }

    public int getTransportationAllowance() {
        return TransportationAllowance;
    }

    public String getMail() {
        return Mail;
    }

    public String getSalaryBonus() {
        return SalaryBonus;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPhone() {
        return Phone;
    }

    public int getBalance() {
        return Balance;
    }

    public int getCreditLimit() {
        return CreditLimit;
    }

    public int getInfectionAllowance() {
        return InfectionAllowance;
    }

    public int getInsuranceNum() {
        return InsuranceNum;
    }

    public int getManagmentId() {
        return ManagmentId;
    }

    public int getRulesId() {
        return RulesId;
    }

    public int getWorkingHours() {
        return WorkingHours;
    }

    public int getWorkingHoursBonus() {
        return WorkingHoursBonus;
    }

    public int getWorkingNaturalId() {
        return WorkingNaturalId;
    }

    public String getBranchName() {
        return BranchName;
    }

    public String getWorkingEnd() {
        return WorkingEnd;
    }

    public String getWorkingStart() {
        return WorkingStart;
    }

}
