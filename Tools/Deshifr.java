package Tools;

public class Deshifr {

    public static String DESHFR(String input) {
        int Key = (input.length() / 2);
        String output = "";

        char[] InS = input.toCharArray();
        for(int i = 0; i < InS.length; i++) {
            output += Character.toString((char)InS[i] - Key);
        }

        return output;
    }
}
