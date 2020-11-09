package pss.library.geometry;

import static java.lang.Math.hypot;

public class Point {
    protected final int x;
    protected final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double distanceFrom(Point point) {
        int dx = x - point.x;
        int dy = y - point.y;

        return hypot(dx, dy);
    }
}
