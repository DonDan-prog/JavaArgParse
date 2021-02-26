public class LongHolder extends Holder<Long>
{
    public void setResource(String str)
    {
        this.field = Long.parseLong(str);
    }
}