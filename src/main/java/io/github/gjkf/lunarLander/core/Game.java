/*
 * Created by Davide Cossu (gjkf), 12/29/2016
 */

package io.github.gjkf.lunarLander.core;

import io.github.gjkf.seriousEngine.ILogic;
import io.github.gjkf.seriousEngine.MouseInput;
import io.github.gjkf.seriousEngine.Window;
import io.github.gjkf.seriousEngine.items.Item;
import io.github.gjkf.seriousEngine.render.Camera;
import io.github.gjkf.seriousEngine.render.Colors;
import io.github.gjkf.seriousEngine.render.Renderer;
import io.github.gjkf.seriousEngine.render.Scene;
import io.github.gjkf.seriousEngine.render.lights.DirectionalLight;
import io.github.gjkf.seriousEngine.render.lights.SceneLight;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Game implements ILogic{

    private Renderer renderer;
    private Camera camera;

    private Scene scene;
    private Lander lander;
    private LunarTerrain terrain;

    public Game(){
        this.renderer = new Renderer();
        camera = new Camera();
    }

    @Override
    public void init(Window window) throws Exception{
        renderer.init(window);

        scene = new Scene();
        setupLights();

        lander = new Lander();
        lander.setPosition(0, 1, -4);
        lander.setScale(0.05f);

        terrain = new LunarTerrain(1000, 1000);
        terrain.setPosition(-.1f, 0, -5);
        terrain.setScale(3f);

        scene.setItems(new Item[] {
                lander,
                terrain
        });
    }

    private void setupLights(){
        SceneLight sceneLight = new SceneLight();
        scene.setSceneLight(sceneLight);

        // Ambient Light
        sceneLight.setAmbientLight(new Vector3f(0.3f, 0.3f, 0.3f));
        sceneLight.setSkyBoxLight(new Vector3f(1.0f, 1.0f, 1.0f));

        // Directional Light
        float lightIntensity = 1.0f;
        Vector3f lightDirection = new Vector3f(0, 0, 1);
        DirectionalLight directionalLight = new DirectionalLight(Colors.WHITE.toVector(), lightDirection, lightIntensity);
        directionalLight.setShadowPosMult(10);
        directionalLight.setOrthoCords(-10.0f, 10.0f, -10.0f, 10.0f, -1.0f, 20.0f);
        sceneLight.setDirectionalLight(directionalLight);
    }

    @Override
    public void input(Window window, MouseInput mouseInput){
        if(window.isKeyPressed(GLFW.GLFW_KEY_UP)){
            lander.increaseThrust();
        }else if(window.isKeyPressed(GLFW.GLFW_KEY_DOWN)){
            lander.reduceThrust();
        }

        if(window.isKeyPressed(GLFW.GLFW_KEY_LEFT)){
            lander.rotateLeft(1);
        }else if(window.isKeyPressed(GLFW.GLFW_KEY_RIGHT)){
            lander.rotateRight(1);
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput){
//         Update camera based on mouse
        if(mouseInput.isLeftButtonPressed()){
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * 0.2f, rotVec.y * 0.2f, 0);
        }
        lander.update();
    }

    @Override
    public void render(Window window){
        renderer.render(window, camera, scene, null);
    }

    @Override
    public void cleanup(){
        renderer.cleanup();
        scene.cleanup();
        try{
            terrain.deleteImage();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}