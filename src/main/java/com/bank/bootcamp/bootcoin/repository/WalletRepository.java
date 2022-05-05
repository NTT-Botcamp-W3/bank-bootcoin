package com.bank.bootcamp.bootcoin.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.bank.bootcamp.bootcoin.entity.DocumentType;
import com.bank.bootcamp.bootcoin.entity.Wallet;
import reactor.core.publisher.Mono;

@Repository
public interface WalletRepository extends ReactiveMongoRepository<Wallet, String> {

  Mono<Wallet> findByEmail(String email);
  Mono<Wallet> findByCellphoneNumber(String cellphoneNumber);
  Mono<Wallet> findByDocumentNumberAndDocumentType(String documentNumber, DocumentType documentType);
}
