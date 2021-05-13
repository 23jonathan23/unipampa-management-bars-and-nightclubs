package edu.unipampa.poo.management.bars.and.nightclubs.Presentation.Tabs;

import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Product;
import edu.unipampa.poo.management.bars.and.nightclubs.Business.ProductHandler;

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
    public void render(Tab productsTab, ProductHandler productHandler) {
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

        ProductTableView.getColumns().addAll(codeColumn, descriptionColumn, quantityColumn, priceCostColumn, priceSaleColumn);
        productsTab.setContent(ProductTableView);

        List<Product> productList = new ArrayList<>();

        try {
            productList = productHandler.getProducts();
        } catch (Exception e) {
            System.out.println("Não foi possível carregar a lista de produtos");
        }

        Product Product = new Product(1, "Description", 3, 30d, 20d);
        productList.add(Product);

        ObservableList<Product> observableProductList = FXCollections.observableArrayList(productList);
        ProductTableView.setItems(observableProductList);
    }
}
