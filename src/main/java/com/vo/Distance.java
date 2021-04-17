package com.vo;

public class Distance {

    public static float selectDis(String str){
        float dis = 0;
        if(str.equals("keras11657")){
            dis = 3;
        }else if (str.equals("keras5108")){
            dis = 2;
        }else if (str.equals("capsnet-tensorflow4")){
            dis = 1;
        }else if (str.equals("tensorflow_scala18")){
            dis = 5;
        }else if (str.equals("transformers1962")){
            dis = 0.97297297f;
        }else if (str.equals("pytorch-lightning2386")){
            dis = 3;
        }else if (str.equals("fastai1678")){
            dis = 2;
        }else {
            dis = (float) (1+(int) (Math.random()*4));
        }
        return dis;
    }
}
