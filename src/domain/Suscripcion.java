package domain;

import java.io.Serializable;

public class Suscripcion implements Serializable{
	private static final long serialVersionUID = 1L;
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
