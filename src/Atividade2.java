import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

/**
 * Created by Mylla on 21/03/2017.
 */

public class Atividade2 {

    static float kernel[][] = {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}}; //Kernel pra contraste

    public static void run() throws IOException {
        String PATH = "C:\\Users\\Mylla\\Pictures";
        BufferedImage img = ImageIO.read(new File(PATH,"kitty.jpg"));
        BufferedImage pixelCat = pixelate(img, 20);
        BufferedImage contrastCat = convolve(pixelCat, kernel, 20);

        ImageIO.write(pixelCat, "png", new File("pixelCat.png"));
        ImageIO.write(contrastCat, "png", new File("contrastCat.png"));
    }

    public static BufferedImage pixelate(BufferedImage img, int size) //size -> tamanho do pixel
    {
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < img.getHeight(); y = y + size) {
            for (int x = 0; x < img.getWidth(); x = x + size) {
                //Color cor = new Color(img.getRGB(x, y));
                //out.setRGB(x, y, cor.getRGB());
                setNextColors(img, out, x, y, size);
            }
        }
        return out;
    }


    public static int saturate(int color, int kernel)
    {
        if(color*kernel > 255)
            return 255;
        else if (color * kernel < 0)
            return 0;
        return (color * kernel);
    }


    public static void setNextColors(BufferedImage img, BufferedImage out, int x, int y, int size)
    {
        Color cor = new Color(img.getRGB(x, y));

        for (int h = y; h < y+size; h++)
        {
            for (int w = x; w < x+size; w++)
            {
                if(h >= img.getHeight() || w >= img.getWidth())
                {
                    continue;
                }
                out.setRGB(w, h, cor.getRGB());
            }
        }

    }

    static Color applyKernel(Color[][] colors, float[][] kernel) //aplica o kernel
    {
        Color newColors[][] = new Color[3][3];
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) { //lê os pixels e multiplica pelo kernel
                int r = saturate((int) (colors[x][y].getRed() * kernel[x][y]));
                int g = saturate((int) (colors[x][y].getGreen() * kernel[x][y]));
                int b = saturate((int) (colors[x][y].getBlue() * kernel[x][y]));
                newColors[x][y] = new Color(r, g, b);
            }

        }

        int sr = 0;
        int sg = 0;
        int sb = 0;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) { //soma tudo nas variasveis pra retornar
                sr +=  newColors[x][y].getRed();
                sg +=  newColors[x][y].getGreen();
                sb +=  newColors[x][y].getBlue();
            }
        }
        return new Color(sr, sg, sb);
    }

    public static int saturate(int cor)
    {
        if (cor > 255)
            return 255;

        else if (cor < 0)
            return 0;
        return cor;
    }

    /*Aplica filtro*/
    public static BufferedImage convolve( BufferedImage img,  float[][] kernel, int pixelSize)
    {
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < img.getHeight(); y++)
        {
            for (int x = 0; x < img.getWidth(); x++)
            {
                Color outColor = new Color(applyKernel(getPixelColor(img, x, y), kernel).getRGB());
                setNextColorC(img,out,x,y,pixelSize, outColor);
                out.setRGB(x, y, outColor.getRGB());
            }
        }
        return out;
    }

    /*Aplica contraste pros pixels de tamanho especifico*/
    public static void setNextColorC (BufferedImage img, BufferedImage out, int x, int y, int pixelSize, Color cor)
    {
        for (int h = y; h < y + pixelSize ; h++) {
            for (int w = x; w < x + pixelSize ; w++) {
                if(h >= img.getHeight() || w >= img.getWidth())
                    continue;
                out.setRGB(w, h, cor.getRGB());
            }
        }
    }

    static Color getColor (BufferedImage img, int x, int y) {
        if (x < 0 || x >= img.getWidth() || y < 0 || y >= img.getHeight())
            return new Color(0, 0, 0);

        return new Color(img.getRGB(x, y));

    }
    /*Pega as cores dos pixels vizinhos e retorna numa matriz*/
    static Color[][] getPixelColor(BufferedImage img, int x, int y) //lê os pixels vizinhos
    {
        return new Color[][]{
                {getColor(img,x-1, y-1), getColor(img, x, y-1), getColor(img, x+1, y-1)},
                {getColor(img,x-1, y+0), getColor(img, x, y+0), getColor(img, x+1, y+0)},
                {getColor(img,x-1, y+1), getColor(img, x, y+1), getColor(img, x+1, y+1)}

        };

    }

    public static void main(String[] args) throws IOException {
        Atividade2 at2 = new Atividade2();
        at2.run();
        System.out.println("ok :)");
    }
}
