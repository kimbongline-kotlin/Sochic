package com.sochic.sochic.Util;

import android.app.Activity;
import android.content.Context;

import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;

public class KakaoSDKAdapter extends KakaoAdapter {

    /* renamed from: com.styleranker.styleranker.UtilFolder.KakaoSDKAdapter$1 */
    class C15981 implements ISessionConfig {
        C15981() {
        }

        public AuthType[] getAuthTypes() {
            return new AuthType[]{AuthType.KAKAO_LOGIN_ALL};
        }

        public boolean isUsingWebviewTimer() {
            return false;
        }

        public boolean isSecureMode() {
            return false;
        }

        public ApprovalType getApprovalType() {
            return ApprovalType.INDIVIDUAL;
        }

        public boolean isSaveFormData() {
            return true;
        }
    }

    /* renamed from: com.styleranker.styleranker.UtilFolder.KakaoSDKAdapter$2 */
    class C15992 implements IApplicationConfig {
        C15992() {
        }

        public Activity getTopActivity() {
            return AppController.getCurrentActivity();
        }

        public Context getApplicationContext() {
            return AppController.getAppControllerContext();
        }
    }

    public ISessionConfig getSessionConfig() {
        return new C15981();
    }

    public IApplicationConfig getApplicationConfig() {
        return new C15992();
    }
}
