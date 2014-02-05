package com.espiandev.redux;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

    private EditText usernameField;
    private EditText passwordField;
    private TextView errorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = (EditText) findViewById(R.id.login_username);
        passwordField = (EditText) findViewById(R.id.login_password);
        errorView = (TextView) findViewById(R.id.login_error);
        findViewById(R.id.login_submit).setOnClickListener(mOnClickListener);

    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (usernameIsValid()) {

                if (passwordIsValid()) {
                    hideErrorView();

                } else {
                    showErrorView();
                    errorView.setText(R.string.login_error_password);
                }

            } else {
                showErrorView();
                errorView.setText(R.string.login_error_username);
            }
        }

    };

    private void hideErrorView() {
        errorView.setVisibility(View.INVISIBLE);
    }

    private void showErrorView() {
        errorView.setVisibility(View.VISIBLE);
    }

    private boolean passwordIsValid() {
        return !TextUtils.isEmpty(passwordField.getText());
    }

    private boolean usernameIsValid() {
        return !TextUtils.isEmpty(usernameField.getText());
    }

}
