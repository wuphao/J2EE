package com.six.controller;

import com.six.service.IguessNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class guessNumController {

    @Autowired
    private IguessNumService guessNumService;

    @GetMapping("/guess")
    public String getGuessNum(Integer guess){
        String ans=guessNumService.guessNum(guess);
        if(ans.charAt(0)=='恭') {
            return success();
        } else {
            return fail();
        }


    }

    private String fail() {
        String line1 = "<p>Good guess, but nope. Try " + guessNumService.getHint() + "</p >";
        String line2 = "<p>You have made " + guessNumService.getNumGuesses() + " guesses.</p >";
        String line3 = "<p>I'm thinking of a number between 1 and 100.</p >";
        String line4 = "        <p>What's your guess?</p >\n" +
                "        <form action=\"http://localhost:8080/guess\">\n" +
                "            <label>\n" +
                "                <input type=\"text\" name=\"guess\">\n" +
                "                <input type=\"submit\" value=\"Submit\">\n" +
                "            </label>\n" +
                "        </form>";

        return line1 + line2 + line3 + line4;
    }

    private String success() {
        String suc1 = "<p>Congratulations!  You got it. And after just " + guessNumService.getNumGuesses() + " tries.</p >";
        String suc2 = "Care to <a href = 'guess.html'>try again</ a>?";
        guessNumService.reset();
        return suc1 + suc2;
    }



}
