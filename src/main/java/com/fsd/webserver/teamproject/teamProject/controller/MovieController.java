package com.fsd.webserver.teamproject.teamProject.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.fsd.webserver.teamproject.teamProject.common.Result;
import com.fsd.webserver.teamproject.teamProject.domain.Movie;
import com.fsd.webserver.teamproject.teamProject.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 *
 */
@RestController
@RequestMapping("/movies")
@CrossOrigin
@Slf4j
public class MovieController {
    @Autowired
    private MovieService movieService;
    @Value("${teamProject.basePath}")
    private String posterPath;


    /**
     * Select all movies from database
     * @return
     */
    @GetMapping
    public Result<List<Movie>> getAll() {
        return movieService.selectAll();
    }

    /**
     * delete a movie by given id
     * @param id
     * @return
     */

    @DeleteMapping("/{id}")
    public Result<String> deleteMovie(@PathVariable Long id) {
        log.info("=================================>" + id.toString());
        Result<String> stringResult = movieService.deleteMovieById(id);
        return stringResult;
    }

    /**
     * add a movie into data base
     * @param file The poster image, user want to upload
     * @param title
     * @param releasedate
     * @param genre
     * @param director
     * @param rating
     * @return
     */
    @PostMapping
    public Result<String> insertMovie(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam String title, @RequestParam LocalDate releasedate, @RequestParam String genre, @RequestParam String director, @RequestParam BigDecimal rating) {
        Movie movie = new Movie();
        movie.setGenre(genre);
        movie.setDirector(director);
        movie.setRating(rating);
        movie.setReleasedate(releasedate);
        movie.setTitle(title);
        //set a default poster for the movie
        movie.setImgurl("logo512.png");
        if (file != null) {
            File targetFile = new File(posterPath);
            //create the directories if it is not existed
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            String originalFileName = file.getOriginalFilename();
            String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
            //using UUID to generate a file name for the poster
            String fileName = UUID.randomUUID().toString() + suffix;
            try {
                file.transferTo(new File(posterPath + fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //re-assign the poster
            movie.setImgurl(fileName);
        }
        //using snowflake to generate a movie id
        Snowflake snowflake = IdUtil.getSnowflake();
        Long l = snowflake.nextId();
        String substring = l.toString().substring(0, 8);
        Long id = Long.parseLong(substring);
        movie.setId(id);
        log.info("The movie will be added is {}", movie.toString());
        return movieService.insertMovie(movie);
    }

    /**
     * select a movie be given id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Movie> getMovieById(@PathVariable Long id) {
        Result<Movie> movieResult = movieService.selectById(id);

        return movieResult;
    }

    /**
     * update a movie according to the id, only update the properties which are not null
     * @param file
     * @param id
     * @param title
     * @param releasedate
     * @param genre
     * @param director
     * @param rating
     * @return
     */
    @PutMapping
    public Result<String> updateMovie(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam Long id, @RequestParam String title, @RequestParam LocalDate releasedate, @RequestParam String genre, @RequestParam String director, @RequestParam BigDecimal rating) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setGenre(genre);
        movie.setDirector(director);
        movie.setRating(rating);
        movie.setReleasedate(releasedate);
        movie.setTitle(title);
        if (file != null) {
            File targetFile = new File(posterPath);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            String originalFileName = file.getOriginalFilename();
            String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));

            String fileName = UUID.randomUUID().toString() + suffix;
            try {
                file.transferTo(new File(posterPath + fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            movie.setImgurl(fileName);
        }
        log.info("the movie will be updated to {}", movie.toString());
        Result<String> stringResult = movieService.updateMovie(movie);
        return stringResult;
    }

    /**
     * select a movie by title according to the given keyword
     * @param keyWord
     * @return
     */
    @GetMapping("/search/")
    public Result<List<Movie>> getMoviesByLikeTitle(@RequestParam("keyWord") String keyWord) {
        log.info("======================>" + keyWord);
        Result<List<Movie>> listResult = movieService.selectByLikeTitle(keyWord);
        return listResult;
    }
}
