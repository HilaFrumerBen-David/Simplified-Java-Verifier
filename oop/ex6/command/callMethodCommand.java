package oop.ex6.command;
import oop.ex6.main.fileException;
import oop.ex6.Scope;
import oop.ex6.regex;
import oop.ex6.variable.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;


/**
 * this class is responsible for handling the command of call another method , extends Command
 * class
 */
public class callMethodCommand extends Command {

    public static final String INVALID_SCOPE_CALL_METHOD_ERROR_MSG = "It is illegal to call a function in this scope";
    public static final String NO_EXIST_METHOD_ERROR_MSG = "This is a call to a function that does not exist";
    public static final String INVALID_ARGUMENT_MSG_ERROR = "invalid argument to this method";

    private static final String EMPTY_STRING = "";
    private static final int GROUP_METHOD_NAME = 1;
    private static final int GROUP_ARGS = 2;
    public static final String INVALID_NUMBER_OF_ARGUMENTS_MSG_ERROR = "invalid number of arguments";


    private final HashMap<String, ArrayList<Variable>> methodsHashMap;
    private final Matcher matcher;

    /**
     * constructor
     * @param line The line that is handled
     * @param scope The scope we are in
     * @param matcher  callMethodPattern.matcher(line)
     * @param methodsHashMap hashmap of all existing function names, with their arguments
     * @param type call method
     */
    public callMethodCommand(String line, Scope scope, Matcher matcher,
                             HashMap<String, ArrayList<Variable>> methodsHashMap, String type) {
        super(line, scope);
        this.setTypeCommand(type);
        this.methodsHashMap = methodsHashMap;
        this.matcher = matcher;
    }

    /**
     * This function checks if the call method in the command is valid
     * @throws fileException The error that is thrown if the call method is incorrect
     */
    @Override
    public void checkValidCommand() throws fileException {

        String methodName = matcher.group(GROUP_METHOD_NAME);
        String[] arrayOfCommand = matcher.group(GROUP_ARGS).split(regex.ONLY_COMMA, -1); //TODO david split -1

        // check if we are in the outermost scope
        if (scope.getOutScope() == null) {
            throw new fileException(INVALID_SCOPE_CALL_METHOD_ERROR_MSG);
        }

        // check if method is exists
        if (!methodsHashMap.containsKey(methodName)) {
            throw new fileException(NO_EXIST_METHOD_ERROR_MSG);
        }
        checkArguments(methodName, arrayOfCommand);
    }

    /**
     * This function checks whether the arguments that we put in the function to the function actually
     * correspond to the arguments that this function is supposed to receive
     * @param methodName the name of method
     * @throws fileException The error that is thrown if the call method is incorrect
     */
    private void checkArguments(String methodName, String[] arrayOfCommand) throws fileException {

        ArrayList<Variable> methodArgs = methodsHashMap.get(methodName);


        // check if equal size args
        if (methodArgs.size() != arrayOfCommand.length){
            if (methodArgs.size() ==0 && arrayOfCommand.length ==1 &&
            arrayOfCommand[0].equals("")){
                return;
            }
            throw new fileException(INVALID_NUMBER_OF_ARGUMENTS_MSG_ERROR);
        }

        int indCurr = 0;
        for (String arg : arrayOfCommand) {


            // clean the string of arg
            arg = arg.trim();
            if (arg.equals(EMPTY_STRING)) {
                throw new fileException(INVALID_NUMBER_OF_ARGUMENTS_MSG_ERROR);
            }
            // check if the argument corresponds to the structure of the variable in this place
            if (!methodArgs.get(indCurr).isValidValAssignment(arg)) {
                // check if the argument to method is variable that exist in outer scope
                Variable val = scope.checkIfExistVar(arg);
                if (val != null) {
                    if (!val.getIsInitialized() || !val.isValidVarAssignment(methodArgs.get(indCurr))) {
                        throw new fileException(INVALID_ARGUMENT_MSG_ERROR);
                    }
                }
                else {
                    throw new fileException(INVALID_ARGUMENT_MSG_ERROR);
                    }
                }
            indCurr++;
        }
    }
}
