package pzv_math;

/**
 * Created by Palladin on 10/15/2015.
 */
import android.graphics.PointF;
public class PolyInfo {
    //#import "WorldInfo.h"
    static final float xs_p = 0.5f;
    static final float ys_p = -0.5f;
    static float s_sum_x=0.0f;
    static float s_sum_y=0.0f;
    static float s_roots[]={
            0.0f,0.366025f,-xs_p,ys_p,xs_p,ys_p,
            -0.8f,0.9f,1.2f,1.4f,//12
            -0.5f,-0.5f,0.0f,0.0f,0.8f,0.4f,-0.8f,0.4f,1.2f,3.4f,-1.1f,7.5f,//12
            0.5f,0.5f,0.0f,0.0f,0.8f,0.4f,-0.8f,0.4f,1.2f,3.4f,-1.1f,7.5f//12
    };
    final static float default_roots[]={
            0.0f,0.366025f,-xs_p,ys_p,xs_p,ys_p,
            -0.8f,0.9f,1.2f,1.4f,//12
            -0.5f,-0.5f,0.0f,0.0f,0.8f,0.4f,-0.8f,0.4f,1.2f,3.4f,-1.1f,7.5f,//12
            0.5f,0.5f,0.0f,0.0f,0.8f,0.4f,-0.8f,0.4f,1.2f,3.4f,-1.1f,7.5f//12
    };
    final static int default_rootCount = 3;
    static int s_rootCount=default_rootCount;

    public static int rootCount() {
        return s_rootCount;
    }
    public static void setRootCount(int val) {
        s_rootCount = val;
    }
    public static float[] roots() {
        return s_roots;
    }
    public static float getR(int input) {
        return s_roots[input];
    }
    public static float distSqRoot(int root, PointF pnt) {
        return WorldInfo.norm2(pnt, s_roots[2*root], s_roots[2*root+1]);
    }
    public static void setRoot (int root, float x, float y) {
        s_roots[2*root] = x;
        s_roots[2*root+1] = y;
    }
    public static void addRoot(float x, float y) {
        s_roots[2*s_rootCount] = x;
        s_roots[2*s_rootCount+1] = y;
        s_rootCount++;
    }
    public static void forgetRoot() {
        if(s_rootCount<=0)
            return;
        s_rootCount--;
    }
    public static void setDefault() {
        s_rootCount = default_rootCount;
        for(int i=0;i<s_roots.length;i++)
            s_roots[i]=default_roots[i];
    }
    public static float getSumX() {
        s_sum_x=0.0f;
        for(int i=0;i<s_rootCount;i++) {
            s_sum_x += s_roots[2*i ];
        }
        return s_sum_x;
    }
    public static float getSumY() {
        s_sum_y=0.0f;
        for(int i=0;i<s_rootCount;i++) {
            s_sum_y += s_roots[2*i+1];
        }
        return s_sum_y;
    }
}
