package com.eduvision.version2.corona;

public class UserInformation {

    public String Store;
    public String numero;

    public UserInformation(){
    }

    public UserInformation(String numero, String store){
        this.Store = store;

        this.numero = numero;
    }
    public String getStore() {
        return Store;
    }

    public void setStore(String store) {
        Store = store;
    }

    public String getUserNumero() {
        return numero;
    }
}
