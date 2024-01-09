package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class IdentityFilter implements PixelFilter {
    public DImage processImage(DImage img) {
        short [][] pixels = img.getBWPixelGrid();

        img.setPixels(pixels);
        return img;
    }
}
