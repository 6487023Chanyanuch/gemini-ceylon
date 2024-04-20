//package com.example.demo.configuration;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//class CustomDateDeserializer extends JsonDeserializer<Date> {
//
//    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//    @Override
//    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
//        String dateString = jsonParser.getText();
//        try {
//            return dateFormat.parse(dateString);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
