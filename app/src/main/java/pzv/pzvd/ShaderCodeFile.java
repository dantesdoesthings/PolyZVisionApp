package pzv.pzvd;

/**
 * Created by Palladin on 11/26/2015.
 */
public class ShaderCodeFile {
    public static final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "varying vec2 mVertex;"+
                    "void main() {" +
                    // the matrix must be included as a modifier of gl_Position
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    //"  gl_Position = uMVPMatrix * vPosition;" +
                    "gl_Position = uMVPMatrix * vPosition;"+
                    //"mVertex = gl_Position.xyz;"+
                    "mVertex = vPosition.xy;"+
                    "}";
    public static final String simpleVertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
//            "varying vec2 mVertex;"+
                    "void main() {" +
                    "   gl_PointSize = 10.0;"+
                    "   gl_Position = uMVPMatrix * vPosition;"+
//            "   mVertex = vPosition.xy;"+
                    "}";
    public static final String simpleShaderCode =
            "precision mediump float;"+
                    "void main() {"+
                    "   gl_FragColor=vec4(0.,0.9,0.8,1.0);" +
                    "}";

    public static final String newFS =
            "precision mediump float;"+
                    "varying vec2 mVertex;"+
                    "uniform int rootCount;"+
                    "uniform vec2 roots[3];"+
                    "uniform vec4 colors[3];"+
                    "uniform int normToUse;"+
                    "uniform int maxIt;"+
                    "uniform float eps;"+
                    "uniform float eps2;" +
                    "uniform vec4 background_color;"+
                    "uniform vec4 failureColor;"+
                    "uniform int postOption;"+
                    "uniform int useAlpha;"+
                    "uniform vec2 alpha;"+
                    "uniform int method;"+
                    "uniform vec2 summ;"+
                    "vec2 ID=vec2(1.0,0.0);"+
                    "vec2 ZERO=vec2(0.0,0.0);"+
                    //"int halfIt = maxIt/4;"+
                    "float mitFloat;" +
                    "float fIt;"+
                    "vec4 main_color=vec4(0.0,0.0,0.0,0.0);"+
                    "vec2 times(vec2 a,vec2 b) { return vec2(a.x * b.x - a.y * b.y,a.x * b.y + a.y * b.x); }"+
                    "vec2 divide(vec2 a,vec2 b){"+
                    "    float den = b.x * b.x + b.y * b.y;"+
                    "    return vec2((b.x * a.x + a.y * b.y) / den,(b.x * a.y - a.x * b.y) / den);"+
                    "}"+
                    "vec2 reciprocal(vec2 b) {"+
                    "   float tt = b.x*b.x+b.y*b.y;"+
                    "   return vec2(b.x/tt,-b.y/tt);"+
                    "}"+
                    //"float norm(vec2 a) {return sqrt(dot(a,a)); }"+
                    "float norm(vec2 a) {"+
                    "    if(normToUse==2)"+
                    "        return sqrt(dot(a, a));"+
                    "    if(normToUse==1)"+
                    "        return abs(a.x)+abs(a.y);"+
                    //"    if(normToUse==0)"+
                    "        return max(abs(a.x),abs(a.y));"+
                    "}"+
////        "float norm2(vec2 a) { return dot(a,a);}"+
////        "float DistanceSq(vec2 a, vec2 b){ return norm2(a-b);}"+
                    "vec2 sq(vec2 z) { return vec2(z.x*z.x-z.y*z.y,2.0*z.x*z.y);}"+
////          "vec2 cube(vec2 z) {return times(z,times(z,z));}"+
                    "vec2 p(vec2 z) {"+
                    "   return times(times(z-roots[0],z-roots[1]),z-roots[2]);"+
                    "}"+
                    "vec2 pd(vec2 z) {"+
                    "   return times(z-roots[0],z-roots[1])+times(z-roots[1],z-roots[2])+times(z-roots[0],z-roots[2]);"+
                    "}"+
                    "vec2 extraNewton(vec2 z) {"+
                    "   vec2 d0 = z-roots[0];"+
                    "   vec2 d1 = z-roots[1];"+
                    "   vec2 d2 = z-roots[2];"+
                    "   vec2 p01 = times(d0,d1);"+
                    "   return divide(times(p01,d2),  p01+times(d1,d2)+times(d0,d2));"+
                    "}"+
                    "vec2 fNewton(vec2 z) {"+
                    "   vec2 d0 = z-roots[0];"+
                    "   vec2 d1 = z-roots[1];"+
                    "   vec2 d2 = z-roots[2];"+
                    "   vec2 p01 = times(d0,d1);"+
                    "   return z - divide(times(p01,d2),  p01+times(d1,d2)+times(d0,d2));"+
                    "}"+
                    "vec2 fAlphaNewton(vec2 z) {"+
                    "   return z - times(alpha,extraNewton(z));"+
                    "}"+
                    "vec2 sigma(vec2 z) {"+
                    "   vec2 sm = ZERO;"+
                    "   for (int i = 0; i<3; i++) {"+
                    "       vec2 term = z - roots[i];"+
                    "       term = reciprocal(term);"+
                    "       sm += term;"+
                    "   }"+
                    "   return sm;"+
                    "}"+
                    "vec2 sigmaD(vec2 z) {"+
                    "   vec2 sm = ZERO;"+
                    "   for (int i = 0; i<3; i++) {"+
                    "       vec2 term = z - roots[i];"+
                    "       term = reciprocal(term);"+
                    "       term = sq(term);"+
                    "       sm += term;"+
                    "   }"+
                    "   return -sm;"+
                    "}"+
                    "vec2 extraHalley(vec2 z) {"+
                    "    vec2 t0 = reciprocal(z-roots[0]);"+
                    "    vec2 t1 = reciprocal(z-roots[1]);"+
                    "    vec2 t2 = reciprocal(z-roots[2]);"+
                    "    vec2 sz = t0+t1+t2;"+
                    "    vec2 extraterm = summ-3.0*z;"+
                    //"    vec2 extraterm = roots[0]+roots[1]+roots[2]-3.0*z;"+
                    "    return divide(sz,sq(sz)+divide(extraterm,p(z)));"+
                    //"    return divide(sz,square(sz));"+
                    "}"+
                    "vec2 fHalley(vec2 z) {"+
                    "    vec2 t0 = reciprocal(z-roots[0]);"+
                    "    vec2 t1 = reciprocal(z-roots[1]);"+
                    "    vec2 t2 = reciprocal(z-roots[2]);"+
                    "    vec2 sz = t0+t1+t2;"+
                    //"    vec2 extraterm = roots[0]+roots[1]+roots[2]-3.0*z;"+
                    "    vec2 extraterm = summ-3.0*z;"+
                    "    return z - divide(sz,sq(sz)+divide(extraterm,p(z)));"+
                    "}"+
                    "vec2 fAlphaHalley(vec2 z) {"+
                    "    return z - times(alpha,extraHalley(z));"+
                    "}"+
//        "vec2 extraESC(vec2 z) {"+
//        "    vec2 t0 = reciprocal(z-roots[0]);"+
//        "    vec2 t1 = reciprocal(z-roots[1]);"+
//        "    vec2 t2 = reciprocal(z-roots[2]);"+
//        "    vec2 sz = t0+t1+t2;"+
//        "    vec2 sdz = (square(t0)+square(t1)+square(t2));"+
//        "    sdz.x += 2.0;"+
//        "    vec2 oneOverS = reciprocal(sz);"+
//        "    vec2 s3 = times(square(oneOverS),oneOverS);"+
//        "    return 1.5*oneOverS+0.5*times(sdz,s3);"+
//        "}"+
                    "vec2 extraMrbf(vec2 z) {"+
                    "    vec2 t0 = reciprocal(z-roots[0]);"+
                    "    vec2 t1 = reciprocal(z-roots[1]);"+
                    "    vec2 t2 = reciprocal(z-roots[2]);"+
                    "    vec2 sz = t0+t1+t2;"+
                    "    vec2 sdz = sq(t0)+sq(t1)+sq(t2);"+
                    "    return divide(sz,sdz);"+
                    "}"+
                    "vec2 fMrbf(vec2 z) {"+
                    "    return z - extraMrbf(z);"+
                    "}"+
//        "vec2 escf(vec2 z ) {"+
//        "    return z-extraESC(z);"+
//        "}"+
                    "vec2 fAlphaMrbf(vec2 z) {"+
                    "    return z - times(alpha, extraMrbf(z));"+
                    "}"+
                    "vec2 fMain(vec2 tin) {"+
                    "if(useAlpha==0) {"+
                    "   if(method==0)"+
                    "       return fNewton(tin);"+
                    "   if(method==1)"+
                    "       return fHalley(tin);"+
                    //"   if(method==2)"+
                    "       return fMrbf(tin);"+
                    "}"+
                    "else"+
                    "{"+
                    "   if(method==0)"+
                    "       return fAlphaNewton(tin);"+
                    "   if(method==1)"+
                    "       return fAlphaHalley(tin);"+
                    //"   if(method==2)"+
                    "       return fAlphaMrbf(tin);"+
                    "   }"+
                    "}"+
//                    "void main() {"+
//                    "	float it=0.0;"+
//                    "   vec2 zn=mVertex.xy;"+
////        "   vec2 resolution = vec2(50.0,50.0);"+
//                    "	vec2 zp=zn;"+
//                    "	vec2 d;"+
////        "	float dx=(zn.x-zp.x);"+
////        "	float dy=(zn.y-zp.y);"+
//                    "	for(int cit=0;cit < 12;cit++)  {"+
//                    "		zn=fNewton(zp);"+
//                    "		d=zn-zp;"+
//                    "		if(norm2(d) < eps)"+
//                    "			break;		"+
//                    "   	zp=zn;"+
//                    "		it++;"+
//                    "	}"+
//                    "float tau = it/12.0;"+
//                    "vec4 tempColor=vColor;"+//background_color;"+
//                    //    "tempColor.x=background_color.x*(mX/100.0);"+
//                    //    "tempColor.y=background_color.y*(mY/100.0);"+
//                    "gl_FragColor = "+
//                    "	main_color*(1.0-tau)+tempColor*tau;"+
//                    "}";
//"void main()"+
//"{" +
//"gl_FragColor=vec4(0.7,0.3,0.1,1.0);" +
//"}";
                    "vec4 oppositeColor(vec4 inColor) {"+
                    "  return vec4(1.0-inColor.x,1.0-inColor.y,1.0-inColor.z,1.0);"+
                    "}"+

                    "vec4 getColor(int index) {" +
                    "    if(index==0) return colors[0];" +
                    "    if(index==1) return colors[1];" +
                    "    if(index==2) return colors[2];" +
                    "    return failureColor;" +
                    "}" +
                    "void main() {"+
//        "   gl_FragColor=vec4(0.7,0.3,0.1,1.0);" +
//        "}";
					"mitFloat=float(maxIt);"+
					"fIt = mitFloat/4.0;"+
                    "float it=-1.0;" +
                    "vec2 zn= mVertex;" +
                    "vec2 zp=zn;" +
                    "vec2 d;" +
                    "vec4 temp=vec4(0.3,0.7,0.0,1.0);"+
                    "for(int cit=0;cit < 50;cit++)  {" +
                    "    if(cit>=maxIt)" +
                    "        break;" +
                    "    zn=fMain(zp);" +
//        "    zn.x=zp.x*0.8;" +
//        "    zn.y=zp.y*0.8;" +
                    "    d=zn-zp;" +
                    "    if(norm(d) < eps) {" +
                    "        zp=zn;" +
                    "        break;" +
                    "    }" +
                    "    zp=zn;" +
                    "    it++;" +
                    "}" +
                    "float tau=-0.3;"+
////        "float tau = it/mitFloat;" +
////        "int iCount = 2*int(rootCount);" +
                    "int crr=-1;" +
                    "float cut=5.0;"+
                    "if(postOption==0) {"+
                    "   for(int cr=0;cr<3;cr++) {"+
                    "       vec2 term =zp-roots[cr];"+
                    "       if(norm(term)<eps2) {"+
                    "           crr = cr;"+
                    "           break;"+
                    "       }"+
                    "   }"+
                    "   tau=it/mitFloat;"+
                    "   main_color=getColor(crr);" +
                    "   temp=main_color*(1.0-tau)+background_color*tau;"+
                    "}"+
                    "else if(postOption==1) {"+
                    "   for(int cr=0;cr<3;cr++) {"+
                    "       vec2 term =zp-roots[cr];"+
                    "       if(norm(term)<eps2) {"+
                    "           crr = cr;"+
                    "           break;"+
                    "       }"+
                    "   }"+
                    "   main_color=getColor(crr);" +
                    "if(it>=mitFloat) {"+
                    "    tau = mod(it,fIt)/fIt;"+
                    "}"+
                    "else {"+
                    "    if(it>=fIt) {"+
                    "        tau = mod(it,fIt)/fIt;"+
//    "        main_color = oppositeColor(main_color);"+
//    "        background_color = oppositeColor(background_color);"+
                    "    }"+
                    "    else {"+
                    "        tau = (it+1.0)/fIt;"+
                    "    }"+
                    "   temp=main_color*(tau)+background_color*(1.0-tau);"+
                    "}"+
                    "}"+ // closing postOption==1
                    "else {"+       // starting the option==2
                    "   if(it>=2.0*cut) {"+
                    "       tau = mod(it,cut)/cut;"+
                    "       main_color = getColor(0);"+
                    "   }"+
                    "   else if(it>=cut) {"+
                    "       tau = mod(it,cut)/cut;"+
                    "       main_color = getColor(1);"+
                    "   }"+
                    "   else {"+
                    "       tau = (it+1.0)/cut;"+
                    "       tau = 1.0-tau;"+
                    "       main_color = getColor(2);"+
                    "   }"+
                    "   temp=main_color*(1.0-tau)+background_color*tau;"+
                    "}"+
                    "gl_FragColor=temp;" +
                    "}";
}
