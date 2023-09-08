package com.dessertoasis.demo.model.favorite;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteListRepository extends JpaRepository<FavoriteList,FavoriteKey > {

}
