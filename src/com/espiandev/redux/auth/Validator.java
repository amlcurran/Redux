package com.espiandev.redux.auth;

import android.text.TextUtils;

public class Validator {

    public static boolean isPasswordValid(CharSequence password) {
        return !isCommonStringValid(password);
    }

    public static boolean isUsernameValid(CharSequence username) {
        return !isCommonStringValid(username);
    }

    /**
     * Common code to determine whether a string is valid or not
     */
    private static boolean isCommonStringValid(CharSequence string) {
        return TextUtils.isEmpty(string);
    }
}
