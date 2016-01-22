package peter.world.explorer;

import org.newdawn.slick.opengl.Texture;

public class Stone extends Block{
	float x,y,z;
	static Texture texture = TextureBank.Instance().GetTextureAtNum(2);
	public Stone(float x, float y, float z) {
		super(x, y, z, texture);
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
