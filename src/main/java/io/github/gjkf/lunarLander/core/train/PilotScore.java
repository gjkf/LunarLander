///*
// * Created by Davide Cossu (gjkf), 8/28/2016
// */
//package io.github.gjkf.lunarLander.core.train;
//
//import org.encog.ml.CalculateScore;
//import org.encog.ml.MLMethod;
//import org.encog.neural.networks.BasicNetwork;
//
///**
// * The pilot used to determine the ANN score
// */
//
//public class PilotScore implements CalculateScore{
//
//    /**
//     * The {@link NeuralPilot} object
//     */
//    private NeuralPilot pilot = new NeuralPilot(false);
//
//    @Override
//    public double calculateScore(MLMethod network) {
////        pilot = new NeuralPilot(false);
//        pilot.setNetwork((BasicNetwork) network);
//        return pilot.scorePilot();
//    }
//
//    @Override
//    public boolean shouldMinimize() {
//        return false;
//    }
//
//    @Override
//    public boolean requireSingleThreaded() {
//        return false;
//    }
//
//    public NeuralPilot getPilot(){
//        return pilot;
//    }
//}
