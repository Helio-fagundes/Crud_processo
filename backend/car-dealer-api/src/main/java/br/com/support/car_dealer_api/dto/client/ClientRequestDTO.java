package br.com.support.car_dealer_api.dto.client;

import lombok.Data;

@Data
public class ClientRequestDTO {
    public String name;
    public String socialSecurity;
    public String phone;
    public String email;
    public String address;
    public String number;
    public String complement;
    public String neighborhood;
    public String city;
    public String state;

}

