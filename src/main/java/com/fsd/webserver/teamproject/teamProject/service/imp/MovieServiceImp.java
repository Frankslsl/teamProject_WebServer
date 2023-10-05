package com.fsd.webserver.teamproject.teamProject.service.imp;

import com.fsd.webserver.teamproject.teamProject.common.Result;

import com.fsd.webserver.teamproject.teamProject.domain.Movie;
import com.fsd.webserver.teamproject.teamProject.domain.MovieExample;
import com.fsd.webserver.teamproject.teamProject.mapper.MovieMapper;
import com.fsd.webserver.teamproject.teamProject.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
@Slf4j
public class MovieServiceImp implements MovieService {
    @Autowired
    MovieMapper movieMapper;

    @Override
    public Result<List<Movie>> selectAll() {
        MovieExample movieExample = new MovieExample();
        movieExample.createCriteria().andTitleIsNotNull();

        List<Movie> movies = movieMapper.selectByExample(movieExample);
        if (!movies.isEmpty()) {
            return Result.success(movies, HttpStatus.ACCEPTED);
        }
        return Result.error("There is no movie stored in database", HttpStatus.NO_CONTENT);
    }

    @Override
    public Result<String> insertMovie(Movie movie) {
        int insert = movieMapper.insert(movie);
        return insert != 0? Result.success("Insert successfully", HttpStatus.CREATED) : Result.error("Something went wrong", HttpStatus.NO_CONTENT);
    }

    @Override
    public Result<String> deleteMovieById(Long id) {
        MovieExample movieExample = new MovieExample();
        movieExample.createCriteria().andIdEqualTo(id);
        int delete = movieMapper.deleteByExample(movieExample);
        return delete != 0? Result.success("delete successfully", HttpStatus.ACCEPTED) : Result.error("Something went wrong", HttpStatus.NO_CONTENT);
    }

    @Override
    public Result<String> updateMovie(Movie movie) {
        int update = movieMapper.updateByPrimaryKeySelective(movie);
        return update != 0? Result.success("update successfully", HttpStatus.ACCEPTED) : Result.error("Something went wrong", HttpStatus.NO_CONTENT);
    }

    @Override
    public Result<Movie> selectById(Long id) {
        Movie movie = movieMapper.selectByPrimaryKey(id);
        return Result.success(movie,HttpStatus.ACCEPTED);
    }

}
