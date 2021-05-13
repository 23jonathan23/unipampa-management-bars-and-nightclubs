
package edu.unipampa.poo.management.bars.and.nightclubs.Presentation;

import edu.unipampa.poo.management.bars.and.nightclubs.Presentation.Tabs.ClientsTab;
import edu.unipampa.poo.management.bars.and.nightclubs.Presentation.Tabs.ProductsTab;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Client;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.ClientCabin;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Product;
import edu.unipampa.poo.management.bars.and.nightclubs.Infra.Repository.DBRepository;
import edu.unipampa.poo.management.bars.and.nightclubs.Business.ClientHandler;
import edu.unipampa.poo.management.bars.and.nightclubs.Business.ProductHandler;
import edu.unipampa.poo.management.bars.and.nightclubs.Business.ConsumptionHandler;

import java.net.URL;
import java.util.ResourceBundle;
import java.nio.file.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

public class UserInterfaceController implements Initializable{
    private String activeTab;
    private Path path = Paths.get("C:/data");
    private DBRepository repository = new DBRepository(path);
    private ClientHandler clientHandler = new ClientHandler(repository);
    private ProductHandler productHandler = new ProductHandler(repository);
    private ConsumptionHandler consumptionHandler = new ConsumptionHandler(repository, clientHandler, productHandler);
    
    @FXML
    private TabPane tab;
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

        var clientsTabContent = new ClientsTab();
        clientsTabContent.render(clientsTab, clientHandler, consumptionHandler);

        activeTab = "clients";
    }

    @FXML
    public void onProductsTab() {
        search.setPromptText("Digite o código do produto");
        addClientOrProduct.setText("Adicionar novo produto");

        var productsTabContent = new ProductsTab();
        productsTabContent.render(productsTab, productHandler);

        activeTab = "products";
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        onClientsTab();
    }
}
