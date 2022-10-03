package com.example.splashscreen;

public class putPDF {
    public String Fullname;
    public String email;
    public String password;
    public String mobile;
    public putPDF()
    {

    }
    public putPDF(String Fullname, String email, String password, String mobile)
    {
        this.Fullname=Fullname;
        this.email=email;
        this.password=password;
        this.mobile=mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getFullname() {
        return Fullname;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }
}
