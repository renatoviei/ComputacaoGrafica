package minibiblioteca;



public class Vetor {
	private int dimensao;
	private float[] vetor;
	
	public Vetor () {}
	public Vetor (int d) {
		this.dimensao = d;
		vetor = new float[d];
	}
	public int getDimensao() {
		return dimensao;
	}
	public void setDimensao(int dimensao) {
		this.dimensao = dimensao;
	}
	public float[] getVetor() {
		return vetor;
	}
	public void setVetor(float[] vetor) {
		this.vetor = vetor;
	}
	@Override
	public String toString() {
		String retorno = "(";
		for(int i = 0; i < this.dimensao; i++) {
			retorno += this.vetor[i];
			if(i != (this.dimensao - 1))
				retorno += ", ";
		}
		retorno += ")";
		return retorno;
	}
	
	
}
