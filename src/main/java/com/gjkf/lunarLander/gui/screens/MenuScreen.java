/*
 * Created by Davide Cossu (gjkf), 8/11/2016
 */
package com.gjkf.lunarLander.gui.screens;

import com.gjkf.seriousEngine.SeriousEngine;
import com.gjkf.seriousEngine.core.gui.GuiButton;
import com.gjkf.seriousEngine.core.gui.GuiLabel;
import com.gjkf.seriousEngine.core.gui.GuiScreenWidget;
import com.gjkf.seriousEngine.core.render.Colors;
import org.lwjgl.opengl.GL11;

public class MenuScreen extends GuiScreenWidget{

    public MenuScreen(int width, int height){
        super(width, height);
//        Renderer.setFont("fonts/ASO.ttf");
        this.debug = true;
        add(new GuiLabel(200, 250, 9f, Colors.WHITE.color, "Lunar Lander"));
        add(new GuiButton(450, 500, "Play", 6f, () -> SeriousEngine.window.setScreen(new MainScreen(width, height))));
    }

    @Override
    public void drawBackground(){
        // Black background
        GL11.glClearColor(0f, 0f, 0f, 1f);
//        Renderer.drawText(width/2, height/2, "Lunar Lander", 20, Colors.WHITE.color);
    }

    @Override
    public void drawForeground(){

    }
}
