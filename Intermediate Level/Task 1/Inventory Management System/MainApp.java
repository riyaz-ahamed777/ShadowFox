import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.function.UnaryOperator;

public class MainApp extends Application {

    private ObservableList<InventoryItem> itemList = FXCollections.observableArrayList();
    private FilteredList<InventoryItem> filteredList = new FilteredList<>(itemList, p -> true);
    private TableView<InventoryItem> table = new TableView<>(filteredList);

    private TextField nameInput = new TextField();
    private TextField qtyInput = new TextField();
    private TextField priceInput = new TextField();
    private ComboBox<String> categoryInput = new ComboBox<>();
    private DatePicker expirationInput = new DatePicker();
    private TextField searchInput = new TextField();
    private Label totalValueLabel = new Label("Total Inventory Value: $0.00");

    @Override
    public void start(Stage stage) {
        stage.setTitle("Enhanced Inventory Management System");

        // Table Columns
        TableColumn<InventoryItem, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<InventoryItem, Integer> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<InventoryItem, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<InventoryItem, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<InventoryItem, String> expirationCol = new TableColumn<>("Expiration Date");
        expirationCol.setCellValueFactory(cellData -> {
            InventoryItem item = cellData.getValue();
            if ("Food".equals(item.getCategory()) && item.getExpirationDate() != null) {
                return new SimpleStringProperty(item.getExpirationDate().toString());
            } else {
                return new SimpleStringProperty("N/A");
            }
        });

        table.getColumns().addAll(nameCol, qtyCol, priceCol, categoryCol, expirationCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Search
        searchInput.setPromptText("Search...");
        searchInput.textProperty().addListener((obs, oldVal, newVal) ->
                filteredList.setPredicate(item ->
                        item.getName().toLowerCase().contains(newVal.toLowerCase()) ||
                                String.valueOf(item.getQuantity()).contains(newVal) ||
                                String.valueOf(item.getPrice()).contains(newVal))
        );

        // Form Layout
        nameInput.setPromptText("Name");
        qtyInput.setPromptText("Qty");
        priceInput.setPromptText("Price");
        categoryInput.getItems().addAll("Electronics", "Clothing", "Furniture", "Food", "Other");
        categoryInput.setValue("Electronics");

        // TextFormatter for inputs
        UnaryOperator<Change> integerFilter = change -> change.getControlNewText().matches("\\d*") ? change : null;
        qtyInput.setTextFormatter(new TextFormatter<>(integerFilter));

        UnaryOperator<Change> decimalFilter = change -> change.getControlNewText().matches("\\d*(\\.\\d{0,2})?") ? change : null;
        priceInput.setTextFormatter(new TextFormatter<>(decimalFilter));

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));
        form.add(new Label("Name:"), 0, 0);
        form.add(nameInput, 1, 0);
        form.add(new Label("Quantity:"), 0, 1);
        form.add(qtyInput, 1, 1);
        form.add(new Label("Price:"), 0, 2);
        form.add(priceInput, 1, 2);
        form.add(new Label("Category:"), 0, 3);
        form.add(categoryInput, 1, 3);
        form.add(new Label("Expiration Date:"), 0, 4);
        form.add(expirationInput, 1, 4);

        categoryInput.valueProperty().addListener((obs, oldVal, newVal) -> {
            if ("Food".equals(newVal)) {
                expirationInput.setDisable(false);
            } else {
                expirationInput.setDisable(true);
                expirationInput.setValue(null);
            }
        });

        Button addBtn = new Button("Add");
        Button updateBtn = new Button("Update");
        Button deleteBtn = new Button("Delete");
        Button clearBtn = new Button("Clear");

        addBtn.setOnAction(e -> addItem());
        updateBtn.setOnAction(e -> updateItem());
        deleteBtn.setOnAction(e -> deleteItem());
        clearBtn.setOnAction(e -> clearInputs());

        HBox buttons = new HBox(10, addBtn, updateBtn, deleteBtn, clearBtn);
        buttons.setAlignment(Pos.CENTER);

        itemList.addListener((ListChangeListener<InventoryItem>) change -> updateTotalValue());

        VBox layout = new VBox(10, searchInput, table, form, buttons, totalValueLabel);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void addItem() {
        if (validateInputs()) {
            LocalDate expirationDate = ("Food".equals(categoryInput.getValue()) && expirationInput.getValue() != null)
                    ? expirationInput.getValue() : null;

            itemList.add(new InventoryItem(
                    nameInput.getText(),
                    Integer.parseInt(qtyInput.getText()),
                    Double.parseDouble(priceInput.getText()),
                    categoryInput.getValue(),
                    expirationDate
            ));
            clearInputs();
        }
    }

    private void updateItem() {
        InventoryItem selected = table.getSelectionModel().getSelectedItem();
        if (selected != null && validateInputs()) {
            LocalDate expirationDate = ("Food".equals(categoryInput.getValue()) && expirationInput.getValue() != null)
                    ? expirationInput.getValue() : null;

            selected.setName(nameInput.getText());
            selected.setQuantity(Integer.parseInt(qtyInput.getText()));
            selected.setPrice(Double.parseDouble(priceInput.getText()));
            selected.setCategory(categoryInput.getValue());
            selected.setExpirationDate(expirationDate);
            table.refresh();
            clearInputs();
        }
    }

    private void deleteItem() {
        InventoryItem selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            itemList.remove(selected);
            clearInputs();
        }
    }

    private boolean validateInputs() {
        try {
            if (nameInput.getText().isEmpty()) throw new Exception();
            Integer.parseInt(qtyInput.getText());
            Double.parseDouble(priceInput.getText());
            return true;
        } catch (Exception e) {
            showAlert("Invalid input. Please check your values.");
            return false;
        }
    }

    private void clearInputs() {
        nameInput.clear();
        qtyInput.clear();
        priceInput.clear();
        categoryInput.setValue("Electronics");
        expirationInput.setValue(null);
        expirationInput.setDisable(true);
        table.getSelectionModel().clearSelection();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void updateTotalValue() {
        double totalValue = itemList.stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
        totalValueLabel.setText(String.format("Total Inventory Value: $%.2f", totalValue));
    }

    public static void main(String[] args) {
        launch(args);
}
}
