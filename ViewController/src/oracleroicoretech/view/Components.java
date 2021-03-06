package oracleroicoretech.view;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import java.sql.SQLException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.adf.model.BindingContext;
import oracle.adf.model.OperationBinding;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.nav.RichTrain;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.binding.BindingContainer;
import oracle.jbo.domain.Number;
import oracle.jbo.ApplicationModule;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.MethodExpression;

import javax.el.ValueExpression;

import javax.faces.application.Application;

import oracle.adf.model.binding.DCBindingContainer;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.AttributeList;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import oracle.jbo.server.AttributeListImpl;

import oracleroicoretech.utils.ADFUtils;


public class Components {
    private RichOutputText ot21; // HighPerformance total amount in partitioning.
    private RichOutputText ot22; // Modular total amount in partitioning.
    private RichOutputText ot23; // ReadOnly total amount in partitioning.
    private RichOutputText factor; // Factor value.
    private RichOutputText priceHpGb; // Price per GB in HighPerformance.
    private RichOutputText priceMpGb; // Price per GB in MidRange.
    private RichOutputText priceRopGb; // Price per GB in LowCost.

    private RichOutputText highPerformanceGb; // HighPerformance GBs.
    private RichOutputText midRangeGb; // Modular GBs.
    private RichOutputText lowCostGb; // ReadOnly GBs.
    private RichInputText email;
    private RichTrain trainSequence;
    private RichSelectOneRadio radioBtn;
    private String radioBtnValue;

    private static final String CLOUD_URL = "http://cloud.oracle.com";

    public Components() {
    }

    public void sendEmail(ActionEvent actionEvent) {
 
        final String username = "";
        final String password = "";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.host", "stbeehive.oracle.com");
        props.put("mail.smtp.port", "993");
        //props.put("mail.smtp.host", "smtp.gmail.com");
        //props.put("mail.smtp.port", "465");
        
        Session session =
            Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
        System.out.println("** Password done!");
        
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("bocabyte17@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                                  InternetAddress.parse("juan.carlos.ruiz.rico@hotmail.com"));
            message.setSubject("Testing Subject");
            message.setText("Testing Text!");
            System.out.println("** Sending message...");
            Transport.send(message);
            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

/**
     * Action associated to the "Report" button.
     * @param actionEvent
     */
    public void generateReportAndCommit(ActionEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();

        // Generating file to download.
        String fileName = null;
        synchronized (this) {
            fileName =
                    new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) +
                    ".csv";
            downloadFileString(generateCsvString(), fileName);
            // --- generateCsvFile(fileName);
        }

        // --- downloadFile(fileName);
        // --- deleteCsvFile(fileName);

        // Commiting.
        System.out.println("Commiting...");
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding =
            (OperationBinding)bindings.getOperationBinding("Commit");
        operationBinding.execute();
        //context.getApplication().evaluateExpressionGet(context, "#{bindings.Commit.execute}", Object.class);
        System.out.println("...Commited!");

    }

    /**
     * This method creates the download stream to the user.
     * @param filePath
     */
    private void downloadFile(String filePath) {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            HttpServletRequest request =
                (HttpServletRequest)context.getExternalContext().getRequest();
            HttpServletResponse response =
                (HttpServletResponse)context.getExternalContext().getResponse();

            File file = new File(filePath);
            int length = 0;
            ServletOutputStream outStream = response.getOutputStream();

            response.setContentLength((int)file.length());
            String fileName = (new File(filePath)).getName();

            // sets HTTP header
            response.setHeader("Content-Disposition",
                               "attachment; filename=\"" + fileName + "\"");

            byte[] byteBuffer = new byte[4096];
            DataInputStream in =
                new DataInputStream(new FileInputStream(file));

            // reads the file's bytes and writes them to the response stream
            while ((in != null) && ((length = in.read(byteBuffer)) != -1)) {
                outStream.write(byteBuffer, 0, length);
            }

            in.close();
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void downloadFileString(StringBuffer file, String fileName) {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            HttpServletResponse response =
                (HttpServletResponse)context.getExternalContext().getResponse();
            ServletOutputStream outStream = response.getOutputStream();

            byte[] outputByte = file.toString().getBytes("UTF-8");

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition",
                               "attachment; filename=\"" + fileName + "\"");
            outStream.write(outputByte, 0, file.length());

            outStream.close();
            response.flushBuffer();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private StringBuffer generateCsvString() {

        StringBuffer csv = new StringBuffer();
        csv.append("STORAGE PLAN,TYPE OF STORAGE,GB,PRICE");
        csv.append(System.getProperty("line.separator"));
        BigDecimal gbsOnlyHP =
            new BigDecimal(highPerformanceGb.getValue().toString()).add(new BigDecimal(lowCostGb.getValue().toString()).add(new BigDecimal(midRangeGb.getValue().toString())));
        csv.append("ONLY HIGH PERFORMANCE STORAGE,," +
                   gbsOnlyHP.doubleValue() + "," +
                   gbsOnlyHP.multiply(new BigDecimal(priceHpGb.getValue().toString())));
        csv.append(System.getProperty("line.separator"));

        csv.append("PARTITIONING");
        csv.append(System.getProperty("line.separator"));
        csv.append(",");
        csv.append("High Performance Storage," +
                   new BigDecimal(highPerformanceGb.getValue().toString()).doubleValue() +
                   "," +
                   new BigDecimal(ot21.getValue().toString()).doubleValue());
        csv.append(System.getProperty("line.separator"));
        csv.append(",");
        csv.append("Mid Range Storage," +
                   new BigDecimal(midRangeGb.getValue().toString()).doubleValue() +
                   "," +
                   new BigDecimal(ot22.getValue().toString()).doubleValue());
        csv.append(System.getProperty("line.separator"));
        csv.append(",");
        csv.append("Low Cost Storage," +
                   new BigDecimal(lowCostGb.getValue().toString()).doubleValue() +
                   "," +
                   new BigDecimal(ot23.getValue().toString()).doubleValue());
        csv.append(System.getProperty("line.separator"));

        csv.append("PARTITIONING + ADVANCED COMPRESSION FACTOR " +
                   new BigDecimal(factor.getValue().toString()).doubleValue());
        csv.append(System.getProperty("line.separator"));
        csv.append(",");
        csv.append("High Performance Storage," +
                   new BigDecimal(highPerformanceGb.getValue().toString()).divide(new BigDecimal(factor.getValue().toString()),
                                                                    2,
                                                                    RoundingMode.HALF_UP).doubleValue() +
                   "," +
                   new BigDecimal(ot21.getValue().toString()).divide(new BigDecimal(factor.getValue().toString()),
                                                                    2,
                                                                    RoundingMode.HALF_UP).doubleValue());
        csv.append(System.getProperty("line.separator"));
        csv.append(",");
        csv.append("Mid Range Storage," +
                   new BigDecimal(midRangeGb.getValue().toString()).divide(new BigDecimal(factor.getValue().toString()),
                                                                    2,
                                                                    RoundingMode.HALF_UP).doubleValue() +
                   "," +
                   new BigDecimal(ot22.getValue().toString()).divide(new BigDecimal(factor.getValue().toString()),
                                                                    2,
                                                                    RoundingMode.HALF_UP).doubleValue());
        csv.append(System.getProperty("line.separator"));
        csv.append(",");
        csv.append("Low Cost Storage," +
                   new BigDecimal(lowCostGb.getValue().toString()).divide(new BigDecimal(factor.getValue().toString()),
                                                                    2,
                                                                    RoundingMode.HALF_UP).doubleValue() +
                   "," +
                   new BigDecimal(ot23.getValue().toString()).divide(new BigDecimal(factor.getValue().toString()),
                                                                    2,
                                                                    RoundingMode.HALF_UP).doubleValue());
        csv.append(System.getProperty("line.separator"));

        return csv;

    }

    /**
     * Generate a .csv file with the customer data.
     * @param fileName
     */
    private void generateCsvFile(String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.append("STORAGE PLAN,TYPE OF STORAGE,GB,PRICE");
            writer.append(System.getProperty("line.separator"));
            BigDecimal gbsOnlyHP =
                new BigDecimal(highPerformanceGb.getValue().toString()).add(new BigDecimal(lowCostGb.getValue().toString()).add(new BigDecimal(midRangeGb.getValue().toString())));
            writer.append("ONLY HIGH PERFORMANCE STORAGE,," +
                          gbsOnlyHP.doubleValue() + "," +
                          gbsOnlyHP.multiply(new BigDecimal(priceHpGb.getValue().toString())));
            writer.append(System.getProperty("line.separator"));

            writer.append("PARTITIONING");
            writer.append(System.getProperty("line.separator"));
            writer.append(",");
            writer.append("High Performance Storage," +
                          new BigDecimal(highPerformanceGb.getValue().toString()).doubleValue() +
                          "," +
                          new BigDecimal(ot21.getValue().toString()).doubleValue());
            writer.append(System.getProperty("line.separator"));
            writer.append(",");
            writer.append("Mid Range Storage," +
                          new BigDecimal(midRangeGb.getValue().toString()).doubleValue() +
                          "," +
                          new BigDecimal(ot22.getValue().toString()).doubleValue());
            writer.append(System.getProperty("line.separator"));
            writer.append(",");
            writer.append("Low Cost Storage," +
                          new BigDecimal(lowCostGb.getValue().toString()).doubleValue() +
                          "," +
                          new BigDecimal(ot23.getValue().toString()).doubleValue());
            writer.append(System.getProperty("line.separator"));

            writer.append("PARTITIONING + ADVANCED COMPRESSION FACTOR " +
                          new BigDecimal(factor.getValue().toString()).doubleValue());
            writer.append(System.getProperty("line.separator"));
            writer.append(",");
            writer.append("High Performance Storage," +
                          new BigDecimal(highPerformanceGb.getValue().toString()).divide(new BigDecimal(factor.getValue().toString()),
                                                                           2,
                                                                           RoundingMode.HALF_UP).doubleValue() +
                          "," +
                          new BigDecimal(ot21.getValue().toString()).divide(new BigDecimal(factor.getValue().toString()),
                                                                           2,
                                                                           RoundingMode.HALF_UP).doubleValue());
            writer.append(System.getProperty("line.separator"));
            writer.append(",");
            writer.append("Mid Range Storage," +
                          new BigDecimal(midRangeGb.getValue().toString()).divide(new BigDecimal(factor.getValue().toString()),
                                                                           2,
                                                                           RoundingMode.HALF_UP).doubleValue() +
                          "," +
                          new BigDecimal(ot22.getValue().toString()).divide(new BigDecimal(factor.getValue().toString()),
                                                                           2,
                                                                           RoundingMode.HALF_UP).doubleValue());
            writer.append(System.getProperty("line.separator"));
            writer.append(",");
            writer.append("Low Cost Storage," +
                          new BigDecimal(lowCostGb.getValue().toString()).divide(new BigDecimal(factor.getValue().toString()),
                                                                           2,
                                                                           RoundingMode.HALF_UP).doubleValue() +
                          "," +
                          new BigDecimal(ot23.getValue().toString()).divide(new BigDecimal(factor.getValue().toString()),
                                                                           2,
                                                                           RoundingMode.HALF_UP).doubleValue());
            writer.append(System.getProperty("line.separator"));

            // Closing data stream.
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteCsvFile(String fileName) {
        File file = new File(fileName);
        file.delete();
    }

    /**
     * This method return the list of data which the graph uses.
     * @return
     */
    public List getTabularData() {
        System.out.println("Retrieving data graph...");
        BigDecimal hpGbs = new BigDecimal(highPerformanceGb.getValue().toString());
        BigDecimal mGbs = new BigDecimal(midRangeGb.getValue().toString());
        BigDecimal ropGbs = new BigDecimal(lowCostGb.getValue().toString());
        BigDecimal hpEurPerGB = new BigDecimal(priceHpGb.getValue().toString());

        mGbs = mGbs.add(ropGbs);
        hpGbs = hpGbs.add(mGbs);
        hpGbs = hpGbs.multiply(hpEurPerGB);

        BigDecimal hpPartitioningAmount = new BigDecimal(ot21.getValue().toString());
        BigDecimal mPartitioningAmount = new BigDecimal(ot22.getValue().toString());
        BigDecimal ropPartitioningAmount = new BigDecimal(ot23.getValue().toString());

        BigDecimal factorValue = new BigDecimal(factor.getValue().toString());

        BigDecimal hpAdvCompression = new BigDecimal(ot21.getValue().toString());
        BigDecimal mAdvCompression = new BigDecimal(ot22.getValue().toString());
        BigDecimal ropAdvCompression = new BigDecimal(ot23.getValue().toString());

        BigDecimal hpOnlyAdvComp = new BigDecimal(hpGbs.doubleValue());
        hpOnlyAdvComp = hpOnlyAdvComp.divide(factorValue, 2, RoundingMode.HALF_UP);

        if (!factorValue.equals(new BigDecimal(0.0))) {
            hpAdvCompression = hpAdvCompression.divide(factorValue, 2, RoundingMode.HALF_UP);
            mAdvCompression = mAdvCompression.divide(factorValue, 2, RoundingMode.HALF_UP);
            ropAdvCompression = ropAdvCompression.divide(factorValue, 2, RoundingMode.HALF_UP);
        }

        System.out.println("...Retrieved!");
        System.out.println("Creating data graph...");
        ArrayList list = new ArrayList();
        String[] rowLabels = 
            new String[] { "High Performance Storage", "Mid Range Storage", "Low Cost Storage" };
        String[] colLabels = 
            new String[] { "Only High Performance", "Partitioning", "AdvCompression", "Partitioning + AdvCompression" };
        BigDecimal[][] values = new BigDecimal[][] {
                     /* High Performance Storage Row */ { hpGbs,
                                                          hpPartitioningAmount,
                                                          hpOnlyAdvComp,
                                                          hpAdvCompression },
                     /* Modular Storage Row */ { new BigDecimal(0.0),
                                                 mPartitioningAmount,
                                                 new BigDecimal(0.0),
                                                 mAdvCompression },
                     /* Read Only Storage Row */ { new BigDecimal(0.0),
                                                   ropPartitioningAmount,
                                                   new BigDecimal(0.0),
                                                   ropAdvCompression } 
                                                        };

        for (int c = 0; c < colLabels.length; c++) {
            for (int r = 0; r < rowLabels.length; r++) {
                list.add(new Object[] { colLabels[c], rowLabels[r], new Double(values[r][c].doubleValue()) });
            }
        }
        System.out.println("...Created!");
        return list;
    }
     
////////////////////////////////////////////////////////////////////////////////
    public Object invokeMethodExpression(String expr, Class returnType,
                                         Class[] argTypes, Object[] args) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ELContext elctx = fc.getELContext();
        ExpressionFactory elFactory =
            fc.getApplication().getExpressionFactory();
        MethodExpression methodExpr =
            elFactory.createMethodExpression(elctx, expr, returnType, argTypes);
        return methodExpr.invoke(elctx, args);
    }
    
    public String backAndCreate() throws SQLException {
        Object hpGbValue = highPerformanceGb.getValue();
        Object mpGbValue = midRangeGb.getValue();
        Object ropGbValue = lowCostGb.getValue();
        Object priceHpGbValue = priceHpGb.getValue();
        Object priceMpGbValue = priceMpGb.getValue();
        Object priceRopGbValue = priceRopGb.getValue();
        Object factorValue = factor.getValue();
        
        String expr = "#{bindings.CreateInsert.execute}";  
        System.out.println("* Created *");
        invokeMethodExpression(expr, null, new Class[]{}, null);
        
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        Application app = facesCtx.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesCtx.getELContext();
    
        ValueExpression veGbHp = elFactory.createValueExpression(elContext, "#{bindings.GbHp1.inputValue}", Object.class); 
        veGbHp.setValue(elContext, hpGbValue);
    
        ValueExpression veGbMp = elFactory.createValueExpression(elContext, "#{bindings.GbMp1.inputValue}", Object.class); 
        veGbMp.setValue(elContext, mpGbValue);
        
        ValueExpression veGbRop = elFactory.createValueExpression(elContext, "#{bindings.GbRop1.inputValue}", Object.class); 
        veGbRop.setValue(elContext, ropGbValue);
        
        ValueExpression vePriceGbHp = elFactory.createValueExpression(elContext, "#{bindings.PricePerGbGp1.inputValue}", Object.class); 
        vePriceGbHp.setValue(elContext, priceHpGbValue);
        
        ValueExpression vePriceGbMp = elFactory.createValueExpression(elContext, "#{bindings.PricePerGbMp1.inputValue}", Object.class); 
        vePriceGbMp.setValue(elContext, priceMpGbValue);
        
        ValueExpression vePriceGbRop = elFactory.createValueExpression(elContext, "#{bindings.PricePerGbRop1.inputValue}", Object.class); 
        vePriceGbRop.setValue(elContext, priceRopGbValue);
        
        ValueExpression veFactor = elFactory.createValueExpression(elContext, "#{bindings.Factor2.inputValue}", Object.class); 
        veFactor.setValue(elContext, factorValue);
        
        return (String)ADFUtils.invokeEL("#{controllerContext.currentViewPort.taskFlowContext.trainModel.getPrevious}");
    }
////////////////////////////////////////////////////////////////////////////////
    public void setOt21(RichOutputText ot21) {
        this.ot21 = ot21;
    }

    public RichOutputText getOt21() {
        return ot21;
    }

    public void setOt22(RichOutputText ot22) {
        this.ot22 = ot22;
    }

    public RichOutputText getOt22() {
        return ot22;
    }

    public void setOt23(RichOutputText ot23) {
        this.ot23 = ot23;
    }

    public RichOutputText getOt23() {
        return ot23;
    }

    public void setFactor(RichOutputText ot14) {
        this.factor = ot14;
    }

    public RichOutputText getFactor() {
        return factor;
    }

    public void setPriceHpGb(RichOutputText it6) {
        this.priceHpGb = it6;
    }

    public RichOutputText getPriceHpGb() {
        return priceHpGb;
    }

    public void setHighPerformanceGb(RichOutputText it3) {
        this.highPerformanceGb = it3;
    }

    public RichOutputText getHighPerformanceGb() {
        return highPerformanceGb;
    }

    public void setMidRangeGb(RichOutputText it8) {
        this.midRangeGb = it8;
    }

    public RichOutputText getMidRangeGb() {
        return midRangeGb;
    }

    public void setLowCostGb(RichOutputText it5) {
        this.lowCostGb = it5;
    }

    public RichOutputText getLowCostGb() {
        return lowCostGb;
    }

    public void setEmail(RichInputText email) {
        this.email = email;
    }

    public RichInputText getEmail() {
        return email;
    }

    public void setTrainSequence(RichTrain trainSequence) {
        this.trainSequence = trainSequence;
    }

    public RichTrain getTrainSequence() {
        return trainSequence;
    }

    public String doNothing() {
        System.out.println("doNothing() clicked");
        return "";
    }

    public void doNothing(ActionEvent actionEvent) {
        System.out.println("doNothing() clicked");
    }

    public void setRadioBtn(RichSelectOneRadio radioBtn) {
        this.radioBtn = radioBtn;
    }

    public RichSelectOneRadio getRadioBtn() {
        return radioBtn;
    }

    public void setRadioBtnValue(String radioBtnValue) {
        this.radioBtnValue = radioBtnValue;
    }

    public String getRadioBtnValue() {
        return radioBtnValue;
    }

    public void setPriceMpGb(RichOutputText priceMpGb) {
        this.priceMpGb = priceMpGb;
    }

    public RichOutputText getPriceMpGb() {
        return priceMpGb;
    }

    public void setPriceRopGb(RichOutputText priceRopGb) {
        this.priceRopGb = priceRopGb;
    }

    public RichOutputText getPriceRopGb() {
        return priceRopGb;
    }

    public void finish(ActionEvent actionEvent) {
        deletePreviousReport();
        insertRowReport();
        commit();
        redirectToOracleCloudPage();
    }
    
    public void deletePreviousReport() {
            DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();   
            DCIteratorBinding it = (DCIteratorBinding)bindings.get("CustomerReportView1Iterator");   
            ViewObject viewObject = it.getViewObject();
            
            String customerId = getCustomerId((String)email.getValue());
            
            viewObject.setWhereClause("CUSTOMER_ID="+customerId);
            viewObject.executeQuery();
            while (viewObject.hasNext()) {
                System.out.println("Delete: got row...");
                viewObject.next();
                viewObject.removeCurrentRow();
                System.out.println("Delete: ...deleted row!");
            }
        }
    
    public void insertRowReport() {
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();   
        DCIteratorBinding it = (DCIteratorBinding)bindings.get("CustomerReportView1Iterator");   
        ViewObject viewObject = it.getViewObject();
        Row newRow = viewObject.createRow();
        
        String customerId = getCustomerId((String)email.getValue());
        
        newRow.setAttribute("CustomerId", customerId);
        newRow.setAttribute("DataInfoId", getDataInfoId(customerId));
        newRow.setAttribute("TimestampCommit", new Date());
        viewObject.insertRow(newRow);
        
        System.out.println("Inserted: row inserted! : "+newRow.getAttribute("CustomerId")+","+newRow.getAttribute("DataInfoId"));
    }
    
    private Number getDataInfoId(String customerId) {
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding it = (DCIteratorBinding)bindings.get("UserinfoView1Iterator");
        ViewObject viewObject = it.getViewObject();
    
        viewObject.setOrderByClause("ROW_NUMBER");
        viewObject.setWhereClause("CONTACT_INFO_ID='" + customerId +"'");
        viewObject.executeQuery();     
        Row r = viewObject.last();
        return (Number)r.getAttribute("RowNumber");
    }

    private String getCustomerId(String email) {
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding it = (DCIteratorBinding)bindings.get("ContactinfoView1Iterator");
        ViewObject viewObject = it.getViewObject();
    
        viewObject.setWhereClause("EMAIL='" + email + "'");
        viewObject.executeQuery();
        Row r = viewObject.first();

        System.out.println("getCustomerId("+email+")="+(String)r.getAttribute("Id"));

        return (String)r.getAttribute("Id");
    }
    
    private void commit(){
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();   
        DCIteratorBinding it = (DCIteratorBinding)bindings.get("CustomerReportView1Iterator");   
        ViewObject viewObject = it.getViewObject();
        OperationBinding commitBinding = (OperationBinding)bindings.getOperationBinding("Commit");
        commitBinding.execute();
    }
    
    public void redirectToOracleCloudPage() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(CLOUD_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
