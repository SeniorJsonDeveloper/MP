package ru.mp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "items",schema = "service")
@Entity
public class ItemEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    private String description;

    private String category;

    private Integer quantity;

    private Double rating;

    private String userId;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private DealEntity deal = new DealEntity();

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    private ShopEntity shops = new ShopEntity();

    private String comment;




}
