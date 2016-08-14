/*
 * Created by Davide Cossu (gjkf), 8/12/2016
 */
package com.gjkf.lunarLander.gui.player;

import com.gjkf.seriousEngine.SeriousEngine;
import com.gjkf.seriousEngine.core.controls.Keys;
import com.gjkf.seriousEngine.core.gui.GuiWidget;
import com.gjkf.seriousEngine.core.render.Image;
import com.gjkf.seriousEngine.core.render.Renderer;
import org.lwjgl.opengl.GL11;

public class Player extends GuiWidget{

    private Image image;
    private int thrust = 0, angle = 0;

    public Player(int x, int y, int width, int height){
        super(x, y, width, height, null);
        Keys.registerKeys(SeriousEngine.window.window,(long w, int key, int code, int action, int mods) -> {
            System.out.println(key);
            if(code == 1){
                if(key == 265){
                    if(thrust < 3){
                        thrust++;
                    }
                }else if(key == 264){
                    if(thrust > 0){
                        thrust--;
                    }
                }
            }
            if(key == 262){
                angle++;
            }else if(key == 263){
                angle--;
            }
        });
    }

    @Override
    public void draw(){
        super.draw();
        this.image = Image.loadImage("textures/lander.png");
//        Renderer.drawImageRegion(this.image, this.x, this.y, 10, 10, 32, 32);
        System.out.println("T: " + thrust + " A: " + angle);
        GL11.glPushMatrix();
        // TODO: See here (http://wiki.lwjgl.org/index.php?title=The_Quad_with_Projection,_View_and_Model_matrices) for shader rotation
        Renderer.drawImageRegion(this.image, this.x, this.y, 32 * thrust, 0, 32, 32);
        GL11.glPopMatrix();
    }

    @Override
    public void update(){
        super.update();
    }
}
