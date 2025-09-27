package main.java.org.example.patterns.observer;

import java.util.*;

interface Observer {
    void update(String stock, double price);
}

class StockMarket {
    private Map<String, Double> prices = new HashMap<>();
    private List<Observer> observers = new ArrayList<>();

    void addObserver(Observer obs) { observers.add(obs); }
    void removeObserver(Observer obs) { observers.remove(obs); }

    void setPrice(String stock, double price) {
        prices.put(stock, price);
        notifyAllObservers(stock, price);
    }

    private void notifyAllObservers(String stock, double price) {
        for (Observer obs : observers) {
            obs.update(stock, price);
        }
    }
}

class MobileApp implements Observer {
    public void update(String stock, double price) {
        System.out.println("MobileApp → " + stock + " is now $" + price);
    }
}

class WebDashboard implements Observer {
    public void update(String stock, double price) {
        System.out.println("WebDashboard → " + stock + " is now $" + price);
    }
}

public class StockMarketDemo {
    public static void main(String[] args) {
        StockMarket market = new StockMarket();
        market.addObserver(new MobileApp());
        market.addObserver(new WebDashboard());

        market.setPrice("AAPL", 145.5);
        market.setPrice("GOOG", 2780.0);
    }
}

