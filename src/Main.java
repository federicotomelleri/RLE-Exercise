import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Main {

    private static final String ESCAPE_CHARACTER = "\\";

    /**
     * decode a string that was previously encoded with RLE algorithm
     * <p>
     *     REQUIREMENTS: <br>
     *     - digits in input encoded string must be used only to point out the number of repetition for a certain character. <br>
     *       It means characters in the output string will be part of the alphabet_1 = all characters - digits
     * </p>
     * @param s string to decode
     * @return decoded string. if input string is empty or blank return empty string
     */
    public static String decode(String s) {
        if (s.isEmpty()) {
            return s;
        }

        if (s.isBlank()) {
            return "";
        }

        StringJoiner decodedStringJoiner = new StringJoiner("");

        char current;
        String currentStr;
        StringJoiner countStringJoiner;
        long count = 0;
        for (int i = 0; i < s.length(); i++) {
            current = s.charAt(i);

            if (Character.isDigit(current)) {
                continue;
            }

            countStringJoiner = new StringJoiner("");
            for (int j = i +1; j < s.length(); j++) {
                if (!Character.isDigit(s.charAt(j))) {
                    break;
                }
                countStringJoiner.add(Character.toString(s.charAt(j)));
            }

            count = Long.parseLong(countStringJoiner.toString());

            currentStr = Character.toString(current);
            for (int k = 0; k < count; k++) {
                decodedStringJoiner.add(currentStr);
            }
        }

        return decodedStringJoiner.toString();
    }

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
     * - input string contains characters in the set alphabet_1 = all character - digits <br>
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

    public static String encodeNoCharRestriction(String s) {
        // check edge cases
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
            // adding escape character
            stringJoiner.add(ESCAPE_CHARACTER);
            stringJoiner.add(Character.toString(previous));
            stringJoiner.add(Long.toString(count));

            previous = current;
            count = 1;
        }
        // adding the last character
        stringJoiner.add(ESCAPE_CHARACTER);
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

        String encodedString;
        String encodedStringFromCharArray;
        String encodedStringNoCharRestriction;
        for (String test : stringTestList) {

            encodedString = encode(test);
            encodedStringFromCharArray = encode(test.toCharArray());
            encodedStringNoCharRestriction = encodeRLE(test);
            System.out.println("input: \"" + test + "\"");
            System.out.println("encoded String: \"" + encodedString + "\"");
            System.out.println("encoded String: \"" + encodedStringFromCharArray + "\"");
            System.out.println("encoded String: \"" + encodedStringNoCharRestriction + "\"");
            System.out.println("are encoded Strings equals: " + encodedString.equalsIgnoreCase(encodedStringFromCharArray));
            System.out.println("is decoded String equals to input: " + test.equalsIgnoreCase(decode(encodedString)));
        }

        stringTestList = new ArrayList<>();
        // wwwwaaadexxxxx\x
        stringTestList.add("wwwwaaadexxxxx\\x");
        // aaaa\\\\\bbbccc
        stringTestList.add("aaaa\\\\\\\\\\bbbccc");
        // aaaa\\\\\bbb1111ccc333332222ddddd77777eeeeee1111111222223333300000www
        stringTestList.add("aaaa\\\\\\\\\\bbb1111ccc333332222ddddd77777eeeeee1111111222223333300000www");
        for (String test : stringTestList) {
            encodedStringNoCharRestriction = encodeNoCharRestriction(test);
            System.out.println("input: \"" + test + "\"");
            System.out.println("encoded String: \"" + encodedStringNoCharRestriction + "\"");
            //System.out.println("is decoded String equals to input: " + test.equalsIgnoreCase(decode(encodedString)));
        }

    }
}