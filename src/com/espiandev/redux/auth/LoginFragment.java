package com.espiandev.redux.auth;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.espiandev.redux.BasicFragment;
import com.espiandev.redux.R;
import com.espiandev.redux.ResourceStringProvider;
import com.espiandev.redux.network.NetworkErrorTranslator;
import com.espiandev.redux.network.Responder;

public class LoginFragment extends BasicFragment implements Responder<String> {

    private EditText usernameField;
    private EditText passwordField;
    private ProgressBar loadingSpinner;
    private View credentialsHost;
    private Checkable rememberMeCheckBox;
    private TokenStorage tokenStorage;
    private AuthListener authListener;
    private final Validator validator;

    public LoginFragment() {
        validator = new Validator();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        usernameField = (EditText) view.findViewById(R.id.login_username);
        passwordField = (EditText) view.findViewById(R.id.login_password);
        passwordField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    submitClickListener.onClick(null);
                    return true;
                }
                return false;
            }
        });
        loadingSpinner = (ProgressBar) view.findViewById(R.id.spinner);
        credentialsHost = view.findViewById(R.id.login_credentials_host);
        rememberMeCheckBox = (CheckBox) view.findViewById(R.id.login_remember_me);
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
        if (activity instanceof AuthListener) {
            authListener = (AuthListener) activity;
        } else {
            throw new ClassCastException("Host of LoginFragment must implement AuthListener");
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
            titleHost.setSubtitle(null);
            launchLoginRequest();
        }

    };

    private void launchLoginRequest() {
        String username = String.valueOf(usernameField.getText());
        String password = String.valueOf(passwordField.getText());
        animationFactory.upAndOut(credentialsHost);
        animationFactory.upAndIn(loadingSpinner);
        networkHelper.login(username, password, this);
        if (rememberMeCheckBox.isChecked()) {
            tokenStorage.storeCredentials(username, password);
        } else {
            tokenStorage.purgeCredentials();
        }
    }

    @Override
    public void onSuccessResponse(String response) {
        animationFactory.cancelAnimations(loadingSpinner);
        animationFactory.downAndOut(loadingSpinner);

        String token = tokenStorage.extractToken(response);
        if (tokenStorage.storeToken(token)) {
            authListener.onLogin();
        } else {
            onErrorResponse(new TokenError());
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
