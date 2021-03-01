package it.objectmethod.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import it.objectmethod.ecommerce.entity.Cart;

@Component
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	//@Query("SELECT c FROM Cart c WHERE c.user.idUtente = ?1") //
	public Cart findByUserIdUtente (Long idUser);


}
