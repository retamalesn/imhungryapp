package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.imhungryapp.demo.ImhungryApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ImhungryApplication.class)
@SpringBootTest
public class ImhungryApplicationTests {

	@Test
	public void contextLoads() {
	}

}
