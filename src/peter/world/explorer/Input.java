package peter.world.explorer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Input {
	float mouseSensitivity;
	float movementSpeed;
	float dx,dy;
	Player camera;
	Map map;
	
	public Input(float mouseSensitivity, float movementSpeed, Player camera, Map map)
	{
		this.mouseSensitivity = mouseSensitivity;
		this.movementSpeed = movementSpeed;
		this.camera = camera;
		this.map = map;
	}
	
	public void Update(float dt)
	{
		dx = Mouse.getDX();
		dy = Mouse.getDY();
		
		camera.yaw(dx * mouseSensitivity);
		camera.pitch(dy * mouseSensitivity);
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) 
		{
			camera.walkForward(movementSpeed);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			camera.walkBackwards(movementSpeed);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			camera.strafeLeft(movementSpeed);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			camera.strafeRight(movementSpeed);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		{
			//map.GenerateTerrain();
			for(int i = 0; i < map.enemies.size(); i++)
			{
				if(map.enemies.get(i).Distance(camera.getPosition().x, camera.getPosition().z))
				{
					map.enemies.get(i).Damage();
				}
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			movementSpeed = 10.0f;
		}
		else
		{
			movementSpeed = .5f;
		}
//		if(Keyboard.isKeyDown(Keyboard.KEY_Q))
//		{
//			isWhite = !isWhite;
//		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Z))
		{
			camera.Jump(dt, map);
		}
	}
}
