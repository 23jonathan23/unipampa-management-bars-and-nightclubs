package edu.unipampa.poo.management.bars.and.nightclubs.Presentation.Tabs;

import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Product;

import java.util.List;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductsTab {
    public static void render(Tab productsTab) {
        TableView ProductTableView = new TableView();
            
        TableColumn<Product, String> codeColumn = new TableColumn<>("Código");
        codeColumn.setMinWidth(150);
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        
        TableColumn<Product, String> descriptionColumn = new TableColumn<>("Nome");
        descriptionColumn.setMinWidth(150);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        ProductTableView.getColumns().addAll(codeColumn, descriptionColumn);
        productsTab.setContent(ProductTableView);
        
        Product Product = new Product(1, "Description", 3, 30d, 20d);
        List<Product> ProductList = new ArrayList<>();
        ProductList.add(Product);
        
        ObservableList<Product> observableProductList = FXCollections.observableArrayList(ProductList);
        
        ProductTableView.setItems(observableProductList);
    }
}
