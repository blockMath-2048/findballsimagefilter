package Helpers;

public class Point3D {
    public double x, y, z;

    public Point3D(double _x, double _y, double _z) {
        x = _x;
        y = _y;
        z = _z;
    }

    public static Point3D random() {
        return new Point3D(Math.random() * 256, Math.random() * 256, Math.random() * 256);
    }

    public double distanceTo(Point3D other) {
        return sum(this, other.mul(-1)).magnitude();
    }

    public double magnitude() {
        return Math.sqrt(x*x+y*y+z*z);
    }

    public static Point3D sum(Point3D a, Point3D b) {
        return new Point3D(a.x+b.x, a.y+b.y, a.z+b.z);
    }

    public Point3D mul(double f) {
        return new Point3D(x * f, y * f, z * f);
    }

    public boolean equals(Point3D other) {
        return (x == other.x && y == other.y && z == other.z);
    }
}
