package edu.unipampa.poo.management.bars.and.nightclubs.Business;

import java.lang.Exception;
import java.util.List;

import edu.unipampa.poo.management.bars.and.nightclubs.Domain.Product;
import edu.unipampa.poo.management.bars.and.nightclubs.Infra.Repository.DBRepository;
import edu.unipampa.poo.management.bars.and.nightclubs.Infra.Interfaces.IDBRepository;
import java.util.ArrayList;

public class ProductHandler {
    private DBRepository _repository;
    
    public ProductHandler(DBRepository repository) {
        this._repository = repository;
    }

    public void setProduct(String description, int quantity, double priceCost, double priceSale) throws IllegalArgumentException, Exception {
        try {
            int CODE_ADDITION = 1;
            int lastCode = getLastCodeIncluded();
            Product product = new Product((lastCode + CODE_ADDITION), description, quantity, priceCost, priceSale);
            
            if(!product.isValid()) {
                throw new IllegalArgumentException("Valores informado para o produto não são válidos");
            }

            _repository.insert(product);
            System.out.println("O ERRO ACONTECE AQUI MESMO EM");
        } catch (IllegalArgumentException err) {
            throw err;
        } catch (Exception err) {
            throw new Exception("Ocorreu um erro inesperado ao tentar cadastrar o produto", err);
        }
    }

    
    public List<Product> getProducts() throws Exception {
        try {
            var listProduct = _repository.queryAll();
            System.out.println("CHEGOU A FAZER");
            List<Product> pro = new ArrayList<>();
            
            for (var v : listProduct) {
                if (v instanceof Product) {
                    Product p = (Product) v;
                    pro.add(p);
                }
            }
            System.out.println("CHEGOU A FAZER222222");
            return pro;
        } catch(Exception err) {
            throw new Exception("Ocorreu um erro inesperado ao tentar obter os dados referentes ao produto", err);
        }
    }
    
    public Product getProduct(int codeProduct) throws Exception, IllegalArgumentException {
        try {
            List<Product> listProduct = _repository.queryAll();
            
            for (Product product : listProduct) {
                if (product.getCode() == codeProduct) {
                    return product;
                }
            }
            
            throw new IllegalArgumentException("O codigo informado está incorreto ou o produto não existe");
        } catch (IllegalArgumentException err) {
            throw err;
        } catch(Exception err) {
            throw err;
        }
    }
    
    public void addItem(int codeProduct, int quantity) throws IllegalArgumentException, Exception {
        try {
            var product = getProduct(codeProduct);
            product.setQuantity(product.getQuantity() + quantity);

            _repository.update(product);
        } catch (IllegalArgumentException err) {
            throw err;
        } catch (Exception err) {
            throw new Exception("Ocorreu um erro inesperado ao tentar adicionar o item ao produto", err);
        }
    }
    
    private int getLastCodeIncluded() throws Exception {
        List<Product> listProduct = getProducts();
        
        var SUBTRACT_INDEX = 1;
    
        if (listProduct.size() == 0) {
            return 1;
        }
        return listProduct.get(listProduct.size() - SUBTRACT_INDEX).getCode();
    }
}
