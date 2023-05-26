package com.example.thewarden;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.*;
import java.io.*;

public class Card extends Sprite{
    public String name;

    public Card(double x, double y, Rect frame,Bitmap bitmap,String name) {
        super(x, y,frame,bitmap);
        this.name=name;
    }
    /*
    public List<List<Integer[]>> diff(Card CardCurr,Card CuggCarr, Integer[] Deck1,Integer[] Deck2,Integer[] DeckTotal, Integer[] DeckUsed,Integer Turn,Integer Score1,Integer Score2)
    {
        List<List<Integer[]>> RetList=new List<List<Integer[]>>();
        return RetList;
    }
    */
}
