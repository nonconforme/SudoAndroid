package com.thinkmobiles.sudo.models.numbers;

import com.thinkmobiles.sudo.models.DefaultResponseModel;
import com.thinkmobiles.sudo.models.addressbook.NumberModel;

import java.util.List;

/**
 * Created by njakawaii on 05.05.2015.
 */
public class NumberListResponse extends DefaultResponseModel{
    public List<NumberObject> getObjects() {
        return objects;
    }

    public void setObjects(List<NumberObject> objects) {
        this.objects = objects;
    }

    private List<NumberObject> objects;

}
