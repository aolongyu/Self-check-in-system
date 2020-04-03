package com.example.demo11.myClass;

public class ParseStr {

    int row;

    public String[][] parseStr(String recvMsg){     //解析服务端传来的数据（字符串），返回二维数组
        String[][] str = new String[66][66];
        char[] chr = recvMsg.toCharArray();

        row = 0;
        int col = 0;
        String temp = "";

        for (int i = 0; i < recvMsg.length(); i++){
            if(chr[i] == ','){
                str[row][col] = temp;
                col++;
                temp = "";
                continue;
            }
            if(chr[i] == '#'){
                str[row][col] = temp;
                row++;
                col = 0;
                temp = "";
                continue;
            }
            temp += chr[i];
        }
        return str;
    }

    public int getRow() {
        return row;
    }
}
