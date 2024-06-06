package daniel.cabrera.mp03pp03fitxer.classes;

import com.google.zxing.common.BitMatrix;

import java.awt.image.BufferedImage;

public class MatrixToImageConverter {

    // <editor-fold desc="Parametres">
    // Constants
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    //</editor-fold>

    // <editor-fold desc="Mètodes">

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = matrix.get(x, y) ? BLACK : WHITE;
                image.setRGB(x, y, rgb);
            }
        }

        return image;
    }

    //</editor-fold>

}