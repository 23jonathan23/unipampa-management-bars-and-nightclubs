package edu.unipampa.poo.management.bars.and.nightclubs.Domain;

public class ClientCabin extends Client {
    private final double TICKET_ADDITION = 1.50;

    public ClientCabin(String rg, String name, double credit) {
        super(rg, name, credit);
        _ticket = _ticket + (_ticket * TICKET_ADDITION);
    }
}
