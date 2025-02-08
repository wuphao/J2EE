package com.six.service.Impl;

import com.six.service.IguessNumService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Random;

@Service
public class guessNumServiceImpl implements IguessNumService, Serializable {

    private static final long serialVersionUID = 1L;

    private int answer= (int) (Math.random()*100+1);
    private String hint;
    private int numGuesses;
    private boolean success;
    private final Random random = new Random();

    public void GuessNumServiceImpl() {
        reset();
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getHint() {
        return "" + hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setNumGuesses(int numGuesses) {
        this.numGuesses = numGuesses;
    }

    public int getNumGuesses() {
        return numGuesses;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setGuess(String guess) {
        System.out.println(answer);
        numGuesses++;

        int g;
        try {
            g = Integer.parseInt(guess);
        } catch (NumberFormatException e) {
            g = -1;
        }

        if (g == answer) {
            success = true;
        } else if (g == -1) {
            hint = "a number next time";
        } else if (g < answer) {
            hint = "higher";
        } else if (g > answer) {
            hint = "lower";
        }
    }

    public void reset() {
        answer = Math.abs(random.nextInt() % 100) + 1;
        success = false;
        numGuesses = 0;
    }
    @Override
    public String guessNum(Integer num){
        setGuess(Integer.toString(num));
        return success?"恭喜你猜对了,用了"+numGuesses+"次":"try "+getHint();
    }
}
