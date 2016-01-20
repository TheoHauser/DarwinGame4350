package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.math.Vector3f;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Theo
 */
public class CannonBall extends Node {
    
    public CannonBall(SimpleApplication sa){
        Sphere cb = new Sphere(20,20,0.9f);
        Geometry ball = new Geometry("Cannon Ball", cb);
        
        Material mat = new Material(sa.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md" );
        mat.setColor("Color", ColorRGBA.White);
        
        ball.setMaterial(mat);
        this.attachChild(ball);
    }
}
