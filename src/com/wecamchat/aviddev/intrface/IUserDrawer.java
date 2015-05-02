package com.wecamchat.aviddev.intrface;

import android.database.Cursor;

import com.wecamchat.aviddev.model.bo.User;

public interface IUserDrawer {

    public void initUserDrawerViewControls();

    public void setUserDrawerData(Cursor cursor);
    public void setUserDrawerData(User user);

}
