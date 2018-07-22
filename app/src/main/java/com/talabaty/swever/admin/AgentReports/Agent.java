package com.talabaty.swever.admin.AgentReports;

public class Agent {
    String id, name, num;

    public Agent(String id, String name, String num) {
        this.id = id;
        this.name = name;
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNum() {
        return num;
    }
}
