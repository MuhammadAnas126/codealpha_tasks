package StockTradingPlatform;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StockTradingPlatform {

    private static Map<String, Stock> market = new HashMap<>();
    private static Portfolio portfolio;
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "portfolio_data.ser";

    public static void main(String[] args) {
        initializeMarket();
        loadPortfolio();

        System.out.println("Welcome to the Stock Trading Platform!");

        boolean running = true;
        while (running) {
            System.out.println("\n--- DASHBOARD ---");
            System.out.println("Cash Balance: $" + String.format("%.2f", portfolio.getBalance()));
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio Performance");
            System.out.println("5. Exit & Save");
            System.out.print("Enter choice: ");

            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {
                case 1:
                    displayMarket();
                    break;
                case 2:
                    handleBuy();
                    break;
                case 3:
                    handleSell();
                    break;
                case 4:
                    displayPortfolio();
                    break;
                case 5:
                    savePortfolio();
                    System.out.println("Data saved. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // -- Helper Methods --

    private static void initializeMarket() {
        market.put("AAPL", new Stock("AAPL", "Apple Inc.", 150.00));
        market.put("GOOGL", new Stock("GOOGL", "Alphabet Inc.", 2800.00));
        market.put("AMZN", new Stock("AMZN", "Amazon.com", 3400.00));
        market.put("TSLA", new Stock("TSLA", "Tesla Inc.", 700.00));
        market.put("MSFT", new Stock("MSFT", "Microsoft", 299.00));
    }

    private static void displayMarket() {
        System.out.println("\n--- Market Data (Prices Updated!) ---");
        System.out.printf("%-5s | %-15s | %s%n", "SYM", "NAME", "PRICE");
        System.out.println("-------------------------------------");
        for (Stock stock : market.values()) {
            stock.updatePrice(); // Simulate market movement
            System.out.println(stock);
        }
    }

    private static void handleBuy() {
        System.out.print("Enter Stock Symbol to Buy (e.g., AAPL): ");
        String symbol = scanner.nextLine().toUpperCase();

        if (market.containsKey(symbol)) {
            Stock stock = market.get(symbol);
            System.out
                    .println("Current Price of " + stock.getSymbol() + ": $" + String.format("%.2f", stock.getPrice()));
            System.out.print("Enter quantity: ");
            try {
                int qty = Integer.parseInt(scanner.nextLine());
                portfolio.buyStock(symbol, stock.getPrice(), qty);
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity.");
            }
        } else {
            System.out.println("Stock not found.");
        }
    }

    private static void handleSell() {
        System.out.print("Enter Stock Symbol to Sell: ");
        String symbol = scanner.nextLine().toUpperCase();

        if (portfolio.getHoldings().containsKey(symbol)) {
            Stock stock = market.get(symbol); // Get current market price
            System.out.println("Current Market Price: $" + String.format("%.2f", stock.getPrice()));
            System.out.print("Enter quantity to sell: ");
            try {
                int qty = Integer.parseInt(scanner.nextLine());
                portfolio.sellStock(symbol, stock.getPrice(), qty);
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity.");
            }
        } else {
            System.out.println("You do not own this stock.");
        }
    }

    private static void displayPortfolio() {
        System.out.println("\n--- Your Portfolio ---");
        System.out.printf("%-10s | %-10s | %-15s | %s%n", "SYMBOL", "QTY", "CURRENT PRICE", "TOTAL VALUE");
        System.out.println("------------------------------------------------------------");

        double totalAssetValue = 0;
        Map<String, Integer> holdings = portfolio.getHoldings();

        if (holdings.isEmpty()) {
            System.out.println("No stocks owned.");
        } else {
            for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
                String symbol = entry.getKey();
                int qty = entry.getValue();
                double currentPrice = market.get(symbol).getPrice();
                double value = currentPrice * qty;
                totalAssetValue += value;

                System.out.printf("%-10s | %-10d | $%-14.2f | $%.2f%n",
                        symbol, qty, currentPrice, value);
            }
        }

        double netWorth = totalAssetValue + portfolio.getBalance();
        System.out.println("------------------------------------------------------------");
        System.out.println("Cash Balance:   $" + String.format("%.2f", portfolio.getBalance()));
        System.out.println("Stock Assets:   $" + String.format("%.2f", totalAssetValue));
        System.out.println("Total Net Worth: $" + String.format("%.2f", netWorth));
    }

    // -- File I/O --

    private static void savePortfolio() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(portfolio);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private static void loadPortfolio() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                portfolio = (Portfolio) ois.readObject();
                System.out.println("Portfolio loaded. Welcome back!");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading portfolio. Creating new one.");
                portfolio = new Portfolio(10000.00); // Start with $10k
            }
        } else {
            portfolio = new Portfolio(10000.00); // Start with $10k
        }
    }
}