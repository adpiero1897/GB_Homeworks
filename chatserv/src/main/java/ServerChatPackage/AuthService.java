package ServerChatPackage;

public interface AuthService {
    void start();
    String getNickByLoginPass(String login, String pass);
    void stop();
    boolean changeNick(String nickOld, String nickNew);
}
