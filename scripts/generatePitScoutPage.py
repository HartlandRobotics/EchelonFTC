import os
import csv
from htmlBuilder.tags import *
from htmlBuilder.attributes import *


file = open("PitScout.csv", newline='')
temp = file.read()
arrOfTeams = temp.split("\n")

#the first element is just the column names
newList = []
for team in arrOfTeams:
    newList.append(team.split(','))
    
#removes the first element (column names)
newList.pop(0)

#make each element of the array in the format that you want to print
f = open("PitScoutResult.html", "w")

#having to actually follow "good" programming rules
f.write("<!doctype html>")
f.write("<html>")
f.write("<body>")

header = H1([], "PitScout Data")
f.write(header.render(pretty = True))

#makes the hyperlink for the team
str1 =  A([Href("#{0}")], "{0}")

for item in newList:
    item[0] = item[0][3:]
    f.write(str1.render(pretty = True).format(item[0]))


#template for each teams info
html = Div([],
        H2([Id("{0}")], "Team Number: {0}"),
        H3([], "AUTO INFO"),
        P([], "Has Auto: {1}"),
        P([], "Wants Help Creating an Auto: {2}"),
        P([], "Coding Language Used: {3}"),
        P([], "Can Shoot: {4}"),
        P([], "Accuracy: {5}%"),
        P([], "Amount of Balls Picked up or Shot: {6}"),
        P([], "Human Player Accuracy: {18}%"),
                
        H3([], "TELE OP INFO"),
        P([], "Can Shoot: {7}"),
        P([], "Accuracy: {8}%"),
        P([], "Preferred Goal: {9}"),
        P([], "Can Play Defense: {10}"),
                   
        H3([], "END GAME INFO"),
        P([], "Can Robot Hang: {11}"),
        P([], "Highest Bar Robot can Hang on: {12}"),
        P([], "Time to Hang: {13}"),
        P([], "Preferred Hanging Spot: {14}"),
        P([], "Does Robot has Side Swing: {15}"),
                   
        H3([], "TEAM INFO"),
        P([], "Driver Experience: {16}"),
        P([], "Operator Experience: {17}"),
        P([], "Extra Notes: {19}"),
                
        )
    
#Writing each teams Info
for item in newList:
    if len(item) > 1:  
        f.write(html.render(pretty = True).format(*item))
        
#back to the "good" programming rules
f.write("</body>")
f.write("</html>")
f.close()

