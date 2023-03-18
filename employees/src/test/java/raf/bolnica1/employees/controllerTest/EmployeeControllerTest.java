package raf.bolnica1.employees.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import raf.bolnica1.employees.dto.employee.EmployeeCreateDto;
import raf.bolnica1.employees.serviceTest.EmployeeServiceTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJwZXJtaXNzaW9ucyI6WyJST0xFX0FETUlOIiwiUk9MRV9NRURfU0VTVFJBIl0sInN1YiI6ImpvaG5kb2UiLCJpYXQiOjE2Nzg5MTc2NzAsImV4cCI6MTY3ODkyODQ3MH0.78j1gPczrKO_OSKPeL6dlW66m-gibLPFCJNyuh3zJgs";

    @Test
    @WithMockUser(username = "johndoe", password = "password1", roles = {"ROLE_ADMIN"})
    public void testCreateEmployeeWithAdminAuthority() throws Exception {
        EmployeeCreateDto dto = EmployeeServiceTest.createEmployeeCreateDto();
        String requestBody = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/employees")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

}
