package com.example.achutharaj_achu_.busnavigenic.model;

public class Passenger {
    public String username;
    public String mobno;
    public String userid;
    public String password;
    public String conpassword;

    public Passenger(){

    }


    public Passenger(String username, String mobno, String userid, String password, String conpassword) {
        this.username = username;
        this.mobno = mobno;
        this.userid = userid;
        this.password = password;
        this.conpassword = conpassword;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Passenger){
            Passenger pass =(Passenger) obj;
            if(pass.userid.equals(userid)){
                return true;
            }else return false;
        }else return false;

    }
}
