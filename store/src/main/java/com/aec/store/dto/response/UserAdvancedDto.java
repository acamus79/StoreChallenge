package com.aec.store.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Detailed representation of the user with output data.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Detailed representation of the user with output data.")
public class UserAdvancedDto {

    /**
     * Code Identifier of the entity User.
     */
    @Schema(description = "Id of the user entity.", example = "528f22c3-1f9c-493f-8334-c70b83b5b885")
    private String id;

    /**
     * The first name the user has.
     */
    @Schema(description = "The first name the user has.", example = "John")
    private String firstName;

    /**
     * The last name the user has.
     */
    @Schema(description = "The last name the user has.", example = "Doe")
    private String lastName;

    /**
     * The email the user has.
     */
    @Schema(description = "The email the user has.", example = "johndoe@mail.com")
    private String email;

    /**
     * The role name the user has.
     */
    @Schema(description = "The role name the user has.", example = "USER")
    private String role;

    /**
     * The creation date of the user.
     */
    @Schema(description = "The creation date of the user.", example = "2023-09-23T12:34:56")
    @JsonProperty("created_at")
    private String createdAt;

    /**
     * The last update date of the user.
     */
    @Schema(description = "The last update date of the user.", example = "2023-09-23T14:45:30")
    @JsonProperty("updated_at")
    private String updatedAt;

    /**
     * The active status of the user.
     */
    @Schema(description = "The active status of the user.", example = "true")
    private Boolean active;
}
