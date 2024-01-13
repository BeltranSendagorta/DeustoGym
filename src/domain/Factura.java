package domain;

import java.util.ArrayList;
import java.util.List;

import main.DeustoGym;

public class Factura {
	private Usuario usuario;
	private int precioFinal = 0;
	private List<Entrenamiento> entrenamientosExtra = new ArrayList<>();
	private List<String> pagos = new ArrayList<>();
	private boolean pagado = false;
	public Factura(Usuario usuario, List<Entrenamiento> entrenamientosExtra) {
		super();
		this.usuario = usuario;
		this.entrenamientosExtra = entrenamientosExtra;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<Entrenamiento> getEntrenamientosExtra() {
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
		return precioFinal;
	}
	public void setPrecioFinal(int precioFinal) {
		this.precioFinal = precioFinal;
	}
	public List<String> getPagos() {
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
	
	public void calcularPago() {
		this.precioFinal = this.usuario.getS().getTp().getPrecio() - (this.usuario.getS().getTp().getPrecio()* (this.usuario.getS().getDescuento()/100));
		int ent = 0;
		for(Entrenamiento e : DeustoGym.listEntrenamientos) {
			for(Usuario u: e.getAsistentes()) {
				if(u.equals(this.usuario)) {
					if(ent >= this.usuario.getS().getTp().getMaxE()) {
						this.precioFinal+=e.getPrecio();
						this.entrenamientosExtra.add(e);
						this.pagos.add(e.toString()+"\\precio: "+ e.getPrecio()+"€");
					}else {
						ent++;
					}
				}
			}
		}
	}
}
