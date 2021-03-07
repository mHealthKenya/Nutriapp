package com.mhealthkenya.root.nutritionapp.Tables;

import com.orm.SugarRecord;

public class Cliniciandetails extends SugarRecord {

    String clinicianid;
    String fname;
    String lname;
    String idnumber;
    String phonenumber;
    String facilityid;
    String email;
    String password;

    public Cliniciandetails() {
    }

    public Cliniciandetails(String clinicianid, String fname, String lname, String idnumber, String phonenumber, String facilityid, String email, String password) {
        this.clinicianid = clinicianid;
        this.fname = fname;
        this.lname = lname;
        this.idnumber = idnumber;
        this.phonenumber = phonenumber;
        this.facilityid = facilityid;
        this.email = email;
        this.password = password;
    }

    public String getClinicianid() {

        return clinicianid;
    }

    public void setClinicianid(String clinicianid) {
        this.clinicianid = clinicianid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getFacilityid() {
        return facilityid;
    }

    public void setFacilityid(String facilityid) {
        this.facilityid = facilityid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
