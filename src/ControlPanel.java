import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.naming.NameAlreadyBoundException;

public class ControlPanel {
    Vector2 panelPos;
    Vector2 panelSize;

    Vector2 dripButtonPos = new Vector2(0,0); // relative to control panel?
    double dripButtonRadius = 50;
    Vector2 resetButtonPos = new Vector2(0,0); // relative to control panel?
    double resetButtonRadius = 50;
    String label = "Volume Dripped: 0 mL";
    double volumeDripped = 0;
    Rectangle2D dripButtonBox = new Rectangle2D.Double(dripButtonPos.x, dripButtonPos.y, dripButtonRadius, dripButtonRadius);
    Rectangle2D resetButtonBox = new Rectangle2D.Double(resetButtonPos.x, resetButtonPos.y, resetButtonRadius, resetButtonRadius);

    // Lower Panel
    Vector2 lowerPanelPos = new Vector2(0,0);

    

    Vector2[] buttonPositions = {new Vector2(0,0), new Vector2(0,0), new Vector2(0,0)};

/*
Once the molarity of the pipette is =90% of the beaker, the beaker should alternate between pink and clear
Once its at 100% it should be fully pink and the titration should
*/

    // 3 pipette concentrations: 0.5, 1, 1.5M; - should be known 
    // give pipette concentrations in control panel
    double[] pipetteConcentrations = {0.5, 1, 1.5};

    //3 beaker concentrations - remain unknown: 2, 2.5, 3M
    double[] beakerConcentrations = {2, 2.5, 3};
    
    String text = "Press the stop button (below) when you want the titration to begin/end";

    public ControlPanel(Vector2 pos, Vector2 size){
        this.panelPos = pos;
        this.panelSize = size;



        updateDimensions();
    }

    public void updateDimensions(){
        dripButtonPos.x = panelPos.x + panelSize.x/3 - dripButtonRadius/2;
        dripButtonPos.y = panelPos.y + (panelSize.y*2)/3 - (dripButtonRadius*3)/4;

        resetButtonPos.x = panelPos.x + (panelSize.x*2)/3 - resetButtonRadius/2;
        resetButtonPos.y = panelPos.y + (panelSize.y*2)/3 - (resetButtonRadius*3)/4;

        dripButtonBox = new Rectangle2D.Double(dripButtonPos.x, dripButtonPos.y, dripButtonRadius, dripButtonRadius);
        resetButtonBox = new Rectangle2D.Double(resetButtonPos.x, resetButtonPos.y, resetButtonRadius, resetButtonRadius);
    }

    public void draw(Graphics2D graphics){

        Graphics2D g = (Graphics2D) graphics;

        // background
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect((int)panelPos.x - 10, (int)panelPos.y - 10, (int)panelSize.x + 10, (int)panelSize.y + 10);
        g.setColor(Color.GRAY);
        g.fillRect((int)panelPos.x, (int)panelPos.y, (int)panelSize.x , (int)panelSize.y );


        // buttons
        double dripButtonOffset = 0;
        double resetButtonOffset = 0;

        if (MouseHandler.getRedStatus()) { dripButtonOffset = 5; }
        if (MouseHandler.getGreenStatus()) { resetButtonOffset = 5; }

        g.setColor(Color.BLACK);
        g.fillOval((int)dripButtonPos.x-5, (int)dripButtonPos.y+5, (int)dripButtonRadius, (int)dripButtonRadius); //shadow
        g.setColor(Color.GREEN);
        g.fillOval((int)(dripButtonPos.x-dripButtonOffset), (int)(dripButtonPos.y+dripButtonOffset), (int)dripButtonRadius, (int)dripButtonRadius);
        
        g.setColor(Color.BLACK);
        g.fillOval((int)resetButtonPos.x-5, (int)resetButtonPos.y+5, (int)resetButtonRadius, (int)resetButtonRadius); //shadow
        g.setColor(Color.RED);
        g.fillOval((int)(resetButtonPos.x-resetButtonOffset), (int)(resetButtonPos.y+resetButtonOffset), (int)resetButtonRadius, (int)resetButtonRadius);
    
        // text
        label = "Volume Dripped: " + volumeDripped + " mL";
        g.setColor(Color.BLACK);
        Font myFont = new Font ("Courier New", 1, 25);
        g.setFont (myFont);
        g.drawString(label, (int)panelPos.x, (int)(panelPos.y+panelSize.y/10));
        
    }
}

