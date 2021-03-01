package it.objectmethod.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import it.objectmethod.ecommerce.entity.CartDetail;

@Component
@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

}
