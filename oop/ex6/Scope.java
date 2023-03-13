package oop.ex6;

import oop.ex6.main.fileException;
import oop.ex6.variable.Variable;

import java.util.HashMap;

/**
 * This department is responsible for the organs belonging to this scope
 */
public class Scope {

    public static final String SAME_VAR_NAME_IN_SCOPE_ERROR_MSG = "Same oop.ex6.variable is already exists " +
            "in this scope.";
    private final Scope outScope;
    private HashMap<String, Variable> varsInScope;

    /**
     * constructors
     * @param outScope  The scope we are in
     * @param varsInScope The variables that exist in this scope
     */

    public Scope(Scope outScope, HashMap<String, Variable> varsInScope) {
        this.outScope = outScope;
        this.varsInScope = varsInScope;
    }


    /**
     *
     * @return the scope
     */
    public Scope getOutScope() {
        return this.outScope;
    }

    /**
     *
     * @return The variables that exist in this scope
     */
    public HashMap<String, Variable> getVarsInScope() {
        return this.varsInScope;
    }


    /**
     * This function inserts members if they do not exist in the current scope
     * @param optionalVar
     * @throws fileException The error that is thrown if the variable exsit
     */
    public void addVarToScope(Variable optionalVar) throws fileException {
        // declaration: int a; \ int a = 6;
        if (varsInScope.containsKey(optionalVar.getName())) {
            throw new fileException(SAME_VAR_NAME_IN_SCOPE_ERROR_MSG);
        }
        varsInScope.put(optionalVar.getName(), optionalVar);
    }

    /**
     * This function checks if the variable exists in this scope or in the scopes above
     * @param varName the variable name
     * @return the variable if exist, else- null
     */
    public Variable checkIfExistVar(String varName) {
        // assignment: a = 8;
        //check if exist in this scope
        if (varsInScope.containsKey(varName)) {
            return varsInScope.get(varName);
        }

        //check if exist in previous scopes
        Scope thisScope = this;
        Scope currScope = this;
        while (currScope != null){ //will be null after we finish to iterate on outermost scoop
            if (currScope.getVarsInScope().containsKey(varName))
            {
                if (currScope == thisScope){
                    Variable var = currScope.getVarsInScope().get(varName);
                thisScope.varsInScope.put(var.getName(), var);
                return var;
                }
                else {
                    Variable var = currScope.getVarsInScope().get(varName).copy();
                    thisScope.varsInScope.put(var.getName(), var);
                    return var;
                }
            }
            currScope = currScope.getOutScope();
        }
        //we didn't find
        return null;
    }
}