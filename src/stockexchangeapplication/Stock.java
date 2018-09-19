/**
 * Stock.java
 */

package stockexchangeapplication;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;


/**
 * The Stock class holds details about a stock and contains methods that allow 
 * the user to buy and sell shares of a stock.
 * <p>
 * <b>Module:</b> CD4102 - Systems Development<br>
 * <b>Due:</b> 23 April 2018
 * </p>
 * 
 * @author Accenture
 * @author Aryana Sadeghian
 * @author 1734167
 */
public class Stock {  
//********************************************************************
// VARIABLES
//********************************************************************
    private StringProperty name; // Company name of the stock
    private IntegerProperty shares; // Quantity of stock
    private DoubleProperty price; // Unit price of stock   
    private DoubleProperty totalValue; // Total value of stock
    
    
//********************************************************************
// CONSTRUCTORS
//********************************************************************
    /**
     * This constructor allows the user to specify the
     * company name and unit price of the stock.
     * Used for personal stock.
     * 
     * @param nameIn the company name of the stock
     * @param priceIn the unit price of the stock
     */
    public Stock(String nameIn, double priceIn) {
        name = new SimpleStringProperty(nameIn);
        shares = new SimpleIntegerProperty(0);
        priceIn = roundDouble(priceIn);
        price = new SimpleDoubleProperty(priceIn);
        totalValue = new SimpleDoubleProperty(0);
    }
    
    /**
     * This constructor allows the user to specify the
     * company name, unit price, and the stock quantity.
     * Used for stock on the Stock Exchange.
     * 
     * @param nameIn the name of the stock
     * @param priceIn the value of the stock
     * @param sharesIn the number of stock
     */
    public Stock(String nameIn, double priceIn, int sharesIn) {
        name = new SimpleStringProperty(nameIn);
        shares = new SimpleIntegerProperty(sharesIn);
        priceIn = roundDouble(priceIn);
        price = new SimpleDoubleProperty(priceIn);
        double initialTotalValue = price.get() * shares.get();
        initialTotalValue = roundDouble(initialTotalValue);
        totalValue = new SimpleDoubleProperty(initialTotalValue);
    }
    
    
//********************************************************************
// METHODS
//********************************************************************
///// FUNCTIONAL METHODS
    /**
     * Buy shares of a stock.
     * Returns the total value of the shares to be bought and decreases the 
     * amount of shares by the number bought.
     * 
     * @param numOfShares number of shares to buy
     * 
     * @return total value of the shares bought
     */
    public double buyShares(int numOfShares) {
    /// Not enough shares
        if(numOfShares > shares.get()) {
            throw new StockException(
                            "There is not enough of this stock to meet your demand.\n\n" +
                            "Please enter a new quantity.\n\n" +
                            "Current number of " + name.get() + " shares: " + shares.get(),        
                            "Insufficient Stock Availability");                    
        }
        
    /// Update quantity of shares
        decreaseShares(numOfShares);
    
    /// Update total value of stock
        calculateStockValue();    
        
    /// Calculate and return buy value
        double buyValue = price.get() * numOfShares;
        buyValue = roundDouble(buyValue);
        return buyValue;
    }
    
    /**
     * Buy shares of a stock.
     * Returns the total value of the shares to be bought and decreases the 
     * amount of shares by the number bought. Also checks account balance.
     * 
     * @param numOfShares number of shares to buy
     * @param accountBalance the account balance of the user
     * 
     * @return total value of the shares bought
     */
    public double buyShares(int numOfShares, double accountBalance) {
    /// Not enough shares
        if(numOfShares > shares.get()) {
            throw new StockException(
                            "There is not enough of this stock to meet your demand.\n\n" +
                            "Please enter a new quantity.\n\n" +
                            "Current number of " + name.get() + " shares: " + shares.get(),        
                            "Insufficient Stock Availability");                    
        }
    /// Calculate buy value
        double buyValue = price.get() * numOfShares;
        buyValue = roundDouble(buyValue);
        
    /// Insufficient account balance
        if(accountBalance < buyValue) {
            throw new StockException(
                            "You do not have enough funds to purchase this stock.\n\n" + 
                            "Please enter a new quantity, or deposit more funds into\n" +
                            "your account and try again.\n\n" +
                            "Your balance: " + accountBalance + "\nPurchase value: " + buyValue +
                            "\nFunds needed: " + (Stock.roundDouble(buyValue - accountBalance)),
                            "Insufficient Account Balance");
        }
        
    /// Update quantity of shares
        decreaseShares(numOfShares);
    
    /// Update total value of stock
        calculateStockValue();    
        
    
        return buyValue;
    }
    
    /**
     * Sell shares of a stock.
     * Returns the total value of the shares to be sold.
     * 
     * @param numOfShares number of shares to sell
     * 
     * @return total value of the shares sold
     */
    public double sellShares(int numOfShares) {
    /// Not enough shares
        if(numOfShares > shares.get()) {
            throw new StockException(
                            "You do not have enough shares of this stock.\n\n" +
                            "Please enter a new quantity.\n\n" +
                            "Current number of " + name.get() + " shares: " + shares.get(),
                            "Insufficient Shares");   
        }
        
    /// Update quantity of shares
        decreaseShares(numOfShares);
    
    /// Update total value of stock
        calculateStockValue();
        
    /// Calculate and return sell value    
        double sellValue = price.get() * numOfShares;
        sellValue = roundDouble(sellValue);
        return sellValue;
    }    
     
    /**
     * Increases the number of shares by the specified amount.
     * 
     * @param increment the amount to increment by
     */
    public void increaseShares(int increment) {
        shares.set(getShares() + increment);
        calculateStockValue();
    }
    
    /**
     * Decreases the number of shares by the specified amount.
     * 
     * @param increment the amount to decrement by
     */
    public void decreaseShares(int decrement) {
        shares.set(getShares() - decrement);
        calculateStockValue();
    }

    /**
     * Provides a string output with the stock details.
     * 
     * @return details of the given stock
     */
        @Override
        public String toString() {
            return "Stock name: " + name.get() +
                   "\nStock price: " + price.get() +
                   "\nStock quantity: " + shares.get() +
                   "\nTotal value: " + totalValue.get();
        }
        
    /**
     * Rounds a double value to 2dp.
     * 
     * @param doubleIn the value to be rounded
     * 
     * @return rounded double value
     */
     public static double roundDouble(double doubleIn) {
         doubleIn = (double) Math.round(doubleIn * 100) / 100;
         return doubleIn;
     }

///// GETTERS & SETTERS
    /**
     * Get the company name of the stock.
     * 
     * @return company name of stock
     */
        public String getName() {
            return name.get();
        }
        
    /**
     * Set the company name of the stock.
     */
        public void setName(String nameIn) {
            name.set(nameIn);
        }
        
    /**
     * Get the name property of the stock.
     * 
     * @return the name property of the stock
     */
        public StringProperty nameProperty() {
            return name;
        }
        
    /**
     * Get the number of shares in the stock.
     * 
     * @return number of shares in the stock
     */    
        public int getShares() {
            return shares.get();
        }
    
    /**
     * Set the number of shares in the stock.
     * 
     * @param numOfShares number of shares to set in the stock
     */
        public void setShares(int numOfShares) {
            shares.set(numOfShares);
            calculateStockValue();
        }
 
    /**
     * Get the shares property of the stock.
     * 
     * @return the shares property of the stock
     */
        public IntegerProperty sharesProperty() {
            return shares;
        }
        
    /**
     * Get the unit price of the stock.
     * 
     * @return unit price of the stock.
     */    
        public double getPrice() {
            return price.get();
        }
        
    /**
     * Set the unit price of the stock.
     * 
     * @param priceIn unit price of the stock
     */
        public void setPrice(double priceIn) {
            priceIn = roundDouble(priceIn);
            price.set(priceIn);
            calculateStockValue();
        }
    
    /**
     * Get the price property of the stock.
     * 
     * @return the price property of the stock
     */
        public DoubleProperty priceProperty() {
            return price;
        }  
        
    /**
     * Get the total value of shares held in the stock.
     * 
     * @return total value of the shares held in the stock
     */
        public double getStockValue() {
            return totalValue.get();
        }
        
    /**
     * Calculate the total value of the shares of the stock.
     * Multiplies stock price by quantity of shares.
     */
        public void calculateStockValue() {
            double stockValue = price.get() * shares.get();
            stockValue = roundDouble(stockValue);
            totalValue.set(stockValue);
        }    
        
    /**
     * Get the total value property of the stock.
     * 
     * @return the total value property of the stock
     */
        public DoubleProperty totalValueProperty() {
            return totalValue;
        }    
}
