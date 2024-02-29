package screens_common_things;

public class Session {

    private static String authToken = "";

    public Session() {}
    public Session(String authToken) {
        Session.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }
}
