package ecommerce_backend.repository;

import ecommerce_backend.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository <Brand, Long>{

}
