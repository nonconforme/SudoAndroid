package com.thinkmobiles.sudo.models;

/**
 * Created by omar on 12.05.15.
 */
public class DrawerMenuItemModel {
  public  DrawerMenuItemModel(int name,String param, int icon){
      this.name = name;
      this.param = param;
      this.icon = icon;

  }
    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    int name;
    int icon;
    String param;
}
