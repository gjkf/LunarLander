/*
 * Created by Davide Cossu (gjkf), 8/28/2016
 */
package com.gjkf.lunarLander.core.train;

import com.gjkf.lunarLander.core.gui.screens.TrainScreen;
import com.gjkf.lunarLander.core.terrain.Terrain;

import java.text.NumberFormat;

public class LanderSimulator {

    public static final double GRAVITY = Terrain.GRAVITY;// 1.62
    public static final double THRUST = 2/125f;//10
    public static final double TERMINAL_VELOCITY = 2;//40

    private int fuel;
    private int seconds;
    private double altitude;
    private double velocity;

    public LanderSimulator() {
        this.fuel = 2500;//200
        this.seconds = 0;
        this.altitude = TrainScreen.player.terrain.getPlayerAltitude(TrainScreen.player);//10000
        this.velocity = TrainScreen.player.getVelocity().y;
    }

    public void turn(int thrust) {
        this.seconds++;
        this.velocity -= GRAVITY;
        this.altitude += this.velocity;

        if (thrust > 0 && this.fuel > 0) {
            this.fuel--;
            this.velocity += thrust/125f;
        }

        this.velocity = Math.max(-TERMINAL_VELOCITY, this.velocity);
        this.velocity = Math.min(TERMINAL_VELOCITY, this.velocity);

        if (this.altitude < 0)
            this.altitude = 0;
    }

    public String telemetry() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(4);
        nf.setMaximumFractionDigits(4);
        StringBuilder result = new StringBuilder();
        result.append("Elapsed: ");
        result.append(seconds);
        result.append(" s, Fuel: ");
        result.append(this.fuel);
        result.append(" l, Velocity: ");
        result.append(nf.format(velocity));
        result.append(" m/s, ");
        result.append((int) altitude);
        result.append(" m");
        return result.toString();
    }

    public int score() {
        return (int) ((this.fuel * 10) + this.seconds + (this.velocity * 10000));
    }

    public int getFuel() {
        return fuel;
    }

    public int getSeconds() {
        return seconds;
    }

    public double getAltitude() {
        return altitude;
    }

    public double getVelocity() {
        return velocity;
    }

    public boolean flying() {
        return (this.altitude > 0);
    }
}


//public class LanderSimulator {
//
//    public static final float TERMINAL_VELOCITY = 2;
//
//    private float fuel;
//    private double altitude;
//
//    private Vector2f velocity;
//    public Player player;
//
//    public LanderSimulator() {
//        player = TrainScreen.player;
//        this.fuel = player.getFuel();
//        this.altitude = player.terrain.getPlayerAltitude(player);
//        this.velocity = player.getVelocity();
//    }
//
//    public void turn() {
//        player.update();
//        this.velocity = player.getVelocity();
//        this.altitude = player.terrain.getPlayerAltitude(player);
//        this.fuel = player.getFuel();
//    }
//
//    public int score() {
////        velocity.scale(1000);
////        return (int) (fuel*10 - velocity.y*10000 - altitude*10);
//        return (int) (fuel*10 + velocity.y*10000);
//    }
//
//    public float getFuel() {
//        return fuel;
//    }
//
//    public double getAltitude() {
//        return altitude;
//    }
//
//    public boolean flying() {
//        return player.terrain.getPlayerAltitude(player) > 0 && player.terrain.getPlayerAltitude(player) < 1500;
//    }
//
//    public Vector2f getVelocity(){
//        return velocity;
//    }
//}