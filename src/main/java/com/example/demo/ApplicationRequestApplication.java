package com.example.demo;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootApplication
public class ApplicationRequestApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ApplicationRequestApplication.class, args);
        ExecutorService execute = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; ++i) {
            execute.execute(new MyTask());
        }
        execute.shutdown();
    }
}

class MyTask implements Runnable {
    public void run() {
        execHttpRequest();
    }

    private void execHttpRequest() {
        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
        try {
            httpclient.start();
            HttpGet request = new HttpGet("http://localhost:8080/Servlet");
            for (int i = 0; i < 250; i++) {
                Future<HttpResponse> future = httpclient.execute(request, null);
                HttpResponse response = future.get();
                System.out.println("Response: " + response.getStatusLine());
                System.out.println("Shutting down");
//				System.out.println(Thread.currentThread().getName());
            }
            httpclient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done");
    }
}