package com.ntt.microservice.credits.configuration;

/**
 * Constants used in the application.
 */
public class Constants {
  private Constants() {
    throw new IllegalStateException("Utility class");
  }

  public static final String TYPE_PERSONAL_CUSTOMER = "PERSONAL";
  public static final int STATUS_CREDIT_PAID = 1;
  public static final int STATUS_CREDIT_PENDING = 0;
  public static final String AUTHOR_FULL_NAME = "Keny Danfer Chumacero Jibaja";
  public static final String AUTHOR_EMAIL = "kenychumacero19@gmail.com";
  public static final String AUTHOR_GITHUB = "https://github.com/kenydanfer01";
  public static final String TITLE_APPLICATION = "API Credits Service";

  public static final String DESCRIPTION_APPLICATION =
      "Service API documentation for managing credits and credit cards.";

  public static final String VERSION_APPLICATION = "1.0";
  public static final String LICENSE = "Apache 2.0";
  public static final String LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0";

  public static final String REPOSITORY_URL =
      "https://github.com/group1-ntt-microservices/microservices-project-01";
}