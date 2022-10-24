package com.danyue.reactspringbootblogbackend.repository;

import com.danyue.reactspringbootblogbackend.entity.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogUserRepository extends JpaRepository<BlogUser, Long> {
    BlogUser findByUsername(String username);

    @Query(value = """
            select\s
            \tp.permission
            from
            \tblog_user_role ur
                left join blog_role r on ur.id_role = r.id
                left join blog_role_permission rp on rp.id_role = r.id
                left join blog_permission p on p.id = rp.id_permission
            where
            \tur.id_user = ?1""", nativeQuery = true)
    List<String> findPermissionByUserId(Long userId);
}
