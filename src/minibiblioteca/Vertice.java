package minibiblioteca;

public class Vertice {
	private float x;
	private float y;
	private float z;
	private boolean dim3D; //true - 3D
	
	public Vertice(float x, float y, float z, boolean dim) {
		this.x = x;
		this.y = y;
		this.dim3D = dim;
		if(dim == true)
			this.z = z;
		else
			this.z = 0;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public boolean isDim3D() {
		return dim3D;
	}

	public void setDim3D(boolean dim3d) {
		dim3D = dim3d;
	}

	@Override
	public String toString() {
		String retorno  = "";
		if(this.dim3D) {
			retorno  = "Vertice [x=" + x + ", y=" + y + ", z=" + z + ", 3D]";
		}
		else
			retorno  = "Vertice [x=" + x + ", y=" + y + ", 2D]";
		 
		return retorno;
	}

}
