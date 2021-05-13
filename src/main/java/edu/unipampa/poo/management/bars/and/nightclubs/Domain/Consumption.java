package edu.unipampa.poo.management.bars.and.nightclubs.Domain;

import java.io.Serializable;

public class Consumption implements Serializable {
    private final int MINIMUM_QUANTITY = 0;
    private final int MINIMUM_CODE_PRODUCT = 0;
    private int _code;
    private String _rg;
    private int _codeProduct;
    private int _quantity;

    public Consumption(int code, String rgclient, int codeproduct, int quantity) {
        _code = code;
        _rg = rgclient;
        _codeProduct = codeproduct;
        _quantity = quantity;
    }

    public int getCode() {
        return _code;
    }

    public String getRg() {
        return _rg;
    }

    public int getCodeproduct() {
        return _codeProduct;
    }

    public int getQuantity() {
        return _quantity;
    }

    public void setQuantity(int quantity) {
        if(quantity <= MINIMUM_QUANTITY) {
			throw new IllegalArgumentException("Valor informado é inválido, o valor informado deve ser maior que 0");
		}
        _quantity = quantity;
    }

    @Override
    public boolean equals(Object consumption) {
        var consumptionCast = (Consumption) consumption;
        return _code == consumptionCast._code;
    }

    public boolean isValid() {
		if(_rg.isEmpty() || _codeProduct <= MINIMUM_CODE_PRODUCT || _quantity <= MINIMUM_QUANTITY) {
			return false;
		}
		
		return true;
	}
}
