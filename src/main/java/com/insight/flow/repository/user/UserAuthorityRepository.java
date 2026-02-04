package com.insight.flow.repository.user;

import com.insight.flow.entity.user.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {

}
