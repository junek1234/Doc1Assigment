package org.example.springbootdocassigment.config;

import org.example.springbootdocassigment.model.Story;
import org.example.springbootdocassigment.repository.StoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final StoryRepository storyRepository;

    public DataInitializer(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @Override
    public void run(String... args) {
        if (storyRepository.count() > 0) return;

        save("Architectural Model Collapses Under Cat Scrutiny",
                "An entire semester's worth of ACTM modeling was destroyed when the campus cat decided it was the perfect place for a nap. Engineering students are now calculating the structural integrity of feline resistance.",
                "ACTM");
        save("Construction Site Replaced By Lego Bricks",
                "In a move to save on material costs, the newly planned campus pavilion will be entirely constructed from Lego bricks, an ACTM spokesperson confirmed.",
                "ACTM");
        save("Students Design Floating Classroom",
                "ACTM students have proposed a floating classroom design on the campus lake to solve the ongoing space shortage. Preliminary tests involved waterproof textbooks.",
                "ACTM");

        save("New Framework Requires Students To Code In Binary",
                "A strict new Software Technology Engineering professor has banned IDEs, mandating that all assignments be submitted via punch cards or raw binary files.",
                "Software Technology Engineering");
        save("Dorm Wifi Rewritten in Rust",
                "Software Technology Engineering students took down the campus network for 24 hours to 'rewrite it in Rust.' The new connection is blazingly fast but requires a 10-step authentication handshake.",
                "Software Technology Engineering");
        save("AI Now Generating Excuses for Late Assignments",
                "An ingenious Software Technology Engineering project trained an AI to generate highly plausible, non-verifiable excuses for missed deadlines. The algorithm has been secretly used by 40% of the class.",
                "Software Technology Engineering");

        save("Supply Chain Optimization Leads To Infinite Pizza",
                "A Climate and Supply Engineering algorithm meant to optimize cafeteria deliveries created a feedback loop, resulting in a continuous, unstoppable flow of pepperoni pizzas to the student lounge.",
                "Climate and Supply Engineering");
        save("Solar Panels Installed Indoors",
                "A miscommunication in the Climate and Supply Engineering department led to the installation of 50 high-efficiency solar panels inside the basement. Researchers are now attempting to harness the power of fluorescent lighting.",
                "Climate and Supply Engineering");
        save("Wind Turbine Generates Enough Power For One Laptop",
                "The highly anticipated campus wind turbine project is complete. While it only generates enough energy to charge a single MacBook Pro, students remain highly optimistic about its climate impact.",
                "Climate and Supply Engineering");
    }

    private void save(String title, String content, String department) {
        Story s = new Story();
        s.setTitle(title);
        s.setContent(content);
        s.setDepartment(department);
        storyRepository.save(s);
    }
}
