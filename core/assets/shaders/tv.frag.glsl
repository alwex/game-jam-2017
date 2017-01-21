#version 120

varying vec4      v_color;
varying vec2      v_texCoord0;

uniform float     time;
uniform vec4      u_color;
uniform sampler2D u_texture;
uniform vec2      u_resolution;

float rand(vec2 co) { return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);}

void main() {

    vec2 q1 = v_texCoord0.xy / u_resolution.xy;

    vec2 uv = 0.5 + (q1 - 0.5) * (0.98 + 0.006); //*sin(0.9));
    uv = v_texCoord0;
    vec3 col;

    // décallage des couleurs RGB
    col.r = texture2D(u_texture, vec2(uv.x + 0.003, uv.y)).r;
    col.g = texture2D(u_texture, vec2(uv.x + 0.000, uv.y)).g;
    col.b = texture2D(u_texture, vec2(uv.x - 0.003, uv.y)).b;

    // assombrissement des bords
    col = clamp(col * 0.5 + 0.5 * col * col * 1.2, 0.0, 1.0);
    col *= 0.6 + 0.4 * 16.0 * uv.x * uv.y * (1.0 - uv.x) * (1.0 - uv.y);
    col *= vec3(0.9, 1.0, 0.7);

    // ajout des scanlines
    col *= 0.95 + 0.2 * sin(10.0 * time + uv.y * 512.0);

    // ajout d'un sintillement
    col *= 1.0 - 0.07 * rand(vec2(time, tan(time)));

    gl_FragColor = vec4(col, 1);
}