package com.co.autentic.RTDDataSearch.users.models;

import com.co.autentic.RTDDataSearch.users.aws.models.TransactionItem;

import java.util.List;

public class ResponseListClient extends Responsemodel{
    private List<TransactionItem> clients;
    private String entity;

    public List<TransactionItem> getClients() {
        return clients;
    }

    public void setClients(List<TransactionItem> clients) {
        this.clients = clients;
    }
}
