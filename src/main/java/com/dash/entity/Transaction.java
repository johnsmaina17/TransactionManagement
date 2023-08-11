package com.dash.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name ="transaction")


public class Transaction {
    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "amount")
    private BigDecimal amount ;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

}
