/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockexchangeapplication;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import javafx.application.Application;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author aryana.m.sadeghian
 */

public class StockExchangeApplication extends Application {
//********************************************************************
// VARIABLES
//********************************************************************
    private DoubleProperty accountBalance;
    private ObservableList<Stock> exchangeData = FXCollections.observableArrayList();
    private ObservableList<Stock> accountData = FXCollections.observableArrayList();
    private ObservableList<Shortcut> shortcuts = FXCollections.observableArrayList();

    
//********************************************************************
// RUN APPLICATION
//********************************************************************    
    @Override
    public void start(Stage primaryStage) {
    //********************************************************************
    // INITIAL SETUP
    //********************************************************************
        primaryStage.setTitle("Accenture Stock Exchange: 1734167");
        Group root = new Group();
        Scene scene = new Scene(root, 693, 332);
        BorderPane borderPane = new BorderPane();
        
        
    //********************************************************************
    // DATA
    //********************************************************************
    /// Account Balance
        accountBalance = new SimpleDoubleProperty(1000);
    
    /// Exchange Stock List
        exchangeData.add(new Stock("Accenture", 122, 100));
        exchangeData.add(new Stock("Amazon", 1544.92, 100));
        exchangeData.add(new Stock("Apple", 168.84, 100));
        exchangeData.add(new Stock("Google", 1053.15, 100));
        exchangeData.add(new Stock("Facebook", 164.89, 100));
        exchangeData.add(new Stock("IBM", 153.90, 100));
        exchangeData.add(new Stock("Intel", 50.83, 100));
        exchangeData.add(new Stock("Samsung", 1158.71, 100));
        exchangeData.add(new Stock("Nintendo", 50.83, 100));
        exchangeData.add(new Stock("Twitter", 31.20, 100));
        exchangeData.add(new Stock("NVIDIA", 241.85, 100));
        exchangeData.add(new Stock("AMD", 10.91, 100));
        exchangeData.add(new Stock("Microsoft", 89.79, 100));
    
    /// Keyboard Shortcuts
        shortcuts.add(new Shortcut("Buy Shares", "CTRL + B"));
        shortcuts.add(new Shortcut("Sell Shares", "CTRL + S"));
        shortcuts.add(new Shortcut("Add Stock", "CTRL + N"));
        shortcuts.add(new Shortcut("Remove Stock", "CTRL + X"));
        shortcuts.add(new Shortcut("Set Stock Price", "CTRL + C"));
        shortcuts.add(new Shortcut("Deposit Funds", "CTRL + F"));
        shortcuts.add(new Shortcut("About", "CTRL + I"));
        shortcuts.add(new Shortcut("Manual", "CTRL + H"));
        shortcuts.add(new Shortcut("Shortcuts", "CTRL + Z"));
        shortcuts.add(new Shortcut("Settings", "CTRL + P"));
        shortcuts.add(new Shortcut("Quit", "CTRL + Q"));

        
    //********************************************************************
    // MENU SETUP
    //********************************************************************    
     // SYSTEM MENU       
        Menu systemMenu = new Menu("System");
        MenuItem systemAboutMenuItem;
        MenuItem systemQuitMenuItem;
        
      // About
        systemAboutMenuItem = new MenuItem("About");
        systemAboutMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+I"));
        systemAboutMenuItem.setOnAction((ActionEvent t) -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("About");
            alert.setHeaderText("About: Stock Exchange Application");
            alert.initModality(Modality.NONE);
            alert.getDialogPane().setPrefWidth(600);
            alert.setResizable(true);
            alert.setContentText(
                    "This Stock Exchange Application was created by Aryana Sadeghian (1734167) as part of module CD4102 coursework for Accenture apprentices " +
                    "due 23 April 2018.\n\n" +
                    "This application features an 'Exchange' tab which lists the details of stock that the user can purchase. By default this list " +
                    "is pre-populated with some data. The user may also add or remove stock as well as reprice stock on this tab. It is worth noting that " +
                    "the user must have sufficient funds to purchase shares.\n\n" +
                    "In addition to the Exchange tab there is an 'Account' tab which lists all the shares that the user has purchased. Here the user " +
                    "can choose to sell back shares that they have purchased, which will then go back onto the exchange. The user may also choose to " +
                    "deposit more funds to increase their account balance.\n\n" +
                    "For further information on how to use this application, please refer to the manual which can be found under the 'Help' menu, or " +
                    "alternatively, the keyboard shortcut CTRL + H.\n\n" +
                    "For a more detailed manual with screenshots, please refer to the user manual document."
            );
            alert.showAndWait();
        });
        
     // Quit   
        systemQuitMenuItem = new MenuItem("Quit");
        systemQuitMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        systemQuitMenuItem.setOnAction((ActionEvent t) -> {
            System.exit(0);
        });
           
        systemMenu.getItems().addAll(systemAboutMenuItem, systemQuitMenuItem);
        
     // HELP MENU
        Menu helpMenu = new Menu("Help");
        MenuItem helpManualMenuItem;
        MenuItem helpShortcutMenuItem;
        
      // Manual
        helpManualMenuItem = new MenuItem("Manual");
        helpManualMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));
        helpManualMenuItem.setOnAction((ActionEvent t) -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Stock Application Guide");
            alert.setHeaderText("Manual");
            alert.initModality(Modality.NONE);
            alert.getDialogPane().setPrefSize(505, 450);
            alert.setResizable(true);
            
            
            GridPane grid = new GridPane();
            grid.setVgap(2);
            
            BorderPane layout = new BorderPane();
            ScrollPane scrollPane = new ScrollPane(grid);
            scrollPane.getStyleClass().add("edge-to-edge");
            layout.setCenter(scrollPane);
            
            NumberBinding wrap = (alert.widthProperty()).subtract(60);
            
        /// BUYING SHARES    
            Text buySharesHeading = new Text("Buying Shares");
            buySharesHeading.getStyleClass().add("heading");
            Text buySharesText = new Text(
                    "1. Navigate to the 'Exchange' tab.\n" +
                    "2. Select a stock from the stock list table.\n" +
                    "3. Select the 'Buy Shares' button to the right.\n" +
                    "4. Enter the number of shares you wish to purchase in the dialog box provided and select 'OK'.\n" +
                    "5. A confirmation dialog will appear, and your purchased shares will now appear under the 'Account' tab.\n" +
                    "6. Your account balance will also be updated."
            );
            buySharesText.wrappingWidthProperty().bind(wrap);
        
        /// SELLING SHARES    
            Text sellSharesHeading = new Text("Selling Shares");
            sellSharesHeading.getStyleClass().add("heading");
            Text sellSharesText = new Text(
                    "1. Navigate to the 'Account' tab.\n" +
                    "2. Select a stock from the stock list table.\n" +
                    "3. Select the 'Sell Shares' button to the right.\n" +
                    "4. Enter the number of shares you wish to sell in the dialog box provided and select 'OK'.\n" +
                    "5. A confirmation dialog will appear, and your sold shares will now be reflected in the exchange.\n" +
                    "6. Your account balance will also be updated."
            );
            sellSharesText.wrappingWidthProperty().bind(wrap);
        
        /// ADDING STOCK    
            Text addStockHeading = new Text("Adding Stock");
            addStockHeading.getStyleClass().add("heading");
            Text addStockText = new Text(
                    "1. Navigate to the 'Exchange' tab.\n" +
                    "2. Select the 'Add Stock' button to the right.\n" +
                    "3. Enter the details of the stock you wish to add in the dialog box provided and select 'Confirm'.\n" +
                    "4. Your new stock will now be reflected in the exchange."
            );
            addStockText.wrappingWidthProperty().bind(wrap);
            
        /// REMOVING STOCK
            Text removeStockHeading = new Text("Removing Stock");
            removeStockHeading.getStyleClass().add("heading");
            Text removeStockText = new Text(
                    "1. Navigate to the 'Exchange' tab.\n" +
                    "2. Select the stock you wish to remove from the stock list table.\n" +
                    "3. Select the 'Remove Stock' button to the right.\n" +
                    "4. The stock will now be removed from the exchange stock list."
            );
            removeStockText.wrappingWidthProperty().bind(wrap);
            
        /// SETTING STOCK PRICE
            Text setStockPriceHeading = new Text("Setting Stock Price");
            setStockPriceHeading.getStyleClass().add("heading");
            Text setStockPriceText = new Text(
                    "1. Navigate to the 'Exchange' tab.\n" +
                    "2. Select the stock you wish to change the price of.\n" +
                    "3. Select the 'Set Stock Price' button to the right.\n" +
                    "4. Enter the new price of the stock in the dialog box provided and select 'OK'.\n" +
                    "5. The new stock price will now be reflected in both the exchange and account stock list."
            );
            setStockPriceText.wrappingWidthProperty().bind(wrap);

        /// DEPOSITING FUNDS
            Text depositFundsHeading = new Text("Depositing Funds");
            depositFundsHeading.getStyleClass().add("heading");
            Text depositFundsText = new Text(
                    "1. Navigate to the 'Account' tab.\n" +
                    "2. Select the 'Deposit Funds' button to the right.\n" +
                    "3. Enter the amount of funds you wish to deposit into your account in the dialog box provided, and select 'OK'.\n" +
                    "4. Your account balance will now be updated."
            );
            depositFundsText.wrappingWidthProperty().bind(wrap);
            
        /// GRID LAYOUT    
            grid.add(buySharesHeading, 1, 0);
            grid.add(buySharesText, 1, 1);
            grid.add(sellSharesHeading, 1, 6);
            grid.add(sellSharesText, 1, 7);
            grid.add(addStockHeading, 1, 12);
            grid.add(addStockText, 1, 13);
            grid.add(removeStockHeading, 1, 18);
            grid.add(removeStockText, 1, 19);
            grid.add(setStockPriceHeading, 1, 24);
            grid.add(setStockPriceText, 1, 25);            
            grid.add(depositFundsHeading, 1, 29);
            grid.add(depositFundsText, 1, 30); 
            
            alert.getDialogPane().setContent(layout);
            alert.getDialogPane().getStylesheets().add(this.getClass().getResource("dialog-skin.css").toExternalForm());
            alert.showAndWait();
        });
        
      // Shortcuts
        helpShortcutMenuItem = new MenuItem("Shortcuts");
        helpShortcutMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
        helpShortcutMenuItem.setOnAction((ActionEvent t) -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Shortcuts");
            alert.setGraphic(null);
            alert.setHeaderText("Keyboard Shortcuts");
            alert.getDialogPane().setPrefSize(320, 320);
            alert.setResizable(true);
            alert.initModality(Modality.NONE);
            
            
            TableView<Shortcut> shortcutList = new TableView<>();
            shortcutList.setEditable(false);
            shortcutList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            

            TableColumn description = new TableColumn("Description");
            description.setCellValueFactory(
                new PropertyValueFactory<>("description"));
            
            TableColumn shortcut = new TableColumn("Shortcut");
            shortcut.setCellValueFactory(
                new PropertyValueFactory<>("shortcut"));
            
            shortcutList.setItems(shortcuts);
            shortcutList.getColumns().addAll(description, shortcut);
            
            BorderPane shortcutBorderPane = new BorderPane();
            shortcutBorderPane.setCenter(shortcutList);
            
            shortcutBorderPane.prefHeightProperty().bind(alert.heightProperty());
            shortcutBorderPane.prefWidthProperty().bind(alert.widthProperty());
            
            alert.getDialogPane().setContent(shortcutBorderPane);

            alert.show();
        });
                        
        helpMenu.getItems().addAll(helpManualMenuItem, helpShortcutMenuItem);
        
     // MENU BAR
        MenuBar menuBar = new MenuBar();   
        menuBar.getMenus().addAll(systemMenu, helpMenu);
        
        
    //********************************************************************
    // TAB SETUP
    //********************************************************************
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
       
    /// EXCHANGE TAB
        Tab exchangeTab = new Tab();
        exchangeTab.setText("Exchange");
        BorderPane exchangeBorderPane = new BorderPane();
        
        VBox exchangeRightBox = new VBox();
        exchangeRightBox.getStyleClass().add("right-panel");
        exchangeRightBox.setPadding(new Insets(10));
        exchangeRightBox.setSpacing(8);
        
        VBox exchangeAccountBalanceBox = new VBox();
        exchangeAccountBalanceBox.setSpacing(2);
        
        Text accountBalanceExchangeText = new Text("Account Balance:");
        accountBalanceExchangeText.getStyleClass().add("account-balance-label");
        
        Text accountBalanceExchangeValue = new Text();
        accountBalanceExchangeValue.getStyleClass().add("account-balance-value");
        accountBalanceExchangeValue.textProperty().bind(accountBalance.asString());
        
        exchangeAccountBalanceBox.getChildren().addAll(accountBalanceExchangeText, accountBalanceExchangeValue);
        
        exchangeRightBox.getChildren().add(exchangeAccountBalanceBox);
        
    /// Stock Table
        TableView<Stock> exchangeTable = new TableView<Stock>();
        exchangeTable.setEditable(false);
        exchangeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn exchangeStockNameCol = new TableColumn("Company Name");
        exchangeStockNameCol.setCellValueFactory(
            new PropertyValueFactory<>("name"));
        
        TableColumn exchangeStockPriceCol = new TableColumn("Price");
        exchangeStockPriceCol.setCellValueFactory(
            new PropertyValueFactory<>("price")); 
        
        TableColumn exchangeStockQtyCol = new TableColumn("Quantity");
        exchangeStockQtyCol.setCellValueFactory(
            new PropertyValueFactory<>("shares"));
        
        TableColumn exchangeStockTotalValueCol = new TableColumn("Total Value");
        exchangeStockTotalValueCol.setCellValueFactory(
            new PropertyValueFactory<>("totalValue"));
        
        exchangeTable.setItems(exchangeData);
        exchangeTable.getColumns().addAll(exchangeStockNameCol, exchangeStockPriceCol, exchangeStockQtyCol, exchangeStockTotalValueCol);
         
     /// Buttons
        // Buy Stock
        Button buySharesBtn = new Button("Buy Shares");
        buySharesBtn.setOnAction((ActionEvent t) -> {
            try {
                Stock selectedItem = exchangeTable.getSelectionModel().getSelectedItem();
                /// No stock selected
                if(selectedItem == null) {
                    throw new NullPointerException(
                            "Please select a stock from the exchange table and try again.");
                }
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Buy Shares");
                dialog.setHeaderText("Buy Shares");
                dialog.setContentText("Please enter the number of shares you wish you purchase:");
                
                Optional<String> result = dialog.showAndWait();
                result.ifPresent(stringInput -> {
                        int numOfShares = Integer.parseInt(stringInput);
                        // User entered quantity of 0 or less
                        if(numOfShares <= 0) {
                            throw new StockException(
                                    "Please enter a quantity greater than 0.",
                                    "Invalid quantity");
                        }
                        
                        double buyValue = selectedItem.buyShares(numOfShares, accountBalance.get());
                        buyValue = Stock.roundDouble(buyValue);
                        double oldAccountBalance = accountBalance.get();
                        accountBalance.set(Stock.roundDouble(accountBalance.get() - buyValue));
                        
                        if(selectedItem.getShares() == 0) {
                            exchangeData.remove(selectedItem);
                        }
                        
                        Stock accountStock = dataContainsStock(accountData, selectedItem.getName());
                        if(accountStock != null) {
                            accountStock.increaseShares(numOfShares);
                        }
                        else {
                            accountData.add(new Stock(selectedItem.getName(), selectedItem.getPrice(), numOfShares));
                        }
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Shares Bought");
                        alert.setHeaderText("You have successfully bought " + numOfShares + " " + selectedItem.getName() + " shares.");
                        alert.setContentText("Old account balance: " + oldAccountBalance +
                                             "\nTotal buy value: " + buyValue +
                                             "\nNew account balance: " + accountBalance.get());
                        alert.showAndWait();
                });
                
            }
            catch(StockException e) {
                stockExceptionDialog(e, buySharesBtn);
            }
            catch(NullPointerException e) {
                Alert exceptionAlert = new Alert(AlertType.ERROR);
                exceptionAlert.setTitle("No stock selected");
                exceptionAlert.setHeaderText("No stock selected");
                exceptionAlert.setContentText(e.getMessage());
                exceptionAlert.showAndWait();
            }
            catch(NumberFormatException e) {
                Alert exceptionAlert = new Alert(AlertType.ERROR);
                exceptionAlert.setTitle("Invalid quantity");
                exceptionAlert.setHeaderText("Invalid quantity");
                exceptionAlert.setContentText("Please enter a valid quantity.");
                exceptionAlert.showAndWait();
                buySharesBtn.fire();
            }
            catch(Exception e) {
                genericExceptionDialog(e);
            }
        });
    
        // Add Stock
        Button addStockBtn = new Button("Add Stock");
        addStockBtn.setOnAction((ActionEvent t) -> {
            Dialog<Stock> dialog = new Dialog<>();
            try {
                dialog.setTitle("Add Stock");
                dialog.setHeaderText("Please enter the details of the stock you wish to add below.");
                //dialog.setGraphic();

                Label companyNameLabel = new Label("Company name:");
                TextField companyNameTxt = new TextField();

                Label stockPriceLabel = new Label("Stock price:");
                TextField stockPriceTxt = new TextField();

                Label stockQuantityLabel = new Label("Quantity:");
                TextField stockQuantityTxt = new TextField();

                GridPane grid = new GridPane();
                grid.add(companyNameLabel, 1, 1);
                grid.add(companyNameTxt, 2, 1);
                grid.add(stockPriceLabel, 1, 2);
                grid.add(stockPriceTxt, 2, 2);
                grid.add(stockQuantityLabel, 1, 3);
                grid.add(stockQuantityTxt, 2, 3);
                grid.setVgap(5);
                grid.setHgap(10);
                dialog.getDialogPane().setContent(grid);

                ButtonType confirmButton = new ButtonType("Confirm", ButtonData.OK_DONE);
                ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                dialog.getDialogPane().getButtonTypes().addAll(confirmButton, cancelButton);

                dialog.setResultConverter((ButtonType button) -> {
                    if(button == confirmButton) {
                        try {
                            String companyName = companyNameTxt.getText();
                            String stockPriceString = stockPriceTxt.getText();
                            String stockQuantityString = stockQuantityTxt.getText();
                            
                            /// Missing values
                            if(companyName.equals("") || stockPriceString.equals("") || stockQuantityString.equals("")) {
                                throw new StockException(
                                        "Please ensure all fields are filled in.",
                                        "Missing values"
                                );
                            }
                            
                            /// Stock exists in Exchange
                            Stock stockExists = dataContainsStock(exchangeData, companyName);
                            if(stockExists != null) {
                                throw new StockException(
                                        "This stock already exists. Please add a different stock.",
                                        "Stock already exists"
                                );
                            }
                            
                            double stockPrice = Double.parseDouble(stockPriceString);
                            stockPrice = Stock.roundDouble(stockPrice);
                            
                            int stockQuantity = Integer.parseInt(stockQuantityString);
                            
                            /// Maximum name length
                            if(companyName.length() > 50) {
                                throw new StockException(
                                        "The company name of the stock may not be longer than 50 characters.",
                                        "Company Name Max Length"
                                );
                            }
                            
                            /// Price exceptions
                            // Value of 0 or less
                            if(stockPrice <= 0) {
                                throw new StockException(
                                        "Please enter a stock price that is greater than 0.",
                                        "Invalid Stock Price");
                            }
                            
                            // Maxiumum stock value
                            if(stockPrice > 10000) {
                                throw new StockException(
                                        "A stock may not be valued at a price of more than 10,000.",
                                        "Maximum Stock Value");
                            }
                            
                            /// Quantity exceptions
                            // User entered quantity of 0 or less
                            if(stockQuantity <= 0) {
                                throw new StockException(
                                        "Please enter a quantity greater than 0.",
                                        "Invalid Quantity");
                            }
                            
                            // User entered quantity greater than 1000
                            if(stockQuantity > 1000) {
                                throw new StockException(
                                        "The maximum quantity of stock you may add is 1000.",
                                        "Maximum Quantity");
                            }
                            
                            Stock newStock = new Stock(     
                                    companyName,
                                    stockPrice,
                                    stockQuantity
                            );
                            return newStock;
                        }
                        catch(StockException error) {
                            dialog.close();
                            stockExceptionDialog(error, addStockBtn);
                        }
                        catch(NumberFormatException error) {
                            dialog.close();
                            Alert exceptionAlert = new Alert(AlertType.ERROR);
                            exceptionAlert.setTitle("Invalid quantity or price");
                            exceptionAlert.setHeaderText("Invalid quantity/price");
                            exceptionAlert.setContentText("Please enter a valid quantity or price.");
                            exceptionAlert.showAndWait();
                            addStockBtn.fire();
                        }
                        catch(Exception error) {
                            genericExceptionDialog(error);
                        }
                    }
                    return null;
                });

                Optional<Stock> result = dialog.showAndWait();
                
                if(result.isPresent()) {
                    Stock newStock = result.get();
                    
                /// Stock exists in Account
                    Stock stockExists = dataContainsStock(accountData, newStock.getName());
                    if(stockExists != null) {
                        stockExists.setPrice(newStock.getPrice());
                        stockExists.setName(newStock.getName());
                    }                        
                    
                    exchangeData.add(newStock);
                }
            }
            catch(StockException e) { 
                dialog.close();
                stockExceptionDialog(e, addStockBtn);
            }
            catch(Exception e) {
                genericExceptionDialog(e);
            }    
        });
        
        // Remove Stock
        Button removeStockBtn = new Button("Remove Stock");
        removeStockBtn.setOnAction((ActionEvent t) -> {
            try {
                Stock selectedItem = exchangeTable.getSelectionModel().getSelectedItem();
            /// Stock not selected
                if(selectedItem == null) {
                    throw new NullPointerException(
                            "Please select a stock from the exchange table and try again.");
                }
                exchangeData.remove(selectedItem);
            }
            catch(NullPointerException e) {
                Alert exceptionAlert = new Alert(AlertType.ERROR);
                exceptionAlert.setTitle("No stock selected");
                exceptionAlert.setHeaderText("No stock selected");
                exceptionAlert.setContentText(e.getMessage());
                exceptionAlert.showAndWait();
            }
            catch(Exception e) {
                genericExceptionDialog(e);
            }
        });
            
        // Set Stock Price
        Button setStockPriceBtn = new Button("Set Stock Price");
        setStockPriceBtn.setOnAction((ActionEvent t) -> {
            try {
                Stock selectedItem = exchangeTable.getSelectionModel().getSelectedItem();
            /// No stock selected
                if(selectedItem == null) {
                    throw new NullPointerException(
                            "Please select a stock from the exchange table and try again.");
                }
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Set Stock Price");
                dialog.setHeaderText("Set Stock Price");
                dialog.setContentText("Please enter the price you would like to set for this stock:");
                
                Optional<String> result = dialog.showAndWait();
                result.ifPresent(stringInput -> {
                    double price = Double.parseDouble(stringInput);
                    price = Stock.roundDouble(price);
                    
                /// User entered price of 0 or less
                    if(price <= 0) {
                        throw new StockException(
                                "Please enter a price greater than 0.",
                                "Invalid Price");
                    }

                /// User entered price greater than 10,000
                    if(price > 10000) {
                        throw new StockException(
                                "Stock price may not be greater than 10,000.",
                                "Maximum stock price");
                    }    
                /// Set new price
                    selectedItem.setPrice(price);
                    Stock accountStock = dataContainsStock(accountData, selectedItem.getName());
                        if(accountStock != null) {
                            accountStock.setPrice(price);
                        }
                });
            }
            catch(StockException e) {
                stockExceptionDialog(e, setStockPriceBtn);
            }
            catch(NullPointerException e) {
                Alert exceptionAlert = new Alert(AlertType.ERROR);
                exceptionAlert.setTitle("No stock selected");
                exceptionAlert.setHeaderText("No stock selected");
                exceptionAlert.setContentText(e.getMessage());
                exceptionAlert.showAndWait();
            }
            catch(NumberFormatException e) {
                Alert exceptionAlert = new Alert(AlertType.ERROR);
                exceptionAlert.setTitle("Invalid price");
                exceptionAlert.setHeaderText("Invalid price");
                exceptionAlert.setContentText("Please enter a valid price.");
                exceptionAlert.showAndWait();
                setStockPriceBtn.fire();
            }            
            catch(Exception e) {
                genericExceptionDialog(e);
            }
        });
        
        Button exchangeButtons[] = new Button[] {
            buySharesBtn,
            addStockBtn,
            removeStockBtn,
            setStockPriceBtn};

        for (Button exchangeButton : exchangeButtons) {
            VBox.setMargin(exchangeButton, new Insets(0, 0, 0, 8));
            exchangeRightBox.getChildren().add(exchangeButton);
        }    
        
        exchangeBorderPane.setCenter(exchangeTable);
        exchangeBorderPane.setRight(exchangeRightBox);
        exchangeTab.setContent(exchangeBorderPane);
                
        // Account Tab
        Tab accountTab = new Tab();
        accountTab.setText("Account");
        BorderPane accountBorderPane = new BorderPane();
        
        VBox accountRightBox = new VBox();
        accountRightBox.setPadding(new Insets(10));
        accountRightBox.setSpacing(8);
        
        VBox accountBalanceBox = new VBox();
        accountBalanceBox.setSpacing(2);
        
        Text accountBalanceText = new Text("Account Balance:");
        accountBalanceText.getStyleClass().add("account-balance-label");
        
        Text accountBalanceValue = new Text();
        accountBalanceValue.getStyleClass().add("account-balance-value");
        accountBalanceValue.textProperty().bind(accountBalance.asString());
        
        accountBalanceBox.getChildren().addAll(accountBalanceText, accountBalanceValue);
        
        accountRightBox.getStyleClass().add("right-panel");
        accountRightBox.getChildren().add(accountBalanceBox);
                        
    /// Account Table
        TableView<Stock> accountTable = new TableView<>();
        accountTable.setEditable(false);
        accountTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn accountStockNameCol = new TableColumn("Company Name");
        accountStockNameCol.setCellValueFactory(
            new PropertyValueFactory<>("name"));
        
        TableColumn accountStockPriceCol = new TableColumn("Price");
        accountStockPriceCol.setCellValueFactory(
            new PropertyValueFactory<>("price")); 
        
        TableColumn accountStockQtyCol = new TableColumn("Quantity");
        accountStockQtyCol.setCellValueFactory(
            new PropertyValueFactory<>("shares"));
        
        TableColumn accountStockTotalValueCol = new TableColumn("Total Value");
        accountStockTotalValueCol.setCellValueFactory(
            new PropertyValueFactory<>("totalValue"));
        
        accountTable.setItems(accountData);
        accountTable.getColumns().addAll(accountStockNameCol, accountStockPriceCol, accountStockQtyCol, accountStockTotalValueCol);
        
        
    /// Buttons
        // Sell Stock
        Button sellSharesBtn = new Button("Sell Shares");
        sellSharesBtn.setOnAction((ActionEvent t) -> {
            try {
                Stock selectedItem = accountTable.getSelectionModel().getSelectedItem();
                /// No stock selected
                if(selectedItem == null) {
                    throw new NullPointerException(
                            "Please select a stock from the account stock table and try again.");
                }
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Sell Shares");
                dialog.setHeaderText("Sell Shares");
                dialog.setContentText("Please enter the number of shares you wish you sell:");
                
                Optional<String> result = dialog.showAndWait();
                result.ifPresent(stringInput -> {
                        int numOfShares = Integer.parseInt(stringInput);
                    /// User entered quantity of 0 or less
                        if(numOfShares <= 0) {
                            throw new StockException(
                                    "Please enter a quantity greater than 0.",
                                    "Invalid quantity");
                        }
                        
                        double saleValue = selectedItem.sellShares(numOfShares);
                        saleValue = Stock.roundDouble(saleValue);
                        double oldAccountBalance = accountBalance.get();
                        accountBalance.set(Stock.roundDouble(accountBalance.get() + saleValue));
                        
                        if(selectedItem.getShares() == 0) {
                            accountData.remove(selectedItem);
                        }
                        
                        Stock exchangeStock = dataContainsStock(exchangeData, selectedItem.getName());
                        if(exchangeStock != null) {
                            exchangeStock.increaseShares(numOfShares);
                        }
                        else {
                            exchangeData.add(new Stock(selectedItem.getName(), selectedItem.getPrice(), numOfShares));
                        }
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Shares Sold");
                        alert.setHeaderText("You have successfully sold " + numOfShares + " " + selectedItem.getName() + " shares.");
                        alert.setContentText("Old account balance: " + oldAccountBalance +
                                             "\nTotal sale value: " + saleValue +
                                             "\nNew account balance: " + accountBalance.get());
                        alert.showAndWait();
                });
                
            }
            // Stock Exception
            catch(StockException e) {
                stockExceptionDialog(e, sellSharesBtn);
            }
            // Null Pointer Exception
            catch(NullPointerException e) {
                Alert exceptionAlert = new Alert(AlertType.ERROR);
                exceptionAlert.setTitle("No stock selected");
                exceptionAlert.setHeaderText("No stock selected");
                exceptionAlert.setContentText(e.getMessage());
                exceptionAlert.showAndWait();
            }
            // Number Format Exception
            catch(NumberFormatException e) {
                Alert exceptionAlert = new Alert(AlertType.ERROR);
                exceptionAlert.setTitle("Invalid quantity");
                exceptionAlert.setHeaderText("Invalid quantity");
                exceptionAlert.setContentText("Please enter a valid quantity.");
                exceptionAlert.showAndWait();
                sellSharesBtn.fire();
            }
            // General Exception
            catch(Exception e) {
                genericExceptionDialog(e);
            }
        });
    
        // Deposit Funds
        Button depositFundsBtn = new Button("Deposit Funds");
        depositFundsBtn.setOnAction((ActionEvent t) -> {
            try {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Deposit Funds");
                dialog.setHeaderText("Deposit Funds");
                dialog.setContentText("Please enter the amount of funds you wish to deposit into your account:");
                
                Optional<String> result = dialog.showAndWait();
                result.ifPresent(stringInput -> {
                    int numOfShares = Integer.parseInt(stringInput);
                    /// User entered amount of 0 or less
                    if(numOfShares <= 0) {
                        throw new StockException(
                                "Please enter an amount greater than 0.",
                                "Invalid amount");
                    }
                    
                    double depositAmount = Double.parseDouble(stringInput);
                    depositAmount = Stock.roundDouble(depositAmount);
                    double oldAccountBalance = accountBalance.get();
                    
                    /// New balance over 10,000,000
                    if((oldAccountBalance + depositAmount) > 10000000) {
                        throw new StockException(
                                "The maximum amount of funds you can have in your account is 10,000,000.\n\n" +
                                        "You may add no more than " + (10000000 - oldAccountBalance) + " to your account.\n\n" +
                                                "Your current account balance is: " + oldAccountBalance,
                                "Maximum Account Balance");
                    }
                    accountBalance.set(Stock.roundDouble(oldAccountBalance + depositAmount));
                    
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Deposit Successful");
                    alert.setHeaderText("You have successfully deposited " + depositAmount + " into your account.");
                    alert.setContentText("Old account balance: " + oldAccountBalance +
                            "\nDeposit amount: " + depositAmount +
                            "\nNew account balance: " + accountBalance.get());
                    alert.showAndWait();
                });
            }
            // Stock Exception
            catch(StockException e) {
                stockExceptionDialog(e, depositFundsBtn);
            }
            // Number Format Exception
            catch(NumberFormatException e) {
                Alert exceptionAlert = new Alert(AlertType.ERROR);
                exceptionAlert.setTitle("Invalid quantity");
                exceptionAlert.setHeaderText("Invalid quantity");
                exceptionAlert.setContentText("Please enter a valid quantity.");
                exceptionAlert.showAndWait();
                depositFundsBtn.fire();
            }
            // General Exception
            catch(Exception e) {
                genericExceptionDialog(e);
            }
        });    
        
        Button accountButtons[] = new Button[] {
            sellSharesBtn,
            depositFundsBtn};

        for (Button accountButton : accountButtons) {
            VBox.setMargin(accountButton, new Insets(0, 0, 0, 8));
            accountRightBox.getChildren().add(accountButton);
        }    
        
        accountBorderPane.setCenter(accountTable);
        accountBorderPane.setRight(accountRightBox);
        accountTab.setContent(accountBorderPane);
        
        tabPane.getTabs().addAll(exchangeTab, accountTab);
        
    //********************************************************************
    // KEYBOARD HOTKEYS
    //********************************************************************
    /// EXCHANGE PANEL
     // Buy Shares
        final KeyCombination buySharesShortcut = new KeyCodeCombination(KeyCode.B,
                                            KeyCombination.CONTROL_DOWN);
     // Remove Stock
        final KeyCombination removeStockShortcut = new KeyCodeCombination(KeyCode.X,
                                            KeyCombination.CONTROL_DOWN);
     
     // Add Stock   
        final KeyCombination addStockShortcut = new KeyCodeCombination(KeyCode.N,
                                            KeyCombination.CONTROL_DOWN);
        
     // Set Stock Price
        final KeyCombination stockPriceShortcut = new KeyCodeCombination(KeyCode.C,
                                            KeyCombination.CONTROL_DOWN);
         
    /// ACCOUNT PANEL
     // Sell Shares
        final KeyCombination sellSharesShortcut = new KeyCodeCombination(KeyCode.S,
                                            KeyCombination.CONTROL_DOWN);
     // Deposit Funds
        final KeyCombination depositFundsShortcut = new KeyCodeCombination(KeyCode.F,
                                            KeyCombination.CONTROL_DOWN); 
        
    /// KEY BINDING EVENT HANDLER    
        scene.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
        /// EXCHANGE PANEL
            if(tabPane.getSelectionModel().getSelectedIndex() == 0) {
            // Buy Shares
                if(buySharesShortcut.match(event)) {
                   buySharesBtn.fire();
                   return;
                }
            // Remove Stock
                if(removeStockShortcut.match(event)) {
                   removeStockBtn.fire();
                   return;
                }
            // Add Stock   
                if(addStockShortcut.match(event)) {
                   addStockBtn.fire();
                   return;
                }
            // Set Stock Price
                if(stockPriceShortcut.match(event)) {
                   setStockPriceBtn.fire();
                   return;
                }
            }
           /// ACCOUNT PANEL
            if(tabPane.getSelectionModel().getSelectedIndex() == 1) {
            // Sell Shares
                if(sellSharesShortcut.match(event)) {
                   sellSharesBtn.fire();
                   return;
               }
            }
        // Deposit Funds
            if(depositFundsShortcut.match(event)) {
                depositFundsBtn.fire();
            }    
        });

        
    /// ADJUST LAYOUT   
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        borderPane.setTop(menuBar);
        borderPane.setCenter(tabPane);
        
        root.getChildren().addAll(borderPane);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(this.getClass().getResource("skin.css").toExternalForm());
        primaryStage.show();
    }
    
    private Stock dataContainsStock(ObservableList<Stock> data, String checkName) {
        String stockName;
        checkName = checkName.toUpperCase();
        
        for(Stock stock : data) {
            stockName = stock.getName().toUpperCase();
            if(stockName.equals(checkName)) {
                return stock;
            }
        }
        return null;
    }
    
    private void stockExceptionDialog(StockException e, Button button) {
        Alert exceptionAlert = new Alert(AlertType.ERROR);
        exceptionAlert.setTitle(e.getTitle());
        exceptionAlert.setHeaderText(e.getTitle());
        exceptionAlert.setContentText(e.getMessage());
        exceptionAlert.showAndWait();
        button.fire();
    }
    
    private void genericExceptionDialog(Exception e) {
        Alert exceptionAlert = new Alert(AlertType.ERROR);
        exceptionAlert.setTitle("Unexpected error");
        exceptionAlert.setHeaderText(e.toString());
        exceptionAlert.setContentText("An unexpected error has occurred.");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionDetails = sw.toString();

        Label exceptionLabel = new Label("Exception details:");

        TextArea textArea = new TextArea(exceptionDetails);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane exceptionContent = new GridPane();
        exceptionContent.setMaxWidth(Double.MAX_VALUE);
        exceptionContent.add(exceptionLabel, 0, 0);
        exceptionContent.add(textArea, 0, 1);

        exceptionAlert.getDialogPane().setExpandableContent(exceptionContent);
        exceptionAlert.showAndWait();
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
