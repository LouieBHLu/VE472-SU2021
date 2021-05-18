package com.ve472.l1;
import java.io.*;
import java.util.*;

public class Cinema{
    protected HashMap<String, Hall> halls;
    protected HashMap<String, List<Hall>> movie2hall;
    protected List<Query> Queries;

    public Cinema(String dirPath){
        halls = new HashMap<>();
        movie2hall = new HashMap<>();
        Queries = new ArrayList<>();

        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if(files == null) System.exit(-1);
        for(File file:files){
            if(file.isFile()){
                // Read the current hall
                Hall hall = new Hall(dirPath + "/" + file.getName());
                halls.put(hall.getHallName(), hall);
                // If current hall's movies does not exist
                if(!movie2hall.containsKey(hall.getMovieName())){
                    // Add a new movie key
                    List<Hall> hallList = new ArrayList<>();
                    hallList.add(hall);
                    movie2hall.put(hall.getMovieName(), hallList);
                }
                else movie2hall.get(hall.getMovieName()).add(hall);
            }
        }

        for(String hallName: movie2hall.keySet()){
            List<Hall> hallList = movie2hall.get(hallName);
            hallList.sort(Comparator.comparing(Hall::getHallName));
            movie2hall.replace(hallName, hallList);
        }
    }

    public void checkQuery(String Path) throws IOException {
        try{
            FileInputStream fis = new FileInputStream(Path);
            BufferedReader br = new BufferedReader(new InputStreamReader((fis)));
            String line;
            try{
                while((line = br.readLine()) != null){
                    line = line.trim();
                    line = line.replace("\n", "");
                    String[] words = line.split(",");
                    for(int i = 0; i < words.length; i++){
                        String tmp = words[i];
                        tmp = tmp.trim();
                        words[i] = tmp;
                    }
                    Query cur = new Query(words[0], words[1]);
                    checkSeat(cur, words[2]);
                    Queries.add(cur);
                }
            } catch (IOException e){
                e.printStackTrace();
                System.exit(-1);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }


    private void checkSeat(Query query, String num){
        num = num.trim();
        int ticketNum = Integer.parseInt(num);
        if(movie2hall.containsKey(query.getMovie())){
            for(Hall hall: movie2hall.get(query.getMovie())){
                // Already modify hall's attributes in findSeat()
                if(findSeat(query, ticketNum, hall)){
                    query.Hall = hall.getHallName();
                    break;
                }
            }
        }
    }

    private Boolean findSeat(Query query, int num, Hall hall){
        if((double) num > hall.m) {return query.isAvailable = false;}
        TreeSet<Centroid> centroids = new TreeSet<>(compareSeat);

        for(int r = 0; r < hall.n; r++){
            for(int c = 0; c < hall.m; c++){
                if(hall.available(r, c, num)){
                    double mid = (double) (num + 1) / 2 + c;
                    int disRow = hall.n - r - 1;
                    disRow *= disRow;
                    double distance = Math.abs(mid - hall.centerLine);
                    distance *= distance;
                    distance += disRow;
                    Centroid temp = new Centroid(r, c);
                    temp.setDistance(distance);
                    centroids.add(temp);
                }
            }
        }

        if(centroids.isEmpty()) return false;
        // Choose the top centroid and set the seats selected to not 0
        Centroid temp = centroids.first();
        for(int i = temp.C; i < temp.C + num; i++) hall.seats.get(temp.R).set(i, false);
        // row number starts from 1
        for(int i = 1; i <= num; i++){
            query.cols.add(temp.C + i);
        }
        query.row = temp.R + 1;
        query.isAvailable = true;
        return true;
    }

    public void printTickets(){
        for(Query query: Queries){
            String out = query.getMe() + "," + query.getMovie();
            if(query.getIsAvailable()){
                String Row = query.getRow() + "";
                out = out + "," + query.getHall() + "," + Row + query.getCols();
            }
            System.out.println(out);
        }
    }

    Comparator<Centroid> compareSeat = (s1, s2) -> {
        if (Double.compare(s1.dis, s2.dis) != 0)
            return Double.compare(s1.dis, s2.dis);
        else if (s1.R != s2.R)
            return s2.R - s1.R;
        else return s1.C - s2.C; // the left
    };


}


class Query{
    public String Me;
    protected String Hall;
    protected String Movie;
    protected List<Integer> cols;
    protected int row;
    protected Boolean isAvailable;

    public Query(String Me, String movie){
        this.Me = Me;
        this.Movie = movie;
        cols = new ArrayList<>();
        isAvailable = false;
    }

    public String getCols(){
        String messageCols = "";
        for(int col: cols){
            messageCols = messageCols + "," + col;
        }
        return  messageCols;
    }

    public String getMe(){return Me;}
    public String getMovie(){return Movie;}
    public String getHall(){return Hall;}
    public Boolean getIsAvailable(){return isAvailable;}
    public Integer getRow(){return row;}
}


class Centroid{
    public int R;
    public int C;
    public double dis;

    public Centroid(int x, int y){
        R = x;
        C = y;
    }

    public void setDistance(double distance){ dis = distance;}
}