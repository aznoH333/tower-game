#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;
vec3 def = vec3(1.0, 0.0, 0.0);

float lerp(float start, float end, float value){
    return ((end - start) * value) + start;
}

void main() {

        vec3 color = texture2D(u_texture, v_texCoords).rgb;


        int posX = int(gl_FragCoord.x);
        int posY = int(gl_FragCoord.y);

        float dist = distance(vec2(posX, posY), vec2(500, 500));

        float bruh = max(1.0 - (dist/2000.0), 0.0);


        color.r *= bruh;
        color.g *= bruh;
        color.b *= bruh;

        gl_FragColor = vec4(color, 1.0);
}

