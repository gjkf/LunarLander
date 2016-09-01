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

    /**
     * The start time of the game. Used to update the clock
     */
    private long startTime;
    /**
     * Current game state
     * 0 = in game; 1 = win; -1 = loss
     */
    private int state = 0;
    /**
     * String representing the printed time
     */
    private String time;
    /**
     * Current player object
     */
    private Player player;
    /**
     * Current terrain object
     */
    private Terrain terrain;

    public MainScreen(float width, float height){
        super(width, height);
        startTime = System.currentTimeMillis();
        terrain = new Terrain(0, 900, width, height-800);
        player = new Player(100, 600, 32, 32, terrain, false);
        terrain.generateTerrain(20);
        add(terrain);
        add(player);
    }

    @Override
    public void drawBackground(){
        if(getState() == 0){
            long elapsed = System.currentTimeMillis() - startTime;
            int seconds = (int) Math.floor(elapsed / 1000) % 60;
            int minutes = (int) Math.floor(elapsed / (60 * 1000)) % 60;
            time = (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
        }
        Renderer.drawText(10, 30, "Time: " + time, 30, Colors.WHITE.color);
        Renderer.drawText(10, 70, "Fuel: " + player.getFuel(), 30, Colors.WHITE.color);
    }

    @Override
    public void drawForeground(){
        switch(getState()){
            case 0:
                break;
            case 1:
                Renderer.drawText(300, 100, "Spaceship successfully landed", 40, Colors.GREEN.color);
                break;
            case -1:
                Renderer.drawText(300, 100, "Spaceship crashed!", 40, Colors.GREEN.color);
                break;
        }
    }

    @Override
    public void update(){
        super.update();
        terrain.checkCollision(player);
    }

    public int getState(){
        return state;
    }

    public void setState(int state){
        this.state = state;
    }
}
