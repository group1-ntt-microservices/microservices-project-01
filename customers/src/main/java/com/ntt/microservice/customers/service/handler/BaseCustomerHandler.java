package com.ntt.microservice.customers.service.handler;

import java.util.List;

/**
 * Interface for handling base customer operations.
 *
 * @param <T> Type of the response DTO.
 * @param <U> Type of the request DTO.
 */
public interface BaseCustomerHandler<T, U> {

  /**
   * Retrieves a list of all entity response DTOs.
   *
   * @return List of response DTOs.
   */
  List<T> findAll();

  /**
   * Retrieves entity response DTO by its ID.
   *
   * @param id The unique identifier of the entity.
   * @return Response DTO representing the entity if found.
   */
  T findById(String id);

  /**
   * Retrieves entity response DTO by its document number.
   *
   * @param documentNumber The document number of the entity.
   * @return Response DTO representing the entity if found.
   */
  T findByDocumentNumber(String documentNumber);

  /**
   * Saves a new entity.
   *
   * @param entity The request DTO representing the entity to be saved.
   * @return Response DTO representing the saved entity.
   */
  T save(U entity);

  /**
   * Updates an existing entity by its unique identifier.
   *
   * @param id     The unique identifier of the entity to update.
   * @param entity The request DTO representing the updated entity information.
   * @return Response DTO representing the updated entity.
   */
  T update(String id, U entity);

  /**
   * Deletes a entity by its unique identifier.
   *
   * @param id The unique identifier of the entity to delete.
   */
  void deleteById(String id);
}
