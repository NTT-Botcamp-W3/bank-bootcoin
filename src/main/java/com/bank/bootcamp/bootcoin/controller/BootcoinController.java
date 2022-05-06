package com.bank.bootcamp.bootcoin.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bank.bootcamp.bootcoin.dto.CreateBuyOrderDTO;
import com.bank.bootcamp.bootcoin.dto.CreateWalletDTO;
import com.bank.bootcamp.bootcoin.entity.ExchangeRate;
import com.bank.bootcamp.bootcoin.entity.Wallet;
import com.bank.bootcamp.bootcoin.service.BootcoinService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("bootcoin")
@RequiredArgsConstructor
public class BootcoinController {
  
  private final BootcoinService bootcoinService;
  
  @PostMapping
  public Mono<Wallet> createWallet(@RequestBody CreateWalletDTO dto) {
    return bootcoinService.createWallet(dto);
  }
  
  @PostMapping("/buyOrder")
  public Mono<Boolean> createBuyOrder(@RequestBody CreateBuyOrderDTO dto) {
    return bootcoinService.createBuyOrder(dto);
  }
  
  @PostMapping("/updateExchangeRate")
  public Mono<ExchangeRate> updateExchangeRate(@RequestBody ExchangeRate exchangeRate) {
    return Mono.just(bootcoinService.updateExchangeRate(exchangeRate.getBuy(), exchangeRate.getSell()));
  }

}
