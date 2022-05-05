package com.bank.bootcamp.bootcoin.entity;

import org.springframework.data.redis.core.RedisHash;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RedisHash
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRate {

  private Double buy; // compra
  private Double sell; // venta
}
