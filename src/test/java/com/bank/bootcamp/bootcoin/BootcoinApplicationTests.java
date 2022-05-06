package com.bank.bootcamp.bootcoin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.kafka.core.KafkaTemplate;
import com.bank.bootcamp.bootcoin.dto.CreateBuyOrderDTO;
import com.bank.bootcamp.bootcoin.dto.CreateWalletDTO;
import com.bank.bootcamp.bootcoin.entity.BuyOrder;
import com.bank.bootcamp.bootcoin.entity.BuyOrderStatus;
import com.bank.bootcamp.bootcoin.entity.DocumentType;
import com.bank.bootcamp.bootcoin.entity.ExchangeRate;
import com.bank.bootcamp.bootcoin.entity.PaymentMethod;
import com.bank.bootcamp.bootcoin.entity.Wallet;
import com.bank.bootcamp.bootcoin.repository.BuyOrderRepository;
import com.bank.bootcamp.bootcoin.repository.WalletRepository;
import com.bank.bootcamp.bootcoin.service.BootcoinService;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class BootcoinApplicationTests {

//  private static BootcoinService bootcoinService;
//  private static WalletRepository walletRepository;
//  private static BuyOrderRepository buyOrderRepository;
//  private static KafkaTemplate<String, BuyOrder> buyOrderKafkaTemplate;
//  
//  private static RedisTemplate<String, ExchangeRate> exchangeRateRedisTemplate;
//  private static ValueOperations<String, ExchangeRate> valueOperations;
//  private ModelMapper mapper = new ModelMapper();
//
//  @BeforeAll
//  public static void setup() {
//    walletRepository = mock(WalletRepository.class);
//    exchangeRateRedisTemplate = mock(RedisTemplate.class);
//    valueOperations = mock(ValueOperations.class);
//    buyOrderRepository = mock(BuyOrderRepository.class);
//    buyOrderKafkaTemplate = mock(KafkaTemplate.class);
////    bootcoinService = new BootcoinService(walletRepository, buyOrderRepository, buyOrderKafkaTemplate);
//    
//    when(exchangeRateRedisTemplate.opsForValue()).thenReturn(valueOperations);
//    when(valueOperations.get("exchangeRate")).thenReturn(new ExchangeRate(4.0, 4.1));
//  }
//
//  @Test
//  public void createWallet() {
//    var wallet = new Wallet();
//    wallet.setCellphoneNumber("941457152");
//    wallet.setDocumentNumber("70682463");
//    wallet.setDocumentType(DocumentType.DNI);
//    wallet.setEmail("bjlacruz27@gmail.com");
//    
//    var dto = mapper.map(wallet, CreateWalletDTO.class);
//    var savedWallet = mapper.map(wallet, Wallet.class);
//    savedWallet.setId(UUID.randomUUID().toString());
//    
//    when(walletRepository.findByCellphoneNumber(wallet.getCellphoneNumber())).thenReturn(Mono.empty());
//    when(walletRepository.findByDocumentNumberAndDocumentType(wallet.getDocumentNumber(), wallet.getDocumentType())).thenReturn(Mono.empty());
//    when(walletRepository.findByEmail(wallet.getEmail())).thenReturn(Mono.empty());
//    when(walletRepository.save(Mockito.any(Wallet.class))).thenReturn(Mono.just(savedWallet));
//    
//    var mono = bootcoinService.createWallet(dto);
//    
//    StepVerifier.create(mono)
//      .assertNext(saved -> {
//        assertThat(saved.getId()).isNotNull();
//      })
//      .verifyComplete();
//  }
//  
//  @Test
//  public void updateExchangeRate() {
//    var er = bootcoinService.updateExchangeRate(4.0, 4.2);
//    assertThat(er).isNotNull();
//    assertThat(er.getBuy()).isEqualTo(4.0);
//  }
//  
//  @Test
//  public void putBuyOrder() {
//    var dto = new CreateBuyOrderDTO();
//    dto.setAmount(10d);
//    dto.setPaymentMethod(PaymentMethod.TRANSFER);
//    dto.setPaymentSourceId("saving-account-id");
//    dto.setRequestWalletId("wallet-id");
//    var savedBuyOrder = mapper.map(dto, BuyOrder.class);
//    savedBuyOrder.setSendDate(LocalDateTime.now());
//    savedBuyOrder.setStatus(BuyOrderStatus.PENDING);
//    
//    when(buyOrderRepository.save(Mockito.any(BuyOrder.class))).thenReturn(Mono.just(savedBuyOrder));
//    
//    var mono = bootcoinService.createBuyOrder(dto);
//    StepVerifier.create(mono)
//      .assertNext(boolValue -> {
//        assertThat(boolValue).isTrue();
//      })
//      .verifyComplete();
//  }
  
}
