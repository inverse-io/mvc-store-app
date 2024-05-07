import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ElectronicStoreApp extends Application {

    private ElectronicStore store;

    public void start(Stage mainStage){
        Pane aPane = new Pane();

        // create view
        ElectronicStoreView view = new ElectronicStoreView();
        aPane.getChildren().addAll(view);

        // make electronic store
        store = ElectronicStore.createStore();

        // making the stage stuff
        mainStage.setTitle("Electronic Store Application - " + store.getName());
        mainStage.setResizable(false);
        mainStage.setScene(new Scene(aPane));
        mainStage.show();

        view.update(store);

        // event handlers
        view.getStoreStock().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // updating the view will enable to button if something is pressed
                view.update(store);
            }
        });

        view.getInCart().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // updating the view will enable to button if something is pressed
                view.update(store);
            }
        });

        view.getAddButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // something must have been selected if this button is active
                Product move = view.getStoreStock().getSelectionModel().getSelectedItem();
                store.addToCart(move);

                view.update(store);
            }
        });

        view.getRemoveButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // find the string item
                String remove = view.getInCart().getSelectionModel().getSelectedItem();
                Product k = store.findCartProduct(remove);

                if(k != null){
                    store.removeFromCart(k);
                }

                view.update(store);
            }
        });

        view.getBuyButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // first test if something exists within the cart
                if( (long)view.getInCart().getItems().size() > 0 ){

                    view.update(store);



                    for( Product p : store.getCartItems().keySet() ){
                        // sell all items
                        p.sellUnits(p.getPendingTransaction());
                        p.voidItemHold(p.getPendingTransaction());
                    }

                    //update store
                    store.calcTotalSales();
                    store.calcRevenue();
                }

                view.update(store);
            }
        });

        view.getResetButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // creates new store to overwrite the current one
                store = ElectronicStore.createStore();

                view.update(store);
            }
        });


    }

    public static void main(String[] args){ launch(args); }
}
