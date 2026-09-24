import com.fasterxml.jackson.annotation.JsonInclude;

public class Post {

    private int userId;
    private String title;
    private String body;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int id;

    public Post() {
        this.userId = 0;
        this.title = "";
        this.body = "";
        this.id = 0;
    }

    public Post(int userId, String title, String body, int id) {
        this.userId = userId;
        this.title = title;
        this.body = body;
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Post [" +
                "userId=" + userId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", id=" + id +
                ']';
    }
}
