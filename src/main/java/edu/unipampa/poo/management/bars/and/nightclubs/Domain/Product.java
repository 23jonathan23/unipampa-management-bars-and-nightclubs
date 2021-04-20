package edu.unipampa.poo.management.bars.and.nightclubs.Domain;

import java.io.Serializable;

public class Product implements Serializable {
	private final int MINIMUM_QUANTITY = 0;
	private int _code;
	private String _description;
	private int _quantity;
	private double _priceCost;
	private double _priceSale;

	public Product(int code, String description, int quantity, double priceCost, double priceSale) {
		_code = code;
		_description = description;
		_quantity = quantity;
		_priceCost = priceCost;
		_priceSale = priceSale;
	}

	public int getCode() {
		return _code;
	}

	public String getDescription() {
		return _description;
	}

	public int getQuantity() {
		return _quantity;
	}

	public void setQuantity(int quantity) throws IllegalArgumentException {
		if(quantity <= MINIMUM_QUANTITY) {
			throw new IllegalArgumentException("Valor informado é inválido, o valor informado deve ser maior que 0");
		}
		_quantity = quantity;
	}

	public double getPriceCost() {
		return _priceCost;
	}

	public double getPriceSale() {
		return _priceSale;
	}

	@Override
    public boolean equals(Object product) {
        var productCast = (Product) product;
        return this._code == productCast._code;
    }
}
