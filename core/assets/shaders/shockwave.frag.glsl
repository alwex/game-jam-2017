varying vec4      v_color;
varying vec2      v_texCoord0;

uniform float     time;
uniform vec4      u_color;
uniform sampler2D u_texture;
uniform vec3      u_position;

void main()
{
    vec2 position = u_position.xy;
    vec3 shock_params = vec3(10.0, 8.0, 0.05);
    vec2 final_coord = v_texCoord0;

    float distance = distance(v_texCoord0, position);
    if ((distance <= (time + shock_params.z))
        && (distance >= (time - shock_params.z))) {

        float diff = distance - time;
        float pow_diff = 1.0 - pow(abs(diff * shock_params.x), shock_params.y);
        float diff_time = diff * pow_diff;
        vec2 diff_xy = normalize(v_texCoord0 - position);
        final_coord = v_texCoord0 + (diff_xy * diff_time);
    }

    gl_FragColor = texture2D(u_texture, final_coord);
}