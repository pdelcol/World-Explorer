package peter.world.explorer;

import java.util.Random;

public class GenerateTerrain {
	

	public float[][] GenerateWhiteNoise(int width, int height)
	{
		Random rand = new Random(System.currentTimeMillis());

		float[][] noise = new float[width][height];
		for(int x = 0; x < noise.length; x++)
		{
			for(int y = 0; y < noise[0].length; y++)
			{
				noise[x][y] = (float) (rand.nextDouble() % 1);
			}
		}
		return noise;
	}
	
	public float[][] GenerateSmoothNoise(float[][] baseNoise, int octave)
	{
		int width = baseNoise.length;
		int height = baseNoise[0].length;
		
		float[][] smoothNoise = new float[width][height];
		// Set up frequency and samples
		int samplePeriod = 1 << octave;
		float sampleFrequency = 1.0f / samplePeriod;
		
		for(int i = 0; i < width; i++)
		{
			//calculate horozontal sampling indicies
			int sample_i0 = (i / samplePeriod) * samplePeriod;
			int sample_i1 = (sample_i0 + samplePeriod) % height; // Wrap
			float horizontal_blend = (i - sample_i0) * sampleFrequency;
			
			for (int j = 0; j < height; j++)
			{
				// Take the sample from vertical blend
				int sample_j0 = (j / samplePeriod) * samplePeriod;
				int sample_j1 = (sample_j0 + samplePeriod) % height;
				float vertical_blend = (j - sample_j0) * sampleFrequency;
				 
		         //blend the top two corners
		         float top = Interpolate(baseNoise[sample_i0][sample_j0],
		            baseNoise[sample_i1][sample_j0], horizontal_blend);
		 
		         //blend the bottom two corners
		         float bottom = Interpolate(baseNoise[sample_i0][sample_j1],
		            baseNoise[sample_i1][sample_j1], horizontal_blend);
		 
		         //final blend
		         smoothNoise[i][j] = Interpolate(top, bottom, vertical_blend);
		      }
		}
		return smoothNoise;
	}
	
	float Interpolate(float x0, float x1, float alpha)
	{
	   return x0 * (1 - alpha) + alpha * x1;
	}
	
	float[][] GeneratePerlinNoise(float[][] baseNoise, int octaveCount)
	{
	   int width = baseNoise.length;
	   int height = baseNoise[0].length;
	 
	   float[][][] smoothNoise = new float[octaveCount][][]; 
	 
	   float persistance = 0.3f;
	 
	   //generate smooth noise
	   for (int i = 0; i < octaveCount; i++)
	   {
	       smoothNoise[i] = GenerateSmoothNoise(baseNoise, i);
	   }
	   
	    float[][] perlinNoise = new float[width][height];
	    float amplitude = 2.0f;
	    float totalAmplitude = 1f;
	 
	    //blend noise together
	    for (int octave = octaveCount - 1; octave >= 0; octave--)
	    {
	       amplitude *= persistance;
	       totalAmplitude += amplitude;
	 
	       for (int i = 0; i < width; i++)
	       {
	          for (int j = 0; j < height; j++)
	          {
	             perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
	          }
	       }
	    }
	 
	   //normalisation
	   for (int i = 0; i < width; i++)
	   {
	      for (int j = 0; j < height; j++)
	      {
	    	  
	          perlinNoise[i][j] /= totalAmplitude;
	      }
	   }
	   // Put into a value that is use able
	   
	   return perlinNoise;
	}
	public float[][] generate1(int SIZE, int octave, float[][] basenoise)
	{
		float[][] perlinNoise = GeneratePerlinNoise(basenoise,octave);
		for(int x = 0; x < perlinNoise[0].length; x++)
		{
			for(int y = 0; y < perlinNoise[0].length; y++)
			{
				perlinNoise[x][y] *= 100;
				perlinNoise[x][y] *= 2;
			}
		}
		return perlinNoise;
	}
	public float[][] generate2(int SIZE, int octave)
	{
		return GeneratePerlinNoise(GenerateWhiteNoise(SIZE,SIZE),octave);
	}
}