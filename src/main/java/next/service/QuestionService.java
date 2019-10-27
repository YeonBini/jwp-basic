package next.service;

import core.jdbc.CannotDeleteException;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;

import java.util.List;

public class QuestionService {
    private QuestionDao questionDao = new QuestionDao();
    private AnswerDao answerDao = new AnswerDao();

    private static QuestionService questionService;

    private QuestionService() {
    }

    public static QuestionService getInstance() {
        if(questionService == null) {
            questionService = new QuestionService();
        }
        return questionService;
    }

    public Question findById(long questionId) {
        return questionDao.findById(questionId);
    }

    public List<Answer> findAllByQuestionId(long questionId) {
        return answerDao.findAllByQuestionId(questionId);
    }

    public void deleteQuestion(long questionId, User user) throws CannotDeleteException {
        Question question = questionDao.findById(questionId);
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);

        if(!user.getName().equals(question.getWriter())) {
            throw new CannotDeleteException("다른 사람에 의해 작성된 글입니다.");
        }

        if(answers.size() > 0) {
            for(Answer ans : answers) {
                if(!ans.getWriter().equals(question.getWriter())) {
                    throw new CannotDeleteException("다른 사용자가 댓글을 단 질문은 삭제할 수 없습니다.");
                }
            }
        }

        boolean isQuestionDeleted = questionDao.deleteQuestion(questionId);

        if(isQuestionDeleted) {
            for(Answer ans :answers) {
                answerDao.delete(ans.getAnswerId());
            }
        }
    }




}
