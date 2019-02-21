package pzv.pzvd;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import android.util.Log;
import pzv_math.*;

import android.opengl.GLES20;

public class Fractal {
	private float mX=120.0f;
    private float mY=150.0f;
    private static final String TAG = "FImg";
    //var vertices;
    //var f32vertices;        
    
	private final FloatBuffer vertexBuffer;
    private final ShortBuffer drawListBuffer;
    private final ShortBuffer smallListBuffer;
    final FloatBuffer rootsBuffer;
    float[] rs =  {0.0f,0.7f,-0.5f,0.5f,0.5f,0.5f};
    private final int mProgram;
    private final int simpleProgram;
    private int mPositionHandle;
    private int simplePositionHandle;
//    private int mColorHandle;
    private int mMVPMatrixHandle;
    private int simpleMVPMatrixHandle;
    private int backgroundColorHandle;
    private int failureColorHandle;
    private int summHandle;
//    private int mouseHandle;
    private int epsHandle;
    private int eps2Handle;
    private int maxItHandle;
    private int rootCountHandle;
    private int rootsHandle;
    private int alphaHandle;
    private int useAlphaHandle;
    private int colorsHandle;
    private int normToUseHandle;
    private int methodHandle;
    private int postOptionHandle;
    public static float ratio=1.0f;
    
    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 2;
    static float s=1.0f;
    static float squareCoords[] = {
            -s,  s,   // top left
            -s, -s, // bottom left
             s, -s, // bottom right
             s,  s }; // top right
    static float squareIOS[] = {
            s,  s,   // top left
            -s, s, // bottom left
            -s, -s, // bottom right
            s,  -s }; // top right

    private final short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices
//    private final short drawOrder[] = { 1, 2, 3, 1, 3, 0 }; // order to draw vertices
    private final short smallOrder[] = { 0, 1, 2  }; // order to draw vertices
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
    float color[] = { 0.2f, 0.709803922f, 0.898039216f, 1.0f };
//    Polynomial p;


    public Fractal() {
//    	p = new Polynomial();
    	// vertices
    	squareCoords[0] = -s;
    	squareCoords[1] = s*ratio;
//    	squareCoords[2] = 0.0f;
    	squareCoords[2] = -s;
    	squareCoords[3] = -s*ratio;
//    	squareCoords[5] = 0.0f;
    	squareCoords[4] = s;
    	squareCoords[5] = -s*ratio;
//    	squareCoords[8] = 0.0f;
    	squareCoords[6] = s;
    	squareCoords[7] = s*ratio;
//    	squareCoords[11] = 0.0f;

    	// initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
        // (# of coordinate values * 4 bytes per float)
                squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        //vertexBuffer.put(squareCoords);
        vertexBuffer.put(squareIOS);
        vertexBuffer.position(0);

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        ByteBuffer slb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                smallOrder.length * 2);
        slb.order(ByteOrder.nativeOrder());
        smallListBuffer = slb.asShortBuffer();
        smallListBuffer.put(smallOrder);
        smallListBuffer.position(0);

        // buffer for roots
        ByteBuffer bb2 = ByteBuffer.allocateDirect(3*2*4);
        bb2.order(ByteOrder.nativeOrder());
        rootsBuffer = bb2.asFloatBuffer();
        float[] t = PolyInfo.roots();
        for (int i=0;i<6;i++)
            rs[i] = t[i];
        rootsBuffer.put(rs);
        rootsBuffer.position(0);

        // prepare shaders and OpenGL program
        int vertexShader = GLFractalRenderer.loadShader(
                GLES20.GL_VERTEX_SHADER,
                ShaderCodeFile.vertexShaderCode);
        int fragmentShader = GLFractalRenderer.loadShader(
                GLES20.GL_FRAGMENT_SHADER,
                ShaderCodeFile.newFS);
        int simpleVertexShader = GLFractalRenderer.loadShader(
                GLES20.GL_VERTEX_SHADER,
                ShaderCodeFile.simpleVertexShaderCode);
        int simpleFragmentShader = GLFractalRenderer.loadShader(
                GLES20.GL_FRAGMENT_SHADER,
                ShaderCodeFile.simpleShaderCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLFractalRenderer.checkGlError("glAttachShader");
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLFractalRenderer.checkGlError("glAttachShader");
        GLES20.glLinkProgram(mProgram);                  // create OpenGL program executables
        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if(mProgram!=0) {
            if (linkStatus[0] != GLES20.GL_TRUE) {
                Log.e(TAG, "Could not link program: ");
                Log.e(TAG, GLES20.glGetProgramInfoLog(mProgram));
                GLES20.glDeleteProgram(mProgram);
//                mProgram = 0;
            }
        }
        //eps01Handle = GLES20.glGetUniformLocation(mProgram, "eps01");
        //GLES20.glUniform1f(eps01Handle, 0.001f);
        simpleProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(simpleProgram, simpleVertexShader);   // add the vertex shader to program
        GLFractalRenderer.checkGlError("glAttachShader");
        GLES20.glAttachShader(simpleProgram, simpleFragmentShader); // add the fragment shader to program
        GLFractalRenderer.checkGlError("glAttachShader");
        GLES20.glLinkProgram(simpleProgram);                  // create OpenGL program executables
        int[] linkStatus2 = new int[1];
        GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, linkStatus2, 0);
        if(simpleProgram!=0) {
            if (linkStatus2[0] != GLES20.GL_TRUE) {
                Log.e(TAG, "Could not link program: ");
                Log.e(TAG, GLES20.glGetProgramInfoLog(simpleProgram));
                GLES20.glDeleteProgram(simpleProgram);
            }
        }
        // set up 2nd VBO
//        glGenVertexArraysOES(1, &_vao2);
//        glBindVertexArrayOES(_vao2);
//        glGenBuffers(1, &_vbo2);
//        glBindBuffer(GL_ARRAY_BUFFER, _vbo2);
//        int rCount =[PolyInfo rootCount];
//        for(int i=0;i<2*rCount;i++)
//            setVertices[i]=[PolyInfo getR:i];
//        sizeOfSetVertices = 2*rCount;

    }
    
    public void draw(float[] mvpMatrix) {
    	if(squareCoords==null)
    		return;
        // Add program to OpenGL environment
        GLES20.glUseProgram(mProgram);
        GLFractalRenderer.checkGlError("glUseProgram");
//        int error;
//        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
//            Log.e("MyApp", ": glError " + error);
//        }
        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLFractalRenderer.checkGlError("glGetUniformLocation");
        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        // Prepare the triangle coordinate data
        squareCoords = WorldInfo.vertices();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);
        GLES20.glVertexAttribPointer(
                mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);
//        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
//        GLFractalRenderer.checkGlError("glGetUniformLocation");
//        GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        backgroundColorHandle = GLES20.glGetUniformLocation(mProgram, "background_color");
        GLFractalRenderer.checkGlError("glGetUniformLocation");
        //GLES20.glUniform4fv(backgroundColorHandle, 1, ColorKeeperOLD.BackgroundColor,0);
        GLES20.glUniform4fv(backgroundColorHandle, 1, ColorInfo.background(),0);

        failureColorHandle = GLES20.glGetUniformLocation(mProgram, "failureColor");
        GLFractalRenderer.checkGlError("glGetUniformLocation");
        GLES20.glUniform4fv(failureColorHandle, 1, ColorInfo.failure(), 0);

        summHandle = GLES20.glGetUniformLocation(mProgram, "summ");
        GLFractalRenderer.checkGlError("glGetUniformLocation");
        //GLES20.glUniform4fv(backgroundColorHandle, 1, ColorKeeperOLD.BackgroundColor,0);
        GLES20.glUniform2f(summHandle, PolyInfo.getSumX(), PolyInfo.getSumY());

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLFractalRenderer.checkGlError("glGetUniformLocation");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        GLFractalRenderer.checkGlError("glUniformMatrix4fv");
//        mouseHandle = GLES20.glGetUniformLocation(mProgram, "mouse");
//        GLES20.glUniform2f(mouseHandle, mX/(float)480.0, mY/(float)480.0);

        epsHandle = GLES20.glGetUniformLocation(mProgram, "eps");
        //GLES20.glUniform1f(epsHandle, IterationKeeperOLD.Eps);
        GLES20.glUniform1f(epsHandle, IterationInfo.eps());
        //Log.d("Fractal: ","Eps set to "+String.valueOf(IterationKeeper.Eps));

        eps2Handle = GLES20.glGetUniformLocation(mProgram, "eps2");
//        GLES20.glUniform1f(eps2Handle, IterationKeeperOLD.Eps2);
        GLES20.glUniform1f(eps2Handle, IterationInfo.eps2());

        maxItHandle = GLES20.glGetUniformLocation(mProgram, "maxIt");
        //GLES20.glUniform1i(maxItHandle, IterationKeeperOLD.MaxIt);
        GLES20.glUniform1i(maxItHandle, IterationInfo.nIterations());

        rootCountHandle = GLES20.glGetUniformLocation(mProgram, "rootCount");

        int tempV = PolyInfo.rootCount();//Polynomial.rootCount;
        GLES20.glUniform1i(rootCountHandle, tempV);

        rootsHandle = GLES20.glGetUniformLocation(mProgram, "roots");
        //GLES20.glUniform1fv(rootsHandle, 2*Polynomial.rootCount, Polynomial.roots,0);
        //GLES20.glUniform2fv(rootsHandle, 3, Polynomial.roots,0);
        GLES20.glUniform2fv(rootsHandle, 3, PolyInfo.roots(),0);
        //
        alphaHandle = GLES20.glGetUniformLocation(mProgram, "alpha");
        GLES20.glUniform2f(alphaHandle, MethodInfo.getAlphaX(), MethodInfo.getAlphaY());
        useAlphaHandle = GLES20.glGetUniformLocation(mProgram, "useAlpha");
        GLES20.glUniform1i(useAlphaHandle, MethodInfo.alpha());
//        GLES20.glUniform2f(alphaHandle, 0.7f, 0.3f);
        methodHandle = GLES20.glGetUniformLocation(mProgram, "method");
        GLES20.glUniform1i(methodHandle, MethodInfo.method());
        // Apply the projection and view transformation
        colorsHandle = GLES20.glGetUniformLocation(mProgram,"colors");
        GLES20.glUniform4fv(colorsHandle, 3, ColorInfo.colors(), 0);

        normToUseHandle = GLES20.glGetUniformLocation(mProgram,"normToUse");
//        MethodInfo.setNorm(0);
        GLES20.glUniform1i(normToUseHandle, MethodInfo.norm());
        postOptionHandle = GLES20.glGetUniformLocation(mProgram,"postOption");
        GLES20.glUniform1i(postOptionHandle, MethodInfo.option());
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
//        GLES20.glDrawElements(
//                GLES20.GL_TRIANGLES, drawOrder.length,
//                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
//        GLES20.glDrawElements(
//                //GLES20.GL_TRIANGLES, smallOrder.length,
//                GLES20.GL_TRIANGLES, smallOrder.length,
//                GLES20.GL_UNSIGNED_SHORT, smallListBuffer);
        GLES20.glDisableVertexAttribArray(mPositionHandle);

        // simple program
        if(IterationInfo.showRoots()) {
            GLES20.glUseProgram(simpleProgram);
            GLFractalRenderer.checkGlError("glUseProgram");
            simplePositionHandle = GLES20.glGetAttribLocation(simpleProgram, "vPosition");
            GLFractalRenderer.checkGlError("glGetAttribLocation");
            // Enable a handle to the triangle vertices
            GLES20.glEnableVertexAttribArray(simplePositionHandle);
            float[] t = PolyInfo.roots();
            for (int i = 0; i < 6; i++)
                rs[i] = t[i];
            rootsBuffer.put(rs);
            rootsBuffer.position(0);
            GLES20.glVertexAttribPointer(
                    simplePositionHandle, COORDS_PER_VERTEX,
                    GLES20.GL_FLOAT, false,
                    2 * 4, rootsBuffer); // vertex stride = coords_per_vertex*4;
            simpleMVPMatrixHandle = GLES20.glGetUniformLocation(simpleProgram, "uMVPMatrix");
            GLFractalRenderer.checkGlError("glGetUniformLocation");
            GLES20.glUniformMatrix4fv(simpleMVPMatrixHandle, 1, false, mvpMatrix, 0);
            GLFractalRenderer.checkGlError("glUniformMatrix4fv");
            // draw simple things
            GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 3);
            GLES20.glDisableVertexAttribArray(simplePositionHandle);
        }

    }
    public void setClick(float iX, float iY) {
    	//var cr = glcanvas.getBoundingClientRect();
        //var ix = event.clientX - cr.left;    	
        mX = WorldOLD.g2wx(iX);
        mY = WorldOLD.g2wy(iY);
//        Log.d("Mouse: ",mX+" "+mY);
        if (Polynomial.rootCount == 4) {            
        	Polynomial.roots[Polynomial.rootCount++] = mX;
        	Polynomial.roots[Polynomial.rootCount++] = mY;
        }
        else {  // don't create the root - just update it
        	Polynomial.roots[4] = mX;
        	Polynomial.roots[5] = mY;
        }    	
    	//mX = iX; 
    	//mY = iY;
    	//int c=3;
    }
    public void setRatio(float r) {
    	ratio = r;
//    	if(squareCoords==null) {
//    		squareCoords = new float[12];
    	//
//    	}
    }

}
