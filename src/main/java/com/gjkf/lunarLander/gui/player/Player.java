/*
 * Created by Davide Cossu (gjkf), 8/12/2016
 */
package com.gjkf.lunarLander.gui.player;

import com.gjkf.seriousEngine.core.gui.GuiWidget;
import com.gjkf.seriousEngine.core.render.Image;
import com.gjkf.seriousEngine.core.render.Renderer;

public class Player extends GuiWidget{

    private Image image;
    private int thrust = 0;

    public Player(int x, int y, int width, int height){
        super(x, y, width, height, null);
    }

    @Override
    public void draw(){
        super.draw();
        this.image = Image.loadImage("textures/lander.png");
//        Renderer.drawImageRegion(this.image, this.x, this.y, 10, 10, 32, 32);
        Renderer.drawImageRegion(this.image, this.x, this.y, 32 * thrust, 0, 32, 32);
    }

    public Image getImage(){
        return image;
    }
}
