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

public class NeuralPilot {

    private BasicNetwork network;
    private boolean track;
    private NormalizedField fuelStats;
    private NormalizedField altitudeStats;
    private NormalizedField velocityStats;

    public NeuralPilot(BasicNetwork network, boolean track){
        fuelStats = new NormalizedField(NormalizationAction.Normalize, "fuel", 2500, 0, -0.9, 0.9);//200
        altitudeStats = new NormalizedField(NormalizationAction.Normalize, "altitude", TrainScreen.player.terrain.getPlayerAltitude(TrainScreen.player), 0, -0.9, 0.9);//10000
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

//            System.err.println((int)((value*10)%3));

            boolean thrust;
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
//            if( value > 0 ){
//                thrust = true;
//                if( track )
//                    System.out.println("THRUST");
//            }else
//                thrust = false;

            if(t != 0 && track)
                System.out.println("THRUST: " + t);

            sim.turn(t);
            if(track)
                System.out.println(sim.telemetry());
        }
        return(sim.score());
    }
}

//public class NeuralPilot {
//
//    private BasicNetwork network;
//    private NormalizedField fuelStats;
//    private NormalizedField altitudeStats;
//    private NormalizedField velocityStats;
//
//    private Player player;
//
//    public NeuralPilot(BasicNetwork network){
//        this.player = TrainScreen.player;
//        fuelStats = new NormalizedField(NormalizationAction.Normalize, "fuel", 2500, 0, -0.9, 0.9);
//        altitudeStats = new NormalizedField(NormalizationAction.Normalize, "altitude", player.y, 0, -0.9, 0.9);
//        velocityStats = new NormalizedField(NormalizationAction.Normalize, "velocity", LanderSimulator.TERMINAL_VELOCITY, -LanderSimulator.TERMINAL_VELOCITY, -0.9, 0.9);
//        this.network = network;
//    }
//
//    public int scorePilot(){
//        LanderSimulator sim = new LanderSimulator();
//        while(sim.flying()){
//            MLData input = new BasicMLData(3);
//            input.setData(0, this.fuelStats.normalize(sim.getFuel()));
//            input.setData(1, this.altitudeStats.normalize(sim.getAltitude()));
//            input.setData(2, this.velocityStats.normalize(sim.getVelocity().y));
//            MLData output = this.network.compute(input);
//            double value = output.getData(0);
//
////            System.out.println(String.format("VEL: %f, ALT: %f, FUEL: %f, THR: %d, SCORE: %d", player.getVelocity().y, player.terrain.getPlayerAltitude(player), sim.getFuel(), player.getThrust(), sim.score()));
//
//            boolean t;
//
//            t = value > 0;
//
////            if(t) sim.player.addThrust(); else sim.player.removeThrust();
//            if(t) sim.player.setThrust(2); else sim.player.setThrust(0);
//
//            if(-player.getVelocity().y >= Terrain.GRAVITY * 10 && player.terrain.getPlayerAltitude(player) < 100){
//                break;
//            }
//
//            sim.turn();
//        }
//        return(sim.score());
//    }
//}