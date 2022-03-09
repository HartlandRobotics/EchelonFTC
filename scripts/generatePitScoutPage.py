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
    
#makes dictionary of column names and index in array
columnNames = newList.pop(0)
columnNamesDic = {}
for name in columnNames:
    columnNamesDic[name] = columnNames.index(name)
print(columnNamesDic)

#make each element of the array in the format that you want to print
f = open("PitScoutResult.html", "w")

#having to actually follow "good" programming rules
f.write("<!doctype html>")
f.write("<html>")
f.write("<body>")

header = H1([], "PitScout Data")
f.write(header.render(pretty = True))

#makes the hyperlink for the team
str1 =  A([Href("#{" + str(columnNamesDic['Team_Key'])+ "}")], "{" + str(columnNamesDic['Team_Key'])+ "}")

for item in newList:
    item[columnNamesDic['Team_Key']] = item[columnNamesDic['Team_Key']][3:]
    f.write(str1.render(pretty = True).format(item[columnNamesDic['Team_Key']]))


#template for each teams info
html = Div([],
        H2([Id("{" + str(columnNamesDic['Team_Key'])+ "}")], "Team Number: {" + str(columnNamesDic['Team_Key'])+ "}"),
        H3([], "AUTO INFO"),
        P([], "Has Auto: {" + str(columnNamesDic['Has_Autonomous'])+ "}"),
        P([], "Wants Help Creating an Auto: {" + str(columnNamesDic['Help_With_Auto'])+ "}"),
        P([], "Coding Language Used: {" + str(columnNamesDic['Coding_Language'])+ "}"),
        P([], "Can Shoot: {" + str(columnNamesDic['Shoots_Auto'])+ "}"),
        P([], "Shots Made Out of Ten: {" + str(columnNamesDic['Percent_Auto_Shots'])+ "}"),
        P([], "Amount of Balls Picked up or Shot: {" + str(columnNamesDic['Balls_Picked_Or_Shot_Auto'])+ "}"),
        P([], "Human Player Shots Made Out of Ten: {" + str(columnNamesDic['Human_Player_Accuracy'])+ "}"),
                
        H3([], "TELE OP INFO"),
        P([], "Can Shoot: {" + str(columnNamesDic['Can_Shoot'])+ "}"),
        P([], "Shots Made Out of Ten: {" + str(columnNamesDic['Shooting_Accuracy'])+ "}"),
        P([], "Preferred Goal: {" + str(columnNamesDic['Preferred_Goal'])+ "}"),
        P([], "Can Play Defense: {" + str(columnNamesDic['Can_Play_Defense'])+ "}"),
                   
        H3([], "END GAME INFO"),
        P([], "Can Robot Hang: {" + str(columnNamesDic['Can_Robot_Hang'])+ "}"),
        P([], "Highest Bar Robot can Hang on: {" + str(columnNamesDic['Highest_Hang_Bar'])+ "}"),
        P([], "Time to Hang: {" + str(columnNamesDic['Hang_Time'])+ "}"),
        P([], "Preferred Hanging Spot: {" + str(columnNamesDic['Preferred_Hanging_Spot'])+ "}"),
        P([], "Does Robot has Side Swing: {" + str(columnNamesDic['Side_Swing'])+ "}"),
                   
        H3([], "TEAM INFO"),
        P([], "Driver Experience: {" + str(columnNamesDic['Driver_Experience'])+ "}"),
        P([], "Operator Experience: {" + str(columnNamesDic['Operator_Experience'])+ "}"),
        P([], "Extra Notes: {" + str(columnNamesDic['Extra_Notes'])+ "}"),
                
        )
    
#Writing each teams Info
for item in newList:
    if len(item) > 1:  
        f.write(html.render(pretty = True).format(*item))
        
#back to the "good" programming rules
f.write("</body>")
f.write("</html>")
f.close()

