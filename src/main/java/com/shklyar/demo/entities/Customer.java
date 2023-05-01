package com.shklyar.demo.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime update;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @NonNull
    @Size(max = 20)
    private String email;
    @NonNull
    @Size(max = 20)
    private String firstName;
    @NonNull
    @Size(max = 20)
    private String lastName;

    private BigDecimal sum;
    private String address;
    @NonNull
    @Size(max = 20)
    private String postOffice;
    @NonNull
    @Size(max = 20)
    private String numberPhone;
    @NonNull
    @Size(max = 20)
    private String departmentPostOffice;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<OrderedProduct> orders;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    public Customer() {

    }
}
