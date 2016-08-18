/*
 * Created by Davide Cossu (gjkf), 8/11/2016
 */
package com.gjkf.lunarLander.core.gui.screens;

import com.gjkf.lunarLander.core.gui.player.Player;
import com.gjkf.lunarLander.core.terrain.Terrain;
import com.gjkf.seriousEngine.core.gui.GuiScreenWidget;
import com.gjkf.seriousEngine.core.render.Colors;
import com.gjkf.seriousEngine.core.render.Renderer;

public class MainScreen extends GuiScreenWidget{

    private long startTime;
    private Player player;
    private Terrain terrain;

    public MainScreen(int width, int height){
        super(width, height);
        this.startTime = System.currentTimeMillis();
        player = new Player(100, 100, 32, 32);
        terrain = new Terrain(0, 900, width, height-800);
        terrain.generateTerrain(20);
        add(player);
        add(terrain);
    }

    @Override
    public void drawBackground(){
        long elapsed = System.currentTimeMillis() - this.startTime;
        int seconds = (int)Math.floor(elapsed/1000)%60;
        int minutes = (int)Math.floor(elapsed/(60*1000))%60;
        String time = (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
        Renderer.drawText(10, 30, "Time: " + time, 30, Colors.WHITE.color);
    }

    @Override
    public void drawForeground(){

    }

}
