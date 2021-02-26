import ArgumentParser.*;

class Test
{
    /** Usage simple as that:
     *  1. Create variables holder
     *  2. Init argparse; you can specify the name of current app to show on help
     *  3. Add arguments; positional args not include command, only info and the name
     *     Requiread or not args include the flag, info and name; last can not be specified;
     *     The flag+info string is must be made like this: "-<command> <type> [info]";
     *     Type can be %s for string, %d for int, %f for float, %l for long.
     *     If you need more, add them with yourself! Use Holder to make new type.
     *  4. Run <ArgParse>.parse(args) and then you take the args from maded holders.
     *  5. PROFIT!
     */
    public static void main(String[] args) 
    {
        IntHolder holder1 = new IntHolder();
        StringHolder holder2 = new StringHolder();
        IntHolder holder3 = new IntHolder();
        
        ArgumentParser argParse = new ArgumentParser("App");
        try 
        {
            argParse.addArgument("-if %d int req var", holder1, true);
            argParse.addArgument("-sf %s str req var", holder2, true);

            argParse.addArgument("-i %d int nonreq var", holder3, false);

            argParse.parse(args);
        } 
        catch (Exception e) 
        {
            System.out.println(e);
            System.out.println();
            System.out.println(argParse);
        }
    }
}