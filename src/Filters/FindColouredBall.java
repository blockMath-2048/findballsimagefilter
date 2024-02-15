package Filters;

import Helpers.Point3D;
import Interfaces.PixelFilter;
import core.DImage;

public class FindColouredBall implements PixelFilter {
    public GaussianBlurFilter blurFilter;
    public FindColouredBall() {
        blurFilter = new GaussianBlurFilter();
    }
    public DImage processImage(DImage img) {
        DImage blurredImage = blurFilter.processImage(img);
        int cX = -1, cY = -1;
        double closestDist = Double.MAX_VALUE;
        Point3D findingColor = new Point3D(255, 0, 0);
        short[][] r = blurredImage.getRedChannel();
        short[][] g = blurredImage.getGreenChannel();
        short[][] b = blurredImage.getBlueChannel();
        for (int x = 0; x < r.length; x++) for (int y = 0; y < r[x].length; y++) {
            Point3D color = new Point3D(r[x][y], g[x][y], b[x][y]);
            double dist = color.distanceTo(findingColor);
            if (dist < closestDist) {
                closestDist = dist;
                cX = x;
                cY = y;
            }
            //r[x][y] = 0;
            //g[x][y] = 0;
            //b[x][y] = 0;
        }
        r[cX][cY] = g[cX][cY] = b[cX][cY] = 255;
        blurredImage.setRedChannel(r);
        blurredImage.setGreenChannel(g);
        blurredImage.setBlueChannel(b);
        return blurredImage;
    }
}
