/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Sphere;
import com.jme3.math.Vector3f;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
/**
 *
 * @author Theo
 */
public class Cannon extends Node{
    
    
    public Cannon(SimpleApplication sa){
        Sphere base = new Sphere(20, 20, 4);
        Geometry bas = new Geometry("Base", base);
        
        Cylinder barrel = new Cylinder(20, 20, 1f, 10f, true);
        Geometry bar = new Geometry("Barrel", barrel);
        
        Material mat = new Material(sa.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Gray);
        
        Material mat1 = new Material(sa.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.DarkGray);
        
        bar.setLocalTranslation(0, 2, 6);
        //mat.setColor("Diffuse", ColorRGBA.LightGray);
        //mat.setFloat("Shininess", 15f);
        
        bas.setMaterial(mat);
        bar.setMaterial(mat1);
        
        this.attachChild(bas);
        this.attachChild(bar);
        
    }
    
}
