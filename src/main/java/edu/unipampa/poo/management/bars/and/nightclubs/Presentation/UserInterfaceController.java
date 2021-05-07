
package edu.unipampa.poo.management.bars.and.nightclubs.Presentation;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

public class UserInterfaceController implements Initializable{
    
    @FXML
    private TabPane tab;
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
    }
}
