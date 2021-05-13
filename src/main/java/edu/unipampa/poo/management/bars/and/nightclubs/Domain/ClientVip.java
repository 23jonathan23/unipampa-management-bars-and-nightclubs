package edu.unipampa.poo.management.bars.and.nightclubs.Domain;

public class ClientVip extends Client {
    private final double TICKET_ADDITION = 2.0;
    
    public ClientVip(String rg, String name, double credit) {
        super(rg, name, credit, "vip");
        _ticket = _ticket + (_ticket * TICKET_ADDITION);
    }
}