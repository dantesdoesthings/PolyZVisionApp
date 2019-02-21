package pzv_math;

/**
 * Created by Palladin on 10/15/2015.
 */
public class ColorInfo {
    static float s_faColors[]={
            1.0f, 144.0f/255.0f, 0.0f, 1.0f,
            //34.0/255, 139.0/255.0, 34.0/255.0, 1.0, dark forest
            //64.0/255, 224.0/255.0, 208.0/255.0, 1.0, Turquoise
            50.0f/255f, 205.0f/255.0f, 50.0f/255.0f, 1.0f, // LimeGreen
            30.0f/255.0f, 144.0f/255.0f, 1.0f, 1.0f,
            0.8f, 0.5f, 0.1f, 1.0f,
            1.0f, 0.0f, 1.0f, 0.5f,
            0.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f, //7
            0.2f, 0.7f, 0.4f, 1.0f,
            0.8f, 0.5f, 0.2f, 1.0f,
            0.37f,0.62f,0.1f,1.0f,
            0.71f,0.05f,0.3f,1.0f,
            0.2f,0.4f,0.6f,1.0f, //12
            0.0f,0.2f,0.9f,0.5f,
            0.5f,0.4f,0.7f,1.0f,
            0.41f,0.1f,0.9f,1.0f,
            0.1f,0.62f,0.2f,1.0f,//16
            0.8f,0.1f,0.2f,1.0f,
            0.0f, 0.5f, 1.0f, 1.0f,
            0.1f,0.8f,0.4f,1.0f
    };
    static float default_faColors[]={
            1.0f, 144.0f/255.0f, 0.0f, 1.0f,
            //34.0/255, 139.0/255.0, 34.0/255.0, 1.0, dark forest
            //64.0/255, 224.0/255.0, 208.0/255.0, 1.0, Turquoise
            50.0f/255f, 205.0f/255.0f, 50.0f/255.0f, 1.0f, // LimeGreen
            30.0f/255.0f, 144.0f/255.0f, 1.0f, 1.0f,
            0.8f, 0.5f, 0.1f, 1.0f,
            1.0f, 0.0f, 1.0f, 0.5f,
            0.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f, //7
            0.2f, 0.7f, 0.4f, 1.0f,
            0.8f, 0.5f, 0.2f, 1.0f,
            0.37f,0.62f,0.1f,1.0f,
            0.71f,0.05f,0.3f,1.0f,
            0.2f,0.4f,0.6f,1.0f, //12
            0.0f,0.2f,0.9f,0.5f,
            0.5f,0.4f,0.7f,1.0f,
            0.41f,0.1f,0.9f,1.0f,
            0.1f,0.62f,0.2f,1.0f,//16
            0.8f,0.1f,0.2f,1.0f,
            0.0f, 0.5f, 1.0f, 1.0f,
            0.1f,0.8f,0.4f,1.0f
    };

    static float s_backgroundColor[]={0.2f,0.2f,0.2f,1.0f};
    static float default_backgroundColor[]={0.0f,0.0f,0.0f,1.0f};
    static float s_failureColor[]={0.0f,0.3f,0.3f,1.0f};
    static float default_failureColor[]={0.0f,0.3f,0.3f,1.0f};
    static float s_infinityColor[]={0.5f,0.5f,0.0f,1.0f};
    static float default_infinityColor[]={0.5f,0.5f,0.0f,1.0f};

    public static float[] background() {
        return s_backgroundColor;
    }
    public static float[] failure() {
        return s_failureColor;
    }
    public static float[] infinity() {
        return s_infinityColor;
    }
    public static float[] colors() {
        return s_faColors;
    }
    public static float getColor(int input, int comp) {
        if(comp==0)
            return s_faColors[4*input];
        if(comp==1)
            return s_faColors[4*input+1];
        return s_faColors[4*input+2];
    }
    public static void setColor(int input, float[] color) {
        switch(input) {
            case 0:
                s_backgroundColor[0]=color[0]; s_backgroundColor[1]=color[1]; s_backgroundColor[2]=color[2];
                break;
            case 1:
                s_failureColor[0]=color[0];s_failureColor[1]=color[1];s_failureColor[2]=color[2];
                break;
//        case 2:
//            s_infinityColor[0]=color[0]; s_infinityColor[1]=color[1]; s_infinityColor[2]=color[2];
//            break;
            default: {
                int inputt = input-2; // No infinity!!
                s_faColors[4*inputt] = color[0];
                s_faColors[4*inputt+1] = color[1];
                s_faColors[4*inputt+2] = color[2];
            }
        }
    }
    public static float[] getColor(int input) {
//    return {s_faColors[3*input],s_faColors[3*input+1],s_faColors[3*input+2]};
        int st = 4*input;
        float temp[]={s_faColors[st],s_faColors[st+1],s_faColors[st+2],s_faColors[st+3]};
        return temp;
    }
    public static void setDefault() {
        for(int i=0;i<4;i++)
            s_infinityColor[i] = default_infinityColor[i];
        for(int i=0;i<4;i++)
            s_failureColor[i] = default_failureColor[i];
        for(int i=0;i<4;i++)
            s_backgroundColor[i] = default_backgroundColor[i];
        for(int i=0;i<s_faColors.length;i++)
            s_faColors[i] = default_faColors[i];
    }
}
