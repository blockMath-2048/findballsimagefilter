package Helpers;


import Filters.ColorPassFilter;

import java.util.ArrayList;

public class Cluster {
    public static ColorPassFilter parent;

    public static ArrayList<Point3D> cluster(ArrayList<Point3D> points, int k) {
        ArrayList<Point3D> centers = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            centers.add(Point3D.random());
        }
        parent.print(": 01");
        ArrayList<Point3D> prevCenters;
        int depth = 0;
        do {
            System.out.println(": /" + depth);
            parent.print(": : 01");
            prevCenters = (ArrayList<Point3D>)centers.clone();
            // Clear clusters
            ArrayList<ArrayList<Integer>> clusters = new ArrayList<>();
            for (int i = 0; i < k; i++) {
                clusters.add(new ArrayList<>());
            }
            parent.print(": : 02");
            // Recalculate clusters
            for (Point3D point : points) {
                int bestCenterIndex = 0;
                double bestCenterDst = Double.POSITIVE_INFINITY;
                for (int i = 0; i < centers.size(); i++) {
                    double dst = point.distanceTo(centers.get(i));
                    if (dst < bestCenterDst) {
                        bestCenterDst = dst;
                        bestCenterIndex = i;
                    }
                }
                clusters.get(bestCenterIndex).add(points.indexOf(point));
            }
            parent.print(": : 03");
            // Recalculate points
            for (ArrayList<Integer> cluster : clusters) {
                Point3D newCenter = new Point3D(0.0, 0.0, 0.0);
                for (Integer i : cluster) {
                    newCenter = Point3D.sum(newCenter, points.get(i));
                }
                newCenter = newCenter.mul(1.0 / cluster.size());
                centers.set(clusters.indexOf(cluster), newCenter);
            }
            parent.print(": : 04");
        } while (!centers.equals(prevCenters) && depth++ < 10);
        return centers;
    }
}
