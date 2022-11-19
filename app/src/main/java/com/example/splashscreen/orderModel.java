package com.example.splashscreen;

public class orderModel {
    String Address,Customer_Phone,Customer_name,Flat,House,OptionalInstruction,ProviderName, ProviderPhone,ServicePrice,ServiceType;


 public orderModel()
    {

    }

    public orderModel(String address, String customer_Phone, String customer_name, String flat, String house, String optionalInstruction, String providerName, String providerPhone, String servicePrice, String serviceType) {
        Address = address;
        Customer_Phone = customer_Phone;
        Customer_name = customer_name;
        Flat = flat;
        House = house;
        OptionalInstruction = optionalInstruction;
        ProviderName = providerName;
        ProviderPhone = providerPhone;
        ServicePrice = servicePrice;
        ServiceType = serviceType;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCustomer_Phone() {
        return Customer_Phone;
    }

    public void setCustomer_Phone(String customer_Phone) {
        Customer_Phone = customer_Phone;
    }

    public String getCustomer_name() {
        return Customer_name;
    }

    public void setCustomer_name(String customer_name) {
        Customer_name = customer_name;
    }

    public String getFlat() {
        return Flat;
    }

    public void setFlat(String flat) {
        Flat = flat;
    }

    public String getHouse() {
        return House;
    }

    public void setHouse(String house) {
        House = house;
    }

    public String getOptionalInstruction() {
        return OptionalInstruction;
    }

    public void setOptionalInstruction(String optionalInstruction) {
        OptionalInstruction = optionalInstruction;
    }

    public String getProviderName() {
        return ProviderName;
    }

    public void setProviderName(String providerName) {
        ProviderName = providerName;
    }

    public String getProviderPhone() {
        return ProviderPhone;
    }

    public void setProviderPhone(String providerPhone) {
        ProviderPhone = providerPhone;
    }

    public String getServicePrice() {
        return ServicePrice;
    }

    public void setServicePrice(String servicePrice) {
        ServicePrice = servicePrice;
    }

    public String getServiceType() {
        return ServiceType;
    }

    public void setServiceType(String serviceType) {
        ServiceType = serviceType;
    }
}
