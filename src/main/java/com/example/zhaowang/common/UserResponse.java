package com.example.zhaowang.common;

public class UserResponse {
    private final int id;
    private final String userName;
    private final int count;

    public UserResponse(int id, int count) {
        this.id = id;
        this.userName = "张三";
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public int getCount() {
        return count;
    }
}
