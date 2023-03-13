package oop.ex6;

/**
 * This class gathers all the regixes that appear in the code
 */
public class regex {

    public static final String GOOD_COMMENT = "\\/\\/.*";

    public static final String WHITE_SPACE_ALL = "\\s*";
    public static final  String METHOD_NAME_DEC =  "([a-zA-Z]+[a-zA-Z0-9_]*)";
    public static final String METHOD_ARG = "((final\\s+)?)\\s*(int|double|char|String|boolean)" +
            "\\s+" + regex.VAR_NAME;

    public static final String METHOD_DECELERATION = "\\s*void\\s+" +
            regex.METHOD_NAME_DEC +
            "\\s*\\((\\s*((final\\s+)?\\s*(int|double|boolean|char|String)\\s+" +
            regex.VAR_NAME +
            "+(\\s*,\\s*(final\\s+)?\\s*(int|double|boolean|char|String)\\s+" +
            regex.VAR_NAME +
            "\\s*)*)?\\s*)\\s*\\)\\s*\\{\\s*";
    public static final String VAR_ASSIGNMENT = "\\s*" + regex.VAR_NAME +
            "\\s*=\\s*((-|\\+)?\\d*\\.?\\d*|'.'|\"[^\"]+\"|" + regex.VAR_NAME + ")\\s*;\\s*";

    public static final String RETURN = "\\s*return\\s*;\\s*";

    public static final String VAR_DECLARATION_AND_ASSIGNMENT = "\\s*(final\\s+)?\\s*(int|char|boolean|String" +
            "|double)\\s+(" + regex.VAR_NAME +
            "\\s*(=\\s*((-|\\+)?\\d*\\.?\\d*|'.'|\"[^\\\"]*\"|" + regex.VAR_NAME + "))?" +
            "(\\s*,\\s*" + regex.VAR_NAME +
            "\\s*(=\\s*((-|\\+)?\\d*\\.?\\d*|'.'|\"[^\\\"]*\"|" + regex.VAR_NAME + "))?)*)\\s*;\\s*";

    public static final String WHILE_OR_IF = "\\s*(if|while)\\s*\\((\\s*(-|\\+)?[\\w\\.]+\\s*)" +
            "((\\|\\||&&)\\s*[\\w\\.]+\\s*)*\\)\\s*\\{\\s*";
    public static final String CALL_METHOD ="\\s*(" + regex.METHOD_NAME +
            ")\\s*\\(\\s*([a-zA-Z0-9_\"\'\\.]*\\s*(\\s*,\\s*[a-zA-Z0-9_\"\'\\.]+\\s*)*)\\s*\\)" +
            "\\s*;\\s*";
    public static final String METHOD_NAME = "[a-zA-Z]+[a-zA-Z0-9_]*";
    public static final String VAR_NAME = "(([a-zA-Z][a-zA-Z0-9_]*)|([_][a-zA-Z0-9_]+))";
    public static final String CHAR = "'.'";
    public static final String ONLY_COMMA = ",";
    public static final String EQUAL = "=";
    public static final String STRING = "\".*\"";
    public static final String BOOLEAN = "(true|false|(-|\\+)?(\\d*)?(\\.\\d*)?)";
    public static final String INTEGER = "(-|\\+)?(\\d+)";
    public static final String DOUBLE = "((-|\\+)?(\\d*)?(\\.\\d*)?)";
    public static final String OR_AND = "(\\|\\|) | (&&)";
    public static final String CLOSE_BRACKET = "\\s*}\\s*";
    public static final String OPEN_BRACKET = ".*\\{\\s*";
    public static final String OPEN_COND_BRACKET = "\\(";
    public static final String CLOSE_COND_BRACKET = "\\)";
}
