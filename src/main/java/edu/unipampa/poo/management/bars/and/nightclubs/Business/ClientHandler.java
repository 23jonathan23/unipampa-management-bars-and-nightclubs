package edu.unipampa.poo.management.bars.and.nightclubs.Business;

import java.lang.Exception;
import java.util.List;

import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Client;
import edu.unipampa.poo.management.bars.and.nightclubs.Infra.Interfaces.IDBRepository;

public class ClientHandler {
    private IDBRepository<Client> _repository;
    
    public ClientHandler(IDBRepository<Client> repository) {
        this._repository = repository;
    }

    public void setClient(String rg, String name, double credit) throws IllegalArgumentException, Exception {
        try {
            var client = new Client(rg, name, credit);
            
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
    
    public Client getSingleClient(int clientCode) throws Exception, IllegalArgumentException {
        try {
            var clientList = _repository.queryAll();
            
            for (var client : listClient) {
                if (client.getCode() == clientCode) {
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

    public void addCreditToClient(double creditToBeAdded, int clientCode) {
        var client = getSingleClient(clientCode);

        client.addCredit(clientCode);
    }
}
