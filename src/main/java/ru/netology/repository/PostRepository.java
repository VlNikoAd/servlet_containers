package ru.netology.repository;

import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {

  ConcurrentHashMap<Long, Post> repo = new ConcurrentHashMap<>();
  AtomicLong COUNT;
  public List<Post> all() {
    return new ArrayList<>(repo.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(repo.get(id));
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      //save
    } else {
      //update
    }


    return post;
  }

  public void removeById(long id) {
  }
}
