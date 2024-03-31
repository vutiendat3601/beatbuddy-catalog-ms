package vn.io.vutiendat3601.beatbuddy.catalog.config;

import static vn.io.vutiendat3601.beatbuddy.catalog.constant.GlobalConstant.BEAT_BUDDY;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
            title = BEAT_BUDDY, version = "1.0.0",
            description = "BeatBuddy Catalog APIs"
        )

)
public class OpenApiConfig {
}
