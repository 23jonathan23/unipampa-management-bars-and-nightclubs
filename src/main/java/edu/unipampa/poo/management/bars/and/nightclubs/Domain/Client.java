package edu.unipampa.poo.management.bars.and.nightclubs.Domain;

import java.io.Serializable;

public class Client implements Serializable {
    private final int MINIMUM_CREDIT = 0;
    protected double _ticket = 100.00;
    protected String _rg;
    protected String _name;
    protected double _credit;
    protected String _category;

    public Client(String rg, String name, double credit, String category) {
        _rg = rg;
        _name = name;
        _credit = credit;
        _category = category;
    }

    public double getTicket() {
        return _ticket;
    }

    public String getRg() {
        return _rg;
    }

    public String getName() {
        return _name;
    }

    public double getCredit() {
        return _credit;
    }

    public String getCategory() {
        return _category;
    }

    public void addCredit(double credit) throws IllegalArgumentException {
        if (credit <= MINIMUM_CREDIT) {
            throw new IllegalArgumentException("O valor informado deve ser maior do que 0");
        }

        _credit += credit;
    }

    public void subCredit(double credit) throws IllegalArgumentException {
        if (credit <= MINIMUM_CREDIT) {
            throw new IllegalArgumentException("O valor informado deve ser maior do que 0");
        } else if ((_credit - credit) < MINIMUM_CREDIT) {
            throw new IllegalArgumentException("O débito a descontar é maior que o saldo atual!");
        }

        _credit -= credit;
    }
    
    @Override
    public boolean equals(Object client) {
        var clientCast = (Client) client;
        return _rg.equals(clientCast._rg);
    }

    public boolean isValid() {
		if (_rg.isEmpty() || _name.isEmpty()) {
			return false;
		}
		
		return true;
	}
}
