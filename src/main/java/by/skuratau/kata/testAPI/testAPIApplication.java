package by.skuratau.kata.testAPI;


import by.skuratau.kata.testAPI.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class testAPIApplication {

    static RestTemplate restTemplate = new RestTemplate();
    static String URL = "http://94.198.50.185:7081/api/users";

    public static void main(String[] args) {

        String result =  getResult();
        System.out.println(result);

    }

    public static String getResult() {

        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity("http://94.198.50.185:7081/api/users", User[].class);
        String cookie = responseEntity.getHeaders().getFirst("Set-Cookie");
        HttpHeaders headers = createHeaders(cookie);
        String one = methodPOST(headers);
        String two = methodPUT(headers);
        String three = methodDELETE(headers);
        return one + two + three;
    }

    public static HttpHeaders createHeaders(String cookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    public static String methodPOST(HttpHeaders headers) {
        User user = new User();
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte) 25 );
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        String one = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class).getBody();
        return one;
    }
    public static String methodPUT(HttpHeaders headers) {
        User updateUser = new User();
        updateUser.setId(3L);
        updateUser.setName("Thomas");
        updateUser.setLastName("Shelby");
        updateUser.setAge((byte) 25);
        HttpEntity<User> entity = new HttpEntity<>(updateUser, headers);
        String two = restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class).getBody();
        return two;
    }

    public static String methodDELETE(HttpHeaders headers) {
        HttpEntity<User> entity = new HttpEntity<>(headers);
        String three = restTemplate.exchange(URL + "/3", HttpMethod.DELETE, entity, String.class).getBody();
        return three;
    }
}
