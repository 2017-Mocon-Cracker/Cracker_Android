package com.example.choi.cracker.Data;

import android.content.Intent;

/**
 * Created by choi on 2017. 7. 18..
 */

public class RideData {
    String WhereRide,UsedMony;
    int Money;

    public int getMoney() {
        return Money;
    }

    public void setMoney(int money) {
        Money = money;
    }

    public String getWhereRide() {
        return WhereRide;
    }

    public void setWhereRide(String whereRide) {
        WhereRide = whereRide;
    }

    public String getUsedMony() {
        return UsedMony;
    }

    public void setUsedMony(String usedMony) {
        UsedMony = usedMony;
    }

    public RideData(int money, String whereRide, String usedMony) {
        Money = money;
        WhereRide = whereRide;
        UsedMony = usedMony;
    }
}
