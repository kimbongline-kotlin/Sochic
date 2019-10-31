package com.sochic.sochic.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sochic.sochic.Util.ApiMannager.ApiClient;
import com.sochic.sochic.Util.ApiMannager.ApiService;
import com.sochic.sochic.Util.UserFolder.User;
import com.sochic.sochic.Util.UserFolder.UserID;

import java.security.MessageDigest;
import java.util.UUID;
import java.util.regex.Pattern;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ScTransActivity
        extends Activity
{
    public ApiService apiService;
    public AppController appController;
    public CompositeDisposable disposable = new CompositeDisposable();
    public int gone = View.GONE;

   // public ImAlert imAlert = new  ImAlert();
    public String id_user = new UserID().get();
    public int invisible = View.INVISIBLE;
    public Scheduler io = Schedulers.io();
    public Scheduler thread = AndroidSchedulers.mainThread();
    public User user = AppController.getInstance().getPrefManager().getUser();
    public int visible = View.VISIBLE;


    public String LockPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }


    public static void hideKeyboard(Activity paramActivity)
    {
        InputMethodManager localInputMethodManager = (InputMethodManager)paramActivity.getSystemService(INPUT_METHOD_SERVICE);
        View localView1 = paramActivity.getCurrentFocus();
        View localView2 = localView1;
        if (localView1 == null) {
            localView2 = new View(paramActivity);
        }
        localInputMethodManager.hideSoftInputFromWindow(localView2.getWindowToken(), 0);
    }


    public void ConnectionError()
    {
        Toast.makeText(getApplicationContext(), "데이터 연결을 확인해주세요..", Toast.LENGTH_LONG).show();
    }


    public boolean LoginCheck()
    {
        return AppController.getInstance().getPrefManager().getUser() != null;
    }

    public void ShowToast(String paramString)
    {
        Toast.makeText(getApplicationContext(), paramString, Toast.LENGTH_LONG).show();
    }



    public boolean isPassValid(String paramString)
    {
        Pattern localPattern = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");
        boolean bool;
        if ((!TextUtils.isEmpty(paramString)) && (localPattern.matcher(paramString).matches())) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
    {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        new ActivityResultEvent(paramInt1, paramInt2, paramIntent);
    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        this.appController = AppController.create(getApplicationContext());
        this.apiService = ((ApiService) ApiClient.getClient(getApplicationContext()).create(ApiService.class));
    }


    public  boolean isInstalledPackage(Context context, String pkgName){
        if (pkgName != null) {
            try {
                PackageManager pm = context.getPackageManager();
                pm.getInstallerPackageName(pkgName);
                return true;
            } catch (Exception e) {
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
     //   getSupportActionBar().hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        ActionBar actionBar = getSupportActionBar();
//
//        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white_color)));
//        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
//        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
//        actionBar.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.
//
//
//        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
//        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
//        View actionbar = inflater.inflate(R.layout.topbar_layout, null);
//
//        ImageView topBtn = (ImageView) actionbar.findViewById(R.id.imageView6);
//        topBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//            }
//        });
//        actionBar.setCustomView(actionbar);
//
//        //액션바 양쪽 공백 없애기
//        androidx.appcompat.widget.Toolbar parent = (androidx.appcompat.widget.Toolbar)actionbar.getParent();
//
//        parent.setContentInsetsAbsolute(0,0);


        return true;


    }

    public void goTo(Class name) {
        startActivity(new Intent(getApplicationContext(),name).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }

    @SuppressLint("MissingPermission")
    public String GetDevicesUUID(Context mContext){
        final TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();
        return deviceId;
    }

    public float convertDpToPixel(float dp, Context context) {
        return (((float) context.getResources().getDisplayMetrics().densityDpi) / DisplayMetrics.DENSITY_DEFAULT) * dp;
    }

    public float convertPixelsToDp(float px, Context context) {
        return px / (((float) context.getResources().getDisplayMetrics().densityDpi) / DisplayMetrics.DENSITY_DEFAULT);
    }

    public  boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

}
