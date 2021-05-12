
package edu.unipampa.poo.management.bars.and.nightclubs.Presentation;

import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Client;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.ClientCabin;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserInterfaceController implements Initializable{
    
    @FXML
    private TabPane tab;
    @FXML
    private Tab clientsTab;
    @FXML
    private Tab clientTab;
    @FXML
    private TextField search;
    @FXML
    private Button listAll;
    @FXML
    private Button add;
    
    @FXML
    public void toClient() {
        if (search == null) {
            search = new TextField("Digite o RG");
        }
        search.setPromptText("Digite o RG");
    }
    @FXML
    public void toProduct() {
        search.setPromptText("Digite o codigo do produto");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        toClient();
        
        TableView tv = new TableView();
        
        TableColumn<Client, String> columnRg = new TableColumn<>("RG");
        columnRg.setMinWidth(150);
        columnRg.setCellValueFactory(new PropertyValueFactory<>("rg"));
        
        TableColumn<Client, String> columnName = new TableColumn<>("Nome");
        columnName.setMinWidth(150);
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        tv.getColumns().addAll(columnRg, columnName);
        clientsTab.setContent(tv);
        
        Client cliente = new ClientCabin("12345", "nome dele", 45d);
        List<Client> listaCliente = new ArrayList<>();
        listaCliente.add(cliente);
        
        ObservableList<Client> ol = FXCollections.observableArrayList(listaCliente);
        
        tv.setItems(ol);
    }
}
