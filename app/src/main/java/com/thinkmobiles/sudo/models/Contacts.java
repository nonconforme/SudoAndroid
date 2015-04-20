package com.thinkmobiles.sudo.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by omar on 19.04.15.
 */
public class Contacts implements Parcelable {

    private String firstName;
    private String lastName;
    private String avatarUrl;
    private String phoneNumber1;
    private String phoneNumber2;
    private String phoneNumber3;


    //Consctructors

    public Contacts(String firstName){
        if(firstName == null){
            throw  new IllegalArgumentException(
                    "First Name cannot be null");
        }

        this. firstName = firstName;
    }

    public Contacts( String firstName,String lastName,String avatarUrl,String phoneNumber1,String phoneNumber2,String phoneNumber3) {
        if(firstName == null){
            throw  new IllegalArgumentException(
                    "First Name cannot be null");
        }

        this. firstName = firstName;
        this. lastName = lastName;
        this. avatarUrl = avatarUrl;
        this. phoneNumber1 = phoneNumber1;
        this. phoneNumber2 = phoneNumber2;
        this. phoneNumber3 = phoneNumber3;
    }
    public Contacts(Parcel in) {

        String[] data = new String[3];

        in.readStringArray(data);
        this.firstName = data[0];
        this.lastName = data[1];
        this.avatarUrl = data[2];

        this.phoneNumber1 = data[3];
        this.phoneNumber2 = data[4];
        this.phoneNumber3 = data[5];
    }


    //Methods

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getPhoneNumber3() {
        return phoneNumber3;
    }

    public void setPhoneNumber3(String phoneNumber3) {
        this.phoneNumber3 = phoneNumber3;
    }



    //Parcelable

    @Override
    public int describeContents() {

        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeStringArray(new String[]{firstName, lastName, avatarUrl, phoneNumber1, phoneNumber2, phoneNumber3});

    }

    public static final Parcelable.Creator<Contacts> CREATOR = new Parcelable.Creator<Contacts>() {
        public Contacts createFromParcel(Parcel in) {
            return new Contacts(in);
        }

        public Contacts[] newArray(int size) {
            return new Contacts[size];
        }
    };


    //Object Methods


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contacts)) return false;

        Contacts contacts = (Contacts) o;

        if (!firstName.equals(contacts.firstName)) return false;
        if (lastName != null ? !lastName.equals(contacts.lastName) : contacts.lastName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }
}
