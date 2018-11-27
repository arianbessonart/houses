package smt.ort.houses.services;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smt.ort.houses.model.LoginBodyRequest;
import smt.ort.houses.model.User;
import smt.ort.houses.network.ClientService;
import smt.ort.houses.network.LoginRestService;
import smt.ort.houses.ui.LoginFragment;

public class LoginService {

    Application application;

    public LoginService(Application application) {
        this.application = application;
    }

    public FacebookCallback<LoginResult> loginFacebookCallback(LoginFragment.LoginListener loginListener) {
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), (object, response) -> {
                    try {
                        User user = new User(object.getString("id"), object.getString("name"), object.getString("email"));
                        Log.d("LOGIN", object.toString());
                        loginListener.onLoginSuccess(user);
                        setServerSession(object.getString("id"), user.getEmail());
                    } catch (JSONException e) {
                        loginListener.onLoginError(e);
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                loginListener.onLoginCancel();
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("ERROR", error.toString());
                loginListener.onLoginError(error);
            }
        };
    }

    private void setServerSession(String token, String email) {
        LoginRestService serviceClient = ClientService.getClientCall(token).create(LoginRestService.class);
        serviceClient.login(new LoginBodyRequest(email)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                SharedPreferences sharedPreferences = application.getSharedPreferences("general", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("authorization", token).commit();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("On Login Failure", "" + t);
            }
        });
    }

}
