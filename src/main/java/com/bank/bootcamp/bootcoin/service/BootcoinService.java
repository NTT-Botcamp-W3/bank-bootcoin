package com.bank.bootcamp.bootcoin.service;

import java.time.LocalDateTime;
import java.util.function.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.bank.bootcamp.bootcoin.dto.CreateBuyOrderDTO;
import com.bank.bootcamp.bootcoin.dto.CreateWalletDTO;
import com.bank.bootcamp.bootcoin.entity.BuyOrder;
import com.bank.bootcamp.bootcoin.entity.BuyOrderStatus;
import com.bank.bootcamp.bootcoin.entity.ExchangeRate;
import com.bank.bootcamp.bootcoin.entity.Wallet;
import com.bank.bootcamp.bootcoin.exception.BankValidationException;
import com.bank.bootcamp.bootcoin.repository.BuyOrderRepository;
import com.bank.bootcamp.bootcoin.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BootcoinService {

  private ModelMapper mapper = new ModelMapper();
  private final RedisTemplate<String, Object> redisTemplate;
  private final WalletRepository walletRepository;
  private final BuyOrderRepository buyOrderRepository;
  private final KafkaTemplate<String, BuyOrder> buyOrderKafkaTemplate;
  
  
  public ExchangeRate updateExchangeRate(Double buy, Double sell) {
    var exchangeRate = new ExchangeRate(buy, sell);
    redisTemplate.opsForValue().set("exchangeRate", exchangeRate);
    return exchangeRate;
  }
  
  public ExchangeRate getExchangeRate() {
    return (ExchangeRate) redisTemplate.opsForValue().get("exchangeRate");
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
        .<CreateWalletDTO>handle((register, sink) -> sink.error(new BankValidationException("Cellphone number already exists")))
        .switchIfEmpty(Mono.just(createWalletDTO))
    )
    .then(
        walletRepository.findByEmail(createWalletDTO.getEmail())
        .<CreateWalletDTO>handle((register, sink) -> sink.error(new BankValidationException("Email already exists")))
        .switchIfEmpty(Mono.just(createWalletDTO))
    )
    .then(
        walletRepository.findByDocumentNumberAndDocumentType(createWalletDTO.getDocumentNumber(), createWalletDTO.getDocumentType())
        .<CreateWalletDTO>handle((register, sink) -> sink.error(new BankValidationException("Document number / type already exists ")))
        .switchIfEmpty(Mono.just(createWalletDTO))
     )
    .flatMap(dto -> {
      var wallet = mapper.map(createWalletDTO, Wallet.class);
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

  public Mono<Boolean> createBuyOrder(CreateBuyOrderDTO createBuyOrderDTO) {
    return Mono.just(createBuyOrderDTO)
    .then(check(createBuyOrderDTO, dto -> ObjectUtils.isEmpty(dto), "No data for creation"))
    .then(check(createBuyOrderDTO, dto -> ObjectUtils.isEmpty(dto.getAmount()), "Amount is required"))
    .then(check(createBuyOrderDTO, dto -> ObjectUtils.isEmpty(dto.getPaymentMethod()), "Payment method is required"))
    .then(check(createBuyOrderDTO, dto -> ObjectUtils.isEmpty(dto.getPaymentSourceId()), "SourceID is required"))
    .then(check(createBuyOrderDTO, dto -> ObjectUtils.isEmpty(dto.getRequestWalletId()), "Request wallet is required"))
    .flatMap(dto -> {
      var buyOrder = new BuyOrder();
      buyOrder.setAmount(dto.getAmount());
      buyOrder.setPaymentMethod(dto.getPaymentMethod());
      buyOrder.setPaymentSourceId(dto.getPaymentSourceId());
      buyOrder.setRequestWalletId(dto.getRequestWalletId());
      buyOrder.setSendDate(LocalDateTime.now());
      buyOrder.setStatus(BuyOrderStatus.PENDING);
      return buyOrderRepository.save(buyOrder).map(savedBuyOrder -> {
        buyOrderKafkaTemplate.send(buyOrder.getPaymentMethod().getTopic(), buyOrder);
        return Boolean.TRUE;
      });
    });
  }
}
