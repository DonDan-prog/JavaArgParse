package ArgumentParser;

import java.util.HashSet;
import java.util.LinkedList;

public class ArgumentParser 
{
    /** Three types of arguments - three collections to keep them */
    private LinkedList<Argument> positionalArguments;
    private HashSet<Argument> requiredArguments;
    private HashSet<Argument> nonRequiredArguments;
    /** The specified app */
    private String fileName = "APP";
    
    /** Default constructor; the name of app will be APP */
    public ArgumentParser()
    {
        this.positionalArguments = new LinkedList<Argument>();
        this.requiredArguments = new HashSet<Argument>();
        this.nonRequiredArguments = new HashSet<Argument>();
    }

    /** Parametrized constructor with the name of current app for cute show
     *  @param String for specify the file name
     */
    public ArgumentParser(String fileName)
    {
        this();
        this.fileName = fileName;
    }

    /** The parse function of inputted args
     *  @param String[] args from command promt
     *  @throws Exception
     */
    public void parse(String[] args) throws Exception
    {
        /** If the length of positional args and required args with specificators and vars if greater than specified args, then there's an error */
        if(args.length < (this.positionalArguments.size() + this.requiredArguments.size() * 2))
            throw new Exception("not enough arguments");
        /** Iterate over first positional args and parse them */
        int i = 0;
        for(; i < this.positionalArguments.size(); i++)
            this.positionalArguments.get(i).setResource(args[i]);
        /** Iterate over rest of args, the first must be argument, second - the variable.
         *  The priority of required and non-required is simple: no special order in it
         */
        for(int j = 0, k = i; j < this.requiredArguments.size() && k < args.length; k += 2, j++)
        {   
            /** Search for actual argument */
            for(Argument arg: this.requiredArguments)
            {
                if(arg.isArgument(args[i]) == true)
                {
                    arg.setResource(args[i + 1]);
                    break;
                }
            }
        }
        for(int j = 0, k = i; j < this.nonRequiredArguments.size() && k < args.length; k += 2, j++)
        {
            /** Search for actual argument */
            for(Argument arg: this.nonRequiredArguments)
            {
                if(arg.isArgument(args[i]) == true)
                {
                    arg.setResource(args[i + 1]);
                    break;
                }
            }
        }
    }
    
    /** Return the help string
     *  @return Help String of maded rules for this ArgParse
     */
    public String toString()
    {
        /** Init the ret string */
        String ret = "Usage: java " + this.fileName + " ";
        /** Correctly output the amount of args need to specify */
        for(Argument i: this.positionalArguments)
            ret += i.getArgName() +  " ";
        for(Argument i: this.requiredArguments)
            ret += '<' + i.getArgName() + "> ";
        for(Argument i: this.nonRequiredArguments)
            ret += '[' + i.getArgName() + "] ";

        ret += "\n\nCommands help:";
        /** Check for non zero length of positional args and print them with info */
        if(this.positionalArguments.size() > 0)
            ret += "Positional args:\n";
        for(Argument i: this.positionalArguments)
            ret += '\t' + i.getArgName() + ' ' + i.getInfo() + '\n';
        /** Check for non zero length of requiredArguments args and print them with info */
        if(this.requiredArguments.size() > 0)
            ret += "\nRequired flag arguments:\n";
        for(Argument i: this.requiredArguments)
            ret += '\t' + i.getShortcut() + i.getInfo() + '\n';
        /** Check for non zero length of nonRequiredArguments args and print them with info */
        if(this.nonRequiredArguments.size() > 0)
            ret += "\nNon-required flag arguments:\n";
        for(Argument i: this.nonRequiredArguments)
            ret += '\t' + i.getShortcut() + i.getInfo() + '\n';

        return ret;
    }
    
    /** Function to add positional argument.
     *  We MUST input them in command promt then executing.
     *  @param info String information of current positional argument
     *  @param name String name of this argument in help insructions
     *  @param holder Holder for resource
     */
    public void addPositional(String info, String argName, Holder holder)
    {
        this.positionalArguments.add(new Argument("", info, argName, holder));
    }

    /** Function to add positional argument.
     *  We MUST input them in command promt then executing.
     *  @param info String information of current positional argument
     *  @param holder Holder for resource
     */
    public void addPositional(String info, Holder holder)
    {
        this.positionalArguments.add(new Argument("", info, "param", holder));
    }
    
    /**
     * The function to add the flag argument; can be required or not
     * @param str The format string for argument
     * @param argName The name of argument to be printed in help
     * @param holder The Holder of resource we need to parse
     * @param isRequired The flag which deteminates the type of argument
     * @throws Exception Can be thrown if error in format string or mismatch the holder with format string
     */
    public void addArgument(String str, String argName, Holder holder, boolean isRequired) throws Exception
    {
        /** If the isRequired true - add the required argument; otherwise add non-required argument.
         *  Notice, that there we adding the argument with special name for help string.
         */
        if(isRequired == true)
            this.addRequieredArgument(str, argName, holder);
        else
            this.addNonRequieredArgument(str, argName, holder);
    }

    /**
     * The function to add the flag argument; can be required or not
     * @param str The format string for argument
     * @param holder The Holder of resource we need to parse
     * @param isRequired The flag which deteminates the type of argument
     * @throws Exception Can be thrown if error in format string or mismatch the holder with format string
     */
    public void addArgument(String str, Holder holder, boolean isRequired) throws Exception
    {
        /** If the isRequired true - add the required argument; otherwise add non-required argument.
         *  Notice, that there we adding the argument without special name for help string.
         */
        if(isRequired == true)
            this.addRequieredArgument(str, "param", holder);
        else
            this.addNonRequieredArgument(str, "param", holder);
    }

    /** Private function to make addition funcs simple.
     *  Private method to add the required argument with given parameters.
     * @param str The format string
     * @param argName The name of argument in help
     * @param holder The holder for resource
     * @throws Exception Can be thrown if error in format string or mismatch the holder with format string
     */
    private void addRequieredArgument(String str, String argName, Holder holder) throws Exception
    {
        /** Check the correctness of format string */
        isInputCorrect(str, holder);
        /** Find the first space, extract the command, then find the second space;
         *  Rest of string from the second space will be info string.
         */
        int firstSpace = str.indexOf(" ");
        String command = str.substring(0, firstSpace);
        String info = str.substring(str.indexOf(" ", firstSpace + 1));
        /** Attempt to make a new argument */
        Argument arg = new Argument(command, info, argName, holder);
        /** Attempt to add the required argument; if something happen - throw the exception */
        if(this.requiredArguments.add(arg) == false)
            throw new Exception("attempt to input equal arguments");
    }

    /** Private function to make addition funcs simple.
     *  Private method to add the non-required argument with given parameters.
     * @param str The format string
     * @param argName The name of argument in help
     * @param holder The holder for resource
     * @throws Exception Can be thrown if error in format string or mismatch the holder with format string
     */
    private void addNonRequieredArgument(String str, String argName, Holder holder) throws Exception
    {
        /** Check the correctness of format string */
        isInputCorrect(str, holder);
        /** Find the first space, extract the command, then find the second space;
         *  Rest of string from the second space will be info string.
         */
        int firstSpace = str.indexOf(" ");
        String command = str.substring(0, firstSpace);
        String info = str.substring(str.indexOf(" ", firstSpace + 1));
        /** Attempt to make a new argument */
        Argument arg = new Argument(command, info, argName, holder);
        /** Attempt to add the required argument; if something happen - throw the exception */
        if(this.nonRequiredArguments.add(arg) == false)
            throw new Exception("attempt to input equal arguments");
    }

    /**
     * Private method for check if we enter correct string and type of holder.
     * @param str The format string to check
     * @param holder Holder for resource; need to compare the inputted type and holder type
     * @throws Exception Can be thrown if error in format string or mismatch the holder with selected type
     */
    private void isInputCorrect(String str, Holder holder) throws Exception
    {
        /** Find the first pos of percent */
        int percentPos = str.indexOf("%");
        /** Find the second pos of percent; if found - then there's an error */
        int secondPercentPos = str.indexOf("%", percentPos + 1);
        /** Handle errors */
        if(secondPercentPos != -1) throw new Exception("error in formatting; only one variable acceptable");
        if(percentPos == -1) throw new Exception("not found variable");
        /** Take the format char and check for correctness of inputted type and given resource */
        char ch = str.charAt(percentPos + 1);
        if(ch == 'd' && !(holder instanceof IntHolder))
            throw new Exception("wrong positional argument; IntHolder expected");
        else if(ch == 's' && !(holder instanceof StringHolder))
            throw new Exception("wrong positional argument; StringHolder expected");
        else if(ch == 'f' && !(holder instanceof FloatHolder))
            throw new Exception("wrong positional argument; FloatHolder expected");
        else if(ch == 'l' && !(holder instanceof LongHolder))
            throw new Exception("wrong positional argument; LongHolder expected");
    }   
}