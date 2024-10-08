package com.neo.exp.Rest;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.neo.exp.config.CloudinaryConfig;
import com.neo.exp.config.UserHolder;
import com.neo.exp.dto.*;
import com.neo.exp.entities.Comment;
import com.neo.exp.entities.FileData;
import com.neo.exp.entities.FileUploadForm;
import com.neo.exp.entities.Posts.AppreciationPost;
import com.neo.exp.entities.Posts.CelebrationPost;
import com.neo.exp.entities.Posts.Post;
import com.neo.exp.entities.Posts.SimplePost;
import com.neo.exp.repositories.FileDataRepository;
import com.neo.exp.services.MediaService;
import com.neo.exp.services.PostService;
import com.neo.exp.dto.CommentDTO;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostResource {

    @Inject
    PostService postService;

    @Inject
    MediaService mediaService;

    @Inject
    UserHolder userHolder;

    @Inject
    FileDataRepository fileDataRepository;
    @Inject
    CloudinaryConfig cloudinaryConfig;
    //************ Simple Post ****************************
    @POST
    @Path("/simple")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed("employe")
    public Response createSimplePost(MultipartFormDataInput input) {
        String userID = userHolder.getUserId();

        if (userID == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Username is required").build();
        }

        try {
            // Delegate processing to the PostService
            SimplePost createdPost = postService.createSimplePost(input, userHolder.getName(), userHolder.getUserId());
            return Response.status(Response.Status.CREATED).entity(createdPost).build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    @PUT
    @Path("/simple/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed("employe")
    @Transactional
    public Response updateSimplePost(@PathParam("id") Long id, @MultipartForm MultipartFormDataInput input) {
        try {

            boolean success = postService.updateSimplePost(id, input);
            if (success) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Post not found").build();
            }
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    @GET
    @Path("/getSimple")
    @RolesAllowed("employe")
    public Response getSimplePosts() {
        List<SimplePostDTO> simplePostDTOs = postService.getSimplePosts();
        return Response.ok(simplePostDTOs).build();
    }

    
    /*
    @GET
    @Path("/getSimple")
    @RolesAllowed("employe")
    public Response getSimplePosts() {
        List<SimplePost> simplePosts = postService.getSimplePosts();
        return Response.ok(simplePosts).build();
    }*/

    //************ Appreciation Post ****************************
    @POST
    @Path("/appreciation")
    @RolesAllowed("employe")
    public Response createAppreciationPost(AppreciationPostDTO dto) {
        AppreciationPost createdPost = postService.createAppreciationPost(dto);
        return Response.status(Response.Status.CREATED).entity(createdPost).build();
    }
    @PUT
    @Path("/appreciation/{id}")
    @RolesAllowed("employe")
    @Transactional
    public Response updateAppreciationPost(@PathParam("id") Long id, AppreciationPostDTO dto) {
        boolean success = postService.updateAppreciationPost(id, dto);
        if (success) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Post not found").build();
        }
    }
    @GET
    @Path("/appreciation")
    @RolesAllowed("employe")
    public Response getAppreciationPosts() {
        List<AppreciationPost> appreciationPosts = postService.getAppreciationPosts();
        return Response.ok(appreciationPosts).build();
    }


    //************ Celebration Post ****************************
    @POST
    @Path("/celebration")
    @RolesAllowed("employe")
    public Response createCelebrationPost(CelebrationPostDTO dto) {
        CelebrationPost createdPost = postService.createCelebrationPost(dto);
        return Response.status(Response.Status.CREATED).entity(createdPost).build();
    }
    @PUT
    @Path("/celebration/{id}")
    @RolesAllowed("employe")
    @Transactional
    public Response updateCelebrationPost(@PathParam("id") Long id, CelebrationPostDTO dto) {
        boolean success = postService.updateCelebrationPost(id, dto);
        if (success) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Post not found").build();
        }
    }
    @GET
    @Path("/celebration")
    @RolesAllowed("employe")
    public Response getCelebrationPosts() {
        List<CelebrationPost> celebrationPosts = postService.getCelebrationPosts();
        return Response.ok(celebrationPosts).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("employe")
    @Transactional
    public Response deletePost(@PathParam("id") Long id) {
        boolean success = postService.deletePost(id);
        if (success) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Post not found")
                    .build();
        }
    }


    @GET
    @Path("/all")
    @RolesAllowed("employe")
    public Response getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        List<PostDTO> postDTOs = posts.stream()
                .map(post -> PostMapper.toPostDTO(post)) // Implement this mapping
                .collect(Collectors.toList());
        return Response.ok(postDTOs).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("employe")
    public Response getPostById(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID is required").build();
        }
        Post post = postService.getPostById(id);
        if (post == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(post).build();
    }


    //************* likes **************************
    @POST
    @Path("/{id}/like")
    @RolesAllowed("employe")
    @Transactional
    public Response likePost(@PathParam("id") Long id) {
        boolean success = postService.addLike(id);
        if (success) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Post not found")
                    .build();
        }
    }

    @DELETE
    @Path("/{id}/like")
    @RolesAllowed("employe")
    @Transactional
    public Response unlikePost(@PathParam("id") Long id) {
        boolean success = postService.removeLike(id);
        if (success) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Post not found")
                    .build();
        }
    }


    @GET
    @Path("/{id}/likes/count")
    @RolesAllowed("employe")
    public Response getLikeCount(@PathParam("id") Long id) {
        Integer likeCount = postService.getLikeCount(id);
        if (likeCount == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Post not found").build();
        }
        return Response.ok(likeCount).build();
    }
    //************* Comments **************************
    @POST
    @Path("/{id}/comment")
    @RolesAllowed("employe")
    @Transactional
    public Response addComment(@PathParam("id") Long id, CommentDTO dto) {
        postService.addComment(id, dto.getCommentText());
        return Response.ok().build();
    }

    @DELETE
    @Path("/{postId}/comment/{commentId}")
    @RolesAllowed("employe")
    @Transactional
    public Response deleteComment(@PathParam("postId") Long postId, @PathParam("commentId") Long commentId) {
        boolean success = postService.deleteComment(postId, commentId);
        if (success) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Comment or Post not found").build();
        }
    }

    @PUT
    @Path("/{postId}/comment/{commentId}")
    @RolesAllowed("employe")
    @Transactional
    public Response updateComment(@PathParam("postId") Long postId, @PathParam("commentId") Long commentId, String newText) {
        boolean success = postService.updateComment(postId, commentId, newText);
        if (success) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Comment or Post not found").build();
        }
    }
    @GET
    @Path("/{postId}/comments")
    @RolesAllowed("employe") // Adjust the role as necessary
    public Response getCommentsByPostId(@PathParam("postId") Long postId) {
        List<CommentDTO> comments = postService.getCommentsByPostId(postId);
        return Response.ok(comments).build();
    }

    @GET
    @Path("/{postId}/comments/count")
    @RolesAllowed("employe")
    public Response getNumberOfComments(@PathParam("postId") Long postId) {
        try {
            int count = postService.getNumberOfComments(postId);
            return Response.ok(count).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }





}
