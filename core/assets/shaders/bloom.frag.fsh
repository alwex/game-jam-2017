#version 120

varying vec4      v_color;
varying vec2      v_texCoord0;

uniform float     time;
uniform float     intensity;
uniform vec4      u_color;
uniform sampler2D u_texture;
uniform vec2      u_resolution;

void main() {

    vec4 sum = vec4(0);
    vec2 q1 = v_texCoord0;
    vec4 origColor = texture2D(u_texture, v_texCoord0);

    for (int i = -4; i < 4; i++) {
        for (int j = -3; j < 3; j++) {
            sum += texture2D(u_texture, vec2(j, i) * 0.004 + vec2(q1.x, q1.y)) * 0.25;
        }
    }

    if (origColor.r < 0.4) {
       gl_FragColor = (sum * sum * 0.012) * intensity + origColor;
    } else {
       if (origColor.r < 0.6) {
          gl_FragColor = (sum * sum * 0.009) * intensity + origColor;
       } else {
          gl_FragColor = (sum * sum * 0.0075) * intensity + origColor;
       }
    }
}