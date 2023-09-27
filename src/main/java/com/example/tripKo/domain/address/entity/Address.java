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
    AddressCategory addressCategory;

    /*
    FK를 place에 두되 Address에서도 Place를 가져올 수 있게 하였습니다.
    Address의 place는 읽기만 가능합니다.
    Place 생성 후 주석 처리 삭제하면 됩니다.
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "address")
    Place place;
     */

    @Builder
    public Address(String buildingName, String roadName, String zipCode, AddressCategory addressCategory) {
        this.buildingName = buildingName;
        this.roadName = roadName;
        this.zipCode = zipCode;
        this.addressCategory = addressCategory;

        addressCategory.getAddressList().add(this);
    }
}
