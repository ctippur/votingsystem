package com.rest.test;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.test.model.Media;
import com.rest.test.model.Vote;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



import com.rest.test.model.Medium;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Path("/vote")
public class VoteResource {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createVote(final HashMap <String,String> newVote) {

        if (newVote.equals(null)) {
            return Response.status(400).entity(new Error("Invalid vote")).build();
        }

        //dbMedium.setId(UUID.randomUUID().toString());
        //ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        String id= newVote.get("MediaId");
        String upDown=newVote.get("updown");

        Vote vVote= new Vote();
        vVote.setMediaId(id);


        Item item=vVote.get();

        HashMap<String, String> ret = new HashMap<>();
        if (item == null){
            ret.put("MediaId", id);
            ret.put("up","0");
            ret.put("down","0");
            vVote.setUp("0");
            vVote.setDown("0");
            vVote.update();
        }else{

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            try{
                //String jString = mapper.writeValueAsString(item);

                //HashMap <String,String> currentVotes=mapper.readValue(jString.replaceFirst("\ufeff", ""), HashMap.class);

                // Update votes
                if (upDown.equals("up")){
                    Integer upVoteInt=Integer.parseInt(String.valueOf(item.get("up"))) + 1;
                    String fUpVote=String.valueOf(upVoteInt);
                    String fDownVote=(String) item.get("down");
                    vVote.setUp(fUpVote);
                    vVote.setDown(fDownVote);
                }else{
                    Integer upVoteInt=Integer.parseInt(String.valueOf(item.get("down"))) + 1;
                    String fDownVote=String.valueOf(upVoteInt);
                    String fUpVote=(String) item.get("up");
                    vVote.setUp(fUpVote);
                    vVote.setDown(fDownVote);

                }
                vVote.update();
                ret.put("MediaId", id);
                ret.put("up",vVote.getUp());
                ret.put("down",vVote.getDown());
            }catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }

        }
        return Response.status(200).entity(ret).build();
    }


    @Path("/{id}") @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Vote getPetDetails(String id) {

        Vote newVote = new Vote();

        return newVote;
    }
}
