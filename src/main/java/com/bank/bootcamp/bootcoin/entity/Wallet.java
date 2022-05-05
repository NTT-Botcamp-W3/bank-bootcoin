package com.bank.bootcamp.bootcoin.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Document(collection = "Wallets")
@Data
public class Wallet {

  private String id;
  
  private DocumentType documentType;
  private String documentNumber;
  private String cellphoneNumber;
  private String email;
}
