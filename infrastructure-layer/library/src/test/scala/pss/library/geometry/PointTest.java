package pss.library.geometry;

import org.junit.Test;

import static java.lang.Math.sqrt;
import static org.junit.Assert.assertEquals;

public class PointTest {

    private final double delta = 0.0000001;

    @Test
    public void createPoint() {
        new Point(1, 2);
    }

    @Test
    public void calculateDistanceCorrectly() throws Exception {
        Point point1 = new Point(1, 2);
        Point point2 = new Point(2, 1);
        Point point3 = new Point(3, 4);

        assertDistance(point1, point1, 0);
        assertDistance(point1, point2, sqrt(2));
        assertDistance(point1, point3, 2 * sqrt(2));
        assertDistance(point2, point3, sqrt(10));
    }

    private void assertDistance(Point point1, Point point2, double expectedDistance) {
        double actualDistance = point1.distanceFrom(point2);

        assertEquals(expectedDistance, actualDistance, delta);
    }
}
