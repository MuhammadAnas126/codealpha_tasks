package StockTradingPlatform;
import java.util.Random;

// 1. Stock Class: Represents a single stock in the market
class Stock {
    private String symbol;
    private String name;
    private double price;

    public Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }

    public String getSymbol() { return symbol; }
    public double getPrice() { return price; }
    
    // Simulate price fluctuation
    public void updatePrice() {
        Random rand = new Random();
        // Change price by -5% to +5%
        double changePercent = (rand.nextDouble() * 0.10) - 0.05;
        this.price += this.price * changePercent;
        if (this.price < 1.0) this.price = 1.0; // Minimum price safety
    }

    @Override
    public String toString() {
        return String.format("%-5s | %-15s | $%.2f", symbol, name, price);
    }
}