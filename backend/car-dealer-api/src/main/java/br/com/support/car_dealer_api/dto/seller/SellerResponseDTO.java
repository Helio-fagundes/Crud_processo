package br.com.support.car_dealer_api.dto.seller;

import lombok.Data;

@Data
public class SellerResponseDTO {
    private String id;
    private String name;
    private String cpf;
    private String phone;
    private String email;
    private boolean manager;

    public void setId(String id) {
        this.id = id;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public void setName(String name) {
        this.name = name;
    }
}
