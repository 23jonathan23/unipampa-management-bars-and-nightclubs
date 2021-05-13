
package edu.unipampa.poo.management.bars.and.nightclubs.Presentation;

import edu.unipampa.poo.management.bars.and.nightclubs.Presentation.Tabs.ClientsTab;
import edu.unipampa.poo.management.bars.and.nightclubs.Presentation.Tabs.ProductsTab;
import edu.unipampa.poo.management.bars.and.nightclubs.Presentation.Tabs.ConsumptionsTab;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Client;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.ClientCabin;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Product;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Consumption;
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

public class AddConsumptionController implements Initializable{
    
    private ConsumptionHandler consumptionHandler;
    
    @FXML
    private Button save;
    @FXML
    private TextField rg;
    @FXML
    private TextField codPro;
    @FXML
    private TextField quantity;
    
    @FXML
    public void toSave(ActionEvent event) {
        try {
            consumptionHandler.setConsumption(rg.getText(), Integer.parseInt(codPro.getText()), 
                    Integer.parseInt(quantity.getText()));
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setFields(Consumption consumption) {
        rg.setText(consumption.getRgClient());
        codPro.setText(consumption.getCodeProduct() + "");
        quantity.setText(consumption.getQuantity() + "");
    }
    
    public void setConsumptionHandler(ConsumptionHandler consumptionHandler) {
        this.consumptionHandler = consumptionHandler;
    }
}
