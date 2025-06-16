package br.com.support.car_dealer_api.dto.client;

import lombok.Data;

@Data
public class ClientResponseDTO {
    public String id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSocialSecurity(String socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    public void setName(String name) {
        this.name = name;
    }

}