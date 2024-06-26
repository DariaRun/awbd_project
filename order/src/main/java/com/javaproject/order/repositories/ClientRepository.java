package com.javaproject.order.repositories;

import com.javaproject.order.model.Client;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Optional<Client> findByEmail(String email);
    void deleteById(Long id);
}
