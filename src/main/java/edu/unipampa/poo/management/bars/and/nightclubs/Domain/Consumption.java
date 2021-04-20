package edu.unipampa.poo.management.bars.and.nightclubs.Domain;

public class Consumption {
    private final int MINIMUM_QUANTITY = 0;
	private int _code;
    private String _rgClient;
    private int _codeProduct;
    private int _quantity;

    public Consumption(int code, String rgClient, int codeProduct, int quantity) {
        _code = code;
        _rgClient = rgClient;
        _codeProduct = codeProduct;
        _quantity = quantity;
    }

    public int getCode() {
        return _code;
    }

    public String getRgClient() {
        return _rgClient;
    }

    public int getCodeProduct() {
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
}
