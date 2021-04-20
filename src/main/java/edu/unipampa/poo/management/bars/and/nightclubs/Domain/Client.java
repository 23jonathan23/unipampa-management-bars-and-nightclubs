package edu.unipampa.poo.management.bars.and.nightclubs.Domain;

public class Client {
    private final int MINIMUM_CREDIT = 0;
    protected double _ticket = 100.00;
    protected String _rg;
    protected String _name;
    protected double _credit;

    public Client(String rg, String name, double credit) {
        _rg = rg;
        _name = name;
        _credit = credit;
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

    public void setCredit(double credit) throws IllegalArgumentException {
        if(credit <= MINIMUM_CREDIT) {
            throw new IllegalArgumentException("Valor informado é inválido, o valor informado deve ser maior que 0");
        }
        _credit = credit;
    }
}