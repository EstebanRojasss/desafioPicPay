package com.desafiopicpay.domain.transactions;


public class AuthenticateTransaction {

    private String status;
    private Data data;

    public String getStatus(){
        return status;
    }

    public Data getData(){
        return data;
    }

    public static class Data{
        private boolean authorization;

        public boolean isAuthorization() {
            return authorization;
        }
    }

}



