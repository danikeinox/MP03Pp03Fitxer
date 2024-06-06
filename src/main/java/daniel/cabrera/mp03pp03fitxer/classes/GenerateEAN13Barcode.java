package daniel.cabrera.mp03pp03fitxer.classes;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;

import java.awt.image.BufferedImage;
import java.util.Random;

public class GenerateEAN13Barcode {

    // <editor-fold defaultstate="collapsed" desc="Variables">
    // Constants
    private static final int BARCODE_HEIGHT = 100;
    private static final int BARCODE_WIDTH = 200;

    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Mètodes">

    public static BufferedImage generateEAN13BarcodeImage(String barcodeText) throws WriterException {
        EAN13Writer barcodeWriter = new EAN13Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.EAN_13, BARCODE_WIDTH, BARCODE_HEIGHT);

        return MatrixToImageConverter.toBufferedImage(bitMatrix);
    }

    public static String generateEAN13Code() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            sb.append(random.nextInt(10));
        }

        String ean13 = sb.toString();
        ean13 += calculateCheckDigit(ean13);

        return ean13;
    }

    private static String calculateCheckDigit(String code) {
        int sum = 0;

        for (int i = 0; i < 12; i++) {
            int digit = Integer.parseInt(String.valueOf(code.charAt(i)));

            if (i % 2 == 0) {
                sum += digit;
            } else {
                sum += 3 * digit;
            }
        }

        int checkDigit = 10 - (sum % 10);
        if (checkDigit == 10) {
            checkDigit = 0;
        }

        return String.valueOf(checkDigit);
    }

    //</editor-fold>

}