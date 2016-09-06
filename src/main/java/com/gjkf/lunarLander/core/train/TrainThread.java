package com.gjkf.lunarLander.core.train;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.ml.train.MLTrain;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.anneal.NeuralSimulatedAnnealing;
import org.encog.neural.pattern.FeedForwardPattern;

import java.util.HashMap;

public class TrainThread extends Thread{

    private int epoch = 0, turn = 0;

    private HashMap<Integer, Double> scoreMap = new HashMap<>();

    private MLTrain train;
    private BasicNetwork network;

    public TrainThread(){
        super("TrainThread");
        network = createNetwork();
        train = new NeuralSimulatedAnnealing(network, new PilotScore(), 10, 2, 100);
        start();
    }

    @Override
    public void run(){
        super.run();
        System.err.println(this.getClass().toString() + " / CurrentThread: " + Thread.currentThread().getName() + " / TrainThread: " + getName());
        if(epoch < 20){
            train.iteration();
//            turn = ((PilotScore)((NeuralSimulatedAnnealing)train).getCalculateScore()).getPilot().getTurn();
            System.out.println("Epoch #" + epoch + " Score:" + train.getError());
            epoch++;
            scoreMap.put(epoch, train.getError());
        }else{
            train.finishTraining();
        }
    }

    private static BasicNetwork createNetwork(){
        FeedForwardPattern pattern = new FeedForwardPattern();
        pattern.setInputNeurons(3);
        pattern.addHiddenLayer(50);
        pattern.setOutputNeurons(1);
        pattern.setActivationFunction(new ActivationTANH());
        BasicNetwork network = (BasicNetwork)pattern.generate();
        network.reset();
        return network;
    }

    private void printTrain(){
        System.out.println("\nHow the winning network landed:");
        network = (BasicNetwork)train.getMethod();
        NeuralPilot pilot = new NeuralPilot(network, true);
        System.out.println(pilot.scorePilot());
        Encog.getInstance().shutdown();
    }

    public int getEpoch(){
        return epoch;
    }

    public double getScore(int epoch){
        return scoreMap.get(epoch);
    }

    public int getTurn(){
        return turn;
    }

    public void setTurn(int turn){
        this.turn = turn;
    }

}
