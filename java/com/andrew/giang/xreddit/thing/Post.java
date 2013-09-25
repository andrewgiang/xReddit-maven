package com.andrew.giang.xreddit.thing;

public class Post {
    public String id, name;
    public String title, url, author, domain, subreddit, subreddit_id;
    public int num_comments, score, ups, downs;
    public boolean over_18, hidden, saved, is_self, clicked;
    public Object edited;
    public Boolean likes;

    public long created, created_utc;

    public String selftext, permalink, link_flair_text, author_flair_text, selftext_html;
    public String thumbnail;


    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", domain='" + domain + '\'' +
                ", subreddit='" + subreddit + '\'' +
                ", subreddit_id='" + subreddit_id + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", created=" + created +
                ", created_utc=" + created_utc +
                ", score=" + score +
                ", downs=" + downs +
                ", ups=" + ups +
                ", num_comments=" + num_comments +
                ", over_18=" + over_18 +
                ", edited=" + edited +
                ", is_self=" + is_self +
                ", saved=" + saved +
                ", clicked=" + clicked +
                ", hidden=" + hidden +
                ", selftext='" + selftext + '\'' +
                ", permalink='" + permalink + '\'' +
                ", link_flair_text='" + link_flair_text + '\'' +
                ", author_flair_text='" + author_flair_text + '\'' +
                '}';
    }
}
