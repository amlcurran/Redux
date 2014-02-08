package com.espiandev.redux;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.espiandev.redux.animation.AnimationFactory;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragment extends TitledFragment implements Response.ErrorListener, Response.Listener<String>, ResourceStringProvider {

    private EditText usernameField;
    private EditText passwordField;
    private ProgressBar loadingSpinner;
    private View credentialsHost;
    private Button submitButton;
    private VolleyHelper volleyHelper;
    private AnimationFactory animationFactory;

    private final ReduxUrlHelper urlHelper;

    public LoginFragment() {
        urlHelper = new ReduxUrlHelper();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);
        usernameField = (EditText) view.findViewById(R.id.login_username);
        passwordField = (EditText) view.findViewById(R.id.login_password);
        loadingSpinner = (ProgressBar) view.findViewById(R.id.login_spinner);
        credentialsHost = view.findViewById(R.id.login_credentials_host);
        submitButton = (Button) view.findViewById(R.id.login_submit);
        submitButton.setOnClickListener(submitClickListener);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof VolleyHelperProvider) {
            volleyHelper = ((VolleyHelperProvider) activity).getVolleyHelper();
        }
        if (activity instanceof AnimationFactoryProvider) {
            animationFactory = ((AnimationFactoryProvider) activity).getAnimationFactory();
        }
    }

    @Override
    public int getTitle() {
        return R.string.log_in;
    }

    @Override
    public int getSubtitle() {
        return 0;
    }

    View.OnClickListener submitClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (isUsernameValid() && isPasswordValid()) {
                launchLoginRequest();
                titleHost.setSubtitle(null);
            } else {
                titleHost.setSubtitle(isUsernameValid() ?
                        getString(R.string.login_error_password) : getString(R.string.login_error_username));
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
        titleHost.setSubtitle(ErrorTranslator.getErrorString(this, error));
    }

    @Override
    public void onResponse(String response) {
        animationFactory.cancelAnimations(loadingSpinner);
        animationFactory.downAndOut(loadingSpinner);
        boolean storedToken = storeToken(response);
        if (storedToken) {
            Toast.makeText(getActivity(), "Stored token", Toast.LENGTH_SHORT).show();
        } else {
            onErrorResponse(null);
        }
    }

    private boolean storeToken(String response) {
        try {
            JSONObject object = new JSONObject(response);
            String token = object.getString("token");
            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                    .putString("auth_token", token)
                    .apply();
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

}
