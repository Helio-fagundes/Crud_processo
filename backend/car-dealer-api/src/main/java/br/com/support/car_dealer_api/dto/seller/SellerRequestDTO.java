package br.com.support.car_dealer_api.dto.seller;

public class SellerRequestDTO {

    private String name;
    private String cpf;
    private String phone;
    private String email;
    private boolean manager;

    public String getCpf() {
        return cpf;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public boolean isManager() {
        return manager;
    }

    public String getName() {
        return name;
    }
}
