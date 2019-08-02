package minibiblioteca;

public class Camera {
	private float[] pontoC;
	private Vetor NVetor;
	private Vetor V;
	private float escalarD;
	private float escalarHX;
	private float escalarHY;
	
	private int[] Iamb;
	private int[] Il;
	private float ka;
	private float ks;
	private float n;
	private Vetor Kd;
	private Vetor Od;
	private float[] Pl;
	
	
	
	public Camera() {}
	
	public Camera(float[] pontoC, Vetor NVetor, Vetor V, float escalarD, float escalarHX, float escalarHY, 
			int[] Iamb, int[] Il, float ka, float ks, float n, Vetor Kd, Vetor Od, float[] Pl) {
		super();
		this.pontoC = pontoC;
		this.NVetor = NVetor;
		this.V = V;
		this.escalarD = escalarD;
		this.escalarHX = escalarHX;
		this.escalarHY = escalarHY;
		this.Iamb = Iamb;
		this.Il = Il;
		this.ka = ka;
		this.ks = ks;
		this.n = n;
		this.Kd = Kd;
		this.Od = Od;
		this.Pl = Pl;
	}

	public float[] getPontoC() {
		return pontoC;
	}

	public void setPontoC(float[] pontoC) {
		this.pontoC = pontoC;
	}

	public Vetor getNVetor() {
		return this.NVetor;
	}

	public void setNVetor(Vetor nVetor) {
		this.NVetor = nVetor;
	}

	public Vetor getV() {
		return V;
	}

	public void setV(Vetor v) {
		V = v;
	}

	public float getEscalarD() {
		return escalarD;
	}

	public void setEscalarD(float escalarD) {
		this.escalarD = escalarD;
	}

	public float getEscalarHX() {
		return escalarHX;
	}

	public void setEscalarHX(float escalarHX) {
		this.escalarHX = escalarHX;
	}

	public float getEscalarHY() {
		return escalarHY;
	}

	public void setEscalarHY(float escalarHY) {
		this.escalarHY = escalarHY;
	}
	
	//PARTE 3

	public int[] getIamb() {
		return Iamb;
	}

	public void setIamb(int[] iamb) {
		Iamb = iamb;
	}

	public int[] getIl() {
		return Il;
	}

	public void setIl(int[] il) {
		Il = il;
	}

	public float getKa() {
		return ka;
	}

	public void setKa(float ka) {
		this.ka = ka;
	}

	public float getKs() {
		return ks;
	}

	public void setKs(float ks) {
		this.ks = ks;
	}
	
	public float getN() {
		return n;
	}

	public void setN(float n) {
		this.n = n;
	}

	public Vetor getKd() {
		return Kd;
	}

	public void setKd(Vetor kd) {
		Kd = kd;
	}

	public Vetor getOd() {
		return Od;
	}

	public void setOd(Vetor od) {
		Od = od;
	}

	public float[] getPl() {
		return Pl;
	}

	public void setPl(float[] pl) {
		Pl = pl;
	}

	@Override
	public String toString() {
		return "Camera \n[\npontoC = [" + pontoC[0] + ", " + pontoC[1] + ", " + pontoC[2] + "]" + ",\nVetor N = " + getNVetor() + ",\nVetor V = " + V + ",\nescalarD = " + escalarD + ",\nEscalarHX = " + escalarHX + ",\nEscalarHY = " + escalarHY + "\n]\n" + 
	"Parte 3:\n" + "Iamb: [" + getIamb()[0] + ", " + getIamb()[1] + ", " + getIamb()[2] + "]\n" + 
				"Il: [" + getIl()[0] + ", " + getIl()[1] + ", " + getIl()[2] + "]\n" + 
				"Ka: " + getKa() + ", Ks: " + getKs() + ", n: " + getN() + "\n" + 
				"Kd: " + getKd().toString() + 
				"Od: " + getOd().toString() + 
				"Pl: (" + getPl()[0] + ", " + getPl()[1] + ", " + getPl()[2] + ")\n";
	}
	
	
	
	
	

}
