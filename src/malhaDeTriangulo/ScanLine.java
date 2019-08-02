package malhaDeTriangulo;

import java.awt.Color;
import java.util.ArrayList;

import minibiblioteca.Camera;
import minibiblioteca.MiniBiblioteca;
import minibiblioteca.Triangulo;
import minibiblioteca.Vertice;
import minibiblioteca.Vetor;

public class ScanLine {
	
	public static ArrayList<Vertice> scanLine(int numTriangulos, Vertice[] projecao, Triangulo[] triangulos, int W, int H, 
			Camera camera, Vetor[] normalTriangulo, Vetor[] normalVertice, float[][] zBuffer, Vertice[] vertices, Color[][] c) {
		ArrayList<Vertice> retorno = new ArrayList<>(); 
		float aMin;
		float aMax;
		float xMax;
		float xMin;
		
		for(int i = 0; i < numTriangulos; i++) {
			
			
			// VERIFICAÇÃO DOS MÁXIMOS E MÍNIMOS Y
			 
			Vertice vxMaximo = projecao[triangulos[i].getV1() - 1];
			Vertice vxMedio = projecao[triangulos[i].getV2() - 1];
			Vertice vxMinimo = projecao[triangulos[i].getV3() - 1];
			
			float[] pontoA = new float[3];
			pontoA[0] = vxMaximo.getX();
			pontoA[1] = vxMaximo.getY();
			pontoA[2] = camera.getEscalarD();
			float[] pontoB = new float[3];
			pontoB[0] = vxMedio.getX();
			pontoB[1] = vxMedio.getY();
			pontoB[2] = camera.getEscalarD();
			float[] pontoC = new float[3];
			pontoC[0] = vxMinimo.getX();
			pontoC[1] = vxMinimo.getY();
			pontoC[2] = camera.getEscalarD();
			
			
			if(vxMaximo.getY() < vxMedio.getY()) {
				Vertice aux = vxMaximo;
				vxMaximo = vxMedio;
				vxMedio = aux;
			}
			
			if(vxMaximo.getY() < vxMinimo.getY()) {
				Vertice aux = vxMaximo;
				vxMaximo = vxMinimo;
				vxMinimo = aux;
			}
			
			if(vxMedio.getY() < vxMinimo.getY()) {
				Vertice aux = vxMedio;
				vxMedio = vxMinimo;
				vxMinimo = aux;
			}
			
			float[] vertexAux = new float[2];
			vertexAux[0] = vxMaximo.getX() + ((vxMedio.getY() - vxMaximo.getY()) / (vxMinimo.getY() - vxMaximo.getY())) * (vxMinimo.getX() - vxMaximo.getX());
			vertexAux[1] = vxMedio.getY();
			
			if(vxMedio.getX() >= vertexAux[0]) {
				aMax = (vxMedio.getY() - vxMaximo.getY()) / (vxMedio.getX() - vxMaximo.getX());
				aMin = (vertexAux[1] - vxMaximo.getY()) / (vertexAux[0] - vxMaximo.getX());
			} else {
				aMin = (vxMedio.getY() - vxMaximo.getY()) / (vxMedio.getX() - vxMaximo.getX());
				aMax = (vertexAux[1] - vxMaximo.getY()) / (vertexAux[0] - vxMaximo.getX());
			}
			
			xMin = vxMaximo.getX();
			xMax = xMin;
			int yMax = (int) (vxMedio.getY() + 0.5);
			
			for(int y = (int) (vxMaximo.getY() + 0.5); y >= yMax; y--) {
				int xEsq = (int) (xMin + 0.5);
				int xDir = (int) (xMax + 0.5);
				
				while(xEsq <= xDir) {
					if(xEsq >= 0 && xEsq < W && y >= 0 && y < H) {
						float[] pontoP = new float[3];
						pontoP[0] = xEsq;
						pontoP[1] = y;
						pontoP[2] = camera.getEscalarD();
						float[] coodBaricentrica = MiniBiblioteca.calcBaricentrica(pontoP, pontoA, pontoB, pontoC);
						Vetor P = pOriginal(coodBaricentrica, triangulos, i, vertices);
						
						if(P.getVetor()[2] < zBuffer[xEsq][y]) {
							zBuffer[xEsq][y] = P.getVetor()[2];
							int yCor = y;
							int xCor = xEsq;
							cor(camera, coodBaricentrica, vertices, triangulos, i, normalVertice, c, yCor, xCor, P);
							Vertice branco = new Vertice(xEsq, y, 0, false);
							retorno.add(branco);
						}
					}
					xEsq++;
				}
				xMin -= (1.0 / aMin);
				xMax -= (1.0 / aMax);
			}
			
			if(vxMedio.getX() >= vertexAux[0]) {
				aMax = (vxMedio.getY() - vxMinimo.getY()) / (vxMedio.getX() - vxMinimo.getX());
				aMin = (vertexAux[1] - vxMinimo.getY()) / (vertexAux[0] - vxMinimo.getX());
			} else {
				aMin = (vxMedio.getY() - vxMinimo.getY()) / (vxMedio.getX() - vxMinimo.getX());
				aMax = (vertexAux[1] - vxMinimo.getY()) / (vertexAux[0] - vxMinimo.getX());
			}
			
			xMin = vxMinimo.getX();
			xMax = xMin;
			
			for(int y = (int) (vxMinimo.getY() + 0.5); y < yMax; y++) {
				int xEsq = (int) (xMin + 0.5);
				int xDir = (int) (xMax + 0.5);
				
				while (xEsq <= xDir) {
					if(xEsq >= 0 && xEsq < W && y >= 0 && y < H) {
						float[] pontoP = new float[3];
						pontoP[0] = xEsq;
						pontoP[1] = y;
						pontoP[2] = camera.getEscalarD();
						float[] coodBaricentrica = MiniBiblioteca.calcBaricentrica(pontoP, pontoA, pontoB, pontoC);
						Vetor P = pOriginal(coodBaricentrica, triangulos, i, vertices);
						
						if(P.getVetor()[2] < zBuffer[xEsq][y]) {
							zBuffer[xEsq][y] = P.getVetor()[2];
							int yCor = y;
							int xCor = xEsq;
							cor(camera, coodBaricentrica, vertices, triangulos, i, normalVertice, c, yCor, xCor, P);
							Vertice branco = new Vertice(xEsq, y, 0, false);
							retorno.add(branco);
						}
					}
					xEsq++;
				}
				xMin += (1.0 / aMin);
				xMax += (1.0 / aMax);
			}
		}
		return retorno;
	}
	
	public static Vetor pOriginal(float[] coordBaricentricas, Triangulo[] triangulos, int i, Vertice[] vertices) {
		Vetor retorno = new Vetor(3);
		
		float P1x = coordBaricentricas[0] * vertices[triangulos[i].getV1() - 1].getX();
		float P1y = coordBaricentricas[0] * vertices[triangulos[i].getV1() - 1].getY();
		float P1z = coordBaricentricas[0] * vertices[triangulos[i].getV1() - 1].getZ();
		
		float P2x = coordBaricentricas[1] * vertices[triangulos[i].getV2() - 1].getX();
		float P2y = coordBaricentricas[1] * vertices[triangulos[i].getV2() - 1].getY();
		float P2z = coordBaricentricas[1] * vertices[triangulos[i].getV2() - 1].getZ();
		
		float P3x = coordBaricentricas[2] * vertices[triangulos[i].getV3() - 1].getX();
		float P3y = coordBaricentricas[2] * vertices[triangulos[i].getV3() - 1].getY();
		float P3z = coordBaricentricas[2] * vertices[triangulos[i].getV3() - 1].getZ();
		
		P1x = P1x + P2x + P3x;
		P1y = P1y + P2y + P3y;
		P1z = P1z + P2z + P3z;
		
		float[] vetor = new float[3];
		vetor[0] = P1x;
		vetor[1] = P1y;
		vetor[2] = P1z;
		retorno.setVetor(vetor);
		return retorno;		
	}
	
	public static void cor(Camera camera, float[] coordBaricentricas, Vertice[] vertices, Triangulo[] triangulos, 
			int i, Vetor[] normalVertice, Color[][] color, int y, int x, Vetor P) {
		
		
		// ENCONTRA NORMAL DE P (N), NORMALIZA N
		 
		float N1x = coordBaricentricas[0] * normalVertice[triangulos[i].getV1() - 1].getVetor()[0];
		float N1y = coordBaricentricas[0] * normalVertice[triangulos[i].getV1() - 1].getVetor()[1];
		float N1z = coordBaricentricas[0] * normalVertice[triangulos[i].getV1() - 1].getVetor()[2];
		
		float N2x = coordBaricentricas[1] * normalVertice[triangulos[i].getV2() - 1].getVetor()[0];
		float N2y = coordBaricentricas[1] * normalVertice[triangulos[i].getV2() - 1].getVetor()[1];
		float N2z = coordBaricentricas[1] * normalVertice[triangulos[i].getV2() - 1].getVetor()[2];
		
		float N3x = coordBaricentricas[2] * normalVertice[triangulos[i].getV3() - 1].getVetor()[0];
		float N3y = coordBaricentricas[2] * normalVertice[triangulos[i].getV3() - 1].getVetor()[1];
		float N3z = coordBaricentricas[2] * normalVertice[triangulos[i].getV3() - 1].getVetor()[2];
		
		N1x = N1x + N2x + N3x;
		N1y = N1y + N2y + N3y;
		N1z = N1z + N2z + N3z;
		float[] vetorN = {N1x, N1y, N1z};
		Vetor N = new Vetor(3);
		N.setVetor(vetorN);
		N = MiniBiblioteca.normalizacaoVetor3D(N);
		
		
		// ENCONTRA V, NORMALIZA V
		 
		Vetor V = new Vetor(3);
		float[] vetorV = {-P.getVetor()[0], -P.getVetor()[1], -P.getVetor()[2]};
		V.setVetor(vetorV);
		V = MiniBiblioteca.normalizacaoVetor3D(V);
		
		
		// ENCONTRA L = Pl - P, NORMALIZA L
		 
		float[] vetorL = {camera.getPl()[0] - P.getVetor()[0], 
						  camera.getPl()[1] - P.getVetor()[1],
						  camera.getPl()[2] - P.getVetor()[2]};
		Vetor L = new Vetor(3);
		L.setVetor(vetorL);
		L = MiniBiblioteca.normalizacaoVetor3D(L);
		
		
		// ENCONTRAR R - R = 2<N,L> * N - L
		 
		float prodEscNL = MiniBiblioteca.produtoEscalar2Vetores3D(N, L);
		float prodEscVN = MiniBiblioteca.produtoEscalar2Vetores3D(V, N);
		
		boolean isId = true;
		boolean isIs = true;
		
		if(prodEscNL < 0) {
			if(prodEscVN < 0) {
				float[] Nnovo = {-N.getVetor()[0], -N.getVetor()[1], -N.getVetor()[2]};
				N.setVetor(Nnovo);				
			} 
			else {
				isId = false;
				isIs = false;
			}
		}
		
		prodEscNL = MiniBiblioteca.produtoEscalar2Vetores3D(N, L);
		float[] vetorR = {2 * prodEscNL * N.getVetor()[0] - L.getVetor()[0],
						  2 * prodEscNL * N.getVetor()[1] - L.getVetor()[1],
						  2 * prodEscNL * N.getVetor()[2] - L.getVetor()[2]};
		Vetor R = new Vetor(3);
		R.setVetor(vetorR);
		
		
		 // VERIFICAÇAO DE CASOS ESPECIAIS
		 
		float prodEscRV = MiniBiblioteca.produtoEscalar2Vetores3D(R, V);
		if(prodEscRV < 0) {
			isIs = false;
		}
		
		
		 // CALCULO DA COR
		 
		//Ia = Ka * Iamb
		int[] Ia = new int[3];
		Ia[0] = (int) (camera.getKa() * camera.getIamb()[0]);
		Ia[1] = (int) (camera.getKa() * camera.getIamb()[1]);
		Ia[2] = (int) (camera.getKa() * camera.getIamb()[2]);
		
		//Id = <N,L> * kD * Od * Il
		int[] Id = new int[3];
		if(isId) {
			Id[0] = (int) (prodEscNL * camera.getKd().getVetor()[0] * camera.getOd().getVetor()[0] * camera.getIl()[0]);
			Id[1] = (int) (prodEscNL * camera.getKd().getVetor()[1] * camera.getOd().getVetor()[1] * camera.getIl()[1]);
			Id[2] = (int) (prodEscNL * camera.getKd().getVetor()[2] * camera.getOd().getVetor()[2] * camera.getIl()[2]);
		}
		
		//Is = <R,V>^n * Ks * Il
		int[] Is = new int[3];
		if(isIs) {
			Is[0] = (int) (Math.pow(prodEscRV, camera.getN()) * camera.getKs() * camera.getIl()[0]);
			Is[1] = (int) (Math.pow(prodEscRV, camera.getN()) * camera.getKs() * camera.getIl()[1]);
			Is[2] = (int) (Math.pow(prodEscRV, camera.getN()) * camera.getKs() * camera.getIl()[2]);
		}
		
		//I = Ia + Id + Is
		int[] I = new int[3];
		I[0] = Ia[0] + Id[0] + Is[0];
		if(I[0] > 255)
			I[0] = 255;
		I[1] = Ia[1] + Id[1] + Is[1];
		if(I[1] > 255)
			I[1] = 255;
		I[2] = Ia[2] + Id[2] + Is[2];
		if(I[2] > 255)
			I[2] = 255;
		
		color[x][y] = new Color(I[0], I[1], I[2]);
	}
}
