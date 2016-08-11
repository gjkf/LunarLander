/*
 * Created by Davide Cossu (gjkf), 8/11/2016
 */
package com.gjkf.lunarLander;

import com.gjkf.lunarLander.gui.screens.MenuScreen;
import com.gjkf.seriousEngine.SeriousEngine;
import com.gjkf.seriousEngine.core.gui.Window;

public class LunarLander{

    public static void main(String... args){
        SeriousEngine engine = new SeriousEngine();

        Window w = new Window(1000, 1000, "Lunar Lander");
        w.setScreen(new MenuScreen(1000, 1000));

        engine.setWindow(w);
        engine.run();
    }

}
