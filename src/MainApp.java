import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainApp extends Application {
    private TableView<InventoryItem> table;
    private ObservableList<InventoryItem> itemList;
    private TextField nameInput = new TextField();
    private TextField qtyInput = new TextField();
    private TextField priceInput = new TextField();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inventory Management System");

        // Initialize the item list and table
        itemList = FXCollections.observableArrayList();
        table = new TableView<>();
        table.setItems(itemList);

        // Create table columns
        TableColumn<InventoryItem, String> nameCol = new TableColumn<>("Item Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<InventoryItem, Integer> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<InventoryItem, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.getColumns().addAll(nameCol, qtyCol, priceCol);

        // Set up input fields
        nameInput.setPromptText("Item Name");
        qtyInput.setPromptText("Quantity");
        priceInput.setPromptText("Price");

        // Create buttons
        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");
        Button clearButton = new Button("Clear");

        // Button actions
        addButton.setOnAction(e -> addItem());
        updateButton.setOnAction(e -> updateItem());
        deleteButton.setOnAction(e -> deleteItem());
        clearButton.setOnAction(e -> clearInputs());

        // Table selection listener
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameInput.setText(newSelection.getName());
                qtyInput.setText(String.valueOf(newSelection.getQuantity()));
                priceInput.setText(String.valueOf(newSelection.getPrice()));
            }
        });

        // Layout setup
        HBox inputBox = new HBox(10, nameInput, qtyInput, priceInput);
        inputBox.setPadding(new Insets(10));

        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton, clearButton);
        buttonBox.setPadding(new Insets(10));

        VBox layout = new VBox(10, table, inputBox, buttonBox);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addItem() {
        if (validateInputs()) {
            itemList.add(new InventoryItem(
                    nameInput.getText(),
                    Integer.parseInt(qtyInput.getText()),
                    Double.parseDouble(priceInput.getText())
            ));
            clearInputs();
        }
    }

    private void updateItem() {
        InventoryItem selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem != null && validateInputs()) {
            selectedItem.setName(nameInput.getText());
            selectedItem.setQuantity(Integer.parseInt(qtyInput.getText()));
            selectedItem.setPrice(Double.parseDouble(priceInput.getText()));
            table.refresh();
            clearInputs();
        } else {
            showAlert("No Selection", "Please select an item to update.");
        }
    }

    private void deleteItem() {
        InventoryItem selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            itemList.remove(selectedItem);
            clearInputs();
        } else {
            showAlert("No Selection", "Please select an item to delete.");
        }
    }

    private boolean validateInputs() {
        try {
            Integer.parseInt(qtyInput.getText());
            Double.parseDouble(priceInput.getText());
            return !nameInput.getText().isEmpty();
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid numbers for quantity and price.");
            return false;
        }
    }

    private void clearInputs() {
        nameInput.clear();
        qtyInput.clear();
        priceInput.clear();
        table.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
}
}
