package be.helha.assurapp.expertise.controllers;

import be.helha.assurapp.expertise.models.Claim;
import be.helha.assurapp.expertise.services.IClaimService;
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

    @GetMapping
    public List<Claim> findAll() {
        return claimService.findAll();
    }

    @GetMapping("/{id}")
    public Claim findById(@PathVariable Long id) {
        return claimService.findById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Claim save(@RequestPart Claim claim, @RequestPart MultipartFile image) {
        long timestamp = System.currentTimeMillis();
        String imageFile = "claim_image_" + timestamp + ".png";

        //Claim claim = new Claim(0L, "ezfz", new Date(), "PENDING", imageFile, null);


        try {
            image.transferTo(new File(FileSystems.getDefault().getPath("..").toAbsolutePath() + "/assurapp-web/src/assets/claim-images/" + imageFile));
        } catch (IOException e){
            e.printStackTrace();
        }

        imageFile = "assets/claim-images/" + imageFile;

        claim.setImageFile(imageFile);
       return claimService.save(claim);
    }


    @PutMapping
    public Claim update(Claim claim) {
        return claimService.update(claim);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        claimService.deleteById(id);
    }

}
