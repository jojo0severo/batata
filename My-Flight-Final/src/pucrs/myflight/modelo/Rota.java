package pucrs.myflight.modelojojo;

public class Rota implements Comparable<Rota> {
	private CiaAerea cia;
	private Aeroporto origem;
	private Aeroporto destino;
	private Aeronave aeronave;
	private int paradas;

	public Rota(CiaAerea cia, Aeroporto origem, int paradas, Aeroporto destino, Aeronave aeronave) {
		this.cia = cia;
		this.origem = origem;
		this.destino = destino;
		this.aeronave = aeronave;
		this.paradas = paradas;
	}

	public int getParadas() {
		return paradas;
	}

	public void setParadas(int paradas) {
		this.paradas = paradas;
	}

	public CiaAerea getCia() {
		return cia;
	}

	public Aeroporto getDestino() {
		return destino;
	}

	public Aeroporto getOrigem() {
		return origem;
	}

	public Aeronave getAeronave() {
		return aeronave;
	}

	@Override
	public String toString() {
		return "Rota [cia=" + cia + ", origem=" + origem + ", destino=" + destino + ", aeronave=" + aeronave + "]";
	}

	@Override
	public int compareTo(Rota other) {
		return cia.getNome().compareTo(other.getCia().getNome());
	}

}
