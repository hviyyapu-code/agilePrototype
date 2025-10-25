package com.example;

import io.javalin.Javalin;
import java.sql.*;
import java.util.*;

public class App {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/agiletool";
        String username = "root";
        String password = "";

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to MySQL successfully");

            Javalin app = Javalin.create(config -> {
                config.http.defaultContentType = "application/json";
                config.plugins.enableCors(cors -> cors.add(it -> it.anyHost()));
            }).start(8080);

            System.out.println("Server running at http://localhost:8080");

            app.get("/api/stories", ctx -> {
                List<Map<String, Object>> stories = new ArrayList<>();
                String sql = "SELECT * FROM user_story";
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        Map<String, Object> story = new HashMap<>();
                        story.put("id", rs.getInt("id"));
                        story.put("title", rs.getString("title"));
                        story.put("description", rs.getString("description"));
                        story.put("status", rs.getString("status"));
                        stories.add(story);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                ctx.json(stories);
            });

            app.post("/api/stories", ctx -> {
                Map<String, String> body = ctx.bodyAsClass(Map.class);
                String title = body.get("title");
                String description = body.get("description");

                if (title == null || description == null || title.isBlank() || description.isBlank()) {
                    ctx.status(400).result("Missing title or description");
                    return;
                }

                String sql = "INSERT INTO user_story (title, description) VALUES (?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, title);
                    ps.setString(2, description);
                    ps.executeUpdate();
                    ctx.status(201).result("User story created successfully");
                } catch (SQLException e) {
                    e.printStackTrace();
                    ctx.status(500).result("Database error");
                }
            });

        } catch (Exception e) {
            System.out.println("Error starting app:");
            e.printStackTrace();
        }
    }
}
