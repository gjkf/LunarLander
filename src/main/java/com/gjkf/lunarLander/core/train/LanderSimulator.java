/*
 * Created by Davide Cossu (gjkf), 8/28/2016
 */
package com.gjkf.lunarLander.core.train;

import com.gjkf.lunarLander.core.gui.player.Player;
import com.gjkf.lunarLander.core.gui.screens.TrainScreen;
import com.gjkf.seriousEngine.core.math.Vector2f;

public class LanderSimulator {

    public static final float TERMINAL_VELOCITY = 2;

    private float fuel;
    private double altitude;

    private Vector2f velocity;
    public Player player;

    public LanderSimulator() {
        player = TrainScreen.player;
        this.fuel = player.getFuel();
        this.altitude = player.terrain.getPlayerAltitude(player);
        this.velocity = player.getVelocity();
    }

    public void turn() {
        player.update();
        this.velocity = player.getVelocity();
        this.altitude = player.terrain.getPlayerAltitude(player);
        this.fuel = player.getFuel();
    }

    public int score() {
        velocity.scale(500);
        return (int) (velocity.length() + fuel);
    }

    public float getFuel() {
        return fuel;
    }

    public double getAltitude() {
        return altitude;
    }

    public boolean flying() {
        return player.terrain.getPlayerAltitude(player) > 0;
    }

    public Vector2f getVelocity(){
        return velocity;
    }
}