package pzv_math;

//import android.view.MotionEvent;
import android.graphics.PointF;
import android.opengl.Matrix;
import android.util.Log;

/**
 * Created by Palladin on 10/15/2015.
 */
public class WorldInfo {
    static final float defaultZoom = 1.0f;
    static final float defaultStep = 0.3f;
    static final float defaultProx2 = 0.08f*0.08f;

    static int s_pictureSizeX=320;
    static int s_pictureSizeY=320;
    static float s_gRatio=1.0f;
    static float ggVertices[] = {1.0f,1.0f, -1.0f,1.0f, 1.0f,-1.0f, -1.0f,-1.0f};

    static float s_gZoom=defaultZoom;
    static float ggCenterX=0.0f;
    static float ggCenterY=0.0f;
    static float s_xIntercept=-1.0f;
    static float s_yIntercept=1.0f;
    static float s_xSlope;
    static float s_ySlope;
//    static GLKMatrix4 s_pvMatrix;
    static float s_gStep = defaultStep;
    static float s_prox2=defaultProx2;
    static World wd;
    static World oldWd;

    public static void initGlobalWorld() {
        wd = new World();
        oldWd = new World();
    }
    public static float[] vertices() {
        return ggVertices;
    }
    public static int psX() {
        return s_pictureSizeX;
    }
    public static int psY() {
        return s_pictureSizeY;
    }
    public static void setPsY(int input) {
        s_pictureSizeY = input;
    }
    public static void setPsX(int input) {
        s_pictureSizeX = input;
    }
    public static float gRatio() {
        return s_gRatio;
    }

    public static void doVertices() {
        float semiWidth =1.0f*s_gZoom;
        float semiHeight =1.0f*s_gRatio*s_gZoom;
        ggVertices[0]=ggCenterX+semiWidth;
        ggVertices[1]=ggCenterY+semiHeight;
        ggVertices[2]=ggCenterX-semiWidth;
        ggVertices[3]=ggCenterY+semiHeight;
        ggVertices[4]=ggCenterX+semiWidth;
        ggVertices[5]=ggCenterY-semiHeight;
        ggVertices[6]=ggCenterX-semiWidth;
        ggVertices[7]=ggCenterY-semiHeight;
        s_xIntercept = ggVertices[2];  // xmin
        s_yIntercept = ggVertices[1]; // ymax
        s_xSlope = (2.0f*semiWidth)/s_pictureSizeX;
        s_ySlope = -2.0f*semiHeight/s_pictureSizeY;
    }

    public static void setgRatio(float val) {
        s_gRatio = val;
    }
//    + (GLKMatrix4) translation {
//        return GLKMatrix4MakeTranslation(-ggCenterX, -ggCenterY, 0.0);
//    }

//    + (GLKMatrix4) scale {
//        return GLKMatrix4MakeScale(1.0/s_gZoom, 1.0/(s_gRatio*s_gZoom), 1.0f);
//    }
    public static float[] translation() {
        float[] t={
                1.0f,0.0f,0.0f,0.0f,
                0.0f,1.0f,0.0f,0.0f,
                0.0f,0.0f,1.0f,0.0f,
                0.0f,0.0f,0.0f,1.0f};
        Matrix.translateM(t,0,-ggCenterX,-ggCenterY,0.0f);
        return t;
    }
    public static float[] scale() {
        float[] t={
                1.0f,0.0f,0.0f,0.0f,
                0.0f,1.0f,0.0f,0.0f,
                0.0f,0.0f,1.0f,0.0f,
                0.0f,0.0f,0.0f,1.0f};
        Matrix.scaleM(t,0,1.0f/s_gZoom,1.0f/(s_gRatio*s_gZoom),1.0f);
        return t;
    }
    public static void setFullScreenRatio() {
        s_gRatio = (float)s_pictureSizeY/(float)s_pictureSizeX;
        WorldInfo.doVertices();
    }
    public static void setNormalRatio() {
        s_gRatio = 1.0f;
        WorldInfo.doVertices();
    }
//    + (GLKMatrix4) pvMatrix {
//        return s_pvMatrix;
//    }
//    public static void setpvMatrix:(GLKMatrix4)input {
//        s_pvMatrix = input;
//    }

    public static void moveLeft() {ggCenterX -= s_gStep; WorldInfo.doVertices();}
    public static void moveRight() {ggCenterX += s_gStep;WorldInfo.doVertices();}
    public static void moveUp() {ggCenterY += s_gStep;WorldInfo.doVertices();}
    public static void moveDown() {ggCenterY -= s_gStep;WorldInfo.doVertices();}
    public static void zoomIn() {
        float s_gRezoom =World.reZoom();
        s_gZoom /= s_gRezoom;
        s_gStep /= s_gRezoom;
        s_prox2 /= s_gRezoom*s_gRezoom;
        WorldInfo.doVertices();
    }
    public static void zoomOut() {
        float s_gRezoom = World.reZoom();
        s_gZoom *= s_gRezoom;
        s_gStep *= s_gRezoom;
        s_prox2 *= s_gRezoom*s_gRezoom;
        WorldInfo.doVertices();
    }
    public static float getMathX(float xin) {
        return s_xIntercept+s_xSlope*xin;
    }
    public static float getMathY(float yin)
    {
        return s_yIntercept+s_ySlope*yin;
    }
    public static void zoomDefault() {
        s_gZoom = 1.0f;
        s_gStep = 0.2f;
        ggCenterX=0.0f;
        ggCenterY=0.0f;
        s_prox2 = 0.08f*0.08f;
        WorldInfo.doVertices();
    }
    public static boolean isClose(float dist2) {
        if(dist2<=s_prox2)
            return true;
        else
            return false;
    }
    public static float norm2(PointF pnt, float x, float y) {
        float dx =(pnt.x-x);
        float dy =(pnt.y-y);
        return dx*dx+dy*dy;
    }
    public static float norm(PointF p) {
        return (float)Math.sqrt(p.x*p.x+p.y*p.y);
    }

    public static float dist(PointF pn1, PointF pn2) {
        float dx =(pn1.x-pn2.x);
        float dy =(pn1.y-pn2.y);
        return (float)Math.sqrt(dx*dx+dy*dy);
    }
    public static void memorize() {
        if(oldWd==null) {
            Log.d("WorldInfo"," oldWd is null");
            return;}
        oldWd.setRatio(s_gRatio);
        oldWd.setZoom(s_gZoom);
        oldWd.setCX(ggCenterX);
        oldWd.setCY(ggCenterY);
        oldWd.setStep(s_gStep);
        oldWd.setProx2(s_prox2);
        oldWd.setVertices(ggVertices);
    }
    public static void restore() {
        if(oldWd==null)
            return;
        WorldInfo.restoreWorld();
    }
    public static void setDefault() {
//        s_gRatio=1.0f;
//        ggVertices[0] = 1.0f;
//        ggVertices[1]=1.0f;
//        ggVertices[2]=-1.0f;
//        ggVertices[3]=1.0f;
//        ggVertices[4]=1.0f;
//        ggVertices[5]=-1.0f;
//        ggVertices[6]=-1.0f;
//        ggVertices[0]=-1.0f;

        s_gZoom=defaultZoom;
        ggCenterX=0.0f;
        ggCenterY=0.0f;
        s_gStep = defaultStep;
        s_prox2=defaultProx2;
        WorldInfo.doVertices();
    }
    public static void restoreWorld() {
        //s_gRatio=oldWd.getRatio();
        float tv[] = oldWd.getVertices();
        for(int i=0;i<8;i++)
            ggVertices[i] = tv[i];

        s_gZoom=oldWd.getZoom();
        ggCenterX=oldWd.getCX();
        ggCenterY=oldWd.getCY();
        s_gStep = oldWd.getStep();
        s_prox2 = oldWd.getProx2();
        WorldInfo.doVertices();
    }
    public static void centerAt(PointF p) {
        ggCenterX = p.x;ggCenterY=p.y;
        doVertices();
    }
    /*public static void rescale (CGPoint p1, CGPoint p2, CGPoint p1hat, CGPoint p2hat) {
        // now, the big assumption should be that all 4 points are colinear.
        // which points we should trust 100%? Let's trust the first two points 100%
        // the other two points will simply provide the magnitude of translation ??
        float x1 = p1.x; float x2 = p2.x; float y1 =p1.y; float y2 = p2.y;
        // let's find the line equation connecting p1 with p2
//    float slope = (y2-y1)/(x2-x1);
//    float intercept=y1-slope*x1;
        // we have to adjust the other two points now
        float t1 = [self dist:p1 :p1hat];
        float t2 = [self dist:p2 :p2hat];
        float shift = (t1+t2)/2.0;
        CGPoint dir = CGPointMake(x2-x1,y2-y1);
        float size = [self norm:dir];
        CGPoint uVec = CGPointMake(dir.x/size,dir.y/size);
        // smaller or bigger?
        float d1 = [self dist: p1 :p2];
        float d2 = [self dist: p1hat :p2hat];
        bool shrunk=true;
        if(d2>d1)
            shrunk=false;
        if(shrunk) {
            p1hat = CGPointMake(p1.x+uVec.x*shift,p1.y+uVec.y*shift);
            p2hat = CGPointMake(p2.x-uVec.x*shift,p2.y-uVec.y*shift);
        }
        else {
            p1hat = CGPointMake(p1.x-uVec.x*shift,p1.y-uVec.y*shift);
            p2hat = CGPointMake(p2.x+uVec.x*shift,p2.y+uVec.y*shift);
        }

        float x1hat = p1hat.x; float x2hat = p2hat.x; float y1hat =p1hat.y; float y2hat = p2hat.y;
        // what is the linear transform ?
        float sOld = s_xIntercept; float mOld = s_xSlope;
        float mNew;
        float interceptNew;
        float s_gZoomX;
        float s_gZoomY;
        if(x2!=x1) {
            mNew = mOld*(x2-x1)/(x2hat-x1hat);
            interceptNew = sOld+mOld*x1-mNew*x1hat;
            s_xSlope = mNew; s_xIntercept = interceptNew;
            s_gZoomX = s_pictureSizeX*s_xSlope/2.0;
            s_gZoom = s_gZoomX;
        }
        else {
            sOld = s_yIntercept; mOld = s_ySlope;
            mNew = mOld*(y2-y1)/(y2hat-y1hat);
            interceptNew = sOld+mOld*y1-mNew*y1hat;
            s_ySlope = mNew; s_yIntercept = interceptNew;
            s_gZoomY = -s_pictureSizeY*s_ySlope/(2.0*s_gRatio);// now there are two potentially conflicting zooms ...
            s_gZoom = s_gZoomY;
        }
//    s_gZoom = (s_gZoomX+s_gZoomY)/2.0;
//    s_xIntercept = ggVertices[2];  // xmin
//    s_yIntercept = ggVertices[1]; // ymax
        ggVertices[2]=s_xIntercept;  // xmin
        ggVertices[1]=s_yIntercept; // ymax
        ggCenterY = ggVertices[1]-s_gRatio*s_gZoom;
        ggCenterX = ggVertices[2]+s_gZoom;
        [self doVertices];
    }*/
    public static void zoom (float factor) {
        s_gZoom /= factor;
        s_gStep /= factor;
        s_prox2 /= factor*factor;
        WorldInfo.doVertices();
    }
}
