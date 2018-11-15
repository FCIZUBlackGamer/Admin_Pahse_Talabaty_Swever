package com.talabaty.swever.admin;

public class SystemPermission {

    int Id;
    int ScreensId;
    int RulesId;
    boolean Create;
    boolean Delete;
    boolean Update;
    boolean View;


    public void setView(boolean view) {
        View = view;
    }

    public void setRulesId(int rulesId) {
        RulesId = rulesId;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getRulesId() {
        return RulesId;
    }

    public int getId() {
        return Id;
    }

    public boolean isView() {
        return View;
    }

    public void setDelete(boolean delete) {
        Delete = delete;
    }

    public void setUpdate(boolean update) {
        Update = update;
    }

    public void setCreate(boolean create) {
        Create = create;
    }

    public void setScreensId(int screensId) {
        ScreensId = screensId;
    }

    public boolean isUpdate() {
        return Update;
    }

    public boolean isDelete() {
        return Delete;
    }

    public boolean isCreate() {
        return Create;
    }

    public int getScreensId() {
        return ScreensId;
    }
}
