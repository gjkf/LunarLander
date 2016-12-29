///*
// * Created by Davide Cossu (gjkf), 8/28/2016
// */
//package io.github.gjkf.lunarLander.core.train;
//
//import io.github.gjkf.seriousEngine.items.Terrain;
//import org.joml.Vector2f;
//
//import java.math.RoundingMode;
//import java.text.DecimalFormat;
//import java.text.NumberFormat;
//
///**
// * This class represents the simulator that simulates the lander flying
// */
//
//public class LanderSimulator{
//
//    /**
//     * The gravity that should apply
//     */
//    private static final double GRAVITY = Terrain.GRAVITY;
//    /**
//     * The terminal velocity of the spaceship
//     */
//    static final double TERMINAL_VELOCITY = 2;
//
//    /**
//     * The current amount of fuel
//     */
//    private int fuel;
//    /**
//     * The amount of seconds passed from the beginning of the generation
//     */
//    private int seconds;
//    /**
//     * The altitude of the spaceship
//     */
//    private float altitude;
//    /**
//     * The current velocity of the spaceship
//     */
//    private float velocity;
//    /**
//     * The current thrust power
//     */
//    private int thrust = 0;
//
//    public LanderSimulator() {
//        this.fuel = 2500;
//        this.seconds = 0;
//        this.altitude = TrainScreen.getAltitude();
//        this.velocity = 0;
//    }
//
//    /**
//     * Recalculates the {@link LanderSimulator#velocity}, {@link LanderSimulator#altitude} and {@link LanderSimulator#fuel}
//     * of the current generation
//     *
//     * @param thrust How powerful the thrust is
//     */
//
//    public void turn(int thrust){
//        this.seconds++;
//        this.velocity -= GRAVITY;
//        this.altitude -= this.velocity;
//        this.thrust = thrust;
//
//        if (this.thrust > 0 && this.fuel > 0) {
//            this.fuel--;
//            this.velocity += this.thrust/125f;
//        }
//
//        this.velocity = (float) Math.max(-TERMINAL_VELOCITY, this.velocity);
//        this.velocity = (float) Math.min(TERMINAL_VELOCITY, this.velocity);
//
//        if (this.altitude < 0)
//            this.altitude = 0;
//
//        DecimalFormat df = new DecimalFormat("#.####");
//        df.setRoundingMode(RoundingMode.CEILING);
//        this.velocity = Float.parseFloat(df.format(this.velocity));
//
//        df = new DecimalFormat("####.##");
//        df.setRoundingMode(RoundingMode.CEILING);
//        this.altitude = Float.parseFloat(df.format(this.altitude));
//
//        TrainScreen.setState(TrainScreen.State.RENDERING);
//        TrainScreen.getPlayer().setFuel(this.fuel);
//        TrainScreen.getPlayer().setThrust(this.thrust);
//        TrainScreen.getPlayer().setVelocity(new Vector2f(0, this.velocity));
//    }
//
//    /**
//     * Formats all the information in a nice way
//     *
//     * @return The newly formatted string
//     */
//
//    public String telemetry() {
//        NumberFormat nf = NumberFormat.getNumberInstance();
//        nf.setMinimumFractionDigits(4);
//        nf.setMaximumFractionDigits(4);
//        return "Elapsed: " +
//                seconds +
//                " s, Fuel: " +
//                this.fuel +
//                " l, Velocity: " +
//                nf.format(velocity) +
//                " m/s, " +
//                (int) altitude +
//                " m";
//    }
//
//    /**
//     * Perhaps the most important method, it calculates the score for the current turn
//     *
//     * @return The current score
//     */
//
//    public int score() {
//        return (int) ((this.fuel * 10) + this.seconds + (this.velocity * 10000));
//    }
//
//    public int getFuel() {
//        return fuel;
//    }
//
//    public int getSeconds() {
//        return seconds;
//    }
//
//    public float getAltitude() {
//        return altitude;
//    }
//
//    public float getVelocity() {
//        return velocity;
//    }
//
//    public boolean flying() {
//        return (this.altitude > 0);
//    }
//
//    public int getThrust(){
//        return thrust;
//    }
//}