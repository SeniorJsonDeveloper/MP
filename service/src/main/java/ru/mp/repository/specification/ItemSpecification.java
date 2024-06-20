package ru.mp.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.mp.dto.item.ItemFilterDto;
import ru.mp.entity.ItemEntity;

import java.math.BigDecimal;

public interface ItemSpecification {

    static Specification<ItemEntity> withSorting(ItemFilterDto itemFilterDto){
        return Specification.where(byPriceRange(itemFilterDto.getMinPrice(),itemFilterDto.getMaxPrice()))
                .and(byCategory(itemFilterDto.getCategory()));

    }

    static Specification<ItemEntity> byCategory(String category){
        return (root, query, criteriaBuilder) -> {
            if (category==null){
                return null;
            }
            return criteriaBuilder.equal(root.get("category"), category);
        };
    }

    static Specification<ItemEntity> byPriceRange(BigDecimal minPrice, BigDecimal maxPrice){
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) {
                return null;
            }
            if (minPrice == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), maxPrice);
            }
            if (maxPrice == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), minPrice);
            }
            return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
        };
    }
}
