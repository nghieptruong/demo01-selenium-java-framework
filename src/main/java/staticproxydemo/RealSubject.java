package staticproxydemo;

public class RealSubject implements Subject {

    @Override
    public void operation() {
        System.out.println("RealSubject working...");
    }
}
