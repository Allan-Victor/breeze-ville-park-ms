package com.breezevillepark_ms.bvp_ms.order;

import com.breezevillepark_ms.bvp_ms.common.BaseEntity;
import com.breezevillepark_ms.bvp_ms.employee.Employee;
import com.breezevillepark_ms.bvp_ms.payment.Payment;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Entity
@Table(name = "_order")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "_order_seq")
    @SequenceGenerator(name = "_order_seq",
            sequenceName = "_order_seq",
            allocationSize = 1)
    private Integer orderId;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id",
    referencedColumnName = "paymentId" )
    private Payment payment;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee submittedBy;
    private BigDecimal totalPrice;
}
