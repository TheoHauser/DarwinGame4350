/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.shape.Line;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.system.AppSettings;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author Rolf
 */
public class JMEInit {
    SimpleApplication sa;
    public static Material gold, magenta, matRed, matGreen, matBlue;
    
    public JMEInit(SimpleApplication sa){
        this.sa = sa;
        initGui();
        initMaterials();
        initLightandShadow();
        initCoordCross();
        initCam();
    }
    
    // -------------------------------------------------------------------------
    // Initialization Methods
    // -------------------------------------------------------------------------
    public static void initAppScreen(SimpleApplication app) {
        AppSettings aps = new AppSettings(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        screen.width *= 0.75;
        screen.height *= 0.75;
        aps.setResolution(screen.width, screen.height);
        app.setSettings(aps);
        app.setShowSettings(false);
    }

    // -------------------------------------------------------------------------
    private void initMaterials() {
        gold = new Material(sa.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        gold.setBoolean("UseMaterialColors", true);
        gold.setColor("Ambient", ColorRGBA.Red);
        gold.setColor("Diffuse", ColorRGBA.Green);
        gold.setColor("Specular", ColorRGBA.Gray);
        gold.setFloat("Shininess", 4f); // shininess from 1-128

        magenta = new Material(sa.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        magenta.setBoolean("UseMaterialColors", true);
        magenta.setColor("Ambient", ColorRGBA.Gray);
        magenta.setColor("Diffuse", new ColorRGBA(0.2f, 0.4f, 0.0f, 1.0f));
        magenta.setColor("Specular", ColorRGBA.Red);
        magenta.setFloat("Shininess", 2f); // shininess from 1-128

        matRed = new Material(sa.getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        matRed.setColor("Color", ColorRGBA.Red);
        matGreen = new Material(sa.getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        matGreen.setColor("Color", ColorRGBA.Green);
        matBlue = new Material(sa.getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        matBlue.setColor("Color", ColorRGBA.Blue);
    }

    // -------------------------------------------------------------------------
    private void initGui() {
        sa.setDisplayFps(true);
        sa.setDisplayStatView(false);
    }

    // -------------------------------------------------------------------------
    private void initLightandShadow() {
        // Light1: white, directional
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.7f, -1.3f, -0.9f)).normalizeLocal());
        sun.setColor(ColorRGBA.Gray);
        sa.getRootNode().addLight(sun);

        // Light 2: Ambient, gray
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(new ColorRGBA(0.7f, 0.7f, 0.7f, 1.0f));
        sa.getRootNode().addLight(ambient);

        // SHADOW
        // the second parameter is the resolution. Experiment with it! (Must be a power of 2)
        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(sa.getAssetManager(), 512, 1);
        dlsr.setLight(sun);
        sa.getViewPort().addProcessor(dlsr);
    }

    // -------------------------------------------------------------------------
    private void initCam() {
        sa.getFlyByCamera().setEnabled(true);
        sa.getCamera().setLocation(new Vector3f(25f, 15, 65f));
        sa.getCamera().lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
    }

    // -------------------------------------------------------------------------
    private void initCoordCross() {
        Line xAxis = new Line(new Vector3f(-3, 0, 0), Vector3f.ZERO);
        Line yAxis = new Line(new Vector3f(0, -3, 0), Vector3f.ZERO);
        Line zAxis = new Line(new Vector3f(0, 0, -3), Vector3f.ZERO);
        Arrow ax = new Arrow(new Vector3f(3, 0, 0));
        Arrow ay = new Arrow(new Vector3f(0, 3, 0));
        Arrow az = new Arrow(new Vector3f(0, 0, 3));
        Geometry geomX = new Geometry("xAxis", xAxis);
        Geometry geomY = new Geometry("yAxis", yAxis);
        Geometry geomZ = new Geometry("zAxis", zAxis);
        geomX.setMaterial(matRed);
        geomY.setMaterial(matGreen);
        geomZ.setMaterial(matBlue);
        Geometry geomXA = new Geometry("xAxis", ax);
        Geometry geomYA = new Geometry("yAxis", ay);
        Geometry geomZA = new Geometry("zAxis", az);
        geomXA.setMaterial(matRed);
        geomYA.setMaterial(matGreen);
        geomZA.setMaterial(matBlue);
        sa.getRootNode().attachChild(geomX);
        sa.getRootNode().attachChild(geomY);
        sa.getRootNode().attachChild(geomZ);
        sa.getRootNode().attachChild(geomXA);
        sa.getRootNode().attachChild(geomYA);
        sa.getRootNode().attachChild(geomZA);
    }
}
