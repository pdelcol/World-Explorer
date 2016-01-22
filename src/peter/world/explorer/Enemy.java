package peter.world.explorer;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class Enemy {
	
	int width = 1;
	int height = 3;
	int length = 1;
	int x, y, z; 
	Texture texture;
	Texture otherTexture;
	Map map;
	int chunkX, chunkY;
	int charNum;
	int health = 100;
	public Enemy(int x, int y, int z, Texture faceTexture, Texture otherTexture, Map map, int EnemyNum)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.texture = faceTexture;
		this.otherTexture = otherTexture;
		this.map = map;
		charNum = EnemyNum;
	}
	public boolean Distance(float x2, float y2)
	{
		System.out.println(Math.sqrt((x-x2)*(x-x2) + (y-y2)*(y-y2)));
		if(Math.sqrt((x-x2)*(x-x2) + (z-y2)*(z-y2)) < 50)
		{
			return true;
		}
		return false;
	}
	public void Damage()
	{
		health-=10;
	}
	public void Update()
	{
		try{
		if(map.map[x][z] < y || y < map.map[x][z])
		{
			y = (int)map.map[x][z] + 1;
		}
		}catch(Exception e){
			//y = (int) map.player.getPosition().y + 3;
		}
		
		if(Distance(map.player.getPosition().x,map.player.getPosition().z))
		{
			if(x < -map.player.getPosition().x)
			{
				x++;
			}
			if(z < -map.player.getPosition().z)
			{
				z++;
			}
			if(x > -map.player.getPosition().x)
			{
				x--;
			}
			if(z > -map.player.getPosition().z)
			{
				z--;
			}
		}
		
	}
	public void Draw()
	{
		texture.bind();
		glBegin(GL_QUADS);
        //FRONT FACE
        glTexCoord3f(0,1,z);
        glVertex3f(x,y,z);

        glTexCoord3f(1,1,z);
        glVertex3f(x + width,y,z);

        glTexCoord3f(1,0,z);
        glVertex3f(x + width,y + height,z);

        glTexCoord3f(0,0,z);
        glVertex3f(x,y + height, z);
        
        glEnd();
        
        otherTexture.bind();
        
        glBegin(GL_QUADS);
        //RIGHT FACE
        glTexCoord3f(0,1,z);
        glVertex3f(x + width,y,z);

        glTexCoord3f(1,1,z - length);
        glVertex3f(x + width,y,z - length);

        glTexCoord3f(1,0,z - length);
        glVertex3f(x + width,y + height,z - length);

        glTexCoord3f(0,0,z);
        glVertex3f(x + width,y + height,z);
        //LEFT FACE
        glTexCoord3f(0,1,z);
        glVertex3f(x,y,z);

        glTexCoord3f(1,1,z - length);
        glVertex3f(x,y,z - length);

        glTexCoord3f(1,0,z - length);
        glVertex3f(x,y + height,z - length);

        glTexCoord3f(0,0,z);
        glVertex3f(x,y + height,z);

        //TOP FACE
        glTexCoord3f(0,1,z - length);
        glVertex3f(x,y + height,z - length);

        glTexCoord3f(1,1,z - length);
        glVertex3f(x + width,y + height,z - length);

        glTexCoord3f(1,0,z);
        glVertex3f(x + width,y + height,z);

        glTexCoord3f(0,0,z);
        glVertex3f(x,y + height,z);

        //BOTTOM FACE
        glTexCoord3f(0,1,z - length);
        glVertex3f(x,y,z - length);

        glTexCoord3f(1,1,z - length);
        glVertex3f(x + width,y,z - length);

        glTexCoord3f(1,0,z);
        glVertex3f(x + width,y,z);

        glTexCoord3f(0,0,z);
        glVertex3f(x,y,z);
        
        //BACK FACE
        glTexCoord3f(0,1,z);
        glVertex3f(x,y,z - length);

        glTexCoord3f(1,1,z);
        glVertex3f(x + width,y,z - length);

        glTexCoord3f(1,0,z);
        glVertex3f(x + width,y + height,z - length);

        glTexCoord3f(0,0,z);
        glVertex3f(x,y + height, z - length);
        
    glEnd();
	}
}
