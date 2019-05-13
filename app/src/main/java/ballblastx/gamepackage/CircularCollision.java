package ballblastx.gamepackage;

public class CircularCollision
{
    public float X, Y, R;

    public CircularCollision(float x, float y, float r)
    {
        X = x;
        Y = y;
        R = r;
    }

    public CircularCollision(String s)
    {
        String[] values = s.split(",");

        X = Float.parseFloat(values[0]);
        Y = Float.parseFloat(values[1]);
        R = Float.parseFloat(values[2]);
    }
}
