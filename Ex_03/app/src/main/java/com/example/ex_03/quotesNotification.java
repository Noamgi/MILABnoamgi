package com.example.ex_03;

import java.util.ArrayList;
import java.util.List;

public class quotesNotification {

    private static List <String> quotesList = fillTheList();

    private static List<String> fillTheList() {
        List <String> QuotesList = new ArrayList<>();
        QuotesList.add("“To the world you may be one person, but to one person you are the world.” – Bill Wilson");
        QuotesList.add("“Love takes off masks that we fear we cannot live without and know we cannot live within.”— James Baldwin");
        QuotesList.add("“Love yourself first and everything else falls into line. You really have to love yourself to get anything done in this world.”— Lucille Ball");
        QuotesList.add("“I love you and that’s the beginning and end of everything.” – F. Scott Fitzgerald");
        QuotesList.add("“The most important thing in life is to learn how to give out love, and to let it come in.” — Morrie Schwartz");
        QuotesList.add("“I love you not only for what you are, but for what I am when I am with you.” – Roy Croft");
        QuotesList.add("“We are shaped and fashioned by what we love.”— Johann Wolfgang von Goethe");
        QuotesList.add("“You are every reason, every hope and every dream I’ve ever had.” – Nicolas Sparks");
        QuotesList.add("“Love is of all passions the strongest, for it attacks simultaneously the head, the heart, and the senses.”— Lao Tzu");
        QuotesList.add(" “When we are in love we seem to ourselves quite different from what we were before.”— Blaise Pascal");
        return QuotesList;
    }

    public static String getQuote(){
        int randomNum = (int) ((Math.random() * 10) + 1);
        String quote = quotesList.get(randomNum);
        return quote;
    }
}
