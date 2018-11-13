package com.example.achutharaj_achu_.busnavigenic.model;

public class Driver {
    public String drivername;
    public String busno;
    public String busname;
    public String busroute;
    public String drivermail;


    public Driver(){

    }

    public Driver(String drivername, String busno, String busname, String busroute, String drivermail) {
        this.drivername = drivername;
        this.busno = busno;
        this.busname = busname;
        this.busroute = busroute;
        this.drivermail = drivermail;
    }





    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Driver){
            Driver user1 =(Driver) obj;
            if(user1.busno.equals(busno)){
                return true;
            }else return false;
        }else return false;

    }
}
