package com.nt.rookie.post.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.Parameter;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.nt.rookie.post.entity.Post;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/add-post.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/remove-post.sql")
public class PostRepositoryTests {
    @Autowired
    private PostRepository postRepository;

    @Test
    public void testGetAllPosts() {
        List<Post> posts = postRepository.findAll();
        assertEquals(12, posts.size());
    }


    @Test
    public void testSearchByKeywordShouldReturnListRecord() {
        List<Post> posts = postRepository.findByKeywordContains("Content");
        assertEquals(12, posts.size());
    }




    @ParameterizedTest(name = "Test search post by keyword equal {0} should return null")
    @ValueSource(strings = {"absadadf"})
    public void testSearchByKeywordShouldNull(String keyword) {
        List<Post> posts = postRepository.findByKeywordContains(keyword);
        assertEquals(0, posts.size());
    }



    @ParameterizedTest(name = "Test find post by title equal {0} should return 1 record")
    @ValueSource(strings = {"Post 1"})
    public void testFindByTitleLikeShouldReturn1Record(String title) {
       Post post = postRepository.findByTitle(title);
        assertEquals("Post 1",post.getTitle());
        assertEquals("Desc Post 1",post.getDescription());
        assertEquals("Content Post 1",post.getContent());

    }




    @Test
    public void testUpdatePost() {
        Post post =postRepository.findByTitle("Post 1");
        post.setTitle("anaconda");
        post.setContent("awm");
        post.setDescription("M4A1");
        postRepository.save(post);
        assertEquals("anaconda",post.getTitle());

    }

        @Test
        public void testCreatePost() {
            Post post = new Post();
            post.setTitle("mudamuda");
            post.setContent("hehe");
            post.setDescription("datebaiyo");
            postRepository.save(post);
            Assertions.assertNotNull(postRepository.findByTitle("mudamuda"));

        }

}