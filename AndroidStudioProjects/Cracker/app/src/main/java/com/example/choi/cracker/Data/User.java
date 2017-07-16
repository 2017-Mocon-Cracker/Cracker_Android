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

    private String Email, CardName, UserName, CardNum;
    private int Money;
    private Boolean isTransfer,isEmpty, Paied;

    public Boolean getEmpty() {
        return isEmpty;
    }

    public void setEmpty(Boolean empty) {
        isEmpty = empty;
    }

    public User(String email, String cardName, String userName, String cardNum, int money, Boolean paied, Boolean isTransfer, Boolean isEmpty) {
        Email = email;
        CardName = cardName;
        UserName = userName;
        CardNum = cardNum;
        Money = money;

        Paied = paied;
        this.isTransfer = isTransfer;
        this.isEmpty = isEmpty;
    }

    public Boolean getPaied() {
        return Paied;
    }

    public void setPaied(Boolean paied) {
        Paied = paied;
    }

    public Boolean getTransfer() {
        return isTransfer;
    }

    public void setTransfer(Boolean transfer) {
        isTransfer = transfer;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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

    public int getMoney() {
        return Money;
    }

    public void setMoney(int money) {
        Money = money;
    }

    public String getCardNum() {
        return CardNum;
    }

    public void setCardNum(String cardNum) {
        CardNum = cardNum;
    }
}
