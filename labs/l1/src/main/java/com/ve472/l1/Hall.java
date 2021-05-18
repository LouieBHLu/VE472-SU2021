package com.ve472.l1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Hall{
    private String HallName;
    private String MovieName;
    protected List<List<Boolean>> seats;
    protected double centerLine;
    protected List<Integer> emptyNum;
    protected double m = 0; // column number
    protected int n = 0; // row number

    public Hall(String HallPath){
        try{
            FileInputStream fis = new FileInputStream(HallPath);
            BufferedReader br = new BufferedReader(new InputStreamReader((fis)));

            seats = new ArrayList<>();
            emptyNum = new ArrayList<>();

            try{
                HallName = br.readLine();
                MovieName = br.readLine();
                String line;

                while((line = br.readLine()) != null){
                    line = line.trim();
                    String[] row = line.split(" ");
                    seats.add(new ArrayList<>());
                    emptyNum.add(row.length);
                    for(String i:row){
                        seats.get(seats.size() - 1).add(i.equals("1"));
                    }
                    n++;
                    m = row.length;
                }
                br.close();
            } catch (IOException e) {
                System.exit(-1);
            }
            centerLine = (m + 1)/2;
        } catch (FileNotFoundException error){
            error.printStackTrace();
            System.exit(-1);
        }
    }

    protected Boolean available(int row, int firstCol, int count){
        if(count + firstCol > m) return false;
        for(int i = firstCol; i < firstCol + count; i++){
            if(!seats.get(row).get(i)) return false;
        }
        return true;
    }

    public String getHallName(){
        return HallName;
    }
    public String getMovieName(){
        return  MovieName;
    }
}