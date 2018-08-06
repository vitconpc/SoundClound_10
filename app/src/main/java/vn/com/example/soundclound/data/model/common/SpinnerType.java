package vn.com.example.soundclound.data.model.common;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static vn.com.example.soundclound.data.model.common.SpinnerType.GENRE_ALL_AUDIO;
import static vn.com.example.soundclound.data.model.common.SpinnerType.GENRE_ALL_MUSIC;
import static vn.com.example.soundclound.data.model.common.SpinnerType.GENRE_ALTERNATIVEROCK;
import static vn.com.example.soundclound.data.model.common.SpinnerType.GENRE_AMBIENT;
import static vn.com.example.soundclound.data.model.common.SpinnerType.GENRE_CLASSICAL;
import static vn.com.example.soundclound.data.model.common.SpinnerType.GENRE_COUNTRY;

@StringDef({GENRE_ALL_MUSIC, GENRE_ALL_AUDIO, GENRE_ALTERNATIVEROCK, GENRE_AMBIENT, GENRE_CLASSICAL, GENRE_COUNTRY})
@Retention(RetentionPolicy.SOURCE)
public @interface SpinnerType {
    String GENRE_ALL_MUSIC = "all-music";
    String GENRE_ALL_AUDIO = "all-audio";
    String GENRE_ALTERNATIVEROCK = "alternativerock";
    String GENRE_AMBIENT = "ambient";
    String GENRE_CLASSICAL = "classical";
    String GENRE_COUNTRY = "country";
}
