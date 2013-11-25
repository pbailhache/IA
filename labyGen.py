import sys,os,random

#UTILISATION
#
# python ./labyGen 
# Créé un fichier lab*I* 
#
# VOus pouvez modifier l'algo de génération de labyrinthe à vos risques et périls.
#
#UTILISATION

#ICI ON REDEFINI LES VARIABLES DU LABYRINTHE (POSSIBLE DE LES CHANGER A LA MAIN DANS LE FICHIER POUR FAIRE D'AUTRE TESTS)

xPlayer = 1
yPlayer = 1
xExit = 9
yExit = 9
wLaby = 10
hLaby = 10

 

class Entity(object):

        def __init__(self,pos_x,pos_y):
                self.x = pos_x
                self.y = pos_y

class Cell(Entity):
        def __init__(self,pos_x,pos_y):
                Entity.__init__(self,pos_x,pos_y)
                self.type = 0 # EMPTY

class Player(Entity):
        def __init__(self,pos_x,pos_y):
                Entity.__init__(self,pos_x,pos_y)

class Labyrinthe(object):
        def __init__(self,h,w):
                self.grid = []
                for i in range(h+1):
                        row = []
                        for j in range(w+1):
                                row.append(Cell(i,j))
                        self.grid.append(row)
                self.h = h
                self.w = w
                self.S = 1
                self.E = 2
                self.HORIZONTAL = 1
                self.VERTICAL = 2

                # print self.grid[20][20].type 
                for i in range(1,w-1):
                        for j in range(1,h-1):
                                self.grid[i][j].type = 1

                divide(self, self.grid, 1, 1, w-2, h-2)

def choose_orientation(width, height):
                if width < height:
                        return 1
                elif height < width:
                        return 2
                else:
                        return (1 if random.random() > 0.5 else 2)

def getRandom(x,n):
        return random.randint(x,n)

def divide(self, grid, x, y, width, height):

        if width == 2 and height == 2:

                if random.random() > 0.5:
                        dx = 1
                        dy = 0
                else:
                        dx = 0
                        dy = 1

                wall_x = x if dx else getRandom(x,x+width)
                wall_y = y if dy else getRandom(y,y+height)


                lenght = height if dy else width

                passage_x = wall_x if dy else getRandom(x,x+width) 
                passage_y = wall_y if dx else getRandom(y,y+height)

                passage3_x = passage_x
                passage3_y = passage_y

                if dy and grid[wall_x][wall_y-1].type == 1:
                        passage3_x = wall_x
                        # print 
                        passage3_y = wall_y

                if dx and grid[wall_x-1][wall_y].type == 1:
                        passage3_x = wall_x
                        passage3_y = wall_y

                passage2_x = passage_x
                passage2_y = passage_y

                if dx and grid[x+width][wall_y].type == 1 :
                        passage2_x = x+width-1
                        # print "useful 3"
                        passage2_y = wall_y

                if dy and grid[wall_x][y+height].type == 1 :
                        passage2_x = wall_x
                        # print "useful 4"
                        passage2_y = y+height-1

                i = wall_x
                j = wall_y

                # print "Juste avant la boucle on a x=",x,"y=",y,"wall_x=",wall_x,"wall_y=",wall_y,"passage_x=",passage_x,"passage_y=",passage_y,"lenght",lenght

                for k in range(1,lenght+1):
                        if (i != passage_x or j != passage_y) and (i != passage2_x or j!=passage2_y) and (i!=passage3_x or j!=passage3_y):
                                grid[i][j].type = 0
                        i += dx
                        j += dy

        elif width <= 2 or height <= 2:
                return None
        else :
                if width < height:
                        dx = 1
                        dy = 0
                elif height < width:
                        dx = 0
                        dy = 1
                elif random.random() > 0.5:
                        dx = 1
                        dy = 0
                else:
                        dx = 0
                        dy = 1

                wall_x = x if dx else getRandom(x+1,x+width-2)
                wall_y = y if dy else getRandom(y+1,y+height-2)


                lenght = height if dy else width

                passage_x = wall_x if dy else getRandom(x+1,x+width-2) 
                passage_y = wall_y if dx else getRandom(y+1,y+height-2)

                passage3_x = wall_x if dy else getRandom(x+1,x+width-2) 
                passage3_y = wall_y if dx else getRandom(y+1,y+height-2)

                if dy and grid[wall_x][wall_y-1].type == 1:
                        passage3_x = wall_x
                        # print 
                        passage3_y = wall_y

                if dx and grid[wall_x-1][wall_y].type == 1:
                        passage3_x = wall_x
                        passage3_y = wall_y

                passage2_x = passage_x
                passage2_y = passage_y

                if dx and grid[x+width][wall_y].type == 1 :
                        passage2_x = x+width-1
                        # print "useful 3"
                        passage2_y = wall_y

                if dy and grid[wall_x][y+height].type == 1 :
                        passage2_x = wall_x
                        # print "useful 4"
                        passage2_y = y+height-1

                i = wall_x
                j = wall_y

                # print "Juste avant la boucle on a x=",x,"y=",y,"wall_x=",wall_x,"wall_y=",wall_y,"passage_x=",passage_x,"passage_y=",passage_y,"lenght",lenght

                for k in range(1,lenght+1):
                        if (i != passage_x or j != passage_y) and (i != passage2_x or j!=passage2_y) and (i!=passage3_x or j!=passage3_y):
                                grid[i][j].type = 0
                        i += dx
                        j += dy

                if dy : #horizontale
                        divide(self,grid,x,y,wall_x-x,height)
                        divide(self,grid,wall_x+1,y,x+width-wall_x-1,height)
                else: 
                        divide(self,grid,x,y,width,wall_y-y)
                        divide(self,grid,x,wall_y+1,width,y+height-wall_y-1)


laby = Labyrinthe(wLaby,hLaby)
laby.grid[xExit-1][yExit-1].type = 2
player = Player(xPlayer,yPlayer)

name = "lab"+str(random.randint(0,20))
fileLab = open(name,"w")


fileLab.write(str(xPlayer+1)+"\n"+str(yPlayer+1)+"\n"+str(wLaby)+"\n"+str(hLaby)+"\n")



for i in range(0,laby.w):
        for j in range(0,laby.h):
                fileLab.write(str(-1*(laby.grid[i][j].type-1)))
        fileLab.write("\n")

