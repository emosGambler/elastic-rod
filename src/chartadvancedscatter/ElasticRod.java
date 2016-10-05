/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chartadvancedscatter;

/**
 *
 * @author Jacek Góraj
 */
public class ElasticRod {

    public static final int N = 258;
    public static final double RODS_LENGTH = 1.0;
    public static final double DS = RODS_LENGTH / N;

    double[] teta = new double[N];
    double[] v = new double[N];
    Coordinate[] coordinates = new Coordinate[N];

    double force;

    ElasticRod(double startingTeta, double startingV) {
        this.teta[0] = startingTeta;
        this.v[0] = startingV;
        this.force = 0.0;
        this.coordinates[0] = new Coordinate(0.0, 0.0);
    }

    ElasticRod(double startingTeta, double startingV, double startingForce, Coordinate startingCoordinates) {
        this.teta[0] = startingTeta;
        this.v[0] = startingV;
        this.force = startingForce;
        this.coordinates[0] = startingCoordinates;
    }

    private Coordinate getCoordinates(int index) {
        return coordinates[index];
    }

    public double getXCoordinate(int index) {
        return getCoordinates(index).getX();
    }

    public double getYCoordinate(int index) {
        return getCoordinates(index).getY();
    }
}
