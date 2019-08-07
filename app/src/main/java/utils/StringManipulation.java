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

    public String removeJpgFromEnd(String imgFile) {
        StringBuilder edited = new StringBuilder();
        for (int i = 0; i < imgFile.length(); i++) {
            if (imgFile.charAt(i) == '.') {
                break;
            }
            else {
                edited.append(imgFile.charAt(i));
            }
        }
        return edited.toString();
    }

    public String formatTime(String time) {
        StringBuilder t = new StringBuilder();
        t.append(time.substring(9, 11));
        t.append(":");
        t.append(time.substring(11, 13));
        return t.toString();
    }

    public String formatIdentifier(String i) {
        return i.substring(0, i.length() - 8);
    }
}
