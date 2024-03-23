package com.ipurvey.gdstransformerservice.amadeus.soap;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.amadeus.xml.fmptbq_23_2_1a.*;
import com.amadeus.xml.qdqlrq_11_1_1a.*;
import com.amadeus.xml.qdqlrq_11_1_1a.OriginatorIdentificationDetailsTypeI;
import com.amadeus.xml.qdqlrq_11_1_1a.SelectionDetailsInformationTypeI;
import com.amadeus.xml.qdqlrq_11_1_1a.StructuredDateTimeType;
import com.amadeus.xml.qdqlrq_11_1_1a.StructuredPeriodInformationType;
import com.amadeus.xml.qdqlrr_11_1_1a.QueueListReply;

/**
 * Generates the query messages as Java objects.
 */
public class MessageFactory {

	public static FareMasterPricerTravelBoardSearch buildFlightRequest() {

		FareMasterPricerTravelBoardSearch fareMasterPricerTravelBoardSearch = new FareMasterPricerTravelBoardSearch();

		NumberOfUnitsType numberOfUnitsType = new NumberOfUnitsType();
		NumberOfUnitDetailsType detailsType = new NumberOfUnitDetailsType();

		List<NumberOfUnitDetailsType312621C> detailsType270113C = new ArrayList<NumberOfUnitDetailsType312621C>();

		NumberOfUnitDetailsType312621C unitDetailsType270113C = new NumberOfUnitDetailsType312621C();
		unitDetailsType270113C.setNumberOfUnits(BigInteger.valueOf(1));
		unitDetailsType270113C.setTypeOfUnit("PX");

		detailsType270113C.add(unitDetailsType270113C);

		numberOfUnitsType.getUnitNumberDetail().add(unitDetailsType270113C);

		fareMasterPricerTravelBoardSearch.setNumberOfUnit(numberOfUnitsType);

		// PAX details
		TravellerReferenceInformationType230874S travellerReferenceInformationType = new TravellerReferenceInformationType230874S();
		String ptcString = new String();
		ptcString = "ADT";
		TravellerDetailsType travellerDetailsType = new TravellerDetailsType();
		travellerDetailsType.setRef(BigInteger.valueOf(1));

		travellerReferenceInformationType.getPtc().add(ptcString);
		travellerReferenceInformationType.getTraveller().add(travellerDetailsType);
		fareMasterPricerTravelBoardSearch.getPaxReference().add(travellerReferenceInformationType);

		// Fare Options
		FareMasterPricerTravelBoardSearch.FareOptions fareOptions = new FareMasterPricerTravelBoardSearch.FareOptions();
		PricingTicketingDetailsType ticketingDetailsType = new PricingTicketingDetailsType();
		PricingTicketingInformationType pricingTicketingInformationType = new PricingTicketingInformationType();
		String priceType = new String();
		priceType = "RP";
		priceType = "RU";
		priceType = "TAC";

		List<String> priceTypeList = new ArrayList<String>();
		priceTypeList.add("RP");
		priceTypeList.add("RU");
		priceTypeList.add("TAC");
		pricingTicketingInformationType.getPriceType().addAll(priceTypeList);
		ticketingDetailsType.setPricingTicketing(pricingTicketingInformationType);
		fareOptions.setPricingTickInfo(ticketingDetailsType);
		fareMasterPricerTravelBoardSearch.setFareOptions(fareOptions);

		// Journey Details
		FareMasterPricerTravelBoardSearch.Itinerary itinerary = new FareMasterPricerTravelBoardSearch.Itinerary();

		OriginAndDestinationRequestType originAndDestinationRequestType = new OriginAndDestinationRequestType();
		originAndDestinationRequestType.setSegRef(BigInteger.valueOf(1));
		itinerary.setRequestedSegmentRef(originAndDestinationRequestType);

		// Departure Details
		DepartureLocationType departureLocationType = new DepartureLocationType();
		ArrivalLocationDetailsType120834C arrivalLocationDetailsType120834C = new ArrivalLocationDetailsType120834C();
		arrivalLocationDetailsType120834C.setLocationId("DEL");
		departureLocationType.setDeparturePoint(arrivalLocationDetailsType120834C);
		itinerary.setDepartureLocalization(departureLocationType);

		// Arrival Details
		ArrivalLocalizationType arrivalLocalizationType = new ArrivalLocalizationType();
		ArrivalLocationDetailsType arrivalLocationDetailsType = new ArrivalLocationDetailsType();
		arrivalLocationDetailsType.setLocationId("BOM");
		arrivalLocalizationType.setArrivalPointDetails(arrivalLocationDetailsType);
		itinerary.setArrivalLocalization(arrivalLocalizationType);

		// Journet Time
		DateAndTimeInformationType181295S dateAndTimeInformationType181295S = new DateAndTimeInformationType181295S();
		DateAndTimeDetailsTypeI dateAndTimeDetailsTypeI = new DateAndTimeDetailsTypeI();
		SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
		dateAndTimeDetailsTypeI.setTimeQualifier("TA");
		dateAndTimeDetailsTypeI.setDate("240324");
		dateAndTimeDetailsTypeI.setTime("2300");
		dateAndTimeDetailsTypeI.setTimeWindow("2");
		dateAndTimeInformationType181295S.setFirstDateTimeDetail(dateAndTimeDetailsTypeI);

		DateAndTimeDetailsType254619C dateAndTimeDetailsType254619C = new DateAndTimeDetailsType254619C();
		dateAndTimeDetailsType254619C.setDayInterval(BigInteger.valueOf(1));
		dateAndTimeDetailsType254619C.setRangeQualifier("C");
		dateAndTimeInformationType181295S.setRangeOfDate(dateAndTimeDetailsType254619C);
		itinerary.setTimeDetails(dateAndTimeInformationType181295S);

		fareMasterPricerTravelBoardSearch.getItinerary().add(itinerary);

		return fareMasterPricerTravelBoardSearch;
	}

	public static QueueList buildPnrQueueListRequest() {
		QueueList queueList = new QueueList();
		AdditionalBusinessSourceInformationTypeI additionalBusinessSourceInformationTypeI = new AdditionalBusinessSourceInformationTypeI();
		OriginatorIdentificationDetailsTypeI originatorIdentificationDetailsTypeI = new OriginatorIdentificationDetailsTypeI();
		originatorIdentificationDetailsTypeI.setInHouseIdentification1("NCEPF3240");
		SourceTypeDetailsTypeI sourceTypeDetailsTypeI = new SourceTypeDetailsTypeI();
		sourceTypeDetailsTypeI.setSourceQualifier1("1");
		additionalBusinessSourceInformationTypeI.setSourceType(sourceTypeDetailsTypeI);
		additionalBusinessSourceInformationTypeI.setOriginatorDetails(originatorIdentificationDetailsTypeI);
		queueList.setTargetOffice(additionalBusinessSourceInformationTypeI);

		QueueInformationTypeI queueInformationTypeI = new QueueInformationTypeI();
		QueueInformationDetailsTypeI queueInformationDetailsTypeI = new QueueInformationDetailsTypeI();
		queueInformationDetailsTypeI.setNumber(BigInteger.valueOf(12));
		queueInformationTypeI.setQueueDetails(queueInformationDetailsTypeI);
		queueList.setQueueNumber(queueInformationTypeI);
		SubQueueInformationTypeI subQueueInformationTypeI = new SubQueueInformationTypeI();
		SubQueueInformationDetailsTypeI subQueueInformationDetailsTypeI = new SubQueueInformationDetailsTypeI();
		subQueueInformationDetailsTypeI.setItemNumber(String.valueOf(0));
		subQueueInformationDetailsTypeI.setIdentificationType("C");
		subQueueInformationTypeI.setSubQueueInfoDetails(subQueueInformationDetailsTypeI);
		queueList.setCategoryDetails(subQueueInformationTypeI);
		List<QueueList.SearchCriteria> searchCriteria = new ArrayList<QueueList.SearchCriteria>();

		SelectionDetailsInformationTypeI selectionDetailsInformationType = new SelectionDetailsInformationTypeI();
		SelectionDetailsTypeI selectionDetailsTypeI = new SelectionDetailsTypeI();
		selectionDetailsInformationType.setOption("TD");
		selectionDetailsTypeI.setSelectionDetails(selectionDetailsInformationType);
		QueueList.SearchCriteria searchCriteria1 = new QueueList.SearchCriteria();
		searchCriteria1.setSearchOption(selectionDetailsTypeI);
		StructuredPeriodInformationType structuredPeriodInformationType = new StructuredPeriodInformationType();
		StructuredDateTimeType structuredDateTimeType = new StructuredDateTimeType();
		structuredDateTimeType.setMonth(String.valueOf(4));
		structuredDateTimeType.setDay(String.valueOf(20));
		structuredDateTimeType.setYear(String.valueOf(2009));
		structuredPeriodInformationType.setBeginDateTime(structuredDateTimeType);
		StructuredDateTimeType structuredDateTimeType2 = new StructuredDateTimeType();
		structuredDateTimeType2.setMonth(String.valueOf(4));
		structuredDateTimeType2.setDay(String.valueOf(20));
		structuredDateTimeType2.setYear(String.valueOf(2009));
		structuredPeriodInformationType.setEndDateTime(structuredDateTimeType2);
		searchCriteria1.getDates().add(structuredPeriodInformationType);


		SelectionDetailsInformationTypeI selectionDetailsInformationType2 = new SelectionDetailsInformationTypeI();
		SelectionDetailsTypeI selectionDetailsTypeI2 = new SelectionDetailsTypeI();
		selectionDetailsInformationType.setOption("DD");
		selectionDetailsTypeI2.setSelectionDetails(selectionDetailsInformationType2);
		QueueList.SearchCriteria searchCriteria2 = new QueueList.SearchCriteria();
		searchCriteria2.setSearchOption(selectionDetailsTypeI);
		StructuredPeriodInformationType structuredPeriodInformationType3 = new StructuredPeriodInformationType();
		StructuredDateTimeType structuredDateTimeType3 = new StructuredDateTimeType();
		structuredDateTimeType3.setMonth(String.valueOf(4));
		structuredDateTimeType3.setDay(String.valueOf(20));
		structuredDateTimeType3.setYear(String.valueOf(2009));
		structuredPeriodInformationType3.setBeginDateTime(structuredDateTimeType3);
		StructuredDateTimeType structuredDateTimeType4 = new StructuredDateTimeType();
		structuredDateTimeType4.setMonth(String.valueOf(4));
		structuredDateTimeType4.setDay(String.valueOf(20));
		structuredDateTimeType4.setYear(String.valueOf(2009));
		structuredPeriodInformationType.setEndDateTime(structuredDateTimeType3);
		searchCriteria2.getDates().add(structuredPeriodInformationType);
		searchCriteria.add(searchCriteria1);
		searchCriteria.add(searchCriteria2);
		return queueList;

	}
//	public static AirSellFromRecommendation airSellFromRecommendation() {
//
//		AirSellFromRecommendation airSellFromRecommendation = new AirSellFromRecommendation();
//
//		MessageActionDetailsTypeI messageActionDetailsTypeI = new MessageActionDetailsTypeI();
//		MessageFunctionBusinessDetailsTypeI messageFunctionBusinessDetailsTypeI = new MessageFunctionBusinessDetailsTypeI();
//		messageFunctionBusinessDetailsTypeI.setMessageFunction("183");
//		String additionalMessageFunction = new String();
//		additionalMessageFunction = "M1";
//		messageFunctionBusinessDetailsTypeI.getAdditionalMessageFunction().add(additionalMessageFunction);
//		messageActionDetailsTypeI.setMessageFunctionDetails(messageFunctionBusinessDetailsTypeI);
//		airSellFromRecommendation.setMessageActionDetails(messageActionDetailsTypeI);
//
//		// ItineryDetails
//		ItineraryDetails itineraryDetails = new ItineraryDetails();
//		OriginAndDestinationDetailsTypeI originAndDestinationDetailsTypeI = new OriginAndDestinationDetailsTypeI();
//		originAndDestinationDetailsTypeI.setDestination("BOM");
//		originAndDestinationDetailsTypeI.setOrigin("DEL");
//		itineraryDetails.setOriginDestinationDetails(originAndDestinationDetailsTypeI);
//
//		MessageActionDetailsTypeI actionDetailsTypeI = new MessageActionDetailsTypeI();
//		MessageFunctionBusinessDetailsTypeI businessDetailsTypeI = new MessageFunctionBusinessDetailsTypeI();
//		businessDetailsTypeI.setMessageFunction("183");
//		actionDetailsTypeI.setMessageFunctionDetails(businessDetailsTypeI);
//		itineraryDetails.setMessage(actionDetailsTypeI);
//
//		SegmentInformation segmentInformation = new SegmentInformation();
//		TravelProductInformationTypeI travelProductInformationTypeI = new TravelProductInformationTypeI();
//		ProductDateTimeTypeI productDateTimeTypeI = new ProductDateTimeTypeI();
//		productDateTimeTypeI.setDepartureDate("170620");
//		travelProductInformationTypeI.setFlightDate(productDateTimeTypeI);
//
//		LocationTypeI locationTypeI = new LocationTypeI();
//		locationTypeI.setTrueLocationId("DEL");
//		travelProductInformationTypeI.setBoardPointDetails(locationTypeI);
//
//		LocationTypeI offPointDetails = new LocationTypeI();
//		offPointDetails.setTrueLocationId("BOM");
//		travelProductInformationTypeI.setOffpointDetails(offPointDetails);
//
//		CompanyIdentificationTypeI companyIdentificationTypeI = new CompanyIdentificationTypeI();
//		companyIdentificationTypeI.setMarketingCompany("UK");
//		travelProductInformationTypeI.setCompanyDetails(companyIdentificationTypeI);
//
//		ProductIdentificationDetailsTypeI productIdentificationDetailsTypeI = new ProductIdentificationDetailsTypeI();
//		productIdentificationDetailsTypeI.setFlightNumber("985");
//		productIdentificationDetailsTypeI.setBookingClass("S");
//		travelProductInformationTypeI.setFlightIdentification(productIdentificationDetailsTypeI);
//
//		segmentInformation.setTravelProductInformation(travelProductInformationTypeI);
//
//		RelatedProductInformationTypeI relatedProductInformationTypeI = new RelatedProductInformationTypeI();
//		relatedProductInformationTypeI.setQuantity(BigInteger.valueOf(1));
//		relatedProductInformationTypeI.getStatusCode().add(0, "NN");
//		segmentInformation.setRelatedproductInformation(relatedProductInformationTypeI);
//		itineraryDetails.getSegmentInformation().add(segmentInformation);
//
//		airSellFromRecommendation.getItineraryDetails().add(itineraryDetails);
//		return airSellFromRecommendation;
//
//	}
//
//
//	public static PNRAddMultiElements pnrAddMultiElementsWithOptionCode0() {
//
//		PNRAddMultiElements addMultiElements = new PNRAddMultiElements();
//		OptionalPNRActionsType optionalPNRActionsType = new OptionalPNRActionsType();
//		optionalPNRActionsType.getOptionCode().add(BigInteger.valueOf(1));
//		addMultiElements.setPnrActions(optionalPNRActionsType);
//
//
//		List<TravellerInfo> travellerInfos = new ArrayList<TravellerInfo>();
//		TravellerInfo travellerInfo = new TravellerInfo();
//		ElementManagementSegmentType elementManagementSegmentType = new ElementManagementSegmentType();
//		ReferencingDetailsType referencingDetailsType = new ReferencingDetailsType();
//		referencingDetailsType.setNumber("2");
//		referencingDetailsType.setQualifier("PR");
//		elementManagementSegmentType.setReference(referencingDetailsType);
//		elementManagementSegmentType.setSegmentName("NM");
//
//		List<PassengerData> passengerDatas = new ArrayList<PassengerData>();
//		PassengerData passengerData = new PassengerData();
//		TravellerInformationTypeI travellerInformationTypeI = new TravellerInformationTypeI();
//		TravellerSurnameInformationTypeI travellerSurnameInformationTypeI = new TravellerSurnameInformationTypeI();
//		travellerSurnameInformationTypeI.setQuantity(BigInteger.valueOf(1));
//		travellerSurnameInformationTypeI.setSurname("Yadav");
//		travellerInformationTypeI.setTraveller(travellerSurnameInformationTypeI);
//
//		List<TravellerDetailsTypeI> travellerDetailsTypeIs = new ArrayList<TravellerDetailsTypeI>();
//		TravellerDetailsTypeI travellerDetailsTypeI = new TravellerDetailsTypeI();
//		travellerDetailsTypeI.setFirstName("Gaurav");
//		travellerDetailsTypeI.setType("adt");
//		return addMultiElements;
//
//
//	}
}
