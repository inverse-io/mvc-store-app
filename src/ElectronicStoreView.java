import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.w3c.dom.Text;

public class ElectronicStoreView extends Pane {

    private ListView<Product> storeStock;
    private ListView<String> inCart;
    private ListView<Product> mostPopular;

    private Button resetButton, addButton, removeButton, buyButton;

    private Label cartLabel;

    private TextField numSalesBox, revenueBox, avgSales;

    public ElectronicStoreView(){
        // creating labels
        Label storeLabel = new Label("Store Stock");
        storeLabel.relocate(400, 20);
        cartLabel = new Label("Current Cart ($0)");
        cartLabel.relocate(780,20);
        Label summaryLabel = new Label("Store Summary:");
        summaryLabel.relocate(80,20);

        Label numSales = new Label("# Sales:");
        numSales.relocate(15,50);
        Label revLabel = new Label("Revenue:");
        revLabel.relocate(15,90);
        Label salesLabel = new Label("$ / Sale:");
        salesLabel.relocate(15,130);

        Label popularItemsLabel = new Label("Most Popular Items:");
        popularItemsLabel.relocate(72,173);

        //listviews
        mostPopular = new ListView<>();
        mostPopular.relocate(10, 200);
        mostPopular.setPrefSize(230,225);

        storeStock = new ListView<>();
        storeStock.relocate(250, 45);
        storeStock.setPrefSize(365, 380);

        inCart = new ListView<>();
        inCart.relocate(625,45);
        inCart.setPrefSize(365,380);

        // text fields
        numSalesBox = new TextField();
        numSalesBox.relocate(80,45);
        numSalesBox.setPrefSize(160,30);

        revenueBox = new TextField();
        revenueBox.relocate(80,85);
        revenueBox.setPrefSize(160,30);

        avgSales = new TextField();
        avgSales.relocate(80,125);
        avgSales.setPrefSize(160,30);

        //adding buttons
        resetButton = new Button("Reset Store");
        resetButton.relocate(60, 435);
        resetButton.setPrefSize(130,40);

        addButton = new Button("Add to Cart");
        addButton.relocate(370, 435);
        addButton.setPrefSize(130,40);

        removeButton = new Button("Remove from Cart");
        removeButton.relocate(675, 435);
        removeButton.setPrefSize(130,40);

        buyButton = new Button("Complete Sale");
        buyButton.relocate(810, 435);
        buyButton.setPrefSize(130,40);


        //add all
        getChildren().addAll(mostPopular, storeStock, inCart, numSalesBox, revenueBox, avgSales, storeLabel, cartLabel, summaryLabel
        , numSales, revLabel, salesLabel, popularItemsLabel, resetButton, addButton, removeButton, buyButton);

        //set size
        setPrefSize(1000,500);
    }

    public void update( ElectronicStore e ){
        numSalesBox.setPromptText("Total Sales");
        revenueBox.setPromptText("Total Revenue");
        avgSales.setPromptText("Avg. Sales");

        storeStock.setItems(FXCollections.observableArrayList(e.getStock()));
        mostPopular.setItems(FXCollections.observableArrayList(e.findMostPopular()));
        inCart.setItems(FXCollections.observableArrayList(e.getCartItems().values()));

        // disable buttons when nothing is selected
        addButton.setDisable(storeStock.getSelectionModel().getSelectedIndex() < 0);
        buyButton.setDisable((long)inCart.getItems().size() == 0); // if nothing is in the cart
        cartLabel.setText("Current Cart ($" + e.cartWorth() + ")");
        removeButton.setDisable(inCart.getSelectionModel().getSelectedIndex() < 0);

        // set text fields
        numSalesBox.setText("" + e.getTotalSales());
        revenueBox.setText("" + e.getRevenue());
        avgSales.setText("" + e.avgSaleRev());
    }

    public Button getAddButton(){ return addButton; }
    public Button getBuyButton() { return buyButton; }
    public Button getRemoveButton() { return removeButton; }
    public Button getResetButton() { return resetButton; }

    public ListView<Product> getStoreStock(){ return storeStock; }
    public ListView<String> getInCart(){ return inCart; }
}
