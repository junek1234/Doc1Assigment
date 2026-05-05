package org.example.springbootdocassigment.repository;

import org.example.springbootdocassigment.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long> {
    List<Story> findByDepartment(String department);
    List<Story> findAllByOrderByCreatedAtDesc();
}
