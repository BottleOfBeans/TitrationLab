import java.awt.*;
import java.awt.geom.Rectangle2D;

public class TitrationManager {

    private double buretteMolarity = 1.2;        //molarity of burette
    private double beakerMolarity = 0.5;        //molarity of beaker
    private double beakerMoles; //amt of moles in beaker
    private double buretteMoles; //amt of moles used from burette

    private Rectangle2D beakerLiquid;
    private Rectangle2D burreteLiquid;
    private double buretteDripCounter = 0;
    


    private final double DRIPAMOUNT = 13.2; //in pixels how much to decrease each drip

    private final double WIDTHLIQUIDBURRETTE =  37;
    private final double HEIGHTLIQUIDBURRETTE = 380;

    private final double WIDTHBEAKERLIQUID =  285;
    private final double HEIGHTBEAKERLIQUID = 160;


    private final Color BLCOLOR = Color.cyan;
    private Color BCOLOR = Color.lightGray;


    private final int maxDrips = (int)(HEIGHTLIQUIDBURRETTE / DRIPAMOUNT);

    public TitrationManager(){
        burreteLiquid = new Rectangle2D.Double(
            GameWindow.burrette.getCurrentPos().x - 25,
            GameWindow.burrette.getCurrentPos().y - 194  + buretteDripCounter * DRIPAMOUNT,
            WIDTHLIQUIDBURRETTE,
            HEIGHTLIQUIDBURRETTE - buretteDripCounter * DRIPAMOUNT
        );

        beakerLiquid = new Rectangle2D.Double(
            GameWindow.beaker.getCurrentPos().x - 140,
            GameWindow.beaker.getCurrentPos().y - 20  - buretteDripCounter * DRIPAMOUNT/5,
            WIDTHBEAKERLIQUID,
            HEIGHTBEAKERLIQUID + buretteDripCounter * DRIPAMOUNT/5
        );
    }
    
    //200, 430 B Dimensions

    public void reset(){
        buretteDripCounter = 0;
        buretteMoles = buretteMolarity * (buretteDripCounter * 0.001);
        beakerMoles = beakerMolarity * 0.05;
        BCOLOR = Color.lightGray;
    }

    public void update(){
        burreteLiquid = new Rectangle2D.Double(
            GameWindow.burrette.getCurrentPos().x - 25,
            GameWindow.burrette.getCurrentPos().y - 194 + buretteDripCounter * DRIPAMOUNT,
            WIDTHLIQUIDBURRETTE,
            HEIGHTLIQUIDBURRETTE - buretteDripCounter * DRIPAMOUNT
        );
        beakerLiquid = new Rectangle2D.Double(
            GameWindow.beaker.getCurrentPos().x - 140,
            GameWindow.beaker.getCurrentPos().y - 20  - buretteDripCounter * DRIPAMOUNT/5,
            WIDTHBEAKERLIQUID,
            HEIGHTBEAKERLIQUID + buretteDripCounter * DRIPAMOUNT/5
        );
    }

    public void drip(){ //decerases burrette height by DRIPAMOUNT pixels
        if (buretteDripCounter + 1 <= maxDrips){
            buretteDripCounter += 1;
            GameWindow.controlPanel.volumeDripped += 1;
        }
    }

    public void drawBurretteLiquid(Graphics g){
        Graphics2D graphics = (Graphics2D) g;
        graphics.setColor(BLCOLOR);
        graphics.fill(burreteLiquid);
    }

    public void drawBeakerLiquid(Graphics g){
        Graphics2D graphics = (Graphics2D) g;
        graphics.setColor(BCOLOR);
        graphics.fill(beakerLiquid);
    }

    public void calcs(Graphics g){
/*
        /*
         * burreteDripCounter is the number of drips in mL
         * BColor is the beaker color
         */
        buretteMoles = buretteMolarity * (buretteDripCounter * 0.001);
        beakerMoles = beakerMolarity * 0.05;
        if(buretteMoles >= (0.9 * beakerMoles)){
            if(beakerMoles >= buretteMoles){
                BCOLOR = Color.pink;
            }
        }
        if(buretteMoles >= beakerMoles){
            BCOLOR = Color.MAGENTA;
        }
    }

}
/*Calculation stuff - idk where to put it 
Molarity of beaker * .05 = moles of beaker
Molarity of burette * buretteDripCounter = moles of burette
if
*/