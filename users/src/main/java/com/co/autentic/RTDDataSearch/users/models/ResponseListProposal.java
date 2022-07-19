package com.co.autentic.RTDDataSearch.users.models;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.co.autentic.RTDDataSearch.users.aws.models.proposalmoldel;

import java.util.List;
import java.util.Map;

public class ResponseListProposal extends Responsemodel {
    private List<proposalmoldel> Proposals;
    private Map<String, AttributeValue> lastKeyEvaluated = null;


    public List<proposalmoldel> getProposals() {
        return Proposals;
    }

    public void setProposals(List<proposalmoldel> proposals) {
        Proposals = proposals;
    }

    public Map<String, AttributeValue> getLastKeyEvaluated() {
        return lastKeyEvaluated;
    }

    public void setLastKeyEvaluated(Map<String, AttributeValue> lastKeyEvaluated) {
        this.lastKeyEvaluated = lastKeyEvaluated;
    }
}
