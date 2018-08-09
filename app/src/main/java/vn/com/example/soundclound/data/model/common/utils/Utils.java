package vn.com.example.soundclound.data.model.common.utils;

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
}
