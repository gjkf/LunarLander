/*
 * Created by Davide Cossu (gjkf), 8/11/2016
 */
package com.gjkf.lunarLander.gui.screens;

import com.gjkf.seriousEngine.core.gui.GuiLabel;
import com.gjkf.seriousEngine.core.gui.GuiScreenWidget;
import com.gjkf.seriousEngine.core.render.Colors;

public class MainScreen extends GuiScreenWidget{
    public MainScreen(int width, int height){
        super(width, height);
        add(new GuiLabel(100, 100, 10f, Colors.GREEN.color, "Test label for screen func\ntion"));
    }

    @Override
    public void drawBackground(){

    }

    @Override
    public void drawForeground(){

    }
}
