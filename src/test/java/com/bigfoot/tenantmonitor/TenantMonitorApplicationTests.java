package com.bigfoot.tenantmonitor;

import com.bigfoot.tenantmonitor.dto.AccessTokenDTO;
import com.bigfoot.tenantmonitor.dto.TokenDTO;
import com.bigfoot.tenantmonitor.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.json.JsonMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class TenantMonitorApplicationTests {

    @Autowired
    private JsonMapper jsonMapper;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Test
    void contextLoads() {
    }

    @Test
    void tokenResponsesRoundTripWithJackson3() {
        TokenDTO tokens = TokenDTO.builder()
                .withAccessToken("access")
                .withAccessTokenExpire(900000)
                .withRefreshToken("refresh")
                .withRefreshTokenExpire(3600000)
                .build();
        TokenDTO decoded = jsonMapper.readValue(jsonMapper.writeValueAsString(tokens), TokenDTO.class);
        assertThat(decoded).usingRecursiveComparison().isEqualTo(tokens);

        AccessTokenDTO access = AccessTokenDTO.builder()
                .withAccessToken("access")
                .withAccessTokenExpire(900000)
                .build();
        AccessTokenDTO decodedAccess = jsonMapper.readValue(jsonMapper.writeValueAsString(access), AccessTokenDTO.class);
        assertThat(decodedAccess).usingRecursiveComparison().isEqualTo(access);
    }

    @Test
    void existingJwtLibraryWorksAlongsideJackson3() {
        var user = User.withUsername("upgrade-test").password("unused").authorities("OWNER").build();
        String token = jwtService.generateToken(user, 60000);
        assertThat(jwtService.isValidToken(token)).isTrue();
        assertThat(jwtService.getUserName(token)).isEqualTo("upgrade-test");
    }

    @Test
    void openApiRemainsPublicAndPropertyApiRequiresAuthentication() throws Exception {
        var mvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();
        mvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paths['/api/v1/property']").exists());
        mvc.perform(get("/api/v1/property"))
                .andExpect(status().isForbidden());
    }
}
