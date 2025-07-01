package org.luans1mple.lmscore.controller.cli.appdata;

public class SessionCLI {
    private static final SessionCLI instance = new SessionCLI();
    private int userId;
    private SessionCLI(){}
    public static SessionCLI getInstance(){
        return instance;
    }
    public int getUserId(){
        return  userId;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }
}
