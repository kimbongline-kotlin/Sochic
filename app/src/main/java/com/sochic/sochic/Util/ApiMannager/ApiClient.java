package com.sochic.sochic.Util.ApiMannager;

import android.content.Context;
import android.text.TextUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ApiClient
{
    private static int REQUEST_TIMEOUT = 60;
    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit = null;

    /* renamed from: com.styleranker.styleranker.UtilFolder.APIMannager.ApiClient$1 */
    static class C15891 implements Interceptor {
        C15891() {
        }

        public Response intercept(Chain chain) throws IOException {
            Request.Builder requestBuilder = chain.request().newBuilder().addHeader("Accept", "application/json").addHeader("Content-Type", "application/json");
            if (!TextUtils.isEmpty("test")) {
                requestBuilder.addHeader("Authorization", "test");
            }
            return chain.proceed(requestBuilder.build());
        }
    }

    public static Retrofit getClient(Context context) {
        if (okHttpClient == null) {
            initOkHttp(context);
        }
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(Constant.BASE_URL).client(okHttpClient).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    private static void initOkHttp(Context context) {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder().connectTimeout((long) REQUEST_TIMEOUT, TimeUnit.SECONDS).readTimeout((long) REQUEST_TIMEOUT, TimeUnit.SECONDS).writeTimeout((long) REQUEST_TIMEOUT, TimeUnit.SECONDS);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);
        httpClient.addInterceptor(new C15891());
        okHttpClient = httpClient.build();
    }
}
