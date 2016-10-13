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
public class ElasticRodChart extends ElasticRod {

    double dForce = 0.01;
    double yPrecision = 0.01;
    double tetaPrecision = 0.1;
    int changedDirectionIndex = 0;//TODO: enum, set value for boundary values
    boolean rodPointingUp = true;
    double vDivider = 1000.0;
    double difference = 0.2;
    double multipler = 1.5;
    double divider = 10.0;

    double tetaNDeg;
    double teta0Deg;

    public static final double FULL_ANGLE = 360.0;
    public static final double HALF_FULL_ANGLE = FULL_ANGLE / 2;
    public static final double MAX_FORCE_VALUE = 1000.0;

    public ElasticRodChart(double startingTeta, double startingV) {
        super(startingTeta, startingV);
        calculateForce();
    }

    private void calculateRodCoordinates() {
        for (int i = 1; i < N; i++) {
            teta[i] = teta[i - 1] + v[i - 1] * DS;
            v[i] = v[i - 1] - force * Math.sin(teta[i]) * DS;
            coordinates[i] = new Coordinate((getPreviousX(i) + Math.cos(teta[i]) * DS),
                    (getPreviousY(i) + Math.sin(teta[i]) * DS));

            if (rodPointingUp) {
                if (isRodGoingDown(i)) {
                    changedDirectionIndex++;
                    rodPointingUp = false;
                }
            } else if (isRodGoingUp(i)) {
                changedDirectionIndex++;
                rodPointingUp = true;
            }
        }

        dForce = setdP(coordinates[N - 1].getY());
        difference = coordinates[N - 1].getY();
    }

    private boolean isRodGoingUp(int i) {
        return coordinates[i - 1].getY() < coordinates[i].getY();
    }

    private boolean isRodGoingDown(int i) {
        return coordinates[i - 1].getY() > coordinates[i].getY();
    }

    private double getPreviousX(int i) {
        return coordinates[i - 1].getX();
    }

    private double getPreviousY(int i) {
        return coordinates[i - 1].getY();
    }

    private double setdP(double yN) {
        if (Math.abs(difference - yN) > 0.005) {
            dForce /= divider;
        } else if (Math.abs(difference - yN) < 0.001) {
            dForce *= multipler;
        }
        return dForce;
    }

    private void calculateForce() {
        if (isRodStraight()) {
            force = 0.0;
            calculateRodCoordinates();
        } else {
            force += dForce;
            calculateRodCoordinates();
            tetaNDeg = toDegrees(teta[N - 1]);
            teta0Deg = toDegrees(teta[0]);
            if (force < MAX_FORCE_VALUE) {
                if (isNotPrecise()) {
                    changedDirectionIndex = 0;
                    calculateForce();
                }
                solveForLoopedRod();
            } else {
                System.out.println("Cannot find force!");
            }
        }
    }

    private void solveForLoopedRod() {
        if (isRodLooped()) {
            changedDirectionIndex = 0;
            calculateForce();
        } else {
            if (tetaNDeg < 0) {
                if (Math.abs(tetaNDeg + teta0Deg) > tetaPrecision) {
                    changedDirectionIndex = 0;
                    calculateForce();
                }
            }
            if (tetaNDeg > 0) {
                if (Math.abs(tetaNDeg - FULL_ANGLE + teta0Deg) > tetaPrecision) {
                    changedDirectionIndex = 0;
                    calculateForce();
                }
            }
        }
    }

    private boolean isRodLooped() {
        return changedDirectionIndex != 2;
    }

    private double toDegrees(double degrees) {
        return degrees * Math.PI / HALF_FULL_ANGLE;
    }

    private boolean isRodStraight() {
        return teta[0] == 0 && v[0] == 0;
    }

    private boolean isNotPrecise() {
        return Math.abs(coordinates[N - 1].getY()) > yPrecision;
    }
}
