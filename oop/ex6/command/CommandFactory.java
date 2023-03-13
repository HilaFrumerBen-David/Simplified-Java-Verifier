package oop.ex6.command;
import oop.ex6.regex;
import oop.ex6.main.fileException;
import oop.ex6.Scope;
import oop.ex6.variable.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is a factory for creating the appropriate command
 */
public class CommandFactory {

    public static final String WHILE_OR_IF = "while of if";
    private static final String CALL_METHOD = "call method";
    public static final String  METHOD_DECLARATION = "method declaration";
    private static final String  VAR_DECLARATION_AND_ASSIGNMENT = "variable declaration and assignment";
    private static final String  VAR_ASSIGNMENT = "variable assignment";
    public static final String  RETURN = "return";
    private static final String INVALID_COMMAND = "The command is not valid";


    /**
     * this function create the appropriate command and return this
     * @param scope The scope we are in
     * @param methodsHashMap hashmap of all existing function names, with their arguments
     * @param line The line that is handled
     * @return Command
     * @throws fileException The error that is thrown if the create command  is incorrect
     */
    public static Command createCommand(Scope scope, HashMap<String, ArrayList<Variable>> methodsHashMap, String line)
            throws fileException {

        // call method
        Pattern callMethodPattern = Pattern.compile(regex.CALL_METHOD);
        Matcher callMethodMatcher = callMethodPattern.matcher(line);
        if (callMethodMatcher.matches()) {
            callMethodCommand callMethodCommand = new
                    callMethodCommand(line, scope,callMethodMatcher, methodsHashMap, CALL_METHOD);
            callMethodCommand.checkValidCommand();
            return callMethodCommand;
        }

        // Declaration of method
        Pattern methodDeclarationPattern = Pattern.compile(regex.METHOD_DECELERATION);
        Matcher methodDeclarationMatcher = methodDeclarationPattern.matcher(line);
        if (methodDeclarationMatcher.matches()) {
            return new methodDeclarationCommand(line, scope, METHOD_DECLARATION);
        }

        // declaration (and assignment) of variable
        Pattern declarationPattern = Pattern.compile(regex.VAR_DECLARATION_AND_ASSIGNMENT);
        Matcher declarationMatcher = declarationPattern.matcher(line);
        if (declarationMatcher.matches()) {
            DeclarationAssignmentCommand decAssCommand = new DeclarationAssignmentCommand(line, scope,
                    declarationMatcher, VAR_DECLARATION_AND_ASSIGNMENT);
            decAssCommand.checkValidCommand();
            return decAssCommand;
        }

        // assignment of variable
        Pattern assignmentPattern = Pattern.compile(regex.VAR_ASSIGNMENT);
        Matcher assignmentMatcher = assignmentPattern.matcher(line);
        if (assignmentMatcher.matches()) {
            AssignmentCommand assignmentCommand = new AssignmentCommand(line, scope, assignmentMatcher,
                    VAR_ASSIGNMENT);
            assignmentCommand.checkValidCommand();
            return assignmentCommand;
        }

        // while or if
        Pattern whileIfPattern = Pattern.compile(regex.WHILE_OR_IF);
        Matcher whileIfMatcher = whileIfPattern.matcher(line);
        if (whileIfMatcher.matches()) {
            whileOrIfCommand whileOrIfCommand = new whileOrIfCommand(line, scope, WHILE_OR_IF);
            whileOrIfCommand.checkValidCommand();
            return whileOrIfCommand;
        }

        // return
        Pattern returnPattern = Pattern.compile(regex.RETURN);
        Matcher returnMatcher = returnPattern.matcher(line);
        if (returnMatcher.matches()) {
            return new ReturnCommand(line, scope, RETURN);
        }

        // else - ERROR
        throw new fileException(INVALID_COMMAND);
    }
}






