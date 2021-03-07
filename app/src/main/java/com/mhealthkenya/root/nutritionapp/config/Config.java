package com.mhealthkenya.root.nutritionapp.config;

public class Config {

    public static String GETCAREGIVERS_URL ="http://197.232.36.170:4500/api/getcaregivers";
    public static String ADDCAREGIVER_URL ="http://197.232.36.170:4500/api/addcaregiver";
    public static String LOGIN_URL ="http://197.232.36.170:4500/api/login";

    public static final String KEY_CAREGIVERID = "client_id";
    public static final String KEY_CAREGIVERIDNUMBER = "id_number";
    public static final String KEY_CAREGIVERPHONENUMBER = "phone_number";
    public static final String KEY_CAREGIVERRECRUITMENTDATE = "created_at";
    public static final String KEY_CAREGIVERCOMMUNICATIONMODE = "message_type";
    public static final String KEY_CAREGIVERCLINICIANID = "user_id";
    public static final String KEY_CLINICIANPOSTID = "user_id";
    public static final String JSON_ARRAYRESULTS = "result";

    public static final String[] commList = {
            "sms",
            "voice"

    };

}
