package com.alwex.ggj.components;

import com.artemis.Component;

/**
 * Created by Isaac on 21/01/2017.
 */
public class SplashComponent extends Component {

    public boolean inWater, initialized, removeOnSplash;

    public SplashComponent(){
    }

    public SplashComponent(boolean removeOnSplash){
        this.removeOnSplash = removeOnSplash;
    }
}
