package pzv.pzvd;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.GestureDetector;
//import android.gesture;
import android.view.MotionEvent;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;

import pzv_math.PolyInfo;
import pzv_math.WorldInfo;

/**
 * Created by Palladin on 10/2/2015.
 */
public class  FractalStage extends GLSurfaceView {
    //private faGLRenderer rend;
    private GLFractalRenderer rend;
    private static final String DEBUG_TAG = "MyDebug";
    //
//    GestureDetector gestureDetector;
    public FractalStage(Context context) {
        super(context);
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        rend = new GLFractalRenderer();
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(rend);
        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
//        gestureDetector = new GestureDetector(context, new GestureListener());
    }
    public FractalStage(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        rend = new GLFractalRenderer();
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(rend);
        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
//        gestureDetector = new GestureDetector(context, new GestureListener());
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return gestureDetector.onTouchEvent(e);
        int action = MotionEventCompat.getActionMasked(event);
        float px;
        float mx; // math x
        float my; // math y
        float py;
        switch(action) {
            case (MotionEvent.ACTION_DOWN):
//                Log.d(DEBUG_TAG, "Action was DOWN");
                px = event.getX();
                py = event.getY();
                mx = WorldInfo.getMathX(px);
                my = WorldInfo.getMathY(py);
//                renderer.setClick(px, py);
//                Log.d(DEBUG_TAG, Float.toString(mx)+ " " + Float.toString(my));
                PolyInfo.setRoot(0,mx,my);
                invalidate();
                this.requestRender();
                return true;
            case (MotionEvent.ACTION_MOVE) :
//                Log.d(DEBUG_TAG,"Action was MOVE");
                px = event.getX();
                py = event.getY();
                mx = WorldInfo.getMathX(px);
                my = WorldInfo.getMathY(py);
//                renderer.setClick(px, py);
//                Log.d(DEBUG_TAG, Float.toString(mx)+ " " + Float.toString(my));
                PolyInfo.setRoot(0,mx,my);
                invalidate();
                this.requestRender();
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }
}
