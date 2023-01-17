package com.neptunesoftware.nipintegrator.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig {

//    @Bean
//    public Jaxb2Marshaller jaxb2Marshaller(){
//        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
//        jaxb2Marshaller.setContextPath("com.neptunesoftware.nipintegrator.NIP.wsdl");
//        return jaxb2Marshaller;
//    }

//    @Bean
//    public NameEnquiryClient nibbsClient(Jaxb2Marshaller jaxb2Marshaller){
//        NameEnquiryClient nameEnquiryClient = new NameEnquiryClient();
//        nameEnquiryClient.setDefaultUri("http://localhost:9001/ws/");
//        nameEnquiryClient.setMarshaller(jaxb2Marshaller);
//        nameEnquiryClient.setUnmarshaller(jaxb2Marshaller);
//        return nameEnquiryClient;
//    }

    //SERVLET CONFIGURATION
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    //NAME_ENQUIRY CONFIGURATIONS
    @Bean(name = "nameenquirysingleitem")
    public DefaultWsdl11Definition nameEnquirysingleitemWsdl11Definition(XsdSchema nameenquirysingleitemSchema){
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("nameenquirysingleitem");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://www.neptunesoftware.com/nipintegrator/inward/client_api/nameenquirysingleitem");
        definition.setSchema(nameenquirysingleitemSchema);
        return definition;
    }

    @Bean(name = "nameenquirysingleitemSchema")
    public XsdSchema nameEnquiryApiSchema(){
        return new SimpleXsdSchema(new ClassPathResource("nameenquirysingleitem.xsd"));
    }

    //BALANCE_ENQUIRY CONFIGURATIONS
    @Bean(name = "balanceEnquiryApi")
    public DefaultWsdl11Definition balanceEnquiryApiWsdl11Definition(XsdSchema balanceEnquiryApiSchema){
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("BalanceEnquiry");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://www.neptunesoftware.com/nipintegrator/inward/client_api/balanceEnquiryApi");
        definition.setSchema(balanceEnquiryApiSchema);
        return definition;
    }

    @Bean(name = "balanceEnquiryApiSchema")
    public XsdSchema balanceEnquiryApiSchema(){
        return new SimpleXsdSchema(new ClassPathResource("balanceEnquiryApi.xsd"));
    }

    //ACCOUNT_BLOCK CONFIGURATIONS
    @Bean(name = "accountBlockApi")
    public DefaultWsdl11Definition accountBlockApiWsdl11Definition(XsdSchema accountBlockApiSchema){
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("AccountBlock");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://www.neptunesoftware.com/nipintegrator/inward/client_api/accountBlockApi");
        definition.setSchema(accountBlockApiSchema);
        return definition;
    }

    @Bean(name = "accountBlockApiSchema")
    public XsdSchema accountBlockApiSchema(){
        return new SimpleXsdSchema(new ClassPathResource("accountBlockApi.xsd"));
    }

    //ACCOUNT_UNBLOCK CONFIGURATIONS
    @Bean(name = "accountUnblockApi")
    public DefaultWsdl11Definition accountUnblockApiWsdl11Definition(XsdSchema accountUnblockApiSchema){
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("AccountUnblock");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://www.neptunesoftware.com/nipintegrator/inward/client_api/accountUnblockApi");
        definition.setSchema(accountUnblockApiSchema);
        return definition;
    }

    @Bean(name = "accountUnblockApiSchema")
    public XsdSchema accountUnblockApiSchema(){
        return new SimpleXsdSchema(new ClassPathResource("accountUnblockApi.xsd"));
    }

    //AMOUNT_UNBLOCK CONFIGURATIONS
    @Bean(name = "amountUnblockApi")
    public DefaultWsdl11Definition amountUnblockApiWsdl11Definition(XsdSchema amountUnblockApiSchema){
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("AmountUnblock");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://www.neptunesoftware.com/nipintegrator/inward/client_api/amountUnblockApi");
        definition.setSchema(amountUnblockApiSchema);
        return definition;
    }

    @Bean(name = "amountUnblockApiSchema")
    public XsdSchema amountUnblockApiSchema(){
        return new SimpleXsdSchema(new ClassPathResource("amountUnblockApi.xsd"));
    }

//MANDATE_ADVICE CONFIGURATIONS

    @Bean(name = "mandateAdviceApi")
    public DefaultWsdl11Definition mandateAdviceApiWsdl11Definition(XsdSchema mandateAdviceApiSchema){
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("MandateAdvice");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://www.neptunesoftware.com/nipintegrator/inward/client_api/mandateAdviceApi");
        definition.setSchema(mandateAdviceApiSchema);
        return definition;
    }

    @Bean(name = "mandateAdviceApiSchema")
    public XsdSchema mandateAdviceApiSchema(){
        return new SimpleXsdSchema(new ClassPathResource("mandateAdviceApi.xsd"));
    }

//AMOUNT_BLOCK CONFIGURATIONS

    @Bean(name = "amountBlockApi")
    public DefaultWsdl11Definition amountBlockApiWsdl11Definition(XsdSchema amountBlockApiSchema){
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("amountBlock");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://www.neptunesoftware.com/nipintegrator/inward/client_api/amountBlockApi");
        definition.setSchema(amountBlockApiSchema);
        return definition;
    }
    @Bean(name = "amountBlockApiSchema")
    public XsdSchema amountBlockApiWsdl11Definition(){
        return new SimpleXsdSchema(new ClassPathResource("amountBlockApi.xsd"));
    }
  
   //FINANCIAL INSTITUTION LIST  CONFIGURATIONS -- financialInstitutionList
    @Bean(name = "financialInstitutionListApi")
    public DefaultWsdl11Definition financialInstitutionListApiWsdl11Definition(XsdSchema financialInstitutionListApiSchema){
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("financialInstitutionList");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://www.neptunesoftware.com/nipintegrator/inward/client_api/financialInstitutionListApi");
        definition.setSchema(financialInstitutionListApiSchema);
        return definition;
    }

    @Bean(name = "financialInstitutionListApiSchema")
    public XsdSchema financialInstitutionListApiSchema(){
        return new SimpleXsdSchema(new ClassPathResource("financialInstitutionListApi.xsd"));
    }

    // FUND TRANSFER DIRECT CREDIT CONFIGURATION

    @Bean(name = "fundTransferDirectCreditApi")
    public DefaultWsdl11Definition fundTransferDirectCreditApiWsdl11Definition(XsdSchema fundTransferDirectCreditApiSchema){
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("fundTransferDirectCredit");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://www.neptunesoftware.com/nipintegrator/inward/client_api/fundTransferDirectCreditApi");
        definition.setSchema(fundTransferDirectCreditApiSchema);
        return definition;
    }

    @Bean(name = "fundTransferDirectCreditApiSchema")
    public XsdSchema fundTransferDirectCreditApiSchema(){
        return new SimpleXsdSchema(new ClassPathResource("fundTransferDirectCreditApi.xsd"));
    }

    // FUND TRANSFER DIRECT DEBIT CONFIGURATION

    @Bean(name = "fundTransferDirectDebitApi")
    public DefaultWsdl11Definition fundTransferDirectDebitApiWsdl11Definition(XsdSchema fundTransferDirectDebitApiSchema){
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("fundTransferDirectDebit");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://www.neptunesoftware.com/nipintegrator/inward/client_api/fundTransferDirectDebitApi");
        definition.setSchema(fundTransferDirectDebitApiSchema);
        return definition;
    }

    @Bean(name = "fundTransferDirectDebitApiSchema")
    public XsdSchema fundTransferDirectDebitApiSchema(){
        return new SimpleXsdSchema(new ClassPathResource("fundTransferDirectDebitApi.xsd"));
    }

 //FUND TRANSFER ADVICE DIRECT DEBIT CONFIGURATIONS
    @Bean(name = "fundTransferAdviceDirectDebitApi")
    public DefaultWsdl11Definition fundTransferAdviceDirectDebitApiWsdl11Definition(XsdSchema fundTransferAdviceDirectDebitApiSchema){
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("fundTransferAdviceDirectDebit");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://www.neptunesoftware.com/nipintegrator/inward/client_api/fundTransferAdviceDirectDebitApi");
        definition.setSchema(fundTransferAdviceDirectDebitApiSchema);
        return definition;
    }

    @Bean(name = "fundTransferAdviceDirectDebitApiSchema")
    public XsdSchema fundTransferAdviceDirectDebitApiSchema(){
        return new SimpleXsdSchema(new ClassPathResource("fundTransferAdviceDirectDebitApi.xsd"));
    }

    //FUND TRANSFER ADVICE DIRECT DEBIT CONFIGURATIONS
    @Bean(name = "fundTransferAdviceDirectCreditApi")
    public DefaultWsdl11Definition fundTransferAdviceDirectCreditApiWsdl11Definition(XsdSchema fundTransferAdviceDirectCreditApiSchema){
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("fundTransferAdviceDirectCredit");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://www.neptunesoftware.com/nipintegrator/inward/client_api/fundTransferAdviceDirectCreditApi");
        definition.setSchema(fundTransferAdviceDirectCreditApiSchema);
        return definition;
    }

    @Bean(name = "fundTransferAdviceDirectCreditApiSchema")
    public XsdSchema fundTransferAdviceDirectCreditApiSchema(){
        return new SimpleXsdSchema(new ClassPathResource("fundTransferAdviceDirectCreditApi.xsd"));
    }
}
