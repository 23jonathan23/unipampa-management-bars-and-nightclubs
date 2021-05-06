package edu.unipampa.poo.management.bars.and.nightclubs.Business;

import java.lang.Exception;
import java.util.List;

import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Consumption;
import edu.unipampa.poo.management.bars.and.nightclubs.Infra.Interfaces.IDBRepository;

public class ConsumptionHandler {
    private IDBRepository<Consumption> _repository;
    
    public ConsumptionHandler(IDBRepository<Consumption> repository) {
        this._repository = repository;
    }

    public void setConsumption(String rgClient, int codeProduct, int quantity) throws IllegalArgumentException, Exception {
        try {
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
        
        var SUBTRACT_INDEX = 1;
    
        return consumptionList.get(consumptionList.size() - SUBTRACT_INDEX).getCode();
    }
}
