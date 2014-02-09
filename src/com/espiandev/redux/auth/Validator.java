package com.espiandev.redux.auth;

import android.text.TextUtils;

public class Validator {

    public boolean isPasswordValid(CharSequence password) {
        return isCommonStringValid(password);
    }

    public boolean isUsernameValid(CharSequence username) {
        return isCommonStringValid(username);
    }

    /**
     * Common code to determine whether a string is valid or not
     */
    private boolean isCommonStringValid(CharSequence string) {
        return !TextUtils.isEmpty(string);
    }
}
