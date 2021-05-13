
package edu.unipampa.poo.management.bars.and.nightclubs.Presentation;

import edu.unipampa.poo.management.bars.and.nightclubs.Presentation.Tabs.ClientsTab;
import edu.unipampa.poo.management.bars.and.nightclubs.Presentation.Tabs.ProductsTab;
import edu.unipampa.poo.management.bars.and.nightclubs.Presentation.Tabs.ConsumptionsTab;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Client;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.ClientCabin;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Product;
import edu.unipampa.poo.management.bars.and.nightclubs.Infra.Repository.DBRepository;
import edu.unipampa.poo.management.bars.and.nightclubs.Business.ClientHandler;
import edu.unipampa.poo.management.bars.and.nightclubs.Business.ProductHandler;
import edu.unipampa.poo.management.bars.and.nightclubs.Business.ConsumptionHandler;
import edu.unipampa.poo.management.bars.and.nightclubs.Presentation.AddOrEditClientModalController;

import java.net.URL;
import java.util.ResourceBundle;
import java.nio.file.*;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class UserInterfaceController implements Initializable{
    private String activeTab;
    private String activeBox;
    
    private ClientsTab clientsTabContent;
    private ProductsTab productsTabContent;
    private ConsumptionsTab consumptionsTabContent;
    
    private Path path = Paths.get(System.getProperty("user.dir") + "/src/main/java/edu/unipampa/poo/management/bars/and/nightclubs/Infra/DataBase/" + "db.bin");
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
    private Tab consumptionsTab;
    @FXML
    private ComboBox comboBox;
    @FXML
    private Button result;
    
    @FXML
    public void getResult(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Result.fxml"));
        Parent root = null;
        try{
            root = loader.load();
        } catch (Exception e) {}
        
        ResultController rc = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        
        double gasto = 0f;
        double ganho = 0f;
        
        try{
            gasto = consumptionHandler.getSpent();
            ganho = consumptionHandler.getSaled() + clientHandler.getValorEmEntrada();
        } catch (Exception e) {}
        
        rc.colocarTexto(gasto, ganho, ganho - gasto);
        
        try{
            clientHandler.clear();
            consumptionHandler.clear();
        } catch (Exception e) {}
    }
    
    @FXML
    public void onClick(ActionEvent event) {
        activeBox = comboBox.getValue().toString();
        System.out.println(activeBox);
    }
    
    @FXML
    public void doSearch(ActionEvent event) {
        System.out.println("ESTÁ ENTRANDO NA CONSULTA");
        try {
            if (activeTab.equals("clients")) {
                if (search.getText() == null) {
                    clientsTabContent.addContent(activeBox);
                } else {
                    clientsTabContent.addContentByRg(search.getText());
                }
            }
            if (activeTab.equals("products")) {
                productsTabContent.addContent(search.getText());
                System.out.println("PRODUTOS----------------");
            }
            if (activeTab.equals("consumptions")) {
                consumptionsTabContent.addContent(search.getText());
            }
        } catch (Exception e) {}
    }
    
    @FXML
    public void onClientsTab() {
        if (search == null) {
            search = new TextField("Digite o RG do cliente");
        }

        search.setPromptText("Digite o RG do cliente");

        if (addClientOrProduct != null) {
            addClientOrProduct.setText("Adicionar novo cliente");
        }

        clientsTabContent = new ClientsTab();
        clientsTabContent.render(clientsTab, clientHandler, consumptionHandler);
        
        activeTab = "clients";
    }

    @FXML
    public void onProductsTab() {
        search.setPromptText("Digite o código do produto");
        addClientOrProduct.setText("Adicionar novo produto");

        productsTabContent = new ProductsTab();
        productsTabContent.render(productsTab, productHandler);

        activeTab = "products";
    }
    
    @FXML
    public void onConsumptionsTab() {
        search.setPromptText("Digite o código do consumo");
        
        consumptionsTabContent = new ConsumptionsTab();
        consumptionsTabContent.render(consumptionsTab, consumptionHandler);
        
        activeTab = "consumptions";
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        onClientsTab();
        
        comboBox.getItems().addAll("", "VIP", "Pista", "Camarote");
        activeBox = "";
    }
    
    @FXML
    public void addNewEntity(ActionEvent event) {
        switch (activeTab) {
            case "clients":
                FXMLLoader loader = new FXMLLoader(getClass().getResource("./AddOrEditClientModal.fxml"));

                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {}

                AddOrEditClientModalController addOrEditClientModalController = loader.getController();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                addOrEditClientModalController.setClientHandler(clientHandler);
                break;

            case "products":
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("./AddOrEditProductModal.fxml"));
                
                Parent root2 = null;
                try {
                    root2 = loader2.load();
                } catch (IOException e) {}
                
                AddOrEditProductModalController addOrEditProductModalController = loader2.getController();
                
                Stage stage2 = new Stage();
                stage2.setScene(new Scene(root2));
                stage2.show();
                
                addOrEditProductModalController.setProductHandler(productHandler);
                break;
            default:
                break;
        }
    }
    
    public static void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(title);
        alert.setAlertType(alertType);
        alert.setContentText(message);
        alert.show();
   }
}
