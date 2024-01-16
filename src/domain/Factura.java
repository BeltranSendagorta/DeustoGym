package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import bd.BaseDatos;

public class Factura implements Serializable{
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private int precioFinal = 0;
	private List<Entrenamiento> entrenamientosExtra = new ArrayList<>();
	private List<String> pagos = new ArrayList<>();
	private boolean pagado = false;
	
	public Factura(Usuario usuario) {
		super();
		this.usuario = usuario;
		if(this.usuario.getS().getTp().getMaxE() != 0)
			calcularEntrExtr();
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<Entrenamiento> getEntrenamientosExtra() {
		if(this.usuario.getS().getTp().getMaxE() != 0)
		calcularEntrExtr();
		return entrenamientosExtra;
	}
	public void setEntrenamientosExtra(List<Entrenamiento> entrenamientosExtra) {
		this.entrenamientosExtra = entrenamientosExtra;
	}
	public boolean isPagado() {
		return pagado;
	}
	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}
	public int getPrecioFinal() {
		calcularPago();
		return precioFinal;
	}
	public void setPrecioFinal(int precioFinal) {
		this.precioFinal = precioFinal;
	}
	public List<String> getPagos() {
		this.pagos.clear();
		calcularPago();
		return pagos;
	}
	public void setPagos(List<String> pagos) {
		this.pagos = pagos;
	}
	public void pagar() {
		if(!pagado) {
			pagado = true;
		}else System.out.println("Ya esta pagada");
	}
	
	public void calcularEntrExtr() {
		this.entrenamientosExtra.clear();
		int ent = 0;
		for(Entrenamiento e : BaseDatos.entrenamientos) {
			for(Usuario u: e.getAsistentes()) {
				if(u.equals(this.usuario)) {
					if(ent >= this.usuario.getS().getTp().getMaxE()) {
						this.entrenamientosExtra.add(e);
					}else {
						ent++;
					}
				}
			}
		}
	}
	public void calcularPago() {
		if(this.usuario.getS().getTp().getMaxE() != 0)
		calcularEntrExtr();
		this.precioFinal = this.usuario.getS().getTp().getPrecio() - (this.usuario.getS().getTp().getPrecio()* (this.usuario.getS().getDescuento()/100));
		for(Entrenamiento e: this.entrenamientosExtra) {
			this.precioFinal+=e.getPrecio();
			this.pagos.add(e.toString()+"\\precio: "+ e.getPrecio()+"€");
		}
	}
	@Override
	public String toString() {
		return "Factura [usuario=" + usuario + ", precioFinal=" + precioFinal + ", entrenamientosExtra="
				+ entrenamientosExtra + ", pagos=" + pagos + ", pagado=" + pagado + "]";
	}
	
}
