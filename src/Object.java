import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Object {
    
    /*
     * To Do List:
     * 
     * Object Class:
     *      *Import all image files for all items
     *      *Make some items interactable and change their states/reference images
     *      *Optional - Add gravity to make objects fall down (easy but do at end)
     *
     * KeyHandler:
     *      *Reset lab with button click
     * 
     * MouseHandler: (TO BE MADE) 
     *      *Auto align objects to interactable parts
     * 
     * GameWindow:
     *      *Draw Everything
     *      *Lag proof it
     *      *Make it look nice
     * 
     * ControlPanel (TO BE MADE)
     *      *Create an interactable panel to control titration
     *
     * Titration Manager (TO BE MADE)
     *      *Control titration variables
     *      *Decides if purple or not
     * 
     */

    final Vector2 GRAVITY = new Vector2(0,2);
    private boolean draggable; // If true then can move by dragging
    private Rectangle2D interactableBox; // Hitbox
    private Vector2 currentPos; // current postiion
    private Vector2 prevPos; // previous position
    private BufferedImage img; // image texture
    private boolean gavityAffected = true;
    

    public Object(Vector2 currentPos, Vector2 dimensions, boolean draggable, String imageURL){
        this.currentPos = currentPos;
        this.prevPos = this.currentPos;
        this.draggable = draggable;
        this.interactableBox = new Rectangle2D.Double(this.currentPos.x - dimensions.x/2, this.currentPos.y - dimensions.y/2, dimensions.x, dimensions.y);
        try {
            this.img = ImageIO.read(new File(imageURL));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /*                    x y
     * Vector Object --> (0,0);
     * wait does vector have both magnitude and direction? or is this a different vector
     */



    public void drawTextures(Graphics g){
        Graphics2D graphics = (Graphics2D)g;
        
        /*
         * Hitbox debugging code
         */
        //graphics.setColor(Color.BLUE);
        //graphics.fill(interactableBox);

        //g.drawImage(image, [starting x], [starting y], [image width], [image height], [image observer]);
        graphics.drawImage(img, (int)interactableBox.getMinX(), (int)interactableBox.getMinY(), (int)interactableBox.getWidth(), (int)interactableBox.getWidth(), null);
        
        /*
         * Center point debugging code
         */
        //graphics.setColor(Color.RED);
        //graphics.fill(centerEllipse());
    }


    public void update(double deltaTime){
        this.interactableBox = new Rectangle2D.Double(
            this.currentPos.x - this.interactableBox.getWidth()/2,
            this.currentPos.y - this.interactableBox.getHeight()/2, 
            this.interactableBox.getWidth(), 
            this.interactableBox.getHeight());
            gravityHandler(deltaTime);
    }

    public void gravityHandler(double deltaTime){
        if(this.currentPos.y < 730){
            Vector2 velocity = this.currentPos.returnSubtract(this.prevPos);
            velocity.addVector(GRAVITY.returnMultiply(deltaTime));
            this.prevPos = this.currentPos;
            Vector2 temp =  this.currentPos.returnAddition(velocity);
            if(temp.x > GameWindow.gameWidth){temp.x = GameWindow.gameWidth;} //Right wall boundary
            if(temp.y > GameWindow.gameHeight ){temp.y = GameWindow.gameHeight;} // Bottom wall boundary
            if(temp.y < 0){temp.y = 0;} //Top wall boundary
            if(temp.x < 0){temp.x = 0;} //Left wall boundary
            this.currentPos = temp;
        }
    }

    public Image getImage(){
        return this.img;
    }
    public Vector2 getCurrentPos(){
        return this.currentPos;
    }
    public boolean getDraggable(){
        return draggable;
    }
    public Rectangle2D getInteractableBox(){
        return this.interactableBox;
    }
    public boolean getGravityAffected(){
        return this.gavityAffected;
    }

    public void setGravityAffected(boolean b){
        this.gavityAffected = b;
    }
    public void setImage(String imageURL){
            try {
                this.img = ImageIO.read(new File(imageURL));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
    public void setCurrentPos(Vector2 currentPos){
        this.currentPos = currentPos;
    }
    public void setDraggable(boolean draggable){
        this.draggable = draggable;
    }
     
    public String toString(){
        return "Object at" + "   ("+currentPos.x+","+currentPos.y+")"+ "with an interactable box around  " + interactableBox.toString() + "    and an imageURL of "+this.img.toString(); 
    }
    public Ellipse2D centerEllipse(){
        return new Ellipse2D.Double(this.currentPos.x - 5, this.currentPos.y - 5, 10, 10);
    }
}
