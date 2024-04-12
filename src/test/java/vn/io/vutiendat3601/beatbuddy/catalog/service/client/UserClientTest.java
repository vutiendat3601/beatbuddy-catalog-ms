package vn.io.vutiendat3601.beatbuddy.catalog.service.client;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import vn.io.vutiendat3601.beatbuddy.catalog.util.ProfilesResolver;

@ActiveProfiles(resolver = ProfilesResolver.class)
@SpringBootTest
public class UserClientTest {
}
