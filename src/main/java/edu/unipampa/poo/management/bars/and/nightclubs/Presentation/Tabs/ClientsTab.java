package edu.unipampa.poo.management.bars.and.nightclubs.Presentation.Tabs;

import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Client;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.ClientCabin;
import edu.unipampa.poo.management.bars.and.nightclubs.Business.ClientHandler;
import edu.unipampa.poo.management.bars.and.nightclubs.Business.ConsumptionHandler;
import edu.unipampa.poo.management.bars.and.nightclubs.Presentation.ClientModalController;

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

public class ClientsTab {
    
    private ClientHandler clientHandler;
    private TableView clientTableView;
    
    private void consultClient(Client client, ConsumptionHandler consumptionHandler) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ClientModal.fxml"));

        Parent root = null;
        
        try {
            root = loader.load();
        } catch (IOException ioe) {
            System.out.println("Algo deu errado :(");
        }
        
        ClientModalController clientModalController = loader.getController();
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        
        clientModalController.setClientData(client, consumptionHandler, clientHandler);
    }

    private void addEditButton(TableColumn tableColumn, ConsumptionHandler consumptionHandler) {
        Callback<TableColumn<Client, Void>, TableCell<Client, Void>> cellFactory = new Callback<TableColumn<Client, Void>, TableCell<Client, Void>>() {
            @Override
            public TableCell<Client, Void> call(final TableColumn<Client, Void> param) {
                final TableCell<Client, Void> cell = new TableCell<Client, Void>() {
                    private final Button btn = new Button("Detalhes");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            consultClient(getTableRow().getItem(), consumptionHandler);
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

    public void render(Tab clientsTab, ClientHandler clientHandler, ConsumptionHandler consumptionHandler) {
        
        this.clientHandler = clientHandler;
        
        clientTableView = new TableView();
            
        TableColumn<Client, String> rgColumn = new TableColumn<>("RG");
        rgColumn.setMinWidth(150);
        rgColumn.setCellValueFactory(new PropertyValueFactory<>("rg"));
        
        TableColumn<Client, String> nameColumn = new TableColumn<>("Nome");
        nameColumn.setMinWidth(150);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        // TableColumn<Client, String> categoryColumn = new TableColumn<>("Categoria");
        // categoryColumn.setMinWidth(150);
        // categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Client, String> creditColumn = new TableColumn<>("Cr??ditos");
        creditColumn.setMinWidth(150);
        creditColumn.setCellValueFactory(new PropertyValueFactory<>("credit"));

        TableColumn<Client, String> editColumn = new TableColumn<>("Detalhes");
        editColumn.setMinWidth(150);
        addEditButton(editColumn, consumptionHandler);

        clientTableView.getColumns().addAll(rgColumn, nameColumn, /*categoryColum,*/ creditColumn, editColumn);
        clientsTab.setContent(clientTableView);
    }
    
    public void setContents() throws Exception {
        ObservableList<Client> observableClientList = FXCollections.observableArrayList(clientHandler.getClients());
        clientTableView.setItems(observableClientList);
    }

    public void addContent(String category) throws Exception {
        ObservableList<Client> observableClientList = FXCollections.observableArrayList(clientHandler.getClientByCategory(category));
        clientTableView.setItems(observableClientList);
    }
    
    public void addContentByRg(String rg) throws Exception {
        ObservableList<Client> observableClientList = FXCollections.observableArrayList(clientHandler.getSingleClient(rg));
        clientTableView.setItems(observableClientList);
    }
}
