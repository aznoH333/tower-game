#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;
uniform float u_windowWidth;
uniform float u_windowHeight;
vec3 def = vec3(0.501960784, 0.62745098, 0.094117647);

float lerp(float start, float end, float value){
    return ((end - start) * value) + start;
}

void main() {
        vec2 screenCenter = vec2(u_windowWidth / 2.0, u_windowHeight / 2.0);
        float screenScalingFactor = 320.0 / u_windowHeight;
        float lightScale = 800.0;


        vec3 color = texture2D(u_texture, v_texCoords).rgb;


        int posX = int(gl_FragCoord.x);
        int posY = int(gl_FragCoord.y);

        float dist = distance(vec2(posX, posY), screenCenter);

        float bruh = max((dist/(lightScale / screenScalingFactor)), 0.0) * 0.3;


        color.r = lerp(color.r, def.r, bruh);
        color.g = lerp(color.g, def.g, bruh);
        color.b = lerp(color.b, def.b, bruh);



        gl_FragColor = vec4(color, 1.0);
}

