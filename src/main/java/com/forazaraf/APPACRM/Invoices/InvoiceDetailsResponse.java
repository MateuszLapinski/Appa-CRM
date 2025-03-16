package com.forazaraf.APPACRM.Invoices;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InvoiceDetailsResponse {
    private Invoice invoice;
    private List<InvoiceItem> items;

    public InvoiceDetailsResponse(Invoice invoice, List<InvoiceItem> items) {
        this.invoice = invoice;
        this.items = items;
    }
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }
    
    
}
