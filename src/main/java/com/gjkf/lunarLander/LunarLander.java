/*
 * Created by Davide Cossu (gjkf), 8/11/2016
 */
package com.gjkf.lunarLander;

import com.gjkf.lunarLander.core.gui.screens.TrainScreen;
import com.gjkf.seriousEngine.SeriousEngine;
import com.gjkf.seriousEngine.core.gui.Window;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.neural.networks.BasicNetwork;
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
        SeriousEngine engine = new SeriousEngine();

        Window w = new Window(1000, 1000, "Lunar Lander");
        w.setScreen(new TrainScreen(1000, 1000));

        engine.setWindow(w);
        engine.run();

//        new TrainScreen(1000, 1000);
    }

}
