/*
 * Created by Davide Cossu (gjkf), 8/28/2016
 */
package com.gjkf.lunarLander.core.gui.screens;

import com.gjkf.lunarLander.core.gui.player.Player;
import com.gjkf.lunarLander.core.terrain.Terrain;
import com.gjkf.lunarLander.core.train.NeuralPilot;
import com.gjkf.lunarLander.core.train.PilotScore;
import com.gjkf.seriousEngine.core.gui.GuiScreenWidget;
import org.encog.Encog;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.ml.train.MLTrain;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.anneal.NeuralSimulatedAnnealing;
import org.encog.neural.pattern.FeedForwardPattern;

import static com.gjkf.lunarLander.core.gui.screens.TrainScreen.State.GEN_TRAINING;
import static com.gjkf.lunarLander.core.gui.screens.TrainScreen.State.TURN_TRAINING;

/**
 * The screen where the neural network is trained and the player is drawn.
 */

public class TrainScreen extends GuiScreenWidget{

    /**
     * The current epoch of the neural network generation
     */
    private static int epoch = 0;
    /**
     * The player's altitude
     */
    private static float altitude = 0;
    /**
     * The current state. {@link State}
     */
    private static State state = GEN_TRAINING;
    /**
     * The train method
     */
    private MLTrain train;
    /**
     * The neural network
     */
    private BasicNetwork network;
    /**
     * The neural pilot
     */
    private NeuralPilot pilot;
    /**
     * The player object
     */
    private static Player player;
    /**
     * The terrain object
     */
    private Terrain terrain;

    public TrainScreen(float width, float height){
        super(width, height);
        terrain = new Terrain(0, 900, width, height-800);
        player = new Player(484, 200, 32, 32, terrain, true);
        terrain.generateTerrain(20);
        network = createNetwork();
        train = new NeuralSimulatedAnnealing(network, new PilotScore(), 10, 2, 100);
        pilot = ((PilotScore)((NeuralSimulatedAnnealing)train).getCalculateScore()).getPilot();
        altitude = player.terrain.getPlayerAltitude(player);
    }

    @Override
    public void drawBackground(){
        System.out.println(String.format("State: %s - Turn: %d - Epoch: %d - Flying: %b - Score: %d",
                state,
                pilot.getTurn(),
                epoch,
                pilot.getSimulator().flying(),
                pilot.getSimulator().score()));
        switch(state){
            // If the current state is rendering, then update the player info
            case RENDERING:
                player.x += player.getVelocity().x;
                player.y += player.getVelocity().y;
                System.err.println(String.format("V: %f / VEL: %f - ALT: %f / Alt: %f / DIFF: %f",
                        player.getVelocity().y,
                        pilot.getSimulator().getVelocity(),
                        pilot.getSimulator().getAltitude(),
                        player.terrain.getPlayerAltitude(player),
                        (pilot.getSimulator().getAltitude()-player.terrain.getPlayerAltitude(player))));
                // Then make the state back to TURN_TRAINING
                state = TURN_TRAINING;
                break;
            // If the current state is generation, then create a new one
            case GEN_TRAINING:
                if(epoch < 20){
                    train.iteration();
                    System.err.println("Epoch #" + epoch + " Score:" + train.getError());
                    // Then make the state to TURN_TRAINING
                    state = TURN_TRAINING;
                }
                break;
            // If the current state is turn training, then train the neural network
            case TURN_TRAINING:
                pilot.scorePilot();
                break;
        }
        player.draw();
        terrain.draw();
    }

    @Override
    public void drawForeground(){

    }

    /**
     * Generates a new network
     *
     * @return The newly created network
     */

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

    /**
     * @return The current state
     */

    public static State getState(){
        return state;
    }

    /**
     * Sets the state to the param
     *
     * @param state The new state
     */

    public static void setState(State state){
        TrainScreen.state = state;
    }

    /**
     * Sets the epoch to the param
     *
     * @param epoch The new epoch
     */

    public static void setEpoch(int epoch){
        TrainScreen.epoch = epoch;
    }

    /**
     * @return The current epoch
     */

    public static int getEpoch(){
        return epoch;
    }

    /**
     * @return The current player
     */

    public static Player getPlayer(){
        return player;
    }

    public static float getAltitude(){
        return altitude;
    }

    /**
     * Enum to store the current state of generation
     */

    public enum State{
        /**
         * Render the new turn
         */
        RENDERING,
        /**
         * Calculate the new turn
         */
        TURN_TRAINING,
        /**
         * Start a new generation
         */
        GEN_TRAINING
    }
}
