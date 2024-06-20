package ru.mp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "shops",schema = "service")
@Entity
public class ShopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private Double rating;

    @Column(nullable = false)
    private Double deposit;

    @JsonProperty("yyyy-MM-dd")
    private LocalDateTime createdAt;




    private Long numberOfPurchases;

    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private List<DealEntity> deals = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private List<ItemEntity> items = new ArrayList<>();


}
