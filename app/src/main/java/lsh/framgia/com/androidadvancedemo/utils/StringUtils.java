package lsh.framgia.com.androidadvancedemo.utils;

import java.util.Locale;

public class StringUtils {

    public static String convertMillisToTimer(int millis) {
        int seconds = millis / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format(Locale.ENGLISH, "%d:%02d", minutes, seconds);
    }

    public static String formatTrackTitle(String name, String artist) {
        return String.format(Locale.ENGLISH, "%s - %s", name, artist);
    }
}
