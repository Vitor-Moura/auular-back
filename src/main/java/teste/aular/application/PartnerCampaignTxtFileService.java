package teste.aular.application;

import teste.aular.domain.entity.Campaign;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PartnerCampaignTxtFileService {

    public static void writesRecord(String record, String fileName){
        BufferedWriter exit = null;

        // try-catch to open the file
        try {
            exit = new BufferedWriter(new FileWriter(fileName, true));
        }
        catch (IOException erro){
            System.out.println("Error opening file");
            erro.printStackTrace();
        }

        // try-catch to read and close file
        try{
            exit.append(record + "\n");
            exit.close();
        }
        catch (IOException error) {
            System.out.println("Error writing file");
            error.printStackTrace();
        }
    }

    public static void AllCapaignsTxtGenerate(List<Campaign> listaCampaign, String fileName){
        int countsDataRecord = 0;

        // Mounts header record
        String header =  "00";
        header += "CAMPAIGN";
        header += LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        header += "01";

        // Writes header record
        writesRecord(header, fileName);

        // Mounts and writes body records (or datas)
        String body;

        for (Campaign c : listaCampaign){
            //Validating if Partner is different from null because there are also hotel campaigns
            if (c.getPartner() != null ) {
                body = "02";
                body += String.format("%06d", c.getCampaignId());
                body += String.format("%-6.6s", c.getType());
                body += String.format("%08.2f", c.getValue());
                body += String.format("%06d", c.getClick());
                body += String.format("%-10.10s", c.getStartedAt());
                body += String.format("%-10.10s\n", c.getFinishedAt());
                body += "03";
                body += String.format("%06d", c.getPartner().getPartnerId());
                body += String.format("%-50.50s", c.getPartner().getName());
                body += String.format("%-40.40s", c.getPartner().getEmail());
                body += String.format("%-14.14s", c.getPartner().getDocumentId());
                body += String.format("%-5.5s", c.getPartner().isFidelity());
                body += String.format("%-14.14s", c.getPartner().getPhoneNumber());
                body += String.format("%-19.19s", c.getPartner().getCreatedAt());
                body += String.format("%-19.19s", c.getPartner().getUpdatedAt());
                body += String.format("%-19.19s", c.getPartner().getDeactivatedAt());
                body += String.format("%-5.5s", c.getPartner().getActive());

                writesRecord(body, fileName);
                countsDataRecord++;
            }
        }

        // Mounts and writes trailer records
        String trailer =  "01";
        trailer += String.format("%010d", countsDataRecord);

        writesRecord(trailer, fileName);

    }

    public static void CapaignsPartnerTxtGenerate(List<Campaign> listaCampaign, String fineName){
        int countsDataRecord = 0;

        // Mounts header record
        String header =  "00";
        header += "CAMPAIGN";
        header += LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        header += "01";

        // Writes header record
        writesRecord(header, fineName);

        // Mounts and writes body records (or datas)
        String body;

        for (Campaign c : listaCampaign){
            body = "02";
            body += String.format("%06d", c.getCampaignId());
            body += String.format("%-6.6s", c.getType());
            body += String.format("%08.2f", c.getValue());
            body += String.format("%06d", c.getClick());
            body += String.format("%-10.10s", c.getStartedAt());
            body += String.format("%-10.10s", c.getFinishedAt());

            writesRecord(body, fineName);
            countsDataRecord++;
        }


        // Mounts and writes trailer records
        String trailer =  "01";
        trailer += String.format("%010d", countsDataRecord);

        writesRecord(trailer, fineName);

    }




}
