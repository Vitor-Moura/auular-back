package teste.aular.application;

import teste.aular.domain.entity.Partner;
import teste.aular.utils.ListObj;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.FileWriter;

public class PartnerCvsFileService {

    public static void PartnerCsvGenerate(ListObj<Partner> list, String fileName) {
        FileWriter file = null;
        Formatter output = null;
        Boolean went_bad = false;
        String headerRecord = "00";
        String bodyRecord = "02";
        String trailerRecord = "01";
        String fileType = "Partner";
        String layoutVersion = "01";
        fileName += ".csv";

        try {
            file = new FileWriter(fileName);
            output = new Formatter(file);
        } catch (IOException erro) {
            System.out.println("Error opening file");
            System.exit(1);
        }
        try {
            //Header
            output.format("%s;%S;%s;%s\n",
                    headerRecord,
                    fileType,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    layoutVersion
            );
            //Body
            for (int i = 0; i < list.getSize(); i++) {
                Partner p = list.getElement(i);
                output.format("%s;%d;%s;%s;%s;%s;%b;%s;%b;%s;%s;%s;%b\n",
                        bodyRecord,
                        p.getPartnerId(),
                        p.getPartnerUuid(),
                        p.getName(),
                        p.getEmail(),
                        p.getDocumentId(),
                        p.isFidelity(),
                        p.getPhoneNumber(),
                        p.getAuthenticated(),
                        p.getCreatedAt(),
                        p.getUpdatedAt(),
                        p.getDeactivatedAt(),
                        p.getActive()
                );
            }
            //Trailer
            output.format("%s;%d\n",
                    trailerRecord,
                    list.getSize()
            );

        } catch (FormatterClosedException error) {

            System.out.println("Error writing file");
            error.printStackTrace();
            went_bad = true;
        } finally {
            output.close();
            try {
                file.close();
            } catch (IOException erro) {
                System.out.println("Error closing file");
                went_bad = true;
            }
            if (went_bad) {
                System.exit(1);
            }
        }

    }

    public static void ReadShowPartnerCvs(String fileName) {

        FileReader file = null;
        Scanner input = null;
        Boolean failed = false;
        fileName += ".csv";

        try {
            file = new FileReader(fileName);
            input = new Scanner(file).useDelimiter(";|\\n");
        } catch (FileNotFoundException error) {
            System.out.println("File not found!");
            System.exit(1);

        }
        try {
            System.out.printf("%2S %36S %50S %40S %15S %8S %14S %12S %20S %20S %20S %8S\n",
                    "id",
                    "uuid",
                    "name",
                    "email",
                    "document id",
                    "fidelity",
                    "phone_number",
                    "authenticated",
                    "created_at",
                    "updated_at",
                    "deactivated_at",
                    "active"
            );
            while (input.hasNext()) {
                String id = input.next();
                String uuid = input.next();
                String name = input.next();
                String email = input.next();
                String document_id = input.next();
                String fidelity = input.next();
                String phone_number = input.next();
                String authenticated = input.next();
                String created_at = input.next();
                String updated_at = input.next();
                String deactivated_at = input.next();
                String active = input.next();
                System.out.printf("%2s %36s %50s %40s %15s %8s %14s %12s %20s %20s %20s %8s",
                        id,
                        uuid,
                        name,
                        email,
                        document_id,
                        fidelity,
                        phone_number,
                        authenticated,
                        created_at,
                        updated_at,
                        deactivated_at,
                        active
                );
            }
        } catch (NoSuchElementException error) {
            System.out.println("Problem file!");
            System.out.println(error);
            error.printStackTrace();
            failed = true;
        } catch (IllegalStateException error) {
            System.out.println("Error reading the file");
            System.out.println(error);
            error.printStackTrace();
            failed = true;
        } finally {
            input.close();
            try {
                file.close();
            } catch (IOException error) {
                System.out.println("Error closing file!");
                failed = true;
            }
            if (failed) {
                System.exit(1);
            }
        }
    }
}
