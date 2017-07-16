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

    private String Email, CardName, UserName;
    private int Money, CardNum;

    public User(String email, String cardName, String userName, int money, int cardNum) {
        Email = email;
        CardName = cardName;
        UserName = userName;
        Money = money;
        CardNum = cardNum;
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

    public int getCardNum() {
        return CardNum;
    }

    public void setCardNum(int cardNum) {
        CardNum = cardNum;
    }
}
