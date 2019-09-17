package bbdn.sample.b2rest.rest;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import bbdn.sample.b2rest.controller.TocHandler;
import bbdn.sample.b2rest.model.TocEntry;
import bbdn.sample.b2rest.model.Token;
import bbdn.sample.b2rest.util.CredentialUtil;
import bbdn.sample.b2rest.util.TokenUtil;

@Path("/courseToc")
public class CourseTocResource {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourseTocEntryById(@PathParam("id") String id, @Context HttpHeaders header) {

        String authHeader = "";
        String message = "GET: ";

        try {
            List<String> authHeaders = header.getRequestHeader(HttpHeaders.AUTHORIZATION);
             
            if (authHeaders.isEmpty()) {
                message += "Auth Headers is empty";
                return Response.status(403).entity("{ \"error\":  \"authHeader is missing\", \"message\" : \"" + message + TokenUtil.getMessage() + "\" }").build();
            } else {
                message += "Parse authHeader from " + authHeaders.get(0) + "... ";
                authHeader = authHeaders.get(0).split(" ", 2)[1];
                message += authHeader;
            }
            
            message += " Checking cache...";
            if ( !TokenUtil.isAuthorized(authHeader) ) {
                return Response.status(403).entity("{ \"error\":  \"authHeader " + authHeader +" does not match\", \"message\" : \"" + message + TokenUtil.getMessage() + "\" }").build();
            }
        } catch (Exception e) {
            return Response.status(403).entity("{ \"error\":  \"authHeader is missing - NullPointerException\", \"message\" : \"" + message + TokenUtil.getMessage() + "\" }").build();
        }

        HashMap<Integer,TocEntry> result = TocHandler.getTocItemById(id);

        int status = (int) result.keySet().toArray()[0];

        if(status == 200) {
            return Response.status(status).entity(result.get(status)).build();
        } else {
            return Response.status(status).entity(result.get(status)).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCourseTocEntry(TocEntry tocEntry, @Context UriInfo uriInfo, @Context HttpHeaders header) throws IOException {

        String authHeader = "";
        String message = "POST: ";

        try {
            List<String> authHeaders = header.getRequestHeader(HttpHeaders.AUTHORIZATION);
            if (authHeaders.isEmpty()) {
                return Response.status(403).entity("{ \"error\":  \"authHeader is missing\", \"message\" : \"" + message + TokenUtil.getMessage() + "\" }").build();
            } else {
                authHeader = authHeaders.get(0).split(" ", 2)[1];
            }
            
            if ( !TokenUtil.isAuthorized(authHeader) ) {
                return Response.status(403).entity("{ \"error\":  \"authHeader " + authHeader +" does not match\", \"message\" : \"" + message + TokenUtil.getMessage() + "\" }").build();
            }
        } catch (Exception e) {
            return Response.status(403).entity("{ \"error\":  \"authHeader is missing - NullPointerException\", \"message\" : \"" + message + TokenUtil.getMessage() + "\" }").build();
        }

        HashMap<Integer,TocEntry> result = TocHandler.addTocItem(tocEntry.getCourseId(), tocEntry.getLabel(), tocEntry.getUrl());

        int status = (int) result.keySet().toArray()[0];

         if(status == 201) {
            TocEntry createdTocEntry = result.get(status);

            URI uri = uriInfo.getAbsolutePathBuilder().path(createdTocEntry.getId()).build();

            return Response.created(uri).status(status).entity(result.get(status)).build();
        } else {
            return Response.status(status).entity(result.get(status)).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCourseTocEntryById(@PathParam("id") String id, TocEntry tocEntry, @Context HttpHeaders header) {

        String authHeader = "";
        String message = "PUT: ";

        try {
            List<String> authHeaders = header.getRequestHeader(HttpHeaders.AUTHORIZATION);
            if (authHeaders.isEmpty()) {
                return Response.status(403).entity("{ \"error\":  \"authHeader is missing\", \"message\" : \"" + message + TokenUtil.getMessage() + "\" }").build();
            } else {
                authHeader = authHeaders.get(0).split(" ", 2)[1];
            }
            
            if ( !TokenUtil.isAuthorized(authHeader) ) {
                return Response.status(403).entity("{ \"error\":  \"authHeader " + authHeader +" does not match\", \"message\" : \"" + message + TokenUtil.getMessage() + "\" }").build();
            }
        } catch (Exception e) {
            return Response.status(403).entity("{ \"error\":  \"authHeader is missing - NullPointerException\", \"message\" : \"" + message + TokenUtil.getMessage() + "\" }").build();
        }

        HashMap<Integer,TocEntry> result = TocHandler.updateTocItemById(id,tocEntry.getCourseId(),tocEntry.getLabel(), tocEntry.getUrl());

        int status = (int) result.keySet().toArray()[0];

        if(status == 204) {
            return Response.status(status).build();
        } else {
            return Response.status(status).entity(result.get(status)).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCourseTocEntryById(@PathParam("id") String id, @Context HttpHeaders header) {

        String authHeader = "";
        String message = "DELETE: ";

        try {
            List<String> authHeaders = header.getRequestHeader(HttpHeaders.AUTHORIZATION);
            if (authHeaders.isEmpty()) {
                return Response.status(403).entity("{ \"error\":  \"authHeader is missing\", \"message\" : \"" + message + TokenUtil.getMessage() + "\" }").build();
            } else {
                authHeader = authHeaders.get(0).split(" ", 2)[1];
            }
            
            if ( !TokenUtil.isAuthorized(authHeader) ) {
                return Response.status(403).entity("{ \"error\":  \"authHeader " + authHeader +" does not match\", \"message\" : \"" + message + TokenUtil.getMessage() + "\" }").build();
            }
        } catch (Exception e) {
            return Response.status(403).entity("{ \"error\":  \"authHeader is missing - NullPointerException\", \"message\" : \"" + message + TokenUtil.getMessage() + "\" }").build();
        }

        HashMap<Integer,TocEntry> result = TocHandler.deleteTocItemById(id);

        int status = (int) result.keySet().toArray()[0];

        if(status == 204) {
            return Response.noContent().status(status).build();
        } else {
            return Response.status(status).entity(result.get(status)).build();
        }
    }

    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authorize(@FormParam("grant_type") String grantType, @Context HttpHeaders header ) {
        
        String authHeader = "";
        String message = "Get Token: ";

        try {
            List<String> authHeaders = header.getRequestHeader(HttpHeaders.AUTHORIZATION);
            if (authHeaders.isEmpty()) {
                return Response.status(403).entity("{ \"error\":  \"authHeader is missing\", \"message\" : \"" + message + TokenUtil.getMessage() + "\" }").build();
            } else {
                authHeader = authHeaders.get(0).split(" ", 2)[1];
            }
            
            if ( !CredentialUtil.isAuthorized(authHeader) ) {
                return Response.status(403).entity("{ \"error\":  \"authHeader " + authHeader +" does not match\", \"message\" : \"" + message + TokenUtil.getMessage() + "\" }").build();
            }
        } catch (Exception e) {
            return Response.status(403).entity("{ \"error\":  \"authHeader is missing - NullPointerException\", \"message\" : \"" + message + TokenUtil.getMessage() + "\" }").build();
        }

        // AuthHeader matches, generate token and cache
        String uuid = UUID.randomUUID().toString();
        String expires = "3200";
        String type = "bearer";

        Token token = new Token(uuid, expires, type);

        if( TokenUtil.cacheToken(token) ) {
            return Response.ok(token).build();
        } else {
            return Response.status(403).entity("{ \"message\" : \"" + message + TokenUtil.getMessage() + "\" }").build();
        }
    }

    @POST
    @Path("/token/revoke")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response revoke(@Context HttpHeaders header ) {
        
        String authHeader = "";
        String message = "Revoke Token: ";

        try {
            List<String> authHeaders = header.getRequestHeader(HttpHeaders.AUTHORIZATION);
            if (authHeaders.isEmpty()) {
                return Response.status(403).entity("{ \"error\":  \"authHeader is missing\", \"message\" : \"" + message + TokenUtil.getMessage() + "\" }").build();
            } else {
                authHeader = authHeaders.get(0).split(" ", 2)[1];
            }
            
            if ( !TokenUtil.isAuthorized(authHeader) ) {
                return Response.status(403).entity("{ \"error\":  \"authHeader " + authHeader +" does not match\", \"message\" : \"" + message + TokenUtil.getMessage() + "\" }").build();
            }
        } catch (Exception e) {
            return Response.status(403).entity("{ \"error\":  \"authHeader is missing - NullPointerException\", \"message\" : \"" + message + TokenUtil.getMessage() + "\" }").build();
        }

        if( TokenUtil.revokeToken() ) {
            return Response.ok().build();
        } else {
            return Response.status(503).entity("{ \"message\" : \"" + message + TokenUtil.getMessage() + "\" }").build();
        }
    }
}