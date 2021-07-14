setwd("/home/hadoop/Desktop/h4/result")
var <-read.csv("var.csv",header=TRUE)
avr <-read.csv("avrg.csv",header=TRUE)

library(ggplot2)

ggplot() + 
  geom_point(data = avr, aes(x = continent, y = AVRG), color="royalblue2", size = 2) +
  labs(y="average temperature (\u00B0C)",  # ASCII code for celcius
       x="continent",
       caption = "average temperature")

ggplot() + 
  geom_point(data = var, aes(x = continent, y = var), color="orange", size = 2) +
  labs(y=expression("temperature variance (\u00B0C)"^2),  # ASCII code for celcius
       x="continent",
       caption = "temperature variation")

