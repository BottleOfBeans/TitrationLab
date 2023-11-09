import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.*;

public class MouseHandler implements MouseListener{

   private static double mouseX = 0;
   private static double mouseY = 0;
   private static Vector2 mouseVector = new Vector2(mouseX,mouseY);
   private static Object selected;
   private static boolean instructionsOpen = false;
   private static boolean redButtonHeld = false;
   private static boolean greenButtonHeld = false;


   @Override
   public void mouseClicked(MouseEvent e) {
      Point p = new Point(e.getX(), e.getY());
      if(GameWindow.instructionsButton.getInteractableBox().contains(p) && instructionsOpen == false){
         instructionsOpen = true;
      }
      if(instructionsOpen == true && GameWindow.closeButton.getInteractableBox().contains(p)){
         instructionsOpen = false;
      }
   }

   @Override
   public void mousePressed(MouseEvent e) {
      Point clickLocation = e.getPoint();
      if(e.getButton() == MouseEvent.BUTTON1 && MouseEvent.MOUSE_DRAGGED == e.MOUSE_DRAGGED){
         for(Object o : GameWindow.objs){
         if(o.getInteractableBox().contains(clickLocation) && o.getDraggable()){
            this.selected = o; 
         }
      }
      }
      
      Point p = new Point(e.getX(), e.getY());
      ControlPanel cp = GameWindow.controlPanel;
      
      //Green Button
      if(cp.dripButtonBox.contains(p)){
         redButtonHeld = true;
      }

      // Red button
      if(cp.resetButtonBox.contains(p)){
         greenButtonHeld = true;
      }
   }

   @Override
   public void mouseReleased(MouseEvent e) {      
      if(redButtonHeld){
         redButtonHeld = false;
      }
      if(greenButtonHeld){
         greenButtonHeld = false;
      }
      if(this.selected != null){
         this.selected = null;
      }
   }

   @Override
   public void mouseEntered(MouseEvent e) {
      ;
   }

   @Override
   public void mouseExited(MouseEvent e) {
      ;
   }

   public static Object getSelected(){
      return selected;
   }
   public static Vector2 getMouseVector(){
      mouseX = MouseInfo.getPointerInfo().getLocation().getX();
      mouseY = MouseInfo.getPointerInfo().getLocation().getY();

      mouseVector = new Vector2(mouseX, mouseY);

      return mouseVector;
   }
   public static boolean getInstructionsOpen(){
      return instructionsOpen;
   }
   public static boolean getRedStatus(){
      return redButtonHeld;
   }
   public static boolean getGreenStatus(){
      return greenButtonHeld;
   }

}
