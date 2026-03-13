package staticproxydemo;

public class Client {
    public static void main(String[] args) {
        Subject subject = new ConcreteProxy();
        subject.operation();
    }
}
