import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Main {

    /**
     * encode the input char array using Run Length Encoding (RLE) algorithm
     * <p>
     * REQUIREMENTS: <br>
     * - input string contains characters in the set alphabet_1 = all character - digits <br>
     * - input string must NOT contain numbers (to assure correct and unique decoding)
     * </p>
     * @param charArray array of char to encode
     * @return RLE encoded string. if input char array is empty or blank return empty string
     */
    public static String encode(char[] charArray) {

        // check edge cases
        if (charArray.length == 0) {
            return "";
        }

        boolean isBlank = true;
        for (char c: charArray) {
            if (!Character.isWhitespace(c)) {
                isBlank = false;
                break;
            }
        }

        if (isBlank) {
            return "";
        }

        StringJoiner stringJoiner = new StringJoiner("");

        char previous = 0;
        char current;
        long count= 0;
        for (int i = 0 ; i < charArray.length; i++) {

            current = charArray[i];
            if (i == 0) { // first iteration
                // loop variables initialization
                previous = charArray[i];
                count = 1;
            } else {
                if (current == previous) {
                    count++;
                } else {
                    stringJoiner.add(Character.toString(previous));
                    stringJoiner.add(Long.toString(count));
                    // updating variables for next iteration
                    previous = current;
                    count = 1;
                }
            }

            if (i == charArray.length -1) { // last iteration
                // adding last element encoding
                stringJoiner.add(Character.toString(previous));
                stringJoiner.add(Long.toString(count));
            }
        }

        return stringJoiner.toString();
    }

    /**
     * encode the input string using Run Length Encoding (RLE) algorithm
     * <p>
     * REQUIREMENTS:
     * - input string contains characters in the set alphabet_1 = all character - digits
     * - input string must NOT contain numbers (to assure correct and unique decoding)
     * </p>
     * @param s input string to encode
     * @return RLE encoded string. if string is empty or blank return empty string
     */
    public static String encode(String s) {
        // check edge cases
        if (s.isEmpty()) {
            return s;
        }

        if (s.isBlank()) {
            return "";
        }

        StringJoiner stringJoiner = new StringJoiner("");

        // initializing variables
        char previous = s.charAt(0);
        char current;
        long count = 1;
        for (int i = 1 ; i < s.length(); i++) {

            current = s.charAt(i);
            if (current == previous) {
                 count++;
                 continue;
            }
            stringJoiner.add(Character.toString(previous));
            stringJoiner.add(Long.toString(count));

            previous = current;
            count = 1;
        }
        // adding the last character
        stringJoiner.add(Character.toString(previous));
        stringJoiner.add(Long.toString(count));

        return stringJoiner.toString();
    }

    public static void main(String[] args) {

        List<String> stringTestList = new ArrayList<>();
        stringTestList.add("wwwwaaadexxxxxx");
        stringTestList.add("aaaabbbccc");
        stringTestList.add("abbbcdddd");
        stringTestList.add("a");
        stringTestList.add("");
        stringTestList.add("    ");
        stringTestList.add("wwwwaaadexxxxxxxxxxxxxxx");

        for (String test : stringTestList) {
            System.out.println("input: \"" + test + "\"");
            System.out.println("encoded String: \"" + encode(test) + "\"");
            System.out.println("encoded String: \"" + encode(test.toCharArray()) + "\"");
        }

    }
}