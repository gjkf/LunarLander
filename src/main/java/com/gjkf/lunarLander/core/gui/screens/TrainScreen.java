/*
 * Created by Davide Cossu (gjkf), 8/28/2016
 */
package com.gjkf.lunarLander.core.gui.screens;

import com.gjkf.lunarLander.core.train.NeuralPilot;
import com.gjkf.lunarLander.core.train.PilotScore;
import com.gjkf.seriousEngine.core.gui.GuiScreenWidget;
import com.gjkf.seriousEngine.core.render.Colors;
import com.gjkf.seriousEngine.core.render.Renderer;
import org.encog.Encog;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.ml.train.MLTrain;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.anneal.NeuralSimulatedAnnealing;
import org.encog.neural.pattern.FeedForwardPattern;

import static com.gjkf.lunarLander.core.gui.screens.TrainScreen.State.GEN_TRAINING;
import static com.gjkf.lunarLander.core.gui.screens.TrainScreen.State.TURN_TRAINING;

public class TrainScreen extends GuiScreenWidget{

    private static int epoch = 0;
    private static State state = GEN_TRAINING;

    private MLTrain train;
    private BasicNetwork network;

    private NeuralPilot pilot;

    public TrainScreen(float width, float height){
        super(width, height);
        network = createNetwork();
        train = new NeuralSimulatedAnnealing(network, new PilotScore(), 10, 2, 100);
        pilot = ((PilotScore)((NeuralSimulatedAnnealing)train).getCalculateScore()).getPilot();
    }

    @Override
    public void drawBackground(){
        System.out.println("State: " + state + " Turn: " + pilot.getTurn() + " SimFl: " + pilot.getSimulator().flying() + " Alt: " + pilot.getSimulator().getAltitude() + " Score: " + pilot.getSimulator().score());
//        System.err.println(this.getClass().toString() + " / CurrentThread: " + Thread.currentThread().getName() + " / TrainThread: " + thread.getName());
        switch(state){
            case RENDERING:
                Renderer.renderFont(200, 200, "Epoch: " + getEpoch(), 5, Colors.WHITE.color);
                Renderer.renderFont(200, 300, "Score: " + pilot.getSimulator().score(), 5, Colors.WHITE.color);
                Renderer.renderFont(200, 400, "Turn: " + pilot.getTurn(), 5, Colors.WHITE.color);
                state = TURN_TRAINING;
                break;
            case GEN_TRAINING:
                if(epoch < 20){
                    train.iteration();
                    System.err.println("Epoch #" + epoch + " Score:" + train.getError());
//                    epoch++;
                    state = TURN_TRAINING;
                }
                break;
            case TURN_TRAINING:
                pilot.scorePilot();
                System.out.println("TURN: " + pilot.getTurn());
                break;
        }
    }

    @Override
    public void drawForeground(){

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
        NeuralPilot pilot = new NeuralPilot(true);
        pilot.setNetwork(network);
        System.out.println(pilot.scorePilot());
        Encog.getInstance().shutdown();
    }

    public static State getState(){
        return state;
    }

    public static void setState(State state){
        TrainScreen.state = state;
    }

    public static void setEpoch(int epoch){
        TrainScreen.epoch = epoch;
    }

    public static int getEpoch(){
        return epoch;
    }

    public enum State{
        RENDERING, TURN_TRAINING, GEN_TRAINING
    }
}
