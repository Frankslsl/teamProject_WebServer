package com.fsd.webserver.teamproject.teamProject;

import com.fsd.webserver.teamproject.teamProject.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TeamProjectApplicationTests {
    @Autowired
    MovieService movieService;

    @Test
    void contextLoads() {
        movieService.selectAll();
    }

}
