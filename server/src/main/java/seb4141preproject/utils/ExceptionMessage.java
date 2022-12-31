package seb4141preproject.utils;

public enum ExceptionMessage {
    MEMBER_NOT_FOUND("존재하지 않는 회원입니다."),
    MEMBER_EMAIL_DUPLICATES("이미 가입된 회원입니다."),
    QUESTION_NOT_FOUND("질문을 찾을 수 없습니다."),
    ANSWER_NOT_FOUND("답변을 찾을 수 없습니다."),
    VOTE_NOT_FOUND("투표 기록을 찾을 수 없습니다.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }
}
