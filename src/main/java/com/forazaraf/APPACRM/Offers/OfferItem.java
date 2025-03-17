package com.forazaraf.APPACRM.Offers;

import com.forazaraf.APPACRM.Products.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "offeritems")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfferItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "offer_id", nullable = false)
    private Offer offer;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;
}
