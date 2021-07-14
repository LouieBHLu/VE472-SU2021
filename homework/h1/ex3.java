package com.ve472.hw1;
import java.util.*;
import java.io.*;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        /////////////////Ex3-2/////////////////
        Vehicle c1 = new Vehicle("123456");
        Car c2 = new Car("123456", 5.0, "95");
        policeCar c3 = new policeCar("123456", 6.0, "92", 5);

        c1.getCompany();
        c2.getCompany();
        c3.getCompany();
        c2.addFuel(10.0);
        c3.chasing();

        /////////////////Ex3-1/////////////////
        textRandom tool = new textRandom("/home/pastrol/ve472/hw1/file.txt");
    }
}


class Vehicle{
    public String Manufacturer = "Tesla";
    public String numPlate;

    public Vehicle(String num){ this.numPlate = num; }
    public void addFuel(){}
    public void getCompany(){System.out.println(this.Manufacturer);}
    public void getnumPlate(){System.out.println(this.numPlate);}
}

class Car extends Vehicle{
    public Double fuelVolume;
    public String fuelType;
    public Car(String num,Double fuelVolume,String fuelType){
        super(num);
        this.fuelType = fuelType;
        this.fuelVolume = fuelVolume;
    }

    public void addFuel(Double maxVolume) throws InterruptedException {
        double time = 0;
        System.out.println("FuelType: " + fuelType + '.');
        if(this.fuelVolume < maxVolume){
            while(fuelVolume < maxVolume){
                fuelVolume++;
                sleep(1000);
                time++;
                System.out.println("Adding for " + time + "s.");
            }
            System.out.println("Tank full!");
        }
        else System.out.println("Tank Already full!");
    }
}

class policeCar extends Car{
    public boolean policeWhistle = false;
    public double dis;
    public policeCar(String num, Double fuelVolume, String fuelType, double distance) {
        super(num, fuelVolume, fuelType);
        this.dis = distance;
    }

    @Override
    public void addFuel(Double maxVolume) throws InterruptedException {
        super.addFuel(maxVolume);
    }

    public void chasing() throws InterruptedException {
        if(!this.policeWhistle){this.policeWhistle = true;}
        System.out.println("Start chasing!");
        while(dis > 0){
            System.out.println("Chasing, alarm shining!");
            sleep(1000);
            dis--;
        }
        System.out.println("Get the criminal!");
        this.policeWhistle = false;
    }
}

class textRandom{
    public textRandom(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader((fis)));
        String line;

        ArrayList<String> firstname = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> email = new ArrayList<>();

        while((line = br.readLine()) != null){
            line = line.trim();
            String[] words = line.split(",");
            firstname.add(words[0]);
            name.add(words[1]);
            email.add(words[2]);
        }

        while(!name.isEmpty()){
            Random ra = new Random();
            int num = ra.nextInt(name.size());
            String output = name.get(num) + "," + firstname.get(num) + "," + email.get(num);
            System.out.println(output);
            name.remove(num);
            firstname.remove(num);
            email.remove(num);
        }
        br.close();
    }
}