package oop.ex6.parser;

import oop.ex6.command.AssignmentCommand;
import oop.ex6.command.Command;
import oop.ex6.command.DeclarationAssignmentCommand;
import oop.ex6.main.fileException;
import oop.ex6.variable.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oop.ex6.regex;
import oop.ex6.Scope;
import oop.ex6.command.CommandFactory;

/**
 * This department is responsible for the rest of the processing of the file after the initial processing
 */
public class mainFilter {

    public static final String INVALID_CLOSE_BRACKETS_ERROR_MSG = "there are too many close brackets";
    public static final String FINISH_FUNC_WITHOUT_RETURN_ERROR_MSG = "At the end of a function there " +
            "should be return";
    private static final String INVALID_RETURN_STATEMENT_ERROR_MSG = "invalid return statement";
    public static final String METHOD_IN_METHOD_MSG_ERROR = "method not can be in another method";
    public static final String VOID = "void";
    public static final String OPEN_BRACKET = "(";
    private final HashMap<String, ArrayList<Variable>> methodsHashMap;

    private final ArrayList<String> fileValidLines;
    private final Pattern patternCloseBracket = Pattern.compile(regex.CLOSE_BRACKET);
    private final Pattern patternOpenBracket = Pattern.compile(regex.OPEN_BRACKET);
    private boolean methodFlag;
    private boolean returnFlag;
    private static final int GROUP_METHOD_NAME = 1;

    private int numBrackets;
    HashMap<String, Variable> globalVarsHashMap = new HashMap<>();

    /**
     * constructor
     * @param fileValidLines The valid lines of the file are separated by line breaks
     * @param methodsHashMap hashmap of all existing function names, with their arguments
     */
    public mainFilter(HashMap<String, ArrayList<Variable>> methodsHashMap, ArrayList<String> fileValidLines) {
        this.methodsHashMap = methodsHashMap;
        this.fileValidLines = fileValidLines;
    }

    /**
     * The function that runs all the tests
     * @throws fileException The error that is thrown if the main filter is incorrect
     */
    public void runMainFilter() throws fileException {

        Scope curScope = new Scope(null, globalVarsHashMap);
        createGlobalVar(curScope);
        returnFlag = false;
        methodFlag = false;
        numBrackets = 0;

        for (String line : fileValidLines) {

            // if the line is : } , so we come back to the outer scope
            if (patternCloseBracket.matcher(line).matches()) {
                numBrackets--;

                curScope = curScope.getOutScope();
                if (curScope == null) {
                    throw new fileException(INVALID_CLOSE_BRACKETS_ERROR_MSG);
                }
                if (curScope.getOutScope() == null){ // on the outermost scope
                    if (!returnFlag ){
                        throw new fileException(FINISH_FUNC_WITHOUT_RETURN_ERROR_MSG);
                    }
                    methodFlag = false;
                }
                returnFlag = false;
                continue;
            }

            // else - line is command
            curScope = handleCommand(curScope, line);
        }
        if (numBrackets != 0){
            throw new fileException("not equal { } brackets");
        }
    }

    /**
     * This function handles the command according to the options the commands have
     * @param curScope The scope we are in
     * @param line The row we are in
     * @return Scope
     * @throws fileException The error that is thrown if the command is incorrect
     */
    private Scope handleCommand( Scope curScope, String line) throws fileException {
        Command command = CommandFactory.createCommand(curScope, methodsHashMap, line);

        if (command.getTypeCommand().equals(CommandFactory.RETURN)){
            if (!methodFlag){
                throw new fileException(INVALID_RETURN_STATEMENT_ERROR_MSG);
            }
            returnFlag = true;
        }

        if (!command.getTypeCommand().equals(CommandFactory.RETURN)) {
            returnFlag = false;
        }

        if (command.getTypeCommand().equals(CommandFactory.WHILE_OR_IF)) {
            HashMap<String, Variable> variableScopeHashMap = new HashMap<>();
            numBrackets++;
            return new Scope(curScope, variableScopeHashMap);
        }

        if (command.getTypeCommand().equals(CommandFactory.METHOD_DECLARATION)){
            HashMap<String, Variable> variableScopeHashMap = new HashMap<>();
            Scope scope = new Scope(curScope, variableScopeHashMap);
            addVarOfMethodDeclaration(line, scope, methodsHashMap);
            numBrackets++;
            if (methodFlag)
            {
                throw new fileException(METHOD_IN_METHOD_MSG_ERROR);
            }
            methodFlag = true;

            curScope = scope;
        }
        return curScope;
    }

    /**
     *
     * @param scope The scope we are in
     * @param line The row we are in
     * @param methodHashMap  hashmap of all existing function names, with their arguments
     * @throws fileException The error that is thrown if the Method Declaration is incorrect
     */

    private void addVarOfMethodDeclaration(String line, Scope scope, HashMap<String, ArrayList<Variable>>
            methodHashMap)
            throws fileException {
        String methodName = line.substring(line.indexOf(VOID) + VOID.length(),
                line.indexOf(OPEN_BRACKET));

        ArrayList<Variable> vars = methodHashMap.get(methodName.trim());
        for (Variable var : vars){
            scope.addVarToScope(var);
        }
    }


    /**
     * This function is responsible for creating the global variables in the program
     * @param outerScope The outer scope, where we are
     * @throws fileException  The error that is thrown if the createGlobalVar is incorrect
     */
    private void createGlobalVar(Scope outerScope) throws fileException {
        int numOfBracketsScopes = 0;
        ArrayList<String> validLinesTemp = new ArrayList<>(fileValidLines);

        for (String line : validLinesTemp) {
            if (patternOpenBracket.matcher(line).matches()) {
                numOfBracketsScopes++;
                continue;
            }
            if (patternCloseBracket.matcher(line).matches()) {
                numOfBracketsScopes--;
                continue;
            }
            if (numOfBracketsScopes == 0) {
                handleGlobalVars(line, outerScope);
            }
        }
    }

    /**
     *
     * @param line The row we are in
     * @param outerScope The outer scope, where we are
     * @throws fileException The error that is thrown if the create Global Var is incorrect
     */
    private void handleGlobalVars(String line, Scope outerScope) throws fileException {

        Pattern varDeclarationPattern = Pattern.compile(regex.VAR_DECLARATION_AND_ASSIGNMENT);
        Matcher varDeclarationMatcher = varDeclarationPattern.matcher(line);

        if (varDeclarationMatcher.matches()) {
            DeclarationAssignmentCommand declarationAssignmentCommand = new DeclarationAssignmentCommand(line
                    , outerScope, varDeclarationMatcher, null);
            declarationAssignmentCommand.checkValidCommand();
            fileValidLines.remove(line);
        }

        Pattern varAssignmentPattern = Pattern.compile(regex.VAR_ASSIGNMENT);
        Matcher varAssignmentMatcher = varAssignmentPattern.matcher(line);

        if (varAssignmentMatcher.matches()) {
            AssignmentCommand assignmentCommand = new AssignmentCommand(line, outerScope,
                    varAssignmentMatcher, null);
            assignmentCommand.checkValidCommand();
            fileValidLines.remove(line);
        }
    }
}



