package org.example.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {

    private final NewsService service;

    public NewsController(NewsService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<News>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> findById(@PathVariable("id") Long id) {
        Optional<News> news = service.findById(id);
        if (news.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(news.get());
    }

    @PreAuthorize("hasAuthority('REPORTER')")
    @PostMapping()
    public ResponseEntity<News> create(@RequestBody News news) {
        News created = service.create(news);
        if (created != null) {
            return ResponseEntity.ok(created);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody News news) {
        Optional<News> newsFound = service.findById(id);
        if (newsFound.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.update(id,news);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<News> news = service.findById(id);
        if (news.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        News news1 = news.get();
        service.delete(news1);
        return ResponseEntity.noContent().build();
    }
}