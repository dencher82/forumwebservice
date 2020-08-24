package telran.ashkelon2020.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import telran.ashkelon2020.dao.ForumRepositoryMongoDB;
import telran.ashkelon2020.dto.MessageDto;
import telran.ashkelon2020.dto.PostDto;
import telran.ashkelon2020.dto.PostResponseDto;
import telran.ashkelon2020.dto.exception.PostNotFoundException;
import telran.ashkelon2020.model.Comment;
import telran.ashkelon2020.model.Post;

@Component
public class ForumServiceImpl implements ForumService {
	
	@Autowired
	ForumRepositoryMongoDB forumRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public PostResponseDto addPost(String author, PostDto postDto) {
		Post post = modelMapper.map(postDto, Post.class);
		post.setAuthor(author);
		Post res = forumRepository.save(post);
		return modelMapper.map(res, PostResponseDto.class);
	}

	@Override
	public PostResponseDto findPostById(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		return modelMapper.map(post, PostResponseDto.class);
	}

	@Override
	public PostResponseDto deletePost(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		forumRepository.deleteById(id);
		return modelMapper.map(post, PostResponseDto.class);
	}

	@Override
	public PostResponseDto updatePost(String id, PostDto postDto) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		String title = postDto.getTitle();
		if (title == null) {
			title = post.getTitle();
		}
		String content = postDto.getContent();
		if (content == null) {
			content = post.getContent();
		}
		Set<String> tags = postDto.getTags();
		if (tags == null) {
			tags = post.getTags();
		}
		post.setTitle(title);
		post.setContent(content);
		post.setTags(tags);
		forumRepository.save(post);
		return modelMapper.map(post, PostResponseDto.class);
	}

	@Override
	public boolean addLikeToPost(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		int like = post.getLikes();
		post.setLikes(++like);
		forumRepository.save(post);
		return true;
	}

	@Override
	public PostResponseDto addCommentToPost(String id, String user, MessageDto messageDto) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		Comment comment = modelMapper.map(messageDto, Comment.class);
		comment.setUser(user);
		post.getComments().add(comment);
		forumRepository.save(post);
		return modelMapper.map(post, PostResponseDto.class);
	}

	@Override
	public List<PostResponseDto> findPostsByAuthor(String author) {
		return forumRepository.findPostsByAuthor(author)
				.map(s -> modelMapper.map(s, PostResponseDto.class))
				.collect(Collectors.toList());
	}

}
