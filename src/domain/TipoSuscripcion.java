package domain;

public enum TipoSuscripcion {
	Basica(7,2), Premium(15,5), Vip(25, 0);
	private int precio;
	private int maxE;
	
	private TipoSuscripcion(int precio, int maxE) {
		this.precio = precio;
		this.maxE = maxE;
	}
	public int getPrecio() {
		return precio;
	}
	public int getMaxE() {
		return maxE;
	}
	
}
