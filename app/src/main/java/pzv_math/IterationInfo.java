package pzv_math;

/**
 * Created by Palladin on 10/15/2015.
 */
public class IterationInfo {
    private static float defaultEps = 0.01f;
    //private static float defaultEps = 0.173f;
    //private static float defaultEps2 = 0.1f;
    private static float defaultEps2 = 0.1f;
    private static int defaultNIt = 10;
    private static float sEps=defaultEps;
    private static float sEps2=defaultEps2;
    private static int snIterations=defaultNIt;
    private static float mEps;
    private static float mEps2;
    private static int mnIt;
    private static boolean sShowRoots = true;

    public static float eps() {
        return sEps;
    }
    public static float eps2() {
        return sEps2;
    }
    public static int nIterations() {
        return snIterations;
    }
    public static boolean showRoots() {
        return sShowRoots;
    }
    public static void setShowRoots(boolean input) {
        sShowRoots = input;
    }
    public static void setIterations(int input) {
        snIterations = input;
    }
    public static void setEps(float input) {
        sEps = input;
    }
    public static void setEps2(float input) {
        sEps2 = input;
    }
    public static void incIt() {
        if(snIterations>=40)
            return;
        snIterations++;
    }
    public static void decIt() {
        if(snIterations<=0)
            return;
        snIterations--;
    }
    public static void defaultEps() {
        sEps=defaultEps;
    }
    public static void defaultEps2() {
        sEps2=defaultEps2;
    }
    public static void defaultNIt() {
        snIterations = defaultNIt;
    }
    public static void memEps() {
        mEps = sEps;
    }
    public static void memEps2() {
        mEps2 = sEps2;
    }
    public static void memNIt() {
        mnIt = snIterations;
    }
    public static void recallEps() {
        sEps = mEps;
    }
    public static void recallEps2() {
        sEps2 = mEps2;
    }
    public static void recallNIt() {
        snIterations = mnIt;
    }
}
