package com.example.demo11.myClass;

public class TurnPwd {

    public static String turnPwd(String Pwd)
    {
        char[] now = Pwd.toCharArray();
        int len = Pwd.length();
        for(int i = 0; i < len; i++)
        {
            if( i % 3 == 0)
            {
                now[i] += i;
            }
            else if(i % 3 == 1)
            {
                now[i] += i/2;
            }
        }
        return String.valueOf(now);
    }
}
