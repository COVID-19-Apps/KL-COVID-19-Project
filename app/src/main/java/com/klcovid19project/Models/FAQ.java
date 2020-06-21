package com.klcovid19project.Models;

public class FAQ {

    String question, answer;

    public FAQ(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public FAQ() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
