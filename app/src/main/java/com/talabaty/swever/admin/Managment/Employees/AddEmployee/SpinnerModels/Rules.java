package com.talabaty.swever.admin.Managment.Employees.AddEmployee.SpinnerModels;

public class Rules {
    int Id ;
    String Name;

    public Rules(int id, String name) {
        Id = id;
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }
}
