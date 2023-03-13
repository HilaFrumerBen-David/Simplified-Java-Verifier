package oop.ex6.command;

import oop.ex6.Scope;
import oop.ex6.main.fileException;

/**
 * An abstract class that aims to unify all commands
 */
public abstract class Command {

    protected final String line;
    private String typeCommand;
    protected Scope scope;

    /**
     * constructor
     * @param line The line that is handled
     * @param scope The scope we are in
     */
    public Command(String line, Scope scope) {
        this.line = line;
        this.scope = scope;
        this.typeCommand = null;
    }

    /**
     * check if the command is valid
     * @throws fileException The error that is thrown if the call method is incorrect
     */
    public abstract void checkValidCommand() throws fileException;

    /**
     * getter for type command
     * @return type of command
     */
    public String getTypeCommand() { return typeCommand; }

    /**
     * set the type command
     * @param type type of command
     */
    public void setTypeCommand(String type)
    {
        this.typeCommand = type;
    }
}
