/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

/**
 *
 * @author rolf
 */
public class Elephant extends Animal {

    public Elephant(Main main) {
        super(main, "Models/Elephant/Elephant.mesh.j3o");
    }

    @Override
    protected void adjust() {
        adjustmentNode.setLocalScale(0.3f);
        adjustmentNode.setLocalTranslation(2.07f, 0.0f, 1.5f);
    }
}
