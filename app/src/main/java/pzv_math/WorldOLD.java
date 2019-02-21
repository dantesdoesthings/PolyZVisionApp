package pzv_math;

public class WorldOLD {
	public static float xMin=-1.0f;
    public static float xMax=1.0f;
    public static float yMin=-1.0f;
    public static float yMax=1.0f;    
    public static float scale=1.0f;
    public static float xyratio = 1.0f;
    public static int  width=500;
    public static int  height=500;
    public static float x_offset=0.0f;
    public static float y_offset=0.0f;
    public static float zoom=1.0f;
    public static void recompute() {        
        scale = Math.min(width / (xMax - xMin), height / (yMax - yMin));        
        xyratio = (xMax - xMin) / (yMax - yMin);        
    }
	public static void zoomIn() {
        xMin = -zoom + x_offset;
        xMax = zoom + x_offset;
        yMax = zoom + y_offset;
        yMin = -zoom + y_offset;        
        recompute();        
    }
	public static void zoomOut() {
        xMin = -zoom + x_offset;
        xMax = zoom + x_offset;
        yMax = zoom + y_offset;
        yMin = -zoom + y_offset;        
        recompute();
    }
	public static void moveLeft() {        
        float step = zoom * 0.05f;
        xMax -= step; xMin -= step;
        recompute();
    }    
	public static void moveRight() {        
		float step = zoom * 0.05f;
        xMax += step; xMin += step;
        recompute();
    }    
	public static void moveUp() {        
		float step = zoom * 0.05f;
        yMax += step; yMin += step;
        recompute();
    }    
	public static void moveDown() {        
		float step = zoom * 0.05f;
        yMax -= step; yMin -= step;
        recompute();
    }   
	public static float g2wx(float t) {
        float slx=(xMax-xMin)/width;
        return slx*t+xMin;
    }
	public static float g2wy(float t) {
        float sly=(yMin-yMax)/height;
        return sly*t+yMax;
    }
}
