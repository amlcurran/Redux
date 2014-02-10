package com.espiandev.redux.auth;

import com.android.volley.AuthFailureError;
import com.espiandev.redux.BasicFragment;
import com.espiandev.redux.R;
import com.espiandev.redux.ResourceStringProvider;
import com.espiandev.redux.network.NetworkErrorTranslator;
import com.espiandev.redux.network.Responder;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class LoginFragment extends BasicFragment implements Responder<String> {

    private EditText usernameField;
    private EditText passwordField;
    private ProgressBar loadingSpinner;
    private View credentialsHost;
    private TokenStorage tokenStorage;
    private LoginListener loginListener;
    private final Validator validator;

    public LoginFragment() {
        validator = new Validator();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        usernameField = (EditText) view.findViewById(R.id.login_username);
        passwordField = (EditText) view.findViewById(R.id.login_password);
        loadingSpinner = (ProgressBar) view.findViewById(R.id.spinner);
        credentialsHost = view.findViewById(R.id.login_credentials_host);
        Button submitButton = (Button) view.findViewById(R.id.login_submit);
        submitButton.setOnClickListener(submitClickListener);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof TokenStorageProvider) {
            tokenStorage = ((TokenStorageProvider) activity).getTokenStorage();
        }
        if (activity instanceof LoginListener) {
            loginListener = (LoginListener) activity;
        } else {
            throw new ClassCastException("Host of LoginFragment must implement LoginListener");
        }
    }

    @Override
    public CharSequence getHostedTitle(ResourceStringProvider stringProvider) {
        return stringProvider.getString(R.string.log_in);
    }

    @Override
    public CharSequence getHostedSubtitle(ResourceStringProvider stringProvider) {
        return null;
    }

    View.OnClickListener submitClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (validator.isPasswordValid(passwordField.getText()) &&
                    validator.isUsernameValid(usernameField.getText())) {
                launchLoginRequest();
                titleHost.setSubtitle(null);
            } else {
                titleHost.setSubtitle(validator.isUsernameValid(usernameField.getText()) ?
                        getString(R.string.login_error_password) : getString(R.string.login_error_username));
            }
        }

    };

    private void launchLoginRequest() {
        String username = String.valueOf(usernameField.getText());
        String password = String.valueOf(passwordField.getText());
        networkHelper.login(username, password, this);
        animationFactory.upAndOut(credentialsHost);
        animationFactory.upAndIn(loadingSpinner);
    }

    @Override
    public void onSuccessResponse(String response) {
        animationFactory.cancelAnimations(loadingSpinner);
        animationFactory.downAndOut(loadingSpinner);

        try {
            JSONObject object = new JSONObject(response);
            String token = object.getString("token");
            boolean storedToken = tokenStorage.storeToken(token);
            if (storedToken) {
                loginListener.onLogin();
            } else {
                onErrorResponse(new TokenError());
            }
        } catch (JSONException e) {
            onErrorResponse(null);
        }
    }

    @Override
    public void onErrorResponse(Exception error) {
        animationFactory.cancelAnimations(loadingSpinner, credentialsHost);
        animationFactory.downAndOut(loadingSpinner);
        animationFactory.downAndIn(credentialsHost);
        if (error instanceof AuthFailureError) {
            titleHost.setSubtitle(getString(R.string.volley_error_wrong_password));
        } else {
            titleHost.setSubtitle(NetworkErrorTranslator.getErrorString(this, error));
        }
    }

}
