package com.thinkmobiles.sudo.models.counties;

import java.util.List;

/**
 * Created by njakawaii on 28.04.2015.
 */
public class CountryModel {
    private String name;
    private String countryIso;

    private List<NumberPackages> buyNumberPackages;
    private  List<NumberPackages> extendNumberPackages;

    public List<NumberPackages> getBuyNumberPackages() {
        return buyNumberPackages;
    }

    public void setBuyNumberPackages(List<NumberPackages> buyNumberPackages) {
        this.buyNumberPackages = buyNumberPackages;
    }

    public List<NumberPackages> getExtendNumberPackages() {
        return extendNumberPackages;
    }

    public void setExtendNumberPackages(List<NumberPackages> extendNumberPackages) {
        this.extendNumberPackages = extendNumberPackages;
    }

    public String getCountryIso() {
        return countryIso;
    }

    public void setCountryIso(String countryIso) {
        this.countryIso = countryIso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
