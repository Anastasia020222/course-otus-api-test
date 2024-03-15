package citrus;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.junit.spring.JUnit4CitrusSpringSupport;
import org.junit.Test;

import static com.consol.citrus.ws.actions.SoapActionBuilder.soap;
import static common.Constants.*;

public class SoapApiTest extends JUnit4CitrusSpringSupport {

  //В тестах используется сервис http://www.dneonline.com/calculator.asmx
  @Test
  @CitrusTest
  public void getTestActionsAdd() {
    RequestAndResponse(ADD_REQUEST, ADD_SOAP_ACTIONS, ADD_RESPONSE);
  }

  @Test
  @CitrusTest
  public void getTestActionsSubtract() {
    RequestAndResponse(SUBTRACT_REQUEST, SUBTRACT_SOAP_ACTIONS, SUBTRACT_RESPONSE);
  }

  @Test
  @CitrusTest
  public void getTestActionsMultiply() {
    RequestAndResponse(MULTIPLY_REQUEST, MULTIPLY_SOAP_ACTIONS, MULTIPLY_RESPONSE);
  }

  private void RequestAndResponse(String request, String actions, String response) {
    run(soap()
        .client(SOAP_CLIENT)              //soup client
        .send()                           //actions на отправку данных
        .message()
        .soapAction(actions)
        .body(request)
    );

    run(soap()
        .client(SOAP_CLIENT)
        .receive()                        //actions на получение данных
        .message()
        .body(response));
  }
}
