package com.bank.bootcamp.bootcoin.dto;

import com.bank.bootcamp.bootcoin.entity.PaymentMethod;
import lombok.Data;

@Data
public class CreateBuyOrderDTO {
  private String requestWalletId;
  private Double amount;
  private PaymentMethod paymentMethod;
  private String paymentSourceId;
}
