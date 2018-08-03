package vn.com.example.soundclound.data.model.common;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static vn.com.example.soundclound.data.model.common.TabType.OFFLINE;
import static vn.com.example.soundclound.data.model.common.TabType.ONLINE;

@IntDef({OFFLINE, ONLINE})
@Retention(RetentionPolicy.SOURCE)
public @interface TabType {
    int OFFLINE = 0;
    int ONLINE = 1;
}
