package data.dictionary.generator.common;

/**
 * @author mjh
 * @create 2019-12-13
 */
public class CommonUtils {


    /**
     * Judge string is blank or not
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(String string) {
        return !isBlank(string);
    }

}
