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
    private Vector2f velocity;
    private int thrust = 0, angle = -90;
    private float fuel  = 2500f;

    public Player(float x, float y, float width, float height){
        super(x, y, width, height, null);
        Keys.registerKeys(SeriousEngine.window.window,(long w, int key, int code, int action, int mods) -> {
            if(code == 1){
                if(key == 265){ // UP arrow
                    addThrust();
                }else if(key == 264){ // DOWN arrow
                    removeThrust();
                }
            }
            if(key == 262){ // LEFT arrow
                rotateLeft();
            }else if(key == 263){ // RIGHT arrow
                rotateRight();
            }
        });
        velocity = new Vector2f();
        velocity.limit(2);
    }

    public float getFuel(){
        return fuel;
    }

    public void addThrust(){
        thrust = this.thrust < 3 ? thrust+=1 : thrust;
    }

    public void removeThrust(){
        thrust = this.thrust > 0 ? thrust-=1 : thrust;
    }

    public void rotateRight(){
        angle-=4;
    }

    public void rotateLeft(){
        angle+=4;
    }

    public int getAngle(){
        return angle;
    }

    public Vector2f getVelocity(){
        return velocity;
    }

    @Override
    public void draw(){
        super.draw();
        image = Image.loadImage("textures/lander" + thrust + ".png");
        Renderer.drawImageRegion(this.image, this.x, this.y, 0, 0, 32, 32, Colors.WHITE.color, 90+this.angle);
    }

    @Override
    public void update(){
        super.update();
        velocity.add(new Vector2f(0, Terrain.gravity));
        velocity.add(new Vector2f((float)Math.cos(Math.toRadians(angle)) * thrust/125f, (float)Math.sin(Math.toRadians(angle)) * thrust/125f));
        x += velocity.x;
        y += velocity.y;
        fuel -= thrust/2f + (System.currentTimeMillis() % 15 == 0 ? 1 : 0);
        System.out.println(String.format("%f, %f, %f, %f, %f", velocity.x, velocity.y, x, y, fuel));
    }
}
