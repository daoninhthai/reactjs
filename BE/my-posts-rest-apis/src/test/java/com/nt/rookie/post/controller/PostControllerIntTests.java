package com.nt.rookie.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nt.rookie.post.config.JwtTokenUtil;
import com.nt.rookie.post.exception.NotFoundException;
import com.nt.rookie.post.model.dto.PostDTO;
import com.nt.rookie.post.model.jwt.JwtRequest;
import com.nt.rookie.post.model.request.CreatePostRequest;
import com.nt.rookie.post.model.request.UpdatePostRequest;
import com.nt.rookie.post.service.JwtUserDetailsService;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ComponentScan(value = {"com.nt.rookie.post.service", "com.nt.rookie.post.repository", "com.nt.rookie.post.config"})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/add-post.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/remove-post.sql")
@SpringBootTest
public class PostControllerIntTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void getAllPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/getall")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(12)))
                .andExpect(jsonPath("$[*].id").isNotEmpty());
    }



    @ParameterizedTest(name = "Test get post with id {0} found")
    @ValueSource(ints = {3})
    public void getByIdFound(int id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/id/" + id)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("Post " + id));
    }

    @ParameterizedTest(name = "Test search post with keyword {0} found list record")
    @ValueSource(strings = {"Content"})
    public void searchByKeywordContainFoundList(String keyword) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/search?keyword="+keyword)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(12)));
    }
    @ParameterizedTest(name = "Test search post with keyword {0} not found record")
    @ValueSource(strings = {"12345"})
    public void searchByKeywordContainNotFound(String search) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/search?keyword="+search)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(0)));
    }
    @Test
    public void updatePostWithAdminAccount() throws Exception {
        UpdatePostRequest updatePostRequest = new UpdatePostRequest();
        updatePostRequest.setTitle("title updated");
        updatePostRequest.setDescription("description updated");
        updatePostRequest.setContent("content updated");
        JwtRequest request = new JwtRequest();
        request.setUsername("admin");
        request.setPassword("admin@123");
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/author/update/2")
                .header("Authorization", "Bearer " + token)
                .content(asJsonString(updatePostRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    @Test
    public void updatePostWithAuthorAccount() throws Exception {
        UpdatePostRequest updatePostRequest = new UpdatePostRequest();
        updatePostRequest.setTitle("title updated");
        updatePostRequest.setDescription("description updated");
        updatePostRequest.setContent("content updated");
        JwtRequest request = new JwtRequest();
        request.setUsername("author");
        request.setPassword("author@123");
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/author/update/1")
                .header("Authorization", "Bearer " + token)
                .content(asJsonString(updatePostRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title updated"))
                .andExpect(jsonPath("$.description").value("description updated"))
                .andExpect(jsonPath("$.content").value("content updated"));
    }

    @Test
    public void deletePostWithAdminAccount() throws Exception {
        JwtRequest request = new JwtRequest();
        request.setUsername("admin");
        request.setPassword("admin@123");
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/author/delete/1")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isForbidden());

    }

    @Test
    public void deletePostWithAuthorAccount() throws Exception {
        JwtRequest request = new JwtRequest();
        request.setUsername("author");
        request.setPassword("author@123");
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/author/delete/1")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void visitAdminStatisticWithAdminAccount() throws Exception {
        JwtRequest request = new JwtRequest();
        request.setUsername("admin");
        request.setPassword("admin@123");
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/admin/post/statistic")
                .header("Authorization", "Bearer " + token)
                .param("author", "admin")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void visitAdminStatisticWithAuthorAccount() throws Exception {
        JwtRequest request = new JwtRequest();
        request.setUsername("author");
        request.setPassword("admin@123");
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/admin/post/statistic")
                .header("Authorization", "Bearer " + token)
                .param("author", "author")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isForbidden());
    }



    @Test
    public void createPostWithAuthorAccount() throws Exception {
        UpdatePostRequest updatePostRequest = new UpdatePostRequest();
        updatePostRequest.setTitle("title created");
        updatePostRequest.setDescription("description created");
        updatePostRequest.setContent("content created");
        JwtRequest request = new JwtRequest();
        request.setUsername("author");
        request.setPassword("author@123");
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/author/create")
                .header("Authorization", "Bearer " + token)
                .content(asJsonString(updatePostRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title created"))
                .andExpect(jsonPath("$.description").value("description created"))
                .andExpect(jsonPath("$.content").value("content created"));
    }

    @Test
    public void createPostWithAdminAccount() throws Exception {
        UpdatePostRequest updatePostRequest = new UpdatePostRequest();
        updatePostRequest.setTitle("title created");
        updatePostRequest.setDescription("description created");
        updatePostRequest.setContent("content created");
        JwtRequest request = new JwtRequest();
        request.setUsername("admin");
        request.setPassword("admin@123");
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/author/create")
                .header("Authorization", "Bearer " + token)
                .content(asJsonString(updatePostRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());

    }
}