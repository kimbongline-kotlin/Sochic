package com.sochic.sochic.Util.Alert;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.view.View.OnClickListener;

import com.sochic.sochic.R;


public class ScAlert {
    AlertDialog simpleDialog;

    public AlertDialog getSimpleDialog() {
        return simpleDialog;
    }

    public void setAlertUtil(AlertDialog target) {
        simpleDialog = target;
    }

  

    public void moreAlert(Activity activity, OnClickListener listener, Boolean equal, Boolean follow) {
        simpleDialog = new Builder(activity, R.style.CustomDialog).setView(((ScDialog) activity.getLayoutInflater().inflate(R.layout.sc_dialog_view, null)).moreAlert(listener,equal,follow)).setCancelable(true).create();
        simpleDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        simpleDialog.show();
        setAlertUtil(simpleDialog);
    }

    public void leavePreAlert(Activity activity, OnClickListener listener) {
        simpleDialog = new Builder(activity, R.style.CustomDialog).setView(((ScDialog) activity.getLayoutInflater().inflate(R.layout.sc_dialog_view, null)).leavePreAlert(listener)).setCancelable(true).create();
        simpleDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        simpleDialog.show();
        setAlertUtil(simpleDialog);
    }

    public void leaveComAlert(Activity activity, OnClickListener listener) {
        simpleDialog = new Builder(activity, R.style.CustomDialog).setView(((ScDialog) activity.getLayoutInflater().inflate(R.layout.sc_dialog_view, null)).leaveComAlert(listener)).setCancelable(false).create();
        simpleDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        simpleDialog.show();
        setAlertUtil(simpleDialog);
    }
}
