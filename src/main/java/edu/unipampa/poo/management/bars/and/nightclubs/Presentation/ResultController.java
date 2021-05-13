
package edu.unipampa.poo.management.bars.and.nightclubs.Presentation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class ResultController implements Initializable{
    
    @FXML
    private Text gasto;
    @FXML
    private Text ganho;
    @FXML
    private Text saldo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void colocarTexto(double valorGasto, double valorGanho, double valorSaldo) {
        gasto.setText("Gasto de " + valorGasto);
        ganho.setText("Ganho de " + valorGanho);
        saldo.setText("Saldo de " + valorSaldo);
    }
}
