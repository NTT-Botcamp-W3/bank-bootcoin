package com.bank.bootcamp.bootcoin.entity;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Document(collection = "BuyOrders")
@Data
public class BuyOrder {

  @Id
  private String id;
  private String requestWalletId;
  private Double amount;
  private PaymentMethod paymentMethod;
  private String paymentSourceId;
  private BuyOrderStatus status;
  private LocalDateTime sendDate;
  private LocalDateTime proccesedDate;
}
