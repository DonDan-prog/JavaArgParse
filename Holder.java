public abstract class Holder<T>
{
    /** Field of abstract class in Holder */
    protected T field = null;
    
    /**
     * The getter method for Holder
     * @return T the type we specified
     */
    public T getResource() { return this.field; }
    /**
     * The method for setting the field paramater via string
     * @param str The new value for this field
     */
    public abstract void setResource(String str);
}