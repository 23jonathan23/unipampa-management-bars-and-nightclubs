package edu.unipampa.poo.management.bars.and.nightclubs.Business;

import java.lang.Exception;
import java.util.List;

import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Client;
import edu.unipampa.poo.management.bars.and.nightclubs.Infra.Interfaces.IDBRepository;
import edu.unipampa.poo.management.bars.and.nightclubs.Infra.Repository.DBRepository;

public class ClientHandler {
    private IDBRepository _repository;
    
    public ClientHandler(DBRepository repository) {
        this._repository = repository;
    }

    public void setClient(String rg, String name, double credit, String category) throws IllegalArgumentException, Exception {
        try {
            var client = new Client(rg, name, credit, category);
            
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

    public List<Client> getClients() throws Exception {
        try {
            var clientList = _repository.queryAll();
            
            return clientList;
        } catch(Exception err) {
            throw new Exception("Ocorreu um erro inesperado ao tentar obter a lista de clientes", err);
        }
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
}
