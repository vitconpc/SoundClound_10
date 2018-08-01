package vn.com.example.soundclound.data.model.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import vn.com.example.soundclound.data.model.common.Constants;

import static android.content.Context.MODE_PRIVATE;

public class Utils {
    public static void savePermission(Context context, boolean isPermission) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SAVE_PERMISSION, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.KEY_PERMISSION, isPermission);
        editor.commit();
    }

    public static boolean getPermission(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SAVE_PERMISSION, MODE_PRIVATE);
        return sharedPreferences.getBoolean(Constants.KEY_PERMISSION, false);
    }
}
