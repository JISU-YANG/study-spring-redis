package com.sparta.redis.article;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ValueOperations<String, Integer> ops;

    public ArticleController(RedisTemplate<String, Integer> articleTemplate) {
        this.ops = articleTemplate.opsForValue();
    }

    @GetMapping("/{articles_id}")
    public void readArticles(@PathVariable("articles_id") Long articlesId) {
        ops.increment("articles:" + articlesId);
    }
}

