/*
 * Created by Davide Cossu (gjkf), 8/11/2016
 */
package com.gjkf.lunarLander.gui.screens;

import com.gjkf.seriousEngine.core.gui.GuiScreenWidget;
import com.gjkf.seriousEngine.core.math.Vector2f;
import com.gjkf.seriousEngine.core.math.Vector4f;
import com.gjkf.seriousEngine.core.render.Colors;
import com.gjkf.seriousEngine.core.render.Renderer;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;

public class MenuScreen extends GuiScreenWidget{

    public MenuScreen(int width, int height){
        super(width, height);
    }

    @Override
    public void drawBackground(){
        HashMap<String, Object> m = new HashMap<>();
        m.put("uResolution", new Vector2f(450, 350));
        m.put("uStartingColor", new Vector4f(1f, .6f, .0f, 1f));
        m.put("uEndingColor", new Vector4f(0f, .6f, .6f, 1f));
        m.put("uDirection", 0);
        Renderer.loadShader("shaders/defaultVertex.glsl", "shaders/gradient.glsl", m,
                () -> Renderer.drawArray(new float[]{150,150, 250,420, 600,500}, Colors.GREEN.color, GL11.GL_TRIANGLES)
        );
    }

    @Override
    public void drawForeground(){

    }
}
