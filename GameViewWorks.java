package com.example.thewarden;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;

import java.util.*;
import java.io.*;


public class GameView extends View
{
    public static String[] Cards = new String[] { "Strip1", "Strip2", "Strip3", "Strip4", "Strip12", "Strip13", "Strip14", "Strip23", "Strip24", "Strip34", "Strip123", "Strip124", "Strip134", "Strip234", "Strip1234", "SSquare", "MSquare", "BSquare", "MRound", "BRound", "MTriangle", "BTriangle", "MOctagon", "BOctagon", "Dot", "Arrow", "Curve", "Star" };
    public static Integer[] Total = new Integer[] { 12,12,12,12,4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 2, 16, 3, 3, 3, 3, 3, 3, 3, 3, 16, 24, 24, 1 };
    public static Integer[] Play1 = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0,  0,  0,  0 };
    public static Integer[] Play2 = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };
    public static Integer[] UsedC = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0 };
    public static Integer[] Inters = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    public static List<Integer> Score1 = new ArrayList<Integer>();
    public static List<Integer> Score2 = new ArrayList<Integer>();
    public static List<Sprite> CardSprites  = new ArrayList<Sprite>();
    public static List<Sprite> RotCardSprites  = new ArrayList<Sprite>();
    public static boolean isPaused = false;
    public static boolean isFinished = false;
    public static boolean isRoundEnded = false;
    public static volatile boolean  Success = false;
    public static volatile boolean  DOPSuccess = false;
    public static boolean IsTurnEnded = false;
    public static boolean NeedToRotate = false;

    public static int MODE = 0;
    public static Random MyRandom = new Random();

    public static String CurrCard = "";
    public static String DOPCHOOSE = "";

    public static Integer S1,S2;
    public static int Rot;

    public static List<String> CurrC=new ArrayList<String>();
    public static List<Integer> Intersected = new ArrayList<Integer>();

    public Bitmap RevBit = BitmapFactory.decodeResource(getResources(), R.drawable.revers);
    Rect firstFrame = new Rect(0, 0, RevBit.getWidth(), RevBit.getHeight());
    public Sprite Revers = new Sprite(0, 0,firstFrame, RevBit);


    public Sprite CurrSprite=Revers;



    public Sprite pauseIcon;
    public Sprite pauseText;
    public Sprite failText;



    public Sprite Strip1;
    public Sprite Strip2;
    public Sprite Strip3;
    public Sprite Strip4;
    public Sprite Strip12;
    public Sprite Strip13;
    public Sprite Strip14;
    public Sprite Strip23;
    public Sprite Strip24;
    public Sprite Strip34;
    public Sprite Strip123;
    public Sprite Strip124;
    public Sprite Strip134;
    public Sprite Strip234;
    public Sprite Strip1234;
    public Sprite SSquare;
    public Sprite MSquare;
    public Sprite BSquare;
    public Sprite MRound;
    public Sprite BRound;
    public Sprite MTriangle;
    public Sprite BTriangle;
    public Sprite MOctagon;
    public Sprite BOctagon;
    public Sprite Dot;
    public Sprite Arrow;
    public Sprite Curve;
    public Sprite Star;

    public static Sprite DoRotate;
    public static Sprite DoNotate;

    public Sprite RotStrip1;
    public Sprite RotStrip2;
    public Sprite RotStrip3;
    public Sprite RotStrip4;
    public Sprite RotStrip12;
    public Sprite RotStrip13;
    public Sprite RotStrip14;
    public Sprite RotStrip23;
    public Sprite RotStrip24;
    public Sprite RotStrip34;
    public Sprite RotStrip123;
    public Sprite RotStrip124;
    public Sprite RotStrip134;
    public Sprite RotStrip234;
    public Sprite RotStrip1234;
    public Sprite TurnArrow1;
    public Sprite TurnArrow2;
    public static int viewWidth;
    public static int HandWidth;
    public static int viewHeight;


    private int[] lvlColor = {250, 250, 250, 255}; //{250, 127, 188, 255}

    private final int timerInterval = 30;

    Paint paint = new Paint();
    Path path = new Path();
    //public GameView(Context context)
    public GameView(Context context,int HGT,int WDT)
    {
        super(context);
        viewHeight=HGT;
        viewWidth=WDT;
        HandWidth=viewWidth*9/10;
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.rot);
        Rect firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        DoRotate = new Sprite(0, 0,firstFrame,b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.not);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        DoNotate = new Sprite(0, 0,firstFrame,b);


        b = BitmapFactory.decodeResource(getResources(), R.drawable.pauseicon);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        pauseIcon = new Sprite(0, 0,firstFrame,b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.pauseicon);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        pauseText = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.game_over);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        failText = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.turnarrow1);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        TurnArrow1 = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.turnarrow2);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        TurnArrow2 = new Sprite(0, 0,firstFrame, b);


        b = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        Arrow = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.boctagon);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        BOctagon = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.bround);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        BRound = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.bsquare);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        BSquare = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.btriangle);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        BTriangle = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.curve);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        Curve = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.dot);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        Dot = new Sprite(0, 0, firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.moctagon);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        MOctagon = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.mround);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        MRound = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.msquare);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        MSquare = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.mtriangle);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        MTriangle = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.ssquare);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        SSquare = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.star);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        Star = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.strip1);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        Strip1 = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.strip12);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        Strip12 = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.strip123);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        Strip123 = new Sprite(0, 0,firstFrame,  b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.strip1234);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        Strip1234 = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.strip124);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        Strip124 = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.strip13);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        Strip13 = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.strip134);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        Strip134 = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.strip14);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        Strip14 = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.strip2);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        Strip2 = new Sprite(0, 0,firstFrame,  b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.strip23);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        Strip23 = new Sprite(0, 0, firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.strip234);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        Strip234 = new Sprite(0, 0, firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.strip24);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        Strip24 = new Sprite(0, 0, firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.strip4);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        Strip4 = new Sprite(0, 0, firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.strip34);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        Strip34 = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.strip4);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        Strip4 = new Sprite(0, 0, firstFrame,b);


        //###############################
        b = BitmapFactory.decodeResource(getResources(), R.drawable.rotstrip1);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        RotStrip1 = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.rotstrip12);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        RotStrip12 = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.rotstrip123);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        RotStrip123 = new Sprite(0, 0,firstFrame,  b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.rotstrip1234);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        RotStrip1234 = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.rotstrip124);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        RotStrip124 = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.rotstrip13);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        RotStrip13 = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.rotstrip134);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        RotStrip134 = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.rotstrip14);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        RotStrip14 = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.rotstrip2);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        RotStrip2 = new Sprite(0, 0,firstFrame,  b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.rotstrip23);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        RotStrip23 = new Sprite(0, 0, firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.rotstrip234);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        RotStrip234 = new Sprite(0, 0, firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.rotstrip24);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        RotStrip24 = new Sprite(0, 0, firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.rotstrip4);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        RotStrip4 = new Sprite(0, 0, firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.rotstrip34);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        RotStrip34 = new Sprite(0, 0,firstFrame, b);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.rotstrip4);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        RotStrip4 = new Sprite(0, 0, firstFrame,b);


        CardSprites.add(Strip1);
        CardSprites.add(Strip2);
        CardSprites.add(Strip3);
        CardSprites.add(Strip4);
        CardSprites.add(Strip12);
        CardSprites.add(Strip13);
        CardSprites.add(Strip14);
        CardSprites.add(Strip23);
        CardSprites.add(Strip24);
        CardSprites.add(Strip34);
        CardSprites.add(Strip123);
        CardSprites.add(Strip124);
        CardSprites.add(Strip134);
        CardSprites.add(Strip234);
        CardSprites.add(Strip1234);
        CardSprites.add(SSquare);
        CardSprites.add(MSquare);
        CardSprites.add(BSquare);
        CardSprites.add(MRound);
        CardSprites.add(BRound);
        CardSprites.add(MTriangle);
        CardSprites.add(BTriangle);
        CardSprites.add(MOctagon);
        CardSprites.add(BOctagon);
        CardSprites.add(Dot);
        CardSprites.add(Arrow);
        CardSprites.add(Curve);
        CardSprites.add(Star);



        RotCardSprites.add(RotStrip1);
        RotCardSprites.add(RotStrip2);
        RotCardSprites.add(RotStrip3);
        RotCardSprites.add(RotStrip4);
        RotCardSprites.add(RotStrip12);
        RotCardSprites.add(RotStrip13);
        RotCardSprites.add(RotStrip14);
        RotCardSprites.add(RotStrip23);
        RotCardSprites.add(RotStrip24);
        RotCardSprites.add(RotStrip34);
        RotCardSprites.add(RotStrip123);
        RotCardSprites.add(RotStrip124);
        RotCardSprites.add(RotStrip134);
        RotCardSprites.add(RotStrip234);
        RotCardSprites.add(RotStrip1234);




        gameplay();
        System.out.println("######"+CurrCard);
        Timer t = new Timer();
        t.start();
    }
    public static void Generate(int player)
    {
        int Gen;
        Gen = Cards.length - 1;
        while (Play1[Gen] + Play2[Gen] + UsedC[Gen] + 1 > Total[Gen])
            Gen = MyRandom.nextInt(Cards.length - 1);
        if (player==1) Play1[Gen] += 1;
        else if (player==2) Play2[Gen] += 1;
    }
    public static void Search(String CurrCard,int player)
    {
        for (int i = 0; i < Cards.length; i++)
        {
            if (Cards[i] == CurrCard)
            {
                if (player==1) Play1[i] -= 1;
                else Play2[i] -= 1;
                UsedC[i] += 1;
                break;
            }
        }
    }
    public static int SumHand(Integer[] player)
    {
        int count=0;
        for (int i = 0; i < player.length; i++)
            count+=player[i];
        return count;
    }
    public static void ROTorNOT(Canvas canvas)
    {
        int D=10,G=viewHeight*6/10;
        if (MODE==1)
        {
            DoRotate.setX(D);
            DoRotate.setY(G);
            DoNotate.setX(viewWidth - D - DoNotate.getFrameWidth()/2);
            DoNotate.setY(G);
            DoRotate.draw(canvas);
            DoNotate.draw(canvas);
        }
    }
    public static void ToRot(MotionEvent event)
    {
        System.out.println("NOW MODE ToRot: "+MODE);
        int D=10,G=viewHeight*6/10;
        int pointerId = event.getPointerId(0);
        int pointerIndex = event.findPointerIndex(pointerId);
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);
        if (event.getAction()==MotionEvent.ACTION_DOWN)
        {
            if ((y>=G)&&(y<=G+DoRotate.getFrameHeight()))
            {
                if ((x>=10)&&(x<=10+DoRotate.getFrameWidth()))
                {
                    System.out.println("NOW MODE AFTER ToRot CORRECT: "+MODE);
                    Rot=1;
                }
                else if ((x>=viewWidth-D-DoNotate.getFrameWidth()/2)&&(x<=viewWidth-D))
                {
                    System.out.println("NOW MODE AFTER ToRot CORRECT: "+MODE);
                    Rot=0;
                }
            }
            else
            {
                MODE=0;
                System.out.println("NOW MODE AFTER ToRot: "+MODE);

            }
        }


    }
    public void gameplay()
    {
        S1=0;
        S2=0;

        Intersected.clear();
        int i, j, Gen, Count1, Count2;
        int CountTwos = 0;
        int ENCAR1=SumHand(Play1),ENCAR2=SumHand(Play2);

        for (i = ENCAR1; i < 6; i++) Generate(1);
        for (i = ENCAR2; i < 6; i++) Generate(2);


        Gen = Cards.length - 1;
        int Attempts = 0;
        while ((Play1[Gen] + Play2[Gen] + UsedC[Gen] + 1 > Total[Gen]) && (Attempts < 100))
        {
            Gen = MyRandom.nextInt(16);
            Attempts += 1;
        }
        if (Attempts==100) System.out.println("No more cards to output");
        UsedC[Gen] += 1;
        CurrCard = Cards[Gen];
        CurrSprite=CardSprites.get(Gen);

        String choose;
        if (CurrCard.indexOf("Strip") == 0)
        {
            choose = CurrCard.replace("Strip", "");

            for (i = 0; i < choose.length(); i++)
            {
                String [] BS=new String[]{String.valueOf('1'),String.valueOf('2'),String.valueOf('3'),String.valueOf('4')};
                String A = String.valueOf(choose.charAt(i));
                for (int D=0;D<4;D++)
                {
                    if (A.equals(BS[D]))
                        for (j = 0; j < 4; j++) Inters[4 * j + D] = 1;
                }
            }
            Integer RoN = MyRandom.nextInt(2);
            if (RoN == 1)
            {
                for (i = 0; i < 4; i++)
                    for (j = 0; j < 4; j++)
                        if (i <= j)
                        {
                            int dop = Inters[4 * i + j] * 3;
                            Inters[4 * i + j] = Inters[4 * j + i] * 3;
                            Inters[4 * j + i] = dop;
                        }
                CurrSprite=RotCardSprites.get(Gen);
            }
        }

    }
    public static void IsPossibleTurn(int player)
    {
        int CountS = 0, CountL = 0, CountM = 0, CountB = 0, CountD = 0, CountA = 0, CountC = 0,i,j;
        for (i = 0; i < Cards.length; i++)
        {
            if ((i >= 0) && (i <= 14))
            {
                if (player==1) CountS += Play1[i];
                else CountS += Play2[i];
            }
            else if (i == 14)
            {
                if (player==1) CountL += Play1[i];
                else CountL += Play2[i];
            }
            else if ((i >= 16) && (i <= 19))
            {
                if (player==1) CountM += Play1[i];
                else CountM += Play2[i];
            }
            else if ((i >= 20) && (i <= 23))
            {
                if (player==1) CountB += Play1[i];
                else CountB += Play2[i];
            }
            else if (i == 24)
            {
                if (player==1) CountD += Play1[i];
                else CountD += Play1[i];
            }
            else if (i == 25)
            {
                if (player==1) CountA += Play1[i];
                else CountA += Play2[i];
            }
            else if (i == 26)
            {
                if (player==1) CountC += Play1[i];
                else CountC += Play2[i];
            }

        }
        if (CountS + CountL + CountM + CountB + CountD + CountA + CountC >21)
        {

            if (player==1) System.out.println("Game over. You lost.");
            else System.out.println("Game over. Bot lost.");
            System.exit(0);
        }
        else if (CountS + CountL + CountM + CountB + CountD + CountA + CountC == 0)
        {
            if (player==1) MODE = -1;
            else MODE = 0;
            System.out.println("No more cards.");
            if (player==1) Generate(1);
            else Generate(2);
        }
        else if ((CountS + CountL + CountB + CountD + CountA + CountC == 0) && ((CurrCard.indexOf('M') == 0) || (CurrCard.indexOf('B') == 0)))
        {
            if (player==1) MODE = -1;
            else MODE = 0;
            System.out.println("No turn.");
            if (player==1) Generate(1);
            else Generate(2);
        }
        else if ((CountS + CountL + CountM + CountD + CountA + CountC == 0) && ((CurrCard.equals("SSquare")) || (CurrCard.indexOf('B') == 0)))
        {
            if (player==1) MODE = -1;
            else MODE = 0;
            System.out.println("No turn.");
            if (player==1) Generate(1);
            else Generate(2);
        }
        else if ((CountS + CountL + CountM + CountB + CountD + CountA == 0) && (CountC == 1))
        {
            if (player==1) MODE = -1;
            else MODE = 0;
            System.out.println("No turn.");
            if (player==1) Generate(1);
            else Generate(2);
        }
        else if (CountS + CountL + CountD + CountC == 0)
        {
            if (((CountM != 0) && (CountB == 0) && ((CurrCard.indexOf('M') == 0) || (CurrCard.indexOf('B') == 0))) || ((CountB != 0) && (CountM == 0) && ((CurrCard.equals("SSquare")) || (CurrCard.indexOf('B') == 0))))
            {
                if (player==1) MODE = -1;
                else MODE = 0;
                System.out.println("No turn.");
                if (player==1) Generate(1);
                else Generate(2);
            }
        }
    }
    public void IIBota()
    {
        int i,j,k=-1;
        System.out.println("Your opponent is thinking...");
        Integer[] VirtInters = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        Integer[] VirtInters1 = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        Integer[] VirtInters2 = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

        List<Integer> VirtIntersected1 = Intersected, VirtIntersected2 = Intersected;
        int CHOICE;
        int MaxPoints = -1, MaxInts = -1;
        int MinPoints = 1000, MinInts = 1000;
        int Visited = 0;
        Integer[] MaxInters = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        Integer[] MinInters = new Integer[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };
        String MaxCard = "";
        String MinCard = "";
        for (CHOICE = 0; CHOICE < Cards.length; CHOICE++)
        {
            if (Play2[CHOICE] > 0)
            {
                String choose = Cards[CHOICE];
                if (choose.indexOf("Strip") == 0)
                {
                    for (i = 0; i < VirtInters.length; i++)
                    {
                        VirtInters[i] = Inters[i];
                        VirtInters1[i] = Inters[i];
                        VirtInters2[i] = Inters[i];
                    }
                    choose = choose.replace("Strip", "");
                    Integer[] InterCurr1 = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
                    Integer[] InterCurr2 = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
                    for (i = 0; i < choose.length(); i++)
                    {
                        String [] BS=new String[]{String.valueOf('1'),String.valueOf('2'),String.valueOf('3'),String.valueOf('4')};
                        String A = String.valueOf(choose.charAt(i));
                        for (int D=0;D<4;D++)
                        {
                            if (A.equals(BS[D]))
                            {
                                for (j = 0; j < 4; j++)
                                    InterCurr1[4 * j + D] = 1;
                                for (j = 0; j < 4; j++)
                                    InterCurr2[4 * j + D] = 1;
                            }
                        }
                    }
                    System.out.println(Cards[CHOICE]);

                    System.out.println("VirtInters1");
                    for (i = 0; i < 4; i++)
                    {
                        for (j = 0; j < 4; j++) System.out.print(VirtInters1[4 * i + j] + " ");
                        System.out.println();
                    }
                    System.out.println("VirtInters2");
                    for (i = 0; i < 4; i++)
                    {
                        for (j = 0; j < 4; j++) System.out.print(VirtInters2[4 * i + j] + " ");
                        System.out.println();
                    }

                    Boolean match, IsIntersected, IsIn;

                    for (j = 0; j < 4; j++)
                    {
                        match = true;
                        IsIntersected = false;
                        for (i = 0; i < 4; i++)
                        {
                            if (VirtInters[4 * i + j] > 3) IsIntersected = true;
                            if (!((InterCurr1[4 * i + j] == Inters[4 * i + j])&& ((InterCurr1[4 * i + j] == 1) || (InterCurr1[4 * i + j] == 3))))
                                match = false;
                        }
                        if ((IsIntersected == true) && (match == false))
                            for (i = 0; i < 4; i++)
                                InterCurr1[4 * i + j] = 0;
                        else if ((IsIntersected == false) && (match == true))
                            for (i = 0; i < 4; i++)
                                InterCurr1[4 * i + j] = (-1) * InterCurr1[4 * i + j];
                    }

                    System.out.println("InterCurr1");
                    for (i = 0; i < 4; i++)
                    {
                        for (j = 0; j < 4; j++) System.out.print(InterCurr1[4 * i + j] + " ");
                        System.out.println();
                    }

                    for (i = 0; i < 4; i++)
                        for (j = 0; j < 4; j++)
                            if (i <= j)
                            {
                                int dop = InterCurr2[4 * i + j] * 3;
                                InterCurr2[4 * i + j] = InterCurr2[4 * j + i] * 3;
                                InterCurr2[4 * j + i] = dop;
                            }
                    for (i = 0; i < 4; i++)
                    {
                        match = true;
                        IsIntersected = false;
                        for (j = 0; j < 4; j++)
                        {
                            if (Inters[4 * i + j] > 3) IsIntersected = true;
                            if (!((InterCurr2[4 * i + j] == Inters[4 * i + j]) && ((InterCurr2[4 * i + j] == 1) || (InterCurr2[4 * i + j] == 3)))) match = false;
                        }
                        if ((IsIntersected == true) && (match == false))
                            for (j = 0; j < 4; j++) InterCurr2[4 * i + j] = 0;
                        else if ((IsIntersected == false) && (match == true))
                            for (j = 0; j < 4; j++) InterCurr2[4 * i + j] = (-1) * InterCurr2[4 * i + j];
                    }

                    System.out.println("InterCurr2");
                    for (i = 0; i < 4; i++)
                    {
                        for (j = 0; j < 4; j++) System.out.print(InterCurr2[4 * i + j] + " ");
                        System.out.println();
                    }

                    int k1 = 0, k2 = 0;
                    for (i = 0; i < 4; i++)
                        for (j = 0; j < 4; j++)
                        {
                            VirtInters1[4 * i + j] += InterCurr1[4 * i + j];
                            if (VirtInters1[4 * i + j] == 4)
                                if (!(VirtIntersected1.contains(4 * i + j)))
                                {
                                    VirtIntersected1.add(4 * i + j);
                                    k1 += 1;
                                }
                            VirtInters2[4 * i + j] += InterCurr2[4 * i + j];
                            if (VirtInters2[4 * i + j] == 4)
                                if (!(VirtIntersected2.contains(4 * i + j)))
                                {
                                    VirtIntersected2.add(4 * i + j);
                                    k2 += 1;
                                }
                        }

                    System.out.println("VirtInters1");
                    for (i = 0; i < 4; i++)
                    {
                        for (j = 0; j < 4; j++) System.out.print(VirtInters1[4 * i + j] + " ");
                        System.out.println();
                    }
                    System.out.println("VirtInters2");
                    for (i = 0; i < 4; i++)
                    {
                        for (j = 0; j < 4; j++) System.out.print(VirtInters2[4 * i + j] + " ");
                        System.out.println();
                    }
                    System.out.println();
                    Visited += 1;
                    if (k1 > MaxInts)
                    {
                        MaxInts = k1;
                        MaxCard = Cards[CHOICE];
                        MaxInters = VirtInters1;
                    }
                    if (k2 > MaxInts)
                    {
                        MaxInts = k2;
                        MaxCard = Cards[CHOICE];
                        MaxInters = VirtInters2;
                    }
                    if (Visited > 1)
                    {
                        if (k1 < MinInts)
                        {
                            MinInts = k1;
                            MinCard = Cards[CHOICE];
                            MinInters = VirtInters1;
                        }
                        if (k2 < MinInts)
                        {
                            MinInts = k2;
                            MinCard = Cards[CHOICE];
                            MinInters = VirtInters2;
                        }
                    }

                }
                else if (choose.equals("SSquare"))
                {
                    k = 1;
                    Visited += 1;
                    if (k > MaxPoints)
                    {
                        MaxPoints = k;
                        MaxCard = Cards[CHOICE];
                    }
                    if (Visited > 1)
                    {
                        if (k < MinPoints)
                        {
                            MinPoints = k;
                            MinCard = Cards[CHOICE];
                        }
                    }
                    IsTurnEnded = true;
                }
                else if (choose.indexOf('M') == 0)
                {
                    if (CurrCard == "SSquare")
                    {
                        k = 2;
                        if (k > MaxPoints)
                        {
                            MaxPoints = k;
                            MaxCard = Cards[CHOICE];
                        }
                        Visited += 1;
                        if (k < MinPoints)
                        {
                            MinPoints = k;
                            MinCard = Cards[CHOICE];
                        }
                        IsTurnEnded = true;
                    }
                }
                else if (choose.indexOf('B') == 0)
                {
                    if (((CurrCard == "MSquare") && (choose == "BSquare"))
                            || ((CurrCard == "MRound") && (choose == "BRound"))
                            || ((CurrCard == "MTriangle") && (choose == "BTriangle"))
                            || ((CurrCard == "MOctagon") && (choose == "BOctagon")))
                    {
                        k = 4;
                        if (k > MaxPoints)
                        {
                            MaxPoints = k;
                            MaxCard = Cards[CHOICE];
                        }
                        Visited += 1;
                        if (k < MinPoints)
                        {
                            MinPoints = k;
                            MinCard = Cards[CHOICE];
                        }
                        IsTurnEnded = true;
                    }
                }
                else if (choose.indexOf('D') == 0) {
                    if (S2 + MaxPoints > S1) {
                        System.out.println("Round ended.");
                        System.out.println("Your score in this round:  " + S1);
                        System.out.println("Enemy score in this round: " + S2);
                        Score1.add(S1);
                        Score2.add(S2 + MaxPoints);
                        CurrCard = "Dot";
                        Play2[CHOICE] -= 1;
                        UsedC[CHOICE] += 1;
                        IsTurnEnded = true;
                        gameplay();
                    }

                } else if (choose.indexOf('A') == 0) {
                    System.out.println("Enemy dropped arrow.");
                    CurrCard = "Arrow";
                    Play2[CHOICE] -= 1;
                    UsedC[CHOICE] += 1;

                } else if (choose.indexOf('С') == 0) {
                    CurrCard = "Curve";
                    Play2[CHOICE] -= 1;
                    UsedC[CHOICE] += 1;

                    System.out.println("Enemy returned " + MinCard + ".");
                    for (i = 0; i < Cards.length; i++)
                    {
                        if (Cards[i] == MinCard)
                        {
                            Play2[i] -= 1;
                            break;
                        }
                    }

                }
                else if (choose.indexOf('S') == 0)
                {
                    k = 20;
                    if ((k > MaxPoints) && (S2 < S1 - 10))
                    {
                        MaxPoints = k;
                        MaxCard = Cards[CHOICE];
                    }
                    IsTurnEnded = true;
                }

            }
        }
        if (IsTurnEnded = true)
        {
            MODE = 0;
            if (MaxPoints > MaxInts)
            {
                CurrCard = MaxCard;
                int IND=Arrays.asList(Cards).indexOf(CurrCard);
                CurrSprite=CardSprites.get(IND);
                S2 += MaxPoints;
                Search(CurrCard,2);
            }
            else
            {
                CurrCard = MaxCard;
                int IND=Arrays.asList(Cards).indexOf(CurrCard);
                CurrSprite=CardSprites.get(IND);
                Search(CurrCard,2);
                int CT1 = 0, CT2 = 0;
                for (i = 0; i < 4; i++)
                    for (j = 0; j < 4; j++)
                    {
                        if (Inters[4 * i + j] == 4) CT1 += 1;
                        if (MaxInters[4 * i + j] == 4) CT2 += 1;
                    }
                System.out.println(CT1);
                System.out.println(CT2);
                CT2 -= CT1;

                Inters = MaxInters;
                for (i = 0; i < CT2; i++) Generate(1);
            }
            int CountTwos = 0;
            for (i = 0; i < 4; i++)
                for (j = 0; j < 4; j++)
                    if (Inters[4 * i + j] == 4)
                        CountTwos += 1;
            if (CountTwos == 16)
            {
                System.out.println("Round ended.");
                System.out.println("Your score in this round:  " + S1);
                System.out.println("Enemy score in this round: " + S2);
                Score1.add(S1);
                Score2.add(S2);
                gameplay();
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawARGB(lvlColor[0], lvlColor[1], lvlColor[2], lvlColor[3]);
        if (MODE>=0)
        {
            IsPossibleTurn(1);
            TurnArrow1.setX(viewWidth/2-TurnArrow1.getFrameWidth()/4);
            TurnArrow1.setY(viewHeight/2-TurnArrow1.getFrameHeight()/4);
            TurnArrow1.draw(canvas);
        }
        if (MODE==-1)
        {
            IsPossibleTurn(2);
            TurnArrow2.setX(viewWidth/2-TurnArrow2.getFrameWidth()/4);
            TurnArrow2.setY(viewHeight/2-TurnArrow2.getFrameHeight()/4);
            TurnArrow2.draw(canvas);
            IIBota();
        }
        Revers.setX(0);
        Revers.setY(viewHeight / 2-Revers.getFrameHeight()/4);
        Revers.draw(canvas);


        pauseIcon.setX(10);
        pauseIcon.setY(viewHeight / 2);
        pauseIcon.draw(canvas);
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setTextSize(55.0f);
        p.setColor(Color.WHITE);
        //if paused

        paint.setStrokeWidth(3);
        int coeff=3,trait_width = 25*coeff; int trait_height = 25*coeff,border=5*coeff;
        for (int GI=0;GI<4;GI++)
            for (int GJ=0;GJ<4;GJ++)
            {
                paint.setColor(Color.LTGRAY);
                canvas.drawRect(viewWidth - trait_width * (5-GJ) - border+coeff*GJ*5, viewHeight / 2 + trait_height * (GI-2) - border+coeff*GI*5, viewWidth - trait_width * (5 -GJ - 1) + border+coeff*GJ*5, viewHeight / 2 + trait_height * (GI -2+ 1) + border+coeff*GI*5, paint);
                paint.setColor(Color.BLACK);
                canvas.drawRect(viewWidth - trait_width * (5-GJ)+coeff*GJ*5, viewHeight / 2 + trait_height * (GI-2) +coeff*GI*5, viewWidth - trait_width * (5-GJ - 1)+coeff*GJ*5, viewHeight / 2 + trait_height * (GI -2+ 1)+coeff*GI*5, paint);
                if (Inters[4*GI+GJ]>0)
                {
                    if (Inters[4*GI+GJ]==1) paint.setColor(Color.parseColor("#AD6700"));
                    if (Inters[4*GI+GJ]==3) paint.setColor(Color.parseColor("#FFFF00"));
                    if (Inters[4*GI+GJ]==4) paint.setColor(Color.parseColor("#FFFFFF"));
                    canvas.drawRect(viewWidth - trait_width * (5-GJ) + border+coeff*GJ*5, viewHeight / 2 + trait_height * (GI-2)  + border+coeff*GI*5, viewWidth - trait_width * (5-GJ - 1) - border+coeff*GJ*5, viewHeight / 2 + trait_height * (GI -2+ 1) - border+coeff*GI*5,paint);

                }
            }
        int ENCAR2=SumHand(Play2);
        for (int i=0;i<ENCAR2;i++)
        {
            Revers.setX(HandWidth * i / ENCAR2+8*(i+1));
            Revers.setY(0);
            Revers.draw(canvas);
        }



        int ENCAR1=SumHand(Play1);
        int coord=0;
        for (int i=0;i<Play1.length;i++)
        {

            if (Play1[i]>0)
            {
                for (int j=0;j<Play1[i];j++)
                {
                    CardSprites.get(i).setX(HandWidth * coord / ENCAR1+8*(coord+1));
                    CardSprites.get(i).setY(viewHeight-CardSprites.get(i).getFrameHeight()/2);
                    CardSprites.get(i).draw(canvas);
                    coord+=1;
                }
            }
        }

        if (CurrSprite==null) CurrSprite=Revers;
        CurrSprite.setX(viewWidth/2-CurrSprite.getFrameWidth()/4);
        CurrSprite.setY(viewHeight/2-CurrSprite.getFrameHeight()/2);
        CurrSprite.draw(canvas);


        if (isPaused)
        {
            pauseText.draw(canvas);
            p.setColor(Color.BLACK);
            canvas.drawText("Pause", viewWidth/2.5f, 450, p);
            canvas.drawText("Click to continue", viewWidth/3.8f, getHeight() - 500, p);

        }
        else if (isFinished)
        {
            failText.draw(canvas);
            p.setColor(Color.BLACK);
            canvas.drawText("Click to restart", viewWidth/3.0f, getHeight() - 500,p);
        }

        // if failed
        //System.out.println("WHY NOT? "+MODE);
        ROTorNOT(canvas);

    }
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        viewWidth = w;
        viewHeight = h;


    }
    protected void update ()
    {
        //powerup


        // new level

        invalidate();
    }
    public void UserPickedStrip(MotionEvent event,String choose)
    {
        int i,j,k;
        String DOP="";
        DOP+=choose;
        choose = choose.replace("Strip", "");
        Integer[] InterCurr = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        for (i = 0; i < choose.length(); i++)
        {
            String [] BS=new String[]{String.valueOf('1'),String.valueOf('2'),String.valueOf('3'),String.valueOf('4')};
            String A = String.valueOf(choose.charAt(i));
            for (int D=0;D<4;D++)
            {
                if (A.equals(BS[D]))
                    for (j = 0; j < 4; j++) InterCurr[4 * j + D] = 1;
            }
        }
        for (i = 0; i < 4; i++)
        {
            for (j = 0; j < 4; j++)
                System.out.print(InterCurr[4 * i + j] + " ");
            System.out.println();
        }
        System.out.println("Rotate (1) or not (0)?");
        NeedToRotate=true;
        UserRotate(event,DOP,InterCurr);

    }
    public void UserRotate(MotionEvent event,String DOP, Integer[] InterCurr)
    {
        if (MODE==1) ToRot(event);
        if (MODE==1)
        {
            int i,j,k;

            Boolean match, IsIntersected, IsIn;
            if (Rot == 0) {
                for (j = 0; j < 4; j++) {
                    match = true;
                    IsIntersected = false;
                    for (i = 0; i < 4; i++) {
                        if (Inters[4 * i + j] > 3)
                            IsIntersected = true;
                        if (!((InterCurr[4 * i + j] == Inters[4 * i + j])
                                && ((InterCurr[4 * i + j] == 1) || (InterCurr[4 * i + j] == 3))))
                            match = false;
                    }
                    if ((IsIntersected == true) && (match == false))
                        for (i = 0; i < 4; i++)
                            InterCurr[4 * i + j] = 0;
                    else if ((IsIntersected == false) && (match == true))
                        for (i = 0; i < 4; i++)
                            InterCurr[4 * i + j] = (-1) * InterCurr[4 * i + j];
                }
            } else if (Rot == 1) {
                for (i = 0; i < 4; i++)
                    for (j = 0; j < 4; j++)
                        if (i <= j) {
                            int dop = InterCurr[4 * i + j] * 3;
                            InterCurr[4 * i + j] = InterCurr[4 * j + i] * 3;
                            InterCurr[4 * j + i] = dop;
                        }
                for (i = 0; i < 4; i++) {
                    match = true;
                    IsIntersected = false;
                    for (j = 0; j < 4; j++) {
                        if (Inters[4 * i + j] > 3)
                            IsIntersected = true;
                        if (!((InterCurr[4 * i + j] == Inters[4 * i + j])
                                && ((InterCurr[4 * i + j] == 1) || (InterCurr[4 * i + j] == 3))))
                            match = false;
                    }
                    if ((IsIntersected == true) && (match == false))
                        for (j = 0; j < 4; j++)
                            InterCurr[4 * i + j] = 0;
                    else if ((IsIntersected == false) && (match == true))
                        for (j = 0; j < 4; j++)
                            InterCurr[4 * i + j] = (-1) * InterCurr[4 * i + j];
                }
            } else {
                System.out.println("I don't know what you're want. Try again.");
            }
            if ((Rot == 0) || (Rot == 1)) {
                k = 0;
                for (i = 0; i < 4; i++)
                    for (j = 0; j < 4; j++) {
                        Inters[4 * i + j] += InterCurr[4 * i + j];
                        if (Inters[4 * i + j] == 4)
                            if (!(Intersected.contains(4 * i + j))) {
                                Intersected.add(4 * i + j);
                                System.out.println("Add to intersections: " + (4 * i + j));
                                k += 1;
                            }
                    }
                i = 0;
                while (i < k)
                {
                    Generate(2);
                    i++;
                }
                int Number=CurrC.indexOf(DOP);
                CurrCard = CurrC.get(Number);
                if (Rot==1) CurrSprite=RotCardSprites.get(Number);
                else if (Rot==0) CurrSprite=CardSprites.get(Number);
                Search(CurrCard,1);
            }
            MODE=-1;
        }


    }
    public void UserPickedSmall(MotionEvent event,String choose)
    {
        CurrCard = "SSquare";
        S1 += 1;
        Search(CurrCard,1);
        int IND=Arrays.asList(Cards).indexOf(CurrCard);
        CurrSprite=CardSprites.get(IND);
        MODE=-1;
    }
    public void UserPickedMiddle(MotionEvent event,String choose)
    {
        if (CurrCard == "SSquare")
        {
            CurrCard = choose;
            S1 += 2;
            Search(CurrCard,1);
            int IND=Arrays.asList(Cards).indexOf(CurrCard);
            CurrSprite=CardSprites.get(IND);
            MODE=-1;
        }
        else MODE=0;
    }
    public void UserPickedBig(MotionEvent event,String choose)
    {
        if (((CurrCard == "MSquare") && (choose == "BSquare"))
                || ((CurrCard == "MRound") && (choose == "BRound"))
                || ((CurrCard == "MTriangle") && (choose == "BTriangle"))
                || ((CurrCard == "MOctagon") && (choose == "BOctagon"))) {
            CurrCard = choose;
            S1 += 4;
            Search(CurrCard,1);
            int IND=Arrays.asList(Cards).indexOf(CurrCard);
            CurrSprite=CardSprites.get(IND);
            MODE=-1;
        }
        else MODE=0;
    }
    public void UserPickedDot(MotionEvent event,String choose)
    {
        System.out.println("Round ended.");
        System.out.println("Your score in this round:  " + S1);
        System.out.println("Enemy score in this round: " + S2);
        Score1.add(S1);
        Score2.add(S2);
        CurrCard = "Dot";
        Search(CurrCard,1);
        int IND=Arrays.asList(Cards).indexOf(CurrCard);
        CurrSprite=CardSprites.get(IND);
        MODE=-1;
        gameplay();
    }
    public void UserPickedArrow(MotionEvent event,String choose)
    {
        System.out.println("You can drop 1 more card.");
        CurrCard = "Arrow";
        Search(CurrCard,1);
        int IND=Arrays.asList(Cards).indexOf(CurrCard);
        CurrSprite=CardSprites.get(IND);
    }
    public void UserPickedCurve(MotionEvent event,String choose)
    {
        CurrCard = "Curve";
        Search(CurrCard, 1);
        int IND = Arrays.asList(Cards).indexOf(CurrCard);
        CurrSprite = CardSprites.get(IND);
    }
    public void DOPUserCurve(MotionEvent event,String choose)
    {
        int i, j;
        System.out.println("Which card you want to return?");
        List<String> RetC = new ArrayList<String>();
        for (i = 0; i < Cards.length; i++)
            for (j = 0; j < Play1[i]; j++) {
                RetC.add(Cards[i]);
                System.out.println(RetC.size() + ": " + Cards[i]);
            }

        new Thread(()->{
            while (DOPSuccess==false)
            {
                int pointerId2 = event.getPointerId(0);
                int pointerIndex2 = event.findPointerIndex(pointerId2);
                float x2 = event.getX(pointerIndex2);
                float y2 = event.getY(pointerIndex2);
                int ret = -1;
                if (event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    if (y2 >= viewHeight - Revers.getFrameHeight() / 2) {
                        int P1HAS2 = SumHand(Play1), i2;
                        for (i2 = 0; i2 < P1HAS2; i2++) {
                            if ((x2 >= HandWidth * i2 / P1HAS2 + 8 * (i2 + 1)) && (x2 <= HandWidth * (i2 + 1) / P1HAS2 + 8 * (i2 + 1 + 1))) {
                                ret = i2 + 1;
                                break;
                            }
                        }
                        String RetCard = RetC.get(ret);
                        for (i2 = 0; i2 < Cards.length; i2++) {
                            if (Cards[i2] == RetCard) {
                                Play1[i2] -= 1;
                                DOPSuccess=true;
                                break;
                            }
                        }
                    }
                }
                IsTurnEnded = true;
                if (DOPSuccess==true) break;
            }

        }).start();

    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        IsPossibleTurn(1);
        int pointerId = event.getPointerId(0);
        int pointerIndex = event.findPointerIndex(pointerId);
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);
        int Number=-1,Number2=-1;
        System.out.println(x+" "+y);
        if (event.getAction()==MotionEvent.ACTION_DOWN)
        {
            if (MODE==0)
            {
                //IsPossibleTurn(1);
                int i,j,k;
                String choose;
                System.out.println("##### Which card will you pick? 0 - None.");
                //int Number = scanner.nextInt();
                if (y>=viewHeight-Revers.getFrameHeight()/2)
                {
                    int P1HAS=SumHand(Play1);
                    for (i=0;i<P1HAS;i++)
                    {
                        if ((x>=HandWidth * i / P1HAS+8*(i+1))&&(x<=HandWidth * (i+1) / P1HAS+8*(i+1+1)))
                        {
                            Number=i+1;
                            break;
                        }
                    }

                }
                else Number=0;
                if (Number == 0)
                {
                    Generate(1);
                    MODE=-1;
                }
                else
                {
                    CurrC.clear();
                    for (i = 0; i < Cards.length; i++)
                        for (j = 0; j < Play1[i]; j++) CurrC.add(Cards[i]);

                    Number -= 1;
                    choose = CurrC.get(Number);
                    System.out.println("You chose " + choose);
                    if (choose.indexOf("Strip") == 0)
                    {
                        MODE=1;
                        System.out.println("NOW MODE: "+MODE);
                        DOPCHOOSE=choose;
                    }
                    else if (choose.equals("SSquare")) UserPickedSmall(event,choose);
                    else if (choose.indexOf('M') == 0) UserPickedMiddle(event,choose);
                    else if (choose.indexOf('B') == 0) UserPickedBig(event,choose);
                    else if (choose.indexOf('D') == 0) UserPickedDot(event,choose);
                    else if (choose.indexOf('A') == 0)
                    {
                        MODE=12;
                        UserPickedArrow(event,choose);
                    }
                    else if (choose.indexOf('C') == 0)
                    {
                        MODE=13;
                        UserPickedCurve(event,choose);
                    }
                }
            }
            else if (MODE==1)
            {
                UserPickedStrip(event,DOPCHOOSE);
            }
            else if (MODE==12)
            {
                int i,j,k;
                String choose;
                System.out.println("##### Which card will you pick? 0 - None.");
                //int Number = scanner.nextInt();
                if (y>=viewHeight-Revers.getFrameHeight()/2)
                {
                    int P1HAS=SumHand(Play1);
                    for (i=0;i<P1HAS;i++)
                    {
                        if ((x>=HandWidth * i / P1HAS+8*(i+1))&&(x<=HandWidth * (i+1) / P1HAS+8*(i+1+1)))
                        {
                            Number=i+1;
                            break;
                        }
                    }

                }
                else Number=0;
                if (Number > 0)
                {
                    CurrC.clear();
                    for (i = 0; i < Cards.length; i++)
                        for (j = 0; j < Play1[i]; j++) CurrC.add(Cards[i]);

                    Number -= 1;
                    choose = CurrC.get(Number);
                    System.out.println("You chose " + choose);
                    if (choose.indexOf("Strip") == 0)
                    {
                        MODE=1;
                        DOPCHOOSE=choose;
                    }
                    else if (choose.equals("SSquare")) UserPickedSmall(event,choose);
                    else if (choose.indexOf('M') == 0) UserPickedMiddle(event,choose);
                    else if (choose.indexOf('B') == 0) UserPickedBig(event,choose);
                    else if (choose.indexOf('D') == 0) UserPickedDot(event,choose);
                    else if (choose.indexOf('A') == 0)
                    {
                        MODE=12;
                        UserPickedArrow(event,choose);
                    }
                    else if (choose.indexOf('C') == 0)
                    {
                        MODE=13;
                        UserPickedCurve(event,choose);
                    }
                }
            }
            else if (MODE==13)
            {
                int i,j,k;
                String choose;
                System.out.println("##### Which card will you pick? 0 - None.");
                //int Number = scanner.nextInt();
                if (y>=viewHeight-Revers.getFrameHeight()/2)
                {
                    int P1HAS=SumHand(Play1);
                    for (i=0;i<P1HAS;i++)
                    {
                        if ((x>=HandWidth * i / P1HAS+8*(i+1))&&(x<=HandWidth * (i+1) / P1HAS+8*(i+1+1)))
                        {
                            Number=i+1;
                            break;
                        }
                    }

                }
                else Number=0;
                if (Number > 0)
                {
                    CurrC.clear();
                    for (i = 0; i < Cards.length; i++)
                        for (j = 0; j < Play1[i]; j++) CurrC.add(Cards[i]);

                    Number -= 1;
                    choose = CurrC.get(Number);
                    int IND=Arrays.asList(Cards).indexOf(choose);
                    Play1[IND]-=1;
                    MODE=-1;
                }
            }
        }


        if (isPaused || isFinished)
        {
            if (pauseText.getBoundingBoxRect().contains((int)event.getX(),(int)event.getY()))
            {
                isPaused = false;
            }
            else if (failText.getBoundingBoxRect().contains((int)event.getX(),(int)event.getY())) {
                isFinished = false;
            }
        }
        else
        {
            int eventAction = event.getAction();
            if (eventAction == MotionEvent.ACTION_DOWN)
            {
                if (pauseIcon.getBoundingBoxRect().contains((int) event.getX(), (int) event.getY()))
                {
                    isPaused = true;
                }
            }
        }
        return true;
    }


    class Timer extends CountDownTimer {

        public Timer() {
            super(Integer.MAX_VALUE, timerInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            update ();
        }

        @Override
        public void onFinish() {

        }
    }
}
