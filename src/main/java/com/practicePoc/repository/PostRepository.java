package com.practicePoc.repository;

import com.practicePoc.entity.Category;
import com.practicePoc.entity.Post;
import com.practicePoc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUser(User user);

    List<Post> findByCategory(Category category);


       @Query(value = "select * from posts  where post_title like :key",nativeQuery = true)
      List<Post> searchByTitle(@Param("key") String title);

   // List<Post> findByTitleContent(String title);


}
