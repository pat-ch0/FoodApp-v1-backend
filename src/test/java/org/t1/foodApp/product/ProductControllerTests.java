package org.t1.foodApp.product;


import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.MvcResult;
import org.json.JSONObject;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;
    private String userCookieValue = "ProductControllerTests";
    private Cookie cookie;
    private String storageJson = "{\"label\":\"Frigo\"}";
    private String storageId;

    @BeforeEach
    public void setUp() throws Exception {
        // set random name to storageJson
        storageJson = "{\"label\":\"Frigo"+Math.random()+"\"}";
        cookie = new Cookie("user_cookie", userCookieValue);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/storages")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(storageJson))
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(responseContent);
        storageId = jsonObject.getString("id");
    }


    @Test
    public void testAddProduct() throws Exception {
        String productJson = "{\"storageId\": \""+storageId+"\", \"stock\": 10, \"barcode\": \"737628064502\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/products")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/storages/"+storageId)
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products.length()").value(1));

        productJson = "{\"storageId\": \""+storageId+"\", \"stock\": -1, \"barcode\": \"737628064502\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/products")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddBadProduct() throws Exception {
        String productJson = "{\"storageId\": \""+storageId+"\", \"stock\": 10, \"barcode\": \"9999999999999999999999999\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/products")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.get("/storages/"+storageId)
                        .cookie(cookie))
                .andExpect(jsonPath("$.products.length()").value(0));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        String productJson = "{\"storageId\": \"" + storageId + "\", \"stock\": 10, \"barcode\": \"737628064502\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/products")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isOk());

        productJson = "{\"storageId\": \"" + storageId + "\", \"stock\": 1, \"barcode\": \"737628064502\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/storages/" + storageId)
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products[?(@.barcode == '737628064502')].stock").value(1));

        String invalidProductJson = "{\"storageId\": \"" + storageId + "\", \"stock\": -1, \"barcode\": \"737628064502\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidProductJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateBadProduct() throws Exception {

        String productJson = "{\"storageId\": \"" + storageId + "\", \"stock\": 1, \"barcode\": \"737628064502\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testGetProductByValidBarcode() throws Exception {
        // Code-barres valide
        String validBarcode = "737628064502";

        mockMvc.perform(MockMvcRequestBuilders.get("/products/" + validBarcode)
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.barcode").value("737628064502"))
                .andExpect(jsonPath("$.stock").value(0))
                .andExpect(jsonPath("$.name").value("Thai peanut noodle kit includes stir-fry rice noodles & thai peanut seasoning"))
                .andExpect(jsonPath("$.imageSrc").value("https://images.openfoodfacts.org/images/products/073/762/806/4502/front_en.6.400.jpg"));

    }

    @Test
    public void testGetProductByInvalidBarcode() throws Exception {
        // Code-barres invalide
        String invalidBarcode = "invalidBarcode";

        mockMvc.perform(MockMvcRequestBuilders.get("/products/" + invalidBarcode)
                        .cookie(cookie))
                .andExpect(status().isNotFound()); // Ou autre statut approprié selon votre implémentation
    }




}