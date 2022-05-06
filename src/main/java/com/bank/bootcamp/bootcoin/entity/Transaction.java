package com.bank.bootcamp.bootcoin.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Transactions")
public class Transaction {

  @Id
  private String id;
  
  private String walletId;
//  private Double 
}
