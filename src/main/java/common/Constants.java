package common;

public class Constants {

  public final static String NAME_PET = "Keisha";
  public final static String CATEGORY_NAME = "cat";
  public final static String STATUS_SOLD = "sold";
  public final static String STATUS_PENDING = "pending";
  public final static long ID = 768857L;
  public final static String SOAP_CLIENT = "soapClient";

  public final static String ADD_REQUEST = "<tem:Add xmlns:tem=\"http://tempuri.org/\">\n"
      + "         <tem:intA>5</tem:intA>\n"
      + "         <tem:intB>7</tem:intB>\n"
      + "      </tem:Add>\n";

  public final static String ADD_RESPONSE = "<AddResponse xmlns=\"http://tempuri.org/\">\n"
      + "         <AddResult>12</AddResult>\n"
      + "      </AddResponse>\n";

  public final static String SUBTRACT_REQUEST = "<tem:Subtract xmlns:tem=\"http://tempuri.org/\">n\n"
      + "                     <tem:intA>98</tem:intA>\n"
      + "                    <tem:intB>15</tem:intB>\n"
      + "                  </tem:Subtract>\n";

  public final static String SUBTRACT_RESPONSE = "<SubtractResponse xmlns=\"http://tempuri.org/\">\n"
      + "         <SubtractResult>83</SubtractResult>\n"
      + "      </SubtractResponse>\n";
  public final static String SUBTRACT_SOAP_ACTIONS = "http://tempuri.org/Subtract";
  public final static String ADD_SOAP_ACTIONS = "http://tempuri.org/Add";

  public final static String MULTIPLY_SOAP_ACTIONS = "http://tempuri.org/Multiply";

  public final static String MULTIPLY_REQUEST = "<tem:Multiply xmlns:tem=\"http://tempuri.org/\">\n"
      + "         <tem:intA>9</tem:intA>\n"
      + "         <tem:intB>12</tem:intB>\n"
      + "      </tem:Multiply>\n";
  public final static String MULTIPLY_RESPONSE = "<MultiplyResponse xmlns=\"http://tempuri.org/\">\n"
      + "         <MultiplyResult>108</MultiplyResult>\n"
      + "      </MultiplyResponse>\n";

}
