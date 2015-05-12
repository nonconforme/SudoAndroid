package com.thinkmobiles.sudo.models.addressbook;

import com.thinkmobiles.sudo.models.ColorModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by njakawaii on 17.04.2015.
 */
public class UserModel  implements Serializable{
    private String companion;
    private List<NumberModel> numbers;
    private String avatar;
    private String credits;

    private String email ;
    private ColorModel color;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public ColorModel getColor() {
        return color;
    }

    public void setColor(ColorModel color) {
        this.color = color;
    }

    public UserModel() {
        numbers = new ArrayList<>();
        avatar = new String();
        companion = new String();
    }

    public List<NumberModel> getNumbers() {
        return numbers;
    }
//    public List<String> getStringNubers(){
//        List<String> strings = new ArrayList<>();
//        for (int i = 0; i < getNumbers().size(); i++) {
//            strings.add(getNumbers().get(i).getNumber());
////        }
//        return  num;
//    }
    public void setNumbers(List<NumberModel> numbers) {
        this.numbers = numbers;
    }

    public String getCompanion() {
        return companion;
    }

    public void setCompanion(String companion) {
        this.companion = companion;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }
}
