package com.breezevillepark_ms.bvp_ms.payment;

import com.breezevillepark_ms.bvp_ms.common.BaseEntity;
import com.breezevillepark_ms.bvp_ms.employee.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "payment_seq")
    @SequenceGenerator(name = "payment_seq",
            sequenceName = "payment_seq",
            allocationSize = 1)
    private Integer paymentId;
    private BigDecimal amount;



    private BigDecimal amountPayable;
    private BigDecimal amountReceived;
    private BigDecimal serviceAmount;
    private BigDecimal taxAmount;

    private BigDecimal amountInChange;

    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private CardType cardtype;
    private String cardNumber;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
