/**
 * 
 */
package com.opentext.bn.content.parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.opentext.bn.content.parser.entity.AssetKeys;
import com.opentext.bn.content.parser.entity.ContentKeys;
import com.opentext.bn.content.parser.entity.DocumentVO;
import com.opentext.bn.content.parser.entity.EnvelopeVO;

public class XIFConstants {
    
    public static final String PATH_SEPARATOR = "/";
    public static final String SERVICE_CODE_TGTS = "TGTS";
    public static enum BuidType {buid, rootBuid};

    public static final String SENDER_ADDRESS_KEY = "senderAddressKey";
    public static final String SENDER_QUALIFIER_KEY = "senderQualifierKey";
    public static final String RECEIVER_ADDRESS_KEY = "receiverAddressKey";
    public static final String RECEIVER_QUALIFIER_KEY = "receiverQualifierKey";
    public static final String SENDER_BUID_KEY = "senderBuid";
    public static final String SENDER_ROOT_BUID_KEY = "senderRootBuid";
    public static final String RECEIVER_BUID_KEY = "receiverBuid";
    public static final String RECEIVER_ROOT_BUID_KEY = "receiverRootBuid";
    public static final String STATUS_CODE_KEY = "statusCode";
    public static final String CORRELATION_ID_KEY = "correlationId";
    public static final String PROCESS_ID_KEY = "processId";
    public static final String ACTIVITY_ID_KEY = "activityId";
    public static final String STEP_ID_KEY = "stepId";
    public static final String SOLUTION_ID_KEY = "solutionId";
    public static final String SNRF_KEY = "snrf";
    public static final String XIF_URI_KEY = "xifUri";
    public static final String SENDER_BU_NAME = "senderbuName";
    public static final String RECEIVER_BU_NAME = "receiverbuName";
    public static final String COMPANY_NAME = "companyName";
    public static final String INPUT_FILE_SIZE = "input_file_size";
    public static final String INPUT_UDF_COUNT = "input_udf_count";
    public static final String INPUT_UDF_DOC_COUNT = "input_udf_doc_count";
    public static final String INPUT_EDI_COUNT= "input_edi_count";
    public static final String INPUT_DOC_COUNT = "input_doc_count";
    public static final String INPUT_IC_COUNT = "input_ic_count";
    public static final String AI_SESSION_NO = "ai_session_no";
    public static final String AI_SESSION_STATUS = "ai_session_status";
    public static final String AI_NODE_NAME = "ai_node_name";
    
    
    public static final String OUTPUT_UDF_COUNT = "output_udf_count";
    public static final String OUTPUT_UDF_DOC_COUNT = "output_udf_doc_count";
    public static final String OUTPUT_EDI_COUNT= "output_edi_count";
    public static final String OUTPUT_DOC_COUNT = "output_doc_count";
    public static final String OUTPUT_IC_COUNT = "output_ic_count";
   
      
    
    
    public static final String XIF_ELEMENT_LABEL_FUNCTION_CODE = "Function Code";
    public static final String XIF_ELEMENT_LABEL_GROUP_CONTROL_NUMBER = "Group Control Number";
    public static final String XIF_ELEMENT_LABEL_GROUP_ACKNOWLEDGE_CODE = "Group Acknowledge Code";
    public static final String XIF_ELEMENT_LABEL_MESSAGE_TYPE = "Message Type";
    public static final String XIF_ELEMENT_LABEL_MESSAGE_CONTROL_NUMBER = "Message Control Number";
    public static final String XIF_ELEMENT_LABEL_MESSAGE_ACKNOWLEDGE_CODE = "Message Acknowledge Code";
    public static final String XIF_ELEMENT_LABEL_MESSAGES_RECEIVED = "Messages Received";
    public static final String XIF_ELEMENT_LABEL_MESSAGES_ACCEPTED = "Messages Accepted";
    public static final String XIF_ELEMENT_STANDARD = "/AIRecognitionTranslationStatus/Standard";
    public static final String XIF_ELEMENT_GROUP = "/AIRecognitionTranslationStatus/Standard/Group";
    
    public static enum XifAttributeSession {direction,codePage,serviceName,aprf,receiverID,senderID,statusCodeDesc,statusCode,disposition,traceUseful,translateMode,dateTimeEnd,dateTimeStart,introspectList,number};
    public static enum XifAttributeServer {cluster,version,OT_DIR,OT_QUEUEID};
    public static enum XifAttributeFile {uri,size,aprf,name,currentFileName,sender,receiver};
    public static enum XifAttributeStandard {type,direction,version,testIndicator,translatable,status,sequence,uuid};
    public static enum XifAttributeStandardDirection {Inbound,Outbound};
    public static enum XifAttributeGroup {status,sequence,uuid};
    public static enum XifAttributeMessage {standard,type,version,status,translateMode,sequence,uuid};
    public static enum XifAttributeLabel {key};
    public static enum XifAttributeLabelKey {primary,secondary};
    public static enum XifAttributeLabelFunctionCodeValue {PO};
    public static enum XifAttributeSet {heirarchy,hierarchy};
    public static enum XifAttributeStandardType {X12,EFT,ANA,VDA,XML,Application,PROP,PROPD,A2A};
    public static enum EnvelopeLevel {INTERCHANGE,FUNCTIONAL_GROUP,TRANSMISSION};
    public static final String FUNCTIONAL_ACKNOWLEDGEMENT_X12 = "997";
    public static final String FUNCTIONAL_ACKNOWLEDGEMENT_EDIFACT = "CONTRL";
    public static final String PURCHASE_ORDER = "850";
    public static final String LABEL_GROUP_CONTROL_NUMBER = "Group Control Number";
    public static final String LABEL_MESSAGE_CONTROL_NUMBER = "Message Control Number";
    public static enum StatusName {FA};
    public static enum StatusState {FA_RECEIVED,FA_EXPECTED,FA_DELINQUENT};
    public static enum StatusBusinessCode {A,R};
    public static enum StatusPolicyResult {OK,LATE};
    public static enum XifModelServer {name,lastModDate};
    
    public static final String XIF_ELEMENT_SESSION                      = "/AIRecognitionTranslationStatus/Session";
    public static final String XIF_ELEMENT_SERVER                      = "/AIRecognitionTranslationStatus/Server";
    
    public static final String XIF_ELEMENT_Model                      = "/AIRecognitionTranslationStatus/ModelsUsed/File";
    
   
    public static final String XIF_ELEMENT_INPUT                        = "/AIRecognitionTranslationStatus/Input";
    public static final String XIF_ELEMENT_INPUT_FILE                   = "/AIRecognitionTranslationStatus/Input/File";
    public static final String XIF_ELEMENT_INPUT_FILE_ERROR_NUMBER      = "/AIRecognitionTranslationStatus/Input/aiError/aiErrorNumber";
    public static final String XIF_ELEMENT_INPUT_FILE_ERROR_SUMMARY     = "/AIRecognitionTranslationStatus/Input/aiError/aiErrorDescriptionSummary";
    public static final String XIF_ELEMENT_INPUT_FILE_ERROR_DETAIL      = "/AIRecognitionTranslationStatus/Input/aiError/aiErrorDescriptionDetail";
    
    public static final String XIF_ELEMENT_INPUT_STANDARD               = "/AIRecognitionTranslationStatus/Input/Standard";
    public static final String XIF_ELEMENT_INPUT_STANDARD_POSSTART      = "/AIRecognitionTranslationStatus/Input/Standard/PosStart";
    public static final String XIF_ELEMENT_INPUT_STANDARD_POSEND        = "/AIRecognitionTranslationStatus/Input/Standard/PosEnd";
    public static final String XIF_ELEMENT_INPUT_STANDARD_SENDER_ID     = "/AIRecognitionTranslationStatus/Input/Standard/Sender/ID";
    public static final String XIF_ELEMENT_INPUT_STANDARD_SENDER_QUAL   = "/AIRecognitionTranslationStatus/Input/Standard/Sender/Qual";
    public static final String XIF_ELEMENT_INPUT_STANDARD_RECEIVER_ID   = "/AIRecognitionTranslationStatus/Input/Standard/Receiver/ID";
    public static final String XIF_ELEMENT_INPUT_STANDARD_RECEIVER_QUAL = "/AIRecognitionTranslationStatus/Input/Standard/Receiver/Qual";
    public static final String XIF_ELEMENT_INPUT_STANDARD_DATE          = "/AIRecognitionTranslationStatus/Input/Standard/Date";
    public static final String XIF_ELEMENT_INPUT_STANDARD_TIME          = "/AIRecognitionTranslationStatus/Input/Standard/Time";
    public static final String XIF_ELEMENT_INPUT_STANDARD_CONTROLNUMBER = "/AIRecognitionTranslationStatus/Input/Standard/ControlNumber";
    public static final String XIF_ELEMENT_INPUT_STANDARD_LABEL         = "/AIRecognitionTranslationStatus/Input/Standard/LabelValues/Set/Label";
    public static final String XIF_ELEMENT_INPUT_STANDARD_VALUE         = "/AIRecognitionTranslationStatus/Input/Standard/LabelValues/Set/Value";
    public static final String XIF_ELEMENT_INPUT_STANDARD_ERROR_NUMBER  = "/AIRecognitionTranslationStatus/Input/Standard/aiError/aiErrorNumber";
    public static final String XIF_ELEMENT_INPUT_STANDARD_ERROR_SUMMARY = "/AIRecognitionTranslationStatus/Input/Standard/aiError/aiErrorDescriptionSummary";
    public static final String XIF_ELEMENT_INPUT_STANDARD_ERROR_DETAIL  = "/AIRecognitionTranslationStatus/Input/Standard/aiError/aiErrorDescriptionDetail";

    public static final String XIF_ELEMENT_INPUT_GROUP                  = "/AIRecognitionTranslationStatus/Input/Standard/Group";
    public static final String XIF_ELEMENT_INPUT_GROUP_POSSTART         = "/AIRecognitionTranslationStatus/Input/Standard/Group/PosStart";
    public static final String XIF_ELEMENT_INPUT_GROUP_POSEND           = "/AIRecognitionTranslationStatus/Input/Standard/Group/PosEnd";
    public static final String XIF_ELEMENT_INPUT_GROUP_SENDER_ID        = "/AIRecognitionTranslationStatus/Input/Standard/Group/Sender/ID";
    public static final String XIF_ELEMENT_INPUT_GROUP_SENDER_QUAL      = "/AIRecognitionTranslationStatus/Input/Standard/Group/Sender/Qual";
    public static final String XIF_ELEMENT_INPUT_GROUP_RECEIVER_ID      = "/AIRecognitionTranslationStatus/Input/Standard/Group/Receiver/ID";
    public static final String XIF_ELEMENT_INPUT_GROUP_RECEIVER_QUAL    = "/AIRecognitionTranslationStatus/Input/Standard/Group/Receiver/Qual";
    public static final String XIF_ELEMENT_INPUT_GROUP_CONTROLNUMBER    = "/AIRecognitionTranslationStatus/Input/Standard/Group/ControlNumber";
    public static final String XIF_ELEMENT_INPUT_GROUP_LABEL            = "/AIRecognitionTranslationStatus/Input/Standard/Group/LabelValues/Set/Label";
    public static final String XIF_ELEMENT_INPUT_GROUP_VALUE            = "/AIRecognitionTranslationStatus/Input/Standard/Group/LabelValues/Set/Value";
    public static final String XIF_ELEMENT_INPUT_GROUP_ERROR_NUMBER     = "/AIRecognitionTranslationStatus/Input/Standard/Group/aiError/aiErrorNumber";
    public static final String XIF_ELEMENT_INPUT_GROUP_ERROR_SUMMARY    = "/AIRecognitionTranslationStatus/Input/Standard/Group/aiError/aiErrorDescriptionSummary";
    public static final String XIF_ELEMENT_INPUT_GROUP_ERROR_DETAIL     = "/AIRecognitionTranslationStatus/Input/Standard/Group/aiError/aiErrorDescriptionDetail";
    
    public static final String XIF_ELEMENT_INPUT_MESSAGE                = "/AIRecognitionTranslationStatus/Input/Standard/Group/Message";
    public static final String XIF_ELEMENT_INPUT_MESSAGE_POSSTART       = "/AIRecognitionTranslationStatus/Input/Standard/Group/Message/PosStart";
    public static final String XIF_ELEMENT_INPUT_MESSAGE_POSEND         = "/AIRecognitionTranslationStatus/Input/Standard/Group/Message/PosEnd";
    public static final String XIF_ELEMENT_INPUT_MESSAGE_SENDER_ID      = "/AIRecognitionTranslationStatus/Input/Standard/Group/Message/Sender/ID";
    public static final String XIF_ELEMENT_INPUT_MESSAGE_SENDER_QUAL    = "/AIRecognitionTranslationStatus/Input/Standard/Group/Message/Sender/Qual";
    public static final String XIF_ELEMENT_INPUT_MESSAGE_RECEIVER_ID    = "/AIRecognitionTranslationStatus/Input/Standard/Group/Message/Receiver/ID";
    public static final String XIF_ELEMENT_INPUT_MESSAGE_RECEIVER_QUAL  = "/AIRecognitionTranslationStatus/Input/Standard/Group/Message/Receiver/Qual";
    public static final String XIF_ELEMENT_INPUT_MESSAGE_CONTROLNUMBER  = "/AIRecognitionTranslationStatus/Input/Standard/Group/Message/ControlNumber";
    public static final String XIF_ELEMENT_INPUT_MESSAGE_SET            = "/AIRecognitionTranslationStatus/Input/Standard/Group/Message/LabelValues/Set";
    public static final String XIF_ELEMENT_INPUT_MESSAGE_LABEL          = "/AIRecognitionTranslationStatus/Input/Standard/Group/Message/LabelValues/Set/Label";
    public static final String XIF_ELEMENT_INPUT_MESSAGE_VALUE          = "/AIRecognitionTranslationStatus/Input/Standard/Group/Message/LabelValues/Set/Value";
    public static final String XIF_ELEMENT_INPUT_MESSAGE_ERROR_NUMBER   = "/AIRecognitionTranslationStatus/Input/Standard/Group/Message/aiError/aiErrorNumber";
    public static final String XIF_ELEMENT_INPUT_MESSAGE_ERROR_SUMMARY  = "/AIRecognitionTranslationStatus/Input/Standard/Group/Message/aiError/aiErrorDescriptionSummary";
    public static final String XIF_ELEMENT_INPUT_MESSAGE_ERROR_DETAIL   = "/AIRecognitionTranslationStatus/Input/Standard/Group/Message/aiError/aiErrorDescriptionDetail";
    
    public static final String XIF_ELEMENT_OUTPUT                                   = "/AIRecognitionTranslationStatus/Output";
    public static final String XIF_ELEMENT_OUTPUT_COMM_FILE                         = "/AIRecognitionTranslationStatus/Output/Communication/File";
	public static final String XIF_ELEMENT_OUTPUT_APP_FILE                          = "/AIRecognitionTranslationStatus/Output/Application/File";
	public static final String XIF_ELEMENT_OUTPUT_EXCEPTION_FILE                     = "/AIRecognitionTranslationStatus/Output/Exception/File";
	public static final String XIF_ELEMENT_OUTPUT_ERROR_FILE                          = "/AIRecognitionTranslationStatus/Output/TraceLog/File";
    
    public static final String XIF_ELEMENT_OUTPUT_COMM_FILE_SENDER_ID     = "/AIRecognitionTranslationStatus/Output/Communication/File/IDs/Sender/ID";
    public static final String XIF_ELEMENT_OUTPUT_COMM_FILE_SENDER_QUAL   = "/AIRecognitionTranslationStatus/Output/Communication/File/IDs/Sender/Qual";
    public static final String XIF_ELEMENT_OUTPUT_COMM_FILE_RECEIVER_ID   = "/AIRecognitionTranslationStatus/Output/Communication/File/IDs/Receiver/ID";
    public static final String XIF_ELEMENT_OUTPUT_COMM_FILE_RECEIVER_QUAL = "/AIRecognitionTranslationStatus/Output/Communication/File/IDs/Receiver/Qual";
    public static final String XIF_ELEMENT_OUTPUT_COMM_FILE_IDS_MessageType = "/AIRecognitionTranslationStatus/Output/Communication/File/IDs/MessageType";
    
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT                        = "/AIRecognitionTranslationStatus/Output/Communication/Input";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_FILE                   = "/AIRecognitionTranslationStatus/Output/Communication/Input/File";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_FILE_ERROR_NUMBER      = "/AIRecognitionTranslationStatus/Output/Communication/Input/aiError/aiErrorNumber";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_FILE_ERROR_SUMMARY     = "/AIRecognitionTranslationStatus/Output/Communication/Input/aiError/aiErrorDescriptionSummary";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_FILE_ERROR_DETAIL      = "/AIRecognitionTranslationStatus/Output/Communication/Input/aiError/aiErrorDescriptionDetail";
    
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD               = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_POSSTART      = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/PosStart";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_POSEND        = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/PosEnd";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_SENDER_ID     = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Sender/ID";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_SENDER_QUAL   = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Sender/Qual";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_RECEIVER_ID   = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Receiver/ID";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_RECEIVER_QUAL = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Receiver/Qual";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_DATE          = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Date";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_TIME          = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Time";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_CONTROLNUMBER = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/ControlNumber";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_LABEL         = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/LabelValues/Set/Label";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_VALUE         = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/LabelValues/Set/Value";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_ERROR_NUMBER  = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/aiError/aiErrorNumber";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_ERROR_SUMMARY = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/aiError/aiErrorDescriptionSummary";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_ERROR_DETAIL  = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/aiError/aiErrorDescriptionDetail";

    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP                  = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP_POSSTART         = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/PosStart";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP_POSEND           = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/PosEnd";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP_SENDER_ID        = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/Sender/ID";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP_SENDER_QUAL      = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/Sender/Qual";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP_RECEIVER_ID      = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/Receiver/ID";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP_RECEIVER_QUAL    = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/Receiver/Qual";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP_CONTROLNUMBER    = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/ControlNumber";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP_LABEL            = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/LabelValues/Set/Label";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP_VALUE            = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/LabelValues/Set/Value";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP_ERROR_NUMBER     = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/aiError/aiErrorNumber";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP_ERROR_SUMMARY    = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/aiError/aiErrorDescriptionSummary";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP_ERROR_DETAIL     = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/aiError/aiErrorDescriptionDetail";
    
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE                = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/Message";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_POSSTART       = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/Message/PosStart";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_POSEND         = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/Message/PosEnd";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_SENDER_ID      = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/Message/Sender/ID";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_SENDER_QUAL    = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/Message/Sender/Qual";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_RECEIVER_ID    = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/Message/Receiver/ID";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_RECEIVER_QUAL  = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/Message/Receiver/Qual";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_CONTROLNUMBER  = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/Message/ControlNumber";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_SET            = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/Message/LabelValues/Set";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_LABEL          = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/Message/LabelValues/Set/Label";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_VALUE          = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/Message/LabelValues/Set/Value";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_ERROR_NUMBER   = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/Message/aiError/aiErrorNumber";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_ERROR_SUMMARY  = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/Message/aiError/aiErrorDescriptionSummary";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_ERROR_DETAIL   = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/Message/aiError/aiErrorDescriptionDetail";
    
    public static final String XIF_ELEMENT_INPUT_STANDARD_DIRECTION     = "/AIRecognitionTranslationStatus/Input/Standard/direction";
    public static final String XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_DIRECTION     = "/AIRecognitionTranslationStatus/Output/Communication/Input/Standard/direction";
    
    public static final StringBuilder xmlPathBuilder = null;
    public static final LinkedHashMap<String, String> sessionAttributes = null;
    public static final LinkedHashMap<String, EnvelopeVO> envelopes = null;
	public static final List<DocumentVO> documents = new ArrayList<DocumentVO>();
	public static final ContentKeys contentKeys = null;
	public static final AssetKeys keys = null;

}
