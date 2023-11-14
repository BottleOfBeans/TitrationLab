import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameWindow extends JPanel implements Runnable {

    /*
     * Window settings --> Ratio of 16:9
     */
    static int gameWidth = 1920; // gameColumnAmount*ActualTileSize;
    static int gameHeight = 1080; // gameRowAmount*ActualTileSize;

    /*
     * to get center x --> gameWidth/2
     * to get center y --> gameHeight/2
     * top left (0,0)
     * bottom right (gameWidth,gameHeight)
     */
    
    Thread gameThread;
    int FPS = 144;
    
    KeyHandler keyH = new KeyHandler();
    MouseListener mouseH = new MouseHandler();

    // static Object e = new Object([position as Vector2], [dimensions as Vector2], draggability, imageUrl)
    static Object cup = new Object(new Vector2(gameWidth/2 - 400,gameHeight/5), new Vector2(100, 100), true, "src/images/cup.png");
    static Object burrette = new Object(new Vector2(gameWidth/2 + 600,gameHeight/5), new Vector2(200, 430), true, "src/images/burrette.png");
    static Object ringStand = new Object(new Vector2(gameWidth/2 ,30), new Vector2(800, 800), false, "src/images/ringstand.png");
    static Object beaker = new Object(new Vector2(gameWidth/2 - 600,gameHeight/5), new Vector2(300, 300), true, "src/images/beaker.png");

    /*
     * Instructions initilizers
     */


    //background
    static Image img = Toolkit.getDefaultToolkit().createImage("src/images/background.png");

    // Instruction panel related objecst
    static Object instructionsButton = new Object(new Vector2(100,100), new Vector2(100, 100), true, "src/images/clipboard.png");
    static Object instructionsScreen = new Object(new Vector2(gameWidth/2,gameHeight/2), new Vector2(900, 900), true, "src/images/instructions.png");
    static Object closeButton = new Object(new Vector2(gameWidth/2 + 400 ,gameHeight/2 - 375), new Vector2(50, 50), false, "src/images/closebutton.png");
    
    /*
     * Assembly Checks
     */
    static boolean buretteConnected = false;
    static boolean beakerConnected = false;


    static Object objs[] = {burrette, beaker, ringStand};

    // control panel object
    //Position, Size (Both Vector2)
    static ControlPanel controlPanel = new ControlPanel(new Vector2(gameWidth-300,100), new Vector2(300,200));

    static TitrationManager tM = new TitrationManager();

    /*
     * These do not have proper image files associated yet so they throw up errors!
     */
    //static Object burette = new Object(new Vector2(0,0), new Vector2(20, 100), false, "buret.png");
    //static Object ringStand = new Object(new Vector2(0,0), new Vector2(20,200), false, "images/ringstand.png");//ringstand.png does NOT currently exist and is a placeholder
    //static ControlPanel controlPanel = new ControlPanel(new Vector2(0,0), new Vector2(50,50)); 
    
    
    /*
     * More Window Settings
     */
     public GameWindow() {
        this.setPreferredSize(new Dimension(gameWidth, gameHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
    }

    /*
     * Starting the thread and managing frame updates
     */
    public void startWindowThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /*
     * Frame Updates :D
     */
    @Override
    public void run() {
        /*
         * Add code here that runs before the game starts
         */

        // Managing updates and FPS
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;
            if (delta >= 1) {
                update(delta); // Sending the deltaTime through the update function
                repaint();
                delta--;
            }

        }
    }

    public void instructionHandler(Graphics g){
        Graphics2D graphics = (Graphics2D)g;
        if(MouseHandler.getInstructionsOpen()){
            instructionsScreen.drawTextures(graphics);
            closeButton.drawTextures(graphics);
        }else{
            instructionsButton.drawTextures(graphics);
        }
    }



    /*
     * Game updates run here
     */
    public void update(double deltaTime) {
        for(Object o : objs){
            o.update(deltaTime);
        }
        if(MouseHandler.getSelected() != null){
            MouseHandler.getSelected().setCurrentPos(MouseHandler.getMouseVector());
        }

        if(ringStand.getInteractableBox().contains(burrette.getInteractableBox())){buretteConnected = true;}
        if(ringStand.getInteractableBox().contains(beaker.getInteractableBox())){beakerConnected = true;}


        tM.update();
    }

    /*
     * Anything to be displayed runs here
     */
    public void paintComponent(Graphics g) {

        // Quick definition of varibles to use with the G2D library
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Drawing the background
        //background.drawTextures(graphics);
        graphics.drawImage(img,0,0,1920,1080, null);

        //Drawing the liquids
        tM.drawBurretteLiquid(graphics);
        tM.drawBeakerLiquid(graphics);

        //Drawing the textures
        for(Object o : objs){
            o.drawTextures(graphics);
        }        
        
        // Drawing the ControlPanel
        controlPanel.draw(graphics);

        //Drawing the instruction panel
        instructionHandler(graphics);
    }

}
