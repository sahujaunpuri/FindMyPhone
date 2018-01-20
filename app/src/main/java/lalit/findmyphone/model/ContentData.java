package lalit.findmyphone.model;

/**
 * Created by Lalit on 1/20/2018.
 */

public class ContentData {
    private Result[] result;

    public Result[] getResult ()
    {
        return result;
    }

    public void setResult (Result[] result)
    {
        this.result = result;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [result = "+result+"]";
    }
}
