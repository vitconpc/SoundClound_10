package vn.com.example.soundclound.data.model.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import vn.com.example.soundclound.BuildConfig;
import vn.com.example.soundclound.data.model.common.Constants;

public class Utils {
    public static String getUrlDownload(String uri) {
        StringBuffer stringBuffer = new StringBuffer(uri);
        stringBuffer.append(Constants.STREAM)
                .append(Constants.CLIENT_ID)
                .append(BuildConfig.CLIENT_ID);
        return stringBuffer.toString();
    }

    public static void saveState(Context context, String s, boolean mIsLoop) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.FILE_SAVE_SHA,Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(s,mIsLoop).apply();
    }

    public static boolean getState(Context context,String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.FILE_SAVE_SHA,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,false);
    }
}
