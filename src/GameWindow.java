import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
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
    static Object cup = new Object(new Vector2(gameWidth/2,gameHeight/2), new Vector2(100, 100), true, "src/images/cup.png");
    
    /*
     * Instructions initilizers
     */


    //background
    static Object background = new Object(new Vector2(950,-200), new Vector2(1800, 1080), false, "src/images/background.png");

    // Instruction panel related objecst
    static Object instructionsButton = new Object(new Vector2(100,100), new Vector2(100, 100), true, "src/images/clipboard.png");
    static Object instructionsScreen = new Object(new Vector2(gameWidth/2,gameHeight/2), new Vector2(900, 900), true, "src/images/instructions.png");
    static Object closeButton = new Object(new Vector2(gameWidth/2 + 400 ,gameHeight/2 - 375), new Vector2(50, 50), false, "src/images/closebutton.png");
    

    static Object objs[] = {cup};

    // control panel object
    //Position, Size (Both Vector2)
    static ControlPanel controlPanel = new ControlPanel(new Vector2(gameWidth-300,100), new Vector2(200,200));

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
        background.drawTextures(graphics);
        
        //Drawing the textures
        cup.drawTextures(graphics);
        
        
        // Drawing the ControlPanel
        controlPanel.draw(graphics);

        //Drawing the instruction panel
        instructionHandler(graphics);
    }

}
