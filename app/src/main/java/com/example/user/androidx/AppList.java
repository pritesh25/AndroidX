package com.example.user.androidx;

import android.graphics.drawable.Drawable;

public class AppList {

    private String name;
    private String pkgname;
    Drawable icon;

    public AppList(String name,String pkgname,Drawable icon) {
        this.name = name;
        this.pkgname = pkgname;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getPkgname() {
        return pkgname;
    }

    public void setPkgname(String pkgname) {
        this.pkgname = pkgname;
    }
}
