package Helpers;

public class Convolutions {
    public static final boolean SHOULD_ASSERT_UNIT_KERNEL = false;
    public static short[][] Convolve(short[][] obj, double[][] kernel) {
        if (SHOULD_ASSERT_UNIT_KERNEL) {
            double sum = 0.0;
            for (double[] arr : kernel) for (double d : arr) sum += d;
            assert (sum > 0.999 && sum < 1.001);
        }

        short[][] result = new short[obj.length - kernel.length + 1][obj[0].length - kernel[0].length + 1];
        for (int i = 0; i < result.length; i++) for (int j = 0; j < result[0].length; j++) {
            double value = 0.0;
            for (int x = 0; x < kernel.length; x++) for (int y = 0; y < kernel[0].length; y++) {
                value += (kernel[x][y] * obj[i+x][j+y]);
            }
            result[i][j] = (short)value;
        }
        return result;
    }
}

