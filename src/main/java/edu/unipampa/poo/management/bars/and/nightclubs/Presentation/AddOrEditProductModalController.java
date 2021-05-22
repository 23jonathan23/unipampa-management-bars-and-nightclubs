
package edu.unipampa.poo.management.bars.and.nightclubs.Presentation;

import edu.unipampa.poo.management.bars.and.nightclubs.Business.ClientHandler;
import edu.unipampa.poo.management.bars.and.nightclubs.Business.ProductHandler;
import edu.unipampa.poo.management.bars.and.nightclubs.Business.ConsumptionHandler;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Client;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.ClientCabin;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.ClientVip;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Product;

import java.net.URL;
import java.util.List;
import javafx.stage.Stage;
import java.lang.Double;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;

public class AddOrEditProductModalController implements Initializable{
    private ProductHandler productHandler;
    Product product = null;
    
    @FXML
    private Button save;
    @FXML
    private Button delete;
    @FXML
    private TextField code;
    @FXML
    private TextField description;
    @FXML
    private TextField quantity;
    @FXML
    private TextField buyPrice;
    @FXML
    private TextField sellPrice;
    
    @FXML
    public void toSave(ActionEvent event) {
        
        try {
            if(!(code.getText().isEmpty())) {
                productHandler.addItem(Integer.parseInt(code.getText()), Integer.parseInt(quantity.getText()));
            } else {
                 productHandler.setProduct(description.getText(), Integer.parseInt(quantity.getText()), 
                Double.parseDouble(buyPrice.getText()), Double.parseDouble(sellPrice.getText()));
            }
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void toDelete(ActionEvent event) {
        System.out.println("deu certo essa merda mano que porra foi essa");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    public void setProductHandler(ProductHandler productHandler) {
        this.productHandler = productHandler;
    }
    
    public void setFields(Product product) {
        description.setText(product.getDescription());
        description.setEditable(false);
        quantity.setText(product.getQuantity() + "");
        buyPrice.setText(product.getPriceCost() + "");
        buyPrice.setEditable(false);
        sellPrice.setText(product.getPriceSale() + "");
        sellPrice.setEditable(false);
        code.setText(product.getCode() + "");
        code.setEditable(false);
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
}
