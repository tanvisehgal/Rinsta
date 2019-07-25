package utils;

public class StringManipulation {

    public String extractUsername(String email) {
        StringBuilder username = new StringBuilder();
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                break;
            }
            username.append(email.charAt(i));
        }
        return username.toString();
    }
}
