package main.java.com.ubo.tp.message.ihm.utilisateurComponent;

public interface IUtilisateurObserver {
    public void saveFollewer(String name, String tag);
    public void removeFollewer(String name, String tag);

    public void rechargeMessage(String name, String tag );

}
