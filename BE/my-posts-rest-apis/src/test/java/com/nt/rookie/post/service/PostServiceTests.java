package com.nt.rookie.post.service;

import com.nt.rookie.post.model.dto.PostDTO;
import com.nt.rookie.post.entity.Post;
import com.nt.rookie.post.exception.NotFoundException;
import com.nt.rookie.post.model.request.UpdatePostRequest;
import com.nt.rookie.post.repository.PostRepository;
import com.nt.rookie.post.service.impl.PostServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PostServiceTests {
    @Mock
    private PostRepository postRepository;

    @Captor
    private ArgumentCaptor<Post> captor;

    @InjectMocks
    private PostServiceImpl underTest;


    @Test
    public void testFindPostByIdGivenIdNotExistInDatabaseShouldThrowNotFoundException() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> underTest.getPostById(1));
        assertEquals("No post found", exception.getMessage());
    }

    @Test
    public void testFindPostByIdGivenIdExistInDatabaseShouldReturnDataSuccessfully() {
        Post mockPost = mock(Post.class);
        when(mockPost.getId()).thenReturn(1);
        when(mockPost.getTitle()).thenReturn("Post title");
        when(mockPost.getContent()).thenReturn("Post content");
        Mockito.when(postRepository.findById(Mockito.anyInt())).thenReturn(java.util.Optional.of(mockPost));
        PostDTO post = underTest.getPostById(1);
        assertEquals(mockPost.getTitle(), post.getTitle());
        assertEquals(mockPost.getContent(), post.getContent());
    }


    @Test
    public void testDeletePostGivenIdNotFoundShouldThrowNotFoundException() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> underTest.deletePost(1));
        assertEquals("No post found", exception.getMessage());
        verify(postRepository, never()).delete(any(Post.class));
    }

    @Test
    public void testDeletePostGivenIdExistShouldDeleteSuccessfully() {
        Post mockPost = mock(Post.class);
        when(mockPost.getId()).thenReturn(1);
        when(mockPost.getTitle()).thenReturn("Title mock post");
        when(postRepository.findById(anyInt())).thenReturn(Optional.of((mockPost)));
        underTest.deletePost(1);
        verify(postRepository, times(1)).delete(captor.capture());
        Post deletedPost = captor.getValue();
        assertEquals("Title mock post", deletedPost.getTitle());
        assertEquals(1, deletedPost.getId());
    }


    @Test
    public void testGetAllPostListGivenPostExistShouldReturnDataSuccessfully() {
        Post Post1 = mock(Post.class);
        Post Post2 = mock(Post.class);
        when(Post1.getId()).thenReturn(1);
        when(Post1.getTitle()).thenReturn("The godfather");
        when(Post1.getDescription()).thenReturn("The godfather The godfather The godfather");
        when(Post1.getContent()).thenReturn("The godfather is a post 1");

        when(Post2.getId()).thenReturn(2);
        when(Post2.getTitle()).thenReturn("Alibaba");
        when(Post2.getDescription()).thenReturn("Alibaba Alibaba Alibaba");
        when(Post2.getContent()).thenReturn("The alibaba is a post 2");
        List<Post> ListPosts = new ArrayList<>();
        ListPosts.add(Post1);
        ListPosts.add(Post2);
        when(postRepository.findAll()).thenReturn(ListPosts);


        List<PostDTO> postsDTO= underTest.getAllPost();
        assertEquals(2, postsDTO.size());
        assertEquals("The godfather", postsDTO.get(0).getTitle());
        assertEquals("The godfather The godfather The godfather", postsDTO.get(0).getDescription());
        assertEquals("The godfather is a post 1", postsDTO.get(0).getContent());

        assertEquals("Alibaba", postsDTO.get(1).getTitle());
        assertEquals("Alibaba Alibaba Alibaba", postsDTO.get(1).getDescription());
        assertEquals("The alibaba is a post 2", postsDTO.get(1).getContent());
    }


    @Test
    public void testGetAllPostGivenNullShouldReturnEmpty() {
        when(postRepository.findAll()).thenReturn(new ArrayList<Post>());
        List<PostDTO> emptyListPost = underTest.getAllPost();
        assertEquals(emptyListPost.size(), 0);
        verify(postRepository, times(1)).findAll();
    }


}
