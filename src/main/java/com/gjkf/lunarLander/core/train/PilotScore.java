/*
 * Created by Davide Cossu (gjkf), 8/28/2016
 */
package com.gjkf.lunarLander.core.train;

import org.encog.ml.CalculateScore;
import org.encog.ml.MLMethod;
import org.encog.neural.networks.BasicNetwork;

public class PilotScore implements CalculateScore{

    public NeuralPilot pilot;

    @Override
    public double calculateScore(MLMethod network) {
        NeuralPilot pilot = new NeuralPilot((BasicNetwork)network, false);
        return pilot.scorePilot();
    }

    @Override
    public boolean shouldMinimize() {
        return false;
    }

    @Override
    public boolean requireSingleThreaded() {
        return false;
    }

    public NeuralPilot getPilot(){
        return pilot;
    }
}
