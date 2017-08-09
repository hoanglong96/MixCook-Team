package team.khonnan.android.miccook.model;

import android.util.Log;

/**
 * Created by quyntg94 on 08/08/2017.
 */

public class UserInfo {
    private String nameFb;
    private String idFb;
    private String avaFb;
    private String emailFb;
    private int ratePoint;
    private int rateNum;

    public int getRatePoint() {
        return ratePoint;
    }

    public void setRatePoint(int ratePoint) {
        this.ratePoint = ratePoint;
    }

    public int getRateNum() {
        return rateNum;
    }

    public void setRateNum(int rateNum) {
        this.rateNum = rateNum;
    }

    public UserInfo() {
    }

    public String getNameFb() {
        return nameFb;
    }

    public void setNameFb(String name) {
        this.nameFb = name;
    }

    public String getIdFb() {
        return idFb;
    }

    public void setIdFb(String idFb) {
        this.idFb = idFb;
    }

    public String getAvaFb() {
        return avaFb;
    }

    public void setAvaFb(String avaFb) {
        this.avaFb = avaFb;
    }

    public String getEmailFb() {
        return emailFb;
    }

    public void setEmailFb(String emailFb) {
        this.emailFb = emailFb;
    }
}
