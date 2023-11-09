import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class ControlPanel {
    Vector2 panelPos;
    Vector2 panelSize;

    Vector2 dripButtonPos = new Vector2(0,0); // relative to control panel?
    double dripButtonRadius = 50;
    boolean dripButtonClicked = false;
    Vector2 resetButtonPos = new Vector2(0,0); // relative to control panel?
    double resetButtonRadius = 50;
    boolean resetButtonClicked = false;
    String label = "Volume Dripped: 0 mL";
    double volumeDripped = 0;


    String text = "Press the stop button (below) when you want the titration to begin/end";

    public ControlPanel(Vector2 pos, Vector2 size){
        this.panelPos = pos;
        this.panelSize = size;

        updateDimensions();
    }

    public void updateDimensions(){
        dripButtonPos.x = panelPos.x + panelSize.x/3 - dripButtonRadius/2;
        dripButtonPos.y = panelPos.y + panelSize.y/2 - dripButtonRadius/2;

        resetButtonPos.x = panelPos.x + (panelSize.x*2)/3 - resetButtonRadius/2;
        resetButtonPos.y = panelPos.y + panelSize.y/2 - resetButtonRadius/2;
    }

    public void draw(Graphics2D graphics){

        Graphics2D g = (Graphics2D) graphics;

        // background
        g.setColor(Color.WHITE);
        g.fillRect((int)panelPos.x, (int)panelPos.y, (int)panelSize.x, (int)panelSize.y);

        // buttons
        double dripButtonOffset = 0;
        double resetButtonOffset = 0;
        if (dripButtonClicked) { dripButtonOffset = 5; }
        if (resetButtonClicked) { resetButtonOffset = 5; }

        g.setColor(Color.BLACK);
        g.fillOval((int)dripButtonPos.x-5, (int)dripButtonPos.y+5, (int)dripButtonRadius, (int)dripButtonRadius); //shadow
        g.setColor(Color.RED);
        g.fillOval((int)(dripButtonPos.x-dripButtonOffset), (int)(dripButtonPos.y+dripButtonOffset), (int)dripButtonRadius, (int)dripButtonRadius);
        
        g.setColor(Color.BLACK);
        g.fillOval((int)resetButtonPos.x-5, (int)resetButtonPos.y+5, (int)resetButtonRadius, (int)resetButtonRadius); //shadow
        g.setColor(Color.GREEN);
        g.fillOval((int)(resetButtonPos.x-dripButtonOffset), (int)(resetButtonPos.y+dripButtonOffset), (int)resetButtonRadius, (int)resetButtonRadius);
    }
}