/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package posts;

/**
 *
 * @author snk
 */
public class Posts {

    private int post_id;
    private int post_user_id;
    private String post_content;
    private int post_sigma_like;
    private int post_sigma_comment;
    private String post_date;

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getPost_sigma_comment() {
        return post_sigma_comment;
    }

    public void setPost_sigma_comment(int post_sigma_comment) {
        this.post_sigma_comment = post_sigma_comment;
    }

    public int getPost_sigma_like() {
        return post_sigma_like;
    }

    public void setPost_sigma_like(int post_sigma_like) {
        this.post_sigma_like = post_sigma_like;
    }

    public int getPost_user_id() {
        return post_user_id;
    }

    public void setPost_user_id(int post_user_id) {
        this.post_user_id = post_user_id;
    }
    
}
