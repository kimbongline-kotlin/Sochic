package com.sochic.sochic.Util.UserFolder;


import com.sochic.sochic.Util.AppController;

public class UserID
{
    public String get()
    {
        if (AppController.getInstance().getPrefManager().getUser() != null) {
            return AppController.getInstance().getPrefManager().getUser().getId_user();
        }
        return "";
    }
}
