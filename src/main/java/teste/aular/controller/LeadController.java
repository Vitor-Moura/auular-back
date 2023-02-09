package teste.aular.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import teste.aular.utils.Disc;
import teste.aular.utils.QueueObj;
import teste.aular.application.LeadTxtFile;
import teste.aular.domain.contract.LeadPetRepository;
import teste.aular.domain.contract.LeadPetTutorRepository;
import teste.aular.domain.entity.LeadPet;
import teste.aular.domain.entity.LeadPetTutor;

import java.util.List;


@RestController
@RequestMapping("/leads")
public class LeadController {

    @Autowired private JavaMailSender mailSender;
    @Autowired
    private LeadPetTutorRepository leadPetTutorRepository;
    @Autowired
    private LeadPetRepository leadPetRepository;

    @Autowired
    private Disc disc;

    @GetMapping
    public ResponseEntity<List<LeadPet>> getLeads() {
        List<LeadPet> lista = leadPetRepository.findAll();
        return lista.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(lista);
    }

    @PostMapping("/uploadLeadsFile")
    public void uploadLeadsFile(@RequestParam MultipartFile leadFile) {
        disc.saveLeadFile(leadFile);
        sendMail();
    }

    QueueObj<LeadPetTutor> filaLeadPetTutor;
    QueueObj<LeadPet> filaLeadPet;

    //Read the Leads file (PetTutor and Pet), stores in the queue and send email
    @PostMapping("/scheduleLeads")
    public ResponseEntity<QueueObj<LeadPet>> scheduleLeads() {
        LeadTxtFile.readTxtFile("/Users/vitormoura/Desktop/leadFiles/LEADS.TXT");

        LeadTxtFile lista1 = new LeadTxtFile();
        filaLeadPetTutor = new QueueObj<>(lista1.getListLeadPetTutorReaded().size());
        for (LeadPetTutor leadPetTutor : lista1.getListLeadPetTutorReaded()) {
            filaLeadPetTutor.insert(leadPetTutor);
        }

        LeadTxtFile lista2 = new LeadTxtFile();
        filaLeadPet = new QueueObj<>(lista2.getListLeadPetReaded().size());
        for (LeadPet leadPet : lista2.getListLeadPetReaded()) {
            filaLeadPet.insert(leadPet);
        }

        if (filaLeadPetTutor.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(200).body(filaLeadPet);
        }
    }

    // Saves in the database the Leads that were in the queue
    @PostMapping("/saveLeads")
    public ResponseEntity<?> saveLeads(){

        if (filaLeadPet == null || filaLeadPet.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        else {
            while (!filaLeadPetTutor.isEmpty()) {
                leadPetTutorRepository.save(filaLeadPetTutor.poll());
            }
            while (!filaLeadPet.isEmpty()) {
                leadPetRepository.save(filaLeadPet.poll());
            }
            return ResponseEntity.status(200).build();
        }
    }

    // sendMail method, endpoint created to be able to test only the sending
    @GetMapping("/sendEmailTest")
    public void sendMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Leads in queue ");
        message.setText("New file with leads in leadsFile folder.\n\n" +
                "Access the link below to check the file data!");
        message.setTo("contato.auular@gmail.com");
        message.setFrom("contato.auular@gmail.com");

        try {
            mailSender.send(message);
            System.out.println("Email successfully sent!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error sending email!");
        }
    }

}
