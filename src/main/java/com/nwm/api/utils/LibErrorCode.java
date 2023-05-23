/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/

package com.nwm.api.utils;
@SuppressWarnings({ "rawtypes", "unchecked", "restriction" })
public final class

LibErrorCode {
	
	/**
	 * get PVMStatusCodes from table tti tracker
	 * 
	 * @return
	 */
	public static int GetAlertTTiTracker(int decimalCode) {
		int errorCode = 0;
		switch (decimalCode) {
		case 0:
			// Angle Remotely Set
			errorCode = 555;
			break;
		case 1:
			// Stopped, Remote OK
			errorCode = 556;
			break;
		case 2:
			// Stopped
			errorCode = 557;
			break;
		case 3:
			// Angle Set At Keypad
			errorCode = 558;
			break;
		case 40:
			// Sun Tracking
			errorCode = 559;
			break;
		case 41:
			// At Rotation Limit
			errorCode = 560;
			break;
		case 42:
			// Shade Tracking
			errorCode = 561;
			break;
		case 43:
			// Night Stow
			errorCode = 562;
			break;
		case 44:
			// Wind Stow
			errorCode = 563;
			break;
		case 45:
			// Snow Stow
			errorCode = 564;
			break;
		case 46:
			// Show Shed
			errorCode = 565;
			break;
		case 5:
			// At Rotation Limit
			errorCode = 566;
			break;
		case 6:
			// At Rotation Limit
			errorCode = 567;
			break;
		case 7:
			// At Rotation Limit
			errorCode = 568;
			break;
		}
		
		return errorCode;
	}
	
	
	/**
	 * get PVMStatusCodes from table pvp 260
	 * 
	 * @return
	 */
	public static int GetPVMStatusCodesModelPVP260(int decimalCode) {
		int errorCode = 0;
		switch (decimalCode) {
		case 1:
			// Rebooting
			errorCode = 551;
			break;
		case 2:
			// Inverter communication fault
			errorCode = 552;
			break;
		case 4:
			// Web post fault
			errorCode = 553;
			break;
		
		case 5:
			// DNS server fault
			errorCode = 554;
			break;
		}
		
		return errorCode;
	}
	
	
	/**
	 * get SystemWarnings from table pvp 260
	 * 
	 * @return
	 */
	public static int GetSystemWarningsModelPVP260(int decimalCode) {
		int errorCode = 0;
		switch (decimalCode) {
		case 1:
			// Fan 1 warning
			errorCode = 541;
			break;
		case 2:
			// Fan 2 warning
			errorCode = 542;
			break;
		case 4:
			// Fan 3 warning
			errorCode = 543;
			break;
		
		case 8:
			// Magnetics high temperature warning
			errorCode = 544;
			break;
		case 16:
			// Power foldback warning
			errorCode = 545;
			break;
		case 32:
			// Heatsink delta temperature warning
			errorCode = 546;
			break;
		case 128:
			// GFDI current warning
			errorCode = 547;
			break;
		case 256:
			// AC surge warning
			errorCode = 548;
			break;
		case 512:
			// DC surge warning
			errorCode = 549;
			break;
		case 1024:
			// Negative DC current warning
			errorCode = 550;
			break;
		}
		
		return errorCode;
	}
	
	
	/**
	 * get SystemFault from table pvp 260
	 * 
	 * @return
	 */
	public static int GetSystemFaultModelPVP260(int decimalCode) {
		int errorCode = 0;
		switch (decimalCode) {
		case 1:
			// Ground fault
			errorCode = 532;
			break;
		case 2:
			// AC contactor fault
			errorCode = 533;
			break;
		case 4:
			// DC contactor fault
			errorCode = 534;
			break;
		
		case 8:
			// Watchdog fault
			errorCode = 535;
			break;
		case 16:
			// CPU load fault
			errorCode = 536;
			break;
		case 32:
			// Too many fault restarts
			errorCode = 537;
			break;
		case 64:
			// Configuration fault
			errorCode = 538;
			break;
		case 128:
			// AC current imbalance
			errorCode = 539;
			break;
		case 256:
			// No AC voltage detected
			errorCode = 540;
			break;
		}
		
		return errorCode;
	}
	
	
	/**
	 * get TemperatureFault from table pvp 260
	 * 
	 * @return
	 */
	public static int GetTemperatureFaultModelPVP260(int decimalCode) {
		int errorCode = 0;
		switch (decimalCode) {
		case 1:
			// Module heat-sink A1 temperature high
			errorCode = 522;
			break;
		case 2:
			// Module heat-sink A2 temperature high
			errorCode = 523;
			break;
		case 4:
			// Module heat-sink B1 temperature high
			errorCode = 524;
			break;
		
		case 8:
			// Module heat-sink B2 temperature high
			errorCode = 525;
			break;
		case 16:
			// Module heat-sink C1 temperature high
			errorCode = 526;
			break;
		case 32:
			// Module heat-sink C2 temperature high
			errorCode = 527;
			break;
		case 64:
			// Control board temperature high
			errorCode = 528;
			break;
		case 128:
			// Magnetics temperature high
			errorCode = 529;
			break;
		case 256:
			// Ambient temperature low
			errorCode = 530;
			break;
		case 512:
			// Magnetics temperature low
			errorCode = 531;
			break;
		}
		
		return errorCode;
	}
	
	
	
	/**
	 * get GridFault from table pvp 260
	 * 
	 * @return
	 */
	public static int GetGridFaultModelPVP260(int decimalCode) {
		int errorCode = 0;
		switch (decimalCode) {
		case 1:
			// Fast AC voltage low, phase A
			errorCode = 508;
			break;
		case 2:
			// Fast AC voltage low, phase B
			errorCode = 509;
			break;
		case 4:
			// Fast AC voltage low, phase C
			errorCode = 510;
			break;
		
		case 8:
			// Slow AC voltage low, phase A
			errorCode = 511;
			break;
		case 16:
			// Slow AC voltage low, phase B
			errorCode = 512;
			break;
		case 32:
			// Slow AC voltage low, phase C
			errorCode = 513;
			break;
		case 64:
			// Fast AC voltage high, phase A
			errorCode = 514;
			break;
		case 128:
			// Fast AC voltage high, phase B
			errorCode = 515;
			break;
		case 256:
			// Fast AC voltage high, phase C
			errorCode = 516;
			break;
		case 512:
			// Slow AC voltage high, phase A
			errorCode = 517;
			break;
		case 1024:
			// Slow AC voltage high, phase B
			errorCode = 518;
			break;
		case 2048:
			// Slow AC voltage high, phase C
			errorCode = 519;
			break;
		case 4096:
			// Low frequency fault
			errorCode = 520;
			break;
		case 8192:
			// High frequency fault
			errorCode = 521;
			break;
		}
		
		return errorCode;
	}
	
	
	/**
	 * get DriveFault from table pvp 260
	 * 
	 * @return
	 */
	public static int GetVoltageFaultModelPVP260(int decimalCode) {
		int errorCode = 0;
		switch (decimalCode) {
		case 1:
			// Peak AC voltage high, phase A
			errorCode = 495;
			break;
		case 2:
			// Peak AC voltage high, phase A
			errorCode = 496;
			break;
		case 4:
			// Peak AC voltage high, phase A
			errorCode = 497;
			break;
		
		case 8:
			// Control PLL fault
			errorCode = 498;
			break;
		case 32:
			// DC voltage high
			errorCode = 499;
			break;
		case 64:
			// 5V power supply fault
			errorCode = 500;
			break;
		case 128:
			// 15V power supply fault
			errorCode = 501;
			break;
		case 256:
			// -15V power supply fault
			errorCode = 502;
			break;
		case 512:
			// 10V power supply fault
			errorCode = 503;
			break;
		case 1024:
			// 24V power supply fault
			errorCode = 504;
			break;
		case 2048:
			// 48V power supply fault
			errorCode = 505;
			break;
		case 4096:
			// DC precharge fault
			errorCode = 506;
			break;
		case 8192:
			// PV input and DC bus voltage delta
			errorCode = 507;
			break;
		}
		
		return errorCode;
	}
	
	
	/**
	 * get DriveFault from table pvp 260
	 * 
	 * @return
	 */
	public static int GetDriveFaultModelPVP260(int decimalCode) {
		int errorCode = 0;
		switch (decimalCode) {
		case 1:
			// Drive protection fault, phase A low
			errorCode = 483;
			break;
		case 2:
			// Drive protection fault, phase B high
			errorCode = 484;
			break;
		case 4:
			// Drive protection fault, phase C low
			errorCode = 485;
			break;
		
		case 8:
			// Drive protection fault, phase A high
			errorCode = 486;
			break;
		case 32:
			// Drive protection fault, phase B low
			errorCode = 487;
			break;
		case 64:
			// Drive protection fault, phase C high
			errorCode = 488;
			break;
		case 128:
			// Peak over-current, phase A
			errorCode = 489;
			break;
		case 256:
			// Peak over-current, phase B
			errorCode = 490;
			break;
		case 512:
			// Peak over-current, phase C
			errorCode = 491;
			break;
		case 1024:
			// RMS over-current, phase A
			errorCode = 492;
			break;
		case 2048:
			// RMS over-current, phase B
			errorCode = 493;
			break;
		case 4096:
			// RMS over-current, phase C
			errorCode = 494;
			break;
		}
		
		return errorCode;
	}
	
	
	/**
	 * get MainFault from table pvp 260
	 * 
	 * @return
	 */
	public static int GetMainFaultModelPVP260(int decimalCode) {
		int errorCode = 0;
		switch (decimalCode) {
		case 1:
			// Drive fault
			errorCode = 477;
			break;
		case 2:
			// Voltage fault
			errorCode = 478;
			break;
		case 4:
			// Grid fault
			errorCode = 479;
			break;
		
		case 8:
			// Temperature fault
			errorCode = 480;
			break;
		case 16:
			// System fault
			errorCode = 481;
			break;
		case 32768:
			// Latching fault
			errorCode = 482;
			break;
		}
		
		return errorCode;
	}
	
	
	/**
	 * get InverterOperatingStatus from table pvp 260
	 * 
	 * @return
	 */
	public static int GetInverterOperatingStatusModelPVP260(int decimalCode) {
		int errorCode = 0;
		switch (decimalCode) {
		case 0:
			// Sleep state
			errorCode = 464;
			break;
		case 1:
			// Startup delay state
			errorCode = 465;
			break;
		case 2:
			// AC precharge state
			errorCode = 466;
			break;
		case 3:
			// DC precharge state
			errorCode = 467;
			break;
		case 4:
			// Idle state
			errorCode = 468;
			break;
		case 5:
			// Power track state
			errorCode = 469;
			break;
		case 6:
			// Reserved
			errorCode = 470;
			break;
		case 7:
			// Reserved
			errorCode = 471;
			break;
		case 8:
			// Reserved
			errorCode = 472;
			break;
		case 9:
			// Fault state
			errorCode = 473;
			break;
		case 10:
			// Initialization state
			errorCode = 474;
			break;
		case 11:
			// Disabled state
			errorCode = 475;
			break;
		case 12:
			// Latching fault state
			errorCode = 476;
			break;

		}
		
		return errorCode;
	}
	
	// end pvp 260 -------------------------------------------------------------------------------------
	
	/**
	 * get StatesByte4 from table abb
	 * 
	 * @return
	 */
	public static int GetStatesByte4ModelABB(int bitLevel) {
		int errorCode = 0;
		switch (bitLevel) {
		case 0:
			// No Alarm
			errorCode = 360;
			break;
		case 1:
			// Sun Low
			errorCode = 361;
			break;
		case 2:
			// Input OC
			errorCode = 362;
			break;
		case 3:
			// Input UV
			errorCode = 363;
			break;
		case 4:
			// Input OV
			errorCode = 364;
			break;
		case 5:
			// Sun Low
			errorCode = 365;
			break;
		case 6:
			//No Parameters
			errorCode = 366;
			break;
		case 7:
			// Bulk OV
			errorCode = 367;
			break;
		case 8:
			//Comm. Error
			errorCode = 368;
			break;
		case 9:
			// Output OC
			errorCode = 369;
			break;
		case 10:
			// IGBT Sat
			errorCode = 370;
			break;
		case 11:
			// Bulk UV
			errorCode = 371;
			break;
		case 12:
			// Internal error
			errorCode = 372;
			break;
		case 13:
			// Grid Fail
			errorCode = 373;
			break;
		case 14:
			// Bulk Low
			errorCode = 374;
			break;
		case 15:
			// Ramp Fail
			errorCode = 375;
			break;
		case 16:
			// Dc/Dc Fail
			errorCode = 376;
			break;
		case 17:
			// Wrong Mode
			errorCode = 377;
			break;
		case 18:
			//Ground Fault
			errorCode = 378;
			break;
		case 19:
			// Over Temp.
			errorCode = 379;
			break;
		case 20:
			// Bulk Cap Fail
			errorCode = 380;
			break;
		case 21:
			// Inverter Fail
			errorCode = 381;
			break;
		case 22:
			// Start Timeout
			errorCode = 382;
			break;
		case 23:
			// Ground Fault
			errorCode = 383;
			break;
		case 24:
			// Degauss error
			errorCode = 384;
			break;
		case 25:
			// Ileak sens. fail
			errorCode = 385;
			break;
		case 26:
			// DC/DC Fail
			errorCode = 386;
			break;
		case 27:
			// Self-Test Error 1
			errorCode = 387;
			break;
		case 28:
			// Self-Test Error 2
			errorCode = 388;
			break;
		case 29:
			// Self-Test Error 3
			errorCode = 389;
			break;
		case 30:
			// Self-Test Error 4
			errorCode = 390;
			break;
		case 31:
			// DC inj error
			errorCode = 391;
			break;
		case 32:
			// Grid OV
			errorCode = 392;
			break;
		case 33:
			// Grid UV
			errorCode = 393;
			break;
		case 34:
			// Grid OF
			errorCode = 394;
			break;
		case 35:
			// Grid UF
			errorCode = 395;
			break;
		case 36:
			// Z grid Hi
			errorCode = 396;
			break;
		case 37:
			// Internal error
			errorCode = 397;
			break;
		case 38:
			// Riso Low
			errorCode = 398;
			break;
		case 39:
			// Vref Error
			errorCode = 399;
			break;
		case 40:
			// Error Meas V
			errorCode = 400;
			break;
		case 41:
			// Error Meas F
			errorCode = 401;
			break;
		case 42:
			// Error Meas Z
			errorCode = 402;
			break;
		case 43:
			// Error Meas Ileak
			errorCode = 403;
			break;
		case 44:
			// Error Read V
			errorCode = 404;
			break;
		case 45:
			// Error Read I
			errorCode = 405;
			break;
		case 46:
			// Table fail
			errorCode = 406;
			break;
		case 47:
			// Fan Fail
			errorCode = 407;
			break;
		case 48:
			// UTH
			errorCode = 408;
			break;
		case 49:
			// Interlock fail
			errorCode = 409;
			break;
		case 50:
			// Remote Off
			errorCode = 410;
			break;
		case 51:
			// Vout Avg error
			errorCode = 411;
			break;
		case 52:
			// Battery low
			errorCode = 412;
			break;
		case 53:
			// Clk fail
			errorCode = 413;
			break;
		case 54:
			// Input UC
			errorCode = 414;
			break;
		case 55:
			// Zero Power
			errorCode = 415;
			break;
		case 56:
			// Fan Stuck
			errorCode = 416;
			break;
		case 57:
			// DC Switch Open
			errorCode = 417;
			break;
		case 58:
			// Tras Switch Open
			errorCode = 418;
			break;
		case 59:
			// AC Switch Open
			errorCode = 419;
			break;
		case 60:
			// Bulk UV
			errorCode = 420;
			break;
		case 61:
			// Autoexclusion
			errorCode = 421;
			break;
		case 62:
			// Grid df/dt
			errorCode = 422;
			break;
		case 63:
			// Den switch Open
			errorCode = 423;
			break;
		case 64:
			// Jbox fail
			errorCode = 424;
			break;
		case 65:
			// DC Door Open
			errorCode = 425;
			break;
		case 66:
			// AC Door Open
			errorCode = 326;
			break;
		case 67:
			// Anti islanding
			errorCode = 427;
			break;
		case 68:
			// Fuse DC Fail
			errorCode = 428;
			break;
		case 69:
			// Liquid Cooler Fail
			errorCode = 429;
			break;
		case 70:
			// SPD AC protection open
			errorCode = 430;
			break;
		case 71:
			// SPD DC protection open
			errorCode = 431;
			break;
		case 72:
			// String selftest fail
			errorCode = 432;
			break;
		case 73:
			// Power reduction start
			errorCode = 433;
			break;
		case 74:
			// Power reduction end
			errorCode = 434;
			break;
		case 75:
			// React. power mode changed
			errorCode = 435;
			break;
		case 76:
			// date/time changed
			errorCode = 436;
			break;
		case 77:
			// Energy data reset
			errorCode = 437;
			break;
		case 79:
			// Arc fault
			errorCode = 438;
			break;
		case 80:
			// Bad “safety” memory area
			errorCode = 439;
			break;
		case 81:
			// Module Door Open
			errorCode = 440;
			break;
		case 82:
			// AF self-test fail
			errorCode = 441;
			break;
		case 83:
			// Communication loss with Communication Board
			errorCode = 442;
			break;

		}
		
		return errorCode;
	}
	
	/**
	 * get StatesByte2 from table abb
	 * 
	 * @return
	 */
	public static int GetStatesByte3ModelABB(int bitLevel) {
		int errorCode = 0;
		switch (bitLevel) {
		case 0:
			// DC/DC OFF
			errorCode = 443;
			break;
		case 1:
			// Ramp Start
			errorCode = 444;
			break;
		case 2:
			// MPPT
			errorCode = 445;
			break;
		case 3:
			// Not Used
			errorCode = 446;
			break;
		case 4:
			// Input OC
			errorCode = 447;
			break;
		case 5:
			// Input UV
			errorCode = 448;
			break;
		case 6:
			// Input OV
			errorCode = 449;
			break;
		case 7:
			// Input Low
			errorCode = 450;
			break;
		case 8:
			// No Parameters
			errorCode = 451;
			break;
		case 9:
			// Bulk OV
			errorCode = 452;
			break;
		case 10:
			// Communication Error
			errorCode = 453;
			break;
		case 11:
			// Ramp Fail
			errorCode = 454;
			break;
		case 12:
			// Internal Error
			errorCode = 455;
			break;
		case 13:
			// Input mode Error
			errorCode = 456;
			break;
		case 14:
			// Ground Fault
			errorCode = 457;
			break;
		case 15:
			// Inverter Fail
			errorCode = 458;
			break;
		case 16:
			// DC/DC IGBT Sat
			errorCode = 459;
			break;
		case 17:
			// DC/DC ILEAK Fail
			errorCode = 460;
			break;
		case 18:
			// DC/DC Grid Fail
			errorCode = 461;
			break;
		case 19:
			// DC/DC Comm. Error
			errorCode = 462;
			break;
		case 255:
			// DC/DC DSP not programmed
			errorCode = 463;
			break;

		}
		
		return errorCode;
	}
	
	/**
	 * get StatesByte2 from table abb
	 * 
	 * @return
	 */
	public static int GetStatesByte2ModelABB(int bitLevel) {
		int errorCode = 0;
		switch (bitLevel) {
		case 0:
			// DC/DC OFF
			errorCode = 339;
			break;
		case 1:
			// Ramp Start
			errorCode = 340;
			break;
		case 2:
			// MPPT
			errorCode = 341;
			break;
		case 3:
			// Not Used
			errorCode = 342;
			break;
		case 4:
			// Input OC
			errorCode = 343;
			break;
		case 5:
			// Input UV
			errorCode = 344;
			break;
		case 6:
			// Input OV
			errorCode = 345;
			break;
		case 7:
			// Input Low
			errorCode = 346;
			break;
		case 8:
			// No Parameters
			errorCode = 347;
			break;
		case 9:
			// Bulk OV
			errorCode = 348;
			break;
		case 10:
			// Communication Error
			errorCode = 349;
			break;
		case 11:
			// Ramp Fail
			errorCode = 350;
			break;
		case 12:
			// Internal Error
			errorCode = 351;
			break;
		case 13:
			// Input mode Error
			errorCode = 352;
			break;
		case 14:
			// Ground Fault
			errorCode = 353;
			break;
		case 15:
			// Inverter Fail
			errorCode = 354;
			break;
		case 16:
			// DC/DC IGBT Sat
			errorCode = 355;
			break;
		case 17:
			// DC/DC ILEAK Fail
			errorCode = 356;
			break;
		case 18:
			// DC/DC Grid Fail
			errorCode = 357;
			break;
		case 19:
			// DC/DC Comm. Error
			errorCode = 358;
			break;
		case 255:
			// DC/DC DSP not programmed
			errorCode = 359;
			break;

		}
		
		return errorCode;
	}
	
	/**
	 * get StatesByte1 from table abb
	 * 
	 * @return
	 */
	public static int GetStatesByte1ModelABB(int bitLevel) {
		int errorCode = 0;
		switch (bitLevel) {
		case 0:
			// Stand By
			errorCode = 298;
			break;
		case 1:
			// Checking Grid
			errorCode = 299;
			break;
		case 2:
			// Run
			errorCode = 300;
			break;
		case 3:
			// Bulk OV
			errorCode = 301;
			break;
		case 4:
			// Out OC
			errorCode = 302;
			break;
		case 5:
			// IGBT Sat
			errorCode = 303;
			break;
		case 6:
			// Bulk UV
			errorCode = 304;
			break;
		case 7:
			// Degauss Error
			errorCode = 305;
			break;
		case 8:
			// No Parameters
			errorCode = 306;
			break;
		case 9:
			// Bulk Low
			errorCode = 307;
			break;
		case 10:
			// Grid OV
			errorCode = 308;
			break;
		case 11:
			// Communication Error
			errorCode = 309;
			break;
		case 12:
			// Degaussing
			errorCode = 310;
			break;
		case 13:
			// Starting
			errorCode = 311;
			break;
		case 14:
			// Bulk Cap Fail
			errorCode = 312;
			break;
		case 15:
			// Leak Fail
			errorCode = 313;
			break;
		case 16:
			// DC/DC Fail
			errorCode = 314;
			break;
		case 17:
			// Ileak Sensor Fail
			errorCode = 315;
			break;
		case 18:
			// Self-Test: relay inverter
			errorCode = 316;
			break;
		case 19:
			// Self-Test: wait for sensor test
			errorCode = 317;
			break;
		case 20:
			// Self-Test: test relay DC/DC + sensor
			errorCode = 318;
			break;
		case 21:
			// Self-Test: relay inverter fail
			errorCode =319;
			break;
		case 22:
			// Self-Test timeout fail
			errorCode = 320;
			break;
		case 23:
			// Self-Test: relay DC/DC fail
			errorCode = 321;
			break;
		case 24:
			// Self-Test 1
			errorCode = 322;
			break;
		case 25:
			// Waiting Self-Test start
			errorCode = 323;
			break;
		case 26:
			// Dc Injection
			errorCode = 324;
			break;
		case 27:
			// Self-Test 2
			errorCode = 325;
			break;
		case 28:
			// Self-Test 3
			errorCode = 326;
			break;
		case 29:
			// Self-Test 4
			errorCode = 327;
			break;
		case 30:
			// Internal Error
			errorCode = 328;
			break;
		case 31:
			// Internal Error
			errorCode = 329;
			break;
		case 40:
			// Forbidden State
			errorCode = 330;
			break;
		case 41:
			// Input UC
			errorCode = 331;
			break;
		case 42:
			// Zero Power
			errorCode = 332;
			break;
		case 43:
			// Grid Not Present
			errorCode = 333;
			break;
		case 44:
			// Waiting Start
			errorCode = 334;
			break;
		case 45:
			// MPPT
			errorCode = 335;
			break;
		case 46:
			// Grid Fail
			errorCode = 336;
			break;
		case 47:
			// Input OC
			errorCode = 337;
			break;
		case 255:
			// Inverter DSP not programmed
			errorCode = 338;
			break;

		}
		
		return errorCode;
	}
	
	
	/**
	 * get StatesByte0 from table abb
	 * 
	 * @return
	 */
	public static int GetStatesByte0ModelABB(int bitLevel) {
		int errorCode = 0;
		switch (bitLevel) {
		case 0:
			// Sending Parameters
			errorCode = 247;
			break;
		case 1:
			// Wait Sun/Grid
			errorCode = 248;
			break;
		case 2:
			// Checking Grid
			errorCode = 249;
			break;
		case 3:
			// Measuring Riso
			errorCode = 250;
			break;
		case 4:
			// DC/DC Start
			errorCode = 251;
			break;
		case 5:
			// Inverter Start
			errorCode = 252;
			break;
		case 6:
			// Run
			errorCode = 253;
			break;
		case 7:
			// Recovery
			errorCode = 254;
			break;
		case 8:
			// Pause
			errorCode = 255;
			break;
		case 9:
			// Ground Fault
			errorCode = 256;
			break;
		case 10:
			// OTH Fault
			errorCode = 257;
			break;
		case 11:
			// Address Setting
			errorCode = 258;
			break;
		case 12:
			// Self-Test
			errorCode = 259;
			break;
		case 13:
			// Self-Test Fail
			errorCode = 260;
			break;
		case 14:
			// Sensor Test + Meas.Riso
			errorCode = 261;
			break;
		case 15:
			// Leak Fault
			errorCode = 262;
			break;
		case 16:
			// Waiting for manual reset
			errorCode = 263;
			break;
		case 17:
			// Internal Error E026
			errorCode = 264;
			break;
		case 18:
			// Internal Error E027
			errorCode = 265;
			break;
		case 19:
			// Internal Error E028
			errorCode = 266;
			break;
		case 20:
			// Internal Error E029
			errorCode = 267;
			break;
		case 21:
			// Internal Error E030
			errorCode = 268;
			break;
		case 22:
			// Sending Wind Table
			errorCode = 269;
			break;
		case 23:
			// Failed Sending table
			errorCode = 270;
			break;
		case 24:
			// UTH Fault
			errorCode = 271;
			break;
		case 25:
			// Remote OFF
			errorCode = 272;
			break;
		case 26:
			// Interlock Fail
			errorCode = 273;
			break;
		case 27:
			// Executing Autotest
			errorCode = 274;
			break;
		case 30:
			// Waiting Sun
			errorCode = 275;
			break;
		case 31:
			// Temperature Fault
			errorCode = 276;
			break;
		case 32:
			// Fan Staucked
			errorCode = 277;
			break;
		case 33:
			// Int. Com. Fault
			errorCode = 278;
			break;
		case 34:
			// Slave Insertion
			errorCode = 279;
			break;
		case 35:
			// DC Switch Open
			errorCode = 280;
			break;
		case 36:
			// TRAS Switch Open
			errorCode = 281;
			break;
		case 37:
			// MASTER Exclusion
			errorCode = 282;
			break;
		case 38:
			// Auto Exclusion
			errorCode = 283;
			break;
		case 98:
			// Erasing Internal EEprom
			errorCode = 284;
			break;
		case 99:
			// Erasing External EEprom
			errorCode = 285;
			break;
		case 100:
			// Counting EEprom
			errorCode = 286;
			break;
		case 101:
			// Freeze
			errorCode = 287;
			break;
		case 110:
			// Forbidden partner board was found
			errorCode = 288;
			break;
		case 111:
			// DC string self-test fault
			errorCode = 289;
			break;
		case 112:
			// Service mode
			errorCode = 290;
			break;
		case 113:
			// “Safety” memory area error
			errorCode = 291;
			break;
		case 114:
			// Too many leak fault events for the day
			errorCode = 292;
			break;
		case 115:
			// Arc fault 
			errorCode = 293;
			break;
		case 116:
			// AF self-test fail
			errorCode = 294;
			break;
		case 117:
			// Communication loss with Communication Board
			errorCode = 295;
			break;
		case 150:
			// DSP communication error
			errorCode = 296;
			break;
		case 200:
			// Dsp Programming
			errorCode = 297;
			break;
		}
		
		

		return errorCode;
	}
	
	// end abb -------------------------------------------------------------------------------------
	
	/**
	 * get FaultCode1 from table model_chint_solectria_inverter_class9725
	 * 
	 * @return
	 */
	public static int GetFaultCode2ModelSolectria(int bitLevel) {
		int errorCode = 0;
		switch (bitLevel) {
		case 0:
			// Reserved
			errorCode = 231;
			break;
		case 1:
			// Internal hardware error
			errorCode = 232;
			break;
		case 2:
			// Input and output power mismatch
			errorCode = 233;
			break;
		case 3:
			// PV2 input reverse connection
			errorCode = 234;
			break;
		case 4:
			// PV2 over current
			errorCode = 235;
			break;
		case 5:
			// PV2 over voltage
			errorCode = 236;
			break;
		case 6:
			// PV abnormal input
			errorCode = 237;
			break;
		case 7:
			// Inverter open-loop self-test error
			errorCode = 238;
			break;
		case 8:
			// Reserved
			errorCode = 239;
			break;
		case 9:
			// PV1 input reverse connection
			errorCode = 240;
			break;
		case 10:
			// PV1 over voltage
			errorCode = 241;
			break;
		case 11:
			// Reserved
			errorCode = 242;
			break;
		case 12:
			// Reserved
			errorCode = 243;
			break;
		case 13:
			// Arcboard abnormal
			errorCode = 244;
			break;
		case 14:
			// Static GFI Protect
			errorCode = 245;
			break;
		case 15:
			// Arc Protection
			errorCode = 246;
			break;
		}

		return errorCode;
	}
	
	
	/**
	 * get FaultCode1 from table model_chint_solectria_inverter_class9725
	 * 
	 * @return
	 */
	public static int GetFaultCode1ModelSolectria(int bitLevel) {
		int errorCode = 0;
		switch (bitLevel) {
		case 0:
			// Inverter voltage offset error
			errorCode = 215;
			break;
		case 1:
			// DCI offset error
			errorCode = 216;
			break;
		case 2:
			// DCI high
			errorCode = 217;
			break;
		case 3:
			// Insulation resistance low
			errorCode = 218;
			break;
		case 4:
			// Dynamic leakage current high
			errorCode = 219;
			break;
		case 5:
			// Frequency detection fault
			errorCode = 220;
			break;
		case 6:
			// Reserved
			errorCode = 221;
			break;
		case 7:
			// MCU protection
			errorCode = 222;
			break;
		case 8:
			// Inverter hardware over current
			errorCode = 223;
			break;
		case 9:
			// Grid voltage imbalance
			errorCode = 224;
			break;
		case 10:
			// Reserved
			errorCode = 225;
			break;
		case 11:
			// Inverter current imbalance
			errorCode = 226;
			break;
		case 12:
			// Power module protection
			errorCode = 227;
			break;
		case 13:
			// Reserved
			errorCode = 228;
			break;
		case 14:
			// Reserved
			errorCode = 229;
			break;
		case 15:
			// Leakage current sensor error
			errorCode = 230;
			break;
		}

		return errorCode;
	}
	
	
	/**
	 * get FaultCode0 from table model_chint_solectria_inverter_class9725
	 * 
	 * @return
	 */
	public static int GetFaultCode0ModelSolectria(int bitLevel) {
		int errorCode = 0;
		switch (bitLevel) {
		case 0:
			// Bus(sum) over voltage (firmware)
			errorCode = 199;
			break;
		case 1:
			// Bus(sum) low voltage
			errorCode = 200;
			break;
		case 2:
			// Bus imbalance
			errorCode = 201;
			break;
		case 3:
			// Bus soft start timeout
			errorCode = 202;
			break;
		case 4:
			// Inverter soft start timeout
			errorCode = 203;
			break;
		case 5:
			// Reserved
			errorCode = 204;
			break;
		case 6:
			// PV1 over current
			errorCode = 205;
			break;
		case 7:
			// Grid line voltage out of range
			errorCode = 206;
			break;
		case 8:
			// Grid phase voltage out of range
			errorCode = 207;
			break;
		case 9:
			// Inverter over current
			errorCode = 208;
			break;
		case 10:
			// Grid Over frequency
			errorCode = 209;
			break;
		case 11:
			// Grid under frequency
			errorCode = 210;
			break;
		case 12:
			// Loss of main
			errorCode = 211;
			break;
		case 13:
			// Grid relay error
			errorCode = 212;
			break;
		case 14:
			// Over-temperature protection
			errorCode = 213;
			break;
		case 15:
			// Sampling offset of output current error
			errorCode = 214;
			break;
		}

		return errorCode;
	}
	
	/**
	 * get warning code from table model_chint_solectria_inverter_class9725
	 * 
	 * @return
	 */
	public static int GetWarningCodeModelSolectria(int bitLevel) {
		int errorCode = 0;
		switch (bitLevel) {
		case 0:
			// External fan error
			errorCode = 183;
			break;
		case 1:
			// Internal fan error
			errorCode = 184;
			break;
		case 2:
			// Internal communication failed
			errorCode = 185;
			break;
		case 3:
			// DSP EEPROM fault
			errorCode = 186;
			break;
		case 4:
			// Not used
			errorCode = 187;
			break;
		case 5:
			// Temperature sensor fault
			errorCode = 188;
			break;
		case 6:
			// Reserved
			errorCode = 189;
			break;
		case 7:
			// Reserved
			errorCode = 190;
			break;
		case 8:
			// Reserved
			errorCode = 191;
			break;
		case 9:
			// LCD EEPROM fault
			errorCode = 192;
			break;
		case 10:
			// Reserved
			errorCode = 193;
			break;
		case 11:
			// Reserved
			errorCode = 194;
			break;
		case 12:
			// Reserved
			errorCode = 195;
			break;
		case 13:
			// Reserved
			errorCode = 196;
			break;
		case 14:
			// Reserved
			errorCode = 197;
			break;
		case 15:
			// Reserved
			errorCode = 198;
			break;
		}

		return errorCode;
	}
	
	
	/**
	 * get warning PermanentFaultCode from table model_chint_solectria_inverter_class9725
	 * 
	 * @return
	 */
	public static int GetPermanentFaultCodeModelSolectria(int bitLevel) {
		int errorCode = 0;
		switch (bitLevel) {
		case 0:
			// Bus(sum) over voltage fault
			errorCode = 167;
			break;
		case 1:
			// Bus(sum) low voltage
			errorCode = 168;
			break;
		case 2:
			// Bus imbalance fault
			errorCode = 169;
			break;
		case 3:
			// Grid relay fault
			errorCode = 170;
			break;
		case 4:
			// Static GFCI Fault
			errorCode = 171;
			break;
		case 5:
			// Reserved
			errorCode = 172;
			break;
		case 6:
			// DCI fault
			errorCode = 173;
			break;
		case 7:
			// Reserved
			errorCode = 174;
			break;
		case 8:
			// Hardware over current fault
			errorCode = 175;
			break;
		case 9:
			// Reserved
			errorCode = 176;
			break;
		case 10:
			// Reserved
			errorCode = 177;
			break;
		case 11:
			// Reserved
			errorCode = 178;
			break;
		case 12:
			// Power module fault
			errorCode = 179;
			break;
		case 13:
			// Internal hardware fault
			errorCode = 180;
			break;
		case 14:
			// Inverter open-loop self-test fault
			errorCode = 181;
			break;
		case 15:
			// 15V of control board low fault
			errorCode = 182;
			break;
		}

		return errorCode;
	}
	
	// end solectria -------------------------------------------------------------------------------------
	
	/**
	 * get error code from table model_advanced_energy_solaron
	 * 
	 * @return
	 */
	public static int GetErrorCodeModelAdvancedSolaron(int bitLevel, int faultLevel) {
		int errorCode = 0;
		switch (faultLevel) {
		case 1:
			switch (bitLevel) {
			case 0:
				// The DC auxiliary power supply voltages are out of range.
				errorCode = 71;
				break;
			case 2:
				// The softstart relay did not close properly in order to charge the DCC bus.
				// This can occur if the AC grid fails and the facility has a backup generator
				// that is interlocked with the inverter.
				errorCode = 73;
				break;
			case 3:
				// Indicates one of the following:
				// The pump has stopped
				// Coolant has leaked enough to have air in the coolant system
				errorCode = 74;
				break;
			case 4:
				// The coolant has reached its maximum temperature limit. The pump and blower
				// will continue to run until the temperature falls back within limits and then
				// the unit will automatically restart.
				errorCode = 75;
				break;
			case 5:
				// Indicates a failure of the control board.
				errorCode = 76;
				break;
			case 6:
				// Indicates one of the following:
				errorCode = 77;
				break;
			case 10:
				// Error occurred when updating firmware.
				errorCode = 78;
				break;
			case 11:
				// The current was flowing into the PV panels from the DC bus. This fault could
				// occur because of a shorted PV panel or another problem with the array.
				errorCode = 79;
				break;
			case 12:
				// The DC bus voltage is too high to allow the inverter to turn on.
				errorCode = 80;
				break;
			case 13:
				// The DC bus voltage fell below the minimum value required to allow the unit to
				// continue to run.
				errorCode = 81;
				break;
			case 14:
				// Negative DC from the array has gone past the trip limit.
				errorCode = 82;
				break;
			case 15:
				// Hardware protection against incorrect input AC voltage.
				errorCode = 83;
				break;
			case 16:
				// AC output current has exceeded the allowed maximum.
				errorCode = 84;
				break;
			case 17:
				// There is an unexplained imbalance of current in the 3-phase AC output. This
				// is caused by either AC ground current or a failure in the AC measurement
				// devices.
				errorCode = 85;
				break;
			case 19:
				// A 3-phase voltage surge exceeded the limit of the unit.
				errorCode = 86;
				break;
			case 22:
				// The mains contactor opened.
				errorCode = 87;
				break;
			case 24:
				// There was a sag in the 3-phase line voltage that went beyond the limit of the
				// unit in either time or voltage.
				errorCode = 88;
				break;
			case 25:
				// The unit has cycled on and off too many times in a short period. There might
				// be something wrong with the unit or the utility supply.
				errorCode = 89;
				break;
			case 26:
				// The line reactor temperature in the bottom of the unit cabinet has exceeded
				// the maximum limit. There might be something wrong with the airflow.
				errorCode = 90;
				break;
			case 29:
				// A low frequency has persisted too long for the parameters of the unit.
				errorCode = 91;
				break;
			case 30:
				// AC frequency has exceeded the limit set in the configuration file.
				errorCode = 92;
				break;
			case 31:
				// The ground current from the DC side exceeds the limit.
				errorCode = 93;
				break;
			}
			break;
		case 2:
			switch (bitLevel) {
			case 0:
				// There is too much AC common-mode voltage on the PV area neutral and hot
				// wires.
				errorCode = 94;
				break;
			case 1:
				// The DC contactor has reported that it has unexpectedly openedor has not
				// operated properly during startup.
				errorCode = 95;
				break;
			case 2:
				// The ambient temperature has exceeded the upper limit. The pump and fans will
				// continue to run until the temperature falls below the limit.
				errorCode = 96;
				break;
			case 3:
				// The cabinet temperature has exceeded the upper limit. The pump and fans will
				// continue to run until the temperature falls below the limit.
				errorCode = 97;
				break;
			case 4:
				// The PV array tie contactor has reported that it has unexpectedly opened or
				// has not operated properly during startup.
				errorCode = 98;
				break;
			case 9:
				// A cable or connector has become loose inside the unit.
				errorCode = 99;
				break;
			case 10:
				// A cable or connector has become loose inside the unit.
				errorCode = 100;
				break;
			case 11:
				// A cable or connector has become loose inside the unit.
				errorCode = 101;
				break;
			case 12:
				// A cable or connector has become loose inside the unit.
				errorCode = 102;
				break;
			case 13:
				// A cable or connector has become loose inside the unit.
				errorCode = 103;
				break;
			case 14:
				// A cable or connector has become loose inside the unit.
				errorCode = 104;
				break;
			case 15:
				// A cable or connector has become loose inside the unit.
				errorCode = 105;
				break;
			case 16:
				// A cable or connector has become loose inside the unit.
				errorCode = 106;
				break;
			case 17:
				// The internal DC bus voltage did not reach an acceptable level quickly enough.
				errorCode = 107;
				break;
			case 18:
				// Someone has pressed the Stop button or the external interlock is preventing
				// the unit from operating.
				errorCode = 108;
				break;
			case 19:
				// A cloud edge disturbed the PV voltage to the unit during turn-on before the
				// unit's DCcontactor could close, causing too great a difference between the
				// bus voltage and the PV voltage.
				errorCode = 109;
				break;
			case 20:
				// Fan is not running fast enough.
				errorCode = 110;
				break;
			case 21:
				// Fan is not running fast enough.
				errorCode = 111;
				break;
			case 22:
				// Fan is not running fast enough.
				errorCode = 112;
				break;
			case 23:
				// Fan is not running fast enough.
				errorCode = 113;
				break;
			case 24:
				// Fan is not running fast enough.
				errorCode = 114;
				break;
			case 25:
				// The positive and negative bipolar PV array voltages are out of balance (not
				// equal), possibly due to a ground fault or insulation failure in the array. On
				// units with the optional charge Equalizer accessory, this fault may occur at
				// night or during the wake-up transition. Array balance faults produced under
				// these conditions are considered normal behavior.
				errorCode = 115;
				break;
			case 26:
				// The available PV array power increased too fast for the inverter to back off
				// the voltage and keep the power from exceeding the trip limit.
				errorCode = 116;
				break;
			case 27:
				// A failure has occurred in a ground fault detection component in the unit.
				errorCode = 117;
				break;
			case 28:
				// Fan is not running fast enough.
				errorCode = 118;
				break;
			case 29:
				// Fan is not running fast enough.
				errorCode = 119;
				break;
			}
			break;
		case 3:
			switch (bitLevel) {
			case 0:
				// The cable for the ground fault detection and interruption(GFDI) device is
				// loose or removed.
				errorCode = 120;
				break;
			case 1:
				// The soft-start contactor is likely to be welded closed and unable to open.
				// Caution is required when dealing with thissituation.
				errorCode = 121;
				break;
			case 2:
				// The PV Tie contactor is likely to be welded closed and unable to open.
				// Caution is required when dealing with this situation.
				errorCode = 122;
				break;
			case 3:
				// The DC contactor is likely to be welded closed and unable to open. Caution is
				// required when dealing with this situation.
				errorCode = 123;
				break;
			case 4:
				// The AC contactor is likely to be welded closed and unable to open. Caution is
				// required when dealing with this situation.
				errorCode = 124;
				break;
			case 8:
				// Fan is not running fast enough.
				errorCode = 125;
				break;
			case 9:
				// phase-A low
				errorCode = 126;
				break;
			case 10:
				// phase-B low
				errorCode = 127;
				break;
			case 11:
				// phase-C low
				errorCode = 128;
				break;
			case 12:
				// phase-A high
				errorCode = 129;
				break;
			case 13:
				// phase-B high
				errorCode = 130;
				break;
			case 14:
				// phase-C high
				errorCode = 131;
				break;
			}
			break;
		}

		return errorCode;
	}
	
	
	
	/**
	 * get wainning limit codes from table model_advanced_energy_solaron
	 * 
	 * @return
	 */
	public static int GetLimitCodeModelAdvancedSolaron(int bitLevel) {
		int errorCode = 0;
		switch (bitLevel) {
		case 8:
			// The unit is reducing power because the output alternating current limit has been exceeded.
			errorCode = 138;
			break;
		case 16:
			// The unit is reducing power because the PV array direct current limit has been exceeded.
			errorCode = 139;
			break;
		case 17:
			// The unit is reducing output power because the AC power limit has been exceeded.
			errorCode = 140;
			break;
		case 18:
			// The MPPT is limited due to excessive DC voltage.
			errorCode = 141;
			break;
		case 19:
			// The MPPT is limited due to insufficient DC voltage.
			errorCode = 142;
			break;
		case 20:
			// The unit is consuming reactive power to limit current harmonics.
			errorCode = 143;
			break;
		case 21:
			// The unit is reducing power due to excessive coolant temperature.
			errorCode = 144;
			break;
		case 22:
			// The unit is inhibiting PWM switching due to excessive AC current.
			errorCode = 145;
			break;
		case 23:
			// The unit is inhibiting PWM switching due to excessive bus capacitor voltage slew rate.
			errorCode = 146;
			break;
		case 24:
			// The unit is inhibiting PWM switching due to excessive power.
			errorCode = 147;
			break;
		}

		return errorCode;
	}
	
	
	/**
	 * get status codes from table model_advanced_energy_solaron
	 * 
	 * @return
	 */
	public static int GetStatusCodeModelAdvancedSolaron(int bitLevel) {
		int errorCode = 0;
		switch (bitLevel) {
		case 0:
			// Bit set if unit is on
			errorCode = 148;
			break;
		case 1:
			// Bit set if the unit has one or more active faults. Important Bit 1 is normally set during sleep status and should be treated as information only during sleep status.
			errorCode = 149;
			break;
		case 2:
			// Bit set if the unit operation has been affected by one or more operating limits. Important Limits are not seen as faults by the unit. The unit will continue to operate with one or more active limits and should not cause alerts to a SCADA control system.
			errorCode = 150;
			break;
		case 3:
			// Bit set for enabled for master control enabled
			errorCode = 151;
			break;
		case 4:
			// Bit set if the unit is in startup mode
			errorCode = 152;
			break;
		case 5:
			// Bit set if the unit has an active warning. Important Warnings are not seen as faults by the unit. The unit will continue to operate with one or more active warnings and should not cause alerts to a SCADA control system.
			errorCode = 153;
			break;
		case 6:
			// Bit set if the unit has been locked out
			errorCode = 154;
			break;
		case 8:
			// Bit set for tracking on (MPP active)
			errorCode = 155;
			break;
		case 9:
			// Bit set for sleep. Important Bit 1 (fault status) is normally set whenever bit 9 is set, and should be treated as information only when the sleep bit is set and not as an active fault.
			errorCode = 156;
			break;
		case 10:
			// Bit set for autostart on
			errorCode = 157;
			break;
		case 11:
			// Bit set if a surge protection device has failed
			errorCode = 158;
			break;
		}

		return errorCode;
	}
	
	
	/**
	 * get warning codes from table model_advanced_energy_solaron
	 * 
	 * @return
	 */
	public static int GetWarningsCodeModelAdvancedSolaron(int bitLevel) {
		int errorCode = 0;
		switch (bitLevel) {
		case 1:
			// Fan not operating normally
			errorCode = 159;
			break;
		case 2:
			// Fan not operating normally
			errorCode = 160;
			break;
		case 3:
			// Fan not operating normally
			errorCode = 161;
			break;
		case 4:
			// Fan not operating normally
			errorCode = 162;
			break;
		case 5:
			// Fan not operating normally
			errorCode = 163;
			break;
		case 6:
			// Fan not operating normally
			errorCode = 164;
			break;
		case 7:
			// Fan not operating normally
			errorCode = 165;
			break;
		case 8:
			// Charge abatement option not operating correctly
			errorCode = 166;
			break;
		}

		return errorCode;
	}
	
	
	/**
	 * get error code from table model_satcon_pvs357_inverter
	 * 
	 * @return
	 */
	public static int GetErrorCodeModelSatconPVS357Inverter(int bitLevel, int faultLevel) {
		int errorCode = 0;
		switch (faultLevel) {
		case 1:
			switch (bitLevel) {
			case 0: errorCode = 569; break; // 0
			case 1: errorCode = 570; break; // 1
			case 2: errorCode = 571; break; // 2
			case 3: errorCode = 572; break; // 3
			case 4: errorCode = 573; break; // 4
			case 5: errorCode = 574; break; // 5
			case 6: errorCode = 575; break; // 6
			case 7: errorCode = 576; break; // 7
			case 8: errorCode = 577; break; // 8
			case 9: errorCode = 578; break; // 9
			case 10: errorCode = 579; break; // 10
			case 11: errorCode = 580; break; // 11
			case 12: errorCode = 581; break; // 12
			case 13: errorCode = 582; break; // 13
			case 14: errorCode = 583; break; // 14
			case 15: errorCode = 584; break; // 15
			}
			break;
			
		case 2: 
			switch (bitLevel) {
			case 0: errorCode = 585; break; // 15
			case 1: errorCode = 586; break; // 17
			case 2: errorCode = 587; break; // 18
			case 3: errorCode = 588; break; // 19
			case 4: errorCode = 589; break; // 20
			case 5: errorCode = 590; break; // 21
			case 6: errorCode = 591; break; // 22
			case 7: errorCode = 592; break; // 23
			case 8: errorCode = 593; break; // 24
			case 9: errorCode = 594; break; // 25
			case 10: errorCode = 595; break; // 26
			case 11: errorCode = 596; break; // 27
			case 12: errorCode = 597; break; // 28
			case 13: errorCode = 598; break; // 29
			case 14: errorCode = 599; break; // 30
			case 15: errorCode = 600; break; // 31
			}
			break;
		case 3: 
			switch (bitLevel) {
			case 0: errorCode = 601; break; // 32
			case 1: errorCode = 602; break; // 33
			case 2: errorCode = 603; break; // 34
			case 3: errorCode = 604; break; // 35
			case 4: errorCode = 605; break; // 36
			case 5: errorCode = 606; break; // 37
			case 6: errorCode = 607; break; // 38
			case 7: errorCode = 608; break; // 39
			case 8: errorCode = 609; break; // 40 
			case 9: errorCode = 610; break; // 41
			case 10: errorCode = 611; break; // 42
			case 11: errorCode = 612; break; // 43
			case 12: errorCode = 613; break; // 44
			case 13: errorCode = 614; break; // 45
			case 14: errorCode = 615; break; // 46
			case 15: errorCode = 616; break; // 47
			
			}
			break;
		case 4: 
			switch (bitLevel) {
			case 0: errorCode = 617; break; // 48
			case 1: errorCode = 618; break; // 49
			case 2: errorCode = 619; break; // 50
			case 3: errorCode = 620; break; // 51
			case 4: errorCode = 621; break; // 52
			case 5: errorCode = 622; break; // 53
			case 6: errorCode = 623; break; // 54
			case 7: errorCode = 624; break; // 55
			case 8: errorCode = 625; break; // 56
			case 9: errorCode = 626; break; // 57
			case 10: errorCode = 627; break; // 58
			case 11: errorCode = 628; break; // 59
			case 12: errorCode = 629; break; // 60
			case 13: errorCode = 630; break; // 61
			case 14: errorCode = 631; break; // 62
			case 15: errorCode = 632; break; // 63
			}
			break;
		case 5: 
			switch (bitLevel) {
			case 0: errorCode = 633; break; // 64
			case 1: errorCode = 634; break; // 65
			case 2: errorCode = 635; break; // 66
			case 3: errorCode = 636; break; // 67
			case 4: errorCode = 637; break; // 68
			case 5: errorCode = 638; break; // 69
			case 6: errorCode = 639; break; // 70
			case 7: errorCode = 640; break; // 71
			case 8: errorCode = 641; break; // 72
			case 9: errorCode = 642; break; // 73
			case 10: errorCode = 643; break; // 74
			case 11: errorCode = 644; break; // 75
			case 12: errorCode = 645; break; // 76
			case 13: errorCode = 646; break; // 77
			case 14: errorCode = 647; break; // 78
			case 15: errorCode = 648; break; // 79
			}
			break;
		case 6: 
			switch (bitLevel) {
			case 0: errorCode = 649; break; // 80
			case 1: errorCode = 650; break; // 81
			case 2: errorCode = 651; break; // 82
			case 3: errorCode = 652; break; // 83
			case 4: errorCode = 653; break; // 84
			case 5: errorCode = 654; break; // 85
			case 6: errorCode = 655; break; // 86
			case 7: errorCode = 656; break; // 87
			case 8: errorCode = 657; break; // 88
			case 9: errorCode = 658; break; // 89
			case 10: errorCode = 659; break; // 90
			case 11: errorCode = 660; break; // 91
			case 12: errorCode = 661; break; // 92
			case 13: errorCode = 662; break; // 93
			case 14: errorCode = 663; break; // 94
			case 15: errorCode = 664; break; // 95
			}
			break;
		case 7: 
			switch (bitLevel) {
			case 0: errorCode = 665; break; // 96
			case 1: errorCode = 666; break; // 97
			case 2: errorCode = 667; break; // 98
			case 3: errorCode = 668; break; // 99
			case 4: errorCode = 669; break; // 100
			case 5: errorCode = 670; break; // 101
			}
			break;
		
		
		}

		return errorCode;
	}
	
	
	
	
	
	
	/**
	 * get error code from table model_ivt_solaron_ext
	 * 
	 * @return
	 */
	public static int GetErrorCodeModelIVTSolaronEXT(int bitLevel, int faultLevel) {
		int errorCode = 0;
		switch (faultLevel) {
		case 1:
			switch (bitLevel) {
			case 0:
				// The DC auxiliary power supply voltages are out of range.
				errorCode = 671;
				break;
			case 2:
				// The softstart relay did not close properly in order to charge the DCC bus.
				// This can occur if the AC grid fails and the facility has a backup generator
				// that is interlocked with the inverter.
				errorCode = 672;
				break;
			case 3:
				// Indicates one of the following:
				// The pump has stopped
				// Coolant has leaked enough to have air in the coolant system
				errorCode = 673;
				break;
			case 4:
				// The coolant has reached its maximum temperature limit. The pump and blower
				// will continue to run until the temperature falls back within limits and then
				// the unit will automatically restart.
				errorCode = 674;
				break;
			case 5:
				// Indicates a failure of the control board.
				errorCode = 675;
				break;
			case 6:
				// Indicates one of the following:
				errorCode = 676;
				break;
			case 10:
				// Error occurred when updating firmware.
				errorCode = 677;
				break;
			case 11:
				// The current was flowing into the PV panels from the DC bus. This fault could
				// occur because of a shorted PV panel or another problem with the array.
				errorCode = 678;
				break;
			case 12:
				// The DC bus voltage is too high to allow the inverter to turn on.
				errorCode = 679;
				break;
			case 13:
				// The DC bus voltage fell below the minimum value required to allow the unit to
				// continue to run.
				errorCode = 680;
				break;
			case 14:
				// Negative DC from the array has gone past the trip limit.
				errorCode = 681;
				break;
			case 15:
				// Hardware protection against incorrect input AC voltage.
				errorCode = 682;
				break;
			case 16:
				// AC output current has exceeded the allowed maximum.
				errorCode = 683;
				break;
			case 17:
				// There is an unexplained imbalance of current in the 3-phase AC output. This
				// is caused by either AC ground current or a failure in the AC measurement
				// devices.
				errorCode = 684;
				break;
			case 19:
				// A 3-phase voltage surge exceeded the limit of the unit.
				errorCode = 685;
				break;
			case 22:
				// The mains contactor opened.
				errorCode = 686;
				break;
			case 24:
				// There was a sag in the 3-phase line voltage that went beyond the limit of the
				// unit in either time or voltage.
				errorCode = 687;
				break;
			case 25:
				// The unit has cycled on and off too many times in a short period. There might
				// be something wrong with the unit or the utility supply.
				errorCode = 688;
				break;
			case 26:
				// The line reactor temperature in the bottom of the unit cabinet has exceeded
				// the maximum limit. There might be something wrong with the airflow.
				errorCode = 689;
				break;
			case 29:
				// A low frequency has persisted too long for the parameters of the unit.
				errorCode = 690;
				break;
			case 30:
				// AC frequency has exceeded the limit set in the configuration file.
				errorCode = 691;
				break;
			case 31:
				// The ground current from the DC side exceeds the limit.
				errorCode = 692;
				break;
			}
			break;
		case 2:
			switch (bitLevel) {
			case 0:
				// There is too much AC common-mode voltage on the PV area neutral and hot
				// wires.
				errorCode = 693;
				break;
			case 1:
				// The DC contactor has reported that it has unexpectedly openedor has not
				// operated properly during startup.
				errorCode = 694;
				break;
			case 2:
				// The ambient temperature has exceeded the upper limit. The pump and fans will
				// continue to run until the temperature falls below the limit.
				errorCode = 695;
				break;
			case 3:
				// The cabinet temperature has exceeded the upper limit. The pump and fans will
				// continue to run until the temperature falls below the limit.
				errorCode = 696;
				break;
			case 4:
				// The PV array tie contactor has reported that it has unexpectedly opened or
				// has not operated properly during startup.
				errorCode = 697;
				break;
			case 9:
				// A cable or connector has become loose inside the unit.
				errorCode = 698;
				break;
			case 10:
				// A cable or connector has become loose inside the unit.
				errorCode = 699;
				break;
			case 11:
				// A cable or connector has become loose inside the unit.
				errorCode = 700;
				break;
			case 12:
				// A cable or connector has become loose inside the unit.
				errorCode = 701;
				break;
			case 13:
				// A cable or connector has become loose inside the unit.
				errorCode = 702;
				break;
			case 14:
				// A cable or connector has become loose inside the unit.
				errorCode = 703;
				break;
			case 15:
				// A cable or connector has become loose inside the unit.
				errorCode = 104;
				break;
			case 16:
				// A cable or connector has become loose inside the unit.
				errorCode = 705;
				break;
			case 17:
				// The internal DC bus voltage did not reach an acceptable level quickly enough.
				errorCode = 706;
				break;
			case 18:
				// Someone has pressed the Stop button or the external interlock is preventing
				// the unit from operating.
				errorCode = 707;
				break;
			case 19:
				// A cloud edge disturbed the PV voltage to the unit during turn-on before the
				// unit's DCcontactor could close, causing too great a difference between the
				// bus voltage and the PV voltage.
				errorCode = 708;
				break;
			case 20:
				// Fan is not running fast enough.
				errorCode = 709;
				break;
			case 21:
				// Fan is not running fast enough.
				errorCode = 710;
				break;
			case 22:
				// Fan is not running fast enough.
				errorCode = 711;
				break;
			case 23:
				// Fan is not running fast enough.
				errorCode = 712;
				break;
			case 24:
				// Fan is not running fast enough.
				errorCode = 713;
				break;
			case 25:
				// The positive and negative bipolar PV array voltages are out of balance (not
				// equal), possibly due to a ground fault or insulation failure in the array. On
				// units with the optional charge Equalizer accessory, this fault may occur at
				// night or during the wake-up transition. Array balance faults produced under
				// these conditions are considered normal behavior.
				errorCode = 714;
				break;
			case 26:
				// The available PV array power increased too fast for the inverter to back off
				// the voltage and keep the power from exceeding the trip limit.
				errorCode = 715;
				break;
			case 27:
				// A failure has occurred in a ground fault detection component in the unit.
				errorCode = 716;
				break;
			case 28:
				// Fan is not running fast enough.
				errorCode = 717;
				break;
			case 29:
				// Fan is not running fast enough.
				errorCode = 718;
				break;
			}
			break;
		case 3:
			switch (bitLevel) {
			case 0:
				// The cable for the ground fault detection and interruption(GFDI) device is
				// loose or removed.
				errorCode = 719;
				break;
			case 1:
				// The soft-start contactor is likely to be welded closed and unable to open.
				// Caution is required when dealing with thissituation.
				errorCode = 720;
				break;
			case 2:
				// The PV Tie contactor is likely to be welded closed and unable to open.
				// Caution is required when dealing with this situation.
				errorCode = 721;
				break;
			case 3:
				// The DC contactor is likely to be welded closed and unable to open. Caution is
				// required when dealing with this situation.
				errorCode = 722;
				break;
			case 4:
				// The AC contactor is likely to be welded closed and unable to open. Caution is
				// required when dealing with this situation.
				errorCode = 723;
				break;
			case 8:
				// Fan is not running fast enough.
				errorCode = 724;
				break;
			case 9:
				// phase-A low
				errorCode = 725;
				break;
			case 10:
				// phase-B low
				errorCode = 726;
				break;
			case 11:
				// phase-C low
				errorCode = 727;
				break;
			case 12:
				// phase-A high
				errorCode = 728;
				break;
			case 13:
				// phase-B high
				errorCode = 729;
				break;
			case 14:
				// phase-C high
				errorCode = 730;
				break;
			}
			break;
		}

		return errorCode;
	}
	
	
	
	/**
	 * get wainning limit codes from table model_ivt_solaron_ext
	 * 
	 * @return
	 */
	public static int GetLimitCodeModelIVTSolaronEXT(int bitLevel) {
		int errorCode = 0;
		switch (bitLevel) {
		case 8:
			// The unit is reducing power because the output alternating current limit has been exceeded.
			errorCode = 731;
			break;
		case 16:
			// The unit is reducing power because the PV array direct current limit has been exceeded.
			errorCode = 732;
			break;
		case 17:
			// The unit is reducing output power because the AC power limit has been exceeded.
			errorCode = 733;
			break;
		case 18:
			// The MPPT is limited due to excessive DC voltage.
			errorCode = 734;
			break;
		case 19:
			// The MPPT is limited due to insufficient DC voltage.
			errorCode = 735;
			break;
		case 20:
			// The unit is consuming reactive power to limit current harmonics.
			errorCode = 736;
			break;
		case 21:
			// The unit is reducing power due to excessive coolant temperature.
			errorCode = 737;
			break;
		case 22:
			// The unit is inhibiting PWM switching due to excessive AC current.
			errorCode = 738;
			break;
		case 23:
			// The unit is inhibiting PWM switching due to excessive bus capacitor voltage slew rate.
			errorCode = 739;
			break;
		case 24:
			// The unit is inhibiting PWM switching due to excessive power.
			errorCode = 740;
			break;
		}

		return errorCode;
	}
	
	
	/**
	 * get status codes from table model_ivt_solaron_ext
	 * 
	 * @return
	 */
	public static int GetStatusCodeModelIVTSolaronEXT(int bitLevel) {
		int errorCode = 0;
		switch (bitLevel) {
		case 0:
			// Bit set if unit is on
			errorCode = 741;
			break;
		case 1:
			// Bit set if the unit has one or more active faults. Important Bit 1 is normally set during sleep status and should be treated as information only during sleep status.
			errorCode = 742;
			break;
		case 2:
			// Bit set if the unit operation has been affected by one or more operating limits. Important Limits are not seen as faults by the unit. The unit will continue to operate with one or more active limits and should not cause alerts to a SCADA control system.
			errorCode = 743;
			break;
		case 3:
			// Bit set for enabled for master control enabled
			errorCode = 744;
			break;
		case 4:
			// Bit set if the unit is in startup mode
			errorCode = 745;
			break;
		case 5:
			// Bit set if the unit has an active warning. Important Warnings are not seen as faults by the unit. The unit will continue to operate with one or more active warnings and should not cause alerts to a SCADA control system.
			errorCode = 746;
			break;
		case 6:
			// Bit set if the unit has been locked out
			errorCode = 747;
			break;
		case 8:
			// Bit set for tracking on (MPP active)
			errorCode = 748;
			break;
		case 9:
			// Bit set for sleep. Important Bit 1 (fault status) is normally set whenever bit 9 is set, and should be treated as information only when the sleep bit is set and not as an active fault.
			errorCode = 749;
			break;
		case 10:
			// Bit set for autostart on
			errorCode = 750;
			break;
		case 11:
			// Bit set if a surge protection device has failed
			errorCode = 751;
			break;
		}

		return errorCode;
	}
	
	
	/**
	 * get warning codes from table model_ivt_solaron_ext
	 * 
	 * @return
	 */
	public static int GetWarningsCodeModelIVTSolaronEXT(int bitLevel) {
		int errorCode = 0;
		switch (bitLevel) {
		case 1:
			// Fan not operating normally
			errorCode = 752;
			break;
		case 2:
			// Fan not operating normally
			errorCode = 753;
			break;
		case 3:
			// Fan not operating normally
			errorCode = 754;
			break;
		case 4:
			// Fan not operating normally
			errorCode = 755;
			break;
		case 5:
			// Fan not operating normally
			errorCode = 756;
			break;
		case 6:
			// Fan not operating normally
			errorCode = 757;
			break;
		case 7:
			// Fan not operating normally
			errorCode = 758;
			break;
		case 8:
			// Charge abatement option not operating correctly
			errorCode = 759;
			break;
		}

		return errorCode;
	}
	
	/**
	 * get error code from table model_satcon_powergate_225_inverter
	 * 
	 * @return
	 */
	public static int GetErrorCodeModelSatconPowergate225Inverter(int bitLevel, int faultLevel) {
		int errorCode = 0;
		switch (faultLevel) {
		case 1:
			switch (bitLevel) {
			case 0: errorCode = 820; break;
			case 1: errorCode = 821; break;
			case 2: errorCode = 822; break;
			case 3: errorCode = 823; break;
			case 4: errorCode = 824; break;
			case 5: errorCode = 825; break;
			case 6: errorCode = 826; break;
			case 7: errorCode = 827; break;
			case 8: errorCode = 828; break;
			case 9: errorCode = 829; break; 
			case 10: errorCode = 830; break;
			case 11: errorCode = 831; break; 
			case 12: errorCode = 832; break;
			case 13: errorCode = 833; break;
			case 14: errorCode = 834; break;
			case 15: errorCode = 835; break;
			}
			break;
			
		
		case 3: 
			switch (bitLevel) {
			case 0: errorCode = 836; break;
			case 1: errorCode = 837; break;
			case 2: errorCode = 838; break;
			case 3: errorCode = 839; break;
			case 4: errorCode = 840; break;
			case 5: errorCode = 841; break;
			case 6: errorCode = 842; break;
			case 7: errorCode = 843; break;
			case 8: errorCode = 844; break; 
			case 9: errorCode = 845; break;
			case 10: errorCode = 846; break;
			case 11: errorCode = 847; break;
			case 12: errorCode = 848; break;
			case 13: errorCode = 849; break;
			case 14: errorCode = 850; break;
			case 15: errorCode = 851; break;
			
			}
			break;
		case 4: 
			switch (bitLevel) {
			case 0: errorCode = 852; break;
			case 1: errorCode = 853; break;
			case 2: errorCode = 854; break;
			case 3: errorCode = 855; break;
			case 4: errorCode = 856; break;
			case 5: errorCode = 857; break;
			case 6: errorCode = 858; break;
			case 7: errorCode = 859; break;
			case 8: errorCode = 860; break;
			case 9: errorCode = 861; break;
			case 10: errorCode = 862; break;
			case 11: errorCode = 863; break;
			case 12: errorCode = 864; break;
			case 13: errorCode = 865; break; 
			case 14: errorCode = 866; break;
			case 15: errorCode = 867; break;
			}
			break;
		case 5: 
			switch (bitLevel) {
			case 0: errorCode = 868; break;
			case 1: errorCode = 869; break;
			case 2: errorCode = 870; break; 
			case 3: errorCode = 871; break;
			case 4: errorCode = 872; break;
			case 5: errorCode = 873; break;
			case 6: errorCode = 874; break;
			case 7: errorCode = 875; break;
			case 8: errorCode = 876; break;
			case 9: errorCode = 877; break;
			case 10: errorCode = 878; break; 
			case 11: errorCode = 879; break;
			case 12: errorCode = 880; break;
			case 13: errorCode = 881; break;
			case 14: errorCode = 882; break;
			case 15: errorCode = 883; break;
			}
			break;
		case 6: 
			switch (bitLevel) {
			case 0: errorCode = 884; break;
			case 1: errorCode = 885; break;
			case 2: errorCode = 886; break;
			case 3: errorCode = 887; break;
			case 4: errorCode = 888; break;
			case 5: errorCode = 889; break;
			case 6: errorCode = 890; break;
			case 7: errorCode = 891; break;
			case 8: errorCode = 892; break; 
			case 9: errorCode = 893; break; 
			case 10: errorCode = 894; break;
			case 11: errorCode = 895; break;
			case 12: errorCode = 896; break;
			case 13: errorCode = 897; break;
			case 14: errorCode = 898; break;
			case 15: errorCode = 899; break;
			}
			break;
		case 7: 
			switch (bitLevel) {
			case 0: errorCode = 900; break; 
			case 1: errorCode = 901; break; 
			case 2: errorCode = 902; break; 
			case 3: errorCode = 903; break;
			case 4: errorCode = 904; break;
			case 5: errorCode = 905; break;
			case 6: errorCode = 906; break;
			case 7: errorCode = 907; break;
			case 8: errorCode = 908; break;
			case 9: errorCode = 909; break;
			case 10: errorCode = 910; break;
			case 11: errorCode = 911; break;
			case 12: errorCode = 912; break;
			case 13: errorCode = 913; break;
			case 14: errorCode = 914; break;
			case 15: errorCode = 915; break;
			}
			break;
		
		
		}

		return errorCode;
	}
	
	
}
