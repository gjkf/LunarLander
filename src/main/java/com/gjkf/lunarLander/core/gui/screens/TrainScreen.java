/*
 * Created by Davide Cossu (gjkf), 8/28/2016
 */
package com.gjkf.lunarLander.core.gui.screens;

import com.gjkf.lunarLander.core.gui.player.Player;
import com.gjkf.lunarLander.core.train.TrainThread;
import com.gjkf.seriousEngine.core.gui.GuiScreenWidget;
import com.gjkf.seriousEngine.core.render.Colors;
import com.gjkf.seriousEngine.core.render.Renderer;

public class TrainScreen extends GuiScreenWidget{

    public static Player player;
    public static TrainThread thread;
    private int X = 0;

    /**
     * The screen constructor
     *
     * @param width  The width
     * @param height The height
     */

    public TrainScreen(float width, float height){
        super(width, height);
//        Terrain terrain = new Terrain(0, 900, 1000, 200);
//        terrain.generateTerrain(20);
//        player = new Player((float) (Math.random() * 100f), 400, 32, 32, terrain, true);

//        Renderer.setFont("fonts/ASO.ttf");
//        add(new GuiButton(30, 300, "TRAIN", 40, ()->thread = new TrainThread()));
//        add(player);
//        add(terrain);
        thread = new TrainThread();
    }

    @Override
    public void drawBackground(){
        thread.run();
        Renderer.drawRect(X, 10, 20, 20, Colors.GREEN.color);
        Renderer.renderFont(200, 200, "Epoch: " + thread.getEpoch(), 5, Colors.WHITE.color);
        System.err.println(this.getClass().toString() + " / CurrentThread: " + Thread.currentThread().getName() + " / TrainThread: " + thread.getName());
        Renderer.renderFont(200, 300, "Score: " + thread.getScore(thread.getEpoch()), 5, Colors.WHITE.color);
        Renderer.renderFont(200, 400, "Turn: " + thread.getTurn(), 5, Colors.WHITE.color);
        X+=20;
    }

    @Override
    public void drawForeground(){

    }

}
