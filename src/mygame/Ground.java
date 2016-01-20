/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Rolf
 */
public class Ground extends Node{
        // -------------------------------------------------------------------------
    public Ground(SimpleApplication sa) {
        // Materials must be initialized first
        Material matGround = new Material(sa.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        matGround.setBoolean("UseMaterialColors", true);
        matGround.setColor("Ambient", new ColorRGBA(0.3f, 0.3f, 0.3f, 1.0f));
        matGround.setColor("Diffuse", ColorRGBA.Green);
        matGround.setColor("Specular", ColorRGBA.Orange);
        matGround.setFloat("Shininess", 2f); // shininess from 1-128
        matGround.setTexture("DiffuseMap", sa.getAssetManager().loadTexture("Textures/texture1.png"));
        //
        // Geometry
        Box box = new Box(80f, 2f, 80f);
        Geometry geomGround = new Geometry("ground", box);
        geomGround.setMaterial(matGround);
        geomGround.setLocalTranslation(0, -2.0f, 0);
        //
        // define shadow behavior
        geomGround.setShadowMode(RenderQueue.ShadowMode.Receive);
        this.attachChild(geomGround);
    }
}
