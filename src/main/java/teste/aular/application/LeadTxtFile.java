package teste.aular.application;

import org.springframework.beans.factory.annotation.Autowired;
import teste.aular.domain.contract.LeadPetRepository;
import teste.aular.domain.contract.LeadPetTutorRepository;
import teste.aular.domain.entity.LeadPet;
import teste.aular.domain.entity.LeadPetTutor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LeadTxtFile {

    @Autowired
    private static LeadPetRepository leadPetRepository;

    @Autowired
    private static LeadPetTutorRepository leadPetTutorRepository;

    private static List<LeadPetTutor> listLeadPetTutorReaded;
    private static List<LeadPet> listLeadPetReaded;

    public static void readTxtFile(String fileName) {
        BufferedReader input = null;
        String record, recordType;
        Integer leadPetTutorId, leadPetId;
        String name, email, password, documentId, phoneNumber;
        String specie, breed, healthDescription;
        LocalDate birthdate;
        Integer leadPetTutorIdReaded;
        int countRecordsRead = 0;
        int countRecordedRecords;

        listLeadPetTutorReaded = new ArrayList<>();
        listLeadPetReaded = new ArrayList<>();


        // try-catch to open the file
        try{
            input = new BufferedReader(new FileReader(fileName));
        }
        catch (IOException error){
            System.out.println("Error opening file");
            error.printStackTrace();
        }

        // try-catch to read and close file
        try {
            // reading the first record from the file
            record = input.readLine();

            while (record != null) {
                recordType = record.substring(0, 2);

                if (recordType.equals("00")){
                    System.out.println("Header record");
                    System.out.println("File type: " + record.substring(2, 16));
                    System.out.println("File generations date/time: " + record.substring(16, 36));
                    System.out.println("Layout version: " + record.substring(36, 38));
                }
                else if (recordType.equals("01")){
                    System.out.println("Trailer record");
                    countRecordedRecords = Integer.parseInt(record.substring(2,7));
                    System.out.println("Number of data records readed: " + countRecordsRead);
                    System.out.println("number of data records recorded: " + countRecordedRecords);
                    if (countRecordsRead == countRecordedRecords){
                        System.out.println("Number of read data records compatible with " +
                                "the number of written data records");
                    }
                    else {
                        System.out.println("Number of read data records incompatible with " +
                                "the number of written data records");
                    }
                }
                else if (recordType.equals("02")) {
                    System.out.println("Pet tutor body record");
                    leadPetTutorId = Integer.valueOf(record.substring(2,8));
                    name = record.substring(8, 58).trim();
                    email = record.substring(58,98).trim();
                    password = record.substring(98, 104).trim();
                    documentId = record.substring(104, 115).trim();
                    phoneNumber = record.substring(115, 129).trim();
                    countRecordsRead++;

                    LeadPetTutor leadPetTutor = new LeadPetTutor(leadPetTutorId, name, email, password, documentId, phoneNumber);
                    listLeadPetTutorReaded.add(leadPetTutor);
                    System.out.println(leadPetTutor);
                }
                else if (recordType.equals("03")) {
                    System.out.println("Pet body record");
                    leadPetId = Integer.valueOf(record.substring(2,8));
                    name = record.substring(8, 58).trim();
                    specie = record.substring(58, 78).trim();
                    breed = record.substring(78, 118).trim();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    birthdate = LocalDate.parse(record.substring(118, 128), formatter);
                    healthDescription = record.substring(128, 228).trim();
                    leadPetTutorIdReaded = Integer.valueOf(record.substring(228,234));
                    
                    LeadPetTutor leadPetTutorById = null;
                    for (LeadPetTutor leadPetTutorReaded: listLeadPetTutorReaded){
                        if (leadPetTutorReaded.getLeadPetTutorId().equals(leadPetTutorIdReaded)){
                            leadPetTutorById = leadPetTutorReaded;
                        }
                    }
                    countRecordsRead++;
                    LeadPet leadPet = new LeadPet(leadPetId, name, specie, breed, birthdate, healthDescription, leadPetTutorById);
                    listLeadPetReaded.add(leadPet);
                    System.out.println(leadPet);
                }
                else {
                    System.out.println("Invalid record type");
                }
                // Read the next record
                record = input.readLine();
            }
            // Close the file
            input.close();
        }
        catch (IOException error){
            System.out.println("error reading file");
            error.printStackTrace();
        }
    }


    public static List<LeadPetTutor> getListLeadPetTutorReaded() {
        return listLeadPetTutorReaded;
    }

    public static void setListLeadPetTutorReaded(List<LeadPetTutor> listLeadPetTutorReaded) {
        LeadTxtFile.listLeadPetTutorReaded = listLeadPetTutorReaded;
    }

    public static List<LeadPet> getListLeadPetReaded() {
        return listLeadPetReaded;
    }

    public static void setListLeadPetReaded(List<LeadPet> listLeadPetReaded) {
        LeadTxtFile.listLeadPetReaded = listLeadPetReaded;
    }
}
