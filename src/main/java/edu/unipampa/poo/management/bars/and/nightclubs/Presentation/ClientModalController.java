package edu.unipampa.poo.management.bars.and.nightclubs.Presentation;

import edu.unipampa.poo.management.bars.and.nightclubs.Business.ConsumptionHandler;
import edu.unipampa.poo.management.bars.and.nightclubs.Business.ClientHandler;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Client;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.ClientCabin;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.ClientVip;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Product;
import java.io.IOException;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class ClientModalController implements Initializable{
    private Client c;
    private ClientHandler ch;
    
    @FXML
    private TableView tv;
    @FXML
    private Button save;
    @FXML
    private Button edit;
    @FXML
    private Button delete;
    @FXML
    private Button pay;
    @FXML
    private Text textName;
    @FXML
    private Text textRg;
    @FXML
    private Text textCredit;
    @FXML
    private Text textCategory;
    @FXML
    private Text textTicket;
    
    @FXML
    public void toEdit(ActionEvent event) {
        
    }
    
    @FXML
    public void toSave(ActionEvent event) {
        System.out.println("deu");
    }

    @FXML
    public void toDelete(ActionEvent event) throws Exception, IOException, ClassNotFoundException, IllegalArgumentException {
        System.out.println("deu");
        ch.delete(c);
    }

    @FXML
    public void toPay(ActionEvent event) {
        System.out.println("deu");
    }
    
    public void setClientData(Client client, ConsumptionHandler consumptionHandler, ClientHandler clientHandler) {
        c = client;
        ch = clientHandler;
        textName.setText(client.getName());
        textRg.setText(client.getRg());
        textCredit.setText(client.getCredit() + "");
        
        if (client instanceof ClientVip) {
            textCategory.setText("VIP");
            c = (ClientVip) client;
            textTicket.setText(c.getTicket() + "");
        } else if (client instanceof ClientCabin) {
            textCategory.setText("Camarote");
            c = (ClientCabin) client;
            textTicket.setText(c.getTicket() + "");
        } else {
            textCategory.setText("Pista");
            textTicket.setText("");
        }
        
        List<Product> products = null;

        try {
            products = consumptionHandler.getProducts(client);
        } catch (IllegalArgumentException e) {
            return;
        } catch (Exception e) {
            return;
        }
        
        tv.setItems(loadData(products));
    }
    
    public ObservableList<Product> loadData(List<Product> productList) {
        ObservableList<Product> oList = FXCollections.observableArrayList(productList);

        return oList;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn<Product, String> columnCode = new TableColumn<>("Código");
        columnCode.setMinWidth(150);
        columnCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        
        TableColumn<Product, String> columnDesc = new TableColumn<>("Descrição");
        columnDesc.setMinWidth(150);
        columnDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        TableColumn<Product, String> columnSale = new TableColumn<>("Preço");
        columnSale.setMinWidth(150);
        columnSale.setCellValueFactory(new PropertyValueFactory<>("priceSale"));
        
        tv.getColumns().addAll(columnCode, columnDesc, columnSale);
    }
}
