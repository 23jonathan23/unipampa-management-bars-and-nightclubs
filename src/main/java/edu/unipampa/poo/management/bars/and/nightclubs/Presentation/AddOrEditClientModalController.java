package edu.unipampa.poo.management.bars.and.nightclubs.Presentation;

import edu.unipampa.poo.management.bars.and.nightclubs.Business.ClientHandler;
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

public class AddOrEditClientModalController implements Initializable {
    private ClientHandler _clientHandler;

    @FXML
    private Button save;
    @FXML
    private TextField rg;
    @FXML
    private TextField name;
    @FXML
    private TextField credits;
    @FXML
    private ChoiceBox<String> categoryDropdown;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryDropdown.getItems().addAll("Pista", "Camarote", "VIP");
        categoryDropdown.getSelectionModel().select(0);
    }

    @FXML
    public void save(ActionEvent event) {
        double credit = 0.0;

        try {
            DecimalFormat df = new DecimalFormat(); 
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setDecimalSeparator(','); 
            df.setDecimalFormatSymbols(dfs);
            credit = df.parse(credits.getText()).doubleValue();
        } catch (Exception e) {
            UserInterfaceController.showAlert("Créditos inválidos", "Insira créditos no formato 0,00", Alert.AlertType.ERROR);
        }

        try {
            switch (categoryDropdown.getValue()) {
                case "VIP":
                    _clientHandler.setClient(rg.getText(), name.getText(), credit, "vip");
                    break;
    
                case "Camarote":
                    _clientHandler.setClient(rg.getText(), name.getText(), credit, "cabin");
                    break;
            
                default:
                    _clientHandler.setClient(rg.getText(), name.getText(), credit, "");
                    break;
            }   
        } catch (Exception e) {
            UserInterfaceController.showAlert("Dados inválidos", e.getMessage(), Alert.AlertType.ERROR);
        }

        Client clientExists = null;
        try { 
            clientExists = _clientHandler.getSingleClient(rg.getText());
        } catch (Exception e) {}
        
        if (clientExists != null) {
            UserInterfaceController.showAlert("Cliente atualizado", "Cliente salvo com sucesso :)", Alert.AlertType.INFORMATION);

            Stage stage = (Stage) save.getScene().getWindow();
            stage.close();
        }
    }

    public void setClientHandler(ClientHandler clientHandler) {
        _clientHandler = clientHandler;
    }
}
