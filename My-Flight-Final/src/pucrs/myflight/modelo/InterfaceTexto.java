package pucrs.myflight.modelo;

public class InterfaceTexto {
	private GerenciadorCias gerCia;
	private GerenciadorRotas gerRotas;
	private GerenciadorAeronaves gerAerov;
	private GerenciadorAeroportos gerAerop;

	public InterfaceTexto(GerenciadorCias gerCia, GerenciadorVoos gerVoo, GerenciadorRotas gerRotas, GerenciadorAeronaves gerAerov, GerenciadorAeroportos gerAerop) {
		this.gerCia = gerCia;
		this.gerRotas = gerRotas;
		this.gerAerov = gerAerov;
		this.gerAerop = gerAerop;
	}

<<<<<<< HEAD
	/*
	public void AerovByCod(String cod) {
=======
	/*public void AerovByCod(String cod) {
>>>>>>> branch 'master' of https://jojones@bitbucket.org/jojones/myflyght-jojo.git
		System.out.println(gerAerov.buscarporCodigo(cod));
		
	}

	public void AerovByDesc(String desc) {
		for (Aeronave batata : gerAerov.buscarPorDesc(desc)){
			System.out.println(batata);
		}
	}

	public void CiaByCod(String cod) {
		System.out.println(gerCia.buscarCod(cod));
	}

	public void AeropByCod(String cod) {
		System.out.println(gerAerop.buscarPorCodigo(cod));
	}

	public void RotasByOrig(String cod) {
		for(Rota batata: gerRotas.buscarPorCodOrigem(cod)){
			System.out.println(batata);
		}
	}

	public void RotasByCodCia(String cod) {
		for(Rota batata: gerRotas.buscarPorCodCia(cod)){
			System.out.println(batata);
		}	
	}
<<<<<<< HEAD
	*/
=======

>>>>>>> branch 'master' of https://jojones@bitbucket.org/jojones/myflyght-jojo.git
	/*public void allAerop() {
		for (Aeroporto batata : gerAerop.listarTodos()) {
			System.out.println(batata);
		}
	}

	public void allAerov() {
		for (Aeronave batata : gerAerov.listarTodas()) {
			System.out.println(batata);
		}
	}

	public void allCia() {
		for (CiaAerea batata : gerCia.listarTodas()) {
			System.out.println(batata);
		}
	}*/
}
