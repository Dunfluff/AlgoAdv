package uppgift2;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.*;

import java.io.*;
import javax.imageio.*;

public class Merge {

	private BufferedImage image;

	public BufferedImage getImage() {
		return this.image;
	}

	public Merge(BufferedImage img1, BufferedImage img2,BufferedImage img3) {
		int width = img1.getWidth();
		int height = img1.getHeight();
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
		WritableRaster inraster1 = img1.getRaster();
		WritableRaster inraster2 = img2.getRaster();
		WritableRaster inraster3 = img3.getRaster();
		int colors = 3;
		WritableRaster outraster = this.image.getRaster();

		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				for (int c = 0; c < colors; c++) {
					double alpha = inraster3.getSample(i, j, 0)/255.00;
					double value1 = inraster1.getSample(i, j, c)*alpha;
					double value2 = (1-alpha)*inraster2.getSample(i, j, c);
					int value = (int)(value1+value2);
					outraster.setSample(i, j, c, value);
				}
	}

	public static void main(String[] args) throws IOException {
			String file1 = "green_boat.jpg";
			String file2 = "orange_flower.jpg";
			String file3 = "template3.png";
			BufferedImage img1 = ImageIO.read(new File(file1));
			BufferedImage img2 = ImageIO.read(new File(file2));
			BufferedImage img3 = ImageIO.read(new File(file3));
			Merge flip = new Merge(img1,img2,img3);
			ImageIO.write(flip.getImage(), "PNG", new File("Merge.png"));

	}
}
