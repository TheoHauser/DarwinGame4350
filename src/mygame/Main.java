package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;

public class Main extends SimpleApplication {
    public static final int NUMBER_OF_ANIMALS = 16;
    Node groundNode;
    Node[] animalNodes;
    float time;
    boolean switched = false;
    JMEInit jmeInit;

    // -------------------------------------------------------------------------
    public static void main(String[] args) {
        Main app = new Main();
        JMEInit.initAppScreen(app); // avoid the monkey ...
        app.start();
    }

    // -------------------------------------------------------------------------
    @Override
    public void simpleInitApp() {
        // initialize graphics (cam, materials etc.)
        jmeInit = new JMEInit(this);
        //
        // add ground
        groundNode = new Ground(this);
        rootNode.attachChild(groundNode);
        initCam();
        //
        // add animals
        addAnimals();
        
        //add canon
        addCanon();
    }

    // -------------------------------------------------------------------------
    private void addAnimals(){
        animalNodes = new Node[NUMBER_OF_ANIMALS];
        for (int i = 0; i < NUMBER_OF_ANIMALS; i++) {
            animalNodes[i] = new Node();
            Animal animal;
            if (i % 2 == 0) {
                animal = new Dinosaur(this);
            } else {
                animal = new Elephant(this);
            }
            animalNodes[i].attachChild(animal);
            float angle = (float) i / NUMBER_OF_ANIMALS * 2 * FastMath.PI;
            float radius = 30f;
            animal.setLocalTranslation(radius * FastMath.sin(angle), 0, radius * FastMath.cos(angle));
            animal.rotate(0, angle, 0);
            groundNode.attachChild(animalNodes[i]);
        }
    }
    
    private void addCanon(){
        Cannon can = new Cannon(this);
        can.setLocalTranslation(0, 0, 0);
        rootNode.attachChild(can);
        
        CannonBall ball = new CannonBall(this);
        ball.setLocalTranslation(0,2,12);
        rootNode.attachChild(ball);
    }
    
    
    // -------------------------------------------------------------------------
    @Override
    public void simpleUpdate(float tpf) {
        for (int i = 0; i < animalNodes.length; i++) {
            animalNodes[i].getChild(0).rotate(0, tpf / 2, 0);
            time += tpf;
        }
    } 
    
    private void initCam(){
        flyCam.setMoveSpeed(30);
    }
}
