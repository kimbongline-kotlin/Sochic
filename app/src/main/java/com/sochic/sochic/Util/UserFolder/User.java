package com.sochic.sochic.Util.UserFolder;


import java.io.Serializable;

public class User
        implements Serializable
{
    String id_user;


    public User() {}

    public User(String id_user)
    {
        this.id_user = id_user;
    }

    public String getId_user()
    {
        return this.id_user;
    }



    public void setId_user(String paramString)
    {
        this.id_user = paramString;
    }


}