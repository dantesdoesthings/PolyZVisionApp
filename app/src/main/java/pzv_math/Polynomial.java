package pzv_math;

public class Polynomial {
	public static float roots[];
    public static int rootCount=3;
    public Polynomial() {
    	roots = new float[24];
    	roots[0] = 0.0f; roots[1] = 0.0f; roots[2] = 1.0f; roots[3] = 0.0f;
        roots[4] = -1.0f; roots[5] = 0.0f;
    }
}
