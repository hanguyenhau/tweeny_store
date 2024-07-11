package com.tweeny_store.tweeny_store.model.email;

public enum EmailTemplate {
    ACTIVATE_ACCOUNT("activate_account");
    private final String name;

    EmailTemplate(String name){
        this.name = name;
    }
}
