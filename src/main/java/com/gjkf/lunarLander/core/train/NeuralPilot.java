/*
 * Created by Davide Cossu (gjkf), 8/28/2016
 */
package com.gjkf.lunarLander.core.train;

import com.gjkf.lunarLander.core.gui.screens.TrainScreen;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;

/**
 * The pilot that hypothetically should "drive" the Lunar Lander
 */
public class NeuralPilot {

    /**
     * Whether or not this is used to track the operations
     */
    private boolean track;

    /**
     * The current turn
     */
    private int turn = 0;

    /**
     * The network to work with
     */
    private BasicNetwork network;
    /**
     * The fuel stats
     */
    private NormalizedField fuelStats;
    /**
     * The altitude stats
     */
    private NormalizedField altitudeStats;
    /**
     * The velocity stats
     */
    private NormalizedField velocityStats;

    /**
     * The {@link LanderSimulator} object
     */
    private LanderSimulator sim;

    public NeuralPilot(boolean track){
        fuelStats = new NormalizedField(NormalizationAction.Normalize, "fuel", 2500, 0, -0.9, 0.9);//200
        altitudeStats = new NormalizedField(NormalizationAction.Normalize, "altitude", 6000, 0, -0.9, 0.9);//10000
        velocityStats = new NormalizedField(NormalizationAction.Normalize, "velocity", LanderSimulator.TERMINAL_VELOCITY, -LanderSimulator.TERMINAL_VELOCITY, -0.9, 0.9);

        this.track = track;
        sim = new LanderSimulator();
    }

    /**
     * Will run the {@link LanderSimulator#turn(int)} method and will return the {@link LanderSimulator#score()}
     *
     * @return The current turn score
     */

    public int scorePilot(){
        if(sim.flying()){
            if(!TrainScreen.getState().equals(TrainScreen.State.RENDERING)){
                // Process the data and create the new value from it
                MLData input = new BasicMLData(3);
                input.setData(0, this.fuelStats.normalize(sim.getFuel()));
                input.setData(1, this.altitudeStats.normalize(sim.getAltitude()));
                input.setData(2, this.velocityStats.normalize(sim.getVelocity()));
                MLData output = this.network.compute(input);
                double value = output.getData(0);

//            if(!track) System.out.println(String.format("VEL: %f, ALT: %f, DATA: %f, SCO: %d", sim.getVelocity(),sim.getAltitude(), value, sim.score()));

                int thrust;
                switch((int) ((Math.abs(value) * 10) % 3)){
                    case 0:
                        thrust = 0;
                        break;
                    case 1:
                        thrust = 1;
                        break;
                    case 2:
                        thrust = 2;
                        break;
                    case 3:
                        thrust = 3;
                        break;
                    default:
                        thrust = 0;
                        break;
                }

                if(thrust != 0 && track)
                    System.out.println("THRUST: " + thrust);


                sim.turn(thrust);
                turn++;
                if(track)
                    System.out.println(sim.telemetry());
            }
        }else{
            TrainScreen.setState(TrainScreen.State.GEN_TRAINING);
            TrainScreen.setEpoch(TrainScreen.getEpoch()+1);
            sim = new LanderSimulator();
            turn = 0;
        }
        return sim.score();
    }

    public LanderSimulator getSimulator(){
        return sim;
    }

    public int getTurn(){
        return turn;
    }

    public void setNetwork(BasicNetwork network){
        this.network = network;
    }

}