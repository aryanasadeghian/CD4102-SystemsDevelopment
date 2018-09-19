/**
 * StockException.java
 */

package stockexchangeapplication;

/**
 * The StockException is designed to handle logical runtime exceptions 
 * specific to the Stock Exchange app.
 * <p>
 * <b>Module:</b> CD4102 - Systems Development<br>
 * <b>Due:</b> 25 April 2018
 * </p>
 * 
 * @author Accenture
 * @author Aryana Sadeghian
 * @author 1734167
 * 
 * @see StockExchangeApplication
 */
public class StockException extends RuntimeException {
//********************************************************************
// VARIABLES
//********************************************************************    
    private String exceptionTitle; // Title of the exception
    
    
//********************************************************************
// CONSTRUCTORS
//********************************************************************
    /**
     * This is the default constructor for the StockException class.
     * A generic exception message will be generated.
     */  
    public StockException() {
        super("An error has occured within the Stock Exchange app.");
    }
    
    /**
     * Use this constructor to display a specific exception message.
     * 
     * @param message the specified exception message
     */  
    public StockException(String message) {
        super(message);
    }
    
    /**
     * Use this constructor to display a specific exception message and assign a title.
     * 
     * @param message the specified exception message
     * @param title the title of the exception
     */  
    public StockException(String message, String title) {
        super(message);
        this.exceptionTitle = title;
    }
    
    
//********************************************************************
// METHODS
//********************************************************************
    /**
     * Returns the title of the exception.
     * 
     * @return the title of the exception
     */
    public String getTitle() {
        return this.exceptionTitle;
    }
    
}
