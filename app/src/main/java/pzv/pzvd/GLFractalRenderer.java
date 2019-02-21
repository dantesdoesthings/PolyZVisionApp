package pzv.pzvd;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.*;
import android.opengl.Matrix;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import pzv_math.*;

public class GLFractalRenderer implements GLSurfaceView.Renderer {	
	private static final String TAG = "FImg";
    //private Triangle mTriangle;
    //private Square   mSquare;
    private Fractal mFractal;
    private float mX;
    private float mY; 

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    //private final float[] mRotationMatrix = new float[16];
	private final float[] mProjectionMatrix = new float[16];
	private final float[] scaleMatrix = new float[16];
	private final float[] mViewMatrix = new float[16];

    private float mAngle;
	public void onSurfaceCreated(GL10 unused, EGLConfig config) {
	        // Set the background frame color
	        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	        // initialize a triangle
	        // mTriangle = new Triangle();
	        // initialize a square
	        // mSquare = new Square();
	         mFractal = new Fractal();
	}
	public void finishSetup() {
		mFractal = new Fractal();
	}
	public void onDrawFrame(GL10 unused) {
		//if(mFractal==null)
		//	return;
		float[] scratch = new float[16];
		// Draw background color
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		Matrix.setIdentityM(mMVPMatrix, 0);
		// Set the camera position (View matrix)
		Matrix.setIdentityM(mViewMatrix, 0);
//		Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
		// shift
		float tr[] = WorldInfo.translation();
		for(int i=0;i<16;i++)
			mViewMatrix[i]=tr[i];
		tr = WorldInfo.scale();
		for(int i=0;i<16;i++)
			scaleMatrix[i]=tr[i];
		// Calculate the projection and view transformation
		//_modelViewMatrix = GLKMatrix4Multiply(_scaleMatrix, _modelViewMatrix);
		Matrix.multiplyMM(mViewMatrix,0,scaleMatrix,0,mViewMatrix,0);
		Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
		// Draw square
		// mSquare.draw(mMVPMatrix);
		mFractal.draw(mMVPMatrix);

		// Create a rotation for the triangle
		// Use the following code to generate constant rotation.
		// Leave this code out when using TouchEvents.
		// long time = SystemClock.uptimeMillis() % 4000L;
		// float angle = 0.090f * ((int) time);
		// Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, 1.0f);
		// Combine the rotation matrix with the projection and camera view
		// Note that the mMVPMatrix factor *must be first* in order
		// for the matrix multiplication product to be correct.
		// Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);
		// Draw triangle
		// mTriangle.draw(scratch);
	}

	public void onSurfaceChanged(GL10 unused, int width, int height) {
		// Adjust the viewport based on geometry changes,
		// such as screen rotation
		GLES20.glViewport(0, 0, width, height);
		WorldOLD.width = width;
		WorldOLD.height = height;
		WorldInfo.setPsX(width);
		WorldInfo.setPsY(height);
		WorldInfo.setgRatio((float)height/width);
		WorldInfo.doVertices();

		float ratio = (float) width / height;
		WorldOLD.xyratio = ratio;
		//World.yMin = -1.0f/World.xyratio;
		//World.yMax = 1.0f/World.xyratio;

		// this projection matrix is applied to object coordinates
		// in the onDrawFrame() method
		//Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
		//Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 1, 3);
		Matrix.setIdentityM(mProjectionMatrix, 0);
//		Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
	}
	/**
	 * Utility method for compiling a OpenGL shader.
	 *
	 * <p><strong>Note:</strong> When developing shaders, use the checkGlError()
	 * method to debug shader coding errors.</p>
	 *
	 * @param type - Vertex or fragment shader type.
	 * @param shaderCode - String containing the shader code.
	 * @return - Returns an id for the shader.
	 */
	public static int loadShader(int type, String shaderCode){

		// create a vertex shader type (GLES20.GL_VERTEX_SHADER)
		// or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
		int shader = GLES20.glCreateShader(type);

		// add the source code to the shader and compile it
		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);
		checkGlError("glCompileShader");

		return shader;
	}
	/**
	 * Utility method for debugging OpenGL calls. Provide the name of the call
	 * just after making it:
	 *
	 * <pre>
	 * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
	 * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
	 *
	 * If the operation is not successful, the check throws an error.
	 *
	 * @param glOperation - Name of the OpenGL call to check.
	 */
	 public static void checkGlError(String glOperation) {
		 int error;
		 while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
			 Log.e(TAG, glOperation + ": glError " + error);
			 throw new RuntimeException(glOperation + ": glError " + error);
		 }
	 }
	public void setClick(float iX, float iY) {
		mX = iX; mY = iY;
		if(mFractal!=null)
			mFractal.setClick(mX, mY);
	}
	public void setRatio(float r) {
		mFractal.setRatio(r);
	}
//	    public boolean onTouch(View view, MotionEvent event) {
//	    	Log.d("Tag:", "getting something "+9.3);
//	    	return false;
//	    }
	    
}
