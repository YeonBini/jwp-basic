package next.model;

import java.util.Date;

public class Answer {
    private long answerId;
    private String writer;
    private String contents;
    private Date createdDate;
    private long questionId;

    public Answer(String writer, String contents, Date createdDate, long questionId) {
        this.writer = writer;
        this.contents = contents;
        this.createdDate = createdDate;
        this.questionId = questionId;
    }

    public Answer(long answerId, String writer, String contents, Date createdDate, long questionId) {
        this.answerId = answerId;
        this.writer = writer;
        this.contents = contents;
        this.createdDate = createdDate;
        this.questionId = questionId;
    }

    public long getAnswerId() {
        return answerId;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public long getTimeForCreatedDate() {
        return this.createdDate.getTime();
    }

    public long getQuestionId() {
        return questionId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (answerId ^ (answerId >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Answer) {
            Answer ans = (Answer) obj;
            if (ans.getAnswerId() == this.answerId) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", writer='" + writer + '\'' +
                ", contents='" + contents + '\'' +
                ", createdDate=" + createdDate +
                ", questionId=" + questionId +
                '}';
    }
}
