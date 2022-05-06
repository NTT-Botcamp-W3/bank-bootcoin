package com.bank.bootcamp.bootcoin.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.bank.bootcamp.bootcoin.entity.BuyOrder;

public interface BuyOrderRepository extends ReactiveMongoRepository<BuyOrder, String> {

}
