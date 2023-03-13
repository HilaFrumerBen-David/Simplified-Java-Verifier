package oop.ex6.command;
import oop.ex6.main.fileException;
import oop.ex6.Scope;
import oop.ex6.variable.Variable;

import java.util.regex.Matcher;


/**
 * this class is responsible for handling the command of putting some value to the member , extends Command
 * class
 */
public class AssignmentCommand extends Command{

    public static final String NOT_EXIST_VAR_TO_ASSIGNMENT_ERROR_MSG = "This variable does not exist, " +
            "so it cannot be assigned";
    public static final String INVALID_ASSIGNMENT_ERROR_MSG = "The assignment value does not fit the " +
            "variable";
    private static final int GROUP_VAR = 1;
    private static final int GROUP_ASSIGNMENT = 4;

    private final Matcher matcher;


    /**
     * constructor
     * @param line The line that is handled
     * @param scope The scope we are in
     * @param matcher  assignmentPattern.matcher(line)
     * @param type var assignment
     */
    public AssignmentCommand(String line, Scope scope, Matcher matcher, String type) {
        super(line, scope);
        this.setTypeCommand(type);
        this.matcher = matcher;

    }

    /**
     * This function checks if the assignment appearing in the command is valid
     * @throws fileException The error that is thrown if the assignment is incorrect
     */
    @Override
    public void checkValidCommand() throws fileException {

        String var = matcher.group(GROUP_VAR);
        String assignmentToVar = matcher.group(GROUP_ASSIGNMENT);

        // check if the variable is exists in the code
        Variable resVar = scope.checkIfExistVar(var);
        if (resVar == null){
                throw new fileException(NOT_EXIST_VAR_TO_ASSIGNMENT_ERROR_MSG);
            }

        tryAssignmentVar(assignmentToVar, resVar);
    }

    /**
     *
     * @param assignmentToVar What you want to put inside the variable
     * @param resVar  The variable where we want to put the new value
     * @throws fileException The error that is thrown if the assignment is incorrect
     */
    private void tryAssignmentVar (String assignmentToVar, Variable resVar) throws fileException {

        // check if the resVar is not final
        if (!resVar.getIsFinal()) {

            // check if the assignmentToVar value corresponds to the structure of resVar
            if (resVar.isValidValAssignment(assignmentToVar)) {
                resVar.setIsInitialized(true);
            }

            // check if the assignmentToVar value is another variable
            Variable varAssignmentToVar = scope.checkIfExistVar(assignmentToVar);
            if (varAssignmentToVar != null && varAssignmentToVar.getIsInitialized() &&
                    resVar.isValidVarAssignment(varAssignmentToVar)){
                resVar.setIsInitialized(true);
            }
        }
        else
        {
            throw new fileException(INVALID_ASSIGNMENT_ERROR_MSG);
        }
    }

}
