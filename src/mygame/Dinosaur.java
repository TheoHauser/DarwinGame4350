/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

/**
 *
 * @author rolf
 */
public class Dinosaur extends Animal {

    public Dinosaur(Main main) {
        super(main,"Models/Triceratops/Triceratops.mesh.j3o");
    }

    @Override
    protected void adjust() {
        adjustmentNode.setLocalScale(0.03f);
        adjustmentNode.setLocalTranslation(0, 0.6f, 0);
    }
}
