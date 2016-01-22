package peter.world.explorer;

import java.io.Serializable;

import org.newdawn.slick.opengl.Texture;

public class Leaf extends Block implements Serializable{
	float x,y,z;
	static Texture texture = TextureBank.Instance().GetTextureAtNum(4);
	public Leaf(float x, float y, float z) {
		super(x, y, z, texture);
		this.x = x;
		this.y = y;
		this.z = z;
		// TODO Auto-generated constructor stub
	}
	public void Update()
	{
		super.Update();
	}
	public void Draw()
	{
		super.Draw();
	}
}
