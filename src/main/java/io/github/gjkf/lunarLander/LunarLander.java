/*
 * Created by Davide Cossu (gjkf), 8/11/2016
 */
package io.github.gjkf.lunarLander;

import io.github.gjkf.lunarLander.core.Game;
import io.github.gjkf.seriousEngine.Engine;
import io.github.gjkf.seriousEngine.ILogic;
import io.github.gjkf.seriousEngine.SharedLibraryLoader;
import io.github.gjkf.seriousEngine.Window;

public class LunarLander{

    public static void main(String... args){
        SharedLibraryLoader.load();
        String os = System.getProperty("os.name").toLowerCase();
        /* Mac OS X needs headless mode for AWT */
        if(os.contains("mac")){
            System.setProperty("java.awt.headless", "true");
        }
        try{
            boolean vSync = true;
            ILogic gameLogic = new Game();
            Window.WindowOptions opts = new Window.WindowOptions();
            opts.cullFace = true;
            opts.showFps = true;
            Engine gameEng = new Engine("Lunar Lander", 800, 800, vSync, opts, gameLogic);
            gameEng.start();
        }catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
