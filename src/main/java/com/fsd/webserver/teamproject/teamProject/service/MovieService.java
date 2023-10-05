package com.fsd.webserver.teamproject.teamProject.service;

import com.fsd.webserver.teamproject.teamProject.common.Result;
import com.fsd.webserver.teamproject.teamProject.domain.Movie;

import java.util.List;

/**
 *
 */
public interface MovieService  {
    Result<List<Movie>> selectAll();
    Result<String> insertMovie(Movie movie);

    Result<String> deleteMovieById(Long id);

    Result<String> updateMovie(Movie movie);

    Result<Movie> selectById(Long id);

}
