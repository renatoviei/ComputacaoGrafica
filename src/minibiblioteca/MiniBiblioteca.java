package minibiblioteca;

//Minibiblioteca de funções matemáticas
public class MiniBiblioteca{
	
	//1)a)
	public static Matriz calculaProduto(Matriz a, Matriz b) {
		Matriz retorno = null;		
		if(a.getColuna() == b.getLinha()) { //PODE FAZER A MULTIPLICACAO
			int linha = a.getLinha();
			int coluna = b.getColuna();
			retorno = new Matriz (linha, coluna);
			
			//Multiplicacao
			for(int i = 0; i < linha; i++) {
				for (int j = 0; j < coluna; j++) {
					for(int k = 0; k < a.getColuna(); k++) {
						retorno.getMatriz()[i][j] += (a.getMatriz()[i][k] * b.getMatriz()[k][j]);
					}
				}
			}
		}
		return retorno;
	}

	//1)b)
	public static Vetor subtraiPontos3D(float[] pA, float[] pB) {
		Vetor retorno = null;
		if(pA.length == 3 && pB.length == 3) {
			retorno = new Vetor(3);
			for(short i = 0; i < 3; i++) {
				retorno.getVetor()[i] = pA[i] - pB[i];
			}
		}
		return retorno;
	}
	
	//1)c)
	public static float produtoEscalar2Vetores3D(Vetor a, Vetor b) {
		float retorno = Float.MIN_VALUE;
		if(a.getDimensao() == 3 && b.getDimensao() == 3) {
			retorno = 0;
			for (int i = 0; i < 3; i++) {
				retorno += a.getVetor()[i] * b.getVetor()[i];
			}
		}
		return retorno;
	}
	
	//1)d)
	public static Vetor produtoVetorial(Vetor a, Vetor b) {
		Vetor retorno = null;
		if(a.getDimensao() == 3 && b.getDimensao() == 3) {
			retorno = new Vetor(3);
			float[][] mat = new float[3][3];
			float diaP1, diaP2, diaP3, diaS1, diaS2, diaS3;
			for(int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (i == 0) {
						mat[i][j] = 1;
					}
					else if(i == 1) {
						mat[i][j] = a.getVetor()[j];
					}
					else if(i == 2) {
						mat[i][j] = b.getVetor()[j];
					}
				}
			}
			diaP1 = mat[0][0] * mat[1][1] * mat[2][2];
		    diaP2 = mat[0][1] * mat[1][2] * mat[2][0];
		    diaP3 = mat[0][2] * mat[1][0] * mat[2][1];
		    
		    diaS1 = mat[2][0] * mat[1][1] * mat[0][2];
		    diaS2 = mat[2][1] * mat[1][2] * mat[0][0];
		    diaS3 = mat[2][2] * mat[1][0] * mat[0][1];
		    
		    retorno.getVetor()[0] = diaP1 - diaS2;
		    retorno.getVetor()[1] = diaP2 - diaS3;
		    retorno.getVetor()[2] = diaP3 - diaS1;
			
		}
		return retorno;
	}
	
	//1)e)
	public static double normaDoVetor(Vetor a) {
		double retorno = Double.MIN_VALUE;
		if(a.getDimensao() == 3) {
			retorno = 0;
			for(int i = 0; i < 3; i++) {
				retorno += Math.pow(a.getVetor()[i], 2);
			}
			retorno  = Math.sqrt(retorno);
		}
		return retorno;
	}
	
	//1)f)
	public static Vetor normalizacaoVetor3D(Vetor a) { //retorna vetor 3D com norma unitária
		Vetor retorno = null;
		if(a.getDimensao() == 3) {
			retorno = new Vetor(3);
			double normal = normaDoVetor(a);
			for(int i  = 0; i < 3; i++) {
				retorno.getVetor()[i] = (float) ((1/normal) * a.getVetor()[i]);
			}
		}
		return retorno;
	}
	
	//1)g)
	public static float[] calcBaricentrica(float[] P, float[] A, float[] B, float[] C) {
		float alpha, beta, gama;
		float a, b, c, d, e, f;

		// CALCULO DAS COORDENADAS DA MATRIZ QUE MULTIPLICA ALPHA E BETA
		a = A[0] - C[0];
		b = B[0] - C[0];
		c = A[1] - C[1];
		d = B[1] - C[1];
		e = P[0] - C[0];
		f = P[1] - C[1];

		// CALCULO DA TRANSPOSTA
		a = 1 / (a * d - b * c) * d;
		b = 1 / (a * d - b * c) * (-b);
		c = 1 / (a * d - b * c) * (-c);
		d = 1 / (a * d - b * c) * a;

		// CALCULO DE ALPHA, BETA E GAMA
		alpha = a * e + b * f;
		beta = c * e + d * f;
		gama = 1 - alpha - beta;

		float[] coordenadaBaricentrica = {alpha, beta, gama};

		return coordenadaBaricentrica;
	}
	
	//1)h)
	public static float[] calcCartesiana(float[] P, float[] A, float[] B, float[] C) {
		float[] retorno = new float[2];
		retorno[0] = P[0]*A[0] + P[1]*B[0] + P[2]*C[0];
		retorno[1] = P[0]*A[1] + P[1]*B[1] + P[2]*C[1];
		return retorno;
	}
	
//	TESTES COM OS EXEMPLOS DA LISTA
	public static void main(String[] agrs) {
//		1)a)
		Matriz a = new Matriz (2,3);
		Matriz b = new Matriz (3,2);
		
		a.getMatriz()[0][0] = (float) 1.5;
		a.getMatriz()[0][1] = (float) 2.5;
		a.getMatriz()[0][2] = (float) 3.5;
		a.getMatriz()[1][0] = (float) 4.5;
		a.getMatriz()[1][1] = (float) 5.5;
		a.getMatriz()[1][2] = (float) 6.5;
		
		b.getMatriz()[0][0] = (float) 7.5;
		b.getMatriz()[0][1] = (float) 8.5;
		b.getMatriz()[1][0] = (float) 9.5;
		b.getMatriz()[1][1] = (float) 10.5;
		b.getMatriz()[2][0] = (float) 11.5;
		b.getMatriz()[2][1] = (float) 12.5;
		
		Matriz mult = calculaProduto(a, b);
		
		System.out.println(mult.toString());

//		1)b)				
		float[] pA = {(float) 3.5, (float) 1.5, (float) 2.0};
		float[] pB = {(float) 1.0, (float) 2.0, (float) 1.5};
		
		Vetor ab = subtraiPontos3D(pA, pB);
		
		System.out.println(ab.toString());
		
//		1)c)
		Vetor ac = new Vetor(3);
		Vetor bc = new Vetor(3);
		
		ac.getVetor()[0] = (float) 3.5;
		ac.getVetor()[1] =  (float) 1.5;
		ac.getVetor()[2] = (float) 2.0;
		
		bc.getVetor()[0] = (float) 1.0;
		bc.getVetor()[1] = (float) 2.0;
		bc.getVetor()[2] = (float) 1.5;
		
		System.out.println(produtoEscalar2Vetores3D(ac, bc));
		
//		1)d)
		Vetor ad = new Vetor(3);
		Vetor bd = new Vetor(3);
		
		ad.getVetor()[0] = (float) 3.5;
		ad.getVetor()[1] =  (float) 1.5;
		ad.getVetor()[2] = (float) 2.0;
		
		bd.getVetor()[0] = (float) 1.0;
		bd.getVetor()[1] = (float) 2.0;
		bd.getVetor()[2] = (float) 1.5;
		Vetor c = produtoVetorial(ad, bd);
		System.out.println(c.toString());
		
//		1)e)
		Vetor ae = new Vetor(3);
		
		ae.getVetor()[0] = (float) 3.5;
		ae.getVetor()[1] =  (float) 1.5;
		ae.getVetor()[2] = (float) 2.0;
		
		System.out.println(normaDoVetor(ae));
		
//		1)f)
		Vetor af = new Vetor(3);
		
		af.getVetor()[0] = (float) 3.5;
		af.getVetor()[1] =  (float) 1.5;
		af.getVetor()[2] = (float) 2.0;
		
		Vetor v = normalizacaoVetor3D(af);
		System.out.println(v.toString());
		
//		1)g)
		float[] P1 = {(float) 2,(float) 3};
		float[] A1 = {0, 0};
		float[] B1 = {2, 0};
		float[] C1 = {1, 1};
		
		float[] retorno1 = calcBaricentrica(P1, A1, B1, C1);
		System.out.print("Coord Baricentrica P(");
		for(int i = 0; i < retorno1.length; i++) {
			System.out.print(retorno1[i] + " ");
		}
		System.out.println(")");
		
//		1)h)
		float[] P = {(float)0.5,(float) 0.25,(float) 0.25};
		float[] A = {-1, 1};
		float[] B = {0, -1};
		float[] C = {1, 1};
		
		float[] retorno = calcCartesiana(P, A, B, C);
		
		for(int i = 0; i < retorno.length; i++) {
			System.out.print(retorno[i] + " ");
		}
	}
	
}
