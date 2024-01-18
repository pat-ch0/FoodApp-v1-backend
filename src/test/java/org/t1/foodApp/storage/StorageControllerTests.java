package org.t1.foodApp.storage;

import jakarta.servlet.http.Cookie;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class StorageControllerTests {

    @Autowired
    private MockMvc mockMvc;
    private String userCookieValue;
    private Cookie cookie;
    private final String storageJson = "{\"label\":\"Frigo\"}";

    @Test
    public void testAddAndGetStorage() throws Exception {
        userCookieValue = "testAddAndGetStorage";
        cookie = new Cookie("user_cookie", userCookieValue);
        mockMvc.perform(MockMvcRequestBuilders.put("/storages")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(storageJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.label").value("Frigo"))
                .andExpect(jsonPath("$.products").isEmpty());

        mockMvc.perform(MockMvcRequestBuilders.put("/storages")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(storageJson))
                .andExpect(status().isBadRequest());

        String secondStorageJson = "{\"label\":\"Congélateur\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/storages")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(secondStorageJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.label").value("Congélateur"));

        mockMvc.perform(MockMvcRequestBuilders.get("/storages")
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void TestCookieSession() throws Exception {
        userCookieValue = "TestCookieSession1";
        cookie = new Cookie("user_cookie", userCookieValue);
        mockMvc.perform(MockMvcRequestBuilders.put("/storages")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(storageJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.label").value("Frigo"))
                .andExpect(jsonPath("$.products").isEmpty());

        mockMvc.perform(MockMvcRequestBuilders.get("/storages")
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
        userCookieValue = "TestCookieSession2";
        cookie = new Cookie("user_cookie", userCookieValue);
        mockMvc.perform(MockMvcRequestBuilders.get("/storages")
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void testAddAndDeleteStorage() throws Exception {
        userCookieValue = "testAddAndDeleteStorage";
        cookie = new Cookie("user_cookie", userCookieValue);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/storages")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(storageJson))
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(responseContent);
        String storageId = jsonObject.getString("id");

        mockMvc.perform(MockMvcRequestBuilders.delete("/storages/" + storageId)
                        .cookie(cookie))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/storages")
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void testGetStorageById() throws Exception {
        userCookieValue = "testGetStorageById";
        cookie = new Cookie("user_cookie", userCookieValue);
        mockMvc.perform(MockMvcRequestBuilders.get("/storages/999")
                        .cookie(cookie))
                .andExpect(status().isNotFound());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/storages")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(storageJson))
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(responseContent);
        String storageId = jsonObject.getString("id");

        mockMvc.perform(MockMvcRequestBuilders.get("/storages/"+storageId)
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(storageId));
    }



}
