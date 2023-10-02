package com.ntt.microservice.customers.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Base repository interface for entity repository.
 *
 * @param <T> The type of the entity managed by the repository.
 * @param <I> The type of the entity's identifier.
 */
@NoRepositoryBean
public interface BaseCustomerRepository<T, I> extends JpaRepository<T, I> {

  /**
   * Checks if an entity with the given document number exists.
   *
   * @param documentNumber The document number to check.
   * @return True if an entity with the document number exists, false otherwise.
   */
  boolean existsByDocumentNumber(String documentNumber);

  /**
   * Finds an entity by its document number.
   *
   * @param documentNumber The document number to search for.
   * @return An Optional containing the entity if found, or an empty Optional if not found.
   */
  Optional<T> findByDocumentNumber(String documentNumber);
}
