/*
 * Created by Davide Cossu (gjkf), 8/18/2016
 */
package com.gjkf.lunarLander.core.terrain;

import com.gjkf.lunarLander.core.gui.player.Player;
import com.gjkf.seriousEngine.core.gui.GuiWidget;
import com.gjkf.seriousEngine.core.math.Vector2f;
import com.gjkf.seriousEngine.core.render.Colors;
import com.gjkf.seriousEngine.core.render.Renderer;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Random;

public class Terrain extends GuiWidget{

    public static final float gravity = 0.001f;

    private ArrayList<Vector2f> points, validPoints;

    private Random random;

    private boolean isValid;

    public Terrain(float x, float y, float width, float height){
        super(x, y, width, height, null);
        random = new Random();
    }

    /**
     * Generates a random series of points that will represent the lunar terrain
     *
     * @param incr The step
     */

    public void generateTerrain(int incr){
        this.validPoints = new ArrayList<>();
        this.points = new ArrayList<>();

        for(int x = (int)this.x; x < this.width+incr*3; x += incr){
            float h = random.nextInt((int) ((this.y - 800) + 1)) + 800;
            float noise = (float) (random.nextGaussian() * 0.1f);
            int y = (int) Math.round(h+noise*Math.sin(x)*100);
            points.add(new Vector2f(x, y));
        }
        this.checkForValidity();
    }

    /**
     * Checks if the generated terrain has at least 1 valid landing spot.
     */

    private void checkForValidity(){
        /* Place holder */
        ArrayList<Vector2f> c = new ArrayList<>();
        for(Vector2f point : this.points){
            /* I need 2 points to compare them. */
            if(c.size() < 2){
                c.add(point);
                continue;
            }
            /* Checks if the first point's y coordinate
             is the same as the second's one.*/
            if(c.get(0).y == c.get(1).y){
                if(c.get(0).x >= 30 && c.get(0).x <= width - 30){
                    if(c.get(1).x >= 30 && c.get(1).x <= width - 30){
                        this.validPoints.add(c.get(0));
                        this.validPoints.add(c.get(1));
                        this.isValid = true;
                    }
                }
            }else{
                ArrayList<Vector2f> t = new ArrayList<>();
                t.add(c.get(1));
                c = t;
            }
        }
        /* Keep filling the array until it's done. */
        if(!this.isValid){this.generateTerrain(20);}
    }

    /**
     * Checks the collision between the given player and the terrain
     *
     * @param player The player to check
     *
     * @return TRUE if the player collided
     */

    public boolean checkCollision(Player player){
        final boolean[] ret = {false};
        points.stream().filter(point -> point.x > player.x && point.x < player.x + player.width)
                .filter(point -> point.y <= player.y + player.height)
                .forEach(point -> {
                    /* Maybe is a valid point */
                    validPoints.stream().filter(validPoint -> point.x == validPoint.x && point.y == validPoint.y)
                            .filter(validPoint -> (player.getAngle() % 360 >= -82 && player.getAngle() % 360 <= -98 )|| (player.getAngle() % 360 >= 262 && player.getAngle() % 360 <= 278))
                            .filter(validPoint -> -player.getVelocity().y <= gravity * 10)
                            .forEach(validPoint ->{
                                System.err.println("LANDED");
                                ret[0] = true;
                            });
                    ret[0] = false;
                    System.err.println("NOT LANDED");
        });
        return ret[0];
    }

    @Override
    public void draw(){
        super.draw();
        if(this.isValid){
            GL11.glPushMatrix();
            ArrayList<Vector2f> c = new ArrayList<>();
            /* Drawing all the points. */
            for(Vector2f point : this.points){
                if(c.size() < 2){
                    c.add(point);
                    continue;
                }
                Renderer.drawLine(c.get(0).x, c.get(0).y, c.get(1).x, c.get(1).y, Colors.WHITE.color);
                ArrayList<Vector2f> t = new ArrayList<>();
                t.add(c.get(1));
                c = t;
            }
            c = new ArrayList<>();
            /* Drawing the valid points for landing. */
            for(Vector2f validPoint : this.validPoints){
                if(c.size() < 2){
                    c.add(validPoint);
                    continue;
                }
                Renderer.drawLine(c.get(0).x, c.get(0).y, c.get(1).x, c.get(0).y, Colors.RED.color);
                ArrayList<Vector2f> t = new ArrayList<>();
                t.add(c.get(1));
                c = t;
            }
            GL11.glPopMatrix();
        }else{
            this.checkForValidity();
        }
    }

}
