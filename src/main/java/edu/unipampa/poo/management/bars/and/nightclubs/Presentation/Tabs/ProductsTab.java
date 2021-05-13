package edu.unipampa.poo.management.bars.and.nightclubs.Presentation.Tabs;

import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Product;
import edu.unipampa.poo.management.bars.and.nightclubs.Business.ProductHandler;

import java.util.List;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import javafx.scene.control.TableCell;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class ProductsTab {
    
    private ProductHandler productHandler;
    private TableView productTableView;
    
    public void consultProduct(Product product) {
        
    }
    
    private void addEditButton(TableColumn tableColumn) {
        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<Product, Void>() {
                    private final Button btn = new Button("Detalhes");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            consultProduct(getTableRow().getItem());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };

                return cell;
            }
        };

        tableColumn.setCellFactory(cellFactory);
    }
    
    public void render(Tab productsTab, ProductHandler productHandler) {
        this.productHandler = productHandler;
        productTableView = new TableView();
        
        TableView ProductTableView = new TableView();
            
        TableColumn<Product, String> codeColumn = new TableColumn<>("Código");
        codeColumn.setMinWidth(150);
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        
        TableColumn<Product, String> descriptionColumn = new TableColumn<>("Descrição");
        descriptionColumn.setMinWidth(300);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        TableColumn<Product, String> quantityColumn = new TableColumn<>("Quantidade");
        quantityColumn.setMinWidth(150);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        TableColumn<Product, String> priceCostColumn = new TableColumn<>("Preço de compra");
        priceCostColumn.setMinWidth(150);
        priceCostColumn.setCellValueFactory(new PropertyValueFactory<>("priceCost"));
        
        TableColumn<Product, String> priceSaleColumn = new TableColumn<>("Preço de venda");
        priceSaleColumn.setMinWidth(150);
        priceSaleColumn.setCellValueFactory(new PropertyValueFactory<>("priceSale"));
        
        TableColumn<Product, String> editColumn = new TableColumn<>("Detalhes");
        editColumn.setMinWidth(150);
        addEditButton(editColumn);

        ProductTableView.getColumns().addAll(codeColumn, descriptionColumn, quantityColumn, priceCostColumn, priceSaleColumn);
        productsTab.setContent(ProductTableView);
    }
    
    public void addContent(String search) throws Exception{
        ObservableList<Product> products;
        
        if (search == null) {
            products = FXCollections.observableArrayList(productHandler.getProducts());
            productTableView.setItems(products);
        } else {
            products = null;
            try {
                List<Product> pro = new ArrayList<>();
                pro.add(productHandler.getProduct(Integer.parseInt(search)));
                products = FXCollections.observableArrayList(pro);
            } catch (Exception e) {}
            productTableView.setItems(products);
        }
        
    }
}
