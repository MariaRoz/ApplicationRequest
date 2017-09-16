package com.example.demo;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.Future;

@SpringBootApplication
public class ApplicationRequestApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ApplicationRequestApplication.class, args);
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		try {
			httpclient.start();
			HttpGet request = new HttpGet("http://localhost:8080/Servlet");
//			ExecutorService exec = Executors.newFixedThreadPool(4);
			for (int i = 0; i <1000 ; i++) {
						Future<HttpResponse> future = httpclient.execute(request, null);
						HttpResponse response = future.get();
						System.out.println("Response: " + response.getStatusLine());
						System.out.println("Shutting down");
					}

		} finally {
			httpclient.close();
		}
		System.out.println("Done");
	}
}


