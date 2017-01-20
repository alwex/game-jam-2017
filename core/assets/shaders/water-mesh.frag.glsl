#version 120

//input from vertex shader
varying vec4 v_color;

void main() {
    //gl_FragColor = v_color;
    gl_FragColor = vec4(1, 0, 0, 1);
}