package com.nt.rookie.post.model.mapper;


import com.nt.rookie.post.entity.Post;
import com.nt.rookie.post.model.dto.PostDTO;
import com.nt.rookie.post.model.request.CreatePostRequest;
import com.nt.rookie.post.model.request.UpdatePostRequest;


public class PostMapper {
    public static PostDTO toPostDTO(Post post) {
        PostDTO tmp = new PostDTO();
        tmp.setId(post.getId());
        tmp.setTitle(post.getTitle());
        tmp.setDescription(post.getDescription());
        tmp.setContent(post.getContent());

        tmp.setCreatedDate(post.getCreatedDate());
        return tmp;
    }
        public static Post toPost(CreatePostRequest request) {
            Post post = new Post();
            post.setTitle(request.getTitle());
            post.setDescription(request.getDescription());
            post.setContent(request.getContent());
            return post;
        }

        public static Post toPost(UpdatePostRequest request, int id) {
            Post post = new Post();
            post.setId(id);
            post.setTitle(request.getTitle());
            post.setDescription(request.getDescription());
            post.setContent(request.getContent());

            return post;

    }
}
