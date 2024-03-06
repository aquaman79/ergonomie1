package main.java.com.ubo.tp.message.ihm.loginComponent;

import main.java.com.ubo.tp.message.datamodel.User;

public interface ISigninObserver {
    void onSigninAttempt(String username, String tag);
    void onSigninFailure(String errorMessage);
    void onSigninSuccess(User newUser);
}
