package com.example.splashscreen;

public class model {
    String Description,Image,email,mobile,name,ServiceType,fee;

    public model() {
    }

    public model(String description,String fee, String image, String serviceType, String email, String mobile, String name) {
        Description = description;
        Image = image;
        this.email = email;
        this.mobile = mobile;
        this.name = name;
        ServiceType = serviceType;
        this.fee=fee;

    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceType() {
        return ServiceType;
    }

    public void setServiceType(String serviceType) {
        ServiceType = serviceType;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
