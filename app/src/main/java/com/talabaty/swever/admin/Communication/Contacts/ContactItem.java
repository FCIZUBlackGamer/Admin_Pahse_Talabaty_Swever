package com.talabaty.swever.admin.Communication.Contacts;

public class ContactItem {
    String id, name, image, address, phone, destination;
    boolean IsFriend;

    public ContactItem(String id, String name, String image, String address, String phone, String destination) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.address = address;
        this.phone = phone;
        this.destination = destination;
    }

    public void setFriend(boolean friend) {
        IsFriend = friend;
    }

    public boolean IsFrind(){
        return IsFriend;
    }
    public String getAddress() {
        return address;
    }

    public String getDestination() {
        return destination;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
