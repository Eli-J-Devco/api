/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelHostMetricsEntity extends ModelBaseEntity {
	private double cpu_usage;
	private double io_delay;
	private double load1;
	private double load5;
	private double load15;
	private double ram_usage;
	private double ram_used_mb;
	private double ram_total_mb;
	private double disk_usage;
	private double disk_used_mb;
	private double disk_total_mb;
	private double disk_free_mb;
	private double disk_avail_mb;
	private double swap_usage;
	private double ksm_sharing_mb;
	private double net_rx_bytes_per_sec;
	private double net_tx_bytes_per_sec;
	private double net_rx_total_mb;
	private double net_tx_total_mb;
	private double uptime_seconds;
	private String ip_addresses;
	private String primary_ip;
	private String inf1;
	private String inf2;
	private String inf3;
	private String inf4;
	private String inf5;
	
	public double getCpu_usage() {
		return cpu_usage;
	}
	public void setCpu_usage(double cpu_usage) {
		this.cpu_usage = cpu_usage;
	}
	public double getIo_delay() {
		return io_delay;
	}
	public void setIo_delay(double io_delay) {
		this.io_delay = io_delay;
	}
	public double getLoad1() {
		return load1;
	}
	public void setLoad1(double load1) {
		this.load1 = load1;
	}
	public double getLoad5() {
		return load5;
	}
	public void setLoad5(double load5) {
		this.load5 = load5;
	}
	public double getLoad15() {
		return load15;
	}
	public void setLoad15(double load15) {
		this.load15 = load15;
	}
	public double getRam_usage() {
		return ram_usage;
	}
	public void setRam_usage(double ram_usage) {
		this.ram_usage = ram_usage;
	}
	public double getRam_used_mb() {
		return ram_used_mb;
	}
	public void setRam_used_mb(double ram_used_mb) {
		this.ram_used_mb = ram_used_mb;
	}
	public double getRam_total_mb() {
		return ram_total_mb;
	}
	public void setRam_total_mb(double ram_total_mb) {
		this.ram_total_mb = ram_total_mb;
	}
	public double getDisk_usage() {
		return disk_usage;
	}
	public void setDisk_usage(double disk_usage) {
		this.disk_usage = disk_usage;
	}
	public double getDisk_used_mb() {
		return disk_used_mb;
	}
	public void setDisk_used_mb(double disk_used_mb) {
		this.disk_used_mb = disk_used_mb;
	}
	public double getDisk_total_mb() {
		return disk_total_mb;
	}
	public void setDisk_total_mb(double disk_total_mb) {
		this.disk_total_mb = disk_total_mb;
	}
	public double getDisk_free_mb() {
		return disk_free_mb;
	}
	public void setDisk_free_mb(double disk_free_mb) {
		this.disk_free_mb = disk_free_mb;
	}
	public double getDisk_avail_mb() {
		return disk_avail_mb;
	}
	public void setDisk_avail_mb(double disk_avail_mb) {
		this.disk_avail_mb = disk_avail_mb;
	}
	public double getSwap_usage() {
		return swap_usage;
	}
	public void setSwap_usage(double swap_usage) {
		this.swap_usage = swap_usage;
	}
	public double getKsm_sharing_mb() {
		return ksm_sharing_mb;
	}
	public void setKsm_sharing_mb(double ksm_sharing_mb) {
		this.ksm_sharing_mb = ksm_sharing_mb;
	}
	public double getNet_rx_bytes_per_sec() {
		return net_rx_bytes_per_sec;
	}
	public void setNet_rx_bytes_per_sec(double net_rx_bytes_per_sec) {
		this.net_rx_bytes_per_sec = net_rx_bytes_per_sec;
	}
	public double getNet_tx_bytes_per_sec() {
		return net_tx_bytes_per_sec;
	}
	public void setNet_tx_bytes_per_sec(double net_tx_bytes_per_sec) {
		this.net_tx_bytes_per_sec = net_tx_bytes_per_sec;
	}
	public double getNet_rx_total_mb() {
		return net_rx_total_mb;
	}
	public void setNet_rx_total_mb(double net_rx_total_mb) {
		this.net_rx_total_mb = net_rx_total_mb;
	}
	public double getNet_tx_total_mb() {
		return net_tx_total_mb;
	}
	public void setNet_tx_total_mb(double net_tx_total_mb) {
		this.net_tx_total_mb = net_tx_total_mb;
	}
	public double getUptime_seconds() {
		return uptime_seconds;
	}
	public void setUptime_seconds(double uptime_seconds) {
		this.uptime_seconds = uptime_seconds;
	}
	public String getIp_addresses() {
		return ip_addresses;
	}
	public void setIp_addresses(String ip_addresses) {
		this.ip_addresses = ip_addresses;
	}
	public String getPrimary_ip() {
		return primary_ip;
	}
	public void setPrimary_ip(String primary_ip) {
		this.primary_ip = primary_ip;
	}
	public String getInf1() {
		return inf1;
	}
	public void setInf1(String inf1) {
		this.inf1 = inf1;
	}
	public String getInf2() {
		return inf2;
	}
	public void setInf2(String inf2) {
		this.inf2 = inf2;
	}
	public String getInf3() {
		return inf3;
	}
	public void setInf3(String inf3) {
		this.inf3 = inf3;
	}
	public String getInf4() {
		return inf4;
	}
	public void setInf4(String inf4) {
		this.inf4 = inf4;
	}
	public String getInf5() {
		return inf5;
	}
	public void setInf5(String inf5) {
		this.inf5 = inf5;
	}
}
