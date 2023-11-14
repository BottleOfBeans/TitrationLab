import java.awt.*;
import java.awt.geom.Rectangle2D;

public class TitrationManager {

    private double molarity;
    private double baseMolarity; //Base in the beaker
    private double acidMolarity; //Acid in the container

    private Rectangle2D beakerLiquid;
    private Rectangle2D burreteLiquid;
    private double buretteDripCounter = 0;
    


    private final double DRIPAMOUNT = 13.2; //in pixels how much to decrease each drip

    private final double WIDTHLIQUIDBURRETTE =  37;
    private final double HEIGHTLIQUIDBURRETTE = 380;

    private final double WIDTHBEAKERLIQUID =  400;
    private final double HEIGHTBEAKERLIQUID = 400;


    private final Color BLCOLOR = Color.cyan;
    private final Color BCOLOR = Color.lightGray;


    private final int maxDrips = (int)(HEIGHTLIQUIDBURRETTE / DRIPAMOUNT);

    public TitrationManager(){
        burreteLiquid = new Rectangle2D.Double(
            GameWindow.burrette.getCurrentPos().x - 25,
            GameWindow.burrette.getCurrentPos().y - 194  + buretteDripCounter * DRIPAMOUNT,
            WIDTHLIQUIDBURRETTE,
            HEIGHTLIQUIDBURRETTE - buretteDripCounter * DRIPAMOUNT
        );

        beakerLiquid = new Rectangle2D.Double(
            GameWindow.beaker.getCurrentPos().x - 200,
            GameWindow.beaker.getCurrentPos().y - 200  - buretteDripCounter * DRIPAMOUNT/3,
            WIDTHBEAKERLIQUID,
            HEIGHTBEAKERLIQUID + buretteDripCounter * DRIPAMOUNT/3
        );
    }
    
    //200, 430 B Dimensions

    public void reset(){
        buretteDripCounter = 0;
    }

    public void update(){
        burreteLiquid = new Rectangle2D.Double(
            GameWindow.burrette.getCurrentPos().x - 25,
            GameWindow.burrette.getCurrentPos().y - 194 + buretteDripCounter * DRIPAMOUNT/3,
            WIDTHLIQUIDBURRETTE,
            HEIGHTLIQUIDBURRETTE - buretteDripCounter * DRIPAMOUNT/3
        );
        beakerLiquid = new Rectangle2D.Double(
            GameWindow.beaker.getCurrentPos().x - 200,
            GameWindow.beaker.getCurrentPos().y - 200  - buretteDripCounter * DRIPAMOUNT/3,
            WIDTHBEAKERLIQUID,
            HEIGHTBEAKERLIQUID + buretteDripCounter * DRIPAMOUNT/3
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

    

}
