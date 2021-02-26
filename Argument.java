public class Argument 
{
    /** The info about argument */
    private String infoString;
    /** The command of this Argument */
    private String argShortcut;
    /** The name of this argument in help message */
    private String argName;
    /** The holder for resource we parse with this argument */
    private Holder holder;

    /**
     * Constructor for Argument
     * @param argShortcut The specified command
     * @param infoString Info string in help message
     * @param argName The name of arg in help message
     * @param holder The holder of resource
     */
    Argument(String argShortcut, String infoString, String argName,  Holder holder)
    {
        this.argName = argName;
        this.infoString = infoString;
        this.argShortcut = argShortcut;
        this.holder = holder;
    }
    
    /**
     * The simple wrapper over holder
     * @param str
     */
    public void setResource(String str) { this.holder.setResource(str); }

    /**
     * Get the argument name
     * @return String of argument name
     */
    public String getArgName() { return this.argName; }
    /**
     * Get the info about argument
     * @return String of argument info
     */
    public String getInfo() { return this.infoString; }
    /**
     * Get the argument shortcut
     * @return String of shortcut
     */
    public String getShortcut() { return this.argShortcut; }

    /**
     * The method for check is the given shortcut is actual Argument command
     * @param arg The given shortcut
     * @return boolean is the given command is Argument
     */
    public boolean isArgument(String arg)
    {
        return this.argShortcut.equals(arg);
    }

    /** 
     * Method for equality in hashMap
     * @param obj to be compared with
     * @return boolean is obj equals to this by shortcut
     */
    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        else if(!(obj instanceof Argument)) return false;
        Argument other = (Argument)obj;
        return this.argShortcut.equals(other.argShortcut);
    }

    /** 
     * The hashCode; makes out of shortcut only
     * @return int hashcode
     */
    public int hashCode() { return this.argShortcut.hashCode(); }
}