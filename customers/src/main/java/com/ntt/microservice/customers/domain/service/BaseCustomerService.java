package com.ntt.microservice.customers.domain.service;

import java.util.List;
import java.util.Optional;

/**
 * A base service interface for managing entities.
 *
 * @param <T> The entity type.
 * @param <I> The type of the entity's identifier.
 */
public interface BaseCustomerService<T, I> {

  /**
   * Retrieves all entities.
   *
   * @return A list of entities.
   */
  List<T> findAll();

  /**
   * Retrieves an entity by its identifier.
   *
   * @param id The identifier of the entity.
   * @return An optional containing the entity if found.
   */
  Optional<T> findById(I id);

  /**
   * Retrieves an entity by its document number.
   *
   * @param documentNumber The document number of the entity.
   * @return An optional containing the entity if found.
   */
  Optional<T> findByDocumentNumber(String documentNumber);

  /**
   * Saves an entity.
   *
   * @param entity The entity to save.
   * @return The saved entity.
   */
  T save(T entity);

  /**
   * Checks if an entity with the given document number exists.
   *
   * @param documentNumber The document number to check.
   * @return True if an entity with the document number exists; otherwise, false.
   */
  boolean existsByDocumentNumber(String documentNumber);

  /**
   * Deletes an entity by its identifier.
   *
   * @param id The identifier of the entity to delete.
   */
  void deleteById(I id);
}
