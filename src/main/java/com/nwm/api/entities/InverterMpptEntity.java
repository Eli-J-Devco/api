package com.nwm.api.entities;

/** Read model for an MPPT number assigned to a configured string. */
public class InverterMpptEntity {
	private Integer id;
	private Integer id_site;
	private Integer id_device;
	private Integer id_mppt;

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	public Integer getId_site() { return id_site; }
	public void setId_site(Integer id_site) { this.id_site = id_site; }
	public Integer getId_device() { return id_device; }
	public void setId_device(Integer id_device) { this.id_device = id_device; }
	public Integer getId_mppt() { return id_mppt; }
	public void setId_mppt(Integer id_mppt) { this.id_mppt = id_mppt; }
}
