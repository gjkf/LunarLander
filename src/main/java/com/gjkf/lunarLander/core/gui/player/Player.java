/*
 * Created by Davide Cossu (gjkf), 8/12/2016
 */
package com.gjkf.lunarLander.core.gui.player;

import com.gjkf.lunarLander.core.terrain.Terrain;
import com.gjkf.seriousEngine.SeriousEngine;
import com.gjkf.seriousEngine.core.controls.Keys;
import com.gjkf.seriousEngine.core.gui.GuiWidget;
import com.gjkf.seriousEngine.core.math.Vector2f;
import com.gjkf.seriousEngine.core.render.Colors;
import com.gjkf.seriousEngine.core.render.Image;
import com.gjkf.seriousEngine.core.render.Renderer;

public class Player extends GuiWidget{

    private Image image;
    private int thrust = 0, angle = -90;
    private Vector2f velocity;

    public Player(int x, int y, int width, int height){
        super(x, y, width, height, null);
        Keys.registerKeys(SeriousEngine.window.window,(long w, int key, int code, int action, int mods) -> {
            if(code == 1){
                if(key == 265){ // UP arrow
                    if(thrust < 3){
                        thrust++;
                    }
                }else if(key == 264){ // DOWN arrow
                    if(thrust > 0){
                        thrust--;
                    }
                }
            }
            if(key == 262){ // LEFT arrow
                angle+=4;
            }else if(key == 263){ // RIGHT arrow
                angle-=4;
            }
        });
        velocity = new Vector2f();
        velocity.limit(2);
    }

    @Override
    public void draw(){
        super.draw();
        this.image = Image.loadImage("textures/lander.png");
        // TODO: Fix the strange stretch the thrust causes.
        Renderer.drawImageRegion(this.image, this.x, this.y, 32f * thrust, 0, 32f, 32f, Colors.WHITE.color, 90+angle);
    }

    @Override
    public void update(){
        super.update();
        this.velocity.add(new Vector2f(0, Terrain.gravity));
        this.velocity.add(new Vector2f((float)Math.cos(Math.toRadians(angle)) * thrust/125f, (float)Math.sin(Math.toRadians(angle)) * thrust/125f));
        this.x += this.velocity.x;
        this.y += this.velocity.y;
        System.out.println(String.format("%f, %f, %d, %d", velocity.x, velocity.y, this.x, this.y));

    }
}
