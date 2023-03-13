package oop.ex6.command;

import oop.ex6.main.fileException;
import oop.ex6.Scope;
import oop.ex6.regex;
import oop.ex6.variable.booleanVariable;
import oop.ex6.variable.Variable;


/**
 * This class is responsible for handling the state of the while loop and the state of the if condition
 */
public class whileOrIfCommand extends Command {

    private static final String CLOSE_BRACKET = ")";
    private static final String OPEN_BRACKET = "(";

    public static final String INVALID_CONDITION_ERROR_MSG = "The condition is not valid";


    /**
     * constructor
     * @param line The line that is handled
     * @param scope The scope we are in
     * @param type while of if
     */
    whileOrIfCommand (String line, Scope scope, String type){
        super(line, scope);
        this.setTypeCommand(type);
    }

    /**
     * initialize the array of command
     * @return array of command
     */
    private String[] createArrayCommand() {
        String[] splitOpenBracket = this.line.split(regex.OPEN_COND_BRACKET);
        String[] splitCloseBracket = splitOpenBracket[1].split(regex.CLOSE_COND_BRACKET);
        String condition = splitCloseBracket[0];
        String[] arrayOfCommand = condition.split(regex.OR_AND);
        return  arrayOfCommand;
    }

    /**
     * This function checks if the while loop or if condition appearing in the command is valid
     * @throws fileException The error that is thrown if the while or if command is incorrect
     */
    @Override
    public void checkValidCommand() throws fileException {

        String[] arrayOfCommand  = createArrayCommand();
        for (String command : arrayOfCommand) {
            // clean the white space before and after the command
            command = command.trim();

            booleanVariable var = new booleanVariable(null,false, false,null);

            Variable resVar = scope.checkIfExistVar(command);
            if (resVar != null && command.length() == 1) {
                if (checkResVar(resVar)) {
                    return;
                }
                else {
                    throw new fileException(INVALID_CONDITION_ERROR_MSG);
                }
            }
            //check if the command is like boolean - true\ false\ int \ double, if not:
            if (!var.isValidValAssignment(command)){

                //check if the command is variable that exist in the code
                checkResVarExist(resVar, var);
            }
        }
    }

    /**
     * check if the command is variable that exist in the code
     * @param resVar The variable that appears inside the condition
     * @param var boolean Variable
     * @throws fileException The error that is thrown if the res var is incorrect
     */
    private void checkResVarExist(Variable resVar, Variable var) throws fileException {
        if (resVar != null) {
            //check if the resVar is initialized and in from - boolean or int or double
            if (!(resVar.getIsInitialized() && var.isValidVarAssignment(resVar))){
                throw new fileException(INVALID_CONDITION_ERROR_MSG);
            }
        } else {
            throw new fileException(INVALID_CONDITION_ERROR_MSG);
        }
    }

    /**
     * Handles the case that resVar appears alone in the condition
     * @param resVar The variable that appears in the condition
     * @return Returns whether it is valid or not
     */
    private boolean checkResVar(Variable resVar) {
        return resVar.getIsInitialized() && (resVar.getType().equals("double") ||
                resVar.getType().equals("int") ||
                resVar.getType().equals("boolean"));
    }
}
