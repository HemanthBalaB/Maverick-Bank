package com.example.maverickbank.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "AccountHolder")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccountHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ✅ Add this
    @Column(name = "accountNo")
    private int accountNo;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "mobileNo")
    private String mobileNo;

    @Column(name = "password")
    private String password;

    @Column(name = "balance")
    private double balance;
}
