package com.example.tripKo.domain.address.entity;


import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Entity
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @Column(nullable = false)
    private String buildingName;

    @Column(nullable = false)
    private String roadName;

    @Column(nullable = false)
    private String zipCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_category_id", nullable = false)
    private AddressCategory addressCategory;

    @Builder
    public Address(String buildingName, String roadName, String zipCode) {
        this.buildingName = buildingName;
        this.roadName = roadName;
        this.zipCode = zipCode;
    }
}
