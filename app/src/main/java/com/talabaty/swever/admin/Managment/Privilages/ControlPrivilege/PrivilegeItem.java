package com.talabaty.swever.admin.Managment.Privilages.ControlPrivilege;

public class PrivilegeItem {
    int id;
    String  name;
    boolean flag;

    public PrivilegeItem(int id, String name, boolean flag) {
        this.id = id;
        this.name = name;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
    public boolean getFlag(){
        return flag;
    }
}
