package smt.ort.houses.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import smt.ort.houses.R;
import smt.ort.houses.model.User;
import smt.ort.houses.services.LoginService;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private CallbackManager callbackManager;
    private LoginListener listener;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void onAttach(Context context) {
        try {
            this.listener = (LoginListener) getActivity();
        } catch (ClassCastException e) {
            Log.w("LoginFragment", "Set listener to dialog, " + e);
        }
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);


        LoginButton loginButton = view.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email"));
        loginButton.setFragment(this);

        loginButton.setReadPermissions("public_profile", "email");

        loginButton.registerCallback(callbackManager, new LoginService(getActivity().getApplication()).loginFacebookCallback(this.listener));


        AccessTokenTracker tokenTracker = new AccessTokenTracker() {

            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    listener.onLogoutSuccess();
                }
            }
        };
        tokenTracker.startTracking();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public interface LoginListener {
        void onLoginSuccess(User user);

        void onLoginCancel();

        void onLoginError(Exception e);

        void onLogoutSuccess();
    }
}
