package com.order.system.domain.service.ports.input.message.listener.payment;


import com.order.system.domain.service.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {

    void paymentCompleted(PaymentResponse paymentResponse);

    void paymentCancelled(PaymentResponse paymentResponse);
}
