package Filters;

import Helpers.Cluster;
import Helpers.Point3D;
import Interfaces.PixelFilter;
import core.DImage;

import java.util.ArrayList;

public class ColorPassFilter implements PixelFilter {
    long st, st0;
    public void print(String s) {
        System.out.println(s + " (" + (System.currentTimeMillis() - st) + " ms)");
        st = System.currentTimeMillis();
    }

    public DImage processImage(DImage img) {
        Cluster.parent = this;
        st = System.currentTimeMillis();
        st0 = st;
        int K = 10;

        short [][] r = img.getRedChannel();
        short [][] g = img.getGreenChannel();
        short [][] b = img.getBlueChannel();
        print("01");
        ArrayList<Point3D> points = new ArrayList<>();
        Point3D[][] bmp = new Point3D[img.getHeight()][img.getWidth()];
        for (int y = 0; y < img.getHeight(); y++) for (int x = 0; x < img.getWidth(); x++) {
            bmp[y][x] = new Point3D(r[y][x], g[y][x], b[y][x]);
            points.add(bmp[y][x]);
        }
        print("02");
        ArrayList<Point3D> centers = Cluster.cluster(points, K);
        print("03");
        for (int y = 0; y < img.getHeight(); y++) for (int x = 0; x < img.getWidth(); x++) {
            Point3D bestCenter = new Point3D(Double.NaN, Double.NaN, Double.NaN);
            double bestDst = Double.POSITIVE_INFINITY;
            for (Point3D center : centers) {
                double dst = bmp[y][x].distanceTo(center);
                if (dst < bestDst) {
                    bestCenter = center;
                    bestDst = dst;
                }
            }
            bmp[y][x] = bestCenter;
        }
        print("04");
        for (int y = 0; y < img.getHeight(); y++) for (int x = 0; x < img.getWidth(); x++) {
            r[y][x] = (short)bmp[y][x].x;
            g[y][x] = (short)bmp[y][x].y;
            b[y][x] = (short)bmp[y][x].z;
        }
        print("05");
        img.setRedChannel(r);
        img.setGreenChannel(g);
        img.setBlueChannel(b);
        print("06");
        System.out.println("Operation completed in " + (System.currentTimeMillis() - st0) + " ms");
        return img;
    }
}
