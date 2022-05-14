package com.example.security.repository;

import com.example.security.models.AppUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IUserRepository extends PagingAndSortingRepository<AppUser, Long> {
    @Query("select u from AppUser u where u.email like %?1%")
    AppUser findByEmail(String email);
}
