package com.budzikovy.medical_clinic_proxy.config;

import com.budzikovy.medical_clinic_proxy.exception.ResourceNotFoundException;
import feign.FeignException;
import feign.RetryableException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new ErrorDecoder.Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 404:
                return new ResourceNotFoundException();
            case 500:
                FeignException exception = FeignException.errorStatus(methodKey, response);
                return new RetryableException(response.status(), exception.getMessage(), response.request().httpMethod(),
                        exception, 1000L, response.request());
            default:
                return errorDecoder.decode(methodKey, response);
        }
    }
}
