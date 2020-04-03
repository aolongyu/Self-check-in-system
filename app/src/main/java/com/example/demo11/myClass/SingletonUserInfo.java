package com.example.demo11.myClass;

public class SingletonUserInfo {

    private static String email;
    private static String uid;
    private static String role;
    private static String name;

    private static class SingletonHolder{
        private static final SingletonUserInfo INSTANCE = new SingletonUserInfo();
    }

    private SingletonUserInfo(){}

    public static final SingletonUserInfo getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public static void setEmail(String email)
    {
        getInstance().email = email;
    }

    public static String getEmail()
    {
        return getInstance().email;
    }

    public static void setUid(String uid) {
        getInstance().uid = uid;
    }

    public static void setRole(String role) {
        getInstance().role = role;
    }

    public static void setName(String name) {
        getInstance().name = name;
    }

    public String getRole() {
        return getInstance().role;
    }

    public static String getName() {
        return getInstance().name;
    }

    public static String getUid() {
        return getInstance().uid;
    }
}
