package com.ipurvey.gdstransformerservice.amadeus.soap;

import com.amadeus.xml.qdqlrq_11_1_1a.QueueList;
import com.amadeus.xml.qdqlrr_11_1_1a.QueueListReply;

public interface AmadeusSoapWebService {

     QueueListReply listPnr(final QueueList listQuery);
}
