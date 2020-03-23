package com.eduvision.version2.corona;

public class UserInformation {

    public String name;
    public String numero;

    public UserInformation(){
    }

    public UserInformation(String name, String numero){
        this.name = name;

        this.numero = numero;
    }
    public String getUserName() {
        return name;
    }

    public String getUserNumero() {
        return numero;
    }
}
