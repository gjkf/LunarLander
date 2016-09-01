/*
 * Created by Davide Cossu (gjkf), 8/28/2016
 */
package com.gjkf.lunarLander.core.train;

import com.gjkf.lunarLander.core.gui.screens.TrainScreen;
import com.gjkf.lunarLander.core.terrain.Terrain;
import com.gjkf.seriousEngine.core.math.Vector2f;

import java.text.NumberFormat;

public class LanderSimulator {

    private static final double GRAVITY = Terrain.GRAVITY;// 1.62
    static final double TERMINAL_VELOCITY = 2;//40

    private int fuel;
    private int seconds;
    private double altitude;
    private Vector2f velocity;

    public LanderSimulator() {
        this.fuel = 2500;//200
        this.seconds = 0;
        this.altitude = TrainScreen.player.terrain.getPlayerAltitude(TrainScreen.player);//10000
        this.velocity = TrainScreen.player.getVelocity();
    }

    public void turn(int thrust){
        this.seconds++;
        this.velocity.y -= GRAVITY;
        this.altitude += this.velocity.y;

        if (thrust > 0 && this.fuel > 0) {
            this.fuel--;
            this.velocity.y += thrust/125f;
        }

        this.velocity.y = (float) Math.max(-TERMINAL_VELOCITY, this.velocity.y);
        this.velocity.y = (float) Math.min(TERMINAL_VELOCITY, this.velocity.y);

        if (this.altitude < 0)
            this.altitude = 0;

        TrainScreen.player.setFuel(fuel);
        TrainScreen.player.setVelocity(velocity);
    }

    public String telemetry() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(4);
        nf.setMaximumFractionDigits(4);
        return "Elapsed: " +
                seconds +
                " s, Fuel: " +
                this.fuel +
                " l, Velocity: " +
                nf.format(velocity.y) +
                " m/s, " +
                (int) altitude +
                " m";
    }

    public int score() {
        return (int) ((this.fuel * 10) + this.seconds + (this.velocity.y * 10000));
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
        return velocity.y;
    }

    public boolean flying() {
        return (this.altitude > 0);
    }
}