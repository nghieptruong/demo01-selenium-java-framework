package staticproxydemo;

public class ConcreteProxy implements Proxy {

    private RealSubject realSubject = new RealSubject();

    @Override
    public void operation() {
        System.out.println("before...");
        realSubject.operation();
        System.out.println("after...");
    }
}
