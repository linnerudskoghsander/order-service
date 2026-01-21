package app.order.repository.customer;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import app.order.entity.CustomerEntity;

@Repository
public interface CustomerRepositoryImpl extends CustomerRepository, JpaRepository<CustomerEntity, Long> {
}



