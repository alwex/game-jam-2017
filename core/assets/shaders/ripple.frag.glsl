#version 120

varying vec4      v_color;
varying vec2      v_texCoord0;

uniform float     time;
uniform float     u_intensity;
uniform vec4      u_color;
uniform sampler2D u_texture;

void main() {
    float intensity = u_intensity;

    vec2 p = -1 + 2 * v_texCoord0;
    float len = length(p);
    vec2 newCoord = v_texCoord0 + (p/len) * cos(len * 12 - time * 4) * intensity;

    gl_FragColor = texture2D(u_texture, newCoord);
}