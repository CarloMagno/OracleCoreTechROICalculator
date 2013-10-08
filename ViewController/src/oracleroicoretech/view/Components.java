package oracleroicoretech.view;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.adf.model.BindingContext;
import oracle.adf.model.OperationBinding;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.binding.BindingContainer;

public class Components {
    private RichOutputText ot1; // HighPerformance total amount in partitioning.
    private RichOutputText ot2; // Modular total amount in partitioning.
    private RichOutputText ot3; // ReadOnly total amount in partitioning.
    private RichInputText it10; // Factor value.
    private RichInputText it6; // Price per GB in HighPerformance.
    private RichInputText it3; // HighPerformance GBs.
    private RichInputText it8; // Modular GBs.
    private RichInputText it5; // ReadOnly GBs.

    public Components() {
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
            new BigDecimal(it3.getValue().toString()).add(new BigDecimal(it5.getValue().toString()).add(new BigDecimal(it8.getValue().toString())));
        csv.append("ONLY HIGH PERFORMANCE STORAGE,," +
                   gbsOnlyHP.doubleValue() + "," +
                   gbsOnlyHP.multiply(new BigDecimal(it6.getValue().toString())));
        csv.append(System.getProperty("line.separator"));

        csv.append("PARTITIONING");
        csv.append(System.getProperty("line.separator"));
        csv.append(",");
        csv.append("High Performance Storage," +
                   new BigDecimal(it3.getValue().toString()).doubleValue() +
                   "," +
                   new BigDecimal(ot1.getValue().toString()).doubleValue());
        csv.append(System.getProperty("line.separator"));
        csv.append(",");
        csv.append("Modular Storage," +
                   new BigDecimal(it8.getValue().toString()).doubleValue() +
                   "," +
                   new BigDecimal(ot2.getValue().toString()).doubleValue());
        csv.append(System.getProperty("line.separator"));
        csv.append(",");
        csv.append("Read-Only Storage," +
                   new BigDecimal(it5.getValue().toString()).doubleValue() +
                   "," +
                   new BigDecimal(ot3.getValue().toString()).doubleValue());
        csv.append(System.getProperty("line.separator"));

        csv.append("PARTITIONING + ADVANCE COMPRESSION FACTOR " +
                   new BigDecimal(it10.getValue().toString()).doubleValue());
        csv.append(System.getProperty("line.separator"));
        csv.append(",");
        csv.append("High Performance Storage," +
                   new BigDecimal(it3.getValue().toString()).divide(new BigDecimal(it10.getValue().toString()),
                                                                    2,
                                                                    RoundingMode.HALF_UP).doubleValue() +
                   "," +
                   new BigDecimal(ot1.getValue().toString()).divide(new BigDecimal(it10.getValue().toString()),
                                                                    2,
                                                                    RoundingMode.HALF_UP).doubleValue());
        csv.append(System.getProperty("line.separator"));
        csv.append(",");
        csv.append("Modular Storage," +
                   new BigDecimal(it8.getValue().toString()).divide(new BigDecimal(it10.getValue().toString()),
                                                                    2,
                                                                    RoundingMode.HALF_UP).doubleValue() +
                   "," +
                   new BigDecimal(ot2.getValue().toString()).divide(new BigDecimal(it10.getValue().toString()),
                                                                    2,
                                                                    RoundingMode.HALF_UP).doubleValue());
        csv.append(System.getProperty("line.separator"));
        csv.append(",");
        csv.append("Read-Only Storage," +
                   new BigDecimal(it5.getValue().toString()).divide(new BigDecimal(it10.getValue().toString()),
                                                                    2,
                                                                    RoundingMode.HALF_UP).doubleValue() +
                   "," +
                   new BigDecimal(ot3.getValue().toString()).divide(new BigDecimal(it10.getValue().toString()),
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
                new BigDecimal(it3.getValue().toString()).add(new BigDecimal(it5.getValue().toString()).add(new BigDecimal(it8.getValue().toString())));
            writer.append("ONLY HIGH PERFORMANCE STORAGE,," +
                          gbsOnlyHP.doubleValue() + "," +
                          gbsOnlyHP.multiply(new BigDecimal(it6.getValue().toString())));
            writer.append(System.getProperty("line.separator"));

            writer.append("PARTITIONING");
            writer.append(System.getProperty("line.separator"));
            writer.append(",");
            writer.append("High Performance Storage," +
                          new BigDecimal(it3.getValue().toString()).doubleValue() +
                          "," +
                          new BigDecimal(ot1.getValue().toString()).doubleValue());
            writer.append(System.getProperty("line.separator"));
            writer.append(",");
            writer.append("Modular Storage," +
                          new BigDecimal(it8.getValue().toString()).doubleValue() +
                          "," +
                          new BigDecimal(ot2.getValue().toString()).doubleValue());
            writer.append(System.getProperty("line.separator"));
            writer.append(",");
            writer.append("Read-Only Storage," +
                          new BigDecimal(it5.getValue().toString()).doubleValue() +
                          "," +
                          new BigDecimal(ot3.getValue().toString()).doubleValue());
            writer.append(System.getProperty("line.separator"));

            writer.append("PARTITIONING + ADVANCE COMPRESSION FACTOR " +
                          new BigDecimal(it10.getValue().toString()).doubleValue());
            writer.append(System.getProperty("line.separator"));
            writer.append(",");
            writer.append("High Performance Storage," +
                          new BigDecimal(it3.getValue().toString()).divide(new BigDecimal(it10.getValue().toString()),
                                                                           2,
                                                                           RoundingMode.HALF_UP).doubleValue() +
                          "," +
                          new BigDecimal(ot1.getValue().toString()).divide(new BigDecimal(it10.getValue().toString()),
                                                                           2,
                                                                           RoundingMode.HALF_UP).doubleValue());
            writer.append(System.getProperty("line.separator"));
            writer.append(",");
            writer.append("Modular Storage," +
                          new BigDecimal(it8.getValue().toString()).divide(new BigDecimal(it10.getValue().toString()),
                                                                           2,
                                                                           RoundingMode.HALF_UP).doubleValue() +
                          "," +
                          new BigDecimal(ot2.getValue().toString()).divide(new BigDecimal(it10.getValue().toString()),
                                                                           2,
                                                                           RoundingMode.HALF_UP).doubleValue());
            writer.append(System.getProperty("line.separator"));
            writer.append(",");
            writer.append("Read-Only Storage," +
                          new BigDecimal(it5.getValue().toString()).divide(new BigDecimal(it10.getValue().toString()),
                                                                           2,
                                                                           RoundingMode.HALF_UP).doubleValue() +
                          "," +
                          new BigDecimal(ot3.getValue().toString()).divide(new BigDecimal(it10.getValue().toString()),
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
        BigDecimal hpGbs = new BigDecimal(it3.getValue().toString());
        BigDecimal mGbs = new BigDecimal(it8.getValue().toString());
        BigDecimal ropGbs = new BigDecimal(it5.getValue().toString());
        BigDecimal hpEurPerGB = new BigDecimal(it6.getValue().toString());

        mGbs = mGbs.add(ropGbs);
        hpGbs = hpGbs.add(mGbs);
        hpGbs = hpGbs.multiply(hpEurPerGB);

        BigDecimal hpPartitioningAmount =
            new BigDecimal(ot1.getValue().toString());
        BigDecimal mPartitioningAmount =
            new BigDecimal(ot2.getValue().toString());
        BigDecimal ropPartitioningAmount =
            new BigDecimal(ot3.getValue().toString());

        BigDecimal factorValue = new BigDecimal(it10.getValue().toString());

        BigDecimal hpAdvCompression =
            new BigDecimal(ot1.getValue().toString());
        BigDecimal mAdvCompression = new BigDecimal(ot2.getValue().toString());
        BigDecimal ropAdvCompression =
            new BigDecimal(ot3.getValue().toString());

        if (!factorValue.equals(new BigDecimal(0.0))) {
            hpAdvCompression =
                    hpAdvCompression.divide(factorValue, 2, RoundingMode.HALF_UP);
            mAdvCompression =
                    mAdvCompression.divide(factorValue, 2, RoundingMode.HALF_UP);
            ropAdvCompression =
                    ropAdvCompression.divide(factorValue, 2, RoundingMode.HALF_UP);
        }

        System.out.println("...Retrieved!");
        System.out.println("Creating data graph...");
        ArrayList list = new ArrayList();
        String[] rowLabels =
            new String[] { "High Performance Storage", "Modular Storage",
                           "ReadOnly Storage" };
        String[] colLabels =
            new String[] { "Only High Performance", "Partitioning",
                           "Partitioning + AdvCompression" };
        BigDecimal[][] values = new BigDecimal[][] {
                /* High Performance Storage Row */ { hpGbs,
                                                     hpPartitioningAmount,
                                                     hpAdvCompression },
                /* Modular Storage Row */ { new BigDecimal(0.0),
                                            mPartitioningAmount,
                                            mAdvCompression },
                /* Read Only Storage Row */ { new BigDecimal(0.0),
                                              ropPartitioningAmount,
                                              ropAdvCompression } };

        for (int c = 0; c < colLabels.length; c++) {
            for (int r = 0; r < rowLabels.length; r++) {
                list.add(new Object[] { colLabels[c], rowLabels[r],
                                        new Double(values[r][c].doubleValue()) });
            }
        }
        System.out.println("...Created!");
        return list;
    }

    public void setOt1(RichOutputText ot1) {
        this.ot1 = ot1;
    }

    public RichOutputText getOt1() {
        return ot1;
    }

    public void setOt2(RichOutputText ot2) {
        this.ot2 = ot2;
    }

    public RichOutputText getOt2() {
        return ot2;
    }

    public void setOt3(RichOutputText ot3) {
        this.ot3 = ot3;
    }

    public RichOutputText getOt3() {
        return ot3;
    }

    public void setIt10(RichInputText it10) {
        this.it10 = it10;
    }

    public RichInputText getIt10() {
        return it10;
    }

    public void setIt6(RichInputText it6) {
        this.it6 = it6;
    }

    public RichInputText getIt6() {
        return it6;
    }

    public void setIt3(RichInputText it3) {
        this.it3 = it3;
    }

    public RichInputText getIt3() {
        return it3;
    }

    public void setIt8(RichInputText it8) {
        this.it8 = it8;
    }

    public RichInputText getIt8() {
        return it8;
    }

    public void setIt5(RichInputText it5) {
        this.it5 = it5;
    }

    public RichInputText getIt5() {
        return it5;
    }

}
