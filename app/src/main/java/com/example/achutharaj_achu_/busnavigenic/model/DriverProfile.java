package com.example.achutharaj_achu_.busnavigenic.model;

public class DriverProfile {

    static DriverProfile driverProfile;

    Driver driver;

    public static void setDriverProfile(Driver driver ){
        if(driverProfile==null){
            driverProfile = new DriverProfile();
        }
        driverProfile.driver = driver;
    }

    public static DriverProfile getDriverProfile(){
        return driverProfile;
    }

    public String getBusNo(){
        return driver.busno;
    }

    public Driver getDriver() {
        return driver;
    }
}
