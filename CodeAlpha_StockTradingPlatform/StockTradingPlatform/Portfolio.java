package StockTradingPlatform;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

class Portfolio implements Serializable {
    private double balance;
    private HashMap<String, Integer> holdings; // Symbol -> Quantity

    public Portfolio(double initialBalance) {
        this.balance = initialBalance;
        this.holdings = new HashMap<>();
    }

    public double getBalance() {
        return balance;
    }

    public Map<String, Integer> getHoldings() {
        return holdings;
    }

    public void buyStock(String symbol, double price, int quantity) {
        double cost = price * quantity;
        if (cost <= balance) {
            balance -= cost;
            holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
            System.out.println("Successfully bought " + quantity + " shares of " + symbol);
        } else {
            System.out.println("Insufficient funds! You need $" + String.format("%.2f", cost));
        }
    }

    public void sellStock(String symbol, double price, int quantity) {
        if (holdings.containsKey(symbol) && holdings.get(symbol) >= quantity) {
            double revenue = price * quantity;
            balance += revenue;
            int remaining = holdings.get(symbol) - quantity;
            if (remaining == 0) {
                holdings.remove(symbol);
            } else {
                holdings.put(symbol, remaining);
            }
            System.out.println("Successfully sold " + quantity + " shares of " + symbol);
        } else {
            System.out.println("Not enough shares to sell.");
        }
    }
}