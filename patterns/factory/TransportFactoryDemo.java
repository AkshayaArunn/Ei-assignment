package main.java.org.example.patterns.factory;

interface Transport { void deliver(); }

class Car implements Transport {
    public void deliver() { System.out.println("Delivering by Car"); }
}
class Bike implements Transport {
    public void deliver() { System.out.println("Delivering by Bike"); }
}
class Bus implements Transport {
    public void deliver() { System.out.println("Delivering by Bus"); }
}

class TransportFactory {
    public static Transport getTransport(String type) {
        switch (type.toLowerCase()) {
            case "car": return new Car();
            case "bike": return new Bike();
            case "bus": return new Bus();
            default: throw new IllegalArgumentException("Unknown transport: " + type);
        }
    }
}

public class TransportFactoryDemo {
    public static void main(String[] args) {
        Transport t1 = TransportFactory.getTransport("car");
        t1.deliver();
        Transport t2 = TransportFactory.getTransport("bike");
        t2.deliver();
    }
}
