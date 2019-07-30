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

    public String removeSpecialChar(String s) {
        StringBuilder edited = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '@' && s.charAt(i) != '.') {
                edited.append(s.charAt(i));
            }
        }
        return edited.toString();
    }

    public String removeJpgFromEnd(String s) {
        StringBuilder edited = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '.') {
                break;
            }
            else {
                edited.append(s.charAt(i));
            }
        }
        return edited.toString();
    }
}
