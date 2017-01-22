package com.alwex.ggj.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by jbrungar on 22/01/17.
 */
public class SliceLineComponent extends Component {
    public Color color = new Color(Color.WHITE);
    public Vector2 sliceStart;
    public Vector2 sliceEnd;

    public SliceLineComponent() {

    }

    public SliceLineComponent(Vector2 sliceStart, Vector2 sliceEnd) {
        this.sliceStart = sliceStart;
        this.sliceEnd = sliceEnd;
    }
}
