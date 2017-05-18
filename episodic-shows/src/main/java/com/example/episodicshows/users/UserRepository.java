package com.example.episodicshows.users;

import com.example.episodicshows.users.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
