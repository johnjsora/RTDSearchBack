package com.co.autentic.RTDDataSearch.users.models;

import com.co.autentic.RTDDataSearch.users.aws.models.proposalmoldel;

public class ResponseProposal extends Responsemodel {
    private proposalmoldel proposal;
    private String Base64FileCSV;

    public proposalmoldel getProposal() {
        return proposal;
    }

    public void setProposal(proposalmoldel proposal) {
        this.proposal = proposal;
    }

    public String getBase64FileCSV() {
        return Base64FileCSV;
    }

    public void setBase64FileCSV(String base64FileCSV) {
        Base64FileCSV = base64FileCSV;
    }
}
