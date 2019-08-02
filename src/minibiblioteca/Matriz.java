package minibiblioteca;



import minibiblioteca.Vetor;

public class Matriz {
	private int linha;
	private int coluna;
	private float[][] matriz;
	
	public Matriz() {}
	
	public Matriz(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
		this.matriz = new float[linha][coluna];
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

	public float[][] getMatriz() {
		return matriz;
	}

	public void setMatriz(float[][] matriz) {
		this.matriz = matriz;
	}
	
	public void setMatrizLine(int l, Vetor V) {
		this.matriz[l][0] = V.getVetor()[0];
		this.matriz[l][1] = V.getVetor()[1];
		this.matriz[l][2] = V.getVetor()[2];
	}

	@Override
	public String toString() {
		String retorno = "";
		for(int i = 0; i < this.linha; i++) {
			for(int j = 0; j < this.coluna; j++) {
				retorno += this.matriz[i][j] + " "; 
			}
			retorno += "\n";
		}
		return retorno;
	}
	
	

}
