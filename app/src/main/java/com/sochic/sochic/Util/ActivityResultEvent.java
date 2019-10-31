package com.sochic.sochic.Util;

import android.content.Intent;

public class ActivityResultEvent
{
    private Intent data;
    private int requestCode;
    private int resultCode;

    public ActivityResultEvent(int paramInt1, int paramInt2, Intent paramIntent)
    {
        this.requestCode = paramInt1;
        this.resultCode = paramInt2;
        this.data = paramIntent;
    }

    public Intent getData()
    {
        return this.data;
    }

    public int getRequestCode()
    {
        return this.requestCode;
    }

    public int getResultCode()
    {
        return this.resultCode;
    }

    public void setData(Intent paramIntent)
    {
        this.data = paramIntent;
    }

    public void setRequestCode(int paramInt)
    {
        this.requestCode = paramInt;
    }

    public void setResultCode(int paramInt)
    {
        this.resultCode = paramInt;
    }
}
