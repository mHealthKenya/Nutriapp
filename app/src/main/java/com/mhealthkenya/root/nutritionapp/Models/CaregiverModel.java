package com.mhealthkenya.root.nutritionapp.Models;

public class CaregiverModel {

    String caregiverid,clinicianid,Idnumber,Phonenumber,RecruitmentDate,CommunicationMode;

    public CaregiverModel() {
    }

    public CaregiverModel(String caregiverid, String clinicianid, String idnumber, String phonenumber, String recruitmentDate, String communicationMode) {
        this.caregiverid = caregiverid;
        this.clinicianid = clinicianid;
        Idnumber = idnumber;
        Phonenumber = phonenumber;
        RecruitmentDate = recruitmentDate;
        CommunicationMode = communicationMode;
    }

    public String getCaregiverid() {
        return caregiverid;
    }

    public void setCaregiverid(String caregiverid) {
        this.caregiverid = caregiverid;
    }

    public String getClinicianid() {
        return clinicianid;
    }

    public void setClinicianid(String clinicianid) {
        this.clinicianid = clinicianid;
    }

    public String getIdnumber() {
        return Idnumber;
    }

    public void setIdnumber(String idnumber) {
        Idnumber = idnumber;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        Phonenumber = phonenumber;
    }

    public String getRecruitmentDate() {
        return RecruitmentDate;
    }

    public void setRecruitmentDate(String recruitmentDate) {
        RecruitmentDate = recruitmentDate;
    }

    public String getCommunicationMode() {
        return CommunicationMode;
    }

    public void setCommunicationMode(String communicationMode) {
        CommunicationMode = communicationMode;
    }
}
