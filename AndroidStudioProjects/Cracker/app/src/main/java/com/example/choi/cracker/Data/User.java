package com.example.choi.cracker.Data;

/**
 * Created by choi on 2017. 7. 15..
 */

public class User {
//    "_id": "596a0494a3c669082bb7bf7c",
//            "Money": 1000,
//            "CardNum": null,
//            "Email": "fluorine2015@gmail.com",
//            "CardName": "",
//            "UserName": "오준석",
//            "isEmpty": 0,
//            "Paied": 0,
//            "isTransfer": 0,
//            "__v": 0

    private String Facebook_ID, CardName, UserName, CardNum;
    private int Money;
    private Boolean isTransfer, isEmpty, Paied;

    public String getFacebook_ID() {
        return Facebook_ID;
    }

    public void setFacebook_ID(String facebook_ID) {
        Facebook_ID = facebook_ID;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String cardName) {
        CardName = cardName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getCardNum() {
        return CardNum;
    }

    public void setCardNum(String cardNum) {
        CardNum = cardNum;
    }

    public int getMoney() {
        return Money;
    }

    public void setMoney(int money) {
        Money = money;
    }

    public Boolean getTransfer() {
        return isTransfer;
    }

    public void setTransfer(Boolean transfer) {
        isTransfer = transfer;
    }

    public Boolean getEmpty() {
        return isEmpty;
    }

    public void setEmpty(Boolean empty) {
        isEmpty = empty;
    }

    public Boolean getPaied() {
        return Paied;
    }

    public void setPaied(Boolean paied) {
        Paied = paied;
    }

    public User(String facebook_ID, String cardName, String userName, String cardNum, int money, Boolean isTransfer, Boolean isEmpty, Boolean paied) {

        Facebook_ID = facebook_ID;
        CardName = cardName;
        UserName = userName;
        CardNum = cardNum;
        Money = money;
        this.isTransfer = isTransfer;
        this.isEmpty = isEmpty;
        Paied = paied;
    }
}
