/*
 * Created by Davide Cossu (gjkf), 8/28/2016
 */
package com.gjkf.lunarLander.core.train;

import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;

public class NeuralPilot {

    private boolean track;

    private BasicNetwork network;
    private NormalizedField fuelStats;
    private NormalizedField altitudeStats;
    private NormalizedField velocityStats;

    public NeuralPilot(BasicNetwork network, boolean track){
        fuelStats = new NormalizedField(NormalizationAction.Normalize, "fuel", 2500, 0, -0.9, 0.9);//200
        altitudeStats = new NormalizedField(NormalizationAction.Normalize, "altitude", 10000, 0, -0.9, 0.9);//10000
        velocityStats = new NormalizedField(NormalizationAction.Normalize, "velocity", LanderSimulator.TERMINAL_VELOCITY, -LanderSimulator.TERMINAL_VELOCITY, -0.9, 0.9);

        this.track = track;
        this.network = network;
    }

    public int scorePilot(){
        LanderSimulator sim = new LanderSimulator();
        while(sim.flying()){
            MLData input = new BasicMLData(3);
            input.setData(0, this.fuelStats.normalize(sim.getFuel()));
            input.setData(1, this.altitudeStats.normalize(sim.getAltitude()));
            input.setData(2, this.velocityStats.normalize(sim.getVelocity()));
            MLData output = this.network.compute(input);
            double value = output.getData(0);

//            if(!track) System.out.println(String.format("VEL: %f, ALT: %f, DATA: %f, SCO: %d", sim.getVelocity(),sim.getAltitude(), value, sim.score()));

            int t;
            switch((int)((Math.abs(value)*10) % 3)){
                case 0:
                    t = 0;
                    break;
                case 1:
                    t = 1;
                    break;
                case 2:
                    t = 2;
                    break;
                case 3:
                    t = 3;
                    break;
                default:
                    t = 0;
                    break;
            }

            if(t != 0 && track)
                System.out.println("THRUST: " + t);

            sim.turn(t);

            if(track)
                System.out.println(sim.telemetry());
        }
        return(sim.score());
    }

}