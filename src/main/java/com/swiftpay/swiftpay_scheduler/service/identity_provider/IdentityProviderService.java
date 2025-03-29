package com.swiftpay.swiftpay_scheduler.service.identity_provider;

public interface IdentityProviderService<T, E> {
    T register(E write);
}