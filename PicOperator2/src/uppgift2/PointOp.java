package uppgift2;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.*;

import java.io.*;
import javax.imageio.*;

public class PointOp {

   private BufferedImage image;

   public BufferedImage getImage(){
      return this.image;
   }

   public PointOp(BufferedImage img, double x, int b){
      int width  = img.getWidth();
      int height = img.getHeight();

      WritableRaster inraster  = img.getRaster();

      this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);      // Assume image is color
      int colors = 3;
      try {int dummy = inraster.getSample(0,0,2);} catch (Exception e) {              // Try accessing blue color value!
          this.image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);// If exception, it is monochrome!
          colors = 1;
      }
      WritableRaster outraster = this.image.getRaster();

      for (int i=0; i<width; i++)
          for (int j=0; j<height; j++) 
              for (int c=0; c<colors; c++) {
                  int value = (int)(inraster.getSample(i,j,c)*x)+b;
                  if(value > 255)value %= 255;
                  outraster.setSample(i,j,c, value );
              }
   }
   
   public static void main(String[] args) {
       try {
           String file        = "green_boat.jpg";
           BufferedImage img  = ImageIO.read(new File(file));
           PointOp flip     = new PointOp(img,-0.5,127);
           ImageIO.write(flip.getImage(), "PNG", new File("flip_"+file+".png"));
       } catch (Exception e) {
           System.out.println("Failed processing!\nUsage: java FlipImage 'image_file'\n\n"+e.toString());
       }

   }
}
