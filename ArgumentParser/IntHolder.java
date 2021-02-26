package ArgumentParser;

public class IntHolder extends Holder<Integer>
{
    public void setResource(String str)
    {
        this.field = Integer.parseInt(str);
    }
}