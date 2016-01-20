/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bounding.BoundingBox;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.WireBox;

/**
 *
 * @author rolf
 */
public abstract class Animal extends Node{

    
    protected Node adjustmentNode;
    private Spatial modelData;
    private boolean bbON = true;
    private Geometry geomBoundingBox;

    // -------------------------------------------------------------------------
    // Abstract Methods
    // adjustment: sets the specific local translation and scaling for a certain
    // mesh
    protected abstract void adjust();   
    
    // -------------------------------------------------------------------------
    public Animal(SimpleApplication sa, String filename) {
        // load data
        modelData = (Node) sa.getAssetManager().loadModel(filename);
        modelData.setModelBound(new BoundingBox());
        modelData.updateModelBound();
        modelData.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        //
        // create bounding box
        Material redWire = new Material(sa.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        redWire.setColor("Color", ColorRGBA.Red);
        redWire.getAdditionalRenderState().setWireframe(true);
        //
        BoundingBox b = (BoundingBox) modelData.getWorldBound();
        WireBox wb = new WireBox();
        wb.fromBoundingBox(b);
        geomBoundingBox = new Geometry("bb", wb);
        geomBoundingBox.setMaterial(redWire);
        geomBoundingBox.setLocalTranslation(b.getCenter());      
        
        // attach model and bounding box to adjustmentNode
        adjustmentNode = new Node();
        adjustmentNode.attachChild(modelData);
        adjustmentNode.attachChild(geomBoundingBox);
        
        // add adjustment node to pivot node        
        this.attachChild(adjustmentNode);
        switchBoundingBox(true);
        
        // adjust!
        adjust();   // this calls the implementation in inherited classes!
    }

    // -------------------------------------------------------------------------
    /**
     * Center of Model in World Coordinates
     *
     * @return
     */
    public Vector3f getCenter() {
        return (modelData.getWorldTranslation());
    }

    // -------------------------------------------------------------------------
    /**
     * Draw / Hide Bounding Box
     *
     * @param show
     */
    public final void switchBoundingBox(boolean on) {
        if (!on) {
            if (bbON) {
                adjustmentNode.detachChild(geomBoundingBox);
            }
        } else {
            if (!bbON) {
                adjustmentNode.attachChild(geomBoundingBox);
            }
        }
    }
}
