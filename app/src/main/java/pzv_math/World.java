package pzv_math;

/**
 * Created by Palladin on 10/15/2015.
 */
public class World {
    static float zoom;
    static float centerX;
    static float centerY;
    static float step;
    static float prox2;
    static float vertices[]={1.0f,1.0f,-1.0f,1.0f,1.0f,-1.0f,-1.0f,-1.0f};
    static float ratio;

    World()
    {
        ratio=1.0f;
        zoom=1.0f;
        centerX=0.0f;
        centerY=0.0f;
        step = 0.2f;
        prox2 = 0.08f*0.08f;
    }
    public float[] getVertices() {
        return vertices;
    }

    public float getRatio() {
        return ratio;
    }
    public float getZoom() {
        return zoom;
    }
    public float getCX() {return centerX;}
    public float getCY() {return centerY;}
    public float getStep() {return step; }
    public float getProx2() {return prox2; }

    public void doVertices() {
        float semiWidth =1.0f*zoom;
        float semiHeight =1.0f*ratio*zoom;
        vertices[0]=centerX+semiWidth;
        vertices[1]=centerY+semiHeight;
        vertices[2]=centerX-semiWidth;
        vertices[3]=centerY+semiHeight;
        vertices[4]=centerX+semiWidth;
        vertices[5]=centerY-semiHeight;
        vertices[6]=centerX-semiWidth;
        vertices[7]=centerY-semiHeight;
    }

    public void setRatio (float input) { ratio = input; }
    public void setCX (float input) { centerX = input; }
    public void setCY (float input) { centerY = input; }
    public void setStep (float input) { step = input; }
    public void setProx2 (float input) { prox2 = input;}
    public void setZoom (float input) { zoom = input;}
    public void setVertices(float[] input) {
        for(int i=0;i<8;i++)
            vertices[i]=input[i];
    }

    public void setNormalRatio() {    ratio = 1.0f;}
    public void moveLeft() {centerX -= step; }
    public void moveRight() {centerX += step;}
    public void moveUp() {centerY += step;}
    public void moveDown() {centerY -= step;}
    public static float reZoom() {
        return 1.4f;
    }
    public void zoomIn() {
        float s_gRezoom = World.reZoom();
        zoom /= s_gRezoom; step /= s_gRezoom;
        prox2 /= s_gRezoom*s_gRezoom;
    }
    public void zoomOut() {
        float s_gRezoom = World.reZoom();
        zoom *= s_gRezoom; step *= s_gRezoom;
        prox2 *= s_gRezoom*s_gRezoom;
    }
}
