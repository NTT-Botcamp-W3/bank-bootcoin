package com.bank.bootcamp.bootcoin.entity;

public enum PaymentMethod {

  YANKI("BuyOrderYanki"), 
  TRANSFER_FROM_SAVING_ACCOUNT("BuyOrderFromSavingAccount"), 
  TRANSFER_FROM_CURRENT_ACCOUNT("BuyOrderFromCurrentAccount"), 
  TRANSFER_FROM_FIXED_ACCOUNT("BuyOrderFromFixedAccount");
  
  private String topic;
  
  private PaymentMethod(String topic) {
    this.topic = topic;
  }

  public String getTopic() {
    return topic;
  }
}
