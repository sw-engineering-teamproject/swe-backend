package swe.issue.external;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swe.issue.application.AssigneeRecommender;
import swe.issue.domain.Issue;
import swe.project.domain.Project;
import swe.user.domain.User;
import swe.user.domain.UserRepository;

@Component
public class InformationRetrievalAssigneeRecommender implements AssigneeRecommender {

  @Autowired
  private UserRepository userRepository;

  @Override
  public List<User> recommend(final Long projectId, final Issue issue) {
    List<User> recommendedUsers = new ArrayList<>();
    String title = issue.getTitle();
    String description = issue.getDescription();
    try{
      title = URLEncoder.encode(issue.getTitle(), StandardCharsets.UTF_8.toString());
      description = URLEncoder.encode(issue.getDescription(),
          StandardCharsets.UTF_8.toString());
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    try {
      URL url = new URL(
          "https://swe2.dongwoo.win/assignee/?title=" + title + "&description=" + description);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      con.setRequestProperty("Accept", "application/json");

      StringBuilder response = new StringBuilder();
      try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
        String line;
        while ((line = br.readLine()) != null) {
          response.append(line);
        }
      }

      JSONObject jsonObject = new JSONObject(response.toString());
      JSONArray assignees = jsonObject.getJSONArray("assignees");

      for (int i = 0; i < assignees.length(); i++) {
        Long userId = Long.parseLong(assignees.getString(i));
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
          recommendedUsers.add(user);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return recommendedUsers;
  }

  @Override
  public void addDataToRecommender(final List<Issue> issues) {
    for (Issue issue : issues) {
      try {
        URL url = new URL("http://0.0.0.0:8000/issue/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        String Title = issue.getTitle();
        String Description = issue.getDescription();
        String assigneeId = issue.getAssignee()
            .map(user -> user.getId().toString())
            .orElseThrow(() -> new NoSuchElementException("Assignee is not present"));
        String fixerId = issue.getFixer()
            .map(user -> user.getId().toString())
            .orElseThrow(() -> new NoSuchElementException("Fixer is not present"));

        String jsonInputString = String.format(
            "{\"title\": \"%s\", \"description\": \"%s\", \"assignee_id\": \"%s\", \"fixer_id\": \"%s\"}",
            Title, Description, assigneeId, fixerId);

        try (OutputStream os = con.getOutputStream()) {
          byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
          os.write(input, 0, input.length);
        }

        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);

        if (responseCode != HttpURLConnection.HTTP_OK) {
          System.out.println("Request to add issue failed: " + responseCode);
          throw new RuntimeException("Request to add issue failed: " + responseCode);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
