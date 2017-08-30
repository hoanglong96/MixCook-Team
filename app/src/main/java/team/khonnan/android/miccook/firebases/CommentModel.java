package team.khonnan.android.miccook.firebases;

/**
 * Created by tungthanh.1497 on 08/29/2017.
 */

public class CommentModel {
    String comment;
    String idUser;
    String time;
    String name;

//    public CommentModel() {
//    }
//
//    @Override
//    public String toString() {
//        return "CommentModel{" +
//                "comment='" + comment + '\'' +
//                ", idUser='" + idUser + '\'' +
//                ", time='" + time + '\'' +
//                '}';
//    }
//
//    public CommentModel(String comment, String idUser) {
//        this.comment = comment;
//        this.idUser = idUser;
//        this.time = "meo meo";
//    }
//
//    public String getComment() {
//
//        return comment;
//    }
//
//    public void setComment(String comment) {
//        this.comment = comment;
//    }
//
//    public String getIdUser() {
//        return idUser;
//    }
//
//    public void setIdUser(String idUser) {
//        this.idUser = idUser;
//    }
//
//    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }


    public CommentModel() {
    }

    public CommentModel(String comment, String idUser, String name) {
        this.comment = comment;
        this.idUser = idUser;
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CommentModel{" +
                "comment='" + comment + '\'' +
                ", idUser='" + idUser + '\'' +
                ", time='" + time + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
