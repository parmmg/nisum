package com.nisum.challenge.infraestructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.HttpStatusCode;

@Data
@Getter
@Setter
public class Response {

	@JsonProperty("data")
	private Object data;
	@JsonProperty("type")
	private String type;
	@JsonProperty("message")
	private String message;
	@JsonProperty("code")
	private HttpStatusCode code;

	public Response(HttpStatusCode code) {
		this.code = code;
	}

}
