package com.taufiqurrahman.pelanggan;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PelangganApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void acceptsAndReturnsJenisKelaminJsonProperty() throws Exception {
		mockMvc.perform(post("/api/pelanggan")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"nama_pelanggan":"Ayu","alamat":"Bandung","jenis_kelamin":"P"}
						"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.jenis_kelamin").value("P"));
	}

}
