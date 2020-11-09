package pss.variable.generation;

import pss.data.ooi.LocationOoi;
import pss.data.ooi.Ooi;
import pss.library.geometry.Point;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class LocationMinAreaOoiGenerator implements OoiGenerable {
    @Override
    public Set<Ooi> generateOoi(int n, int startOoiId) {
        Set<Point> visitedPoints = new HashSet<>();
        int neighborX[] = {1, 0, -1, 0, 1, -1, 1, -1, 2, 0, -2, 0, 2, 2, 1, -1, -2, -2, 1, -1};
        int neighborY[] = {0, 1, 0, -1, 1, 1, -1, -1, 0, 2, 0, -2, 1, -1, 2, 2, 1, -1, -2, -2};
        int totalNeighbors = 20;
        Set<Ooi> oois = new HashSet<>();
        PriorityQueue<Point> queue = new PriorityQueue<>((p1, p2) -> {
            if (p1.getX() == p2.getX())
                return Integer.compare(p1.getY(), p2.getY());
            return Integer.compare(p1.getX(), p2.getX());
        });
        Point p = new Point(0, 0);
        visitedPoints.add(p);
        int locationOoiId = 0;
        oois.add(new LocationOoi(startOoiId, getOoiName(locationOoiId), p));
        startOoiId++;
        locationOoiId++;
        queue.add(p);
        for (int i = 1; i < n; ) {
            Point point = queue.poll();
            for (int j = 0; j < totalNeighbors && i < n; j++) {
                int newX = point.getX() + neighborX[j];
                int newY = point.getX() + neighborY[j];
                Point locationPoint = new Point(newX, newY);
                if (!visitedPoints.contains(locationPoint)) {

                    LocationOoi locationOoi = new LocationOoi(startOoiId, getOoiName(locationOoiId), locationPoint);
                    startOoiId++;
                    locationOoiId++;
                    oois.add(locationOoi);
                    queue.add(locationOoi.getPoint());
                    i++;
                }
            }
        }
        return oois;
    }

    private String getOoiName(int locationOoiId) {
        return String.valueOf((char) (locationOoiId + 'a'));
    }
}
