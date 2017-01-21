#version 120

varying vec4      v_color;
varying vec2      v_texCoord0;

uniform float     time;
uniform vec4      u_color;
uniform sampler2D u_texture;
uniform vec2      u_resolution;
uniform vec3      u_position;
uniform float     u_intensity;

void main() {

    if (u_intensity > 0) {
        float radial_blur_offset = u_intensity; // 0.05;
        float radial_brightness = 1;
    
        vec2 position = vec2(u_position.x / u_resolution.x, u_position.y / u_resolution.y);
    
        vec2 uv = vec2(v_texCoord0);
        //    uv.x *= (u_resolution.x / u_resolution.y);
        vec2 radial_size = vec2(1.0 / u_resolution);
        radial_size.x *= (u_resolution.x / u_resolution.y);
        vec2 radial_origin = vec2(position);
        vec4 colour = vec4(0.0);
    
        float blur_amount = 30.0;
    
        uv += radial_size * 0.5 - radial_origin;
    
        for (float i = 0.0; i < blur_amount; i++)
        {
            float offset = 1.0 - radial_blur_offset * (i / (blur_amount - 1.0));
            colour += texture2D(u_texture, uv * offset + radial_origin);
        }

        gl_FragColor = colour / (blur_amount - 1.0) * radial_brightness;
    } else {
        gl_FragColor = texture2D(u_texture, v_texCoord0) * v_color;
    }
}