package edu.unipampa.poo.management.bars.and.nightclubs.Business;

import java.lang.Exception;
import java.util.ArrayList;
import java.util.List;

import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Client;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.ClientCabin;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.ClientVip;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Consumption;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Product;
import edu.unipampa.poo.management.bars.and.nightclubs.Infra.Repository.DBRepository;
import java.io.IOException;

public class ConsumptionHandler {
    private DBRepository _repository;
    private ClientHandler _clientHandler;
    private ProductHandler _productHandler;
    
    public ConsumptionHandler(DBRepository repository, ClientHandler clientHandler, ProductHandler productHandler) {
        this._repository = repository;
        this._clientHandler = clientHandler;
        this._productHandler = productHandler;
    }

    public void setConsumption(String rgClient, int codeProduct, int quantity) throws IllegalArgumentException, Exception {
        try {
            var client = _clientHandler.getSingleClient(rgClient);
            
            var product = _productHandler.getProduct(codeProduct);
            var totalConsumption = (product.getPriceSale() * quantity);
            
            if(totalConsumption > client.getTicket()) {
                throw new IllegalArgumentException("Seu saldo não é suficiente para consumir esse produto!");
            }

            var CODE_ADDITION = 1;
            var lastCode = getLastConsumptionCode();
            var consumption = new Consumption((lastCode + CODE_ADDITION), rgClient, codeProduct, quantity);
            
            if (!consumption.isValid()) {
                throw new IllegalArgumentException("Dados inválidos: o RG, código do produto e quantidade não podem ser vazios");
            }

            _repository.insert(consumption);
        } catch (IllegalArgumentException err) {
            throw err;
        } catch (Exception err) {
            throw new Exception("Ocorreu um erro inesperado ao tentar cadastrar o consumo", err);
        }
    }

    public double payConsumptionsByClient(String rgClient) throws Exception {
        try {
            var client = _clientHandler.getSingleClient(rgClient);
            
            var consumptions = getConsumptionsByClient(client);
            
            var totalPayable = client.getTicket();
            for (var consumption : consumptions) {
                var product = _productHandler.getProduct(consumption.getCodeProduct());
                
                totalPayable += (consumption.getQuantity() * product.getPriceSale());
            }

            client.subCredit(totalPayable);

            for (var consumption : consumptions) {
                _repository.delete(consumption);
            }

            return client.getCredit();

        } catch (IllegalArgumentException err) {
            throw err;
        } catch(Exception err) {
            throw new Exception("Ocorreu um erro inesperado ao tentar realizar o pagamento da conta do cliente", err);
        }
    }
    
    public double getSpent() throws Exception{
        List<Consumption> consu = getConsumptions();
        double valor = 0d;
        for (Consumption c : consu) {
            valor += _productHandler.getProduct(c.getCodeProduct()).getPriceCost() * c.getQuantity();
        }
        return valor;
    }
    
    public double getSaled() throws Exception{
        List<Consumption> consu = getConsumptions();
        double valor = 0d;
        for (Consumption c : consu) {
            valor += _productHandler.getProduct(c.getCodeProduct()).getPriceSale() * c.getQuantity();
        }
        return valor;
    }
    
    public List<String> getReportAll() throws IllegalArgumentException, Exception {
        List<Consumption> cons = getConsumptions();
        List<String> valores = new ArrayList<>();
        
        for (Consumption c : cons) {
            valores.add(getReportConsumptionsByClient(c.getRgClient()));
        }
        return valores;
    }

    public String getReportConsumptionsByClient(String rgClient) throws IllegalArgumentException, Exception {
        try { 
            var client = _clientHandler.getSingleClient(rgClient);
            
            var consumptions = getConsumptionsByClient(client);

            String clientType = " [PISTA]";

            if (client instanceof ClientVip) {
                clientType = " [VIP]";
            } else if (client instanceof ClientCabin) {
                clientType = " [CAMAROTE]";
            }

            var report = "Cliente: " + client.getName() + clientType + "\nEntrada: " + client.getTicket();
            
            double totalPrice = 0;

            for (var consumption : consumptions) {
                var product = _productHandler.getProduct(consumption.getCodeProduct());
                
                double totalPriceByProduct = (consumption.getQuantity() * product.getPriceSale());
                
                report = "\n" + product.getDescription() + 
                    " Quantidade: " + consumption.getQuantity() + 
                    " Preço unidade: " + product.getPriceSale() +
                    " Preço total: " + totalPriceByProduct;
                
                totalPrice += totalPriceByProduct;
            }
            
            if (!(client instanceof ClientVip)) {
                report = "\nTotal: " + totalPrice;
            }

            return report;
        } catch (IllegalArgumentException err) {
            throw err;
        } catch(Exception err) {
            throw new Exception("Ocorreu um erro inesperado ao tentar obter os consumos do cliente", err);
        }
    }
    
    public List<Product> getProducts(Client client) throws Exception, IllegalArgumentException{
        List<Product> products = new ArrayList<>();
        
        List<Consumption> consu = getConsumptionsByClient(client);
        
        for (Consumption c : consu) {
            products.add(_productHandler.getProduct(c.getCodeProduct()));
        }
        
        return products;
    }

    private List<Consumption> getConsumptionsByClient(Client client) throws Exception, IllegalArgumentException {
        try {
            var listConsumptions = getConsumptions();
            var clientConsumptions = new ArrayList<Consumption>();

            for (var consumption : listConsumptions) {
                if (consumption.getRgClient().equals(client.getRg())) {
                    clientConsumptions.add(consumption);
                }
            }

            return clientConsumptions;

        } catch (IllegalArgumentException err) {
            throw err;
        } catch(Exception err) {
            throw err;
        }
    }
    
    public List<Consumption> getConsumptionsByClient(String rg) throws Exception, IllegalArgumentException {
        Client client = _clientHandler.getSingleClient(rg);
        return getConsumptionsByClient(client);
    }
    
    public void clear()  throws Exception, IOException, ClassNotFoundException, IllegalArgumentException {
        List<Consumption> cons = getConsumptions();
        
        for (Consumption c : cons) {
            _repository.delete(c);
        }
    }

    public List<Consumption> getConsumptions() throws Exception {
        try {
            var consumptionList = _repository.queryAll();
            
            return consumptionList;
        } catch(Exception err) {
            throw new Exception("Ocorreu um erro inesperado ao tentar obter a lista de consumos", err);
        }
    }

    private int getLastConsumptionCode() throws Exception {
        var consumptionList = getConsumptions();
        
        if (consumptionList.size() == 0) {
            return 0;
        }
        
        var SUBTRACT_INDEX = 1;
    
        return consumptionList.get(consumptionList.size() - SUBTRACT_INDEX).getCode();
    }
}
