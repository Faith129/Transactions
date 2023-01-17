package com.neptunesoftware.nipintegrator.NIP.constants;

public class ResponseMapper {
	
	public String nipResponse(String sybaseResponse) 
	{
		String nipResponse = "96";
		
		switch(sybaseResponse)
		{
		case "00" :	nipResponse =  "00";
		break;
		case "1" :	nipResponse =  "01";
		break;
		case "3" :	nipResponse =  "03";
		break;
		case "5" :	nipResponse =  "05";
		break;
		case "-11701" :	nipResponse =  "06";
		break;
		case "7" :	nipResponse =  "07";
		break;
		case "8" :	nipResponse =  "08";
		break;
		case "9" :	nipResponse =  "09";
		break;
		case "12" :	nipResponse =  "12";
		break;
		case "13" :	nipResponse =  "13";
		break;
		case "14" :	nipResponse =  "14";
		break;
		case "15" :	nipResponse =  "15";
		break;
		case "16" :	nipResponse =  "16";
		break;
		case "17" :	nipResponse =  "17";
		break;
		case "18" :	nipResponse =  "18";
		break;
		case "21" :	nipResponse =  "21";
		break;
		case "25" :	nipResponse =  "25";
		break;
		case "26" :	nipResponse =  "26";
		break;
		case "30" :	nipResponse =  "30";
		break;
		case "34" :	nipResponse =  "34";
		break;
		case "35" :	nipResponse =  "35";
		break;
		case "51" :	nipResponse =  "51";
		break;
		case "57" :	nipResponse =  "57";
		break;
		case "58" :	nipResponse =  "58";
		break;
		case "61" :	nipResponse =  "61";
		break;
		case "63" :	nipResponse =  "63";
		break;
		case "65" :	nipResponse =  "65";
		break;
		case "68" :	nipResponse =  "68";
		break;
		case "69" :	nipResponse =  "69";
		break;
		case "70" :	nipResponse =  "70";
		break;
		case "71" :	nipResponse =  "71";
		break;
		case "91" :	nipResponse =  "91";
		break;
		case "92" :	nipResponse =  "92";
		break;
		case "94" :	nipResponse =  "94";
		break;
		case "96" :	nipResponse =  "96";
		break;
		case "97" :	nipResponse =  "97";
		break;
				
		}
		
		return nipResponse;	
	}

}
