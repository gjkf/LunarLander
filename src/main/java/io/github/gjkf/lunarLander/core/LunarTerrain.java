/*
 * Created by Davide Cossu (gjkf), 12/30/2016
 */

package io.github.gjkf.lunarLander.core;

import io.github.gjkf.seriousEngine.items.Item;
import io.github.gjkf.seriousEngine.loaders.obj.OBJLoader;
import io.github.gjkf.seriousEngine.render.Material;
import io.github.gjkf.seriousEngine.render.Texture;
import org.joml.Matrix3f;
import org.joml.Quaternionf;
import org.joml.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class LunarTerrain extends Item{

    /**
     * The gravity force to apply to all items.
     */
    public static final float GRAVITY = 1e-5f;
    /**
     * The image file for the texture.
     */
    private final File imageOutput = new File(System.getProperty("user.home"), "lunarTerrain.png");
    /**
     * The {@link Random} object used to create a pseudo-random terrain.
     */
    private Random random;
    /**
     * Vector representing the terrain.
     */
    private ArrayList<Vector2f> points;
    /**
     * The valid points where you can land.
     */
    private ArrayList<ArrayList<Vector2f>> validPoints;
    /**
     * Whether or not the current terrain is suitable for a game.
     */
    private boolean isValid;
    /**
     * The dimension of the terrain.
     */
    private float width, height;
    /**
     * The step of the generation algorithm.
     */
    private int inc = 10;

    public LunarTerrain(float width, float height) throws Exception{
        super(OBJLoader.loadMesh("/engineModels/quad.obj"));
        this.width = width;
        this.height = height;
        // I use the current nano time so that each time the program is executed I guarantee the most randomness possible.
        random = new Random(System.nanoTime());

        generateTerrain(inc);

        createTerrainImage(points, validPoints);

        getMesh().setMaterial(new Material(new Texture(new FileInputStream(imageOutput)), 1));
        setRotation(new Quaternionf().setFromUnnormalized(new Matrix3f().rotateXYZ(0, 0, (float) Math.toRadians(180))));
    }

    /**
     * Generates a random series of points that will represent the lunar terrain.
     *
     * @param increase The step
     */

    private void generateTerrain(int increase){
        validPoints = new ArrayList<>();
        points = new ArrayList<>();

        for(float x = 0; x < this.width; x += increase){
            float noise = (float) (random.nextGaussian() * 0.1f);
            int y = (int) Math.abs(height / 2 + noise * Math.sin(x) * 150);
            points.add(new Vector2f(x, y));
        }
        checkForValidity();
    }

    /**
     * Checks if the generated terrain has at least 1 valid landing spot.
     * <p>In case it's missing one it will regenerate the terrain.<p/>
     *
     * @see #generateTerrain(int)
     */

    private void checkForValidity(){
        // Place holder
        ArrayList<Vector2f> c = new ArrayList<>();

        for(Vector2f point : this.points){
            // We do not want points that go beyond bounds.
            if(point.y > height || point.x > width){
                generateTerrain(inc);
                return;
            }
            // I need 2 points to compare them.
            if(c.size() < 2){
                c.add(point);
                continue;
            }
            // Checks if the first point's y coordinate is the same as the second's one.
            if(c.get(0).y == c.get(1).y){
                ArrayList<Vector2f> temp = new ArrayList<>();
                temp.add(c.get(0));
                temp.add(c.get(1));
                validPoints.add(temp);
                if(validPoints.size() >= 2)
                    isValid = true;
            }
            // Updates the place holder.
            ArrayList<Vector2f> t = new ArrayList<>();
            t.add(c.get(1));
            c = t;
        }
        // Keep filling the array until it's done.
        if(!isValid) generateTerrain(inc);
    }

    /**
     * Creates the texture for the image.
     *
     * @param points      The points that are to be colored with white.
     * @param validPoints The points that are to be colored with red.
     *
     * @throws IOException If the image could not be created.
     */

    private void createTerrainImage(ArrayList<Vector2f> points, ArrayList<ArrayList<Vector2f>> validPoints) throws IOException{
        BufferedImage image = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        //Holds the points to draw.
        ArrayList<Vector2f> toDraw = new ArrayList<>();

        // White points.
        g.setColor(Color.WHITE);
        for(Vector2f p : points){
            // We need 2 points to compare them.
            if(toDraw.size() < 2){
                toDraw.add(p);
                continue;
            }
            // Let's draw, shall we?
            g.drawLine((int) toDraw.get(0).x, (int) toDraw.get(0).y, (int) toDraw.get(1).x, (int) toDraw.get(1).y);
            // Updates the place holder.
            ArrayList<Vector2f> t = new ArrayList<>();
            t.add(toDraw.get(1));
            toDraw = t;
        }

        // Red points
        g.setColor(Color.RED);
        for(ArrayList<Vector2f> p : validPoints){
            Vector2f first = p.get(0);
            Vector2f second = p.get(1);

            // Let's draw, shall we?
            g.drawLine((int) first.x, (int) first.y, (int) second.x, (int) second.y);
        }
        // Creates the image.
        ImageIO.write(image, "png", imageOutput);
    }

    /**
     * Deletes the image so that on the new launch of the program a new one can be created.
     */

    public void deleteImage(){
        imageOutput.delete();
    }

}