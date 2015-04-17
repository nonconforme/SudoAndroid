package com.thinkmobiles.sudo.models.addressbook;

import java.util.List;

/**
 * Created by njakawaii on 17.04.2015.
 */
public class UserModel  {
    private  String companion;
    private List<NumberModel> numbers;

    public List<NumberModel> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<NumberModel> numbers) {
        this.numbers = numbers;
    }

    public String getCompanion() {
        return companion;
    }

    public void setCompanion(String companion) {
        this.companion = companion;
    }
}
