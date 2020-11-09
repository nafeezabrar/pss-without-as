package pss.data.ooi;

import pss.library.geometry.Point;

public class LocationOoi extends Ooi {
    protected final Point point;

    public LocationOoi(int id, String name, Point point) {
        super(id, name);
        this.point = point;
    }


    public Point getPoint() {
        return point;
    }

    @Override
    public String getName() {
        return name + "(" + point.getX() + "," + point.getY() + ")";
    }

    public String getLiteralName() {
        return name;
    }
}
