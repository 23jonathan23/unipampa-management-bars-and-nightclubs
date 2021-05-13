package edu.unipampa.poo.management.bars.and.nightclubs.Business;

import java.lang.Exception;
import java.util.List;

import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Client;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.ClientVip;
import edu.unipampa.poo.management.bars.and.nightclubs.Domain.ClientCabin;
import edu.unipampa.poo.management.bars.and.nightclubs.Infra.Interfaces.IDBRepository;
import edu.unipampa.poo.management.bars.and.nightclubs.Infra.Repository.DBRepository;
import java.io.IOException;
import java.util.ArrayList;

public class ClientHandler {
    private IDBRepository _repository;
    
    public ClientHandler(DBRepository repository) {
        this._repository = repository;
    }

    public void setClient(String rg, String name, double credit, String category) throws IllegalArgumentException, Exception {
        try {
            Client client;
            
            switch (category) {
                case "cabin":
                    client = new ClientCabin(rg, name, credit);
                    break;

                case "vip":
                    client = new ClientVip(rg, name, credit);
                    break;
            
                default:
                    client = new Client(rg, name, credit, category);
                    break;
            }
            
            if (!client.isValid()) {
                throw new IllegalArgumentException("Dados inválidos: os campos RG e Nome devem ser preenchidos");
            }

            _repository.insert(client);
        } catch (IllegalArgumentException err) {
            throw err;
        } catch (Exception err) {
            throw new Exception("Ocorreu um erro inesperado ao tentar cadastrar o cliente", err);
        }
    }
    
    public void clear() throws Exception, IOException, ClassNotFoundException, IllegalArgumentException {
        List<Client> clientes = getClients();
        for (Client c : clientes) {
            _repository.delete(c);
        }
    }
    
    public void delete(Client client)  throws Exception, IOException, ClassNotFoundException, IllegalArgumentException {
        _repository.delete(client);
    }

    public List<Client> getClients() throws Exception {
        try {
            var clientList = _repository.queryAll();
            
            return clientList;
        } catch(Exception err) {
            throw new Exception("Ocorreu um erro inesperado ao tentar obter a lista de clientes", err);
        }
    }
    
    public double getValorEmEntrada() throws Exception{
        List<Client> clientes = getClients();
        
        double valor = 0d;
        for (Client c : clientes) {
            valor += getTicketPrice(c.getRg());
        }
        return valor;
    }
    
    public double getTicketPrice(String rg) throws Exception, IllegalArgumentException {
        Client cliente = getSingleClient(rg);
        if (cliente instanceof ClientCabin) {
            return 150d;
        } else if (cliente instanceof ClientVip) {
            return 200d;
        }
        return 100d;
    }
    
    public Client getSingleClient(String clientRg) throws Exception, IllegalArgumentException {
        try {
            List<Client> clientList = _repository.queryAll();
            
            for (Client client : clientList) {
                if (client.getRg().equals(clientRg)) {
                    return client;
                }
            }
            
            throw new IllegalArgumentException("Cliente não encontrado");
        } catch (IllegalArgumentException err) {
            throw err;
        } catch(Exception err) {
            throw err;
        }
    }

    public void addCreditToClient(double creditToBeAdded, String clientRg) throws Exception {
        var client = getSingleClient(clientRg);

        client.addCredit(creditToBeAdded);
    }

    public void decreaseCreditFromClient(double creditToBeSubtracted, String clientRg) throws Exception {
        var client = getSingleClient(clientRg);

        client.subCredit(creditToBeSubtracted);
    }
    
    public List<Client> getClientByCategory(String category) throws Exception{
        List<Client> clientsList = getClients();
        List<Client> returnClients = new ArrayList<>();
        for (Client c : clientsList) {
            if (c instanceof ClientVip && category.equals("VIP")) {
                returnClients.add(c);
            } else if (c instanceof ClientCabin && category.equals("Camarote")) {
                returnClients.add(c);
            } else if (category.equals("Pista")) {
                returnClients.add(c);
            } else if (category.equals("")) {
                returnClients.add(c);
            }
        }
        return returnClients;
    }
}
