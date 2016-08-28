/*
 * Created by Davide Cossu (gjkf), 8/28/2016
 */
package com.gjkf.lunarLander.core.gui.screens;

import com.gjkf.lunarLander.core.gui.player.Player;
import com.gjkf.lunarLander.core.terrain.Terrain;
import com.gjkf.seriousEngine.core.gui.GuiScreenWidget;

public class TrainScreen extends GuiScreenWidget{

    public static Player player;

    /**
     * The screen constructor
     *
     * @param width  The width
     * @param height The height
     */

    public TrainScreen(float width, float height){
        super(width, height);
        Terrain t = new Terrain(0, 900, 1000, 200);
        t.generateTerrain(20);
        player = new Player((float) (Math.random() * 100f), 400, 32, 32, t, true);
    }

    @Override
    public void drawBackground(){

    }

    @Override
    public void update(){
        super.update();

    }

    @Override
    public void drawForeground(){

    }

}
