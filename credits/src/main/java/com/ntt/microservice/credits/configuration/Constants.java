package com.ntt.microservice.credits.configuration;

public class Constants {
  private Constants() {
    throw new IllegalStateException("Utility class");
  }

  public static final String TYPE_PERSONAL_CUSTOMER = "PERSONAL";
  public static final int STATUS_CREDIT_PAID = 1;
  public static final int STATUS_CREDIT_PENDING = 0;
}