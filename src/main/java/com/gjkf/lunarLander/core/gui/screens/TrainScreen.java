/*
 * Created by Davide Cossu (gjkf), 8/28/2016
 */
package com.gjkf.lunarLander.core.gui.screens;

import com.gjkf.lunarLander.core.gui.player.Player;
import com.gjkf.lunarLander.core.terrain.Terrain;
import com.gjkf.lunarLander.core.train.NeuralPilot;
import com.gjkf.lunarLander.core.train.PilotScore;
import com.gjkf.seriousEngine.core.gui.GuiButton;
import com.gjkf.seriousEngine.core.gui.GuiScreenWidget;
import com.gjkf.seriousEngine.core.render.Renderer;
import org.encog.Encog;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.ml.train.MLTrain;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.anneal.NeuralSimulatedAnnealing;
import org.encog.neural.pattern.FeedForwardPattern;

public class TrainScreen extends GuiScreenWidget{

    public static Player player;

    private MLTrain train;
    private BasicNetwork network;

    /**
     * The screen constructor
     *
     * @param width  The width
     * @param height The height
     */

    public TrainScreen(float width, float height){
        super(width, height);
        Terrain terrain = new Terrain(0, 900, 1000, 200);
        terrain.generateTerrain(20);
        player = new Player((float) (Math.random() * 100f), 400, 32, 32, terrain, true);

        Renderer.setFont("fonts/ASO.ttf");
        add(new GuiButton(300, 300, "TRAIN", 40, ()-> System.out.println("Train")));
        add(player);
        add(terrain);
        network = createNetwork();
        startTrain();
        printTrain();
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

    private void startTrain(){
        train = new NeuralSimulatedAnnealing(network, new PilotScore(), 10, 2, 500);

        int epoch = 1;

        for(int i = 0; i < 20; i++) {
            train.iteration();
            System.out.println("Epoch #" + epoch + " Score:" + train.getError());
            epoch++;
        }
        train.finishTraining();
    }

    private void printTrain(){
        System.out.println("\nHow the winning network landed:");
        network = (BasicNetwork)train.getMethod();
        NeuralPilot pilot = new NeuralPilot(network, true);
        System.out.println(pilot.scorePilot());
        Encog.getInstance().shutdown();
    }

    @Override
    public void drawBackground(){

    }

    @Override
    public void drawForeground(){

    }

}
