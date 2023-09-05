package com.example.povlapi_flaten.main;

import com.example.povlapi_flaten.client.PovlApiClient;
import com.example.povlapi_flaten.model.User;

/**
 * @description:TODO
 * @author:Povlean
 */
public class ApiMain {
    public static void main(String[] args) {
        PovlApiClient povlApiClient = new PovlApiClient("123456", "123456");

        String res1 = povlApiClient.getNameByGet("Get Api");
        System.out.println(res1);

        String res2 = povlApiClient.getNameByPost("Post Api");
        System.out.println(res2);

        User user = new User("Body User", "24");
        String res3 = povlApiClient.getUsernameByPost(user);
        System.out.println(res3);
    }
}
