package raf.bolnica1.patient.util;

import com.sendgrid.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import raf.bolnica1.patient.domain.Patient;
import raf.bolnica1.patient.domain.VaccinationData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Random;

public class PDFUtils {

    /**
     * fields:
     *      - certificateID
     *      - certificateDate
     *      - name
     *      - gender
     *      - dateOfBirth
     *      - jmbg
     *
     *      - vaccineType
     *      - manufacturer
     *      - vaccinationDate
     *      - healthCareInstitution
     *
     *      - PCRResult
     *      - sampleType
     *      - PCRManufacturer
     *      - dateOfSampling
     *      - dateOfResult
     *      - PCRLab
     *
     *      - agResult
     *      - agSampleType
     *      - agManufacturer
     *      - agDateOfSampling
     *      - agDateOfResult
     *      - agLab
     *
     *      - dateOfPositiveTest
     *
     */


    public static String makeVaccinationCertificate(Patient patient, VaccinationData vaccinationData){

        PDDocument template;
        try {
            template = PDDocument.load(new File("sertifikat.pdf"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PDAcroForm acroForm = template.getDocumentCatalog().getAcroForm();
        List<PDField> fields = acroForm.getFields();

        for (PDField field : fields) {
            if (field instanceof PDTextField) {


                System.out.println(field.getPartialName());
                if (field.getPartialName().equals("certificateID")) {
                    try {
                        Random random = new Random();
                        field.setValue(Integer.toString(random.nextInt()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (field.getPartialName().equals("certificateDate")) {
                    try {
                        LocalDateTime currentDateTime = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String formattedDateTime = currentDateTime.format(formatter);
                        field.setValue(formattedDateTime);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (field.getPartialName().equals("name")) {
                    try {
                        field.setValue(patient.getName()+" "+patient.getSurname());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (field.getPartialName().equals("gender")) {
                    try {
                        field.setValue(patient.getGender().name());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (field.getPartialName().equals("dateOfBirth")) {
                    try {
                        field.setValue(patient.getDateOfBirth().toString());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (field.getPartialName().equals("jmbg")) {
                    try {
                        field.setValue(patient.getJmbg());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (field.getPartialName().equals("vaccineType")) {
                    try {
                        field.setValue(vaccinationData.getVaccination().getName());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (field.getPartialName().equals("manufacturer")) {
                    try {
                        field.setValue(vaccinationData.getVaccination().getManufacturer());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (field.getPartialName().equals("vaccinationDate")) {
                    try {
                        field.setValue(vaccinationData.getVaccinationDate().toString());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (field.getPartialName().equals("healthCareInstitution")) {
                    try {
                        field.setValue("Opsta bolnica \"Radivoje Domanovic\" ");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }



            }
        }

        try {
            template.save(new File(patient.getLbp()+".pdf"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            template.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return patient.getLbp()+".pdf";

    }

    public static void sendToMail(String pdf,String email){

        /// sendgrid password: 1589grewg431ffwew

        Email from = new Email("rafsibolnica@gmail.com");
        String subject = "Kovid Sertifikat";

        System.out.println(email);
        //Email to = new Email(email);
        Email to = new Email("stefanbalaz2@gmail.com");

        Content content = new Content("text/plain", "Sertifikat");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("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");
        Request request = new Request();

        // Attach the PDF file
        Path pdfFilePath = Paths.get(pdf);
        byte[] pdfBytes = new byte[0];
        try {
            pdfBytes = Files.readAllBytes(pdfFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Attachments attachments = new Attachments();
        attachments.setContent(new String(Base64.getEncoder().encode(pdfBytes)));
        attachments.setType("application/pdf");
        attachments.setFilename("attachment.pdf");
        attachments.setDisposition("attachment");
        attachments.setContentId("PDF");
        mail.addAttachments(attachments);

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            //throw ex;
            ex.printStackTrace();
        }


    }


    public static void removePDF(String filePath){
        File file = new File(filePath);

        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                System.out.println("PDF file deleted successfully.");
            } else {
                System.out.println("Failed to delete the PDF file.");
            }
        } else {
            System.out.println("The specified PDF file does not exist.");
        }
    }


}
