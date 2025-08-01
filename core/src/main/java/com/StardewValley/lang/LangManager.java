package com.StardewValley.lang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

public class LangManager {
    private static I18NBundle bundle;

    public static void load(Locale locale) {
        FileHandle baseFileHandle = Gdx.files.internal("i18n/messages"); // پایه (messages.properties بدون پسوند)
        bundle = I18NBundle.createBundle(baseFileHandle, locale);
    }

    public static String get(String key) {
        if(bundle == null) return key; // fallback اگر لود نشده بود
        return bundle.get(key);
    }

    public static String format(String key, Object... args) {
        if(bundle == null) return key;
        return bundle.format(key, args);
    }

    public static Locale getCurrentLocale() {
        if(bundle == null) return Locale.ENGLISH;
        return bundle.getLocale();
    }
}
