package nanque.suleimane.lz78;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Principal {

	public static void main(String[] args) {

		//String[] frame = { "A", "B", "B", "A", "B", "B", "A", "B", "B", "B", "A", "A", "B", "A", "B", "A" };
		
		String[] frame = {"139","149","155","160","162","154","164","192"
						 ,"147","151","152","155","158","154","157","169"
						 ,"142","150","158","158","156","157","162","169"
						 ,"143","151","161","160","152","151","158","163"
						 ,"149","151","157","161","157","157","161","163"
						 ,"143","151","158","161","160","159","164","171"
						 ,"141","157","163","158","155","155","157","163"
						 ,"144","162","163","157","167","174","170","168"};
		
		double entropia = calcularEntropia(frame);
		
		System.out.println("Entropia dados bruto");
		System.out.println(Util.doubleFormat(entropia));

		List<Dado> codigos = codificarLz78(frame);
		
		System.out.println("------------------------------------");
		
		System.out.println("Entropia dados LZ78");
		entropia = calcularEntropia(gerarFrame(codigos));
		
		System.out.println(Util.doubleFormat(entropia));
		
		criarArquivo(codigos);

	}

	private static List<Dado> codificarLz78(String[] frame) {

		List<Dado> dicionario = new ArrayList<Dado>();
		List<Dado> codigos = new ArrayList<Dado>();
		int indice = 0;
		String palavra = "";
		String anterior = "";
		String proximo = "";

		for (int i = 0; i < frame.length; i++) {
			proximo = frame[i];
			palavra = anterior + proximo;
			for (int j = 0; j < dicionario.size(); j++) {
				if (dicionario.get(j).getValor().equals(palavra)) {
					indice = j + 1;
					anterior += proximo;
					palavra = "";
				}
			}
			if (!palavra.equals("")) {
				dicionario.add(new Dado(indice, palavra));
				codigos.add(new Dado(indice, frame[i]));
				indice = 0;
				anterior = "";
			}
			palavra = "";
		}

		return codigos;
	}
	
	private static double calcularEntropia(String[] frame) {
		
		String valor = "";
		double ocorrenciaValor = 0;
		double probabilidade = 0.0;
		double entropia = 0.0;
		
		for (int i = 0; i < frame.length; i++) {
			valor = frame[i];
			for (int j = 0; j < frame.length; j++) {
				if (valor.equals(frame[j])) {
					ocorrenciaValor ++;
				}

			}
			double totalSimblos = frame.length;
			probabilidade = ocorrenciaValor / totalSimblos;
			entropia += -(probabilidade * (Math.log(probabilidade) / Math.log(2)));
			probabilidade = 0.0;
			ocorrenciaValor = 0;

		}
		return entropia;
	}

	private static void criarArquivo(List<Dado> codigos) {
		try {
			OutputStream fos = new FileOutputStream("lz78.txt");
			Writer osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			
			bw.write("LZ=[");
			for(int i = 0; i < codigos.size(); i++) {
				bw.write(codigos.get(i).getIndice().toString() + " ");
				bw.write(codigos.get(i).getValor().toString());
				if(i < codigos.size() - 1) bw.write("; ");
			}
			bw.write("]");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String[] gerarFrame(List<Dado> codigos) {
		String[] valores = new String[codigos.size()];
		for(int i = 0; i < codigos.size(); i++) {
			valores[i] = codigos.get(i).getValor().toString();
		}
		return valores;
	}
}

class Dado {

	private Object indice;
	private Object valor;
	
	public Dado(Object indice, Object valor) {
		this.indice = indice;
		this.valor = valor;
	}

	public Object getIndice() {
		return indice;
	}

	public void setIndice(Object indice) {
		this.indice = indice;
	}

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = valor;
	}
}