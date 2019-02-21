package pzv_math;

/**
 * Created by Palladin on 10/15/2015.
 */
public class MethodInfo {
    static int s_showRoots;

    static final float defaultR = 1.1f;
    static final float defaultArg = 0.3f;
    static int s_norm=2;
    static int s_postOption=0;
    static int s_method=0;  // Newton's method
    static float alphaX=1.0509f;
    static float alphaY=0.3251f;
    static int s_useAlpha=0;
    static float s_rd = defaultR;
    static float s_angle = defaultArg;
    static float mem_rd = defaultR;
    static float mem_angle = defaultArg;
    public static int method() {
        return s_method;
    }
    public static int norm() {
        return s_norm;
    }
    public static int option() {
        return s_postOption;
    }
    public static void setMethod(int input) {
        s_method = input;
    }
    public static void setNorm(int input) {
        s_norm = input;
    }
    public static void setOption(int input) {
        s_postOption = input;
    }
    public static void useAlpha(int input) {
        s_useAlpha = input;
    }
    private static float sqrt(float x) {
        return (float)Math.sqrt(x);
    }
    private static float sin(float x) {
        return (float)Math.sin(x);
    }
    private static float cos(float x) {
        return (float)Math.cos(x);
    }
    private static float atan2(float y,float x) {
        return (float)Math.atan2(y,x);
    }
    public static void setAlpha(float xin, float yin) {
        s_rd = sqrt(xin * xin + yin * yin);
        s_angle = atan2(yin, xin);
        alphaX = xin; alphaY = yin;
    }
    public static int alpha() {
        return s_useAlpha;
    }
    public static void setAlphaPolar(float r , float angle) {
        //{cAlpha = cexp(argAlpha*I)*rAlpha;}
        s_rd = r; s_angle = angle;
        alphaX = r*cos(angle);
        alphaY = r*sin(angle);
    }
    public static void setAlphaAngle(float input) {
        s_angle = input;
        alphaX = s_rd*cos(s_angle);
        alphaY = s_rd*sin(s_angle);
    }
    public static void setAlphaRd(float input) {
        s_rd = input;
        alphaX = s_rd*cos(s_angle);
        alphaY = s_rd*sin(s_angle);
    }
    public static float getAlphaX() {
        return alphaX;
    }
    public static float getAlphaY() {
        return alphaY;
    }
    public static void defaultAngle() {
        s_angle = defaultArg;
        alphaX = s_rd*cos(s_angle);
        alphaY = s_rd*sin(s_angle);
    }
    public static void defaultRd() {
        s_rd = defaultR;
        alphaX = s_rd*cos(s_angle);
        alphaY = s_rd*sin(s_angle);
    }
    public static float getAlphaAngle() {
        return s_angle;
    }
    public static float getAlphaR() {
        return s_rd;
    }
    public static void memAngle() {
        mem_angle = s_angle;
    }
    public static void memRd() {
        mem_rd = s_rd;
    }
    public static void restoreAngle() {
        s_angle = mem_angle;
        alphaX = s_rd*cos(s_angle);
        alphaY = s_rd*sin(s_angle);
    }
    public static void restoreRd() {
        s_rd = mem_rd;
        alphaX = s_rd*cos(s_angle);
        alphaY = s_rd*sin(s_angle);
    }
}
