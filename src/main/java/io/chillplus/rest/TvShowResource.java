package io.chillplus.rest;

import io.chillplus.domain.TvShow;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Path("/api/tv")
public class TvShowResource {
    private Long sequence = 0L;
    List<TvShow> tvShows = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.status(Response.Status.OK).entity(tvShows).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(TvShow tvShow) {
        if (tvShow.id != null) {
            return Response.status(400, "Id should not be specified").build();
        }
        if (tvShow.title == null || tvShow.title.isBlank()) {
            return Response.status(400, "Title cannot be blank").build();
        }
        tvShow.id = sequence++;

        tvShows.add(tvShow);
        return Response.status(Response.Status.CREATED).entity(tvShow).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneById(@PathParam("id") long id) {
        TvShow entity;
        for (int i = 0; i < tvShows.size(); i++) {
            TvShow tvShow = tvShows.get(i);
            if (tvShow.id == id) {
                entity = tvShow;
                return Response.status(Response.Status.OK).entity(entity).build();
            }
        }
        return Response.status(404, "TvShow with id " + id + " not found").build();
    }

    @DELETE
    public void deleteAll() {
        tvShows.clear();
    }

    @DELETE
    @Path("/{id}")
    public void deleteOne(@PathParam("id") long id) {
        int index = 0;
        for (; index < tvShows.size(); index++) {
            TvShow tvShow = tvShows.get(index);
            if (tvShow.id == id) {
                break;
            }
        }
        if (index < tvShows.size()) {
            tvShows.remove(index);
        }
    }
}
