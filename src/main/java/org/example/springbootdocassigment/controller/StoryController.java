package org.example.springbootdocassigment.controller;

import org.example.springbootdocassigment.model.Story;
import org.example.springbootdocassigment.repository.StoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
public class StoryController {

    private final StoryRepository storyRepository;

    public StoryController(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @GetMapping
    public List<Story> getAllStories() {
        return storyRepository.findAllByOrderByCreatedAtDesc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Story> getStoryById(@PathVariable Long id) {
        return storyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/department/{department}")
    public List<Story> getStoriesByDepartment(@PathVariable String department) {
        return storyRepository.findByDepartment(department);
    }

    @PostMapping
    public Story createStory(@RequestBody Story story) {
        return storyRepository.save(story);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Story> updateStory(@PathVariable Long id, @RequestBody Story storyDetails) {
        return storyRepository.findById(id)
                .map(story -> {
                    story.setTitle(storyDetails.getTitle());
                    story.setContent(storyDetails.getContent());
                    story.setDepartment(storyDetails.getDepartment());
                    return ResponseEntity.ok(storyRepository.save(story));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable Long id) {
        return storyRepository.findById(id)
                .map(story -> {
                    storyRepository.delete(story);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
