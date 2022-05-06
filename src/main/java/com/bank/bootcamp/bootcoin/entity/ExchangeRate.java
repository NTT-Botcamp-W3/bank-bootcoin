package com.bank.bootcamp.bootcoin.entity;

import java.io.Serializable;
import org.springframework.data.redis.core.RedisHash;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("ExchangeRate")
public class ExchangeRate implements Serializable {

  private static final long serialVersionUID = -225453776543968884L;
  private Double buy; // compra
  private Double sell; // venta
}
