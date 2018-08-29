package controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.imhungryapp.demo.ImhungryApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ImhungryApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RastaurantControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}

	@Test
	public void verifyGetRestaurants() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/all").accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.*", hasSize(10))).andDo(print());
	}

	@Test
	public void verifyGetRestaurantsByRating() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/byrating/3").accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.[0].id").isNumber()).andExpect(jsonPath("$.[0].logo").exists())
				.andExpect(jsonPath("$.[0].rating").exists()).andExpect(jsonPath("$.[0].comercialEmail").exists())
				.andExpect(jsonPath("$.[0].adminNumber").exists()).andExpect(jsonPath("$.[0].comercialEmail").exists())
				.andExpect(jsonPath("$.[0].address").exists()).andExpect(jsonPath("$.[0].location").exists())
				.andDo(print());
	}

	@Test
	public void verifyInvalidGetRestaurantsByRatingArgument() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/byrating/r").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	public void verifyInvalidGetRestaurantsByRatingId() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/byrating/0").accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isEmpty()).andDo(print());
	}
	
	@Test
	public void verifyDeleteRestaurant() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/restaurants/delete/97").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().is(200))
		.andDo(print());
	}
	
	@Test
	public void verifyInvalidDeleteRestaurant() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/restaurants/delete/0").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().is(404))
		.andDo(print());
	}
	
	@Test
	public void verifySaveRestaurant() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/restaurants/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\n" + 
        		"\"address\":\"San Juan 2434\",\n" + 
        		"\"logo\":\"https://www.infiwebs.com/elegant-elements-fusion-builder/wp-content/uploads/sites/4/2017/10/logosquare-chimp-mini.jpg\",\n" + 
        		"\"adminNumber\":\"444444\",\n" + 
        		"\"legalName\":\"Nico Reta\",\n" + 
        		"\"comercialEmail\":\"fssds@sdsdsds.com\",\n" + 
        		"\"location\":\"-32.9890273,-68.8117311\",\n" + 
        		"\"meals\":[\n" + 
        		"	{\"name\":\"pollo\",\n" + 
        		"		\"description\":\"pollo description\",\n" + 
        		"		\"price\":\"200\"\n" + 
        		"	}\n" + 
        		"	],\n" + 
        		"\"reviews\":[\n" + 
        		"	{\n" + 
        		"		\"name\":\"otra review\",\n" + 
        		"		\"review\":\"otra description\",\n" + 
        		"		\"rating\":\"4\"\n" + 
        		"	},\n" + 
        		"		{\n" + 
        		"		\"name\":\"una review\",\n" + 
        		"		\"review\":\"una description\",\n" + 
        		"		\"rating\":\"2\"\n" + 
        		"	}],\n" + 
        		"	\"purchaseOrders\":[{\n" + 
        		"		\"address\":\"direccion destino\",\n" + 
        		"		\"latLng\":\"-32.9685663,-68.8472866\",\n" + 
        		"	\"meals\":[{\n" + 
        		"		\"id\":\"68\"\n" + 
        		"	},\n" + 
        		"	{\n" + 
        		"		\"id\":\"68\"\n" + 
        		"	}]}]\n" + 
        		"}")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id").isNumber())
		.andExpect(jsonPath("$.logo").exists())
		.andExpect(jsonPath("$.rating").exists())
		.andExpect(jsonPath("$.comercialEmail").exists())
		.andExpect(jsonPath("$.adminNumber").exists())
		.andExpect(jsonPath("$.comercialEmail").value("fssds@sdsdsds.com"))
		.andExpect(jsonPath("$.address").exists())
		.andExpect(jsonPath("$.location").exists())
		.andDo(print());
	}
	
	@Test
	public void verifyMalformedSaveRestaurant() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/restaurants/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\n" + 
        		"\"address\":\"San Juan 2434\",\n" + 
        		"\"address2\":\"Mendoza 2434\",\n" +
        		"\"logo\":\"https://www.infiwebs.com/elegant-elements-fusion-builder/wp-content/uploads/sites/4/2017/10/logosquare-chimp-mini.jpg\",\n" + 
        		"\"adminNumber\":\"444444\",\n" + 
        		"\"legalName\":\"Nico Reta\",\n" + 
        		"\"comercialEmail\":\"fssds@sdsdsds.com\",\n" + 
        		"\"location\":\"-32.9890273,-68.8117311\"")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().is(400))		
		.andDo(print());
	}

}
