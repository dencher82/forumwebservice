package telran.ashkelon2020.service;

import java.util.List;

import telran.ashkelon2020.dto.MessageDto;
import telran.ashkelon2020.dto.PostDto;
import telran.ashkelon2020.dto.PostResponseDto;

public interface ForumService {
	
	PostResponseDto addPost(String author, PostDto postDto);
	
	PostResponseDto findPostById(String id);
	
	PostResponseDto deletePost(String id);
	
	PostResponseDto updatePost(String id, PostDto postDto);
	
	boolean addLikeToPost(String id);
	
	PostResponseDto addCommentToPost(String id, String user, MessageDto messageDto);
	
	List<PostResponseDto> findPostsByAuthor(String author);
	
}
