/*
 * Created by Davide Cossu (gjkf), 8/28/2016
 */
package com.gjkf.lunarLander.core.train;

import com.gjkf.lunarLander.core.gui.screens.TrainScreen;

import java.text.NumberFormat;

public class LanderSimulator{

    private static final double GRAVITY = 0.009;// 1.62
    static final double TERMINAL_VELOCITY = 2;//40

    private int fuel;
    private int seconds;
    private double altitude;
    private float velocity;

    public LanderSimulator() {
        this.fuel = 2500;//200
        this.seconds = 0;
        this.altitude = 6000;//10000
        this.velocity = 0;
    }

    public void turn(int thrust){
        this.seconds++;
        this.velocity -= GRAVITY;
        this.altitude += this.velocity;

        if (thrust > 0 && this.fuel > 0) {
            this.fuel--;
            this.velocity += thrust/125f;
        }

        this.velocity = (float) Math.max(-TERMINAL_VELOCITY, this.velocity);
        this.velocity = (float) Math.min(TERMINAL_VELOCITY, this.velocity);

        if (this.altitude < 0)
            this.altitude = 0;

        TrainScreen.setState(TrainScreen.State.RENDERING);
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
                nf.format(velocity) +
                " m/s, " +
                (int) altitude +
                " m";
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