/*
 * Created by Davide Cossu (gjkf), 8/12/2016
 */
package com.gjkf.lunarLander.core.gui.player;

import com.gjkf.lunarLander.core.gui.screens.MainScreen;
import com.gjkf.lunarLander.core.terrain.Terrain;
import com.gjkf.seriousEngine.SeriousEngine;
import com.gjkf.seriousEngine.core.controls.Keys;
import com.gjkf.seriousEngine.core.gui.GuiWidget;
import com.gjkf.seriousEngine.core.math.Vector2f;
import com.gjkf.seriousEngine.core.render.Colors;
import com.gjkf.seriousEngine.core.render.Image;
import com.gjkf.seriousEngine.core.render.Renderer;

public class Player extends GuiWidget{

    /**
     * The terrain object
     */
    public Terrain terrain;
    /**
     * Vector representing the velocity of the spaceship
     */
    private Vector2f velocity;
    /**
     * The spaceship thrust
     */
    private int thrust = 0;
    /**
     * The spaceship rotation angle
     */
    private int angle = -90;
    /**
     * The spaceship current fuel level
     */
    private float fuel  = 2500f;
    /**
     * Whether or not this object is used to train a network
     */
    private boolean train;

    public Player(float x, float y, float width, float height, Terrain terrain, boolean training){
        super(x, y, width, height, null);
        this.velocity = new Vector2f();
        this.velocity.limit(2);
        this.terrain = terrain;
        this.train = training;
        if(!train){
            Keys.registerKeys(SeriousEngine.window.window, (long w, int key, int code, int action, int mods) -> {
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
        }
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

    public int getThrust(){
        return thrust;
    }

    public void setThrust(int thrust){
        this.thrust = thrust;
    }

    public void setVelocity(Vector2f velocity){
        this.velocity = velocity;
    }

    public void setFuel(float fuel){
        this.fuel = fuel;
    }

    @Override
    public void draw(){
        super.draw();
        if(!train)
            Renderer.drawImageRegion(Image.loadImage("textures/lander" + thrust + ".png"), this.x, this.y, 0, 0, 32, 32, Colors.WHITE.color, 90+this.angle);
    }

    @Override
    public void update(){
        super.update();
        if(train || ((MainScreen)this.getParent()).getState() == 0){
            velocity.add(new Vector2f((float) Math.cos(Math.toRadians(angle)) * thrust / 125f, (float) Math.sin(Math.toRadians(angle)) * thrust / 125f));
            velocity.add(new Vector2f(0, Terrain.GRAVITY));
            x += velocity.x;
            y += velocity.y;
            fuel -= thrust / 2f + (System.currentTimeMillis() % 15 == 0 ? 1 : 0);
//            System.out.println(String.format("%f, %f, %f, %f, %f", velocity.x, velocity.y, x, y, fuel));
        }
    }
}
