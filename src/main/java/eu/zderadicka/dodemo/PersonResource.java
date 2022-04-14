package eu.zderadicka.dodemo;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import io.quarkus.panache.common.Sort;

@Path("/person")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    @GET
    public List<Person> getAll() throws Exception {

        return Person.findAll(Sort.ascending("lastName")).list();
    }

    @POST
    @Transactional
    public Response create(Person p) {
        if (p == null || p.id != null) {
            throw new WebApplicationException("Person id is not null");
        }
        p.persist();

        return Response.ok(p).status(201).build();
    }

    @PUT
    @Transactional
    @Path("{id}")
    public Person update(@PathParam Long id, Person p) {
        Person entity = Person.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Person with id " + id + " does not exist", 404);
        }

        if (p.salutation != null) entity.salutation = p.salutation;
        if (p.firstName != null) entity.firstName = p.firstName;
        if (p.lastName != null) entity.lastName = p.lastName;

        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam Long id) {
        Person entity = Person.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Cannot delete id "+ id, 404);
        }

        entity.delete();

        return Response.status(204).build();
    }
}
