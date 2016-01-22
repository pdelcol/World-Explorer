package peter.world.explorer;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import static org.lwjgl.opengl.GL11.*;

public class Player {
	private Vector3f position;
	
	private float yaw = 180.0f;
	
	private float pitch = 10.0f;
	
	//This is the chunk posititon
	Vector2f chunkPos = new Vector2f(0,0);
	
	public Player(float x, float y, float z)
	{
		position = new Vector3f(x,y,z);
	}
	
	public void yaw(float amt)
	{
		yaw += amt;
	}
	public void pitch(float amt)
	{
		pitch -= amt;
	}
	
	public Vector3f getPosition()
	{
		return position;
	}
	public void setPosition(Vector3f vector)
	{
		position = vector;
	}
	public Vector2f getChunk()
	{
		return chunkPos;
	}
	public void setChunk(Vector2f vector)
	{
		chunkPos = vector;
	}
	public void walkForward(float distance)
	{
	    position.x -= distance * (float)Math.sin(Math.toRadians(yaw));
	    position.z += distance * (float)Math.cos(Math.toRadians(yaw));
	    //position.y += distance * (float)Math.sin(Math.toRadians(pitch));
	}
	 
	public void walkBackwards(float distance)
	{
	    position.x += distance * (float)Math.sin(Math.toRadians(yaw));
	    position.z -= distance * (float)Math.cos(Math.toRadians(yaw));
	    //position.y -= distance * (float)Math.sin(Math.toRadians(pitch));
	}
	 
	public void strafeLeft(float distance)
	{
	    position.x -= distance * (float)Math.sin(Math.toRadians(yaw-90));
	    position.z += distance * (float)Math.cos(Math.toRadians(yaw-90));
	}
	
	public void strafeRight(float distance)
	{
	    position.x -= distance * (float)Math.sin(Math.toRadians(yaw+90));
	    position.z += distance * (float)Math.cos(Math.toRadians(yaw+90));
	}
	
	public void lookThrough()
    {
        //roatate the pitch around the X axis
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        //roatate the yaw around the Y axis
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        //translate to the position vector's location
        glTranslatef(position.x, position.y, position.z);
    }
	public void Update(Map map, float dt)
	{
		try{
		if(map.map[-(int) position.x][-(int) position.z] < -position.y - 4)
			position.y += .5f;
		if(map.map[-(int) position.x][-(int) position.z] > -position.y - 4)
			position.y = -map.map[-(int) position.x][-(int) position.z] - 4;
		}catch(Exception e){
			//System.err.println(e);
		}
	}
	public void Jump(float dt, Map map)
	{
		
		try{
		if(position.y == -map.map[(int) -position.x][(int) -position.z] - 4)
			position.y -= 2;
		}catch(Exception e){
			//System.err.println(e);
		}
	}
}
