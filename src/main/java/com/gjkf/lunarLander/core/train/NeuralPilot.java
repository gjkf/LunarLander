/*
 * Created by Davide Cossu (gjkf), 8/28/2016
 */
package com.gjkf.lunarLander.core.train;

import com.gjkf.lunarLander.core.gui.player.Player;
import com.gjkf.lunarLander.core.gui.screens.TrainScreen;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;

public class NeuralPilot {

    private BasicNetwork network;
    private NormalizedField fuelStats;
    private NormalizedField altitudeStats;
    private NormalizedField velocityStats;

    private Player player;

    public NeuralPilot(BasicNetwork network){
        this.player = TrainScreen.player;
        fuelStats = new NormalizedField(NormalizationAction.Normalize, "fuel", 2500, 0, -0.9, 0.9);
        altitudeStats = new NormalizedField(NormalizationAction.Normalize, "altitude", player.y, 0, -0.9, 0.9);
        velocityStats = new NormalizedField(NormalizationAction.Normalize, "velocity", LanderSimulator.TERMINAL_VELOCITY, -LanderSimulator.TERMINAL_VELOCITY, -0.9, 0.9);
        this.network = network;
    }

    public int scorePilot(){
        LanderSimulator sim = new LanderSimulator();
        while(sim.flying()){
            MLData input = new BasicMLData(3);
            input.setData(0, this.fuelStats.normalize(sim.getFuel()));
            input.setData(1, this.altitudeStats.normalize(sim.getAltitude()));
            input.setData(2, this.velocityStats.normalize(sim.getVelocity().y));
//            input.setData(3, this.velocityStats.normalize(sim.getVelocity().y));
            MLData output = this.network.compute(input);
            double value = output.getData(0);

//            System.out.println(String.format("VEL: %f, ALT: %f, DATA: %f, THR: %d", player.getVelocity().y, player.terrain.getPlayerAltitude(player), value, player.getThrust()));

            boolean t;

            t = value > 0;

            if(t) sim.player.addThrust(); else sim.player.removeThrust();

            sim.turn();
        }
        return(sim.score());
    }
}