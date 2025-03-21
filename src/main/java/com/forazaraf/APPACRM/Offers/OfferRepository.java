package com.forazaraf.APPACRM.Offers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByStatus(Offer.OfferStatus status);
}
