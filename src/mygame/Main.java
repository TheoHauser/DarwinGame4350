package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.math.Quaternion;
import com.jme3.scene.Node;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;

public class Main extends SimpleApplication {
    public static final int NUMBER_OF_ANIMALS = 16;
    Node groundNode;
    Node[] animalNodes;
    Cannon can;
    CannonBall ball;
    Node pivoty = new Node();
    Node pivotx = new Node();
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
        initCam();
        initKeys();
        //
        // add ground
        groundNode = new Ground(this);
        rootNode.attachChild(groundNode);
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
                animal = new Dinosaur(this);
            if (i % 2 == 0) {
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
        can = new Cannon(this);
        can.setLocalTranslation(0, 0, 0);

        rootNode.attachChild(pivoty);
        rootNode.attachChild(pivotx);
        
        pivoty.attachChild(can);
        pivotx.attachChild(can);
        
        ball = new CannonBall(this);
        ball.setLocalTranslation(0,2,0);
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
    private void initKeys(){
        inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_NUMPAD4));
        inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_NUMPAD6));
        inputManager.addMapping("up", new KeyTrigger(KeyInput.KEY_NUMPAD8));
        inputManager.addMapping("down", new KeyTrigger(KeyInput.KEY_NUMPAD2));
        inputManager.addMapping("Shoot", new KeyTrigger(KeyInput.KEY_SPACE));
        
        // Add the names to the action listener.
        inputManager.addListener(actionListener,"Shoot");
        inputManager.addListener(analogListener,"left", "right", "up", "down");
    }
    
    private ActionListener actionListener = new ActionListener(){
        public void onAction(String name, boolean keyPressed, float tpf){
            if(name.equals("Shoot")){
                CannonBall c = (CannonBall)ball.deepClone();
                c.setLocalTranslation(0, 2, 0);
                rootNode.attachChild(c);
            }
        }
    };
    
    
    private AnalogListener analogListener = new AnalogListener(){
        public void onAnalog(String name, float value, float tpf){
            Quaternion quaternion = new Quaternion();
            if(name.equals("left")){
                Vector3f yAxis = can.worldToLocal(Vector3f.UNIT_Y, null);
                can.rotate(quaternion.fromAngleAxis(FastMath.PI/60, yAxis));
            }
            if(name.equals("right")){
                Vector3f yAxis = can.worldToLocal(Vector3f.UNIT_Y, null);
                can.rotate(quaternion.fromAngleAxis(-FastMath.PI/60, yAxis));
            }
            if(name.equals("up")){
                can.rotate(-1*tpf,0, 0);

            }
            if(name.equals("down")){
                can.rotate(1*tpf,0, 0);
            }
        }
    };
}
