package main.java.org.example.patterns.adapter;

interface PayPalGateway {
    void payWithPayPal(double amount);
}

class OldPayPal implements PayPalGateway {
    public void payWithPayPal(double amount) {
        System.out.println("Paid $" + amount + " via PayPal");
    }
}

interface PaymentGateway {
    void pay(double amount);
}

class StripePayment {
    public void makeStripePayment(double amount) {
        System.out.println("Paid $" + amount + " via Stripe");
    }
}

class StripeAdapter implements PaymentGateway {
    private StripePayment stripe = new StripePayment();
    public void pay(double amount) { stripe.makeStripePayment(amount); }
}

public class PaymentAdapterDemo {
    public static void main(String[] args) {
        OldPayPal old = new OldPayPal();
        old.payWithPayPal(100);

        PaymentGateway gateway = new StripeAdapter();
        gateway.pay(250);
    }
}
