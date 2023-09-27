package com.aec.store.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Basic representation of the User with output data.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Basic representation of the User with output data.")
public class UserBasicDto {

	/**
	 * Code Identifier of the entity User.
	 */
	@Schema(description = "Id of the User entity.", example = "528f22c3-1f9c-493f-8334-c70b83b5b885")
	private String id;

	/**
	 * The first name the user has.
	 */
	@Schema(description = "The first name the user has.", example = "Jhon")
	private String firstName;

	/**
	 * The last name the user has.
	 */
	@Schema(description = "The last name the user has.", example = "Doe")
	private String lastName;

	/**
	 * The email the user has.
	 */
	@Schema(description = "The email the user has.", example = "jhondoe@mail.com")
	private String email;

	/**
	 * The role name the user has.
	 */
	@Schema(description = "The role name the user has.", example = "USER")
	private String role;
}
