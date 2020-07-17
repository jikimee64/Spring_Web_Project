package kr.or.ns.controller;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;

public JsonNode Logout(String autorize_code) {
	final String RequestUrl = "https://kapi.kakao.com/v1/user/logout";

	final HttpClient client = HttpClientBuilder.create().build();

	final HttpPost post = new HttpPost(RequestUrl);

	post.addHeader("Authorization", "Bearer " + autorize_code);

	JsonNode returnNode = null;

	try {

		final HttpResponse response = client.execute(post);

		ObjectMapper mapper = new ObjectMapper();

		returnNode = mapper.readTree(response.getEntity().getContent());

	} catch (UnsupportedEncodingException e) {

		e.printStackTrace();

	} catch (ClientProtocolException e) {

		e.printStackTrace();

	} catch (IOException e) {

		e.printStackTrace();

	} finally {

	}

	return returnNode;

}
