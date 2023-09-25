package com.aec.store.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Basic representation of the User with output data.")
public class UserBasicDto {

	@Schema(description = "Id of the testimonials entity.", example = "528f22c3-1f9c-493f-8334-c70b83b5b885")
	private String id;

	@Schema(description = "The first name the user has.", example = "Jhon")
	private String firstName;

	@Schema(description = "The last name the user has.", example = "Doe")
	private String lastName;

	@Schema(description = "The email the user has.", example = "jhondoe@mail.com")
	private String email;

	@Schema(description = "The rol name the user has.", example = "USER")
	private String role;

}
