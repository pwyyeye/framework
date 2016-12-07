package login;

import org.junit.Test;

import com.xxl.controller.common.LoginController;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;  
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;  
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;  
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*; 


/**
 * Unit test for simple App.
 */
public class LoginCotrollerTest {
	
	private MockMvc mockMvc;
	
	@Test
	public void testLogin() throws Exception {
		LoginController login = new LoginController();
		mockMvc = standaloneSetup(login).build();
		mockMvc.perform(
				get("/loginController/home"))
				.andExpect(view().name("login"))
				.andDo(print())
				.andReturn();
		;

	}
}
