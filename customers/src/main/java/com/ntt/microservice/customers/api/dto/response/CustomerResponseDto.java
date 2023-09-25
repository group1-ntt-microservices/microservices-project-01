package com.ntt.microservice.customers.api.dto.response;

        import lombok.Getter;
        import lombok.Setter;

@Getter
@Setter
public class CustomerResponseDto {
    private String id;
    private String documentNumber;
    private String name;
    private String customerType;
    private String address;
    private String phone;
    private String mail;
}
