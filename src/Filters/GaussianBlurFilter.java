package Filters;

import Helpers.Convolutions;
import Interfaces.PixelFilter;
import core.DImage;


public class GaussianBlurFilter implements PixelFilter {


    private final int KERNEL_SIZE = 20;
    private static double[][] kernel = null;

    public GaussianBlurFilter() {
        if (kernel != null) return;
        kernel = new double[KERNEL_SIZE][KERNEL_SIZE];
        int KERNEL_STDEV = 30;
        double kernelSum = 0.0;
        for (int x = 0; x < kernel.length; x++) for (int y = 0; y < kernel[x].length; y++) {
            double dx = (double) x - (KERNEL_SIZE / 2.0), dy = (double) y - (KERNEL_SIZE / 2.0);
            double df = Math.sqrt(dx * dx + dy * dy);
            double density = Math.exp(-0.5 * Math.pow(df / KERNEL_STDEV, 2));
            kernel[x][y] = density;
            kernelSum += density;
        }

        for (int x = 0; x < kernel.length; x++) for (int y = 0; y < kernel[x].length; y++) {
            kernel[x][y] /= kernelSum;
        }
    }

    public DImage processImage(DImage img) {
        int SCALE = 2;
        short [][] r0 = img.getRedChannel();
        short [][] g0 = img.getGreenChannel();
        short [][] b0 = img.getBlueChannel();

        short[][] r = new short[r0.length / SCALE][r0[0].length / SCALE];
        short[][] g = new short[g0.length / SCALE][g0[0].length / SCALE];
        short[][] b = new short[b0.length / SCALE][b0[0].length / SCALE];
        for (int x = 0; x < r.length; x++) for (int y = 0; y < r[x].length; y++) {
            r[x][y] = r0[x*SCALE][y*SCALE];
            g[x][y] = g0[x*SCALE][y*SCALE];
            b[x][y] = b0[x*SCALE][y*SCALE];
        }

        r = Convolutions.Convolve(r, kernel);
        g = Convolutions.Convolve(g, kernel);
        b = Convolutions.Convolve(b, kernel);

        DImage newImg = new DImage(r[0].length, r.length);

        newImg.setRedChannel(r);
        newImg.setGreenChannel(g);
        newImg.setBlueChannel(b);
        return newImg;
    }
}
