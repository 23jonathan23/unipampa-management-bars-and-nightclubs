
package edu.unipampa.poo.management.bars.and.nightclubs.Presentation.Tabs;

import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Client;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.ClientCabin;
import edu.unipampa.poo.management.bars.and.nightclubs.Business.ClientHandler;
import edu.unipampa.poo.management.bars.and.nightclubs.Business.ConsumptionHandler;
import edu.unipampa.poo.management.bars.and.nightclubs.Business.ProductHandler;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Product;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Consumption;
import edu.unipampa.poo.management.bars.and.nightclubs.Presentation.ClientModalController;
import edu.unipampa.poo.management.bars.and.nightclubs.Presentation.AddConsumptionController;

import java.util.List;
import java.util.ArrayList;
import javafx.util.Callback;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class ConsumptionsTab {
    
    private ConsumptionHandler consumptionHandler;
    TableView consumptionTableView;
    
    public void consultConsumption(Consumption consumption) {
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("./AddConsumption.fxml"));
                 
                 Parent root3 = null;
                 try {
                     root3 = loader3.load();
                 } catch (IOException e) {}
                 
                 AddConsumptionController addCons = loader3.getController();
                 
                 Stage stage3 = new Stage();
                 stage3.setScene(new Scene(root3));
                 stage3.show();
                 
                 addCons.setConsumptionHandler(consumptionHandler);
                 addCons.setFields(consumption);
    }
    
    private void addEditButton(TableColumn tableColumn) {
        Callback<TableColumn<Consumption, Void>, TableCell<Consumption, Void>> cellFactory = new Callback<TableColumn<Consumption, Void>, TableCell<Consumption, Void>>() {
            @Override
            public TableCell<Consumption, Void> call(final TableColumn<Consumption, Void> param) {
                final TableCell<Consumption, Void> cell = new TableCell<Consumption, Void>() {
                    private final Button btn = new Button("Detalhes");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            consultConsumption(getTableRow().getItem());
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
    
    public void render(Tab consumptionsTab, ConsumptionHandler consumptionHandler) {
        consumptionTableView = new TableView();
            
        TableColumn<Product, String> codeColumn = new TableColumn<>("Código");
        codeColumn.setMinWidth(150);
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        
        TableColumn<Product, String> descriptionColumn = new TableColumn<>("RG do cliente");
        descriptionColumn.setMinWidth(300);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("rgClient"));
        
        TableColumn<Product, String> codeProductColumn = new TableColumn<>("Código do Produto");
        codeProductColumn.setMinWidth(150);
        codeProductColumn.setCellValueFactory(new PropertyValueFactory<>("codeProduct"));
        
        TableColumn<Product, String> quantityColumn = new TableColumn<>("Quantidade");
        quantityColumn.setMinWidth(150);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        TableColumn<Product, String> editColumn = new TableColumn<>("Detalhes");
        editColumn.setMinWidth(150);
        addEditButton(editColumn);

        consumptionTableView.getColumns().addAll(codeColumn, descriptionColumn, codeProductColumn, quantityColumn);
        consumptionsTab.setContent(consumptionTableView);
    }
    
    public void addContent(String search) throws Exception{
        ObservableList<Consumption> consumptions;
        if (search == null) {
            consumptions = FXCollections.observableArrayList(consumptionHandler.getConsumptions());
            consumptionTableView.setItems(consumptions);
        } else {
            consumptions = FXCollections.observableArrayList(consumptionHandler.getConsumptionsByClient(search));
            consumptionTableView.setItems(consumptions);
        }
    }
}
