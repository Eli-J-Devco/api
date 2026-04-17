/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import java.util.List;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.nwm.api.entities.BaseAlertEnum;
import org.springframework.stereotype.Service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelSungrowSh6250hvMvEntity;
import com.nwm.api.utils.Lib;

@Service
public class ModelSungrowSh6250hvMvService extends DB {

	TriggerAlertService service = new TriggerAlertService();

	enum AlertEnum implements BaseAlertEnum {
		m1_alarm_state_accircuitbreakerabnormal(2568, "m1_alarm_state_accircuitbreakerabnormal"),
		m1_alarm_state_acspdalarm(2569, "m1_alarm_state_acspdalarm"),
		m1_alarm_state_antipidpowersupplyabnormal(2570, "m1_alarm_state_antipidpowersupplyabnormal"),
		m1_alarm_state_bypasscircuitbreakerabnormal(2571, "m1_alarm_state_bypasscircuitbreakerabnormal"),
		m1_alarm_state_bypassfuseabnormal(2572, "m1_alarm_state_bypassfuseabnormal"),
		m1_alarm_state_contactorcontactabnormal(2573, "m1_alarm_state_contactorcontactabnormal"),
		m1_alarm_state_ctunbalance(2574, "m1_alarm_state_ctunbalance"),
		m1_alarm_state_dcbypassforwardovercurrentalarm(2575, "m1_alarm_state_dcbypassforwardovercurrentalarm"),
		m1_alarm_state_dcbypassreserveovercurrentalarm(2576, "m1_alarm_state_dcbypassreserveovercurrentalarm"),
		m1_alarm_state_dcfuseabnormal(2577, "m1_alarm_state_dcfuseabnormal"),
		m1_alarm_state_dcsensorabnormal(2578, "m1_alarm_state_dcsensorabnormal"),
		m1_alarm_state_dcspdalarm(2579, "m1_alarm_state_dcspdalarm"),
		m1_alarm_state_dcswitchabnormal(2580, "m1_alarm_state_dcswitchabnormal"),
		m1_alarm_state_dspmdccommunicationabnormal(2581, "m1_alarm_state_dspmdccommunicationabnormal"),
		m1_alarm_state_energymetercommunicationabnormal(2582, "m1_alarm_state_energymetercommunicationabnormal"),
		m1_alarm_state_externalpowersupplyabnormal(2583, "m1_alarm_state_externalpowersupplyabnormal"),
		m1_alarm_state_fan2anomaly(2584, "m1_alarm_state_fan2anomaly"),
		m1_alarm_state_fanabnormal(2585, "m1_alarm_state_fanabnormal"),
		m1_alarm_state_frequencydeviationactivepowerregulation(2586, "m1_alarm_state_frequencydeviationactivepowerregulation"),
		m1_alarm_state_gfrtoperation(2587, "m1_alarm_state_gfrtoperation"),
		m1_alarm_state_groundfuseabnormal(2588, "m1_alarm_state_groundfuseabnormal"),
		m1_alarm_state_insulationboardcommunicationanomaly(2589, "m1_alarm_state_insulationboardcommunicationanomaly"),
		m1_alarm_state_lowinsulationresistance(2590, "m1_alarm_state_lowinsulationresistance"),
		m1_alarm_state_temperatureabnormalalarm(2591, "m1_alarm_state_temperatureabnormalalarm"),
		m1_alarm_state_temperatureandhumidityboardcommerror(2592, "m1_alarm_state_temperatureandhumidityboardcommerror"),
		m1_alarm_state_tributaryboardcommunicationerror(2593, "m1_alarm_state_tributaryboardcommunicationerror"),
		m1_alarm_state_voltagedeviationreactivepowerregulation(2594, "m1_alarm_state_voltagedeviationreactivepowerregulation"),
		m1_fault_state_1_acleakagecurrentprotection(2645, "m1_fault_state_1_acleakagecurrentprotection"),
		m1_fault_state_1_acovercurrent(2646, "m1_fault_state_1_acovercurrent"),
		m1_fault_state_1_acovervoltage(2647, "m1_fault_state_1_acovervoltage"),
		m1_fault_state_1_acundervoltage(2648, "m1_fault_state_1_acundervoltage"),
		m1_fault_state_1_busovervoltage(2649, "m1_fault_state_1_busovervoltage"),
		m1_fault_state_1_busundervoltage(2650, "m1_fault_state_1_busundervoltage"),
		m1_fault_state_1_contactorfault(2651, "m1_fault_state_1_contactorfault"),
		m1_fault_state_1_dcfusefault(2652, "m1_fault_state_1_dcfusefault"),
		m1_fault_state_1_dcleakagecurrentprotection(2653, "m1_fault_state_1_dcleakagecurrentprotection"),
		m1_fault_state_1_dcovercurrent(2654, "m1_fault_state_1_dcovercurrent"),
		m1_fault_state_1_dcovervoltage(2655, "m1_fault_state_1_dcovervoltage"),
		m1_fault_state_1_dcundervoltage(2656, "m1_fault_state_1_dcundervoltage"),
		m1_fault_state_1_detectionfusefault(2657, "m1_fault_state_1_detectionfusefault"),
		m1_fault_state_1_fanfault(2658, "m1_fault_state_1_fanfault"),
		m1_fault_state_1_frequencyabnormal(2659, "m1_fault_state_1_frequencyabnormal"),
		m1_fault_state_1_gfdiprotection(2660, "m1_fault_state_1_gfdiprotection"),
		m1_fault_state_1_groundingfault(2661, "m1_fault_state_1_groundingfault"),
		m1_fault_state_1_hardwarefault(2662, "m1_fault_state_1_hardwarefault"),
		m1_fault_state_1_inverterovervoltage(2663, "m1_fault_state_1_inverterovervoltage"),
		m1_fault_state_1_islandingprotection(2664, "m1_fault_state_1_islandingprotection"),
		m1_fault_state_1_moduleovertemperature(2665, "m1_fault_state_1_moduleovertemperature"),
		m1_fault_state_1_overfrequency(2666, "m1_fault_state_1_overfrequency"),
		m1_fault_state_1_overloadprotection(2667, "m1_fault_state_1_overloadprotection"),
		m1_fault_state_1_pdpprotection(2668, "m1_fault_state_1_pdpprotection"),
		m1_fault_state_1_reactorovertemperature(2669, "m1_fault_state_1_reactorovertemperature"),
		m1_fault_state_1_sensorfailure(2670, "m1_fault_state_1_sensorfailure"),
		m1_fault_state_1_temperatureabnormal(2671, "m1_fault_state_1_temperatureabnormal"),
		m1_fault_state_1_transformerovertemperature(2672, "m1_fault_state_1_transformerovertemperature"),
		m1_fault_state_1_underfrequency(2673, "m1_fault_state_1_underfrequency"),
		m1_fault_state_2_accurrentunbalance(2674, "m1_fault_state_2_accurrentunbalance"),
		m1_fault_state_2_acspdfault(2675, "m1_fault_state_2_acspdfault"),
		m1_fault_state_2_dcspdfault(2676, "m1_fault_state_2_dcspdfault"),
		m1_fault_state_2_pvpolarityreversed(2677, "m1_fault_state_2_pvpolarityreversed"),
		m1_fault_state_2_accabinetovertemperature(2678, "m1_fault_state_2_accabinetovertemperature"),
		m1_fault_state_2_acfusefault(2679, "m1_fault_state_2_acfusefault"),
		m1_fault_state_2_acswitchdisconnection(2680, "m1_fault_state_2_acswitchdisconnection"),
		m1_fault_state_2_acswitchfault(2681, "m1_fault_state_2_acswitchfault"),
		m1_fault_state_2_backuppowersupplyabnormal(2682, "m1_fault_state_2_backuppowersupplyabnormal"),
		m1_fault_state_2_buffercontactorfault(2683, "m1_fault_state_2_buffercontactorfault"),
		m1_fault_state_2_carriersyncfault(2684, "m1_fault_state_2_carriersyncfault"),
		m1_fault_state_2_controlcabinettemperatureabnormal(2685, "m1_fault_state_2_controlcabinettemperatureabnormal"),
		m1_fault_state_2_controlpowersupplyabnormal(2686, "m1_fault_state_2_controlpowersupplyabnormal"),
		m1_fault_state_2_currentunbalance2(2687, "m1_fault_state_2_currentunbalance2"),
		m1_fault_state_2_currentunbalance3(2688, "m1_fault_state_2_currentunbalance3"),
		m1_fault_state_2_dccabinetovertemperature(2689, "m1_fault_state_2_dccabinetovertemperature"),
		m1_fault_state_2_dcfusegroundingfault(2690, "m1_fault_state_2_dcfusegroundingfault"),
		m1_fault_state_2_dcinjectionfault(2691, "m1_fault_state_2_dcinjectionfault"),
		m1_fault_state_2_dcswitchfault(2692, "m1_fault_state_2_dcswitchfault"),
		m1_fault_state_2_dcvoltagesamplingfault(2693, "m1_fault_state_2_dcvoltagesamplingfault"),
		m1_fault_state_2_devicecoderepeatfault(2694, "m1_fault_state_2_devicecoderepeatfault"),
		m1_fault_state_2_driveboardfault(2695, "m1_fault_state_2_driveboardfault"),
		m1_fault_state_2_fan2fault(2696, "m1_fault_state_2_fan2fault"),
		m1_fault_state_2_gridvoltageunbalance(2697, "m1_fault_state_2_gridvoltageunbalance"),
		m1_fault_state_2_insulationimpedance(2698, "m1_fault_state_2_insulationimpedance"),
		m1_fault_state_2_neutralpointpotentialshift(2699, "m1_fault_state_2_neutralpointpotentialshift"),
		m1_fault_state_2_paralleloperation(2700, "m1_fault_state_2_paralleloperation"),
		m1_fault_state_2_samplingfault(2701, "m1_fault_state_2_samplingfault"),
		m1_fault_state_2_softstartfault(2702, "m1_fault_state_2_softstartfault"),
		m1_node_state_1_acbfaultstate(2703, "m1_node_state_1_acbfaultstate"),
		m1_work_state_emergencystop(2704, "m1_work_state_emergencystop"),
		m1_work_state_faultstop(2705, "m1_work_state_faultstop"),
		m1_work_state_iodspcommunicationabnormal(2706, "m1_work_state_iodspcommunicationabnormal"),
		m1_work_state_iomdccommunicationabnormal(2707, "m1_work_state_iomdccommunicationabnormal"),
		m1_work_state_stopped(2708, "m1_work_state_stopped"),
		m2_alarm_state_accircuitbreakerabnormal(2709, "m2_alarm_state_accircuitbreakerabnormal"),
		m2_alarm_state_acspdalarm(2710, "m2_alarm_state_acspdalarm"),
		m2_alarm_state_antipidpowersupplyabnormal(2711, "m2_alarm_state_antipidpowersupplyabnormal"),
		m2_alarm_state_bypasscircuitbreakerabnormal(2712, "m2_alarm_state_bypasscircuitbreakerabnormal"),
		m2_alarm_state_bypassfuseabnormal(2713, "m2_alarm_state_bypassfuseabnormal"),
		m2_alarm_state_contactorcontactabnormal(2714, "m2_alarm_state_contactorcontactabnormal"),
		m2_alarm_state_ctunbalance(2715, "m2_alarm_state_ctunbalance"),
		m2_alarm_state_dcbypassforwardovercurrentalarm(2716, "m2_alarm_state_dcbypassforwardovercurrentalarm"),
		m2_alarm_state_dcbypassreserveovercurrentalarm(2717, "m2_alarm_state_dcbypassreserveovercurrentalarm"),
		m2_alarm_state_dcfuseabnormal(2718, "m2_alarm_state_dcfuseabnormal"),
		m2_alarm_state_dcsensorabnormal(2719, "m2_alarm_state_dcsensorabnormal"),
		m2_alarm_state_dcspdalarm(2720, "m2_alarm_state_dcspdalarm"),
		m2_alarm_state_dcswitchabnormal(2721, "m2_alarm_state_dcswitchabnormal"),
		m2_alarm_state_dspmdccommunicationabnormal(2722, "m2_alarm_state_dspmdccommunicationabnormal"),
		m2_alarm_state_energymetercommunicationabnormal(2723, "m2_alarm_state_energymetercommunicationabnormal"),
		m2_alarm_state_externalpowersupplyabnormal(2724, "m2_alarm_state_externalpowersupplyabnormal"),
		m2_alarm_state_fan2anomaly(2725, "m2_alarm_state_fan2anomaly"),
		m2_alarm_state_fanabnormal(2726, "m2_alarm_state_fanabnormal"),
		m2_alarm_state_frequencydeviationactivepowerregulation(2727, "m2_alarm_state_frequencydeviationactivepowerregulation"),
		m2_alarm_state_gfrtoperation(2728, "m2_alarm_state_gfrtoperation"),
		m2_alarm_state_groundfuseabnormal(2729, "m2_alarm_state_groundfuseabnormal"),
		m2_alarm_state_insulationboardcommunicationanomaly(2730, "m2_alarm_state_insulationboardcommunicationanomaly"),
		m2_alarm_state_lowinsulationresistance(2731, "m2_alarm_state_lowinsulationresistance"),
		m2_alarm_state_temperatureabnormalalarm(2732, "m2_alarm_state_temperatureabnormalalarm"),
		m2_alarm_state_temperatureandhumidityboardcommerror(2733, "m2_alarm_state_temperatureandhumidityboardcommerror"),
		m2_alarm_state_tributaryboardcommunicationerror(2734, "m2_alarm_state_tributaryboardcommunicationerror"),
		m2_alarm_state_voltagedeviationreactivepowerregulation(2735, "m2_alarm_state_voltagedeviationreactivepowerregulation"),
		m2_fault_state_1_acleakagecurrentprotection(2736, "m2_fault_state_1_acleakagecurrentprotection"),
		m2_fault_state_1_acovercurrent(2737, "m2_fault_state_1_acovercurrent"),
		m2_fault_state_1_acovervoltage(2738, "m2_fault_state_1_acovervoltage"),
		m2_fault_state_1_acundervoltage(2739, "m2_fault_state_1_acundervoltage"),
		m2_fault_state_1_busovervoltage(2740, "m2_fault_state_1_busovervoltage"),
		m2_fault_state_1_busundervoltage(2741, "m2_fault_state_1_busundervoltage"),
		m2_fault_state_1_contactorfault(2742, "m2_fault_state_1_contactorfault"),
		m2_fault_state_1_dcfusefault(2743, "m2_fault_state_1_dcfusefault"),
		m2_fault_state_1_dcleakagecurrentprotection(2744, "m2_fault_state_1_dcleakagecurrentprotection"),
		m2_fault_state_1_dcovercurrent(2745, "m2_fault_state_1_dcovercurrent"),
		m2_fault_state_1_dcovervoltage(2746, "m2_fault_state_1_dcovervoltage"),
		m2_fault_state_1_dcundervoltage(2747, "m2_fault_state_1_dcundervoltage"),
		m2_fault_state_1_detectionfusefault(2748, "m2_fault_state_1_detectionfusefault"),
		m2_fault_state_1_fanfault(2749, "m2_fault_state_1_fanfault"),
		m2_fault_state_1_frequencyabnormal(2750, "m2_fault_state_1_frequencyabnormal"),
		m2_fault_state_1_gfdiprotection(2751, "m2_fault_state_1_gfdiprotection"),
		m2_fault_state_1_groundingfault(2752, "m2_fault_state_1_groundingfault"),
		m2_fault_state_1_hardwarefault(2753, "m2_fault_state_1_hardwarefault"),
		m2_fault_state_1_inverterovervoltage(2754, "m2_fault_state_1_inverterovervoltage"),
		m2_fault_state_1_islandingprotection(2755, "m2_fault_state_1_islandingprotection"),
		m2_fault_state_1_moduleovertemperature(2756, "m2_fault_state_1_moduleovertemperature"),
		m2_fault_state_1_overfrequency(2757, "m2_fault_state_1_overfrequency"),
		m2_fault_state_1_overloadprotection(2758, "m2_fault_state_1_overloadprotection"),
		m2_fault_state_1_pdpprotection(2759, "m2_fault_state_1_pdpprotection"),
		m2_fault_state_1_reactorovertemperature(2760, "m2_fault_state_1_reactorovertemperature"),
		m2_fault_state_1_sensorfailure(2761, "m2_fault_state_1_sensorfailure"),
		m2_fault_state_1_temperatureabnormal(2762, "m2_fault_state_1_temperatureabnormal"),
		m2_fault_state_1_transformerovertemperature(2763, "m2_fault_state_1_transformerovertemperature"),
		m2_fault_state_1_underfrequency(2764, "m2_fault_state_1_underfrequency"),
		m2_fault_state_2_accurrentunbalance(2765, "m2_fault_state_2_accurrentunbalance"),
		m2_fault_state_2_acspdfault(2766, "m2_fault_state_2_acspdfault"),
		m2_fault_state_2_dcspdfault(2767, "m2_fault_state_2_dcspdfault"),
		m2_fault_state_2_pvpolarityreversed(2768, "m2_fault_state_2_pvpolarityreversed"),
		m2_fault_state_2_accabinetovertemperature(2769, "m2_fault_state_2_accabinetovertemperature"),
		m2_fault_state_2_acfusefault(2770, "m2_fault_state_2_acfusefault"),
		m2_fault_state_2_acswitchdisconnection(2771, "m2_fault_state_2_acswitchdisconnection"),
		m2_fault_state_2_acswitchfault(2772, "m2_fault_state_2_acswitchfault"),
		m2_fault_state_2_backuppowersupplyabnormal(2773, "m2_fault_state_2_backuppowersupplyabnormal"),
		m2_fault_state_2_buffercontactorfault(2774, "m2_fault_state_2_buffercontactorfault"),
		m2_fault_state_2_carriersyncfault(2775, "m2_fault_state_2_carriersyncfault"),
		m2_fault_state_2_controlcabinettemperatureabnormal(2776, "m2_fault_state_2_controlcabinettemperatureabnormal"),
		m2_fault_state_2_controlpowersupplyabnormal(2777, "m2_fault_state_2_controlpowersupplyabnormal"),
		m2_fault_state_2_currentunbalance2(2778, "m2_fault_state_2_currentunbalance2"),
		m2_fault_state_2_currentunbalance3(2779, "m2_fault_state_2_currentunbalance3"),
		m2_fault_state_2_dccabinetovertemperature(2780, "m2_fault_state_2_dccabinetovertemperature"),
		m2_fault_state_2_dcfusegroundingfault(2781, "m2_fault_state_2_dcfusegroundingfault"),
		m2_fault_state_2_dcinjectionfault(2782, "m2_fault_state_2_dcinjectionfault"),
		m2_fault_state_2_dcswitchfault(2783, "m2_fault_state_2_dcswitchfault"),
		m2_fault_state_2_dcvoltagesamplingfault(2784, "m2_fault_state_2_dcvoltagesamplingfault"),
		m2_fault_state_2_devicecoderepeatfault(2785, "m2_fault_state_2_devicecoderepeatfault"),
		m2_fault_state_2_driveboardfault(2786, "m2_fault_state_2_driveboardfault"),
		m2_fault_state_2_fan2fault(2787, "m2_fault_state_2_fan2fault"),
		m2_fault_state_2_gridvoltageunbalance(2788, "m2_fault_state_2_gridvoltageunbalance"),
		m2_fault_state_2_insulationimpedance(2789, "m2_fault_state_2_insulationimpedance"),
		m2_fault_state_2_neutralpointpotentialshift(2790, "m2_fault_state_2_neutralpointpotentialshift"),
		m2_fault_state_2_paralleloperation(2791, "m2_fault_state_2_paralleloperation"),
		m2_fault_state_2_samplingfault(2792, "m2_fault_state_2_samplingfault"),
		m2_fault_state_2_softstartfault(2793, "m2_fault_state_2_softstartfault"),
		m2_work_state_emergencystop(2794, "m2_work_state_emergencystop"),
		m2_work_state_faultstop(2795, "m2_work_state_faultstop"),
		m2_work_state_iodspcommunicationabnormal(2796, "m2_work_state_iodspcommunicationabnormal"),
		m2_work_state_stopped(2797, "m2_work_state_stopped"),
		work_state_door_open_prot(2798, "work_state_door_open_prot"),
		work_state_external_emer_stop(2799, "work_state_external_emer_stop"),
		work_state_local_emer_stop(2800, "work_state_local_emer_stop"),
		work_state_mv_fault(2801, "work_state_mv_fault"),
		work_state_rem_emer_stop(2802, "work_state_rem_emer_stop"),
		work_state_smoke_prot(2803, "work_state_smoke_prot"),
		work_state_stopped(2804, "work_state_stopped");



		private final int id;
		private final String column;

		AlertEnum(int id, String column) {
			this.id = id;
			this.column = column;
		}

		public int getId() {
			return id;
		}

		public String getColumn() {
			return column;
		}
	}

	/**
	 * @description set data 
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data
	 */
	
	public ModelSungrowSh6250hvMvEntity setModelSungrowSh6250hvMv(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSungrowSh6250hvMvEntity dataModel = new ModelSungrowSh6250hvMvEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001");
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setActive_power(power);
				dataModel.setAmp_phase_a(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setAmp_phase_b(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setAmp_phase_c(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setApparent_power(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setCo2_emission(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setDay_yield(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				
				
				dataModel.setDc_current_input_1(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setDc_current_input_2(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setDc_power_input(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setDc_voltage_input_1(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setDc_voltage_input_2(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setFrequency(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setInsulation_neg_mod_1(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setInsulation_neg_mod_2(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setInsulation_pos_mod_1(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModel.setInsulation_pos_mod_2(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				
				dataModel.setOperating_time(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setOperation_stat(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModel.setPower_factor(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel.setReactive_power(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModel.setTotal_yield(energy);
				dataModel.setTransformer_oil_temp(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModel.setVolt_line_ab(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModel.setVolt_line_bc(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModel.setVolt_line_ca(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModel.setM1_alarm_state_accircuitbreakerabnormal(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				
				dataModel.setM1_alarm_state_acspdalarm(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModel.setM1_alarm_state_antipidpowersupplyabnormal(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModel.setM1_alarm_state_bypasscircuitbreakerabnormal(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModel.setM1_alarm_state_bypassfuseabnormal(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModel.setM1_alarm_state_contactorcontactabnormal(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModel.setM1_alarm_state_ctunbalance(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModel.setM1_alarm_state_dcbypassforwardovercurrentalarm(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModel.setM1_alarm_state_dcbypassreserveovercurrentalarm(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModel.setM1_alarm_state_dcfuseabnormal(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModel.setM1_alarm_state_dcsensorabnormal(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				
				dataModel.setM1_alarm_state_dcspdalarm(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModel.setM1_alarm_state_dcswitchabnormal(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModel.setM1_alarm_state_dspmdccommunicationabnormal(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModel.setM1_alarm_state_energymetercommunicationabnormal(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModel.setM1_alarm_state_externalpowersupplyabnormal(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModel.setM1_alarm_state_fan2anomaly(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModel.setM1_alarm_state_fanabnormal(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModel.setM1_alarm_state_frequencydeviationactivepowerregulation(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModel.setM1_alarm_state_gfrtoperation(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				dataModel.setM1_alarm_state_groundfuseabnormal(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				
				dataModel.setM1_alarm_state_insulationboardcommunicationanomaly(Double.parseDouble(!Lib.isBlank(words.get(51)) ? words.get(51) : "0.001"));
				dataModel.setM1_alarm_state_lowinsulationresistance(Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001"));
				dataModel.setM1_alarm_state_temperatureabnormalalarm(Double.parseDouble(!Lib.isBlank(words.get(53)) ? words.get(53) : "0.001"));
				dataModel.setM1_alarm_state_temperatureandhumidityboardcommerror(Double.parseDouble(!Lib.isBlank(words.get(54)) ? words.get(54) : "0.001"));
				dataModel.setM1_alarm_state_tributaryboardcommunicationerror(Double.parseDouble(!Lib.isBlank(words.get(55)) ? words.get(55) : "0.001"));
				dataModel.setM1_alarm_state_voltagedeviationreactivepowerregulation(Double.parseDouble(!Lib.isBlank(words.get(56)) ? words.get(56) : "0.001"));
				dataModel.setM1_fault_state_1_acleakagecurrentprotection(Double.parseDouble(!Lib.isBlank(words.get(57)) ? words.get(57) : "0.001"));
				dataModel.setM1_fault_state_1_acovercurrent(Double.parseDouble(!Lib.isBlank(words.get(58)) ? words.get(58) : "0.001"));
				dataModel.setM1_fault_state_1_acovervoltage(Double.parseDouble(!Lib.isBlank(words.get(59)) ? words.get(59) : "0.001"));
				dataModel.setM1_fault_state_1_acundervoltage(Double.parseDouble(!Lib.isBlank(words.get(60)) ? words.get(60) : "0.001"));
				
				dataModel.setM1_fault_state_1_busovervoltage(Double.parseDouble(!Lib.isBlank(words.get(61)) ? words.get(61) : "0.001"));
				dataModel.setM1_fault_state_1_busundervoltage(Double.parseDouble(!Lib.isBlank(words.get(62)) ? words.get(62) : "0.001"));
				dataModel.setM1_fault_state_1_contactorfault(Double.parseDouble(!Lib.isBlank(words.get(63)) ? words.get(63) : "0.001"));
				dataModel.setM1_fault_state_1_dcfusefault(Double.parseDouble(!Lib.isBlank(words.get(64)) ? words.get(64) : "0.001"));
				dataModel.setM1_fault_state_1_dcleakagecurrentprotection(Double.parseDouble(!Lib.isBlank(words.get(65)) ? words.get(65) : "0.001"));
				dataModel.setM1_fault_state_1_dcovercurrent(Double.parseDouble(!Lib.isBlank(words.get(66)) ? words.get(66) : "0.001"));
				dataModel.setM1_fault_state_1_dcovervoltage(Double.parseDouble(!Lib.isBlank(words.get(67)) ? words.get(67) : "0.001"));
				dataModel.setM1_fault_state_1_dcundervoltage(Double.parseDouble(!Lib.isBlank(words.get(68)) ? words.get(68) : "0.001"));
				dataModel.setM1_fault_state_1_detectionfusefault(Double.parseDouble(!Lib.isBlank(words.get(69)) ? words.get(69) : "0.001"));
				dataModel.setM1_fault_state_1_fanfault(Double.parseDouble(!Lib.isBlank(words.get(70)) ? words.get(70) : "0.001"));
				
				dataModel.setM1_fault_state_1_frequencyabnormal(Double.parseDouble(!Lib.isBlank(words.get(71)) ? words.get(71) : "0.001"));
				dataModel.setM1_fault_state_1_gfdiprotection(Double.parseDouble(!Lib.isBlank(words.get(72)) ? words.get(72) : "0.001"));
				dataModel.setM1_fault_state_1_groundingfault(Double.parseDouble(!Lib.isBlank(words.get(73)) ? words.get(73) : "0.001"));
				dataModel.setM1_fault_state_1_hardwarefault(Double.parseDouble(!Lib.isBlank(words.get(74)) ? words.get(74) : "0.001"));
				dataModel.setM1_fault_state_1_inverterovervoltage(Double.parseDouble(!Lib.isBlank(words.get(75)) ? words.get(75) : "0.001"));
				dataModel.setM1_fault_state_1_islandingprotection(Double.parseDouble(!Lib.isBlank(words.get(76)) ? words.get(76) : "0.001"));
				dataModel.setM1_fault_state_1_moduleovertemperature(Double.parseDouble(!Lib.isBlank(words.get(77)) ? words.get(77) : "0.001"));
				dataModel.setM1_fault_state_1_overfrequency(Double.parseDouble(!Lib.isBlank(words.get(78)) ? words.get(78) : "0.001"));
				dataModel.setM1_fault_state_1_overloadprotection(Double.parseDouble(!Lib.isBlank(words.get(79)) ? words.get(79) : "0.001"));
				dataModel.setM1_fault_state_1_pdpprotection(Double.parseDouble(!Lib.isBlank(words.get(80)) ? words.get(80) : "0.001"));
				
				dataModel.setM1_fault_state_1_reactorovertemperature(Double.parseDouble(!Lib.isBlank(words.get(81)) ? words.get(81) : "0.001"));
				dataModel.setM1_fault_state_1_sensorfailure(Double.parseDouble(!Lib.isBlank(words.get(82)) ? words.get(82) : "0.001"));
				dataModel.setM1_fault_state_1_temperatureabnormal(Double.parseDouble(!Lib.isBlank(words.get(83)) ? words.get(83) : "0.001"));
				dataModel.setM1_fault_state_1_transformerovertemperature(Double.parseDouble(!Lib.isBlank(words.get(84)) ? words.get(84) : "0.001"));
				dataModel.setM1_fault_state_1_underfrequency(Double.parseDouble(!Lib.isBlank(words.get(85)) ? words.get(85) : "0.001"));
				dataModel.setM1_fault_state_2_accurrentunbalance(Double.parseDouble(!Lib.isBlank(words.get(86)) ? words.get(86) : "0.001"));
				dataModel.setM1_fault_state_2_acspdfault(Double.parseDouble(!Lib.isBlank(words.get(87)) ? words.get(87) : "0.001"));
				dataModel.setM1_fault_state_2_dcspdfault(Double.parseDouble(!Lib.isBlank(words.get(88)) ? words.get(88) : "0.001"));
				dataModel.setM1_fault_state_2_pvpolarityreversed(Double.parseDouble(!Lib.isBlank(words.get(89)) ? words.get(89) : "0.001"));
				dataModel.setM1_fault_state_2_accabinetovertemperature(Double.parseDouble(!Lib.isBlank(words.get(90)) ? words.get(90) : "0.001"));
				
				dataModel.setM1_fault_state_2_acfusefault(Double.parseDouble(!Lib.isBlank(words.get(91)) ? words.get(91) : "0.001"));
				dataModel.setM1_fault_state_2_acswitchdisconnection(Double.parseDouble(!Lib.isBlank(words.get(92)) ? words.get(92) : "0.001"));
				dataModel.setM1_fault_state_2_acswitchfault(Double.parseDouble(!Lib.isBlank(words.get(93)) ? words.get(93) : "0.001"));
				dataModel.setM1_fault_state_2_backuppowersupplyabnormal(Double.parseDouble(!Lib.isBlank(words.get(94)) ? words.get(94) : "0.001"));
				dataModel.setM1_fault_state_2_buffercontactorfault(Double.parseDouble(!Lib.isBlank(words.get(95)) ? words.get(95) : "0.001"));
				dataModel.setM1_fault_state_2_carriersyncfault(Double.parseDouble(!Lib.isBlank(words.get(96)) ? words.get(96) : "0.001"));
				dataModel.setM1_fault_state_2_controlcabinettemperatureabnormal(Double.parseDouble(!Lib.isBlank(words.get(97)) ? words.get(97) : "0.001"));
				dataModel.setM1_fault_state_2_controlpowersupplyabnormal(Double.parseDouble(!Lib.isBlank(words.get(98)) ? words.get(98) : "0.001"));
				dataModel.setM1_fault_state_2_currentunbalance2(Double.parseDouble(!Lib.isBlank(words.get(99)) ? words.get(99) : "0.001"));
				dataModel.setM1_fault_state_2_currentunbalance3(Double.parseDouble(!Lib.isBlank(words.get(100)) ? words.get(100) : "0.001"));
				
				dataModel.setM1_fault_state_2_dccabinetovertemperature(Double.parseDouble(!Lib.isBlank(words.get(101)) ? words.get(101) : "0.001"));
				dataModel.setM1_fault_state_2_dcfusegroundingfault(Double.parseDouble(!Lib.isBlank(words.get(102)) ? words.get(102) : "0.001"));
				dataModel.setM1_fault_state_2_dcinjectionfault(Double.parseDouble(!Lib.isBlank(words.get(103)) ? words.get(103) : "0.001"));
				dataModel.setM1_fault_state_2_dcswitchfault(Double.parseDouble(!Lib.isBlank(words.get(104)) ? words.get(104) : "0.001"));
				dataModel.setM1_fault_state_2_dcvoltagesamplingfault(Double.parseDouble(!Lib.isBlank(words.get(105)) ? words.get(105) : "0.001"));
				dataModel.setM1_fault_state_2_devicecoderepeatfault(Double.parseDouble(!Lib.isBlank(words.get(106)) ? words.get(106) : "0.001"));
				dataModel.setM1_fault_state_2_driveboardfault(Double.parseDouble(!Lib.isBlank(words.get(107)) ? words.get(107) : "0.001"));
				dataModel.setM1_fault_state_2_fan2fault(Double.parseDouble(!Lib.isBlank(words.get(108)) ? words.get(108) : "0.001"));
				dataModel.setM1_fault_state_2_gridvoltageunbalance(Double.parseDouble(!Lib.isBlank(words.get(109)) ? words.get(109) : "0.001"));
				dataModel.setM1_fault_state_2_insulationimpedance(Double.parseDouble(!Lib.isBlank(words.get(110)) ? words.get(110) : "0.001"));
				
				dataModel.setM1_fault_state_2_neutralpointpotentialshift(Double.parseDouble(!Lib.isBlank(words.get(111)) ? words.get(111) : "0.001"));
				dataModel.setM1_fault_state_2_paralleloperation(Double.parseDouble(!Lib.isBlank(words.get(112)) ? words.get(112) : "0.001"));
				dataModel.setM1_fault_state_2_samplingfault(Double.parseDouble(!Lib.isBlank(words.get(113)) ? words.get(113) : "0.001"));
				dataModel.setM1_fault_state_2_softstartfault(Double.parseDouble(!Lib.isBlank(words.get(114)) ? words.get(114) : "0.001"));
				dataModel.setM1_node_state_1_accircuitbreakerstate(Double.parseDouble(!Lib.isBlank(words.get(115)) ? words.get(115) : "0.001"));
				dataModel.setM1_node_state_1_acfusestatus(Double.parseDouble(!Lib.isBlank(words.get(116)) ? words.get(116) : "0.001"));
				dataModel.setM1_node_state_1_acmaincontactorstate(Double.parseDouble(!Lib.isBlank(words.get(117)) ? words.get(117) : "0.001"));
				dataModel.setM1_node_state_1_acbfaultstate(Double.parseDouble(!Lib.isBlank(words.get(118)) ? words.get(118) : "0.001"));
				dataModel.setM1_node_state_1_acbremotelocal(Double.parseDouble(!Lib.isBlank(words.get(119)) ? words.get(119) : "0.001"));
				dataModel.setM1_node_state_2_dcswitchstate1(Double.parseDouble(!Lib.isBlank(words.get(120)) ? words.get(120) : "0.001"));
				
				dataModel.setM1_node_state_2_dcswitchstate2(Double.parseDouble(!Lib.isBlank(words.get(121)) ? words.get(121) : "0.001"));
				dataModel.setM1_node_state_2_dcswitchstate3(Double.parseDouble(!Lib.isBlank(words.get(122)) ? words.get(122) : "0.001"));
				dataModel.setM1_node_state_2_dcswitchstate4(Double.parseDouble(!Lib.isBlank(words.get(123)) ? words.get(123) : "0.001"));
				dataModel.setM1_node_state_2_spdnodestate(Double.parseDouble(!Lib.isBlank(words.get(124)) ? words.get(124) : "0.001"));
				dataModel.setM1_work_state_alarmrunning(Double.parseDouble(!Lib.isBlank(words.get(125)) ? words.get(125) : "0.001"));
				dataModel.setM1_work_state_antipidrunning(Double.parseDouble(!Lib.isBlank(words.get(126)) ? words.get(126) : "0.001"));
				dataModel.setM1_work_state_deratingrunning(Double.parseDouble(!Lib.isBlank(words.get(127)) ? words.get(127) : "0.001"));
				dataModel.setM1_work_state_emergencystop(Double.parseDouble(!Lib.isBlank(words.get(128)) ? words.get(128) : "0.001"));
				dataModel.setM1_work_state_faultstop(Double.parseDouble(!Lib.isBlank(words.get(129)) ? words.get(129) : "0.001"));
				dataModel.setM1_work_state_initialstandby(Double.parseDouble(!Lib.isBlank(words.get(130)) ? words.get(130) : "0.001"));
				
				dataModel.setM1_work_state_iodspcommunicationabnormal(Double.parseDouble(!Lib.isBlank(words.get(131)) ? words.get(131) : "0.001"));
				dataModel.setM1_work_state_iomdccommunicationabnormal(Double.parseDouble(!Lib.isBlank(words.get(132)) ? words.get(132) : "0.001"));
				dataModel.setM1_work_state_keystop(Double.parseDouble(!Lib.isBlank(words.get(133)) ? words.get(133) : "0.001"));
				dataModel.setM1_work_state_running(Double.parseDouble(!Lib.isBlank(words.get(134)) ? words.get(134) : "0.001"));
				dataModel.setM1_work_state_standby(Double.parseDouble(!Lib.isBlank(words.get(135)) ? words.get(135) : "0.001"));
				dataModel.setM1_work_state_starting(Double.parseDouble(!Lib.isBlank(words.get(136)) ? words.get(136) : "0.001"));
				dataModel.setM1_work_state_stopped(Double.parseDouble(!Lib.isBlank(words.get(137)) ? words.get(137) : "0.001"));
				dataModel.setM1_work_state_stopping(Double.parseDouble(!Lib.isBlank(words.get(138)) ? words.get(138) : "0.001"));
				dataModel.setM1_work_state_totalsignalbitofrunningstate(Double.parseDouble(!Lib.isBlank(words.get(139)) ? words.get(139) : "0.001"));
				dataModel.setM1_work_state_totalstopbit(Double.parseDouble(!Lib.isBlank(words.get(140)) ? words.get(140) : "0.001"));
				
				dataModel.setM2_alarm_state_accircuitbreakerabnormal(Double.parseDouble(!Lib.isBlank(words.get(141)) ? words.get(141) : "0.001"));
				dataModel.setM2_alarm_state_acspdalarm(Double.parseDouble(!Lib.isBlank(words.get(142)) ? words.get(142) : "0.001"));
				dataModel.setM2_alarm_state_antipidpowersupplyabnormal(Double.parseDouble(!Lib.isBlank(words.get(143)) ? words.get(143) : "0.001"));
				dataModel.setM2_alarm_state_bypasscircuitbreakerabnormal(Double.parseDouble(!Lib.isBlank(words.get(144)) ? words.get(144) : "0.001"));
				dataModel.setM2_alarm_state_bypassfuseabnormal(Double.parseDouble(!Lib.isBlank(words.get(145)) ? words.get(145) : "0.001"));
				dataModel.setM2_alarm_state_contactorcontactabnormal(Double.parseDouble(!Lib.isBlank(words.get(146)) ? words.get(146) : "0.001"));
				dataModel.setM2_alarm_state_ctunbalance(Double.parseDouble(!Lib.isBlank(words.get(147)) ? words.get(147) : "0.001"));
				dataModel.setM2_alarm_state_dcbypassforwardovercurrentalarm(Double.parseDouble(!Lib.isBlank(words.get(148)) ? words.get(148) : "0.001"));
				dataModel.setM2_alarm_state_dcbypassreserveovercurrentalarm(Double.parseDouble(!Lib.isBlank(words.get(149)) ? words.get(149) : "0.001"));
				dataModel.setM2_alarm_state_dcfuseabnormal(Double.parseDouble(!Lib.isBlank(words.get(150)) ? words.get(150) : "0.001"));
				
				dataModel.setM2_alarm_state_dcsensorabnormal(Double.parseDouble(!Lib.isBlank(words.get(151)) ? words.get(151) : "0.001"));
				dataModel.setM2_alarm_state_dcspdalarm(Double.parseDouble(!Lib.isBlank(words.get(152)) ? words.get(152) : "0.001"));
				dataModel.setM2_alarm_state_dcswitchabnormal(Double.parseDouble(!Lib.isBlank(words.get(153)) ? words.get(153) : "0.001"));
				dataModel.setM2_alarm_state_dspmdccommunicationabnormal(Double.parseDouble(!Lib.isBlank(words.get(154)) ? words.get(154) : "0.001"));
				dataModel.setM2_alarm_state_energymetercommunicationabnormal(Double.parseDouble(!Lib.isBlank(words.get(155)) ? words.get(155) : "0.001"));
				dataModel.setM2_alarm_state_externalpowersupplyabnormal(Double.parseDouble(!Lib.isBlank(words.get(156)) ? words.get(156) : "0.001"));
				dataModel.setM2_alarm_state_fan2anomaly(Double.parseDouble(!Lib.isBlank(words.get(157)) ? words.get(157) : "0.001"));
				dataModel.setM2_alarm_state_fanabnormal(Double.parseDouble(!Lib.isBlank(words.get(158)) ? words.get(158) : "0.001"));
				dataModel.setM2_alarm_state_frequencydeviationactivepowerregulation(Double.parseDouble(!Lib.isBlank(words.get(159)) ? words.get(159) : "0.001"));
				dataModel.setM2_alarm_state_gfrtoperation(Double.parseDouble(!Lib.isBlank(words.get(160)) ? words.get(160) : "0.001"));
				
				dataModel.setM2_alarm_state_groundfuseabnormal(Double.parseDouble(!Lib.isBlank(words.get(161)) ? words.get(161) : "0.001"));
				dataModel.setM2_alarm_state_insulationboardcommunicationanomaly(Double.parseDouble(!Lib.isBlank(words.get(162)) ? words.get(162) : "0.001"));
				dataModel.setM2_alarm_state_lowinsulationresistance(Double.parseDouble(!Lib.isBlank(words.get(163)) ? words.get(163) : "0.001"));
				dataModel.setM2_alarm_state_temperatureabnormalalarm(Double.parseDouble(!Lib.isBlank(words.get(164)) ? words.get(164) : "0.001"));
				dataModel.setM2_alarm_state_temperatureandhumidityboardcommerror(Double.parseDouble(!Lib.isBlank(words.get(165)) ? words.get(165) : "0.001"));
				dataModel.setM2_alarm_state_tributaryboardcommunicationerror(Double.parseDouble(!Lib.isBlank(words.get(166)) ? words.get(166) : "0.001"));
				dataModel.setM2_alarm_state_voltagedeviationreactivepowerregulation(Double.parseDouble(!Lib.isBlank(words.get(167)) ? words.get(167) : "0.001"));
				dataModel.setM2_fault_state_1_acleakagecurrentprotection(Double.parseDouble(!Lib.isBlank(words.get(168)) ? words.get(168) : "0.001"));
				dataModel.setM2_fault_state_1_acovercurrent(Double.parseDouble(!Lib.isBlank(words.get(169)) ? words.get(169) : "0.001"));
				dataModel.setM2_fault_state_1_acovervoltage(Double.parseDouble(!Lib.isBlank(words.get(170)) ? words.get(170) : "0.001"));
				
				dataModel.setM2_fault_state_1_acundervoltage(Double.parseDouble(!Lib.isBlank(words.get(171)) ? words.get(171) : "0.001"));
				dataModel.setM2_fault_state_1_busovervoltage(Double.parseDouble(!Lib.isBlank(words.get(172)) ? words.get(172) : "0.001"));
				dataModel.setM2_fault_state_1_busundervoltage(Double.parseDouble(!Lib.isBlank(words.get(173)) ? words.get(173) : "0.001"));
				dataModel.setM2_fault_state_1_contactorfault(Double.parseDouble(!Lib.isBlank(words.get(174)) ? words.get(174) : "0.001"));
				dataModel.setM2_fault_state_1_dcfusefault(Double.parseDouble(!Lib.isBlank(words.get(175)) ? words.get(175) : "0.001"));
				dataModel.setM2_fault_state_1_dcleakagecurrentprotection(Double.parseDouble(!Lib.isBlank(words.get(176)) ? words.get(176) : "0.001"));
				dataModel.setM2_fault_state_1_dcovercurrent(Double.parseDouble(!Lib.isBlank(words.get(177)) ? words.get(177) : "0.001"));
				dataModel.setM2_fault_state_1_dcovervoltage(Double.parseDouble(!Lib.isBlank(words.get(178)) ? words.get(178) : "0.001"));
				dataModel.setM2_fault_state_1_dcundervoltage(Double.parseDouble(!Lib.isBlank(words.get(179)) ? words.get(179) : "0.001"));
				dataModel.setM2_fault_state_1_detectionfusefault(Double.parseDouble(!Lib.isBlank(words.get(180)) ? words.get(180) : "0.001"));
				
				dataModel.setM2_fault_state_1_fanfault(Double.parseDouble(!Lib.isBlank(words.get(181)) ? words.get(181) : "0.001"));
				dataModel.setM2_fault_state_1_frequencyabnormal(Double.parseDouble(!Lib.isBlank(words.get(182)) ? words.get(182) : "0.001"));
				dataModel.setM2_fault_state_1_gfdiprotection(Double.parseDouble(!Lib.isBlank(words.get(183)) ? words.get(183) : "0.001"));
				dataModel.setM2_fault_state_1_groundingfault(Double.parseDouble(!Lib.isBlank(words.get(184)) ? words.get(184) : "0.001"));
				dataModel.setM2_fault_state_1_hardwarefault(Double.parseDouble(!Lib.isBlank(words.get(185)) ? words.get(185) : "0.001"));
				dataModel.setM2_fault_state_1_inverterovervoltage(Double.parseDouble(!Lib.isBlank(words.get(186)) ? words.get(186) : "0.001"));
				dataModel.setM2_fault_state_1_islandingprotection(Double.parseDouble(!Lib.isBlank(words.get(187)) ? words.get(87) : "0.001"));
				dataModel.setM2_fault_state_1_moduleovertemperature(Double.parseDouble(!Lib.isBlank(words.get(188)) ? words.get(188) : "0.001"));
				dataModel.setM2_fault_state_1_overfrequency(Double.parseDouble(!Lib.isBlank(words.get(189)) ? words.get(189) : "0.001"));
				dataModel.setM2_fault_state_1_overloadprotection(Double.parseDouble(!Lib.isBlank(words.get(190)) ? words.get(190) : "0.001"));
				
				dataModel.setM2_fault_state_1_pdpprotection(Double.parseDouble(!Lib.isBlank(words.get(191)) ? words.get(191) : "0.001"));
				dataModel.setM2_fault_state_1_reactorovertemperature(Double.parseDouble(!Lib.isBlank(words.get(192)) ? words.get(192) : "0.001"));
				dataModel.setM2_fault_state_1_sensorfailure(Double.parseDouble(!Lib.isBlank(words.get(193)) ? words.get(193) : "0.001"));
				dataModel.setM2_fault_state_1_temperatureabnormal(Double.parseDouble(!Lib.isBlank(words.get(194)) ? words.get(194) : "0.001"));
				dataModel.setM2_fault_state_1_transformerovertemperature(Double.parseDouble(!Lib.isBlank(words.get(195)) ? words.get(195) : "0.001"));
				dataModel.setM2_fault_state_1_underfrequency(Double.parseDouble(!Lib.isBlank(words.get(196)) ? words.get(196) : "0.001"));
				dataModel.setM2_fault_state_2_accurrentunbalance(Double.parseDouble(!Lib.isBlank(words.get(197)) ? words.get(197) : "0.001"));
				dataModel.setM2_fault_state_2_acspdfault(Double.parseDouble(!Lib.isBlank(words.get(198)) ? words.get(198) : "0.001"));
				dataModel.setM2_fault_state_2_dcspdfault(Double.parseDouble(!Lib.isBlank(words.get(199)) ? words.get(199) : "0.001"));
				dataModel.setM2_fault_state_2_pvpolarityreversed(Double.parseDouble(!Lib.isBlank(words.get(200)) ? words.get(200) : "0.001"));
				
				dataModel.setM2_fault_state_2_accabinetovertemperature(Double.parseDouble(!Lib.isBlank(words.get(201)) ? words.get(201) : "0.001"));
				dataModel.setM2_fault_state_2_acfusefault(Double.parseDouble(!Lib.isBlank(words.get(202)) ? words.get(202) : "0.001"));
				dataModel.setM2_fault_state_2_acswitchdisconnection(Double.parseDouble(!Lib.isBlank(words.get(203)) ? words.get(203) : "0.001"));
				dataModel.setM2_fault_state_2_acswitchfault(Double.parseDouble(!Lib.isBlank(words.get(204)) ? words.get(204) : "0.001"));
				dataModel.setM2_fault_state_2_backuppowersupplyabnormal(Double.parseDouble(!Lib.isBlank(words.get(205)) ? words.get(205) : "0.001"));
				dataModel.setM2_fault_state_2_buffercontactorfault(Double.parseDouble(!Lib.isBlank(words.get(206)) ? words.get(206) : "0.001"));
				dataModel.setM2_fault_state_2_carriersyncfault(Double.parseDouble(!Lib.isBlank(words.get(207)) ? words.get(207) : "0.001"));
				dataModel.setM2_fault_state_2_controlcabinettemperatureabnormal(Double.parseDouble(!Lib.isBlank(words.get(208)) ? words.get(208) : "0.001"));
				dataModel.setM2_fault_state_2_controlpowersupplyabnormal(Double.parseDouble(!Lib.isBlank(words.get(209)) ? words.get(209) : "0.001"));
				dataModel.setM2_fault_state_2_currentunbalance2(Double.parseDouble(!Lib.isBlank(words.get(210)) ? words.get(210) : "0.001"));
				
				dataModel.setM2_fault_state_2_currentunbalance3(Double.parseDouble(!Lib.isBlank(words.get(211)) ? words.get(211) : "0.001"));
				dataModel.setM2_fault_state_2_dccabinetovertemperature(Double.parseDouble(!Lib.isBlank(words.get(212)) ? words.get(212) : "0.001"));
				dataModel.setM2_fault_state_2_dcfusegroundingfault(Double.parseDouble(!Lib.isBlank(words.get(213)) ? words.get(213) : "0.001"));
				dataModel.setM2_fault_state_2_dcinjectionfault(Double.parseDouble(!Lib.isBlank(words.get(214)) ? words.get(214) : "0.001"));
				dataModel.setM2_fault_state_2_dcswitchfault(Double.parseDouble(!Lib.isBlank(words.get(215)) ? words.get(215) : "0.001"));
				dataModel.setM2_fault_state_2_dcvoltagesamplingfault(Double.parseDouble(!Lib.isBlank(words.get(216)) ? words.get(216) : "0.001"));
				dataModel.setM2_fault_state_2_devicecoderepeatfault(Double.parseDouble(!Lib.isBlank(words.get(217)) ? words.get(217) : "0.001"));
				dataModel.setM2_fault_state_2_driveboardfault(Double.parseDouble(!Lib.isBlank(words.get(218)) ? words.get(218) : "0.001"));
				dataModel.setM2_fault_state_2_fan2fault(Double.parseDouble(!Lib.isBlank(words.get(219)) ? words.get(219) : "0.001"));
				dataModel.setM2_fault_state_2_gridvoltageunbalance(Double.parseDouble(!Lib.isBlank(words.get(220)) ? words.get(220) : "0.001"));
				
				dataModel.setM2_fault_state_2_insulationimpedance(Double.parseDouble(!Lib.isBlank(words.get(221)) ? words.get(221) : "0.001"));
				dataModel.setM2_fault_state_2_neutralpointpotentialshift(Double.parseDouble(!Lib.isBlank(words.get(222)) ? words.get(222) : "0.001"));
				dataModel.setM2_fault_state_2_paralleloperation(Double.parseDouble(!Lib.isBlank(words.get(223)) ? words.get(223) : "0.001"));
				dataModel.setM2_fault_state_2_samplingfault(Double.parseDouble(!Lib.isBlank(words.get(224)) ? words.get(224) : "0.001"));
				dataModel.setM2_fault_state_2_softstartfault(Double.parseDouble(!Lib.isBlank(words.get(225)) ? words.get(225) : "0.001"));
				dataModel.setM2_node_state_1_accircuitbreakerstate(Double.parseDouble(!Lib.isBlank(words.get(226)) ? words.get(226) : "0.001"));
				dataModel.setM2_node_state_1_acfusestatus(Double.parseDouble(!Lib.isBlank(words.get(227)) ? words.get(227) : "0.001"));
				dataModel.setM2_node_state_1_acmaincontactorstate(Double.parseDouble(!Lib.isBlank(words.get(228)) ? words.get(228) : "0.001"));
				dataModel.setM2_node_state_1_acbfaultstate(Double.parseDouble(!Lib.isBlank(words.get(229)) ? words.get(229) : "0.001"));
				dataModel.setM2_node_state_1_acbremotelocal(Double.parseDouble(!Lib.isBlank(words.get(230)) ? words.get(230) : "0.001"));
				
				dataModel.setM2_node_state_2_dcswitchstate1(Double.parseDouble(!Lib.isBlank(words.get(231)) ? words.get(231) : "0.001"));
				dataModel.setM2_node_state_2_dcswitchstate2(Double.parseDouble(!Lib.isBlank(words.get(232)) ? words.get(232) : "0.001"));
				dataModel.setM2_node_state_2_dcswitchstate3(Double.parseDouble(!Lib.isBlank(words.get(233)) ? words.get(233) : "0.001"));
				dataModel.setM2_node_state_2_dcswitchstate4(Double.parseDouble(!Lib.isBlank(words.get(234)) ? words.get(234) : "0.001"));
				dataModel.setM2_node_state_2_spdnodestate(Double.parseDouble(!Lib.isBlank(words.get(235)) ? words.get(235) : "0.001"));
				dataModel.setM2_work_state_alarmrunning(Double.parseDouble(!Lib.isBlank(words.get(236)) ? words.get(236) : "0.001"));
				dataModel.setM2_work_state_antipidrunning(Double.parseDouble(!Lib.isBlank(words.get(237)) ? words.get(237) : "0.001"));
				dataModel.setM2_work_state_deratingrunning(Double.parseDouble(!Lib.isBlank(words.get(238)) ? words.get(238) : "0.001"));
				dataModel.setM2_work_state_emergencystop(Double.parseDouble(!Lib.isBlank(words.get(239)) ? words.get(239) : "0.001"));
				dataModel.setM2_work_state_faultstop(Double.parseDouble(!Lib.isBlank(words.get(240)) ? words.get(240) : "0.001"));
				
				dataModel.setM2_work_state_initialstandby(Double.parseDouble(!Lib.isBlank(words.get(241)) ? words.get(241) : "0.001"));
				dataModel.setM2_work_state_iodspcommunicationabnormal(Double.parseDouble(!Lib.isBlank(words.get(242)) ? words.get(242) : "0.001"));
				dataModel.setM2_work_state_iomdccommunicationabnormal(Double.parseDouble(!Lib.isBlank(words.get(243)) ? words.get(243) : "0.001"));
				dataModel.setM2_work_state_keystop(Double.parseDouble(!Lib.isBlank(words.get(244)) ? words.get(244) : "0.001"));
				dataModel.setM2_work_state_running(Double.parseDouble(!Lib.isBlank(words.get(245)) ? words.get(245) : "0.001"));
				dataModel.setM2_work_state_standby(Double.parseDouble(!Lib.isBlank(words.get(246)) ? words.get(246) : "0.001"));
				dataModel.setM2_work_state_starting(Double.parseDouble(!Lib.isBlank(words.get(247)) ? words.get(247) : "0.001"));
				dataModel.setM2_work_state_stopped(Double.parseDouble(!Lib.isBlank(words.get(248)) ? words.get(248) : "0.001"));
				dataModel.setM2_work_state_stopping(Double.parseDouble(!Lib.isBlank(words.get(249)) ? words.get(249) : "0.001"));
				dataModel.setM2_work_state_totalsignalbitofrunningstate(Double.parseDouble(!Lib.isBlank(words.get(250)) ? words.get(250) : "0.001"));
				
				dataModel.setM2_work_state_totalstopbit(Double.parseDouble(!Lib.isBlank(words.get(251)) ? words.get(151) : "0.001"));
				dataModel.setMv_node_state_external_e_stop(Double.parseDouble(!Lib.isBlank(words.get(152)) ? words.get(152) : "0.001"));
				dataModel.setMv_node_state_gas_relay_alarm(Double.parseDouble(!Lib.isBlank(words.get(253)) ? words.get(253) : "0.001"));
				dataModel.setMv_node_state_gas_relay_trip(Double.parseDouble(!Lib.isBlank(words.get(254)) ? words.get(254) : "0.001"));
				dataModel.setMv_node_state_hv_remote_ctrl(Double.parseDouble(!Lib.isBlank(words.get(255)) ? words.get(255) : "0.001"));
				dataModel.setMv_node_state_hv_remote_ctrl_trip(Double.parseDouble(!Lib.isBlank(words.get(256)) ? words.get(256) : "0.001"));
				dataModel.setMv_node_state_hv_room_smoke(Double.parseDouble(!Lib.isBlank(words.get(257)) ? words.get(257) : "0.001"));
				dataModel.setMv_node_state_low_oil_level_trip(Double.parseDouble(!Lib.isBlank(words.get(258)) ? words.get(258) : "0.001"));
				dataModel.setMv_node_state_mv_ds(Double.parseDouble(!Lib.isBlank(words.get(259)) ? words.get(259) : "0.001"));
				dataModel.setMv_node_state_mv_fuse(Double.parseDouble(!Lib.isBlank(words.get(260)) ? words.get(260) : "0.001"));
				
				dataModel.setMv_node_state_mv_load_switch(Double.parseDouble(!Lib.isBlank(words.get(261)) ? words.get(261) : "0.001"));
				dataModel.setMv_node_state_mv_load_switch_1(Double.parseDouble(!Lib.isBlank(words.get(262)) ? words.get(262) : "0.001"));
				dataModel.setMv_node_state_mv_load_switch_2(Double.parseDouble(!Lib.isBlank(words.get(263)) ? words.get(263) : "0.001"));
				dataModel.setMv_node_state_mv_vcb(Double.parseDouble(!Lib.isBlank(words.get(264)) ? words.get(264) : "0.001"));
				dataModel.setMv_node_state_oil_temp_alarrm(Double.parseDouble(!Lib.isBlank(words.get(265)) ? words.get(265) : "0.001"));
				dataModel.setMv_node_state_oil_temp_trip(Double.parseDouble(!Lib.isBlank(words.get(266)) ? words.get(266) : "0.001"));
				dataModel.setMv_node_state_pressure_relief_trip(Double.parseDouble(!Lib.isBlank(words.get(267)) ? words.get(267) : "0.001"));
				dataModel.setMv_node_state_sf6_low_press_alarm(Double.parseDouble(!Lib.isBlank(words.get(268)) ? words.get(268) : "0.001"));
				dataModel.setMv_node_state_winding_temp_alarm(Double.parseDouble(!Lib.isBlank(words.get(269)) ? words.get(269) : "0.001"));
				dataModel.setMv_node_state_winding_temp_trip(Double.parseDouble(!Lib.isBlank(words.get(270)) ? words.get(270) : "0.001"));
				
				dataModel.setWork_state_door_open_prot(Double.parseDouble(!Lib.isBlank(words.get(271)) ? words.get(271) : "0.001"));
				dataModel.setWork_state_external_emer_stop(Double.parseDouble(!Lib.isBlank(words.get(272)) ? words.get(272) : "0.001"));
				dataModel.setWork_state_local_emer_stop(Double.parseDouble(!Lib.isBlank(words.get(273)) ? words.get(273) : "0.001"));
				dataModel.setWork_state_mv_fault(Double.parseDouble(!Lib.isBlank(words.get(274)) ? words.get(274) : "0.001"));
				dataModel.setWork_state_rem_emer_stop(Double.parseDouble(!Lib.isBlank(words.get(275)) ? words.get(275) : "0.001"));
				dataModel.setWork_state_running(Double.parseDouble(!Lib.isBlank(words.get(276)) ? words.get(276) : "0.001"));
				dataModel.setWork_state_smoke_prot(Double.parseDouble(!Lib.isBlank(words.get(277)) ? words.get(277) : "0.001"));
				dataModel.setWork_state_stopped(Double.parseDouble(!Lib.isBlank(words.get(278)) ? words.get(278) : "0.001"));
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModel.setNvmActivePower(power);
				dataModel.setNvmActiveEnergy(energy);
				
				return dataModel;
				
			} else {
				return new ModelSungrowSh6250hvMvEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelSungrowSh6250hvMvEntity();
		}
	}


	/**
	 * @description insert data from datalogger to model ModelSungrowSh6250hv
	 * @author duc.pham
	 * @since 2026-04-15
	 * @param data from datalogger
	 */
	
	public boolean insertModelSungrowSh6250hvMv(ModelSungrowSh6250hvMvEntity obj) {
		try {
			Object insertId = insert("ModelSungrowSh6250hvMv.insertModelSungrowSh6250hvMv", obj);
	        if(insertId == null ) {
	        	return false;
	        }
			ZoneId zoneId = ZoneId.of(obj.getTimezone_value());
			ZonedDateTime zdtNow = ZonedDateTime.now(zoneId);
			int hours = zdtNow.getHour();
			if (hours >= 9 && hours <= 17 && obj.getEnable_alert() >= 1) {
//				service.checkTriggerAlert(obj.getDatatablename(), obj.getTime(), obj.getId_device(), AlertEnum.values());
			}
	        return true;
		} catch (Exception ex) {
			log.error("insertModelSungrowSh6250hvMv", ex);
			return false;
		}

	}
}
