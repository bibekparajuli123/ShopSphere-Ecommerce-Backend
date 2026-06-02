package com.shopsphere.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {

    private Long customerId;

    private String name;

    private String email;

    private String phone;

    private String address;
}