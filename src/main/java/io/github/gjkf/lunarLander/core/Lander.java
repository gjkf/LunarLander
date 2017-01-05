/*
 * Created by Davide Cossu (gjkf), 12/29/2016
 */

package io.github.gjkf.lunarLander.core;

import io.github.gjkf.seriousEngine.items.Item;
import io.github.gjkf.seriousEngine.loaders.obj.OBJLoader;
import io.github.gjkf.seriousEngine.render.Material;
import io.github.gjkf.seriousEngine.render.Texture;
import org.joml.Matrix3f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * This is the <tt>Lander</tt> class.
 * <p>It provides methods to update the texture, check collision with the terrain and more.</p>
 */

public class Lander extends Item{

    /**
     * The various materials for the mesh.
     */
    private Material[] icons;
    /**
     * The thrust level.
     */
    private int thrust;
    /**
     * The angle in degrees.
     */
    private int angle;
    /**
     * The velocity vector.
     */
    private Vector3f velocity;

    /**
     * Constructs a new Lander.
     *
     * @throws Exception If the model could not be loaded or textures found.
     */

    public Lander() throws Exception{
        super(OBJLoader.loadMesh("/engineModels/quad.obj"));
        velocity = new Vector3f(0);
        icons = new Material[] {
                new Material(new Texture("/textures/lander0.png"), 1),
                new Material(new Texture("/textures/lander1.png"), 1),
                new Material(new Texture("/textures/lander2.png"), 1),
                new Material(new Texture("/textures/lander3.png"), 1)
        };
        setIcon(0);
        angle = -90;
        // I need this because else it would be upside down.
        setRotation(new Quaternionf().setFromUnnormalized(new Matrix3f().rotateXYZ(0, 0, (float) Math.toRadians(270 + angle))));
    }

    /**
     * Updates the velocity and position of the lander.
     */

    public void update(){
        float factor = 500;
        float Vx = (float) -Math.cos(Math.toRadians(angle)) * thrust / (factor * 75f);
        float Vy = (float) Math.sin(Math.toRadians(-angle)) * thrust / (factor * 75f);
        velocity.add(new Vector3f(
                Vx,
                Vy,
                0f
        ));
        velocity.add(new Vector3f(0, -LunarTerrain.GRAVITY, 0));

        velocity.y = Math.max(-5e-2f, this.velocity.y);
        velocity.y = Math.min(5e-2f, this.velocity.y);
        velocity.x = Math.max(-5e-2f, this.velocity.x);
        velocity.x = Math.min(5e-2f, this.velocity.x);

        setPosition(getPosition().x + velocity.x, getPosition().y + velocity.y, getPosition().z + velocity.z);
    }

    /**
     * Sets the correct icon corresponding to the thrust level.
     *
     * @param thrust The thrust power. Cannot be lower than 0 and bigger than 3.
     */

    private void setIcon(int thrust){
        if(thrust >= 4 || thrust < 0)
            return;
        getMesh().setMaterial(icons[thrust]);
    }

    /**
     * Increases the thrust up to a maximum of 3.
     * <p>Then sets the new updated icon.</p>
     */

    public void increaseThrust(){
        thrust = thrust < 3 ? ++thrust : thrust;
        setIcon(thrust);
    }

    /**
     * Decreases the thrust down to a minimum of 0.
     * <p>Then sets the new updated icon.</p>
     */

    public void reduceThrust(){
        thrust = thrust > 0 ? --thrust : thrust;
        setIcon(thrust);
    }

    /**
     * Rotates the lander left by the given amount.
     *
     * @param value The angle in degrees.
     */

    public void rotateLeft(int value){
        angle += value;
        setRotation(new Quaternionf().setFromUnnormalized(new Matrix3f().rotateXYZ(0, 0, (float) Math.toRadians(270 + angle))));
    }

    /**
     * Rotates the lander right by the given amount.
     *
     * @param value The angle in degrees.
     */

    public void rotateRight(int value){
        angle -= value;
        setRotation(new Quaternionf().setFromUnnormalized(new Matrix3f().rotateXYZ(0, 0, (float) Math.toRadians(270 + angle))));
    }

}