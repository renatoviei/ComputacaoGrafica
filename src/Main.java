

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;

import malhaDeTriangulo.Desenhar;
import malhaDeTriangulo.ScanLine;
import minibiblioteca.Camera;
import minibiblioteca.Matriz;
import minibiblioteca.MiniBiblioteca;
import minibiblioteca.Triangulo;
import minibiblioteca.Vertice;
import minibiblioteca.Vetor;

public class Main {
	
	private static int w = 850; //width da tela
	private static int h = 850; //height da tela
	private static Vertice[] vertices;
	private static Vertice[] projecao;
	private static Triangulo[] triangulos;
	private static Camera camera = new Camera();
	private static Vetor[] normalTriangulo;
    private static Vetor[] normalVertice;
    private static float[][] zBuffer = new float[w][h];
    private static Color[][] color = new Color[w][h];
    char[][] tela = new char[w][h];

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean opcao = true;
		while(opcao) {
			
			lerArquivoCamera();
			lerArquivoImagem();
			
			
			// RASTERIZAÇÃO DE POLIGONOS (SCANLINE)
			 
			ArrayList<Vertice> retornoScanLine = ScanLine.scanLine(triangulos.length, projecao, triangulos, w, h, camera, normalTriangulo, normalVertice, zBuffer, vertices, color);
			Vertice[] pintar = new Vertice[retornoScanLine.size()];
			for (int i = 0; i < pintar.length; i++) {
				pintar[i] = retornoScanLine.get(i);
			}
			
			
			//PINTAR ELEMENTO
			JFrame frame1 = new JFrame();
			frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame1.setSize(w + 20, h + 49);
			frame1.add(new Desenhar(pintar, w, h, color));
			frame1.setLocationRelativeTo(null);
			frame1.setVisible(true);
			 
			 System.out.println("Para desenhar novamente digite 'd', para parar a execução digite 'p'");
			 String tec = scanner.nextLine();
			 if(tec.equals("p") || tec.equals("P")) {
				 opcao = false;
			 }
		}
	}
	

	public static void lerArquivoImagem() {
		try{
	         BufferedReader br = new BufferedReader(new FileReader("Objetos\\calice2.byu"));
	         if(br.ready()){
	        	 
	        	 
	        	 // VARIÁVEIS PARA ARMAZENAR OS X/Y MAX E MIN
	        	  
	        	 float Xmax = Float.MIN_NORMAL;
		         float Ymax = Float.MIN_NORMAL;
		         float Xmin = Float.MAX_VALUE;
		         float Ymin = Float.MAX_VALUE;
	        	 
		         String linha = br.readLine();
		         String linha1[] = linha.split(" ");
	            
	            
		         int numVertice = Integer.parseInt(linha1[0]);
		         int numTriangulos = Integer.parseInt(linha1[1]);
		         vertices = new Vertice[numVertice];
		         triangulos = new Triangulo[numTriangulos];
		         normalVertice = new Vetor[numVertice];
		         normalTriangulo = new Vetor[numTriangulos];
	            
	            /*
	             * Ler os vertices do arquivo,
	             * calculo o Xmax, Ymax, Xmin e Ymin e
	             * Adiciona o vertice v no vetor de vertices 
	             */

		         for (int i = 0; i < numVertice; i++) {
	            	linha1 = br.readLine().split(" ");
	            	Vertice v  = new Vertice(Float.parseFloat(linha1[0]), Float.parseFloat(linha1[1]), Float.parseFloat(linha1[2]), true);
	            	
	            	if(!(Xmax >= v.getX())) {
	            		Xmax = v.getX();
	            	}
	            	if(!(Xmin <= v.getX())) {
	            		Xmin = v.getX();
	            	}
	            	if(!(Ymax >= v.getY())) {
	            		Ymax = v.getY();
	            	}
	            	if(!(Ymin <= v.getY())) {
	            		Ymin = v.getY();
	            	}
	            	
	            	vertices[i] = v;
	            }
	               
	            
	            
		         
	             // Leitura dos indices que formam os triangulos
	             
		         for (int j = 0; j < numTriangulos; j++) {
	            	linha1 = br.readLine().split(" ");
	            	Triangulo t  = new Triangulo(Integer.parseInt(linha1[0]), Integer.parseInt(linha1[1]), Integer.parseInt(linha1[2]));
	            	triangulos[j] = t;
	            }
	            
	            
	            // ORTOGONALIZAR V
	             
	            float prodEscalarVN = MiniBiblioteca.produtoEscalar2Vetores3D(camera.getV(), camera.getNVetor());
	            float prodEscalarNN = MiniBiblioteca.produtoEscalar2Vetores3D(camera.getNVetor(), camera.getNVetor());
	            float[] coordVlinha = new float[3];
	            coordVlinha[0] = camera.getNVetor().getVetor()[0] * (prodEscalarVN/prodEscalarNN);
	            coordVlinha[1] = camera.getNVetor().getVetor()[1] * (prodEscalarVN/prodEscalarNN); 
	            coordVlinha[2] = camera.getNVetor().getVetor()[2] * (prodEscalarVN/prodEscalarNN);
	            
	            coordVlinha[0] = camera.getV().getVetor()[0] - coordVlinha[0];  
	            coordVlinha[1] = camera.getV().getVetor()[1] - coordVlinha[1];
	            coordVlinha[2] = camera.getV().getVetor()[2] - coordVlinha[2];
	            
	            Vetor vLinha = new Vetor (3);
	            vLinha.setVetor(coordVlinha);
	            camera.setV(vLinha);
	                        
	            
	            // CALCULA U = N X V'
	             
	            Vetor U = MiniBiblioteca.produtoVetorial(camera.getNVetor(), vLinha);
	            
	            
	            // NORMALIZAR VETORES U, V e N
	             
	            U = MiniBiblioteca.normalizacaoVetor3D(U);
	            camera.setV(MiniBiblioteca.normalizacaoVetor3D(camera.getV()));
	            camera.setNVetor(MiniBiblioteca.normalizacaoVetor3D(camera.getNVetor()));
	            
	            
	            //  MATRIZ DE MUDANÇA DE BASE 
	             
	            Matriz matMB = new Matriz(3,3);
	            matMB.setMatrizLine(0, U);             		//LINHA 1 RECEBE U
	            matMB.setMatrizLine(1, camera.getV());		//LINHA 2 RECEBE V
	            matMB.setMatrizLine(2, camera.getNVetor()); //LINHA 3 RECEBE N
	            
	            
	            
	            // PASSA TODOS OS VERTICES PARA COORDENADAS DE VISTA
	             
	            Matriz MBmultP;
	            for(int i = 0; i < vertices.length; i++) {
	            	Matriz matrizP = new Matriz(3,1);
	            	float[] P = new float[3];
	            	P[0] = vertices[i].getX();
	            	P[1] = vertices[i].getY();
	            	P[2] = vertices[i].getZ();
	            	Vetor vetorP = MiniBiblioteca.subtraiPontos3D(P, camera.getPontoC());
	            	
	            	matrizP.getMatriz()[0][0] = vetorP.getVetor()[0];
	            	matrizP.getMatriz()[1][0] = vetorP.getVetor()[1];
	            	matrizP.getMatriz()[2][0] = vetorP.getVetor()[2];
	            	
	            	MBmultP = MiniBiblioteca.calculaProduto(matMB, matrizP);
	            	
	            	vertices[i].setX(MBmultP.getMatriz()[0][0]);
	            	vertices[i].setY(MBmultP.getMatriz()[1][0]);
	            	vertices[i].setZ(MBmultP.getMatriz()[2][0]);
	            }
	            
	            
	            // PROJECAO EM PERSPECTIVA
	             
	            projecao = new Vertice[numVertice];
	            
	            for(int i = 0; i < projecao.length; i++) {
	            	float xP = camera.getEscalarD() * vertices[i].getX() / vertices[i].getZ();
	            	float yP = camera.getEscalarD() * vertices[i].getY() / vertices[i].getZ();	            	
	            	projecao[i] = (new Vertice(xP, yP, 0, false));
	            }
	            
	            
	            // COORDENADAS NORMALIZADAS
	             
	            for (int i = 0; i < projecao.length; i++) {
	            	projecao[i].setX(projecao[i].getX() / camera.getEscalarHX());
	            	projecao[i].setY(projecao[i].getY() / camera.getEscalarHY());
	            }
	            
	            
	            // COORDENADAS DE TELA
	             
	            for(int i = 0; i < projecao.length; i++) {
	            	projecao[i].setX((float)(((projecao[i].getX() + 1) / 2) * w + 0.5));
	            	projecao[i].setY((float)(h - ((projecao[i].getY() + 1) / 2) * h + 0.5));
	            }
	            
	            //PARTE 3
	            
	            
	            // CALCULAR NORMAL DE CADA TRIANGULO
	             
	            float[] ponto1 = new float[3];
	            float[] ponto2 = new float[3];
	            float[] ponto3 = new float[3];
	            for(int i = 0; i < numTriangulos; i++) {
	            	Vertice vx1 = projecao[triangulos[i].getV1() - 1];
	    			Vertice vx2 = projecao[triangulos[i].getV2() - 1];
	    			Vertice vx3 = projecao[triangulos[i].getV3() - 1];
	    			
	    			ponto1[0] = vx1.getX();
	    			ponto1[1] = vx1.getY();
	    			ponto1[2] = vx1.getZ();
	    			ponto2[0] = vx2.getX();
	    			ponto2[1] = vx2.getY();
	    			ponto2[2] = vx2.getZ();
	    			ponto3[0] = vx3.getX();
	    			ponto3[1] = vx3.getY();
	    			ponto3[2] = vx3.getZ();
	    			
	    			Vetor sub21 = MiniBiblioteca.subtraiPontos3D(ponto2, ponto1);
	    			Vetor sub31 = MiniBiblioteca.subtraiPontos3D(ponto3, ponto1);
	    			
	    			normalTriangulo[i] = MiniBiblioteca.normalizacaoVetor3D(MiniBiblioteca.produtoVetorial(sub21, sub31));
	            }
	            
	            
	             // CALCULAR NORMAL DE CADA VERTICE
	             
	            for (int vertex = 0; vertex < numVertice; vertex++) {
	            	float[] somaNormal = new float[3];
	            	somaNormal[0] = 0;
	            	somaNormal[1] = 0;
	            	somaNormal[2] = 0;
	            	for(int triangulo = 0; triangulo < numTriangulos; triangulo++) {
	            		if(vertex == triangulos[triangulo].getV1() || vertex == triangulos[triangulo].getV2() || vertex == triangulos[triangulo].getV3()) {
	            			somaNormal[0] += normalTriangulo[triangulo].getVetor()[0];
	            			somaNormal[1] += normalTriangulo[triangulo].getVetor()[1];
	            			somaNormal[2] += normalTriangulo[triangulo].getVetor()[2];
	            		}
	            	}
	            	Vetor norm = new Vetor(3);
	            	norm.setVetor(somaNormal);
	            	normalVertice[vertex] = MiniBiblioteca.normalizacaoVetor3D(norm);
	            }
	            
	            
	             // PREPARAR Z-BUFFER
	             
	            
	            for(int i = 0; i < zBuffer.length; i++) {
	            	for(int j = 0; j < zBuffer[i].length; j++) {
	            		zBuffer[i][j] = Float.MAX_VALUE;
	            	}
	            }
	            
	            
	            // RASTERIZAR CADA TRIANGULO USANDO SCANLINE
	             
	            
	            for(int i = 0; i < color.length; i++) {
	            	for (int j = 0; j < color[i].length; j++) {
	            		color[i][j] = Color.BLACK;
	            	}
	            }
	         }
	         br.close();
		}catch(IOException ioe){
	         ioe.printStackTrace();
	    }
	}

	public static void lerArquivoCamera() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("Camera\\camera.txt"));
			if(br.ready()) {
				String[] ponto = br.readLine().split(" ");
				float[] P = new float[3];
				P[0] = Float.parseFloat(ponto[0]);
				P[1] = Float.parseFloat(ponto[1]);
				P[2] = Float.parseFloat(ponto[2]);
				
				
				String[] N = br.readLine().split(" ");
				float[] vn = new float[3];
				vn[0] = Float.parseFloat(N[0]);
				vn[1] = Float.parseFloat(N[1]);
				vn[2] = Float.parseFloat(N[2]);
				Vetor vetorN = new Vetor(3);
				vetorN.setVetor(vn);
				
				String[] V = br.readLine().split(" ");
				float[] vv = new float[3];
				vv[0] = Float.parseFloat(V[0]);
				vv[1] = Float.parseFloat(V[1]);
				vv[2] = Float.parseFloat(V[2]);
				Vetor vetorV = new Vetor(3);
				vetorV.setVetor(vv);
				
				
				String[] escalares = br.readLine().split(" ");
				float d = Float.parseFloat(escalares[0]);
				float hx = Float.parseFloat(escalares[1]);
				float hy = Float.parseFloat(escalares[2]);
				
				camera.setPontoC(P);
				camera.setNVetor(vetorN);
				camera.setV(vetorV);
				camera.setEscalarD(d);
				camera.setEscalarHX(hx);
				camera.setEscalarHY(hy);
				
				//PARTE 3
				
				String[] strIam = br.readLine().split(" ");
				int[] Iamb = new int[3];
				Iamb[0] = Integer.parseInt(strIam[0]);
				Iamb[1] = Integer.parseInt(strIam[1]);
				Iamb[2] = Integer.parseInt(strIam[2]);
				
				String[] strIl = br.readLine().split(" ");
				int[] Il = new int[3];
				Il[0] = Integer.parseInt(strIl[0]);
				Il[1] = Integer.parseInt(strIl[1]);
				Il[2] = Integer.parseInt(strIl[2]);
				
				String[] KaKsn = br.readLine().split(" ");
				float Ka = Float.parseFloat(KaKsn[0]);
				float Ks = Float.parseFloat(KaKsn[1]);
				float n = Float.parseFloat(KaKsn[2]);
				
				String[] strKd = br.readLine().split(" ");
				float[] VetorKd = new float[3];
				VetorKd[0] = Float.parseFloat(strKd[0]);
				VetorKd[1] = Float.parseFloat(strKd[1]);
				VetorKd[2] = Float.parseFloat(strKd[2]);
				Vetor Kd = new Vetor(3);
				Kd.setVetor(VetorKd);
				
				String[] strOd = br.readLine().split(" ");
				float[] VetorOd = new float[3];
				VetorOd[0] = Float.parseFloat(strOd[0]);
				VetorOd[1] = Float.parseFloat(strOd[1]);
				VetorOd[2] = Float.parseFloat(strOd[2]);
				Vetor Od = new Vetor(3);
				Od.setVetor(VetorOd);
				
				String[] strPl = br.readLine().split(" ");
				float[] Pl = new float[3];
				Pl[0] = Float.parseFloat(strPl[0]);
				Pl[1] = Float.parseFloat(strPl[1]);
				Pl[2] = Float.parseFloat(strPl[2]);
				
				camera.setIamb(Iamb);
				camera.setIl(Il);
				camera.setKa(Ka);
				camera.setKs(Ks);
				camera.setN(n);
				camera.setKd(Kd);
				camera.setOd(Od);
				camera.setPl(Pl);			
				
				System.out.println(camera.toString());
				System.out.println();
			}
			br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
 
	
}
