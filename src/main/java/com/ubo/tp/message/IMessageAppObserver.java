package main.java.com.ubo.tp.message;

import main.java.com.ubo.tp.message.datamodel.User;

public interface IMessageAppObserver {
    void onUserSignupCompleted(User user);
    void onUserSignupFailed(String errorMessage);
}