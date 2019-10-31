package com.sochic.sochic.Util;


import androidx.annotation.NonNull;

public interface ShareReturn {
    void onError(@NonNull Throwable th);

    void onSuccess(@NonNull String str);
}
