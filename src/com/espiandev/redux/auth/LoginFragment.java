package com.espiandev.redux.auth;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.espiandev.redux.animation.AnimationFactoryProvider;
import com.espiandev.redux.animation.AnimationFactory;
import com.espiandev.redux.R;
import com.espiandev.redux.ResourceStringProvider;
import com.espiandev.redux.TitledFragment;
import com.espiandev.redux.network.NetworkErrorTranslator;
import com.espiandev.redux.network.NetworkHelper;
import com.espiandev.redux.network.NetworkHelperProvider;
import com.espiandev.redux.network.Responder;

public class LoginFragment extends TitledFragment implements Responder<String>, ResourceStringProvider {

    private EditText usernameField;
    private EditText passwordField;
    private ProgressBar loadingSpinner;
    private View credentialsHost;
    private NetworkHelper networkHelper;
    private TokenStorage tokenStorage;
    private AnimationFactory animationFactory;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);
        usernameField = (EditText) view.findViewById(R.id.login_username);
        passwordField = (EditText) view.findViewById(R.id.login_password);
        loadingSpinner = (ProgressBar) view.findViewById(R.id.login_spinner);
        credentialsHost = view.findViewById(R.id.login_credentials_host);
        Button submitButton = (Button) view.findViewById(R.id.login_submit);
        submitButton.setOnClickListener(submitClickListener);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof NetworkHelperProvider) {
            networkHelper = ((NetworkHelperProvider) activity).getNetworkHelper();
        }
        if (activity instanceof AnimationFactoryProvider) {
            animationFactory = ((AnimationFactoryProvider) activity).getAnimationFactory();
        }
        if (activity instanceof TokenStorageProvider) {
            tokenStorage = ((TokenStorageProvider) activity).getTokenStorage();
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
            if (Validator.isPasswordValid(passwordField.getText()) &&
                    Validator.isUsernameValid(usernameField.getText())) {
                launchLoginRequest();
                titleHost.setSubtitle(null);
            } else {
                titleHost.setSubtitle(Validator.isUsernameValid(usernameField.getText()) ?
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
        boolean storedToken = tokenStorage.storeToken(response);
        if (storedToken) {
            Toast.makeText(getActivity(), "Stored token", Toast.LENGTH_SHORT).show();
        } else {
            onErrorResponse(new TokenError());
        }
    }

    @Override
    public void onErrorResponse(Exception error) {
        animationFactory.cancelAnimations(loadingSpinner, credentialsHost);
        animationFactory.downAndOut(loadingSpinner);
        animationFactory.downAndIn(credentialsHost);
        titleHost.setSubtitle(NetworkErrorTranslator.getErrorString(this, error));
    }

}
