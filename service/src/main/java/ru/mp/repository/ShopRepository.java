package ru.mp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mp.entity.ShopEntity;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<ShopEntity, Integer>, JpaSpecificationExecutor<ShopEntity> {

    @Query("select p from ShopEntity p where p.name like :name")
    Optional<ShopEntity> findByName(@Param("name")String name);

//    @Query("select p from ShopEntity p where p.name like :name")
    Boolean existsByName(String name);
}