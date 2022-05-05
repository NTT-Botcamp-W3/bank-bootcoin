package com.bank.bootcamp.bootcoin.service;

import java.util.function.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.bank.bootcamp.bootcoin.dto.CreateWalletDTO;
import com.bank.bootcamp.bootcoin.entity.ExchangeRate;
import com.bank.bootcamp.bootcoin.entity.Wallet;
import com.bank.bootcamp.bootcoin.exception.BankValidationException;
import com.bank.bootcamp.bootcoin.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BootcoinService {

  private ModelMapper modelMapper = new ModelMapper();
  private final RedisTemplate<String, ExchangeRate> exchangeRateRedisTemplate;
  private final WalletRepository walletRepository;
  
  public ExchangeRate updateExchangeRate(Double buy, Double sell) {
    var exchangeRate = new ExchangeRate(buy, sell);
    exchangeRateRedisTemplate.opsForValue().set("exchangeRate", exchangeRate);
    return exchangeRate;
  }
  
  public ExchangeRate getExchangeRate() {
    return exchangeRateRedisTemplate.opsForValue().get("exchangeRate");
  }
  
  public Mono<Wallet> createWallet(CreateWalletDTO createWalletDTO) {
    return Mono.just(createWalletDTO)
    .then(check(createWalletDTO, dto -> ObjectUtils.isEmpty(dto), "No data for creation"))
    .then(check(createWalletDTO, dto -> ObjectUtils.isEmpty(dto.getCellphoneNumber()), "Cellphone number is required"))
    .then(check(createWalletDTO, dto -> ObjectUtils.isEmpty(dto.getDocumentNumber()), "Document number is required"))
    .then(check(createWalletDTO, dto -> ObjectUtils.isEmpty(dto.getDocumentType()), "Document type is required"))
    .then(check(createWalletDTO, dto -> ObjectUtils.isEmpty(dto.getEmail()), "Email is required"))
    .then(
        walletRepository.findByCellphoneNumber(createWalletDTO.getCellphoneNumber())
        .then(Mono.error(new BankValidationException("Cellphone number already exists")))
        .defaultIfEmpty(createWalletDTO)
    )
    .then(
        walletRepository.findByEmail(createWalletDTO.getEmail())
        .then(Mono.error(new BankValidationException("Email already exists")))
        .defaultIfEmpty(createWalletDTO)
    )
    .then(
        walletRepository.findByDocumentNumberAndDocumentType(createWalletDTO.getDocumentNumber(), createWalletDTO.getDocumentType())
        .then(Mono.error(new BankValidationException("Document number / type already exists ")))
        .defaultIfEmpty(createWalletDTO)
     )
    .flatMap(dto -> {
      var wallet = modelMapper.map(createWalletDTO, Wallet.class);
      return walletRepository.save(wallet);
    })
    ;
  }
  
  private <T> Mono<T> check(T t, Predicate<T> predicate, String messageForException) {
    return Mono.create(sink -> {
      if (predicate.test(t)) {
        sink.error(new BankValidationException(messageForException));
        return;
      } else {
        sink.success(t);
      }
    });
  }
}
