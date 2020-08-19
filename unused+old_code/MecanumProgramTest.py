import math
x = 1
y = -.5
speedlurl = 0
speedrull = 0

if x>=0 and y >= 0 :
    speedlurl = math.sqrt((y*y)+(x*x))
elif x<=0 and y<=0:
    speedlurl = -math.sqrt((y*y)+(x*x))
elif x>=0 and y<=0:
    ang = ((math.atan(y/x)*180/3.141592)+360)
    speedlurl = (math.sqrt((y*y)+(x*x))) * ((((math.atan(y/x)*180/3.141592)+360) - 315)/45)
elif x<=0 and y>=0:
    ang = ((math.atan(y/x)*180/3.141592)+360)
    speedlurl = (math.sqrt((y*y)+(x*x))) * ((315 - ((math.atan(y/x)*180/3.141592)+360))/45)

if x<=0 and y >= 0 :
    speedrull = math.sqrt((y*y)+(x*x))
elif x>=0 and y<=0:
    speedrull = -math.sqrt((y*y)+(x*x))
elif x>=0 and y>=0:
    ang = (math.atan(y/x)*180/3.141592)
    speedrull = (math.sqrt((y*y)+(x*x))) * ((ang - 45)/45)
elif x<=0 and y<=0:
    ang = (math.atan(y/x)*180/3.141592)
    speedrull = (math.sqrt((y*y)+(x*x))) * ((45 - ang)/45)

print ("lf: ", end = '')
print(speedlurl)
print ("rr: ", end = '')
print(speedlurl)
print ("rf: ", end = '')
print(speedrull)
print ("lr: ", end = '')
print(speedrull)
