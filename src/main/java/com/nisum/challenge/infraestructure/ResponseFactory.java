package com.nisum.challenge.infraestructure;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.Collection;

public class ResponseFactory {

	public static <T> Response getStatusOk(String message, T responseData){
        Response response = new Response(HttpStatus.OK);
        response.setMessage(message);
        if(responseData != null) {
            response.setData(responseData);
            response.setType(responseData.getClass().getCanonicalName());
            if (responseData instanceof Collection<?> && !((Collection<?>)responseData).isEmpty()) {
                response.setType(response.getType() + "<" + ((Collection<?>)responseData).iterator().next().getClass().getCanonicalName() + ">");
            }
        }
        return response;
    }

    public static Response getStatusException(HttpStatusCode code, String message, @NotNull Class<?> clazz) {
        Response response = new Response(code);
        response.setType(clazz.getCanonicalName());
        response.setMessage(message);
        return response;
    }

}
