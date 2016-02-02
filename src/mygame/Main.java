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
    CannonBall[] b;
    int numBalls = 0;
    Vector3f[] bloc = new Vector3f[20];
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

        rootNode.attachChild(can);
        
        ball = new CannonBall(this);
        ball.setLocalTranslation(0,2,0);
        rootNode.attachChild(ball);
        
        b = new CannonBall[20];
    }
    
    
    // -------------------------------------------------------------------------
    @Override
    public void simpleUpdate(float tpf) {
        for (int i = 0; i < animalNodes.length; i++) {
            animalNodes[i].getChild(0).rotate(0, tpf / 2, 0);
            time += tpf;
        }
        updateFlight(tpf);
    } 
    
    public void createCannonBall(){
        b[numBalls] = (CannonBall)ball.deepClone();
        rootNode.attachChild(b[numBalls]);
        b[numBalls].setLocalRotation(can.getLocalRotation());
        bloc[numBalls] = new Vector3f(0,0,1);
        can.localToWorld(bloc[numBalls],bloc[numBalls]);
        numBalls = (numBalls+1)%20;
        System.out.println("numBalls = "+ numBalls);
    }
    
    private void updateFlight(float tpf){
        Vector3f pos;
        for(int i = 0; i< numBalls; i++){
            pos = b[i].getWorldTranslation();
            if(pos.y<0f||Math.abs(pos.x)>=150)
                rootNode.detachChild(b[i]);
            b[i].move(bloc[i].mult(2));
            b[i].move(0f,-4.9f*tpf,0f);
            for(int j = 0; j < animalNodes.length; j++){
                Animal animal = (Animal)animalNodes[j].getChild(0);
                Vector3f center = animal.getCenter();
                if(center.distance(pos)<=4){
                    //animalNodes[j].detachChild(animal);
                    System.out.println("animal name:" + animal.getClass());
                    if(animal instanceof Dinosaur){
                        animalNodes[j].detachAllChildren();
                        animal = new Elephant(this);
                        float angle = (float) j / NUMBER_OF_ANIMALS * 2 * FastMath.PI;
                        float radius = 30f;
                        animal.setLocalTranslation(radius * FastMath.sin(angle), 0, radius * FastMath.cos(angle));
                        animal.rotate(0, angle, 0);
                        animalNodes[j].attachChild(animal);
                    }
                    else if(animal instanceof Elephant){
                        animalNodes[j].detachAllChildren();
                        animal = new Dinosaur(this);
                        float angle = (float) j / NUMBER_OF_ANIMALS * 2 * FastMath.PI;
                        float radius = 30f;
                        animal.setLocalTranslation(radius * FastMath.sin(angle), 0, radius * FastMath.cos(angle));
                        animal.rotate(0, angle, 0);
                        animalNodes[j].attachChild(animal);
                    }
                }
            }
        }
        System.out.println("b[0] local translation:" + bloc[0]);
    }
    
    private void initCam(){
        flyCam.setMoveSpeed(30);
        flyCam.setRotationSpeed(0);
    }
    private void initKeys(){
        inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("up", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("down", new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping("Shoot", new KeyTrigger(KeyInput.KEY_SPACE));
        
        // Add the names to the action listener.
        inputManager.addListener(actionListener,"Shoot");
        inputManager.addListener(analogListener,"left", "right", "up", "down");
    }
    
    private ActionListener actionListener = new ActionListener(){
        public void onAction(String name, boolean keyPressed, float tpf){
            if(name.equals("Shoot") && keyPressed){
                createCannonBall();
            }
        }
    };
    
    private AnalogListener analogListener = new AnalogListener(){
        public void onAnalog(String name, float value, float tpf){
            Quaternion quaternion = new Quaternion();
            if(name.equals("left")){
                Vector3f yAxis = can.worldToLocal(Vector3f.UNIT_Y, null);
                can.rotate(quaternion.fromAngleAxis(FastMath.PI/90, yAxis));
            }
            if(name.equals("right")){
                Vector3f yAxis = can.worldToLocal(Vector3f.UNIT_Y, null);
                can.rotate(quaternion.fromAngleAxis(-FastMath.PI/90, yAxis));
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
