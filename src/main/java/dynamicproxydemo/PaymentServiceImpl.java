package dynamicproxydemo;

public class PaymentServiceImpl implements PaymentService {
    @Override
    public void pay(int amount) {
        System.out.println("Paying " + amount + " USD");
    }
}
