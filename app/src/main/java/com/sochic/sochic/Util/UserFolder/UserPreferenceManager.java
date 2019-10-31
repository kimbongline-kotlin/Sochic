package com.sochic.sochic.Util.UserFolder;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferenceManager
{
    private static final String KEY_ID_USER = "id_user";
    private static final String KEY_LOGIN_TYPE = "login_type";
    private static final String PREF_NAME = "peazle";
    int PRIVATE_MODE = 0;
    private String TAG = UserPreferenceManager.class.getSimpleName();
    Context _context;
    SharedPreferences.Editor editor;
    SharedPreferences pref;

    public UserPreferenceManager(Context paramContext)
    {
        this._context = paramContext;
        this.pref = this._context.getSharedPreferences("raphacall", this.PRIVATE_MODE);
        this.editor = this.pref.edit();
    }

    public void clear()
    {
        this.editor.clear();
        this.editor.commit();
    }

    public User getUser()
    {
        if (this.pref.getString("id_user", null) != null) {
            return new User(this.pref.getString("id_user", null));
        }
        return null;
    }

    public void storeUser(User paramUser)
    {
        this.editor.putString("id_user", paramUser.getId_user());
        this.editor.commit();
    }
}
