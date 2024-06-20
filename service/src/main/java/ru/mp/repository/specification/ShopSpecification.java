package ru.mp.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.mp.dto.shop.ShopFilterDto;
import ru.mp.entity.ShopEntity;

public interface ShopSpecification {

    static Specification<ShopEntity> withSorting(ShopFilterDto shopFilterDto){
        return Specification.where(byRating(shopFilterDto.getMinRating(), shopFilterDto.getMaxRating()));
    }

    static Specification<ShopEntity> byRating(Double minRating, Double maxRating) {
        return (root, query, criteriaBuilder) -> {
            if (minRating == null && maxRating == null) {
                return null;
            }
            if (minRating == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), maxRating);
            }
            if (maxRating == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("rating"), minRating);
            }
            return criteriaBuilder.between(root.get("rating"), minRating, maxRating);
        };


    }
    }

