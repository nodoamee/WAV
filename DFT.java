package sample;

/**
 * Created by abedaigorou on 16/02/25.
 */
import static java.lang.StrictMath.*;

public class DFT
{
    private int m=0;
    private Short[] F;
    public final static int f=400;
    public DFT(int m, Short[] F)
    {
        this.m=m;
        this.F=F;
    }

    public double an(int n)
    {
        double an=0;
        for(int k=0;k<m;k++) {
            if(k<F.length)
                an += F[k] * cos(n * (2 * PI * k / m));
            //System.out.println("F[k]"+F[k].shortValue());
        }
            System.out.println("an(" + n+ ")" + an / m);

        return (an/m);
    }

    public double bn(int n)
    {
        double bn=0;
        for(int k=0;k<m;k++) {
            if(k<F.length)
                bn += -F[k]* sin(n * (2 * PI * k / m));
        }
            System.out.println("bn(" + n + ")" + bn / m);

        return (bn/m);
    }
}
