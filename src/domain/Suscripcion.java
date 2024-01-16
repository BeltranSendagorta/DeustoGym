package domain;

public class Suscripcion {
	private TipoSuscripcion tp;
	private int descuento;
	
	public Suscripcion(TipoSuscripcion tp, int descuento) {
		super();
		this.tp = tp;
		this.descuento = descuento;
	}

	public TipoSuscripcion getTp() {
		return tp;
	}

	public void setTp(TipoSuscripcion tp) {
		this.tp = tp;
	}

	public int getDescuento() {
		return descuento;
	}

	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}
	
}
