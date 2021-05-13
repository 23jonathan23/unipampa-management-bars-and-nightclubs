
package edu.unipampa.poo.management.bars.and.nightclubs.Presentation;

import edu.unipampa.poo.management.bars.and.nightclubs.Presentation.Tabs.ClientsTab;
import edu.unipampa.poo.management.bars.and.nightclubs.Presentation.Tabs.ProductsTab;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UserInterfaceController implements Initializable {
    @FXML
    private TextField search;
    @FXML
    private Button listAll;
    @FXML
    private Button addClientOrProduct;
    @FXML
    private Tab clientsTab;
    @FXML
    private Tab productsTab;
    
    @FXML
    public void onClientsTab() {
        if (search == null) {
            search = new TextField("Digite o RG do cliente");
        }

        search.setPromptText("Digite o RG do cliente");

        if (addClientOrProduct != null) {
            addClientOrProduct.setText("Adicionar novo cliente");
        }

        ClientsTab.render(clientsTab);
    }

    @FXML
    public void onProductsTab() {
        search.setPromptText("Digite o código do produto");
        addClientOrProduct.setText("Adicionar novo produto");

        ProductsTab.render(productsTab);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        onClientsTab();
    }
}
