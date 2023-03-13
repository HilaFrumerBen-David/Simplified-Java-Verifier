package oop.ex6.parser;


import oop.ex6.main.fileException;
import oop.ex6.regex;
import oop.ex6.variable.Variable;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oop.ex6.variable.variablesFactory;

/**
 * This class is responsible for the first pass over the file
 */
public class firstFilter {
    public static final int ARGUMENT_GROUP = 0;
    private static final int GROUP_METHOD_NAME = 1;
    private static final int GROUP_METHOD_ARGS = 2;
    public static final String METHOD_NAME_ERROR_MSG = "method name is not valid";
    public static final String SAME_METHOD_NAME_ERROR_MSG = "Method with the same name is already exists";
    public static final String SAME_ARG_NAME_ERROR_MSG = "Argument with the same name is already exists";
    private static final int NAME_ARG_GROUP = 4;
    private static final int TYPE_ARG_GROUP = 3;
    private BufferedReader bufferedReader;

    /**
     * constructor
     * @param bufferedReader buffered Reader of the file
     */
    public firstFilter(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    /**
     * This function is responsible for deleting all the comments that appear in the file, as well as binding
     * the methods and their arguments that appear in the file
     * @param fileLines The lines of the file are separated by line breaks
     * @param methodsHashMap hashmap of all existing function names, with their arguments
     * @throws IOException errors from IO
     * @throws fileException The error that is thrown if the clear is incorrect
     */
    public void clearFile( ArrayList<String> fileLines, HashMap<String, ArrayList<Variable>> methodsHashMap)
            throws IOException, fileException {

        String line;
        Pattern patternGoodComment = Pattern.compile(regex.GOOD_COMMENT);
        Pattern patternWhiteSpaceAll = Pattern.compile(regex.WHITE_SPACE_ALL);
        Pattern patternMethodDeclaration = Pattern.compile(regex.METHOD_DECELERATION);

        while ((line = bufferedReader.readLine()) != null) {
            Matcher matcherGoodComment = patternGoodComment.matcher(line);
            Matcher matcherWhiteSpaceAll = patternWhiteSpaceAll.matcher(line);
            Matcher matcherMethodDeclaration = patternMethodDeclaration.matcher(line);
            if ((!matcherGoodComment.matches()) && (!matcherWhiteSpaceAll.matches())) {
                fileLines.add(line);
            }
            if (matcherMethodDeclaration.matches()) {// check if starting method
                createMethodDeclaration(matcherMethodDeclaration, methodsHashMap);
            }
        }
    }


    /**
     * Handles the declaration of the function and if valid insert to hashmap
     * @param matcherMethodDeclaration patternMethodDeclaration.matcher(line)
     * @param methods hashmap of all existing function names, with their arguments
     * @throws fileException The error that is thrown if the method declaration is incorrect
     */
    private void createMethodDeclaration(Matcher matcherMethodDeclaration, HashMap<String,
            ArrayList<Variable>> methods)
            throws fileException {
        ArrayList<String> nameOfArgsMapped = new ArrayList<>();
        String methodName = "";
        Pattern argPattern = Pattern.compile(regex.METHOD_ARG); // one argument
        String optionalMethodName = matcherMethodDeclaration.group(GROUP_METHOD_NAME);
        checkValidMethodName(optionalMethodName);
        methodName = optionalMethodName;
        String methodArgs = matcherMethodDeclaration.group(GROUP_METHOD_ARGS); // all arguments
        ArrayList<Variable> varMethodArgs = new ArrayList<Variable>();

        if (methodArgs != null) { // there are arguments in method
            Matcher matcherArg = argPattern.matcher(methodArgs); // find one argument
            while (matcherArg.find()) { // finds each argument
                Matcher matcherArgTemp = argPattern.matcher(matcherArg.group(ARGUMENT_GROUP)); // 0, the argument,
                // easy to divide
                // the components (name) from it
                if (matcherArgTemp.find()) {
                    String nameArg = matcherArg.group(NAME_ARG_GROUP); // 4

                    if (nameOfArgsMapped.contains(nameArg))
                    {
                        throw new fileException(SAME_ARG_NAME_ERROR_MSG);
                    }
                    nameOfArgsMapped.add(nameArg);
                    String typeArg = matcherArg.group(TYPE_ARG_GROUP); //  3
                    boolean isFinalArg = matcherArg.group(1).trim().equals("final");

                    varMethodArgs.add(variablesFactory.createVariable(isFinalArg, nameArg, typeArg,
                            true));
                }
            }
        }
        if (methods.containsKey(methodName)) {
            throw new fileException(SAME_METHOD_NAME_ERROR_MSG);
        }
        methods.put(methodName, varMethodArgs);
    }

    /**
     * check if theMethod Name is valid
     * @param optionalMethodName the name
     * @throws fileException The error that is thrown if the name is incorrect
     */
    private void checkValidMethodName(String optionalMethodName) throws fileException {
        Pattern patternNameMethod = Pattern.compile(regex.METHOD_NAME);
        Matcher matcherNameMethod = patternNameMethod.matcher(optionalMethodName);
        if (!matcherNameMethod.matches()) {
            throw new fileException(METHOD_NAME_ERROR_MSG);
        }
    }
}
