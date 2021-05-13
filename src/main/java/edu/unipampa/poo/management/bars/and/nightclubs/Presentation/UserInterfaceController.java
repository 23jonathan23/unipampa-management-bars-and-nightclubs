
package edu.unipampa.poo.management.bars.and.nightclubs.Presentation;

import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Client;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.ClientCabin;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Product;
import edu.unipampa.poo.management.bars.and.nightclubs.Infra.Repository.DBRepository;
import edu.unipampa.poo.management.bars.and.nightclubs.Business.ClientHandler;
import edu.unipampa.poo.management.bars.and.nightclubs.Business.ProductHandler;
import edu.unipampa.poo.management.bars.and.nightclubs.Business.ConsumptionHandler;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.ResourceBundle;
import java.nio.file.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.stage.Stage;
import javafx.util.Callback;

public class UserInterfaceController implements Initializable{
    
    private boolean clientOrProduct;
    
    private TableView tv = new TableView();
    private TableView tv2 = new TableView();
    
    private Path path = Paths.get("C:/data");
    private DBRepository repository = new DBRepository(path);
    private ClientHandler clientHandler = new ClientHandler(repository);
    private ProductHandler productHandler = new ProductHandler(repository);
    private ConsumptionHandler cHandler = new ConsumptionHandler(repository);
    
    @FXML
    private TabPane tab;
    @FXML
    private Tab clientsTab;
    @FXML
    private Tab productsTab;
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
        clientOrProduct = true;
    }
    @FXML
    public void toProduct() {
        search.setPromptText("Digite o codigo do produto");
        clientOrProduct = false;
    }
    
    @FXML
    public void loadAll(ActionEvent ae){
        
        System.out.println("APERTOU----------");
        if (clientOrProduct) {
            loadAllClients();
        } else {
            loadAllProducts();
        }
    }
    
    public void loadAllClients(){
        List<Client> clientList = new ArrayList<>();
        try {
            clientList = clientHandler.getClients();
        } catch (Exception e) {
            
        }
        ObservableList<Client> oClientList = FXCollections.observableArrayList(clientList);
        tv.setItems(oClientList);
        
        Client c = new Client("12345", "ronaldo", 45d);
        List<Client> listC = new ArrayList<>();
        listC.add(c);
        ObservableList<Client> oc = FXCollections.observableArrayList(listC);
        tv.setItems(oc);
    }
    
    public void loadAllProducts(){
        List<Product> productList = new ArrayList<>();
        try {
            productList = productHandler.getProducts();
        } catch (Exception e) {
            
        }
        ObservableList<Product> oProductList = FXCollections.observableArrayList(productList);
        tv2.setItems(oProductList);
    }

    public void consult(Client client) {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientModal.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ioe) {
            System.out.println("não deu certo isso aqui");
        }
        
        ClientModalController cmController = loader.getController();
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        
        cmController.setClientData(client, cHandler);
    }
    
    public void addButton(TableColumn tc) {

        Callback<TableColumn<Client, Void>, TableCell<Client, Void>> cellFactory = new Callback<TableColumn<Client, Void>, TableCell<Client, Void>>() {
            @Override
            public TableCell<Client, Void> call(final TableColumn<Client, Void> param) {
                final TableCell<Client, Void> cell = new TableCell<Client, Void>() {

                    private final Button btn = new Button("Ver");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            consult(getTableRow().getItem());
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
        tc.setCellFactory(cellFactory);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        toClient();
        
        TableColumn<Client, String> columnRg = new TableColumn<>("RG");
        columnRg.setMinWidth(150);
        columnRg.setCellValueFactory(new PropertyValueFactory<>("rg"));
        
        TableColumn<Client, String> columnName = new TableColumn<>("Nome");
        columnName.setMinWidth(150);
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<Client, String> columnCredit = new TableColumn<>("Credito");
        columnCredit.setMinWidth(150);
        columnCredit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        
        TableColumn<Client, String> columnEdit = new TableColumn<>("  ");
        columnEdit.setMinWidth(150);
        addButton(columnEdit);
        
        tv.getColumns().addAll(columnRg, columnName, columnCredit, columnEdit);
        
        TableColumn<Product, String> columnId = new TableColumn<>("Código");
        columnId.setMinWidth(150);
        columnId.setCellValueFactory(new PropertyValueFactory<>("code"));
        
        TableColumn<Product, String> columnDesc = new TableColumn<>("Descrição");
        columnDesc.setMinWidth(300);
        columnDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        TableColumn<Product, String> columnQuantity = new TableColumn<>("Quantidade");
        columnQuantity.setMinWidth(150);
        columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        TableColumn<Product, String> columnCost = new TableColumn<>("Preço de Compra");
        columnCost.setMinWidth(150);
        columnCost.setCellValueFactory(new PropertyValueFactory<>("priceCost"));
        
        TableColumn<Product, String> columnSale = new TableColumn<>("Preço de Venda");
        columnSale.setMinWidth(150);
        columnSale.setCellValueFactory(new PropertyValueFactory<>("priceSale"));
        
        TableColumn<Product, String> columnEdit2 = new TableColumn<>("  ");
        columnEdit2.setMinWidth(150);
        
        tv2.getColumns().addAll(columnId, columnDesc, columnQuantity, columnCost, columnSale, columnEdit2);
        
        clientsTab.setContent(tv);
        productsTab.setContent(tv2);
    }
}
