package edu.unipampa.poo.management.bars.and.nightclubs.Presentation.Tabs;

import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Client;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.ClientCabin;

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

public class ClientsTab {
    public static void render(Tab clientsTab) {
        TableView clientTableView = new TableView();
            
        TableColumn<Client, String> rgColumn = new TableColumn<>("RG");
        rgColumn.setMinWidth(150);
        rgColumn.setCellValueFactory(new PropertyValueFactory<>("rg"));
        
        TableColumn<Client, String> nameColumn = new TableColumn<>("Nome");
        nameColumn.setMinWidth(150);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        // TableColumn<Client, String> categoryColumn = new TableColumn<>("Categoria");
        // categoryColumn.setMinWidth(150);
        // categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Client, String> creditColumn = new TableColumn<>("Créditos");
        creditColumn.setMinWidth(150);
        creditColumn.setCellValueFactory(new PropertyValueFactory<>("credit"));

        clientTableView.getColumns().addAll(rgColumn, nameColumn, /*categoryColum,*/ creditColumn);
        clientsTab.setContent(clientTableView);
        
        Client client = new ClientCabin("000000000", "Cliente fictício", 45d);
        List<Client> clientList = new ArrayList<>();
        clientList.add(client);
        
        ObservableList<Client> observableClientList = FXCollections.observableArrayList(clientList);
        
        clientTableView.setItems(observableClientList);
    }
}
