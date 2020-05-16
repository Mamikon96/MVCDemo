import rx.Observable;
import rx.subjects.BehaviorSubject;

import java.util.*;

public class DataService {

    private static DataService instance;

    private Map<Double, Double> points = new HashMap<Double, Double>();

    private BehaviorSubject<Map<Double, Double>> subject;// = BehaviorSubject.create();




    private DataService() {
        subject = BehaviorSubject.create(points);
        initData(6);
    }

    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }


    public Observable<Map<Double, Double>> getData() {
        return subject;
    }


    public void addPoint(Point point) {
        points.put(point.getX(), point.getY());
        subject.onNext(points);
    }

    public void addPoint(double x, double y) {
        points.put(x, y);
        subject.onNext(points);
    }

    public void addPoint(double x) {
        points.put(x, FunctionService.getY(x));
        subject.onNext(points);
    }

    public void deletePoint(Point point) {
        points.remove(point.getX());
        subject.onNext(points);
    }

    public void deletePoint(double x, double y) {
        points.remove(x);
        subject.onNext(points);
    }

    public void deletePoint(double x) {
        points.remove(x);
        subject.onNext(points);
    }

    public void deletePoints(double[] points) {
        for (int i = 0; i < points.length; i++) {
            this.points.remove(points[i]);
        }
        subject.onNext(this.points);
    }

    public void changePoint(double oldX, double newX) {
        points.remove(oldX);
        points.put(newX, FunctionService.getY(newX));
        subject.onNext(points);
    }

    public void initData(int dataCount) {
        clearData();
        for (double i = 0; i < dataCount; i++) {
            points.put(i, FunctionService.getY(i));
        }
        subject.onNext(points);
    }

    public void clearData() {
        points = new HashMap<Double, Double>();
        subject.onNext(points);
    }
}
