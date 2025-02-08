package com.six.service;

public interface IguessNumService {

    String guessNum(Integer num);

    void reset();

    String getHint();

    int getNumGuesses();
}
