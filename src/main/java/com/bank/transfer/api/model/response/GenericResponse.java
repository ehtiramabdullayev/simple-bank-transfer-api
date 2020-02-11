package com.bank.transfer.api.model.response;

/**
 * @author Ehtiram_Abdullayev on 2/11/2020
 * @project bank-transfer
 */
public class GenericResponse<T> {
    private final T t;
    private final Response response;

    public GenericResponse(T t, Response response) {
        this.t = t;
        this.response = response;
    }

    public GenericResponse(T t) {
        this.t = t;
        this.response = new Response(200,"SUCCESS");
    }

    public GenericResponse(Response response) {
        this.t = null;
        this.response = response;
    }

    public T getT() {
        return t;
    }

    public Response getResponse() {
        return response;
    }
}
