package com.espiandev.redux;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.espiandev.redux.animation.AnimationFactory;
import com.espiandev.redux.animation.RealAnimationFactory;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity implements Response.ErrorListener, Response.Listener<String> {

    public static final String TAG = LoginActivity.class.getSimpleName();

    private EditText usernameField;
    private EditText passwordField;
    private TextView errorView;
    private ReduxUrlHelper urlHelper;
    private ProgressBar loadingSpinner;
    private View credentialsHost;
    private Button submitButton;
    AnimationFactory animationFactory;
    VolleyHelper volleyHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        animationFactory = new RealAnimationFactory();
        volleyHelper = new ApplicationVolleyHelper(this);
        urlHelper = new ReduxUrlHelper();

        usernameField = (EditText) findViewById(R.id.login_username);
        passwordField = (EditText) findViewById(R.id.login_password);
        loadingSpinner = (ProgressBar) findViewById(R.id.login_spinner);
        errorView = (TextView) findViewById(R.id.login_error);
        submitButton = (Button) findViewById(R.id.login_submit);
        credentialsHost = findViewById(R.id.login_credentials_host);

        submitButton.setOnClickListener(submitClickListener);

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
        animationFactory.upAndOut(credentialsHost);
        animationFactory.upAndIn(loadingSpinner);
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
        animationFactory.cancelAnimations(loadingSpinner, credentialsHost);
        animationFactory.downAndOut(loadingSpinner);
        animationFactory.downAndIn(credentialsHost);
        showErrorView(R.string.login_error_auth);
    }

    @Override
    public void onResponse(String response) {
        animationFactory.cancelAnimations(loadingSpinner);
        animationFactory.downAndOut(loadingSpinner);
        boolean storedToken = storeToken(response);
        if (storedToken) {
            setResult(RESULT_OK);
            finish();
        } else {
            onErrorResponse(null);
        }
    }

    private boolean storeToken(String response) {
        try {
            JSONObject object = new JSONObject(response);
            String token = object.getString("token");
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putString("auth_token", token)
                    .apply();
            Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
