package main.java.com.ubo.tp.message.ihm.signupComponent;


import main.java.com.ubo.tp.message.datamodel.User;

public interface ISignupObserver {
    void onSignupAttempt(String username, String tag, String avatarPath);
    void onSignupFailure(String errorMessage);
    void onSignupSuccess(User newUser);
}
