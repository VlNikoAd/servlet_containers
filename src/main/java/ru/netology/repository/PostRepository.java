package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {

    private final ConcurrentHashMap<Long, Post> repo = new ConcurrentHashMap<>();
    private final AtomicLong COUNT = new AtomicLong(0);

    public List<Post> all() {
        return new ArrayList<>(repo.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(repo.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(COUNT.incrementAndGet());
            repo.put(post.getId(), post);
        } else {
            if (repo.containsKey(post.getId()))
                repo.replace(post.getId(), post);
            else
                throw new NotFoundException("Failed to update post");
        }

        return post;
    }

    public void removeById(long id) {
        repo.remove(id);
    }
}
