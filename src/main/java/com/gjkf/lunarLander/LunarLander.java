/*
 * Created by Davide Cossu (gjkf), 8/11/2016
 */
package com.gjkf.lunarLander;

import com.gjkf.lunarLander.core.gui.screens.TrainScreen;
import com.gjkf.lunarLander.core.train.NeuralPilot;
import com.gjkf.lunarLander.core.train.PilotScore;
import org.encog.Encog;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.ml.train.MLTrain;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.anneal.NeuralSimulatedAnnealing;
import org.encog.neural.pattern.FeedForwardPattern;

public class LunarLander{

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

    public static void main(String... args){
//        SeriousEngine engine = new SeriousEngine();
//
//        Window w = new Window(1000, 1000, "Lunar Lander");
//        w.setScreen(new MenuScreen(1000, 1000));
//
//        engine.setWindow(w);
//        engine.run();

        new TrainScreen(1000, 1000);
        BasicNetwork network = createNetwork();

//        MLTrain train = new MLMethodGeneticAlgorithm(() -> {
//            final BasicNetwork result = createNetwork();
//            result.reset();
//            return result;
//        },new PilotScore(), (int) 1e3);

        MLTrain train = new NeuralSimulatedAnnealing(network, new PilotScore(), 10, 2, 1000);

        int epoch = 1;

        for(int i = 0; i < 200; i++) {
            train.iteration();
            System.out.println("Epoch #" + epoch + " Score:" + train.getError());
            epoch++;
        }
        train.finishTraining();

        System.out.println("\nHow the winning network landed:");
        network = (BasicNetwork)train.getMethod();
        NeuralPilot pilot = new NeuralPilot(network, true);
        System.out.println(pilot.scorePilot());
        Encog.getInstance().shutdown();
    }

}
