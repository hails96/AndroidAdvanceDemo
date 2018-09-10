package lsh.framgia.com.androidadvancedemo.utils;

import java.util.Locale;

import lsh.framgia.com.androidadvancedemo.githubdemo.Constant;

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

    public static String formatSearchUrl(String query) {
        return String.format(Locale.ENGLISH, "%s%s%s", Constant.GITHUB_BASE_API_URL,
                Constant.GITHUB_SEARCH_USER_END_POINT, query);
    }

    public static String formatDoubleNumber(double number) {
        return String.format(Locale.ENGLISH, "%02f", number);
    }
}
