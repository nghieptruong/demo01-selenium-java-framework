package dynamicproxydemo;

import java.lang.reflect.Proxy;

public class Client {
    public static void main(String[] args) {
        Subject realSubject = new RealSubject();

        // Tạo InvocationHandler
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(realSubject);

        // Tạo proxy động tại runtime
        Subject proxy = (Subject) Proxy.newProxyInstance(
                Subject.class.getClassLoader(),    // ClassLoader
                new Class[]{ Subject.class },     // Danh sách interface
                myInvocationHandler                         // Handler xử lý mọi method call
        );

        proxy.operation();

        //========================================================
        PaymentService realPayment = new PaymentServiceImpl();
        MyInvocationHandler handler = new MyInvocationHandler(realPayment);

        PaymentService proxyPayment = (PaymentService) Proxy.newProxyInstance(
                PaymentService.class.getClassLoader(),
                new Class[]{ PaymentService.class },
                handler
        );

        proxyPayment.pay(500000);
    }
}
