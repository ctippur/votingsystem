package com.rest.test;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.test.model.Category;
import com.rest.test.model.Media;
import com.rest.test.MediaPojo;
import com.rest.test.model.Medium;
import com.rest.test.model.Vote;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.*;
import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
@Path("/media")
public class MediaResource {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public Response createMedium(final HashMap <String, ArrayList<Object>> newMedium) {

        if (newMedium.isEmpty()) {
            return Response.status(400).entity(new Error("Invalid name or breed")).build();
        }

        Medium medium = new Medium();


        ArrayList <Object> categoryMovies=newMedium.get("movies");
        ArrayList <Object> categoryTv=newMedium.get("tv");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        for (int i = 0; i < categoryMovies.size(); i++) {
            //System.out.println(categoryMovies.get(i));
            Media m=new Media();
            Object j = categoryMovies.get(i);

            try {
                String jString = mapper.writeValueAsString(j);
                MediaPojo p = mapper.readValue(jString.replaceFirst("\ufeff", ""), MediaPojo.class);
                m.setType("Movie");
                m.setId(p.getId());
                m.setDescription(p.getDescription());
                m.setCategory(p.getCategory().toString());
                m.setCategory(String.join(",", p.getCategory()));

            }catch (IOException ex){
                ex.printStackTrace();
            }

            //m.setDescription(String.valueOf(j.get("description")));
            //m.setId(String.valueOf(j.get("id")));
            //m.setCategory(j.get("category").toString());
            //System.out.println(j.get("category").toString());
            m.save();

        }

        for (int i = 0; i < categoryTv.size(); i++) {
            //System.out.println(categoryMovies.get(i));
            Media m=new Media();
            Object j = categoryTv.get(i);

            try {
                String jString = mapper.writeValueAsString(j);
                MediaPojo p = mapper.readValue(jString.replaceFirst("\ufeff", ""), MediaPojo.class);
                m.setType("Tv");
                m.setId(p.getId());
                m.setDescription(p.getDescription());
                m.setCategory(String.join(",", p.getCategory()));
                //m.setCategory(p.getCategory().toString());

            }catch (IOException ex){
                ex.printStackTrace();
            }
            m.save();

        }


        //dbMedium.setId(UUID.randomUUID().toString());
        //medium.save();
        return Response.status(200).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listMedia(@QueryParam("limit") int limit) {
        /*
        if (limit < 1) {
            limit = 10;
        }
        */

        List <Media> mMap = new ArrayList<>();

        Media m = new Media();
        List <Map <String, AttributeValue>> mList =  m.get();

        Vote v=new Vote();
        List <Map <String, AttributeValue>> vList =  v.getAll();

        System.out.println(vList);


        //List <HashMap<String, HashMap<String, String>>> allVotes = new ArrayList<HashMap<String, HashMap<String, String>>>();

        HashMap <String, HashMap<String, String>> allVotes = new HashMap<>();

        // Read all votes and add to hash
        for (Map<String, AttributeValue> vItem : vList){
            String id = vItem.get("MediaId").getS();
            String up = vItem.get("up").getS();
            String down = vItem.get("down").getS();
            HashMap <String,String> tempHashValues = new HashMap<>();
            tempHashValues.put("up", up);
            tempHashValues.put("down", down);
            allVotes.put(id,tempHashValues);
        }


        // For every Media item, find of there is a corresponding vote
        for (Map<String, AttributeValue> mItem : mList){
            Media mTemp=new Media();
            try{
                String vDescription = mItem.get("description").getS();
                String vCategory = mItem.get("category").getS();
                String vType = mItem.get("type").getS();
                String vId = mItem.get("MediaId").getS();

                mTemp.setCategory(vCategory);
                mTemp.setDescription(vDescription);
                mTemp.setType(vType);
                mTemp.setId(vId);

                if (allVotes.containsKey(vId)){
                    mTemp.setUp(allVotes.get(vId).get("up"));
                    mTemp.setDown(allVotes.get(vId).get("down"));
                }else{
                    mTemp.setUp("0");
                    mTemp.setDown("0");

                }
                mMap.add(mTemp); // Add to the list


            } catch (NumberFormatException e){
                System.out.println(e.getMessage());
            }


        }
        HashMap <String, List> hMapresp = new HashMap<>();
        hMapresp.put("aaData",mMap);
        return Response.status(200).entity(hMapresp).build();
    }

    @Path("/{media_type}") @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Medium getMediaDetails() {
        Medium newMedium = new Medium();
        return newMedium;
    }
}
