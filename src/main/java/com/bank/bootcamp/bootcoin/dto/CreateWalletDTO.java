package com.bank.bootcamp.bootcoin.dto;

import com.bank.bootcamp.bootcoin.entity.DocumentType;
import lombok.Data;

@Data
public class CreateWalletDTO {
  private DocumentType documentType;
  private String documentNumber;
  private String cellphoneNumber;
  private String email;
}
