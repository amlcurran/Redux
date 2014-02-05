package com.espiandev.redux;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.espiandev.redux.animation.AnimationFactory;
import com.espiandev.redux.animation.RealAnimationFactory;

public class LoginActivity extends Activity implements Response.ErrorListener, Response.Listener<String> {

    public static final String TAG = LoginActivity.class.getSimpleName();

    private EditText usernameField;
    private EditText passwordField;
    private TextView errorView;
    VolleyHelper volleyHelper;
    AnimationFactory animationFactory;
    private ReduxUrlHelper urlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        animationFactory = new RealAnimationFactory();
        volleyHelper = new ApplicationVolleyHelper(this);
        urlHelper = new ReduxUrlHelper();

        usernameField = (EditText) findViewById(R.id.login_username);
        passwordField = (EditText) findViewById(R.id.login_password);
        errorView = (TextView) findViewById(R.id.login_error);
        findViewById(R.id.login_submit).setOnClickListener(submitClickListener);

    }

    View.OnClickListener submitClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (isUsernameValid() && isPasswordValid()) {
                hideErrorView();
                launchLoginRequest();
            } else {
                showErrorView(isUsernameValid() ? R.string.login_error_password : R.string.login_error_username);
            }
        }

    };

    private void launchLoginRequest() {
        String loginUrl = urlHelper.buildLoginUrl(String.valueOf(usernameField.getText()),
                String.valueOf(passwordField.getText()));
        StringRequest request = new StringRequest(loginUrl, this, this);
        volleyHelper.getRequestQueue().add(request);
        animationFactory.upAndOut(findViewById(R.id.login_credentials_host));
        animationFactory.upAndIn(findViewById(R.id.login_spinner));
    }

    private void hideErrorView() {
        animationFactory.fadeOut(errorView);
    }

    private void showErrorView(final int errorResId) {
        animationFactory.refadeIn(errorView, new Runnable() {
            @Override
            public void run() {
                errorView.setText(errorResId);
            }
        });
    }

    private boolean isPasswordValid() {
        return !TextUtils.isEmpty(passwordField.getText());
    }

    private boolean isUsernameValid() {
        return !TextUtils.isEmpty(usernameField.getText());
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        animationFactory.cancelAnimations(findViewById(R.id.login_spinner));
        animationFactory.cancelAnimations(findViewById(R.id.login_credentials_host));
        animationFactory.downAndOut(findViewById(R.id.login_spinner));
        animationFactory.downAndIn(findViewById(R.id.login_credentials_host));
        showErrorView(R.string.login_error_auth);
    }

    @Override
    public void onResponse(String response) {
        Toast.makeText(this, "Success response", Toast.LENGTH_LONG).show();
        animationFactory.cancelAnimations(findViewById(R.id.login_spinner));
        animationFactory.downAndOut(findViewById(R.id.login_spinner));
    }
}
