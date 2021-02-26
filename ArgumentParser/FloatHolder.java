package ArgumentParser;

public class FloatHolder extends Holder<Float> 
{
    public void setResource(String str)
    {
        this.field = Float.parseFloat(str);
    }
}