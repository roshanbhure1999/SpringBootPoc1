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


   @Query(value = "select * from posts  where post_title like :key or post_id like :key or post_added_post like :key or" +
           " post_content like :key or post_image_name like :key or post_title like :key or category_category_id like :key" +
           " or user_id like :key ",nativeQuery = true)
   List<Post> findByTitleContent(@Param("key") String title);



}
