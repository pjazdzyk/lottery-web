package pl.lottery.resultsannouncer;

import java.util.regex.Pattern;

public class UuidValidator {
    private final static Pattern UUID_REGEX_PATTERN = Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    public static boolean isValidUUID(String uuidAsString) {
        if (uuidAsString == null) {
            return false;
        }
        return UUID_REGEX_PATTERN.matcher(uuidAsString).matches();
    }

    public static boolean isNotValidUUID(String uuidAsString){
        return !isValidUUID(uuidAsString);
    }

}
