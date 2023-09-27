package com.example.tripKo.domain.address.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class AddressCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    private String emdName;

    private String siggName;

    private String sidoName;

    @OneToMany(mappedBy = "addressCategory")
    private List<Address> addressList = new ArrayList<>();

    @Builder
    public AddressCategory(String emdName, String siggName, String sidoName, List<Address> addressList) {
        this.emdName = emdName;
        this.siggName = siggName;
        this.sidoName = sidoName;
        this.addressList = addressList;
    }
}
