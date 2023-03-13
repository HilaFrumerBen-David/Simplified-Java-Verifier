package oop.ex6.command;

import oop.ex6.Scope;
import oop.ex6.main.fileException;
import oop.ex6.regex;
import oop.ex6.variable.Variable;
import oop.ex6.variable.variablesFactory;

import java.util.regex.Matcher;


/**
 *This function is responsible for handling the declaration of a variable and assigning a value to it
 *  , extends Command
 * class
 */
public class DeclarationAssignmentCommand extends Command{
    private static final int GROUP_FINAL = 1;
    private static final int GROUP_TYPE = 2;
    private static final int GROUP_VARIABLES = 3;
    private static final int IND_NAME = 0;
    private static final int IND_ASSIGNMENT = 1;
    public static final String FINAL_ERROR_MSG = "final variable need to assignment";
    public static final String INVALID_ASSIGNMENT_MSG_ERROR = "assignment is not valid";
    private Boolean isFinal;
    private final Matcher matcher;


    /**
     * constructor
     * @param line The line that is handled
     * @param scope The scope we are in
     * @param matcher  declarationPattern.matcher(line)
     * @param type variable declaration and assignment
     */
    public DeclarationAssignmentCommand(String line, Scope scope, Matcher matcher, String type)  {
        super(line, scope);
        this.setTypeCommand(type);
        this.matcher = matcher;
    }

    /**
     * This function checks if the declaration and the assignment appearing in the command is valid
     * @throws fileException The error that is thrown if the decleration and  assignment is incorrect
     */
    @Override
    public void checkValidCommand() throws fileException {
        String[] arrayOfCommand = initialize();

        for (String var : arrayOfCommand) {
            var = var.trim();

            // the command is only declaration
            if (!var.contains(regex.EQUAL)) {
                if (!isFinal) {
                    Variable variable = variablesFactory.createVariable(isFinal, var, getTypeCommand(),
                            false);
                    scope.addVarToScope(variable);
                } else {
                    throw new fileException(FINAL_ERROR_MSG);
                }
            }

            // the command is assignment and declaration
            else {

                String[] nameAndAssignment = var.split(regex.EQUAL);
                String nameVar = nameAndAssignment[IND_NAME].trim();
                String assignmentToVar = nameAndAssignment[IND_ASSIGNMENT].trim();
                Variable newVar = variablesFactory.createVariable(isFinal, nameVar, this.getTypeCommand(),
                        true);

                // check if the assignmentToVar value corresponds to the structure of newVar
                if (newVar.isValidValAssignment(assignmentToVar)) {
                    newVar.setIsInitialized(true);
                    scope.addVarToScope(newVar);
                    continue;
                }

                // check if the assignmentToVar value is another variable
                Variable resVar = scope.checkIfExistVar(assignmentToVar);
                if (resVar != null && resVar.getIsInitialized() &&
                        newVar.isValidVarAssignment(resVar)) {
                    newVar.setIsInitialized(true);
                    scope.addVarToScope(newVar);
                } else {
                    throw new fileException(INVALID_ASSIGNMENT_MSG_ERROR);
                }
            }
        }
    }

    /**
     * initialize the commands
     * @return array of commands in this line
     */
    private String[] initialize() {
        String type = matcher.group(GROUP_TYPE);
        this.setTypeCommand(type);

        // TODO : group include the name : a = 5 or a;
        String[] arrayOfCommand = matcher.group(GROUP_VARIABLES).split(regex.ONLY_COMMA);
        this.isFinal = matcher.group(GROUP_FINAL) != null;
        return arrayOfCommand;
    }
}
