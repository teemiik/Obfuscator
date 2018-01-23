package com.example.webapp.webapp;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by bolgov.artem on 04.09.17.
 */

public class Student extends HashMap<String, String>{
    public int Id;
    public String FIO;
    public int CardNumber;
    public Date DateBirth;
    public String Telephone;

    static final String S_Id = "Id";
    static final String S_FIO = "FIO";
    static final String S_CardNumber = "CardNumber";
    static final String S_DateBirth = "DateBirth";
    static final String S_Telephone = "Telephone";

    public Student(String id, String Fio, String telephone){
        super();

        Id = Integer.parseInt(id);
        FIO = Fio;
        Telephone = telephone;

        super.put(S_Id, id);
        super.put(S_FIO, Fio);
        super.put(S_Telephone, telephone);

    }

    public Student(String id, String FIO, String CardNumber, String DateBirth, String Telephone){
        super();
        super.put(S_Id, id);
        super.put(S_FIO, FIO);
        super.put(S_CardNumber, CardNumber);
        super.put(S_DateBirth, DateBirth);
        super.put(S_Telephone, Telephone);

    }
}
