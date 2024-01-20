package be.helha.assurapp.expertise.controllers;

import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.expertise.models.Claim;
import be.helha.assurapp.expertise.services.IClaimService;
import be.helha.assurapp.utils.EmailSender;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/claims")
public class ClaimController {

    private IClaimService claimService;
    private EmailSender emailSender;

    @GetMapping
    public List<Claim> findAll() {
        return claimService.findAll();
    }

    @GetMapping("/user/{id}")
    public List<Claim> findByUserId(@PathVariable Long id) {
        return claimService.findClaimByClient(id);
    }
    @GetMapping("/expert/{id}")
    public List<Claim> findByExpertId(@PathVariable Long id) {
        return claimService.findClaimByExpert(id);
    }

    @GetMapping("/insurer/{id}")
    public List<Claim> findByInsurerId(@PathVariable Long id) {
        return claimService.findClaimByInsurer(id);
    }

    @GetMapping("/insurer/{id}/pending")
    public List<Claim> findPendingByInsurerId(@PathVariable Long id) {
        return claimService.findPendingClaimByInsurer(id);
    }

    @GetMapping("/status/{status}")
    public List<Claim> findByStatus(@PathVariable String status) {
        return claimService.findClaimByStatus(status);
    }

    @GetMapping("/{id}")
    public Claim findById(@PathVariable Long id) {
        return claimService.findById(id);
    }

    @PostMapping
    public Claim save(@RequestBody Claim claim){
       return claimService.save(claim);
    }


    @PutMapping
    public Claim update(@RequestBody Claim claim) {
        return claimService.update(claim);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        claimService.deleteById(id);
    }

    @PostMapping("/notifyExpert")
    public Claim notifyExpert(@RequestBody Claim claim) {

        Claim c = this.claimService.findById(claim.getId());

        User expert = c.getExpert();

        String message = String.format("""
        Dear %s,

        You have been assigned as the expert for the following claim:

        Claim Details:

    Claim ID: %s
    Description: %s
    Assignment Date: %s
  Please review the details and provide your expert evaluation and recommendations at your earliest convenience.
  If you have any questions or need additional information, feel free to contact us.
  Thank you for your expertise and cooperation.
  Best regards,%s""",
                expert.getName() + ' ' + expert.getLastname().toUpperCase(),
                c.getId(),
                c.getDescription(),
                c.getDate(),

                expert.getName() + ' ' + expert.getLastname());

        this.emailSender.send(expert.getEmail(), "noreply@example.com", "Claim Assignment Notification", message);
        return c;
    }


}
