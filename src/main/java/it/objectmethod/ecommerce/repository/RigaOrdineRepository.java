package it.objectmethod.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import it.objectmethod.ecommerce.entity.RigaOrdine;

@Component
@Repository
public interface RigaOrdineRepository extends JpaRepository<RigaOrdine, Long>{

}
