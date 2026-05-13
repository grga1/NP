// Да се имплементира класа Post во која ќе се чуваат информациите за објава на една социјална мрежа. Во класата да се имплементираат следните методи:

// Конструктор Post(String username, String postContent)
// void addComment (String username, String commentId, String content, String replyToId) - метод за додавање на коментар со ИД commentId и содржина content од корисникот со корисничко име username. Коментарот може да биде директно на самата објава (replyToId=null во таа ситуација) или да биде reply на веќе постоечки коментар/reply. **
// void likeComment (String commentId) - метод за лајкнување на коментар.
// String toString() - toString репрезентација на една објава во форматот прикажан подолу. Коментарите се листаат во опаѓачки редослед според бројот на лајкови (во вкупниот број на лајкови се сметаат и лајковите на replies на коментарите, како и на replies na replies итн.)
// ** Решенијата кои ќе овозможат само коментари на објавата ќе бидат оценети со 50% од поените. Истото тоа е рефлектирано во тест примерите (50% од тест примерите се со коментари само на објавата, 50% се со вгнездени коментари и replies)

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class PostTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String postAuthor = sc.nextLine();
        String postContent = sc.nextLine();

        Post p = new Post(postAuthor, postContent);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(";");
            String testCase = parts[0];

            if (testCase.equals("addComment")) {
                String author = parts[1];
                String id = parts[2];
                String content = parts[3];
                String replyToId = null;
                if (parts.length == 5) {
                    replyToId = parts[4];
                }
                p.addComment(author, id, content, replyToId);
            } else if (testCase.equals("likes")) { //likes;1;2;3;4;1;1;1;1;1 example
                for (int i = 1; i < parts.length; i++) {
                    p.likeComment(parts[i]);
                }
            } else {
                System.out.println(p);
            }

        }
    }
}

class Comment{
    String username;
    String commentId;
    String content;
    int likes;
    List<Comment> replies;
    boolean isReply;

    public Comment(String username, String commentId, String content) {
        this.username = username;
        this.commentId = commentId;
        this.content = content;
        this.likes = 0;
        this.replies = new ArrayList<>();
       this.isReply=false;
    }

    int getTotalLikes(){
      int total = likes;
      for(Comment r : replies){
          total+=r.getTotalLikes();
      }
      return total;
    }


    public String toString(int level) {
        StringBuilder sb = new StringBuilder();
        String prostor = "    ".repeat(level+1);

            sb.append(prostor).append("Comment: ").append(content).append("\n");
            sb.append(prostor).append("Written by: ").append(username).append("\n");
            sb.append(prostor).append("Likes: ").append(likes).append("\n");

            replies.stream().sorted(Comparator.comparing(Comment::getTotalLikes).reversed())
                    .forEach(r->sb.append(r.toString(level+1)));
        return sb.toString();
    }
}
class Post{
    String username;
    String postContent;
    Map<String,Comment> mapa;

    Post(String username, String postContent){
        this.username = username;
        this.postContent =postContent;
        this.mapa = new TreeMap<>();
    }
    void addComment (String username, String commentId, String content, String replyToId){
      Comment c = new Comment(username, commentId, content);
      mapa.put(commentId,c);
      if (replyToId!=null){
          c.isReply=true;
          mapa.get(replyToId).replies.add(c);
      }
    }
    void likeComment (String commentId){
       mapa.get(commentId).likes++;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Post: ").append(postContent).append("\n");
        sb.append("Written by: ").append(username).append("\n");
        sb.append("Comments: ").append("\n");
        mapa.values().stream().filter(x->!x.isReply).sorted(Comparator.comparing(Comment::getTotalLikes).reversed()).forEach(s->sb.append(s.toString(1)));
        return sb.toString();
    }
}
